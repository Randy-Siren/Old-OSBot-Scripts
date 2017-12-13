package slayer.data.enums;

import org.osbot.script.rs2.map.Position;
import org.osbot.script.rs2.utility.Area;

public enum TuraelTaskData {

	/** Enum Values **/
	BANSHEE(1, "Banshee", new Area(0, 0, 0, 0), new Position[] {}, 1),
	BAT(2, "Bat", new Area(0, 0, 0, 0), new Position[] {}, 1),
	BIRD(3, "Bird", new Area(0, 0, 0, 0), new Position[] {}, 1),
	BEAR(4, "Bear", new Area(0, 0, 0, 0), new Position[] {}, 1),
	CAVE_BUG(5, "Cave Bug", new Area(0, 0, 0, 0), new Position[] {}, 1),
	CAVE_SLIME(6, "Cave Slime", new Area(0, 0, 0, 0), new Position[] {}, 1),
	COW(7, "Cow", new Area(0, 0, 0, 0), new Position[] {}, 1),
	CRAWLING_HAND(
			8,
			"Crawling Hand",
			new Area(0, 0, 0, 0),
			new Position[] {},
			1),
	DESERT_LIZARD(
			9,
			"Desert Lizard",
			new Area(0, 0, 0, 0),
			new Position[] {},
			1),
	DOG(10, "Dog", new Area(0, 0, 0, 0), new Position[] {}, 1),
	DWARF(11, "Dwarf", new Area(0, 0, 0, 0), new Position[] {}, 1),
	GHOST(12, "Ghost", new Area(0, 0, 0, 0), new Position[] {}, 1),
	GOBLIN(13, "Goblin", new Area(0, 0, 0, 0), new Position[] {}, 1),
	ICEFIEND(14, "Icefiend", new Area(0, 0, 0, 0), new Position[] {}, 1),
	MINOTAUR(15, "Minotaur", new Area(0, 0, 0, 0), new Position[] {}, 1),
	MONKEY(16, "Monkey", new Area(0, 0, 0, 0), new Position[] {}, 1),
	SCORPION(17, "Scorpion", new Area(0, 0, 0, 0), new Position[] {}, 1),
	SKELETON(18, "Skeleton", new Area(0, 0, 0, 0), new Position[] {}, 1),
	SPIDER(19, "Spider", new Area(0, 0, 0, 0), new Position[] {}, 1),
	WOLF(20, "Wolf", new Area(0, 0, 0, 0), new Position[] {}, 1),
	ZOMBIE(21, "Zombie", new Area(0, 0, 0, 0), new Position[] {}, 1);

	/** Constructor **/
	private TuraelTaskData(int taskID, String name, Area fightArea,
			Position[] path, int bankMethod) {
		this.taskID = taskID;
	}

	/** Data Declaration **/
	private int taskID;
	private String name;
	private Area fightArea;
	private Position[] path;
	private int bankMethod;

	/** Methods **/
	public int getTaskID() {
		return this.taskID;
	}

	public String getName() {
		return this.name;
	}

	public Area getFightArea() {
		return this.fightArea;
	}

	public Position[] getPath() {
		return this.path;
	}

	public int bankMethod() {
		return this.bankMethod;
	}

	/** Access Method **/
	public static TuraelTaskData forTaskID(int taskID) {
		for (TuraelTaskData data : values())
			if (data.taskID == taskID)
				return data;
		return null;
	}

}
