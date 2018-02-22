package com.russellsayshi.remotemouse.client;

import java.io.IOException;

/**
 * Interface that the GUI uses to access the client to send
 * data to the server.
 *
 * @author Russell Coleman
 * @version 1.0
 */
public interface InputReceiver {
	/**
	 * Send a key press event to the server.
	 *
	 * @param key The key pressed
	 */
	void keyPress(int key) throws IOException;

	/**
	 * Send a key release event to the server.
	 *
	 * @param key The key released
	 */
	void keyRelease(int key) throws IOException;

	/**
	 * Send a mouse move event to the server.
	 *
	 * @param dx The delta x
	 * @param dy The delta y 
	 */
	void mouseMove(int dx, int dy) throws IOException;

	/**
	 * Send a mouse press event to the server.
	 *
	 * @param button The button pressed
	 */
	void mousePress(int button) throws IOException;

	/**
	 * Send a mouse release event to the server.
	 *
	 * @param button The button released
	 */
	void mouseRelease(int button) throws IOException;

	/**
	 * Send a mouse wheel event to the server.
	 *
	 * @param scroll The amount scrolled
	 */
	void mouseWheel(int scroll) throws IOException;
}
