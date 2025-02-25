package org.example.client_server_system;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.stream.Collectors;

public class Server implements Runnable {

    //Attribute
    Selector selector;
    ServerSocketChannel serverChannel;
    private int port;
    private List<SocketChannel> clients = new ArrayList<>();
    private LinkedHashMap<SocketChannel, String> players = new LinkedHashMap<>();
    private final int MAX_PLAYERS = 3;

    //Constructor
    public Server(int port) {
        this.port = port;
    }

    private void initServer() {
        try {
            // Erstellt selector um mehrere Clients mit non-Blocking I/O zu managen
            selector = Selector.open();
            // TCP server socket channel sorgt für Verbindung zwischen Clients und Server
            serverChannel = ServerSocketChannel.open();
            // server channel Einstellungen
            serverChannel.bind(new InetSocketAddress(port));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Skat Server started on port " + port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void startServerLoop() {
        try {
            while (true) {
                // wartet auf Verbindung
                selector.select();
                // sammelt die Events vom Client
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                // Verarbeitung von Events
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (key.isAcceptable()) {
                        acceptPlayer(selector, serverChannel);
                    } else if (key.isReadable()) {
                        handlePlayerMove(key);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void acceptPlayer(Selector selector, ServerSocketChannel serverChannel) throws IOException {
        if (clients.size() >= MAX_PLAYERS) return; // nicht mehr als 3 Spieler

        // erstellt und registriert neuen Client
        SocketChannel client = serverChannel.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        clients.add(client);

        System.out.println("Player connected: " + client.getRemoteAddress());
    }

    private void handlePlayerMove(SelectionKey key) throws IOException {
        // ließt Nachricht vom Client
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = client.read(buffer);

        // checks for Disconnect
        if (bytesRead == -1) {
            System.out.println("Player disconnected.");
            clients.remove(client);
            client.close();
            return;
        }

        // wandelt bytes in String um
        buffer.flip();
        byte[] bytes = new byte[bytesRead];
        buffer.get(bytes);
        String message = new String(bytes).trim();
        System.out.println("Received: " + message);

        String[] messages = message.split("\n");
        for (String msg : messages) {
            processMessage(client, msg.trim());
        }

    }

    private void processMessage(SocketChannel client, String trim) {
        // teilt Nachricht in Typ und Inhalt auf
        String[] parts = trim.split(":", 2);
        if (parts.length < 2) return; // Nachrichtenformat ungültig

        MessageType messageType = MessageType.valueOf(parts[0]);
        String content = parts[1];

        // Verarbeitet unterschiedliche Typen von Nachrichten
        switch (messageType) {
            case CONNECTION:
                setMetaData(client, trim);
                updateWaitingLobby();
                break;
            case START_GAME:
                System.out.println("BID yay " + content);
                break;
            case CARD_PLAYED:
                System.out.println(content);
                break;
            default:
                System.out.println("Unknown message type.");
        }
    }

    private void updateWaitingLobby() {
        for (SocketChannel client: clients) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.clear();
            String message = MessageType.UPDATE_LOBBY.name() + ":" + socketListToString(players);
            buffer.put(message.getBytes());
            buffer.flip();
            try {
                client.write(buffer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setMetaData(SocketChannel socketChannel, String message){
        String[] parts = message.split(":", 2);
        players.put(socketChannel, parts[1]);
    }

    // mit Chat
    private String socketListToString(LinkedHashMap<SocketChannel, String> clients) {
        return clients.entrySet().stream()
                .map(entry -> {
                    SocketChannel channel = entry.getKey();
                    String username = entry.getValue(); // Get the username from the map
                    try {
                        InetSocketAddress remoteAddress = (InetSocketAddress) channel.getRemoteAddress();
                        return username + ":" + remoteAddress.getAddress().getHostAddress();
                    } catch (Exception e) {
                        return username + " (Unknown Address)";
                    }
                })
                .collect(Collectors.joining(";"));
    }

    @Override
    public void run() {
        initServer();
        startServerLoop();
    }

}
