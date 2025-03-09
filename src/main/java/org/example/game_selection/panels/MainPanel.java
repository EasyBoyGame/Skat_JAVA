package org.example.game_selection.panels;

import org.example.client_server_system.Client;
import org.example.client_server_system.Server;
import org.example.game_selection.GameSelection;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainPanel extends JPanel {
    private GameSelection parentWindow;

    public MainPanel(GameSelection parentWindow) {
        this.parentWindow = parentWindow;

        // JPanel Einstellungen
        setLayout(null);
        setPreferredSize(new Dimension(parentWindow.getWidth(), parentWindow.getHeight()));

        initButtons();
        debugButton();
        setVisible(true);
        revalidate();
        repaint();
    }

    public void debugButton(){
        int buttonWidth = parentWindow.getWidth() * 8 / 10;
        int buttonHeight = parentWindow.getHeight() * 2 / 10;

        Button debugButton = new Button("Debug Game");
        int centerX = (parentWindow.getWidth() - buttonWidth) / 2;
        int centerY = (parentWindow.getHeight() - buttonHeight) / 3;
        debugButton.setBounds(centerX, centerY/ 3, buttonWidth, buttonHeight);
        debugButton.addActionListener(e -> {
            int port = 1234;
            String username1 = "Debug1";
            String username2 = "Debug2";
            String username3 = "Debug3";
            Server server = new Server(port);
            new Thread(server).start();
            try {
                //Thread.sleep(500);
                Client client = new Client(username1, InetAddress.getLocalHost().getHostAddress(), port, parentWindow);
                //Thread.sleep(500);
                Client client2 = new Client(username2, InetAddress.getLocalHost().getHostAddress(), port, parentWindow);
                //Thread.sleep(500);
                Client client3 = new Client(username3, InetAddress.getLocalHost().getHostAddress(), port, parentWindow);
            } catch (/*InterruptedException | */UnknownHostException ex) {
                throw new RuntimeException(ex);
            }
        });
        add(debugButton);
    }

    private void initButtons() {
        int buttonWidth = parentWindow.getWidth() * 8 / 10;
        int buttonHeight = parentWindow.getHeight() * 2 / 10;

        // Join Game Button
        Button joinButton = new Button("Join Game");
        int centerX = (parentWindow.getWidth() - buttonWidth) / 2;
        int centerY = (parentWindow.getHeight() - buttonHeight) / 3;
        joinButton.setBounds(centerX, centerY, buttonWidth, buttonHeight);
        joinButton.addActionListener(e -> parentWindow.changePanel(PanelType.JOIN_GAME));
        add(joinButton);

        // Host Game Button
        Button hostButton = new Button("Host Game");
        centerX = (parentWindow.getWidth() - buttonWidth) / 2;
        centerY = (parentWindow.getHeight() - buttonHeight) / 3 * 2;
        hostButton.setBounds(centerX, centerY, buttonWidth, buttonHeight);
        hostButton.addActionListener(e -> parentWindow.changePanel(PanelType.HOST_GAME));
        add(hostButton);
    }
}
