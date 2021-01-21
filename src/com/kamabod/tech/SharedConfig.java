package com.kamabod.tech;

/**
 * This is an auxiliary class used by both server side and client side. It
 * stores shared parameters. This class was created to avoid hard-coding and
 * make the code more flexible.
 *
 * @author Kama
 */
public class SharedConfig {

	private final String defaultHostname = "localhost";
	private final String quitCommand = "\\q";
	private final int defaultPort = 3333;

	/**
	 * Gets defaultPort
	 * 
	 * @return defaultPort
	 */
	public int getDefaultPort() {
		return defaultPort;
	}

	/**
	 * Gets defaultHostname
	 * 
	 * @return defaultHostname;
	 */
	public String getDefaultHostname() {
		return defaultHostname;
	}

	/**
	 * Gets quitCommand. Once a user types \q he terminates the program.
	 * 
	 * @return quitCommand;
	 */
	public String getQuitCommand() {
		return quitCommand;
	}
}
