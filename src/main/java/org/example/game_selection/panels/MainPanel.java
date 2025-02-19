package org.example.game_selection.panels;

import org.example.game_selection.GameSelection;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private GameSelection parentWindow;

    public MainPanel(GameSelection parentWindow) {
        this.parentWindow = parentWindow;

        // JPanel Einstellungen
        setLayout(null);
        setPreferredSize(new Dimension(parentWindow.getWidth(), parentWindow.getHeight()));

        initButtons();
        setVisible(true);
        revalidate();
        repaint();
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
