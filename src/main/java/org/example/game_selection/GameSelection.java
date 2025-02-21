package org.example.game_selection;

import org.example.game_selection.panels.*;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.util.HashMap;

public class GameSelection extends JFrame {
    //Attribute
    private double ScreenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private double ScreenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    public HashMap<PanelType, JPanel> panels = new HashMap<>();
    private JPanel currentPanel;

    public GameSelection() {
        initWindow();
    }

    private void initWindow() {

        //JFrame Einstellungen
        setSize((int) (ScreenWidth/2), (int) (ScreenHeight/2));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Registrierung der einzelnen User Interfaces
        panels.put(PanelType.MAIN_MENU, new MainPanel(this));
        panels.put(PanelType.JOIN_GAME, new JoinPanel(this));
        panels.put(PanelType.HOST_GAME, new HostPanel(this));
        panels.put(PanelType.WAITING_LOBBY, WaitingLobby.createInstance(this));

        changePanel(PanelType.MAIN_MENU);

        setVisible(true);
    }

    // Methode zum Wechseln zwischen verschiedenen UIs (Dank verwendung von Enum beliebig ausbaubar)
    public void changePanel(PanelType type){
        if (currentPanel != null) {
            remove(currentPanel);
        }

        currentPanel = panels.get(type);
        add(currentPanel);
        revalidate();
        repaint();
    }

}
