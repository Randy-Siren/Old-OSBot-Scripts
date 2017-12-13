package org.goldengates.bluescales.api;

import org.osbot.rs07.script.Script;

public abstract class Node {

	public Script s;

	public Node(Script s) {
		this.s = s;
	}
	
	public abstract boolean validate() throws InterruptedException;

	public abstract void execute() throws InterruptedException;

}
