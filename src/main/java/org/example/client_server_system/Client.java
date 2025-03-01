package org.example.client_server_system;

import org.example.game.GameWindow;
import org.example.game_selection.GameSelection;
import org.example.game_selection.panels.PanelType;
import org.example.game_selection.panels.WaitingLobby;
import org.example.logic.Farbe;
import org.example.logic.Karte;
import org.example.logic.Kartenart;

import javax.swing.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class Client {

    //Attribute
    private SocketChannel clientChannel;
    private ByteBuffer buffer;
    private String username;
    private String ip;
    private GameSelection parentwindow;
    private int port;
    private GameWindow gameWindow;
    private int playerTurn = 0;

    /**
     * Ein Client erkennt und sendet Inputs des Spielers an den Server und Updated die GUI durch Infos vomServer
     *
     * @param username Name des Spielers im Spiel
     * @param ip       Addresse aus dem Intranet
     * @param port     Port des Servers mit dem man sich verbinden möchte
     */
    public Client(String username, String ip, int port, GameSelection parentwindow) {
        this.username = username;
        this.ip = ip;
        this.port = port;
        this.parentwindow = parentwindow;

        initClient();
        sendPlayerActions(MessageType.CONNECTION, username);
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

                        switch (messageType) {
                            case UPDATE_LOBBY -> updateWaitingLobby(content);
                            case START_GAME -> startGame(content);
                            case CARD_PLAYED -> playerTurn = Integer.parseInt(content);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Disconnected from server.");
            }
        }).start();
    }

    private void startGame(String message) {
        String[] cards = message.split(",");
        List<Karte> deck = new ArrayList<>();
        for (String card : cards) {
            String[] parts = card.split(" ");
            deck.add(new Karte(Farbe.valueOf(parts[0]), Kartenart.valueOf(parts[1])));
        }
        gameWindow = new GameWindow(deck, this);
    }

    private void updateWaitingLobby(String message) {
        WaitingLobby.getInstance().setClient(this);
        WaitingLobby.getInstance().setPort(port);
        String[] players = message.split(";", 5);
        WaitingLobby.getInstance().players = new String[3][2];
        WaitingLobby.getInstance().connectedPlayers = 0;
        for (String player : players) {
            if(player.isEmpty()) continue;
            String[] parts = player.split(":", 2);
            WaitingLobby.getInstance().addPlayer(parts[0], parts[1]);
        }
        WaitingLobby.getInstance().updateWindow();
        parentwindow.changePanel(PanelType.WAITING_LOBBY);
    }


    public void sendPlayerActions(MessageType messageType, String message) {
        ByteBuffer tempBuffer = ByteBuffer.wrap((messageType.name() + ":" + message + "\n").getBytes());

        try {
            clientChannel.write(tempBuffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public String getUsername() {
        return username;
    }
}
