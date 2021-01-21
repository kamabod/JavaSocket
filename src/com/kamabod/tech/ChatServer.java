package com.kamabod.tech;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * Server is implemented using 2 classes ChatServer as well as
 * UserHandlingThread. This is the chatServer program. It starts the server and
 * is listening on a specific port. When a new client connects, it creates an
 * instance of UserHandlingThread for that client. The server is capable of
 * handling many clients simultaneously.
 *
 * @author Kama
 */
public class ChatServer {

	private int port;
	private Set<String> userNamesSet = new HashSet<String>();
	private Set<UserHandlingThread> userThreadsSet = new HashSet<UserHandlingThread>();

	/**
	 * Class parameterized constructor
	 * 
	 * @param int port
	 */
	public ChatServer(int port) {
		this.port = port;
	}

	/**
	 * Executes the server connection. Starts the server, listening on a particular
	 * port. After a client is connected, it creates an instance of a
	 * UserHandlingThread for that client. Can handle many clients.
	 * 
	 */
	public void process() {

		try (ServerSocket ssocket = new ServerSocket(port)) {
			System.out.println("ChatServer is listening on port" + " " + port);

			while (true) {
				Socket socket = ssocket.accept();
				System.out.println("New user has connected.");

				UserHandlingThread newUser = new UserHandlingThread(socket, this);
				userThreadsSet.add(newUser);
				newUser.start();
			}

		} catch (IOException e) {
			System.out.println("Server Error.");
		}
	}

	/**
	 * Checks for args. If no args provided, uses default values. Creates ChatServer
	 * object and accesses the object method process
	 * 
	 * @param String [] args
	 */
	public static void main(String[] args) {

		SharedConfig sconfig = new SharedConfig();
		int defaultport = sconfig.getDefaultPort();
		int port = 0;

		if (args.length == 0) {
			port = defaultport;
		} else {
			port = Integer.parseInt(args[0]);
		}

		System.out.println("Server port number is: " + port);

		ChatServer server = new ChatServer(port);
		server.process();
	}

	/**
	 * Broadcasts a message from one user to others
	 * 
	 * @param String message, UserHandlingThread excludeUser
	 * 
	 */
	public void deliverMessage(String message, UserHandlingThread excludeUser) {

		for (UserHandlingThread aUser : userThreadsSet) {
			if (aUser != excludeUser) {
				aUser.sendMessage(message);
			}
		}
	}

	/**
	 * Stores a userName of the recently connected client.
	 * 
	 * @param String userName
	 */
	public void addAUserName(String userName) {
		userNamesSet.add(userName);
	}

	/**
	 * When a client leaves, removes their userName together with
	 * UserhHandlingThread
	 * 
	 * @param String userName, UserHandlingThread aUser
	 */
	public void removeAuser(String userName, UserHandlingThread aUser) {
		boolean isRemoved = userNamesSet.remove(userName);
		if (isRemoved) {
			userThreadsSet.remove(aUser);
			System.out.println("The user" + userName + "has left.");
		}
	}

	/**
	 * Gets user names
	 */
	Set<String> getUserNames() {
		return this.userNamesSet;
	}

	/**
	 * Checks if has users, returns true if some users are connected
	 */
	boolean containsUsers() {
		return !this.userNamesSet.isEmpty();
	}
}
