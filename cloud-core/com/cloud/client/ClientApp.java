package com.cloud.client;

import java.io.*;
import java.net.Socket;

public class ClientApp {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 9090;

        try (Socket socket = new Socket(host, port);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.print("Enter message: ");
            String message = userInput.readLine();
            out.println(message);

            String response = in.readLine();
            System.out.println("Server replied: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}