package org.goldengates.buildascript.api;

import java.awt.Color;

import org.goldengates.buildascript.data.WorkshopData;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;

public class Utilities {

	public static Color setFontColour(String fontColour) {
		if (fontColour.equalsIgnoreCase("black")) {
			return Color.black;
		}
		if (fontColour.equalsIgnoreCase("blue")) {
			return Color.blue;
		}
		if (fontColour.equalsIgnoreCase("cyan")) {
			return Color.cyan;
		}
		if (fontColour.equalsIgnoreCase("gray")) {
			return Color.gray;
		}
		if (fontColour.equalsIgnoreCase("green")) {
			return Color.green;
		}
		if (fontColour.equalsIgnoreCase("magenta")) {
			return Color.magenta;
		}
		if (fontColour.equalsIgnoreCase("orange")) {
			return Color.orange;
		}
		if (fontColour.equalsIgnoreCase("pink")) {
			return Color.pink;
		}
		if (fontColour.equalsIgnoreCase("red")) {
			return Color.red;
		}
		if (fontColour.equalsIgnoreCase("white")) {
			return Color.white;
		}
		if (fontColour.equalsIgnoreCase("yellow")) {
			return Color.yellow;
		}
		return Color.black;
	}

	public static Color setPaintColour(String paintColour) {

		if (paintColour.equalsIgnoreCase("black")) {
			return new Color(Color.black.getRed(), Color.black.getGreen(), Color.black.getBlue(), 70);
		}
		if (paintColour.equalsIgnoreCase("blue")) {
			return new Color(Color.blue.getRed(), Color.blue.getGreen(), Color.blue.getBlue(), 70);
		}
		if (paintColour.equalsIgnoreCase("cyan")) {
			return new Color(Color.cyan.getRed(), Color.cyan.getGreen(), Color.cyan.getBlue(), 70);
		}
		if (paintColour.equalsIgnoreCase("gray")) {
			return new Color(Color.gray.getRed(), Color.gray.getGreen(), Color.gray.getBlue(), 70);
		}
		if (paintColour.equalsIgnoreCase("green")) {
			return new Color(Color.green.getRed(), Color.green.getGreen(), Color.green.getBlue(), 70);
		}
		if (paintColour.equalsIgnoreCase("magenta")) {
			return new Color(Color.magenta.getRed(), Color.magenta.getGreen(), Color.magenta.getBlue(), 70);
		}
		if (paintColour.equalsIgnoreCase("orange")) {
			return new Color(Color.orange.getRed(), Color.orange.getGreen(), Color.orange.getBlue(), 70);
		}
		if (paintColour.equalsIgnoreCase("pink")) {
			return new Color(Color.pink.getRed(), Color.pink.getGreen(), Color.pink.getBlue(), 70);
		}
		if (paintColour.equalsIgnoreCase("red")) {
			return new Color(Color.red.getRed(), Color.red.getGreen(), Color.red.getBlue(), 70);
		}
		if (paintColour.equalsIgnoreCase("white")) {
			return new Color(Color.white.getRed(), Color.white.getGreen(), Color.white.getBlue(), 70);
		}
		if (paintColour.equalsIgnoreCase("yellow")) {
			return new Color(Color.yellow.getRed(), Color.yellow.getGreen(), Color.yellow.getBlue(), 70);
		}
		return new Color(Color.black.getRed(), Color.black.getGreen(), Color.black.getBlue(), 70);
	}

	public static Tab getTabForValue(int i) throws InterruptedException {
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

	public static Skill getSkillForString(String name) {
		for (Skill s : Skill.values()) {
			if (s.name().equalsIgnoreCase(name)) {
				WorkshopData.skillChanged = 1;
				return s;
			}
		}
		return null;
	}

}
