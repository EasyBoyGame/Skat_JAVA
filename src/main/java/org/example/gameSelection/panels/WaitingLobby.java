package org.example.gameSelection.panels;

import org.example.clientServerSystem.Client;
import org.example.clientServerSystem.MessageType;
import org.example.gameSelection.GameSelection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.net.InetAddress;

public class WaitingLobby extends JPanel {
    private static WaitingLobby INSTANCE;
    private GameSelection parentWindow;
    public String[][] players = new String[3][2];
    private int port;
    public int connectedPlayers;
    private Client client;

    private WaitingLobby(GameSelection parentWindow){
        this.parentWindow = parentWindow;
        parentWindow.setResizable(false);

        setLayout(null);
        setPreferredSize(new Dimension(parentWindow.getWidth(), parentWindow.getHeight()));

        updateWindow();
        setVisible(true);
        revalidate();
        repaint();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void updateWindow(){
        this.removeAll();
        initButtons();
        initLabels();
    }

    public static WaitingLobby createInstance(GameSelection parentWindow){
        if (INSTANCE == null) {
            return INSTANCE = new WaitingLobby(parentWindow);
        }
        return INSTANCE;
    }

    public static WaitingLobby getInstance(){
        return INSTANCE;
    }

    public void addPlayer(String username, String ip){
        if (connectedPlayers >= 2) return;
        players[connectedPlayers][0] = username;
        players[connectedPlayers][1] = ip;
        connectedPlayers++;
    }

    public void setPort(int port){
        this.port = port;
    }

    private void initButtons() {
        JButton startGame = new JButton("Start");
        startGame.setEnabled(true);
        startGame.setBounds(parentWindow.getWidth() - 150, parentWindow.getHeight() - 120, 100, 50);
        startGame.addActionListener(e -> {
            client.sendPlayerActions(MessageType.OPEN_GAME, "");
            parentWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            parentWindow.dispatchEvent(new WindowEvent(parentWindow, WindowEvent.WINDOW_CLOSING));
        });
        add(startGame);
    }

    private void initLabels() {
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
        playerOne.setText(players[0][0]);

        JLabel playerOneIp = new JLabel();
        playerOneIp.setEnabled(true);
        playerOneIp.setBounds(parentWindow.getWidth() / 10 * 3, 80, 150, 50);
        playerOneIp.setText(players[0][1]);

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
        playerTwo.setText(players[1][0]);

        JLabel playerTwoIp = new JLabel();
        playerTwoIp.setEnabled(true);
        playerTwoIp.setBounds(parentWindow.getWidth() / 10 * 3, 150, 150, 50);
        playerTwoIp.setText(players[1][1]);

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
        playerThree.setText(players[2][0]);

        JLabel playerThreeIp = new JLabel();
        playerThreeIp.setEnabled(true);
        playerThreeIp.setBounds(parentWindow.getWidth() / 10 * 3, 220, 150, 50);
        playerThreeIp.setText(players[2][1]);

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