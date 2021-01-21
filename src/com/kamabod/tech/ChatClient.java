package com.kamabod.tech;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Client is implemented using 3 classes: ChatClient, ReadingThread and
 * WritingThread. Starts client side program. Connects to server using hostname
 * and port number. When the connection is established it creates 2 threads and
 * starts them.
 *
 * @author Kama
 */
public class ChatClient {

	private String hostname;
	private int port;
	private String userName;
	private boolean disconnected;

	/**
	 * Class parameterized constructor
	 * 
	 * @param String hostname, int port
	 */
	public ChatClient(String hostname, int port) {
		this.port = port;
		this.hostname = hostname;
	}

	/**
	 * Executes the client connection. Creates and starts 2 threads.
	 * 
	 */
	public void process() {

		try {
			Socket socket = new Socket(hostname, port);
			System.out.println("Connected to ChatServer.");

			new ReadingThread(socket, this).start();
			new WritingThread(socket, this).start();

		} catch (UnknownHostException e) {
			System.out.println("Server Could Not Be Found.");
		} catch (IOException e) {
			System.out.println("I/O Device Error.");
		}
	}

	/**
	 * Gets user name
	 * 
	 * @param String userName
	 */
	public String getAUserName() {
		return this.userName;
	}

	/**
	 * Sets user name
	 * 
	 * @param String userName
	 */
	public void setAUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets boolean disconnected. Checks if the user is disconnected
	 */
	public boolean getDisconnected() {
		return this.disconnected;
	}

	/**
	 * Sets boolean disconnected
	 * 
	 * @param boolean isDisconnected
	 */
	public void setDisconnected(boolean isDisconnected) {
		this.disconnected = isDisconnected;
	}

	/**
	 * Creates SharedConfig object. Checks args. If no args provided, uses default
	 * values. If args exist, assigns them to local variables. Creates client object
	 * and accesses process method of that object.
	 * 
	 * @param String[] args
	 */
	public static void main(String[] args) {

		SharedConfig sconfig = new SharedConfig();
		int defaultport = sconfig.getDefaultPort();
		String defaulthostname = sconfig.getDefaultHostname();
		int port = 0;
		String hostname = "";

		if (args.length == 0) {
			hostname = defaulthostname;
			port = defaultport;
		} else {
			hostname = args[0];
			port = Integer.parseInt(args[1]);
		}

		ChatClient client = new ChatClient(hostname, port);
		client.process();
	}
}
