package org.example.clientServerSystem;

import org.example.game.GameWindow;

public class Player {
    private String username;
    private String ip;

    public Player(String username, String ip) {
        this.ip = ip;
        this.username = username;
        GameWindow.players.add(this);
    }

    public String getUsername() {
        return username;
    }

    public String getIp() {
        return ip;
    }
}
