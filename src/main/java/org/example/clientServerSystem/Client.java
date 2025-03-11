package org.example.clientServerSystem;

import org.example.game.GameWindow;
import org.example.gameSelection.GameSelection;
import org.example.gameSelection.panels.PanelType;
import org.example.gameSelection.panels.WaitingLobby;
import org.example.logic.*;

import javax.swing.*;
import java.awt.event.WindowEvent;
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
                 startClientLogic();
            } catch (IOException e) {
                System.out.println("Disconnected from server.");
            }
        }).start();

    }

    public void startClientLogic() throws IOException {
        while (true) {
            buffer.clear();
            int bytesRead = clientChannel.read(buffer);
            if (bytesRead > 0) {
                // wandelt bytes in String um
                buffer.flip();
                byte[] bytes = new byte[bytesRead];
                buffer.get(bytes);
                String message = new String(bytes).trim();
                System.out.println("Server-message: " + message);

                String[] messages = message.split("\n");

                for (String msg: messages){
                    msg = msg.trim();

                    // teilt Nachricht in Typ und Inhalt auf
                    String[] parts = msg.split(":", 2);
                    if (parts.length < 2) return; // Nachrichtenformat ungültig

                    MessageType messageType = MessageType.valueOf(parts[0]);
                    String content = parts[1];

                    switch (messageType) {
                        case UPDATE_LOBBY -> updateWaitingLobby(content);
                        case OPEN_GAME -> openGame(content);
                        case CARD_PLAYED -> cradPlayed(content);
                        case REIZEN -> gameWindow.enableReizen(content, false);
                        case REIZ_ANTWORT -> gameWindow.enableReizen(content, true);
                        case START_SPIELAUSWAHL -> new SpielAuswahl(gameWindow, this);
                        case TRUMPF -> trumpf(content);
                        case END_GAME -> showResult(content);
                        default -> throw new ArithmeticException("NO SUCH MESSAGEYTYPE");
                    }
                }
            }
        }
    }

    private void trumpf(String content) throws IOException {
        String ansageUser = "ERROR";
        for (String[] info: WaitingLobby.getInstance().players) {
            InetSocketAddress inetAddress = (InetSocketAddress) clientChannel.getRemoteAddress();
            if (info[1].equals(inetAddress.getAddress().getHostAddress())) ansageUser = info[0];
        }
        trumpf = Farbe.valueOf(content);
        Mischen mischen = new Mischen();
        gameWindow.setDeck(mischen.kartenSortieren(gameWindow.getDeck(), trumpf));
        gameWindow.updateButtonText();
        JOptionPane.showMessageDialog(gameWindow, "Spieler " + ansageUser + " spielt " + trumpf.name());
    }

    private void cradPlayed(String content) {
        System.out.println("Client-content: " + content);
        String[] parts = content.split(":");
        gameWindow.setSpielstart(true);
        gameWindow.cardPlayed(parts[0]);
        playerTurn = Integer.parseInt(parts[1]);
        gameWindow.enableButtons();
    }

    private void showResult(String content) {
        String[] parts = content.split(";");
        String message = "";
        message += parts[0].equals("WIN") ?
                WaitingLobby.getInstance().players[Integer.parseInt(parts[1])][0] + " hat mit " + parts[2] + " Augen gewonnen => +" + parts[3] + " Punkte." :
                WaitingLobby.getInstance().players[Integer.parseInt(parts[1])][0] + " hat mit " + parts[2] + " Augen verloren => --" + Integer.parseInt(parts[3]) * 2 + " Punkte.";
        JOptionPane.showMessageDialog(gameWindow, message);
    }


    private void openGame(String message) {
        if(gameWindow != null){
            gameWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            gameWindow.dispatchEvent(new WindowEvent(gameWindow, WindowEvent.WINDOW_CLOSING));
        }

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
