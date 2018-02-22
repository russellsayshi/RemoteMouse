package com.russellsayshi.remotemouse.lib;

/**
 * Possible events that can be sent from a client to server.
 * WARNING: Order matters because I'm serializing and decoding these
 * enums as ints to avoid writing lots of repetitive boilerplate.
 * If you change the ordering or add a new value, ensure it is
 * reflected identically across <b>both</b> the client and server
 * before starting either.
 *
 * @author Russell Coleman
 * @version 1.0
 */
public enum Event {
	KEY_PRESS,
	KEY_RELEASE,
	MOUSE_MOVE,
	MOUSE_PRESS,
	MOUSE_RELEASE,
	MOUSE_WHEEL,
	STOP
}
