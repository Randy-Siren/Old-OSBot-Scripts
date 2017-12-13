package slayer.misc;

import org.osbot.script.Script;

public abstract class ComplexNode {

	public Script script;

	public ComplexNode(Script script) {
		this.script = script;
	}

	public abstract boolean activateNode() throws InterruptedException;

	public abstract void executeNode() throws InterruptedException;

	public abstract boolean condition() throws InterruptedException;

	public abstract void run() throws InterruptedException;

}
