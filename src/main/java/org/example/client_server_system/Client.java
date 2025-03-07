package org.example.client_server_system;

import org.example.game.GameWindow;
import org.example.game_selection.GameSelection;
import org.example.game_selection.panels.PanelType;
import org.example.game_selection.panels.WaitingLobby;
import org.example.logic.Farbe;
import org.example.logic.Karte;
import org.example.logic.Kartenart;
import org.example.logic.SpielAuswahl;

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
    private Farbe trumpf;


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
                            case OPEN_GAME -> openGame(content);
                            case CARD_PLAYED -> {
                                parts = content.split(":");
                                gameWindow.setSpielstart(true);
                                gameWindow.cardPlayed(parts[0]);
                                playerTurn = Integer.parseInt(parts[1]);
                            }
                            case REIZEN -> gameWindow.enableReizen(content, false);
                            case REIZ_ANTWORT -> gameWindow.enableReizen(content, true);
                            case START_SPIELAUSWAHL -> {
                                SpielAuswahl spielAuswahl = new SpielAuswahl(gameWindow);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Disconnected from server.");
            }
        }).start();
    }


    private void openGame(String message) {
        String[] decks = message.split(":");                    // alle Spielkarten
        List<Karte> deck = extractCards(decks[0].split(","));   // Spielkarten als Liste
        List<Karte> skat = extractCards(decks[1].split(","));   // Skat als Liste

        gameWindow = new GameWindow(deck, skat, this);
    }


    private List<Karte> extractCards(String[] list) {
        List<Karte> cards = new ArrayList<>();
        for (String card : list) {
            String[] parts = card.split(" ");
            cards.add(new Karte(Farbe.valueOf(parts[0]), Kartenart.valueOf(parts[1])));
        }
        return cards;
    }


    private void updateWaitingLobby(String message) {
        WaitingLobby.getInstance().setClient(this);
        WaitingLobby.getInstance().setPort(port);
        String[] players = message.split(";", 5);
        WaitingLobby.getInstance().players = new String[3][2];
        WaitingLobby.getInstance().connectedPlayers = 0;
        for (String player : players) {
            if (player.isEmpty()) continue;
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
