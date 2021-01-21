package com.kamabod.tech;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Reads info from the server and shows it on the console in an infinite loop
 * till a client leaves
 *
 * @author Kama
 */
public class ReadingThread extends Thread {
	private BufferedReader breader;
	private Socket socket;
	private ChatClient client;

	/**
	 * Class parameterized constructor
	 * 
	 * @param Socket socket, ChatClient client
	 */
	public ReadingThread(Socket socket, ChatClient client) {
		this.socket = socket;
		this.client = client;

		try {
			InputStream inputStream = this.socket.getInputStream();
			breader = new BufferedReader(new InputStreamReader(inputStream));
		} catch (IOException e) {
			System.out.println("Input Stream Error.");
		}
	}

	/**
	 * Runs the thread. Displays messages on the console in an infinite loop until a
	 * client disconnects
	 */
	@Override
	public void run() {
		while (true) {
			try {
				String reply = breader.readLine();
				System.out.println("\n" + reply);

				if (client.getAUserName() != null) {
					System.out.print("[" + client.getAUserName() + "]: ");
				}
			} catch (IOException e) {

				if (client.getDisconnected() != true) {
					System.out.println("Error Reading Data From Server.");
				}
				break;
			}
		}
	}
}
