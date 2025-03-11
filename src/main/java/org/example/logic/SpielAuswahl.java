package org.example.logic;

import org.example.clientServerSystem.Client;
import org.example.clientServerSystem.MessageType;
import org.example.game.GameWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class SpielAuswahl extends JFrame {

    private ArrayList<JButton> coloredButtons = new ArrayList<>();
    private JButton aufnehmenBtn;
    private JButton handspielBtn;
    private JButton ansagenBtn;
    private Mischen mischen = new Mischen();
    private GameWindow gameWindow;
    private List<Karte> deck;
    private Farbe spielmodus = Farbe.KREUZ;
    private Client client;

    public SpielAuswahl(GameWindow gameWindow, Client client) {
        super();
        this.client = client;
        this.gameWindow = gameWindow;
        deck = gameWindow.getDeck();
        this.client.sendPlayerActions(MessageType.BUBEN, getBuben());
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        int frameWidth = 400;
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

        int count = 0;
        for (Farbe farbe : Farbe.values()) {
            addColoredButton(farbe, cp, count);
            count++;
        }

        aufnehmenBtn = getButton("Skat aufnehmen", this::jButton7_ActionPerformed, cp, 16);
        handspielBtn = getButton("Handspiel ansagen", this::jButton8_ActionPerformed, cp, 220);
        ansagenBtn = getButton("Spiel ansagen", this::jButton9_ActionPerformed, cp, 180);
        ansagenBtn.setVisible(false);
        ansagenBtn.setEnabled(false);

        setVisible(true);
        this.setAlwaysOnTop(true);
    }

    public JButton getButton(String text, ActionListener event, Container cp, int x) {
        JButton jButton = new JButton();
        jButton.setBounds(x, 136, 153, 41);
        jButton.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton.setText(text);
        jButton.setMargin(new Insets(2, 2, 2, 2));
        jButton.addActionListener(event);
        cp.add(jButton);
        return jButton;
    }

    public void addColoredButton(Farbe farbe, Container cp, int count) {
        JButton jButton = new JButton();
        jButton.setBounds(70+(count%3)*80, 16+(50*(count/3)), 73, 33);
        jButton.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton.setText(farbe.name());
        jButton.setMargin(new Insets(2, 2, 2, 2));
        jButton.addActionListener(evt -> buttonActionPerformed(evt, farbe));
        cp.add(jButton);
        coloredButtons.add(jButton);
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
        aufnehmenBtn.setVisible(false);
        aufnehmenBtn.setEnabled(false);
        handspielBtn.setVisible(false);
        handspielBtn.setEnabled(false);
        ansagenBtn.setVisible(true);
        ansagenBtn.setEnabled(true);
        gameWindow.enableSkatButton();

        // Skat wird zum Kartendeck hinzugefügt
        List<Karte> deck = gameWindow.getDeck();
        deck.addAll(gameWindow.getSkat());
        gameWindow.setDeck(mischen.kartenSortieren(deck, spielmodus));
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
        for (JButton button : coloredButtons) {
            button.setBackground(null);
        }
    }
}