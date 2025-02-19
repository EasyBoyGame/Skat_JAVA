package org.example.client_server_system;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    //Attribute
    Socket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    String username;

    /**
     * Ein Client erkennt und sendet Inputs des Spielers an den Server und Updated die GUI durch Infos vomServer
     * @param username Name des Spielers im Spiel
     * @param ip Addresse aus dem Intranet
     * @param port Port des Servers mit dem man sich verbinden möchte
     */
    public Client(String username, String ip, int port) {
        this.username = username;
        try {
            //Versucht Verbindung mit dem Server aufzubauen
            socket = new Socket(ip, port);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            
            joinWaitingLobby();

        } catch (IOException e) {
            System.out.println("Can't connect to server ");
        }
    }

    private void joinWaitingLobby() {
        try {
            bufferedWriter.write(String.valueOf(MessageType.WAITING_LOBBY_JOIN));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
