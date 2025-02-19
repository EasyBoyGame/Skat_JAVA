package org.example.game_selection.panels;

import org.example.game_selection.GameSelection;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;

public class WaitingLobby extends JPanel {
    private GameSelection parentWindow;
    private String username;
    private InetAddress ip;
    private int port;

    public WaitingLobby(GameSelection parentWindow, String username, InetAddress ip, int port){
        this.parentWindow = parentWindow;
        parentWindow.setResizable(false);
        this.username = username;
        this.ip = ip;
        this.port = port;


        setLayout(null);
        setPreferredSize(new Dimension(parentWindow.getWidth(), parentWindow.getHeight()));

        initButtons();
        initLabels();
        setVisible(true);
        revalidate();
        repaint();
    }

    private void initButtons() {
        JButton startGame = new JButton("Start");
        startGame.setEnabled(true);
        startGame.setBounds(parentWindow.getWidth() - 150, parentWindow.getHeight() - 120, 100, 50);
        startGame.addActionListener(e -> System.out.println("BUTTON WORKS!"));
        add(startGame);
    }

    private void initLabels(){
        JLabel gamePort = new JLabel();
        gamePort.setBounds(parentWindow.getWidth() - 120, 20, 100, 50);
        gamePort.setText("Port: " + port);
        add(gamePort);


        //region columnnames
        Font columnName = new Font("ARIAL", Font.PLAIN, 16);

        JLabel player = new JLabel();
        player.setBounds(parentWindow.getWidth() / 10, 20, 200, 50);
        player.setText("Spielername");
        player.setFont(columnName);

        JLabel playerIp = new JLabel();
        playerIp.setEnabled(true);
        playerIp.setBounds(parentWindow.getWidth() / 10 * 3, 20, 150, 50);
        playerIp.setText("Spieler IP-Adresse");
        playerIp.setFont(columnName);

        JLabel playerNr = new JLabel();
        playerNr.setEnabled(true);
        playerNr.setBounds(parentWindow.getWidth() / 10 * 6, 20, 200, 50);
        playerNr.setText("Spielernummer");
        playerNr.setFont(columnName);

        add(player);
        add(playerIp);
        add(playerNr);
        //endregion

        //region player one
        JLabel playerOne = new JLabel();
        playerOne.setBounds(parentWindow.getWidth() / 10, 80, 200, 50);
        playerOne.setText(username);

        JLabel playerOneIp = new JLabel();
        playerOneIp.setEnabled(true);
        playerOneIp.setBounds(parentWindow.getWidth() / 10 * 3, 80, 150, 50);
        playerOneIp.setText(String.valueOf(ip));

        JLabel playerOneNr = new JLabel();
        playerOneNr.setEnabled(true);
        playerOneNr.setBounds(parentWindow.getWidth() / 10 * 6, 80, 200, 50);
        playerOneNr.setText("Spieler 1");

        add(playerOne);
        add(playerOneIp);
        add(playerOneNr);
        //endregion

        //region player two
        JLabel playerTwo = new JLabel();
        playerTwo.setEnabled(true);
        playerTwo.setBounds(parentWindow.getWidth() / 10, 150, 200, 50);
        playerTwo.setText("SPIELER 2");

        JLabel playerTwoIp = new JLabel();
        playerTwoIp.setEnabled(true);
        playerTwoIp.setBounds(parentWindow.getWidth() / 10 * 3, 150, 150, 50);
        playerTwoIp.setText("SPIELER 2 - IP");

        JLabel playerTwoNr = new JLabel();
        playerTwoNr.setEnabled(true);
        playerTwoNr.setBounds(parentWindow.getWidth() / 10 * 6, 150, 200, 50);
        playerTwoNr.setText("Spieler 2");

        add(playerTwo);
        add(playerTwoIp);
        add(playerTwoNr);
        //endregion

        //region player three
        JLabel playerThree = new JLabel();
        playerThree.setEnabled(true);
        playerThree.setBounds(parentWindow.getWidth() / 10, 220, 200, 50);
        playerThree.setText("SPIELER 3");

        JLabel playerThreeIp = new JLabel();
        playerThreeIp.setEnabled(true);
        playerThreeIp.setBounds(parentWindow.getWidth() / 10 * 3, 220, 150, 50);
        playerThreeIp.setText("SPIELER 3 - IP");

        JLabel playerThreeNr = new JLabel();
        playerThreeNr.setEnabled(true);
        playerThreeNr.setBounds(parentWindow.getWidth() / 10 * 6, 220, 200, 50);
        playerThreeNr.setText("Spieler 3");

        add(playerThree);
        add(playerThreeIp);
        add(playerThreeNr);
        //endregion
    }
}