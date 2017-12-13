package slayer.data;

import org.osbot.script.rs2.map.Position;
import org.osbot.script.rs2.utility.Area;

public class MiscVar {

	public static String[] turaelNPCList = { "Banshee", "Bat", "Bird", "Bear",
			"Cave Bug", "Cave Slime", "Cow", "Crawling Hand", "Desert Lizard",
			"Dog", "Dwarf", "Ghost", "Goblin", "Icefiend", "Minotaur",
			"Monkey", "Scorpion", "Skeleton", "Spider", "Wolf", "Zombie" };
	public static String[] mazchnaNPCList = {/* TODO */};
	public static String[] vannakaNPCList = {/* TODO */};
	public static String[] chaeldarNPCList = {/* TODO */};
	public static String[] duradelNPCList = {/* TODO */};

	public static final int ENCHANTED_GEM_ID = 4155; // Action = "Activate"

	/** Turael Vars **/
	// Paths
	public static final Position[] STAIRS_TO_TURAEL = {
			new Position(2899, 3562, 0), new Position(2899, 3550, 0),
			new Position(2913, 3545, 0), new Position(2923, 3540, 0),
			new Position(2930, 3536, 0) };
	// Areas
	public static final Area TURAEL_AREA = new Area(2928, 3534, 2931, 3537);
}
