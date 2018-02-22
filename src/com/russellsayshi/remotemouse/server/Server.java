package com.russellsayshi.remotemouse.server;

import java.io.*;
import java.net.*;
import java.awt.Robot;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.MouseInfo;
import java.awt.AWTException;
import java.awt.Point;
import com.russellsayshi.remotemouse.lib.*;

/**
 * Creates and run events for a TCP Server.
 *
 * @author Russell Coleman
 * @version 1.0
 */
public class Server {
	private int port;
	private Robot robot;
	private Dimension screenSize;
	private static Event[] events = Event.values();

	/**
	 * Entry point. Run this to start a server and leave
	 * it running in the background.
	 *
	 * @param args Unused.
	 */
	public static void main(String[] args) throws AWTException {
		(new Server(Constants.PORT)).listen();
	}

	/**
	 * Creates a server with a certain port.
	 *
	 * @param port Port.
	 */
	public Server(int port) throws AWTException {
		this.port = port;
		robot = new Robot();
		robot.setAutoDelay(0);
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	}

	/**
	 * Handles one specific request from the client
	 * and allows it to read additional data from input
	 * stream if necessary by dispatching it to the
	 * appropriate methid.
	 *
	 * @param event The event read from the client
	 * @param in The stream used to read more data
	 */
	private void performOperation(Event event, DataInputStream in) throws IOException {
		int mouseDeltaX, mouseDeltaY;
		int button;
		int mouseWheelDelta;
		switch(event) {
			case MOUSE_MOVE:
				mouseDeltaX = in.readInt();
				mouseDeltaY = in.readInt();
				Point p = MouseInfo.getPointerInfo().getLocation();
				p.x += mouseDeltaX;
				p.y += mouseDeltaY;
				if(p.x < 0) p.x = 0;
				if(p.y < 0) p.y = 0;
				if(p.x >= screenSize.getWidth()) p.x = (int)screenSize.getWidth()-1;
				if(p.y >= screenSize.getHeight()) p.y = (int)screenSize.getHeight()-1;
				robot.mouseMove(p.x, p.y);
				break;
			case MOUSE_PRESS:
				button = in.readInt();
				robot.mousePress(button);
				break;
			case MOUSE_RELEASE:
				button = in.readInt();
				robot.mouseRelease(button);
				break;
			case KEY_PRESS:
				button = in.readInt();
				robot.keyPress(button);
				break;
			case KEY_RELEASE:
				button = in.readInt();
				robot.keyRelease(button);
				break;
			case MOUSE_WHEEL:
				mouseWheelDelta = in.readInt();
				robot.mouseWheel(mouseWheelDelta);
				break;
			default:
				//Something has gone horribly wrong! This should match everything int he enum.
				System.err.println("Unhandled event: " + event);
				break;
		}
	}

	/**
	 * Handles a client and then exits.
	 * Throws IOException if something unexpected happens.
	 */
	private void handleClient(Socket client) throws IOException {
		DataInputStream in = new DataInputStream(client.getInputStream());
		while(true) {
			//Read in what the client wants us to do
			int operation = in.readInt();
			if(operation < 0 || operation >= events.length) {
				//The operation is invalid. Just log it and ignore.
				System.err.println("Invalid operation: " + operation);
				continue;
			}
			//Determine the operation
			Event event = events[operation];

			//Determine if the client is trying to
			//end the session
			if(event == Event.STOP) {
				break;
			}

			//Perform the operation
			performOperation(event, in);
		}
	}

	/**
	 * Starts the server listening.
	 * Will handle as many clients as necessary.
	 * Does not return unless there is an exception.
	 */
	public void listen() {
		try(ServerSocket server = new ServerSocket(port)) {
			while(true) {
				try(Socket client = server.accept()) {
					handleClient(client);
				} catch(IOException|IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
