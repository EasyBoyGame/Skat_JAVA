package org.example.game;

import org.example.client_server_system.Client;
import org.example.client_server_system.MessageType;
import org.example.game_selection.panels.WaitingLobby;
import org.example.logic.Karte;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class GameWindow extends JFrame {

    private JButton jButton1 = new JButton();   // deck Karte 1
    private JButton jButton2 = new JButton();   // deck Karte 2
    private JButton jButton3 = new JButton();   // deck Karte 3
    private JButton jButton4 = new JButton();   // deck Karte 4
    private JButton jButton5 = new JButton();   // deck Karte 5
    private JButton jButton6 = new JButton();   // deck Karte 6
    private JButton jButton7 = new JButton();   // deck Karte 7
    private JButton jButton8 = new JButton();   // deck Karte 8
    private JButton jButton9 = new JButton();   // deck Karte 9
    private JButton jButton10 = new JButton();  // deck Karte 10
    private JButton jButton11 = new JButton();  // Skat Karte 1
    private JButton jButton12 = new JButton();  // Skat Karte 2
    private JButton jButton14 = new JButton();  // reizen ja
    private JButton jButton15 = new JButton();  // reizen nein
    private JLabel jLabelCard1 = new JLabel();
    private JLabel jLabelCard2 = new JLabel();
    private JLabel jLabelCard3 = new JLabel();
    private Canvas canvas4 = new Canvas();
    private Canvas canvas5 = new Canvas();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel[] cardPlaces = new JLabel[3];
    private List<Karte> deck;
    private List<Karte> skat;
    private Client client;
    private String[][] players;
    private boolean reizen = true;
    private boolean spielstart = false;
    private boolean reizAntwort = false;


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
        setTitle("GameWindow");
        setResizable(false);
        Container cp = getContentPane();
        cp.setLayout(null);

        //region Karten
        jButton1.setBounds(12, 401, 93, 141);
        jButton1.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton1.setText(deck.get(0).toString());
        jButton1.setMargin(new Insets(2, 2, 2, 2));
        jButton1.addActionListener(this::buttonActionPerformed);
        cp.add(jButton1);
        jButton2.setBounds(111, 401, 93, 141);
        jButton2.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton2.setText(deck.get(1).toString());
        jButton2.setMargin(new Insets(2, 2, 2, 2));
        jButton2.addActionListener(this::buttonActionPerformed);
        cp.add(jButton2);
        jButton3.setBounds(210, 401, 93, 141);
        jButton3.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton3.setText(deck.get(2).toString());
        jButton3.setMargin(new Insets(2, 2, 2, 2));
        jButton3.addActionListener(this::buttonActionPerformed);
        cp.add(jButton3);
        jButton4.setBounds(309, 401, 93, 141);
        jButton4.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton4.setText(deck.get(3).toString());
        jButton4.setMargin(new Insets(2, 2, 2, 2));
        jButton4.addActionListener(this::buttonActionPerformed);
        cp.add(jButton4);
        jButton5.setBounds(408, 401, 93, 141);
        jButton5.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton5.setText(deck.get(4).toString());
        jButton5.setMargin(new Insets(2, 2, 2, 2));
        jButton5.addActionListener(this::buttonActionPerformed);
        cp.add(jButton5);
        jButton6.setBounds(507, 401, 93, 141);
        jButton6.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton6.setText(deck.get(5).toString());
        jButton6.setMargin(new Insets(2, 2, 2, 2));
        jButton6.addActionListener(this::buttonActionPerformed);
        cp.add(jButton6);
        jButton7.setBounds(606, 401, 93, 141);
        jButton7.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton7.setText(deck.get(6).toString());
        jButton7.setMargin(new Insets(2, 2, 2, 2));
        jButton7.addActionListener(this::buttonActionPerformed);
        cp.add(jButton7);
        jButton8.setBounds(705, 401, 93, 141);
        jButton8.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton8.setText(deck.get(7).toString());
        jButton8.setMargin(new Insets(2, 2, 2, 2));
        jButton8.addActionListener(this::buttonActionPerformed);
        cp.add(jButton8);
        jButton9.setBounds(804, 401, 93, 141);
        jButton9.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton9.setText(deck.get(8).toString());
        jButton9.setMargin(new Insets(2, 2, 2, 2));
        jButton9.addActionListener(this::buttonActionPerformed);
        cp.add(jButton9);
        jButton10.setBounds(903, 401, 93, 141);
        jButton10.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton10.setText(deck.get(9).toString());
        jButton10.setMargin(new Insets(2, 2, 2, 2));
        jButton10.addActionListener(this::buttonActionPerformed);
        cp.add(jButton10);
        //endregion

        //region Buttons Skat
        jButton11.setBounds(1002, 401, 93, 141);
        jButton11.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton11.setText(skat.get(0).toString());
        jButton11.setMargin(new Insets(2, 2, 2, 2));
        jButton11.addActionListener(this::buttonActionPerformed);
        jButton11.setVisible(false);
        jButton11.setEnabled(false);
        cp.add(jButton11);

        jButton12.setBounds(1101, 401, 93, 141);
        jButton12.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton12.setText(skat.get(1).toString());
        jButton12.setMargin(new Insets(2, 2, 2, 2));
        jButton12.addActionListener(this::buttonActionPerformed);
        jButton12.setEnabled(false);
        jButton12.setVisible(false);
        cp.add(jButton12);
        //endregion


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

        //region jLabel
        jLabelCard1.setBounds(384, 56, 136, 137);
        cp.add(jLabelCard1);
        cardPlaces[0] = jLabelCard1;
        jLabelCard2.setBounds(744, 64, 136, 137);
        cp.add(jLabelCard2);
        cardPlaces[1] = jLabelCard2;
        jLabelCard3.setBounds(568, 256, 136, 137);
        cp.add(jLabelCard3);
        cardPlaces[2] = jLabelCard3;
        jLabel1.setBounds(400, 16, 80, 24);
        jLabel1.setFont(new Font("Dialog", Font.BOLD, 11));
        jLabel1.setText("Spieler 2");
        cp.add(jLabel1);
        jLabel2.setBounds(768, 16, 80, 24);
        jLabel2.setFont(new Font("Dialog", Font.BOLD, 11));
        jLabel2.setText("Spieler 3");
        cp.add(jLabel2);
        //endregion

        canvas4.setBounds(1080, 16, 24, 73);
        cp.add(canvas4);
        canvas5.setBounds(1080, 100, 24, 73);
        cp.add(canvas5);

        updateButtonText();
        setVisible(true);
    }


    // Buttonname → wird zu jeweiliger Karte umbenannt
    public void updateButtonText() {
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
    }

    public void buttonActionPerformed(ActionEvent evt) {
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


    public void cardPlayed(String content) {
        cardPlaces[client.getPlayerTurn()].setText(content);
    }


    public void enableReizen(String reizWert, boolean reizAntwort) {
        this.reizAntwort = reizAntwort;
        jButton14.setVisible(true);
        jButton14.setText(reizWert);
        jButton15.setVisible(true);
    }


    public void enableButton() {
        jButton11.setEnabled(true);
        jButton11.setVisible(true);
        jButton12.setEnabled(true);
        jButton12.setVisible(true);
    }


    private void reizen_ActionPerformed(ActionEvent actionEvent) {
        if (reizAntwort){
            handleReizantwort(actionEvent, MessageType.REIZ_ANTWORT);
        } else {
            handleReizantwort(actionEvent, MessageType.REIZEN);
        }
    }


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


    public void setSkat(List<Karte> skat) {
        this.skat = skat;
    }


    public List<Karte> getDeck() {
        return deck;
    }


    public List<Karte> getSkat() {
        return skat;
    }
}