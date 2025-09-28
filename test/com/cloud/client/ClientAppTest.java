package com.cloud.client;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

class ClientAppTest {

	@Test
	void testConnection() throws UnknownHostException, InterruptedException, IOException {
		 Thread serverThread = new Thread(() -> {
	            try (ServerSocket serverSocket = new ServerSocket(9091)) {
	                Socket socket = serverSocket.accept();
	                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	                String message = in.readLine();
	                out.println("Echo: " + message);
	                socket.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        });
	        serverThread.start();

	        // Give the server a moment to start
	        Thread.sleep(200);

	        // Now act as the client connecting to the mock server
	        try (Socket socket = new Socket("localhost", 9091);
	             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

	            out.println("TestMessage");
	            String response = in.readLine();

	            assertTrue(response.contains("TestMessage"));
	        }
	    }
	}
