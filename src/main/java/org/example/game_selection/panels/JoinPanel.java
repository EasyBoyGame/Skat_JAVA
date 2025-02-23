package org.example.game_selection.panels;

import org.example.client_server_system.Client;
import org.example.game_selection.GameSelection;

import javax.swing.*;
import java.awt.*;

public class JoinPanel extends JPanel {

    GameSelection parentWindow;
    JTextField textField1;
    JTextField textField2;
    JTextField textField3;

    public JoinPanel(GameSelection parentWindow) {
        this.parentWindow = parentWindow;


        // JPanel Einstellungen
        setLayout(null);
        setPreferredSize(new Dimension(parentWindow.getWidth(), parentWindow.getHeight()));

        initWindow();

        setVisible(true);
        revalidate();
        repaint();
    }

    private void initWindow() {
        createInputLabel(1, "Username:", textField1 = new JTextField());
        createInputLabel(2, "IP:", textField2 = new JTextField());
        createInputLabel(3, "Port:", textField3 = new JTextField());

        initButton();
    }

    protected void initButton() {

        // Join Button
        JButton joinButton = new JButton("JOIN");
        joinButton.setBounds((parentWindow.getWidth() - 100) / 2, parentWindow.getHeight() / 10 * 6, 100, parentWindow.getHeight() / 9);
        joinButton.setVisible(true);
        joinButton.addActionListener(e -> {Client client = new Client(textField1.getText(), textField2.getText(), Integer.parseInt(textField3.getText()), parentWindow);});
        add(joinButton);

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

        // JLabel für bezeichnung
        JLabel label = new JLabel(text);
        label.setSize(panelWidth/4, panelHeight);
        namePanel.add(label);

        // Textfeld zum JLabel
        textField.setBounds(panelWidth/4, 0, panelWidth/4*3, panelHeight);
        namePanel.add(textField);

        add(namePanel);
    }

}
