package com.russellsayshi.remotemouse.lib;

/**
 * Holds some constant values shared between Client and Server.
 *
 * @author Russell Coleman
 * @version 1.0
*/
public final class Constants {
	/**
	 * This class is not supposed to be instantiated so it'll complain.
	 */
	private Constants() {
		throw new IllegalStateException("Don't instantiate me.");
	}

	/**
	 * Magic number. Change to whatever you want.
	 */
	public static final int PORT = 8023;
}
