package org.example.game;

import org.example.clientServerSystem.Client;
import org.example.clientServerSystem.MessageType;
import org.example.clientServerSystem.Player;
import org.example.gameSelection.panels.WaitingLobby;
import org.example.logic.Farbe;
import org.example.logic.Karte;
import org.example.logic.Kartenart;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class GameWindow extends JFrame {
    private ArrayList<JButton> jButtons = new ArrayList<>();
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
    public static ArrayList<Player> players = new ArrayList<>();
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
        int frameWidth = 1300;
        int frameHeight = 600;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("SKAT - " + client.getPlayer().getUsername());
        setResizable(false);
        Container cp = getContentPane();
        cp.setLayout(null);

        //region jLabel
        String myName = client.getPlayer().getUsername();
        int userindex;
        if (players.get(0).getUsername().equals(myName)) {
            userindex = 0;
        } else if (players.get(1).getUsername().equals(myName)) {
            userindex = 1;
        } else {
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
        jLabel1.setText("anderer 1");
        cp.add(jLabel1);
        jLabel2.setBounds(768, 16, 80, 24);
        jLabel2.setFont(new Font("Dialog", Font.PLAIN, 11));
        jLabel2.setText("anderer 2");
        cp.add(jLabel2);
        //endregion

        for (int i = 0; i < 10; i++) {
            setupButton(cp , i);
        }
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

    public void setupButton(Container cp, int count) {
        JButton jButton = new JButton();
        jButton.setForeground(new Color(0, 0, 0, 0));
        jButton.setBounds(10 + count*100, 401, 93, 141);
        jButton.setFont(new Font("Dialog", Font.PLAIN, -10));
        jButton.setText(deck.get(count).toString());
        jButton.setMargin(new Insets(2, 2, 2, 2));
        jButton.addActionListener(this::buttonActionPerformed);
        cp.add(jButton);
        jButtons.add(jButton);
    }

    // Buttonname → wird zu jeweiliger Karte umbenannt
    public void updateButtonText() {
        // sets the name of the card (button) for further usage -> communication with server
        int i = 0;
        for (JButton jButton : jButtons) {
            jButton.setText(deck.get(i).toString());
            jButton.setIcon(cardImage.loadImageFromFile(cardImage.bilder.get(deck.get(i).toString()),
                    80, 120));
            i++;
        }

        if (jButton11.isVisible()) {
            jButton11.setText(deck.get(10).toString());
            jButton12.setText(deck.get(11).toString());
            jButton11.setIcon(cardImage.loadImageFromFile(cardImage.bilder.get(deck.get(10).toString()),
                    80, 120));
            jButton12.setIcon(cardImage.loadImageFromFile(cardImage.bilder.get(deck.get(11).toString()),
                    80, 120));
        }
    }

    public void buttonActionPerformed(ActionEvent evt) {
        if(skatDruecken && skatCount < 2){
            JButton button = (JButton) evt.getSource();
            button.setVisible(false);
            button.setEnabled(false);
            skat.add(new Karte(Farbe.valueOf(button.getText().split(" ")[0]), Kartenart.valueOf(button.getText().split(" ")[1])));
            skatCount++;
            if(skatCount == 2) client.sendPlayerActions(MessageType.SKAT_SENDEN, skat.get(0).toString() + "," + skat.get(1).toString());
        }

        if (!spielstart) return;
        int currentTurn = client.getPlayerTurn();
        if (players.get(currentTurn) == client.getPlayer()) {
            JButton button = (JButton) evt.getSource();
            button.setVisible(false);
            button.setEnabled(false);
            client.sendPlayerActions(MessageType.CARD_PLAYED, button.getText());
        } else {
            JOptionPane.showMessageDialog(this, "Spieler " +
                    players.get(currentTurn).getUsername() + " ist am Zug!\nBitte warten");
        }
    }

    public void cardPlayed(String content) {
        if (content.isEmpty()) return;
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
        if (players.get(currentTurn) == client.getPlayer()){
            setButtonEnable(true);
        } else {
            setButtonEnable(false);
        }
    }


    private void setButtonEnable(boolean bool) {
        for (JButton jButton : jButtons) {
            jButton.setEnabled(true);
        }
        jButton11.setEnabled(bool);
        jButton12.setEnabled(bool);
    }


    public void enableSkatButton() {
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


    public List<Karte> getDeck() {
        return deck;
    }


    public List<Karte> getSkat() {
        return skat;
    }
}