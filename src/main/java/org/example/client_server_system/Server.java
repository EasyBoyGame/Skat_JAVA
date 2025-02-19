package org.example.client_server_system;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {

    //Attribute
    private ServerSocket serverSocket;
    private int port;
    ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    BufferedReader bufferedReader;

    //Constructor
    public Server(int port) {
        this.port = port;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void startClientLoop() {
        try {
            while (!serverSocket.isClosed()){
                // wartet auf Connectionrequest von Client
                if (clientHandlers.size() == 3) continue;
                System.out.println("Waiting for Client.");
                Socket client = serverSocket.accept();

                bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String username = bufferedReader.readLine();
                System.out.println("The Client " + username + " has connected.");

                ClientHandler clientHandler = new ClientHandler(username, client, this);
                clientHandlers.add(clientHandler);
                System.out.println("A new client handler has been made and added.");

                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {
        startClientLoop();
    }

    public void newPlayerJoin(String username) {
        for (ClientHandler clienthandler: clientHandlers) {
            clienthandler.updateLobby(clientHandlers);
        }
    }

    public void startGameLogic() {
        //TODO init game Logik here
    }
}
