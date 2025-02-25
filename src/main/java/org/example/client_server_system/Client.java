package org.example.client_server_system;

import org.example.game_selection.GameSelection;
import org.example.game_selection.panels.PanelType;
import org.example.game_selection.panels.WaitingLobby;

import javax.swing.*;
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
    GameSelection parentwindow;
    int port;

    /**
     * Ein Client erkennt und sendet Inputs des Spielers an den Server und Updated die GUI durch Infos vomServer
     * @param username Name des Spielers im Spiel
     * @param ip Addresse aus dem Intranet
     * @param port Port des Servers mit dem man sich verbinden möchte
     */
    public Client(String username, String ip, int port, GameSelection parentwindow) {
        this.username = username;
        this.ip = ip;
        this.port = port;
        this.parentwindow = parentwindow;

        initClient();
        try {
            sendPlayerActions(MessageType.CONNECTION, username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        startServerListenLoop();
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

                        // wandelt bytes in String um
                        buffer.flip();
                        byte[] bytes = new byte[bytesRead];
                        buffer.get(bytes);
                        String message = new String(bytes).trim();
                        System.out.println("Server: " + message);

                        // teilt Nachricht in Typ und Inhalt auf
                        String[] parts = message.split(":", 2);
                        if (parts.length < 2) return; // Nachrichtenformat ungültig

                        MessageType messageType = MessageType.valueOf(parts[0]);
                        String content = parts[1];

                        switch (messageType){
                            case UPDATE_LOBBY -> updateWaitingLobby(content);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Disconnected from server.");
            }
        }).start();
    }

    private void updateWaitingLobby(String message) {
        WaitingLobby.getInstance().setPort(port);
        String[] players = message.split(";", 5);
        WaitingLobby.getInstance().players = new String[3][2];
        WaitingLobby.getInstance().connectedPlayers = 0;
        for (String player: players) {
            String[] parts = player.split(":", 2);
            WaitingLobby.getInstance().addPlayer(parts[0], parts[1]);
        }
        WaitingLobby.getInstance().updateWindow();
        parentwindow.changePanel(PanelType.WAITING_LOBBY);
    }

  
    private void sendPlayerActions(MessageType messageType, String message) throws IOException {

        ByteBuffer tempBuffer = ByteBuffer.wrap((messageType.name() + ":" + message + "\n").getBytes());
        clientChannel.write(tempBuffer);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
