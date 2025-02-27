package org.example.game;

import org.example.logic.Karte;
import org.example.logic.Mischen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GameWindow extends JFrame {

    private JButton jButton1 = new JButton();
    private Canvas canvas1 = new Canvas();
    private JButton jButton2 = new JButton();
    private JButton jButton3 = new JButton();
    private JButton jButton4 = new JButton();
    private JButton jButton5 = new JButton();
    private JButton jButton6 = new JButton();
    private JButton jButton7 = new JButton();
    private JButton jButton8 = new JButton();
    private JButton jButton9 = new JButton();
    private JButton jButton10 = new JButton();
    private JButton jButton11 = new JButton();
    private Canvas canvas2 = new Canvas();
    private Canvas canvas3 = new Canvas();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private Canvas canvas4 = new Canvas();
    private Canvas canvas5 = new Canvas();
    Mischen mischen = new Mischen();
    List<Karte> deck = mischen.shuffle();
    // Ende Attribute

    public GameWindow() {
        // Frame init
        super();
        this.client = client;
        this.deck = deck;
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
        // Anfang Komponenten

        jButton1.setBounds(112, 440, 105, 73);
        jButton1.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton1.setText(deck.get(0).toString());
        jButton1.setMargin(new Insets(2, 2, 2, 2));
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });
        cp.add(jButton1);
        canvas1.setBounds(384, 56, 136, 137);
        cp.add(canvas1);
        jButton2.setBounds(224, 440, 105, 73);
        jButton2.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton2.setText(deck.get(1).toString());
        jButton2.setMargin(new Insets(2, 2, 2, 2));
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });
        cp.add(jButton2);
        jButton3.setBounds(336, 440, 105, 73);
        jButton3.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton3.setText(deck.get(2).toString());
        jButton3.setMargin(new Insets(2, 2, 2, 2));
        jButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });
        cp.add(jButton3);
        jButton4.setBounds(448, 440, 105, 73);
        jButton4.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton4.setText(deck.get(3).toString());
        jButton4.setMargin(new Insets(2, 2, 2, 2));
        jButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });
        cp.add(jButton4);
        jButton5.setBounds(560, 440, 105, 73);
        jButton5.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton5.setText(deck.get(4).toString());
        jButton5.setMargin(new Insets(2, 2, 2, 2));
        jButton5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });
        cp.add(jButton5);
        jButton6.setBounds(672, 440, 105, 73);
        jButton6.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton6.setText(deck.get(5).toString());
        jButton6.setMargin(new Insets(2, 2, 2, 2));
        jButton6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });
        cp.add(jButton6);
        jButton7.setBounds(784, 440, 105, 73);
        jButton7.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton7.setText(deck.get(6).toString());
        jButton7.setMargin(new Insets(2, 2, 2, 2));
        jButton7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });
        cp.add(jButton7);
        jButton8.setBounds(896, 440, 105, 73);
        jButton8.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton8.setText(deck.get(7).toString());
        jButton8.setMargin(new Insets(2, 2, 2, 2));
        jButton8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });
        cp.add(jButton8);
        jButton9.setBounds(1008, 440, 105, 73);
        jButton9.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton9.setText(deck.get(8).toString());
        jButton9.setMargin(new Insets(2, 2, 2, 2));
        jButton9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });
        cp.add(jButton9);
        jButton10.setBounds(1120, 440, 105, 73);
        jButton10.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton10.setText(deck.get(9).toString());
        jButton10.setMargin(new Insets(2, 2, 2, 2));
        jButton10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });
        cp.add(jButton11);
        jButton11.setBounds(1080, 300, 105, 73);
        jButton11.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton11.setText("Skat aufnehmen");
        jButton11.setMargin(new Insets(2, 2, 2, 2));
        jButton11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton11_ActionPerformed(evt);
            }
        });
        cp.add(jButton11);
        canvas2.setBounds(744, 64, 136, 137);
        cp.add(canvas2);
        canvas3.setBounds(568, 256, 136, 137);
        cp.add(canvas3);
        jLabel1.setBounds(400, 16, 80, 24);
        jLabel1.setFont(new Font("Dialog", Font.BOLD, 11));
        jLabel1.setText("Spieler 2");
        cp.add(jLabel1);
        jLabel2.setBounds(768, 16, 80, 24);
        jLabel2.setFont(new Font("Dialog", Font.BOLD, 11));
        jLabel2.setText("Spieler 3");
        cp.add(jLabel2);
        canvas4.setBounds(1080, 16, 24, 73);
        cp.add(canvas4);
        canvas5.setBounds(1080, 100, 24, 73);
        cp.add(canvas5);

        setVisible(true);
    }


    public static void main(String[] args) {
        //new GUI();
    }

    public void buttonActionPerformed(ActionEvent evt) {
        int currentTurn = client.getPlayerTurn();
        if (players[currentTurn][0].equals(client.getUsername())){
            JButton button = (JButton) evt.getSource();
            button.setVisible(false);
            button.setEnabled(false);
            client.sendPlayerActions(MessageType.CARD_PLAYED, button.getText());
        } else {
            JOptionPane.showMessageDialog(this, "Spieler " + players[currentTurn][0] + " ist am Zug!\nBitte warten");
        }
        //gelegteKarte.addButtonText(jButton1.getText());
    }


    public void jButton11_ActionPerformed(ActionEvent evt) {

    }


}