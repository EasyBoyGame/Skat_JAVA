package org.example.client_server_system;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    String username;
    Socket client;
    Server server;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;

    public ClientHandler(String username, Socket client, Server server) {
        this.username = username;
        this.client = client;
        this.server = server;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        clientActionListener();
    }

    private void clientActionListener() {
        while(!client.isClosed()){
            try {
                MessageType actionType = MessageType.valueOf(bufferedReader.readLine());
                String actionBody = bufferedReader.readLine();
                processMessage(actionType, actionBody);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processMessage(MessageType actionType, String actionBody) {
        switch (actionType){
            case START_GAME:
            default:
                System.out.println("MessageType invalid!");
        }
    }
}
