package org.example.game;

import org.example.client_server_system.Client;
import org.example.client_server_system.MessageType;
import org.example.game_selection.panels.WaitingLobby;
import org.example.logic.Farbe;
import org.example.logic.Karte;
import org.example.logic.Kartenart;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class GameWindow extends JFrame {

    private final JButton jButton1 = new JButton();   // Deck Karte 1
    private final JButton jButton2 = new JButton();   // Deck Karte 2
    private final JButton jButton3 = new JButton();   // Deck Karte 3
    private final JButton jButton4 = new JButton();   // Deck Karte 4
    private final JButton jButton5 = new JButton();   // Deck Karte 5
    private final JButton jButton6 = new JButton();   // Deck Karte 6
    private final JButton jButton7 = new JButton();   // Deck Karte 7
    private final JButton jButton8 = new JButton();   // Deck Karte 8
    private final JButton jButton9 = new JButton();   // Deck Karte 9
    private final JButton jButton10 = new JButton();  // Deck Karte 10
    private final JButton jButton11 = new JButton();  // Skat Karte 1
    private final JButton jButton12 = new JButton();  // Skat Karte 2
    private final JButton jButton14 = new JButton();  // reizen ja
    private final JButton jButton15 = new JButton();  // reizen nein
    private JLabel jLabelCard1 = new JLabel();
    private JLabel jLabelCard2 = new JLabel();
    private JLabel jLabelCard3 = new JLabel();
    private Canvas canvas4 = new Canvas();
    private Canvas canvas5 = new Canvas();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel[] cardPlaces = new JLabel[3];
    private List<Karte> deck;
    public List<Karte> skat;
    private Client client;
    private String[][] players;
    private boolean spielstart = false;
    private boolean reizAntwort = false;
    public boolean skatDruecken;
    public int skatCount = 0;
    CardImage cardImage = new CardImage();



    public GameWindow(List<Karte> deck, List<Karte> skat, Client client) {
        super();
        this.client = client;
        this.deck = deck;
        this.skat = skat;
        players = WaitingLobby.getInstance().players;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int frameWidth = 1286;
        int frameHeight = 592;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("SKAT - " + client.getUsername());
        setResizable(false);
        Container cp = getContentPane();
        cp.setLayout(null);


        //region jLabel
        String myName = client.getUsername();
        String username1;
        String username2;
        int userindex;
        if (players[0][0].equals(myName)) {
            username1 = players[1][0];
            username2 = players[2][0];
            userindex = 0;
        } else if (players[1][0].equals(myName)) {
            username2 = players[0][0];
            username1 = players[2][0];
            userindex = 1;
        } else {
            username1 = players[0][0];
            username2 = players[1][0];
            userindex = 2;
        }

        // Label unten mitte
        jLabelCard3.setBounds(568, 256, 136, 137);
        cp.add(jLabelCard3);
        cardPlaces[(userindex) % 3] = jLabelCard3;

        // Label oben links
        jLabelCard1.setBounds(384, 64, 136, 137);
        cp.add(jLabelCard1);
        cardPlaces[(1 + userindex) % 3] = jLabelCard1;

        // Label oben rechts
        jLabelCard2.setBounds(744, 64, 136, 137);
        cp.add(jLabelCard2);
        cardPlaces[(2 + userindex) % 3] = jLabelCard2;

        jLabel1.setBounds(400, 16, 80, 24);
        jLabel1.setFont(new Font("Dialog", Font.PLAIN, 11));
        jLabel1.setText(username1);
        cp.add(jLabel1);
        jLabel2.setBounds(768, 16, 80, 24);
        jLabel2.setFont(new Font("Dialog", Font.PLAIN, 11));
        jLabel2.setText(username2);
        cp.add(jLabel2);
        //endregion

        //region Karten
        jButton1.setForeground(new Color(0, 0, 0, 0));
        jButton1.setBounds(12, 401, 93, 141);
        jButton1.setFont(new Font("Dialog", Font.PLAIN, -10));
        jButton1.setText(deck.get(0).toString());
        jButton1.setMargin(new Insets(2, 2, 2, 2));
        jButton1.addActionListener(this::buttonActionPerformed);
        cp.add(jButton1);
        jButton2.setForeground(new Color(0, 0, 0, 0));
        jButton2.setBounds(111, 401, 93, 141);
        jButton2.setFont(new Font("Dialog", Font.PLAIN, -10));
        jButton2.setText(deck.get(1).toString());
        jButton2.setMargin(new Insets(2, 2, 2, 2));
        jButton2.addActionListener(this::buttonActionPerformed);
        cp.add(jButton2);
        jButton3.setForeground(new Color(0, 0, 0, 0));
        jButton3.setBounds(210, 401, 93, 141);
        jButton3.setFont(new Font("Dialog", Font.PLAIN, -10));
        jButton3.setText(deck.get(2).toString());
        jButton3.setMargin(new Insets(2, 2, 2, 2));
        jButton3.addActionListener(this::buttonActionPerformed);
        cp.add(jButton3);
        jButton4.setForeground(new Color(0, 0, 0, 0));
        jButton4.setBounds(309, 401, 93, 141);
        jButton4.setFont(new Font("Dialog", Font.PLAIN, -10));
        jButton4.setText(deck.get(3).toString());
        jButton4.setMargin(new Insets(2, 2, 2, 2));
        jButton4.addActionListener(this::buttonActionPerformed);
        cp.add(jButton4);
        jButton5.setForeground(new Color(0, 0, 0, 0));
        jButton5.setBounds(408, 401, 93, 141);
        jButton5.setFont(new Font("Dialog", Font.PLAIN, -10));
        jButton5.setText(deck.get(4).toString());
        jButton5.setMargin(new Insets(2, 2, 2, 2));
        jButton5.addActionListener(this::buttonActionPerformed);
        cp.add(jButton5);
        jButton6.setForeground(new Color(0, 0, 0, 0));
        jButton6.setBounds(507, 401, 93, 141);
        jButton6.setFont(new Font("Dialog", Font.PLAIN, -10));
        jButton6.setText(deck.get(5).toString());
        jButton6.setMargin(new Insets(2, 2, 2, 2));
        jButton6.addActionListener(this::buttonActionPerformed);
        cp.add(jButton6);
        jButton7.setForeground(new Color(0, 0, 0, 0));
        jButton7.setBounds(606, 401, 93, 141);
        jButton7.setFont(new Font("Dialog", Font.PLAIN, -10));
        jButton7.setText(deck.get(6).toString());
        jButton7.setMargin(new Insets(2, 2, 2, 2));
        jButton7.addActionListener(this::buttonActionPerformed);
        cp.add(jButton7);
        jButton8.setForeground(new Color(0, 0, 0, 0));
        jButton8.setBounds(705, 401, 93, 141);
        jButton8.setFont(new Font("Dialog", Font.PLAIN, -10));
        jButton8.setText(deck.get(7).toString());
        jButton8.setMargin(new Insets(2, 2, 2, 2));
        jButton8.addActionListener(this::buttonActionPerformed);
        cp.add(jButton8);
        jButton9.setForeground(new Color(0, 0, 0, 0));
        jButton9.setBounds(804, 401, 93, 141);
        jButton9.setFont(new Font("Dialog", Font.PLAIN, -10));
        jButton9.setText(deck.get(8).toString());
        jButton9.setMargin(new Insets(2, 2, 2, 2));
        jButton9.addActionListener(this::buttonActionPerformed);
        cp.add(jButton9);
        jButton10.setForeground(new Color(0, 0, 0, 0));
        jButton10.setBounds(903, 401, 93, 141);
        jButton10.setFont(new Font("Dialog", Font.PLAIN, -10));
        jButton10.setText(deck.get(9).toString());
        jButton10.setMargin(new Insets(2, 2, 2, 2));
        jButton10.addActionListener(this::buttonActionPerformed);
        cp.add(jButton10);
        //endregion

        //region Buttons Skat
        jButton11.setForeground(new Color(0, 0, 0, 0));
        jButton11.setBounds(1002, 401, 93, 141);
        jButton11.setFont(new Font("Dialog", Font.PLAIN, -10));
        jButton11.setText(skat.get(0).toString());
        jButton11.setMargin(new Insets(2, 2, 2, 2));
        jButton11.addActionListener(this::buttonActionPerformed);
        jButton11.setVisible(false);
        jButton11.setEnabled(false);
        cp.add(jButton11);

        jButton12.setForeground(new Color(0, 0, 0, 0));
        jButton12.setBounds(1101, 401, 93, 141);
        jButton12.setFont(new Font("Dialog", Font.PLAIN, -10));
        jButton12.setText(skat.get(1).toString());
        jButton12.setMargin(new Insets(2, 2, 2, 2));
        jButton12.addActionListener(this::buttonActionPerformed);
        jButton12.setEnabled(false);
        jButton12.setVisible(false);
        cp.add(jButton12);
        //endregion

        //region Reizen
        jButton14.setBounds(400, 150, 105, 73);
        jButton14.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton14.setText("Reizen");
        jButton14.setMargin(new Insets(2, 2, 2, 2));
        jButton14.addActionListener(this::reizen_ActionPerformed);
        cp.add(jButton14);
        jButton14.setVisible(false);

        jButton15.setBounds(400, 260, 105, 73);
        jButton15.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton15.setText("Nein");
        jButton15.setMargin(new Insets(2, 2, 2, 2));
        jButton15.addActionListener(this::reizen_ActionPerformed);
        cp.add(jButton15);
        jButton15.setVisible(false);
        //endregion

        //region Spielernamen
        canvas4.setBounds(1080, 16, 24, 73);
        cp.add(canvas4);
        canvas5.setBounds(1080, 100, 24, 73);
        cp.add(canvas5);
        //endregion

        updateButtonText();
        setVisible(true);
    }


    // Buttonname → wird zu jeweiliger Karte umbenannt
    public void updateButtonText() {
        // sets the name of the card (button) for further usage -> communication with server
        jButton1.setText(deck.get(0).toString());
        jButton2.setText(deck.get(1).toString());
        jButton3.setText(deck.get(2).toString());
        jButton4.setText(deck.get(3).toString());
        jButton5.setText(deck.get(4).toString());
        jButton6.setText(deck.get(5).toString());
        jButton7.setText(deck.get(6).toString());
        jButton8.setText(deck.get(7).toString());
        jButton9.setText(deck.get(8).toString());
        jButton10.setText(deck.get(9).toString());
        if (jButton11.isVisible()) {
            jButton11.setText(deck.get(10).toString());
            jButton12.setText(deck.get(11).toString());
        }

        // sets the image of the card (button)
        jButton1.setIcon(cardImage.loadImageFromFile(cardImage.bilder.get(deck.get(0).toString()) , 80, 120));
        jButton2.setIcon(cardImage.loadImageFromFile(cardImage.bilder.get(deck.get(1).toString()) , 80, 120));
        jButton3.setIcon(cardImage.loadImageFromFile(cardImage.bilder.get(deck.get(2).toString()) , 80, 120));
        jButton4.setIcon(cardImage.loadImageFromFile(cardImage.bilder.get(deck.get(3).toString()) , 80, 120));
        jButton5.setIcon(cardImage.loadImageFromFile(cardImage.bilder.get(deck.get(4).toString()) , 80, 120));
        jButton6.setIcon(cardImage.loadImageFromFile(cardImage.bilder.get(deck.get(5).toString()) , 80, 120));
        jButton7.setIcon(cardImage.loadImageFromFile(cardImage.bilder.get(deck.get(6).toString()) , 80, 120));
        jButton8.setIcon(cardImage.loadImageFromFile(cardImage.bilder.get(deck.get(7).toString()) , 80, 120));
        jButton9.setIcon(cardImage.loadImageFromFile(cardImage.bilder.get(deck.get(8).toString()) , 80, 120));
        jButton10.setIcon(cardImage.loadImageFromFile(cardImage.bilder.get(deck.get(9).toString()) , 80, 120));
        if (jButton11.isVisible()) {
            jButton11.setIcon(cardImage.loadImageFromFile(cardImage.bilder.get(deck.get(10).toString()) , 80, 120));
            jButton12.setIcon(cardImage.loadImageFromFile(cardImage.bilder.get(deck.get(11).toString()) , 80, 120));
        }
    }


    public void buttonActionPerformed(ActionEvent evt) {
        // Buttonlogik zum Wegdrücken des Skats
        if(skatDruecken && skatCount < 2){
            JButton button = (JButton) evt.getSource();
            button.setVisible(false);
            button.setEnabled(false);
            skat.add(new Karte(Farbe.valueOf(button.getText().split(" ")[0]), Kartenart.valueOf(button.getText().split(" ")[1])));
            skatCount++;
            if(skatCount == 2) client.sendPlayerActions(MessageType.SKAT_SENDEN, skat.get(0).toString() + "," + skat.get(1).toString());
        }

        // allgemeine Buttonlogik (Sendet Nachricht, dass eine Karte gespielt wurde an Server)
        if (!spielstart) return;
        int currentTurn = client.getPlayerTurn();
        if (players[currentTurn][0].equals(client.getUsername())) {
            JButton button = (JButton) evt.getSource();
            button.setVisible(false);
            button.setEnabled(false);
            client.sendPlayerActions(MessageType.CARD_PLAYED, button.getText());
        } else {
            JOptionPane.showMessageDialog(this, "Spieler " + players[currentTurn][0] + " ist am Zug!\nBitte warten");
        }
    }


    // updatet die gelegten Karten
    public void cardPlayed(String content) {
        if (content.isEmpty()) return;
        // löscht alle Karten, wenn ein Stich abgeschlossen ist und ein neuer anfängt
        if (cardPlaces[2].getIcon() != null && cardPlaces[1].getIcon() != null && cardPlaces[0].getIcon() != null){
            for (JLabel label: cardPlaces) {
                label.setText("");
                // TODO delete all icons once every three cards
                label.setIcon(null);
            }
            this.revalidate();
            this.repaint();
        }

        cardPlaces[client.getPlayerTurn()].setIcon(cardImage.loadImageFromFile(cardImage.bilder.get((content)), 80, 120));
        //cardPlaces[client.getPlayerTurn()].setText(content);
    }


    public void enableReizen(String reizWert, boolean reizAntwort) {
        this.reizAntwort = reizAntwort;
        jButton14.setVisible(true);
        jButton14.setText(reizWert);
        jButton15.setVisible(true);
    }


    public void enableButtons(){
        int currentTurn = client.getPlayerTurn();
        if (players[currentTurn][0].equals(client.getUsername())){
            setButtonEnable(true);
        } else {
            setButtonEnable(false);
        }
    }


    private void setButtonEnable(boolean bool) {
        jButton1.setEnabled(bool);
        jButton2.setEnabled(bool);
        jButton3.setEnabled(bool);
        jButton4.setEnabled(bool);
        jButton5.setEnabled(bool);
        jButton6.setEnabled(bool);
        jButton7.setEnabled(bool);
        jButton8.setEnabled(bool);
        jButton9.setEnabled(bool);
        jButton10.setEnabled(bool);
        jButton11.setEnabled(bool);
        jButton12.setEnabled(bool);
    }


    public void enableSkatButton() {
        jButton11.setEnabled(true);
        jButton11.setVisible(true);
        jButton12.setEnabled(true);
        jButton12.setVisible(true);
    }

    // sendet Reizinfos an den Server
    private void reizen_ActionPerformed(ActionEvent actionEvent) {
        if (reizAntwort){
            handleReizantwort(actionEvent, MessageType.REIZ_ANTWORT);
        } else {
            handleReizantwort(actionEvent, MessageType.REIZEN);
        }
    }


    // sendet Reizinfos an den Server
    private void handleReizantwort(ActionEvent actionEvent, MessageType messageType){
        if (actionEvent.getSource() == jButton14) {
            client.sendPlayerActions(messageType, "true");
            jButton14.setVisible(false);
            jButton15.setVisible(false);
        } else if (actionEvent.getSource() == jButton15) {
            client.sendPlayerActions(messageType, "false");
            jButton14.setVisible(false);
            jButton15.setVisible(false);
        }
    }


    public void setSpielstart(boolean bool) {
        spielstart = bool;
    }


    public void setDeck(List<Karte> deck) {
        this.deck = deck;
    }


    public List<Karte> getDeck() {
        return deck;
    }


    public List<Karte> getSkat() {
        return skat;
    }
}