package org.example.client_server_system;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {

    //Attribute
    SocketChannel clientChannel;
    ByteBuffer buffer;
    String username;
    String ip;
    int port;

    /**
     * Ein Client erkennt und sendet Inputs des Spielers an den Server und Updated die GUI durch Infos vomServer
     * @param username Name des Spielers im Spiel
     * @param ip Addresse aus dem Intranet
     * @param port Port des Servers mit dem man sich verbinden möchte
     */
    public Client(String username, String ip, int port) {
        this.username = username;
        this.ip = ip;
        this.port = port;

        initClient();
        startServerListenLoop();
        try {
            sendPlayerActions(MessageType.CARD_PLAYED, "c7");
            sendPlayerActions(MessageType.START_GAME, "Heheheha");
            sendPlayerActions(MessageType.UPDATE_LOBBY, "ne diggi");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initClient() {
        try {
            clientChannel = SocketChannel.open(new InetSocketAddress(ip, port));
            clientChannel.configureBlocking(false);
            buffer = ByteBuffer.allocate(1024);

            System.out.println("Connected to Skat Server!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void startServerListenLoop() {
        new Thread(() -> {
            try {
                while (true) {
                    buffer.clear();
                    int bytesRead = clientChannel.read(buffer);
                    if (bytesRead > 0) {
                        buffer.flip();
                        String message = new String(buffer.array(), 0, bytesRead);
                        System.out.println("Server: " + message);
                    }
                }
            } catch (IOException e) {
                System.out.println("Disconnected from server.");
            }
        }).start();
    }

    private void sendPlayerActions(MessageType messageType, String message) throws IOException {
        buffer.clear();
        message = messageType.name() + ":" + message;
        buffer.put(message.getBytes());
        buffer.flip();
        clientChannel.write(buffer);

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
