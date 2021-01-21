package com.kamabod.tech;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * This thread reads info from the user and sends it then to the server. It runs
 * an infinite loop till a user leaves.
 *
 * @author Kama
 */
public class WritingThread extends Thread {
	private PrintWriter writer;
	private Socket socket;
	private ChatClient client;

	/**
	 * Class parameterized constructor
	 * 
	 * @param Socket socket, ChatClient client
	 */
	public WritingThread(Socket socket, ChatClient client) {
		this.socket = socket;
		this.client = client;

		try {
			OutputStream outputStream = socket.getOutputStream();
			writer = new PrintWriter(outputStream, true);
		} catch (IOException e) {
			System.out.println("OutputStream Error.");
		}
	}

	/**
	 * Executes the thread. Uses Scanner to scan user input. Prompts a user to type
	 * their name. Reads info from user and sends it to server. Uses sconfig object
	 * to get default values.
	 */
	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in);
		SharedConfig sconfig = new SharedConfig();

		System.out.println("\nWelcome to chat. \nPlease enter your name: ");
		String userName = scanner.nextLine();

		client.setAUserName(userName);
		writer.println("[" + userName + "] :");
		String userMessage;

		do {
			userMessage = scanner.nextLine();
			writer.println(userMessage);
		} while (!userMessage.equals(sconfig.getQuitCommand()));

		try {
			socket.close();
			scanner.close();
			client.setDisconnected(true);
			System.out.println("Goodbye " + userName);
		} catch (IOException e) {
			System.out.println("Cannot Write To Server.");
		}
	}
}