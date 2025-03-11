package org.example.client_server_system;

import org.example.database.DBHelper;
import org.example.logic.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Server implements Runnable {

    //Attribute
    Selector selector;
    ServerSocketChannel serverChannel;
    DBHelper helper = new DBHelper();
    private String timestamp;
    private boolean createTable = false;
    private int port;
    private List<SocketChannel> clients = new ArrayList<>();
    private List<String> usernames = new ArrayList<>();
    private List<String> stich = new ArrayList<>();
    String skat;
    private int augenSolo;          // Augen des Solospielers
    private int augenDuo;           // Augen des Teams
    private Reizen reizen;
    private final int MAX_PLAYERS = 3;
    private int playedCards;        // gespielte Karten (-anzahl)
    private int soloPlayer;         //
    private int stichWin;           // ausspielender
    private int startPlayer = 2;    // Kartengeber
    private int reizPlayer;         // reizende (sagen)
    private int answPlayer;         // reiz antwortende (hören)
    private Farbe trumpf;           // das angesagte Spiel
    private String buben;           // Buben des Alleinspielers für die Punkteauswertung
    private boolean handspiel;      // gibt an, ob Handspiel angesagt wurde



    // TODO 2. GESAMTEN CODE KOMMENTIEREN


    // TODO EXTRA: NULL HAND PUNKT AUSZÄHLEN NICHT IMPLEMENTIERT

    // TODO EXTRA (wichtig?): DATENBANK IN LISTE AUSLESEN, UM NACH DEM SPIELEN EINE
    //  SPIELAUSWERTUNG ZU HABEN

    // TODO EXTRA: SPIEL EINPASSEN

    // TODO EXTRA: SPIELER DISCONNECT


    @Override
    public void run() {
        initServer();
        startServerLoop();
    }


    // Konstruktor
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
                if (!createTable) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                    timestamp = LocalDateTime.now().format(formatter);
                    helper.createTable(timestamp, usernames.get(0), usernames.get(1), usernames.get(2));
                }
                createTable = true;
                startNewGame();
                break;
            case REIZEN:
                reizen(content);
                break;
            case REIZ_ANTWORT:
                reizenAntwort(content);
                break;
            case CARD_PLAYED:
                stich.add(content);
                stichWin = (this.stichWin + 1) % 3;
                if (stich.size() > 2) {
                    stichWin = vergleichStich(stich);
                    if (stichWin == soloPlayer) {
                        augenSolo = augenZaehlen(augenSolo);
                    } else {
                        if (trumpf == Farbe.NULL) {
                            setPoints(false);
                        }
                        augenDuo = augenZaehlen(augenDuo);
                    }
                }
                System.out.println("Augensolo: " + augenSolo);
                System.out.println("Augenduo: " + augenDuo);
                if (playedCards < 31) {
                    playedCards++;
                    sendServerBroadcast(MessageType.CARD_PLAYED, content + ":" + stichWin);
                    System.out.println("Server-content: " + content);
                }
                if (playedCards == 31) {
                    setPoints(true);
                }
                break;
            case TRUMPF:
                this.trumpf = Farbe.valueOf(content);
                sendServerBroadcast(MessageType.TRUMPF, content);
                sendServerBroadcast(MessageType.CARD_PLAYED, ":" + (startPlayer + playedCards) % 3);
                break;
            case BUBEN:
                buben = content;
                break;
            case HANDSPIEL:
                handspiel = true;
                skat = content;
                break;
            case SKAT_SENDEN:
                skat = content;
                break;
            default:
                System.out.println("Unknown message type.");
        }
    }


    private void startNewGame() {
        reizen = new Reizen();
        augenSolo = 0;
        augenDuo = 0;
        handspiel = false;
        playedCards = 1;
        spreadCards();
        startPlayer = (startPlayer + 1) % 3;
        reizPlayer = (startPlayer + 2) % 3;
        answPlayer = (startPlayer + 1) % 3;
        stichWin = (startPlayer + 1) % 3;
        sendServerMessage(clients.get(reizPlayer), MessageType.REIZEN, "" + reizen.appendReizwert());
    }


    private void reizenAntwort(String content) {
        if (content.equals("true")) {
            sendServerMessage(clients.get(reizPlayer), MessageType.REIZEN, "" + reizen.appendReizwert());
        } else {
            if (reizPlayer == startPlayer) {
                soloPlayer = reizPlayer;
                sendServerMessage(clients.get(reizPlayer), MessageType.START_SPIELAUSWAHL, "");
            } else {
                answPlayer = reizPlayer;
                reizPlayer = startPlayer;
                sendServerMessage(clients.get(reizPlayer), MessageType.REIZEN, "" + reizen.appendReizwert());
            }
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
            builder.append(":");    // : zur Trennung von Skat und den Spielkarten
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


    // Entscheidet, wer den Stich bekommt
    private int vergleichStich(List<String> karten) {
        String gewinnerKarte = karten.get(0);
        int index = stichWin;
        int gewinnerIndex = index;
        String bedient = karten.get(0).split(" ")[0];
        if (istTrumpf(karten.get(0))) bedient = "TRUMPF";
        Kartenwert kartenwert = new Kartenwert(trumpf);

        int cardPoints2 = cardToPoints(karten.get(1), bedient);
        int cardPoints3 = cardToPoints(karten.get(2), bedient);
        
        if (cardToPoints(gewinnerKarte, bedient) < cardPoints2 || (cardToPoints(gewinnerKarte, bedient) == cardPoints2 && kartenwert.getFarbwert(gewinnerKarte) < kartenwert.getFarbwert(karten.get(1)))){
            gewinnerKarte = karten.get(1);
            gewinnerIndex = (index + 1) % 3;
        }

        if (cardToPoints(gewinnerKarte, bedient) < cardPoints3 || (cardToPoints(gewinnerKarte, bedient) == cardPoints3 && kartenwert.getFarbwert(gewinnerKarte) < kartenwert.getFarbwert(karten.get(2)))){
            gewinnerKarte = karten.get(2);
            gewinnerIndex = (index + 2) % 3;
        }

        return gewinnerIndex;
    }


    private int cardToPoints(String karte, String bedient) {
        Kartenwert kartenwert = new Kartenwert(trumpf);
        int points = 0;
        if (istTrumpf(karte)) points += 20;
        else if (!bedient.equals("TRUMPF")){
            if (karte.split(" ")[0].equals(bedient)) points += 10;
        }
        points += kartenwert.kartenWertigkeit.get(karte.split(" ")[1]);
        return points;
    }
    

    private boolean istTrumpf(String karte) {
        if (trumpf.name().equals("NULL")) return false;
        if (karte.split(" ")[1].equals("BUBE")) return true;
        return Farbe.valueOf(karte.split(" ")[0]) == trumpf;
    }


    // Berechnet den Spielwert des Solo-Spielers
    private int spielWert() {
        int count = 1;
        List<String> buben = new ArrayList<>(Arrays.asList(this.buben.split(",")));
        if (augenSolo >= 90 || augenSolo <= 30) count++;             // Spielstufe +1 bei Schneider
        if (augenSolo == 120 || augenSolo == 0) count++;           // Spielstufe +1 bei Schwarz

        if (handspiel) count++;                 // Spielstufe +1 bei Handspiel
        if (buben.isEmpty()) count += 4;
        else {
            if (buben.get(0).isEmpty()) {
                for (String karte : buben) {        // Berechnung Spielstufe mit n-Buben spiel n+1
                    if (!karte.isEmpty()) count++;
                    else break;
                }
            } else {
                for (String karte : buben) {           // Berechnung Spielstufe ohne n-Buben spiel n+1
                    if (karte.isEmpty()) count++;
                    else break;
                }
            }
        }


        // Spielstufe mit Grundwert multiplizieren
        switch (trumpf) {
            case KREUZ -> count *= 12;
            case PIK -> count += 11;
            case HERZ -> count *= 10;
            case KARO -> count *= 9;
            case GRAND -> count += 24;
            case NULL -> count = handspiel ? 35 : 23;
        }
        return count;
    }


    // zählt die Augen
    private int augenZaehlen(int augenWert) {
        Kartenwert kartenwert = new Kartenwert(trumpf);
        if (handspiel && playedCards == 31) {
            String skat1 = skat.split(",")[0].split(" ")[1];
            String skat2 = skat.split(",")[1].split(" ")[1];
            augenSolo += kartenwert.getPunkte(Kartenart.valueOf(skat1)) + kartenwert.getPunkte(Kartenart.valueOf(skat2));
        } else if (!handspiel && playedCards == 3) {
            String skat1 = skat.split(",")[0].split(" ")[1];
            String skat2 = skat.split(",")[1].split(" ")[1];
            augenSolo += kartenwert.getPunkte(Kartenart.valueOf(skat1)) + kartenwert.getPunkte(Kartenart.valueOf(skat2));
        }
        for (String karte : stich) {
            augenWert += kartenwert.getPunkte(Kartenart.valueOf(karte.split(" ")[1]));
        }
        stich.clear();
        return augenWert;
    }


    // Entscheidet, wer gewonnen hat und verteilt die Punkte in der DB
    private void setPoints(boolean nullSieg) {
        int spielwert = spielWert();
        boolean win;
        if (trumpf != Farbe.NULL) {
            if (augenSolo > augenDuo) {
                win = true;
                switch (soloPlayer) {
                    case 0:
                        helper.setRoundResults(timestamp, spielwert, 0, 0);
                        break;
                    case 1:
                        helper.setRoundResults(timestamp, 0, spielwert, 0);
                        break;
                    case 2:
                        helper.setRoundResults(timestamp, 0, 0, spielwert);
                        break;
                }
            } else {
                win = false;
                switch (soloPlayer) {
                    case 0:
                        helper.setRoundResults(timestamp, spielwert * -2, 0, 0);
                        break;
                    case 1:
                        helper.setRoundResults(timestamp, 0, spielwert * -2, 0);
                        break;
                    case 2:
                        helper.setRoundResults(timestamp, 0, 0, spielwert * -2);
                        break;
                }
            }
        } else {
            if (nullSieg) {
                win = true;
                switch (soloPlayer) {
                    case 0:
                        helper.setRoundResults(timestamp, spielwert, 0, 0);
                        break;
                    case 1:
                        helper.setRoundResults(timestamp, 0, spielwert, 0);
                        break;
                    case 2:
                        helper.setRoundResults(timestamp, 0, 0, spielwert);
                        break;
                }
            } else {
                win = false;
                switch (soloPlayer) {
                    case 0:
                        helper.setRoundResults(timestamp, spielwert * -2, 0, 0);
                        break;
                    case 1:
                        helper.setRoundResults(timestamp, 0, spielwert * -2, 0);
                        break;
                    case 2:
                        helper.setRoundResults(timestamp, 0, 0, spielwert * -2);
                        break;
                }
            }
        }
        StringBuilder message = new StringBuilder();
        if (win) message.append("WIN");
        else message.append("LOSE");
        message.append(";").append(soloPlayer).append(";").append(augenSolo).append(";").append(spielwert);
        startNewGame();
        sendServerBroadcast(MessageType.END_GAME, String.valueOf(message));
    }
}