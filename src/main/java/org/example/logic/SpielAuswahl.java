package org.example.logic;

import org.example.client_server_system.Client;
import org.example.client_server_system.MessageType;
import org.example.game.GameWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class SpielAuswahl extends JFrame {

    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();
    private JButton jButton3 = new JButton();
    private JButton jButton4 = new JButton();
    private JButton jButton5 = new JButton();
    private JButton jButton6 = new JButton();
    private JButton jButton7 = new JButton();
    private JButton jButton8 = new JButton();
    private JButton jButton9 = new JButton();
    private Mischen mischen = new Mischen();
    private GameWindow gameWindow;
    private List<Karte> deck;
    private Farbe spielmodus;
    private Client client;
    public boolean skatDruecken = false;


    public SpielAuswahl(GameWindow gameWindow, Client client) {
        super();
        this.client = client;
        this.gameWindow = gameWindow;
        deck = gameWindow.getDeck();
        this.client.sendPlayerActions(MessageType.BUBEN, getBuben());
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        int frameWidth = 520;
        int frameHeight = 242;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("spielAuswahl");
        setResizable(false);
        Container cp = getContentPane();
        cp.setLayout(null);

        //region set Button propertiers
        jButton1.setBounds(16, 16, 73, 33);
        jButton1.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton1.setText("Kreuz");
        jButton1.setMargin(new Insets(2, 2, 2, 2));
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonActionPerformed(evt, Farbe.KREUZ);
            }
        });
        cp.add(jButton1);
        jButton2.setBounds(96, 16, 73, 33);
        jButton2.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton2.setText("Pik");
        jButton2.setMargin(new Insets(2, 2, 2, 2));
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonActionPerformed(evt, Farbe.PIK);
            }
        });
        cp.add(jButton2);
        jButton3.setBounds(176, 16, 73, 33);
        jButton3.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton3.setText("Herz");
        jButton3.setMargin(new Insets(2, 2, 2, 2));
        jButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonActionPerformed(evt, Farbe.HERZ);
            }
        });
        cp.add(jButton3);
        jButton4.setBounds(256, 16, 73, 33);
        jButton4.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton4.setText("Karo");
        jButton4.setMargin(new Insets(2, 2, 2, 2));
        jButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonActionPerformed(evt, Farbe.KARO);
            }
        });
        cp.add(jButton4);
        jButton5.setBounds(336, 16, 73, 33);
        jButton5.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton5.setText("Grand");
        jButton5.setMargin(new Insets(2, 2, 2, 2));
        jButton5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonActionPerformed(evt, Farbe.GRAND);
            }
        });
        cp.add(jButton5);
        jButton6.setBounds(416, 16, 73, 33);
        jButton6.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton6.setText("Null");
        jButton6.setMargin(new Insets(2, 2, 2, 2));
        jButton6.addActionListener(evt -> buttonActionPerformed(evt, Farbe.NULL));
        cp.add(jButton6);
        jButton7.setBounds(16, 136, 152, 41);
        jButton7.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton7.setText("Skat aufnehmen");
        jButton7.setMargin(new Insets(2, 2, 2, 2));
        jButton7.addActionListener(this::jButton7_ActionPerformed);
        cp.add(jButton7);
        jButton8.setBounds(336, 136, 153, 41);
        jButton8.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton8.setText("Handspiel ansagen");
        jButton8.setMargin(new Insets(2, 2, 2, 2));
        jButton8.addActionListener(this::jButton8_ActionPerformed);
        cp.add(jButton8);
        jButton9.setBounds(336, 136, 153, 41);
        jButton9.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton9.setText("Spiel ansagen");
        jButton9.setMargin(new Insets(2, 2, 2, 2));
        jButton9.addActionListener(this::jButton9_ActionPerformed);
        cp.add(jButton9);
        jButton9.setVisible(false);
        jButton9.setEnabled(false);
        //endregion

        setVisible(true);
        this.setAlwaysOnTop(true);
    }


    // kontrolliert die Buttons
    public void buttonActionPerformed(ActionEvent evt, Farbe trumpf) {
        JButton button = (JButton) evt.getSource();
        backGroundNull();
        button.setBackground(Color.red);
        spielmodus = trumpf;
        gameWindow.setDeck(mischen.kartenSortieren(gameWindow.getDeck(), trumpf));
        gameWindow.updateButtonText();
    }


    // Button Skat aufnehmen
    public void jButton7_ActionPerformed(ActionEvent evt) {
        jButton7.setVisible(false);
        jButton7.setEnabled(false);
        jButton8.setVisible(false);
        jButton8.setEnabled(false);
        jButton9.setVisible(true);
        jButton9.setEnabled(true);
        gameWindow.enableSkatButton();

        // Skat wird zum Kartendeck hinzugefügt
        List<Karte> deck = gameWindow.getDeck();
        deck.addAll(gameWindow.getSkat());
        gameWindow.setDeck(deck);
        //GUI.dec1.addAll(GUI.decskat);

        spielmodus = Farbe.KREUZ;
        switch (spielmodus) {
            case KREUZ:
            case GRAND:
                gameWindow.setDeck(mischen.kartenSortieren(gameWindow.getDeck(), Farbe.KREUZ));
                break;
            case PIK:
                gameWindow.setDeck(mischen.kartenSortieren(gameWindow.getDeck(), Farbe.PIK));
                break;
            case HERZ:
                gameWindow.setDeck(mischen.kartenSortieren(gameWindow.getDeck(), Farbe.HERZ));
                break;
            case KARO:
                gameWindow.setDeck(mischen.kartenSortieren(gameWindow.getDeck(), Farbe.KARO));
                break;
            case NULL:
                gameWindow.setDeck(mischen.kartenSortieren(gameWindow.getDeck(), Farbe.NULL));
                break;
        }
        client.sendPlayerActions(MessageType.BUBEN, getBuben());

        gameWindow.updateButtonText();
        gameWindow.skatDruecken = true;
        gameWindow.skat = new ArrayList<>();
    }


    // Button Handspiel ansagen
    public void jButton8_ActionPerformed(ActionEvent evt) {
        client.sendPlayerActions(MessageType.HANDSPIEL, String.join(",", gameWindow.getSkat().toString()));
        jButton9_ActionPerformed(evt);
    }


    // Button Spiel ansagen
    public void jButton9_ActionPerformed(ActionEvent evt) {
        if (spielmodus == null) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie ein Spiel aus!");
            return;
        }
        if (gameWindow.skatDruecken) {
            if (gameWindow.skatCount != 2) {
                JOptionPane.showMessageDialog(this, "Bitte legen Sie den Skat ab!");
                return;
            }
        }
        client.sendPlayerActions(MessageType.TRUMPF, spielmodus.name());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        gameWindow.setSpielstart(true);
    }


    // holt sich die Buben des Solo-Spielers
    public String getBuben() {
        String buben = "";
        buben += checkForBube("KREUZ") ? "KREUZ BUBE," : ",";
        buben += checkForBube("PIK") ? "PIK BUBE," : ",";
        buben += checkForBube("HERZ") ? "HERZ BUBE," : ",";
        buben += checkForBube("KARO") ? "KARO BUBE" : "";

        return buben;
    }


    private boolean checkForBube(String farbe) {
        boolean result = false;
        for (int i = 0; i < 4; i++) {
            if (deck.get(i).toString().equals(farbe + " BUBE")){
                result = true;
            }
        }
        return result;
    }


    // sets the color of every other Button (which is not selected) to white
    public void backGroundNull() {
        jButton1.setBackground(null);
        jButton2.setBackground(null);
        jButton3.setBackground(null);
        jButton4.setBackground(null);
        jButton5.setBackground(null);
        jButton6.setBackground(null);
    }
}