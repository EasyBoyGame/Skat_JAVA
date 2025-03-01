package org.example.logic;
import org.example.game.GameWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;
import java.util.List;

public class spielAuswahl extends JFrame{

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



    public spielAuswahl(GameWindow gameWindow) {
        super();
        this.gameWindow = gameWindow;
        deck = gameWindow.getDeck();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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

        jButton1.setBounds(16, 16, 73, 33);
        jButton1.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton1.setText("Kreuz");
        jButton1.setMargin(new Insets(2, 2, 2, 2));
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1_ActionPerformed(evt);
            }
        });
        cp.add(jButton1);
        //canvas1.setBounds(384, 56, 136, 137);
        //cp.add(canvas1);
        jButton2.setBounds(96, 16, 73, 33);
        jButton2.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton2.setText("Pik");
        jButton2.setMargin(new Insets(2, 2, 2, 2));
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton2_ActionPerformed(evt);
            }
        });
        cp.add(jButton2);
        jButton3.setBounds(176, 16, 73, 33);
        jButton3.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton3.setText("Herz");
        jButton3.setMargin(new Insets(2, 2, 2, 2));
        jButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton3_ActionPerformed(evt);
            }
        });
        cp.add(jButton3);
        jButton4.setBounds(256, 16, 73, 33);
        jButton4.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton4.setText("Karo");
        jButton4.setMargin(new Insets(2, 2, 2, 2));
        jButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton4_ActionPerformed(evt);
            }
        });
        cp.add(jButton4);
        jButton5.setBounds(336, 16, 73, 33);
        jButton5.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton5.setText("Grand");
        jButton5.setMargin(new Insets(2, 2, 2, 2));
        jButton5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton5_ActionPerformed(evt);
            }
        });
        cp.add(jButton5);
        jButton6.setBounds(416, 16, 73, 33);
        jButton6.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton6.setText("Null");
        jButton6.setMargin(new Insets(2, 2, 2, 2));
        jButton6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton6_ActionPerformed(evt);
            }
        });
        cp.add(jButton6);
        jButton7.setBounds(16, 136, 152, 41);
        jButton7.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton7.setText("Skat aufnehmen");
        jButton7.setMargin(new Insets(2, 2, 2, 2));
        jButton7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton7_ActionPerformed(evt);
            }
        });
        cp.add(jButton7);
        jButton8.setBounds(336, 136, 153, 41);
        jButton8.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton8.setText("Handspiel ansagen");
        jButton8.setMargin(new Insets(2, 2, 2, 2));
        jButton8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton8_ActionPerformed(evt);
            }
        });
        cp.add(jButton8);
        jButton9.setBounds(336, 136, 153, 41);
        jButton9.setFont(new Font("Dialog", Font.BOLD, 11));
        jButton9.setText("Spiel ansagen");
        jButton9.setMargin(new Insets(2, 2, 2, 2));
        jButton9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton9_ActionPerformed(evt);
            }
        });
        cp.add(jButton9);
        jButton9.setVisible(false);
        jButton9.setEnabled(false);
        setVisible(true);
    }

    // Button Kreuz
    public void jButton1_ActionPerformed(ActionEvent evt) {
        backGroundNull();
        jButton1.setBackground(Color.red);
        spielmodus = Farbe.KREUZ;
        gameWindow.setDeck(mischen.kartenSortieren(gameWindow.getDeck(), Farbe.KREUZ));
        gameWindow.updateButtonText();
    }

    // Button Pik
    public void jButton2_ActionPerformed(ActionEvent evt) {
        backGroundNull();
        jButton2.setBackground(Color.red);
        spielmodus = Farbe.PIK;
        gameWindow.setDeck(mischen.kartenSortieren(gameWindow.getDeck(), Farbe.PIK));
        gameWindow.updateButtonText();
    }

    // Button Herz
    public void jButton3_ActionPerformed(ActionEvent evt) {
        backGroundNull();
        jButton3.setBackground(Color.red);
        spielmodus = Farbe.HERZ;
        gameWindow.setDeck(mischen.kartenSortieren(gameWindow.getDeck(), Farbe.HERZ));
        gameWindow.updateButtonText();
    }

    // Button Karo
    public void jButton4_ActionPerformed(ActionEvent evt) {
        backGroundNull();
        jButton4.setBackground(Color.red);
        spielmodus = Farbe.KARO;
        gameWindow.setDeck(mischen.kartenSortieren(gameWindow.getDeck(), Farbe.KARO));
        gameWindow.updateButtonText();
    }

    // Button Grand
    public void jButton5_ActionPerformed(ActionEvent evt) {
        backGroundNull();
        jButton5.setBackground(Color.red);
        spielmodus = Farbe.KREUZ;
        gameWindow.setDeck(mischen.kartenSortieren(gameWindow.getDeck(), Farbe.KREUZ));
        gameWindow.updateButtonText();
    }

    // Button Null
    public void jButton6_ActionPerformed(ActionEvent evt) {
        backGroundNull();
        jButton6.setBackground(Color.red);
        spielmodus = Farbe.NULL;
        gameWindow.setDeck(mischen.kartenSortieren(gameWindow.getDeck(), Farbe.NULL));
        gameWindow.updateButtonText();
    }
    public void jButton7_ActionPerformed(ActionEvent evt) {
        jButton7.setVisible(false);
        jButton7.setEnabled(false);
        jButton8.setVisible(false);
        jButton8.setEnabled(false);
        jButton9.setVisible(true);
        jButton9.setEnabled(true);
        gameWindow.enableButton();
        // TODO HIER BITTE
        //GUI.dec1.addAll(GUI.decskat);

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
            default:
                throw new IllegalArgumentException("Ungültiger Spielmodus: " + spielmodus);
        }

        gameWindow.updateButtonText();
    }
    public void jButton8_ActionPerformed(ActionEvent evt) {
        setVisible(false);
        setEnabled(false);
        gameWindow.setReizen(false);//GUI.spielstart = true;
    }

    public void jButton9_ActionPerformed(ActionEvent evt) {
        setVisible(false);
        setEnabled(false);
        gameWindow.setReizen(false);//GUI.spielstart = true;
    }

    public void backGroundNull() {
        jButton1.setBackground(null);
        jButton2.setBackground(null);
        jButton3.setBackground(null);
        jButton4.setBackground(null);
        jButton5.setBackground(null);
        jButton6.setBackground(null);
    }
}
