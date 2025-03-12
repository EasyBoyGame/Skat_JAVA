package org.example.gameSelection;

import org.example.gameSelection.panels.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class GameSelection extends JFrame {
    //Attribute
    private final double SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private final double SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    public HashMap<PanelType, JPanel> panels = new HashMap<>();
    private JPanel currentPanel;

    public GameSelection() {
        initWindow();
    }

    private void initWindow() {
        initJFrame();
        //Registrierung der einzelnen User Interfaces
        panels.put(PanelType.MAIN_MENU, new MainPanel(this));
        panels.put(PanelType.JOIN_GAME, new JoinPanel(this));
        panels.put(PanelType.HOST_GAME, new HostPanel(this));
        panels.put(PanelType.WAITING_LOBBY, WaitingLobby.createInstance(this));
        changePanel(PanelType.MAIN_MENU);
    }

    public void initJFrame() {
        //JFrame Einstellungen
        setSize((int) (SCREEN_WIDTH /2), (int) (SCREEN_HEIGHT /2));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void changePanel(PanelType type){
        if (currentPanel != null) remove(currentPanel);
        currentPanel = panels.get(type);
        add(currentPanel);
        revalidate();
        repaint();
    }
}
