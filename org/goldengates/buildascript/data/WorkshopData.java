package org.goldengates.buildascript.data;

import java.util.ArrayList;

import org.goldengates.buildascript.workshop.Action;
import org.osbot.rs07.api.ui.Skill;

public class WorkshopData {

	public static ArrayList<String> values = new ArrayList<String>();
	public static boolean guiWait = true;
	public static ArrayList<Action> actions = new ArrayList<Action>();

	// Paint Variables
	public static String scriptName = "Build-a-Script Workshop";
	public static String author = "GoldenGates";
	public static long startTime = 0;
	public static String paintColour = "white";
	public static int paintChanged = 1;
	public static String fontColour = "black";
	public static int fontChanged = 1;
	public static boolean paintIDs = true;

	// Misc
	public static Skill skillToTrack = null;
	public static int skillChanged = 0;

}
