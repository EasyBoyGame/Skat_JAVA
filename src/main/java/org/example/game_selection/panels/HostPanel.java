package org.example.game_selection.panels;

import org.example.client_server_system.Client;
import org.example.client_server_system.Server;
import org.example.game_selection.GameSelection;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostPanel extends JPanel {

    GameSelection parentWindow;
    JTextField textField1;
    JTextField textField2;

    public HostPanel(GameSelection parentWindow) {
        this.parentWindow = parentWindow;

        setLayout(null);
        setPreferredSize(new Dimension(parentWindow.getWidth(), parentWindow.getHeight()));

        initWindow();


        setVisible(true);
        revalidate();
        repaint();
    }

    private void initWindow() {
        createInputLabel(2, "Username:", textField1 = new JTextField());
        createInputLabel(3, "Port:", textField2 = new JTextField());

        initButton();
    }

    protected void initButton() {

        // Host Button
        JButton hostbutton = new JButton("Host");
        hostbutton.setBounds((parentWindow.getWidth() - 100) / 2, parentWindow.getHeight() / 10 * 6, 100, parentWindow.getHeight() / 9);
        hostbutton.addActionListener(e -> {
            int port = Integer.parseInt(textField2.getText());
            String username = textField1.getText();
            Server server = new Server(port);
            new Thread(server).start();
            try {
                Thread.sleep(500);
                Client client = new Client(username, "localhost", port);

                parentWindow.changePanel(PanelType.WAITING_LOBBY, username, InetAddress.getLocalHost(), port);
            } catch (UnknownHostException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        add(hostbutton);

        // Return Button
        JButton returnButton = new JButton("<-");
        returnButton.setBounds(10, 10, 30, 30);
        returnButton.addActionListener(e -> parentWindow.changePanel(PanelType.MAIN_MENU));
        add(returnButton);
    }


    private void createInputLabel(int number, String text, JTextField textField) {
        // Größenparameter
        int panelWidth = parentWindow.getWidth() / 4 * 3;
        int panelHeight = parentWindow.getHeight() / 9;
        int panelX = (parentWindow.getWidth() - panelWidth) / 2;
        int panelY = (parentWindow.getHeight() - panelHeight) / 6 * number;

        // Nested JPanel
        JPanel namePanel = new JPanel();
        namePanel.setLayout(null);
        namePanel.setBounds(panelX, panelY, panelWidth, panelHeight);

        // JLabel für Bezeichnung
        JLabel label = new JLabel(text);
        label.setSize(panelWidth / 4, panelHeight);
        namePanel.add(label);

        // Textfeld zum Label
        textField.setBounds(panelWidth / 4, 0, panelWidth / 4 * 3, panelHeight);
        namePanel.add(textField);

        add(namePanel);
    }

}
