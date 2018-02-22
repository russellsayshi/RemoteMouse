package com.russellsayshi.remotemouse.client;

import java.io.*;
import java.net.*;
import java.util.*;
import com.russellsayshi.remotemouse.lib.*;

/**
 * Connects to TCP RemoteMouse server and feeds inputs from the ClientInput
 * class into here.
 *
 * @author Russell Coleman
 * @version 1.0
 */
public class Client implements InputReceiver {
	private DataOutputStream out;

	/**
	 * Entry point of client application.
	 * Creates a client class and starts.
	 *
	 * @param args Unused.
	 */
	public static void main(String[] args) {
		ClientInput input = new ClientInput();
		String host = input.getHostnameOfServer();
		Client client = new Client(host);
		input.setReceiver(client);
		input.getInput();
	}

	/**
	 * Writes an Event enum to the server.
	 *
	 * @param event The event to write.
	 */
	private void writeEvent(Event e) throws IOException {
		out.writeInt(e.ordinal()); //yeah I know
	}

	/**
	 * Sends to server key press event.
	 *
	 * @param key The key pressed.
	 */
	@Override
	public void keyPress(int key) throws IOException {
		writeEvent(Event.KEY_PRESS);
		out.writeInt(key);
	}

	/**
	 * Sends to server key release event.
	 *
	 * @param key The key released.
	 */
	@Override
	public void keyRelease(int key) throws IOException {
		writeEvent(Event.KEY_RELEASE);
		out.writeInt(key);
	}

	/**
	 * Sends to server mouse move event. Delta x and y.
	 *
	 * @param dx Delta x
	 * @param dy Delta y
	 */
	@Override
	public void mouseMove(int dx, int dy) {
		writeEvent(Event.MOUSE_MOVE);
		out.writeInt(dx);
		out.writeInt(dy);
	}

	/**
	 * Sends to server mouse press event.
	 *
	 * @param button Button pressed
	 */
	@Override
	public void mousePress(int button) {
		writeEvent(Event.MOUSE_PRESS);
		out.writeInt(button);
	}

	/**
	 * Sends to server mouse release event.
	 *
	 * @param button Button released
	 */
	@Override
	public void mouseRelease(int button) {
		writeEvent(Event.MOUSE_RELEASE);
		out.writeInt(button);
	}

	/**
	 * Sends to server mouse wheel event.
	 *
	 * @param scroll Amount scrolled
	 */
	@Override
	public void mouseWheel(int scroll) {
		writeEvent(Event.MOUSE_WHEEL);
		out.writeInt(scroll);
	}

}
