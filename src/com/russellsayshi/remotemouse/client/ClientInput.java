package com.russellsayshi.remotemouse.client;

import java.util.*;
import javax.swing.*;
import java.io.*;

/**
 * This class is the GUI front end to the client class.
 * It'll read in input and send it over to Client
 * through the InputReceiver interface, which
 * then sends it to the server, but this class
 * doesn't know about that ofc.
 *
 * @author Russell Coleman
 * @version 1.0
 */
public class ClientInput {
	private static final String LAST_HOST_FILE = "last_host.txt";
	private InputReceiver receiver;
	private String hostname;
	private Thread mouseLockThread;

	/**
	 * {@code forcePrompt} defaults to false.
	 *
	 * @see ClientInput#getHostnameOfServer(boolean)
	 */
	public Optional<String> getHostnameOfServer() {
		return getHostnameOfServer(false);
	}

	/**
	 * Sets the receiver that all input events get passed to.
	 *
	 * @param receiver The new receiver.
	 */
	public void setReceiver(InputReceiver receiver) {
		this.receiver = receiver;
	}

	/**
	 * Gets input from the user and feeds it to the
	 * InputReceiver class. Shouldn't return unless something goes
	 * wrong.
	 *
	 * Throws an IllegalStateException if receiver was not set prior.
	 * 
	 * @see ClientInput#setReceiver(InputReceiver)
	 */
	public void getInput() {
		if(receiver == null) throw new IllegalStateException("Cannot get input for null receiver.");

		JFrame frame = new JFrame("ClientInput");
		frame.setSize(640, 480); //standard size
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addFocusListener(this);
		frame.add(new JLabel("Host: " + hostname));

		lockMouse();
	}

	/**
	 * Tries to determine if a hostanme is already saved
	 * and if not it'll ask the user for one.
	 *
	 * Side effect: saves hostname to file for future use.
	 *
	 * @param forcePrompt If true, it won't check storage and will always ask the user.
	 * @returns The hostname the user wants to connect to, if it can find it.
	 */
	public Optional<String> getHostnameOfServer(boolean forcePrompt) {
		if(!forcePrompt) {
			//See if we can find a stored last host.
			try (Scanner scan = new Scanner(new File(LAST_HOST_FILE))) {
				String lastHost = scan.nextLine().trim();
				if(lastHost != null && !lastHost.equals("")) {
					hostname = lastHost;
					return Optional.of(lastHost);
				}
			}
		}

		//Now see if the user wants to give us a host.
		String result = JOptionPane.showInputDialog(null, "Enter hostname or IP of server");
		if(result != null) {
			result = result.trim();
			if(!result.equals("")) {
				//We have a hostname! Let's save it if we can. If not, no harm done.
				try (PrintWriter pw = new PrintWriter(LAST_HOST_FILE)) {
					pw.println(result);
				} catch(IOException ioe) {
					ioe.printStackTrace();
				}
				hostname = result;
				return Optional.of(result);
			}
		}

		//Return nothing.
		return Optional.empty();
	}
}
