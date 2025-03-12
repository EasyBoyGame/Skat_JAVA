package org.example.gameSelection.panels;

import org.example.clientServerSystem.Client;
import org.example.clientServerSystem.MessageType;
import org.example.clientServerSystem.Player;
import org.example.gameSelection.GameSelection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class WaitingLobby extends JPanel {
    private static WaitingLobby INSTANCE;
    private GameSelection parentWindow;
    public ArrayList<Player> players = new ArrayList<>();
    private int port;
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
        if (players.size() >= 2) return;
        Player player = new Player(username, ip);
        players.add(player);
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
        Font font = new Font("ARIAL", Font.PLAIN, 16);

        JLabel player = new JLabel();
        player.setBounds(parentWindow.getWidth() / 10, 20, 200, 50);
        player.setText("Spielername");
        player.setFont(font);

        JLabel playerIp = new JLabel();
        playerIp.setEnabled(true);
        playerIp.setBounds(parentWindow.getWidth() / 10 * 3, 20, 150, 50);
        playerIp.setText("Spieler IP-Adresse");
        playerIp.setFont(font);

        JLabel playerNr = new JLabel();
        playerNr.setEnabled(true);
        playerNr.setBounds(parentWindow.getWidth() / 10 * 6, 20, 200, 50);
        playerNr.setText("Spielernummer");
        playerNr.setFont(font);

        add(player);
        add(playerIp);
        add(playerNr);

        for (int i = 0; i < players.size(); i++) {
            setUpPlayer(players.get(i), i);
        }
    }

    public void setUpPlayer(Player player, int count) {
        //region player three
        JLabel playerThree = new JLabel();
        playerThree.setEnabled(true);
        playerThree.setBounds(parentWindow.getWidth() / 10, 80 + 70*count, 200, 50);
        playerThree.setText(player.getUsername());

        JLabel playerThreeIp = new JLabel();
        playerThreeIp.setEnabled(true);
        playerThreeIp.setBounds(parentWindow.getWidth() / 10 * 3, 80 + 70*count, 150, 50);
        playerThreeIp.setText(player.getIp());

        JLabel playerThreeNr = new JLabel();
        playerThreeNr.setEnabled(true);
        playerThreeNr.setBounds(parentWindow.getWidth() / 10 * 6, 80 + 70*count, 200, 50);
        playerThreeNr.setText("Spieler " + count);

        add(playerThree);
        add(playerThreeIp);
        add(playerThreeNr);
    }
}