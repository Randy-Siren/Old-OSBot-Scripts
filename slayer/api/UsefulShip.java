package slayer.api;

import org.osbot.script.Script;
import org.osbot.script.rs2.ui.Tab;

public class UsefulShip {

	public static Tab getTabForValue(Script s, int i)
			throws InterruptedException {
		if (i == 0)
			return (Tab.ATTACK);
		if (i == 1)
			return (Tab.SKILLS);
		if (i == 2)
			return (Tab.QUEST);
		if (i == 3)
			return (Tab.INVENTORY);
		if (i == 4)
			return (Tab.EQUIPMENT);
		if (i == 5)
			return (Tab.PRAYER);
		if (i == 6)
			return (Tab.MAGIC);
		if (i == 7)
			return (Tab.CLANCHAT);
		if (i == 8)
			return (Tab.FRIENDS);
		if (i == 9)
			return (Tab.IGNORES);
		if (i == 10)
			return (Tab.LOGOUT);
		if (i == 11)
			return (Tab.SETTINGS);
		if (i == 12)
			return (Tab.EMOTES);
		if (i == 13)
			return (Tab.MUSIC);
		return null;
	}

	public static String tabToString(int i) {
		if (i == 0)
			return new String("ATTACK");
		if (i == 1)
			return new String("SKILLS");
		if (i == 2)
			return new String("QUEST");
		if (i == 3)
			return new String("INVENTORY");
		if (i == 4)
			return new String("EQUIPMENT");
		if (i == 5)
			return new String("PRAYER");
		if (i == 6)
			return new String("MAGIC");
		if (i == 7)
			return new String("CLANCHAT");
		if (i == 8)
			return new String("FRIENDS");
		if (i == 9)
			return new String("IGNORES");
		if (i == 10)
			return new String("LOGOUT");
		if (i == 11)
			return new String("SETTINGS");
		if (i == 12)
			return new String("EMOTES");
		if (i == 13)
			return new String("MUSIC");
		return null;
	}

}
