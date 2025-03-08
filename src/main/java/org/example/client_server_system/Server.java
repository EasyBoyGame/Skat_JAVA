package org.example.client_server_system;

import org.example.logic.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Server implements Runnable {

    //Attribute
    Selector selector;
    ServerSocketChannel serverChannel;
    private int port;
    private List<SocketChannel> clients = new ArrayList<>();
    private List<String> usernames = new ArrayList<>();
    private List<String> stich = new ArrayList<>();
    private int soloPlayer;
    private int augenSolo;
    private int augenDuo;
    private Reizen reizen;
    private final int MAX_PLAYERS = 3;
    private int gameturn;
    private int startPlayer = 2;
    private int reizPlayer;
    private int answPlayer;
    private Farbe trumpf;


    // TODO 1. WENN DIE ERSTEN BEIDEN SPIELER NEIN REIZEN, DANN MUSS DER LETZTE SPIELER DEN SKAT
    //  SOFORT BEKOMMEN (eigentlich nicht aber so wirds erstmal implementiert)

    // TODO 2. SPIEL MUSS BEENDET WERDEN, SOBALD ALLE KARTEN GESPIELT WURDEN

    // TODO 3. SOBALD SPIEL ANGESAGT WIRD (setSpielstart = true), MÜSSEN KARTEN BEI DUO ENTSPRECHEND
    //  SORTIERT WERDEN

    // TODO 4. KARTEN BILDER VON ARNE UND JULIAN EINFÜGEN

    // TODO 5. GESAMTEN CODE KOMMENTIEREN


    // TODO EXTRA (wichtig?): DATENBANK IN LISTE AUSLESEN, UM NACH DEM SPIELEN EINE
    //  SPIELAUSWERTUNG ZU HABEN

    // TODO EXTRA: SPIELER DISCONNECT



    @Override
    public void run() {
        initServer();
        startServerLoop();
    }


    //Constructor
    public Server(int port) {
        reizen = new Reizen();
        this.port = port;
    }


    private void initServer() {
        try {
            // erstellt Selector, um mehrere Clients mit non-Blocking I/O zu managen
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
        // liest Nachricht vom Client
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
                sendServerBroadcast(MessageType.UPDATE_LOBBY, socketListToString());
                break;
            case OPEN_GAME:
                gameturn = 1;
                spreadCards();
                startPlayer = (startPlayer + 1 > 2) ? 0 : +1;
                reizPlayer = (startPlayer + 2) % 3;
                answPlayer = (startPlayer + 1) % 3;
                sendServerMessage(clients.get(reizPlayer), MessageType.REIZEN, "" + reizen.appendReizwert());
                break;
            case REIZEN:
                reizen(content);
                break;
            case REIZ_ANTWORT:
                reizenAntwort(content);
                break;
            case CARD_PLAYED:
                if (stich.size() > 2) {
                    int stichWin = vergleichStich(stich);
                    if (stichWin == soloPlayer) {
                        augenSolo = augenZaehlen(augenSolo);
                    } else {
                        augenDuo = augenZaehlen(augenDuo);
                    }
                }
                stich.add(content);
                if (gameturn < 31) {
                    //sendServerMessage(clients.get((startPlayer + gameturn) % 3), MessageType.CARD_PLAYED, content);
                    // FIXME NOT FIXME: GAMETURN MUSS EINFACH NUR VORHER ERHÖHT WERDEN...
                    gameturn++;
                    sendServerBroadcast(MessageType.CARD_PLAYED, content + ":" + gameturn % 3);
                    System.out.println("Server-content: " + content);
                    //gameturn++;
                }
                if (gameturn == 30) {
                    // TODO 2. HIER REINFÜGEN
                    // AUGEN ZÄHLEN
                    // ENTSCHEIDEN WER GEWINNT UND IN DB DIE PUNKTE FÜRS JEWEILIGE anges. SPIEL EINTRAGEN
                    // GEWINNER BEKANNTGEBEN
                    // KARTEN NEU MISCHEN UND AUSTEILEN
                }
                break;
            case TRUMPF:
                this.trumpf = Farbe.valueOf(content);
                sendServerBroadcast(MessageType.TRUMPF, content);
                sendServerBroadcast(MessageType.CARD_PLAYED, ":" + (startPlayer + gameturn) % 3);
                break;
            default:
                System.out.println("Unknown message type.");
        }
    }


    private void reizenAntwort(String content) {
        if (content.equals("true")) {
            sendServerMessage(clients.get(reizPlayer), MessageType.REIZEN, "" + reizen.appendReizwert());
        } else {
            soloPlayer = reizPlayer;
            sendServerMessage(clients.get(reizPlayer), MessageType.START_SPIELAUSWAHL, "");
        }
    }


    private void reizen(String content) {
        if (content.equals("false")) {
            if (reizPlayer == startPlayer) {
                soloPlayer = answPlayer;
                sendServerMessage(clients.get(answPlayer), MessageType.START_SPIELAUSWAHL, "");
            } else {
                reizPlayer = startPlayer;
                sendServerMessage(clients.get(reizPlayer), MessageType.REIZEN, "" + reizen.getReizwert());
            }
        } else {
            sendServerMessage(clients.get(answPlayer), MessageType.REIZ_ANTWORT, "" + reizen.getReizwert());
        }
    }


    private void spreadCards() {
        Mischen mischen = new Mischen();
        int counter = 1;
        for (SocketChannel client : clients) {

            //region Stringbuilder
            List<Karte> karten = new ArrayList<>(mischen.getKarten(counter));
            List<Karte> skat = new ArrayList<>(mischen.getKarten(4));
            StringBuilder builder = new StringBuilder();
            for (Karte karte : karten) {
                builder.append(karte).append(",");
            }
            builder.append(":");    // ':' zur Trennung von Skat und den Spielkarten
            for (Karte karte : skat) {
                builder.append(karte).append(",");
            }
            //endregion Stringbuilder

            sendServerMessage(client, MessageType.OPEN_GAME, builder.toString());

            counter++;
        }
    }


    public void sendServerBroadcast(MessageType messageType, String message) {
        for (SocketChannel client : clients) {
            ByteBuffer tempBuffer = ByteBuffer.wrap((messageType.name() + ":" + message + "\n").getBytes());
            try {
                client.write(tempBuffer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void sendServerMessage(SocketChannel client, MessageType messageType, String message) {
        ByteBuffer tempBuffer = ByteBuffer.wrap((messageType.name() + ":" + message + "\n").getBytes());
        try {
            client.write(tempBuffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void setMetaData(SocketChannel socketChannel, String message) {
        String[] parts = message.split(":", 2);
        usernames.add(parts[1]);
    }


    private String socketListToString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < clients.size(); i++) {
            try {
                InetSocketAddress inetAddress = (InetSocketAddress) clients.get(i).getRemoteAddress();
                builder.append(usernames.get(i)).append(":").append(inetAddress.getAddress().getHostAddress()).append(";");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return builder.toString();
    }


    private int vergleichStich(List<String> karten) {
        String[] reihenfolge = {"SIEBEN", "ACHT", "NEUN", "DAME", "KOENIG", "ZEHN", "BUBE", "ASS"};
        Map<String, Integer> wertung = new HashMap<>();
        for (int i = 0; i < reihenfolge.length; i++) {
            wertung.put(reihenfolge[i], i);
        }

        String angespielteFarbe = karten.get(0).split(" ")[0];
        int gewinnerIndex = 0;
        String besteKarte = karten.get(0);

        for (int i = 1; i < karten.size(); i++) {
            String[] karte = karten.get(i).split(" ");
            String farbe = karte[0];
            String wert = karte[1];

            String[] beste = besteKarte.split(" ");
            String besteFarbe = beste[0];
            String besteWert = beste[1];

            if (trumpf.name().equals("NULL")) {
                // Im Nullspiel gewinnt die niedrigste Karte in der angespielten Farbe
                if (farbe.equals(angespielteFarbe) && wertung.get(wert) < wertung.get(besteWert)) {
                    gewinnerIndex = i;
                    besteKarte = karten.get(i);
                }
            } else {
                // Normales Spiel mit Trumpf
                boolean aktuelleIstTrumpf = farbe.equals(trumpf.name());
                boolean besteIstTrumpf = besteFarbe.equals(trumpf.name());

                if ((aktuelleIstTrumpf && !besteIstTrumpf) ||
                        (aktuelleIstTrumpf == besteIstTrumpf && farbe.equals(besteFarbe) && wertung.get(wert) > wertung.get(besteWert))) {
                    gewinnerIndex = i;
                    besteKarte = karten.get(i);
                }
            }
        }
        return gewinnerIndex;
    }


    private int augenZaehlen(int augenWert) {
        Kartenwert kartenwert = new Kartenwert(trumpf);
        for (String karte : stich) {
            augenWert += kartenwert.getPunkte(Kartenart.valueOf(karte.split(" ")[1]));
        }
        stich.clear();
        return augenWert;
    }
}

