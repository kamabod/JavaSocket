package com.kamabod.tech;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This thread deals with connection for each client connected, thus the server
 * can handle many clients simultaneously.
 *
 * @author Kama
 */
public class UserHandlingThread extends Thread {

	private Socket socket;
	private ChatServer server;
	private PrintWriter pwriter;

	/**
	 * Class parameterized constructor
	 * 
	 * @param Socket socket, ChatServer server
	 */
	public UserHandlingThread(Socket socket, ChatServer server) {
		this.socket = socket;
		this.server = server;
	}

	/**
	 * Executes the thread. Reads messages from a client and sends them to other
	 * clients. Informs users when a new user is connected. Uses sconfig object to
	 * get default values.
	 */
	@Override
	public void run() {

		try {
			InputStream inputStream = socket.getInputStream();
			BufferedReader breader = new BufferedReader(new InputStreamReader(inputStream));

			OutputStream outputStream = socket.getOutputStream();
			pwriter = new PrintWriter(outputStream, true);
			printAllUsers();

			String userName = breader.readLine();
			server.addAUserName(userName);

			String serverMessage = "New user connected:" + userName;
			server.deliverMessage(serverMessage, this);

			String clientMessage;

			SharedConfig sconfig = new SharedConfig();

			do {
				clientMessage = breader.readLine();
				serverMessage = userName + clientMessage;
				if (!clientMessage.equals(sconfig.getQuitCommand())) {
					server.deliverMessage(serverMessage, this);
				}
			} while (clientMessage != null && !clientMessage.equals(sconfig.getQuitCommand()));

			serverMessage = userName + "has left.";
			server.deliverMessage(serverMessage, this);

			server.removeAuser(userName, this);
			socket.close();
			breader.close();

		} catch (IOException e) {
			System.out.println("Client Connection Error.");
		}
	}

	/**
	 * Informs a new user about a currently connected users
	 */
	public void printAllUsers() {
		if (server.containsUsers()) {
			pwriter.println("Users currently connected:" + server.getUserNames());
		} else {
			pwriter.println("No other users connected.");
		}
	}

	/**
	 * Sends a message to a client
	 * 
	 * @param String message
	 */
	public void sendMessage(String message) {
		pwriter.println(message);
	}
}
