package slayer.api;


import org.osbot.script.Script;

public abstract class Node {

	public Script script;

	public Node(Script script) {
		this.script = script;

	}

	public abstract boolean validate() throws InterruptedException;

	public abstract void execute() throws InterruptedException;

}