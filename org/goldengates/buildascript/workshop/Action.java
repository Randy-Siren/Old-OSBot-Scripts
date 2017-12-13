package org.goldengates.buildascript.workshop;

import javax.swing.JOptionPane;

import org.goldengates.buildascript.BuildAScript;
import org.goldengates.buildascript.api.Utilities;
import org.osbot.rs07.api.Bank.BankMode;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.*;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

public class Action {

	int aType;
	int[] intArray = new int[5];
	String[] stringArray = new String[5];
	int y = -10;
	NPC npc;
	RS2Object object;
	GroundItem groundItem;
	Area area;
	Position position;

	@SuppressWarnings("unchecked")
	public void runAction(Script s) throws InterruptedException {
		switch (aType) {
		case 2:
			while (!s.getBank().isOpen()) {
				object = s.getObjects().closestThatContains("Bank Booth");
				npc = s.getNpcs().closestThatContains("Banker");
				s.log("We in here!");
				if (object != null || npc != null) {
					s.getBank().open();
					MethodProvider.sleep(500);
				}
			}
			break;

		case 3:
			while (s.getBank().isOpen()) {
				MethodProvider.sleep(250);
				s.getBank().close();
			}
			break;

		case 4:
			while (s.getBank().isOpen() && s.getInventory().contains(intArray)) {
				s.getBank().depositAll(intArray);
				MethodProvider.sleep(500);
			}
			break;

		case 5:
			while (s.getBank().isOpen() && !s.getInventory().isEmptyExcept(intArray)) {
				s.getBank().depositAllExcept(intArray);
				MethodProvider.sleep(500);
			}

		case 6:
			if (s.getBank().isOpen() && !s.getInventory().isFull()) {
				s.getBank().withdraw(intArray[0], intArray[1]);
				MethodProvider.sleep(500);
			}
			break;

		case 7:
			groundItem = s.getGroundItems().closest(intArray);
			if (groundItem != null) {
				s.getCamera().toEntity(groundItem);
				groundItem.interact(stringArray);
				MethodProvider.sleep(500);
			}
			break;

		case 8:
			if (s.getInventory().contains(intArray)) {
				s.getInventory().drop(intArray);
				MethodProvider.sleep(250);
			}
			break;

		case 9:
			while (s.getInventory().contains(intArray)) {
				s.getInventory().dropAll(intArray);
				MethodProvider.sleep(500);
			}
			break;

		case 10:
			while (!s.getInventory().isEmpty()) {
				s.getInventory().dropAll();
				MethodProvider.sleep(500);
			}
			break;

		case 11:
			if (s.getInventory().contains(intArray)) {
				s.getInventory().interact(stringArray[0], intArray);
				MethodProvider.sleep(250);
			}
			break;

		case 12:
			if (s.getInventory().contains(intArray)) {
				s.getInventory().interact("Use", intArray);
				MethodProvider.sleep(250);
			}
			break;

		case 13:
			if (s.getInventory().contains(intArray[0]) && s.getInventory().contains(intArray[1])) {
				s.getInventory().interact("Use", intArray[0]);
				MethodProvider.sleep(500);
				s.getInventory().interact("Use", intArray[1]);
				MethodProvider.sleep(500);
			}
			break;

		case 14:
			npc = s.getNpcs().closest(intArray[1]);
			if (npc != null && s.getInventory().contains(intArray[0])) {
				s.getInventory().interact("Use", intArray[0]);
				MethodProvider.sleep(500);
				npc.interact();
				MethodProvider.sleep(500);
			}
			break;

		case 15:
			object = s.getObjects().closest(intArray[1]);
			if (object != null && s.getInventory().contains(intArray[0])) {
				s.getInventory().interact("Use", intArray[0]);
				MethodProvider.sleep(500);
				object.interact();
				MethodProvider.sleep(500);
			}
			break;

		case 16:
			s.getKeyboard().typeString(stringArray[0], false);
			break;

		case 17:
			s.getKeyboard().typeString("", true);

		case 18:
			s.getMouse().click(false);
			MethodProvider.sleep(250);
			break;

		case 19:
			s.getMouse().click(true);
			MethodProvider.sleep(250);
			break;

		case 20:
			s.getMouse().move(intArray[0], intArray[1]);
			MethodProvider.sleep(250);
			break;

		case 21:
			npc = s.getNpcs().closest(intArray);
			if (npc != null) {
				s.getCamera().toEntity(npc);
				if (npc.getPosition().distance(s.myPosition()) > 8) {
					s.getWalking().walk(npc);
				} else {
					npc.interact(stringArray);
					MethodProvider.sleep(500);
				}
			}
			break;

		case 22:
			npc = s.getNpcs().closest(new Filter<NPC>() {
				@Override
				public boolean match(NPC arg0) {
					return arg0.isAttackable();
				}
			});
			if (!s.myPlayer().isUnderAttack() && npc != null && !npc.isUnderAttack()) {
				s.getCamera().toEntity(npc);
				npc.interact(stringArray);
				MethodProvider.sleep(500);
			}
			break;

		case 23:
			object = s.getObjects().closest(intArray);
			if (object != null) {
				s.getCamera().toEntity(object);
				object.interact(stringArray);
			}
			break;

		case 24:
			s.getTabs().open(Utilities.getTabForValue(intArray[0]));
			break;

		case 25:
			if (s.getTabs().getOpen() != Tab.MAGIC) {
				s.getTabs().open(Tab.MAGIC);
			}
			s.getMouse().move(intArray[0], intArray[1]);
			MethodProvider.sleep(500);
			s.getMouse().click(false);
			break;

		case 26:
			npc = s.getNpcs().closest(intArray[2]);
			if (s.getTabs().getOpen() != Tab.MAGIC) {
				s.getTabs().open(Tab.MAGIC);
			}
			s.getMouse().move(intArray[0], intArray[1]);
			MethodProvider.sleep(500);
			s.getMouse().click(false);
			MethodProvider.sleep(500);
			npc.interact(stringArray);
			MethodProvider.sleep(500);
			break;

		case 27:
			object = s.getObjects().closest(intArray[2]);
			if (s.getTabs().getOpen() != Tab.MAGIC) {
				s.getTabs().open(Tab.MAGIC);
			}
			s.getMouse().move(intArray[0], intArray[1]);
			MethodProvider.sleep(500);
			s.getMouse().click(false);
			MethodProvider.sleep(500);
			object.interact(stringArray);
			MethodProvider.sleep(500);
			break;

		case 28:
			while (!s.getInventory().isEmpty() && s.getBank().isOpen()) {
				s.getBank().depositAll();
				MethodProvider.sleep(500);
			}
			break;

		case 29:
			area = new Area(intArray[0], intArray[1], intArray[2], intArray[3]);
			if (!area.contains(s.myPosition()))
				return;
			y = intArray[4];
			if (y != -1) {
				BuildAScript.settings.line = y;
			}
			break;

		case 30:
			area = new Area(intArray[0], intArray[1], intArray[2], intArray[3]);
			if (area.contains(s.myPosition()))
				return;
			y = intArray[4];
			if (y != -1) {
				BuildAScript.settings.line = y;
			}
			break;

		case 31:
			if (!s.myPlayer().isUnderAttack())
				return;
			y = intArray[0];
			if (y != -1)
				BuildAScript.settings.line = y;
			break;

		case 32:
			if (s.myPlayer().isUnderAttack())
				return;
			y = intArray[0];
			if (y != -1)
				BuildAScript.settings.line = y;
			break;

		case 33:
			npc = s.getNpcs().closest(intArray);
			if (npc == null)
				return;
			y = intArray[4];
			if (y != -1)
				BuildAScript.settings.line = y;
			break;

		case 34:
			if (!s.getInventory().isFull())
				return;
			y = intArray[0];
			if (y != -1)
				BuildAScript.settings.line = y;
			break;

		case 35:
			if (s.getInventory().isFull())
				return;
			y = intArray[0];
			if (y != -1)
				BuildAScript.settings.line = y;
			break;

		case 36:
			if (s.getSkills().getDynamic(Skill.HITPOINTS) < intArray[0])
				return;
			y = intArray[1];
			if (y != -1)
				BuildAScript.settings.line = y;
			break;

		case 37:
			if (s.getSkills().getDynamic(Skill.HITPOINTS) >= intArray[0])
				return;
			y = intArray[1];
			if (y != -1)
				BuildAScript.settings.line = y;
			break;

		case 38:
			MethodProvider.sleep(intArray[0]);
			break;

		case 39:
			/** DISCONTINUED **/
			break;

		case 40:
			int ticker = 0;
			while (ticker == 0) {
				MethodProvider.sleep(500);
				if (!s.myPlayer().isAnimating()) {
					MethodProvider.sleep(700);
					if (!s.myPlayer().isAnimating()) {
						MethodProvider.sleep(800);
						if (!s.myPlayer().isAnimating())
							ticker = 1;
					}
				}
			}
			break;
		case 41:
			MethodProvider.sleep(250);
			while (s.myPlayer().isUnderAttack()) {
				MethodProvider.sleep(250);
			}
			break;

		case 42:
			if (s.getSettings().isRunning() || s.getSettings().getRunEnergy() > intArray[0])
				return;
			MethodProvider.sleep(250);
			s.getSettings().setRunning(true);
			break;

		case 43:
			position = new Position(intArray[0], intArray[1], intArray[2]);
			if (position.distance(s.myPosition()) < 2)
				return;
			MethodProvider.sleep(250);
			s.getWalking().walk(position);
			MethodProvider.sleep(500);
			break;

		case 44:
			if (!s.getInventory().contains(intArray[0]))
				return;
			y = intArray[1];
			if (y != -1)
				BuildAScript.settings.line = y;
			break;

		case 45:
			if (s.getInventory().contains(intArray[0]))
				return;
			y = intArray[1];
			if (y != -1)
				BuildAScript.settings.line = y;
			break;

		case 46:
			if (!s.getInventory().isEmpty())
				return;
			y = intArray[0];
			if (y != -1)
				BuildAScript.settings.line = y;
			break;

		case 47:
			if (!s.getBank().isOpen())
				return;
			MethodProvider.sleep(250);
			s.getBank().enableMode(BankMode.WITHDRAW_NOTE);
			MethodProvider.sleep(500);
			break;

		case 48:
			if (!s.getBank().isOpen())
				return;
			MethodProvider.sleep(250);
			s.getBank().enableMode(BankMode.WITHDRAW_ITEM);
			MethodProvider.sleep(500);
			break;

		case 49:
			while (s.myPlayer().isAnimating())
				MethodProvider.sleep(500);
			break;

		case 50:
			MethodProvider.sleep(250);
			while (s.myPlayer().isMoving()) {
				MethodProvider.sleep(500);
			}
			break;

		case 51:
			if (s.getWorlds().getCurrentWorld() == intArray[0])
				return;
			s.getWorlds().hop(intArray[0]);
			break;

		case 52:
			npc = s.getNpcs().closest(intArray);
			if (npc != null)
				return;
			y = intArray[4];
			if (y != -1)
				BuildAScript.settings.line = y;
			break;

		case 53:
			object = s.getObjects().closest(intArray);
			if (object == null)
				return;
			y = intArray[4];
			if (y != -1)
				BuildAScript.settings.line = y;
			break;

		case 54:
			object = s.getObjects().closest(intArray);
			if (object != null)
				return;
			y = intArray[4];
			if (y != -1)
				BuildAScript.settings.line = y;
			break;

		case 55:
			groundItem = s.getGroundItems().closest(intArray);
			if (groundItem == null)
				return;
			y = intArray[4];
			if (y != -1)
				BuildAScript.settings.line = y;
			break;

		case 56:
			groundItem = s.getGroundItems().closest(intArray);
			if (groundItem != null)
				return;
			y = intArray[4];
			if (y != -1)
				BuildAScript.settings.line = y;
			break;
		}
	}

	public String createListText() {
		String s = "";
		try {
			switch (aType) {
			case 2:
				s = "Open Closest Bank";
				break;
			case 3:
				s = "Close Bank";
				break;
			case 4:
				s = "Deposit the ITEM[" + intArray[0] + "].";
				break;
			case 5:
				s = "Deposit All Except the ITEM[" + intArray[0] + "].";
				break;
			case 6:
				s = "Withdraw [" + intArray[1] + "] of the ITEM[" + intArray[0] + "].";
				break;
			case 7:
				s = "[" + stringArray[0] + "] the GROUNDITEM[" + intArray[0] + "," + intArray[1] + "," + intArray[2]
						+ "," + intArray[3] + "," + intArray[4] + "].";
				break;
			case 8:
				s = "Drop the ITEM[" + intArray[0] + "].";
				break;
			case 9:
				s = "Drop All of the ITEM[" + intArray[0] + "].";
				break;
			case 10:
				s = "Drop All.";
				break;
			case 11:
				s = "[" + stringArray[0] + "] the ITEM[" + intArray[0] + "].";
				break;
			case 12:
				s = "Use the ITEM[" + intArray[0] + "].";
				break;
			case 13:
				s = "Use the ITEM[" + intArray[0] + "] on the ITEM[" + intArray[1] + "].";
				break;
			case 14:
				s = "Use the ITEM[" + intArray[0] + "] on the NPC[" + intArray[1] + "].";
				break;
			case 15:
				s = "Use the ITEM[" + intArray[0] + "] on the OBJECT[" + intArray[1] + "].";
				break;
			case 16:
				s = "Type String [" + stringArray[0] + "].";
				break;
			case 17:
				s = "Press Enter.";
				break;
			case 18:
				s = "Press Left Mouse Button.";
				break;
			case 19:
				s = "Press Right Mouse Button.";
				break;
			case 20:
				s = "Move mouse to [" + intArray[0] + ", " + intArray[1] + "].";
				break;
			case 21:
				s = "[" + stringArray[0] + "] the NPC[" + intArray[0] + "," + intArray[1] + "," + intArray[2] + ","
						+ intArray[3] + "," + intArray[4] + "].";
				break;
			case 22:
				s = "Attack the NPC[" + +intArray[0] + "," + intArray[1] + "," + intArray[2] + "," + intArray[3] + ","
						+ intArray[4] + "].";
				break;
			case 23:
				s = "[" + stringArray[0] + "] the OBJECT[" + intArray[0] + "," + intArray[1] + "," + intArray[2] + ","
						+ intArray[3] + "," + intArray[4] + "].";
				break;
			case 24:
				s = "Open tab [" + Utilities.tabToString(intArray[0]) + "].";
				break;
			case 25:
				s = "Cast spell at location [" + intArray[0] + ", " + intArray[1] + "].";
				break;
			case 26:
				s = "Cast spell at location [" + intArray[0] + ", " + intArray[1] + "] on the NPC[" + intArray[0]
						+ "].";
				break;
			case 27:
				s = "Cast spell at location [" + intArray[0] + ", " + intArray[1] + "] on the OBJECT[" + intArray[0]
						+ "].";
				break;
			case 28:
				s = "Deposit All.";
				break;
			case 29:
				s = "Jump to line [" + intArray[4] + "] if player is in Area [" + intArray[0] + ", " + intArray[1]
						+ ", " + intArray[2] + ", " + intArray[3] + "].";
				break;
			case 30:
				s = "Jump to line [" + intArray[4] + "] if player is NOT in Area [" + intArray[0] + ", " + intArray[1]
						+ ", " + intArray[2] + ", " + intArray[3] + "].";
				break;
			case 31:
				s = "Jump to line [" + intArray[0] + "] if player is in combat.";
				break;
			case 32:
				s = "Jump to line [" + intArray[0] + "] if player is NOT in combat.";
				break;
			case 33:
				s = "Jump to line [" + intArray[4] + "] if the NPC[" + intArray[0] + "," + intArray[1] + ","
						+ intArray[2] + intArray[3] + "] exists.";
				break;
			case 34:
				s = "Jump to line [" + intArray[0] + "] if inventory is full.";
				break;
			case 35:
				s = "Jump to line [" + intArray[0] + "] if inventory is NOT full.";
				break;
			case 36:
				s = "Jump to line [" + intArray[1] + "] if health is below [" + intArray[0] + "].";
				break;
			case 37:
				s = "Jump to line [" + intArray[1] + "] if health is above [" + intArray[0] + "].";
				break;
			case 38:
				s = "Sleep for [" + intArray[0] + "] milliseconds.";
				break;
			case 39:
				s = "Wait until and interface shows up.";
				break;
			case 40:
				s = "Wait until player is not animating (long).";
				break;
			case 41:
				s = "Wait until player is not in combat.";
				break;
			case 42:
				s = "Turn run on at [" + intArray[0] + "]%.";
				break;
			case 43:
				s = "Walk to position [" + intArray[0] + ", " + intArray[1] + ", " + intArray[2] + "].";
				break;
			case 44:
				s = "Jump to line [" + intArray[1] + "] if inventory does contain the ITEM[" + intArray[0] + "].";
				break;
			case 45:
				s = "Jump to line [" + intArray[1] + "] if inventory does NOT contain the ITEM[" + intArray[0] + "].";
				break;
			case 46:
				s = "Jump to line [" + intArray[0] + "] if inventory is empty.";
				break;
			case 47:
				s = "Set bank withdraw mode to Noted.";
				break;
			case 48:
				s = "Set bank withdraw mode to Item.";
				break;
			case 49:
				s = "Wait until player is not animating (short).";
				break;
			case 50:
				s = "Wait until player is not moving.";
				break;
			case 51:
				s = "Hop to a random world (not F2P or PvP).";
				break;
			case 52:
				s = "Jump to line [" + intArray[4] + "] if the NPC[" + intArray[0] + "," + intArray[1] + ","
						+ intArray[2] + "," + intArray[3] + "] does NOT exist.";
				break;
			case 53:
				s = "Jump to line [" + intArray[4] + "] if the Object[" + intArray[0] + "," + intArray[1] + ","
						+ intArray[2] + "," + intArray[3] + "] exists.";
				break;
			case 54:
				s = "Jump to line [" + intArray[4] + "] if the Object[" + intArray[0] + "," + intArray[1] + ","
						+ intArray[2] + "," + intArray[3] + "] does NOT exist.";
				break;
			case 55:
				s = "Jump to line [" + intArray[4] + "] if the GroundItem[" + intArray[0] + "," + intArray[1] + ","
						+ intArray[2] + "," + intArray[3] + "] exists.";
				break;
			case 56:
				s = "Jump to line [" + intArray[4] + "] if the GroundItem[" + intArray[0] + "," + intArray[1] + ","
						+ intArray[2] + "," + intArray[3] + "] does NOT exist.";
				break;
			}
		} catch (Exception e) {
			JOptionPane.showInternalMessageDialog(null, "Some Error Happened Bro. :(", "Error", JOptionPane.ERROR);
			return e + "";
		}
		return s;
	}

	public int getAType() {
		return aType;
	}

	public int[] getIntArray() {
		return intArray;
	}

	public String[] getStringArray() {
		return stringArray;
	}

	public Action(Script script, int aType) {
		this.aType = aType;
	}

	public Action(Script script, int aType, int[] intArray) {
		this.aType = aType;
		this.intArray = intArray;
	}

	public Action(Script script, int aType, String[] stringArray) {
		this.aType = aType;
		this.stringArray = stringArray;
	}

	public Action(Script script, int aType, int[] intArray, String[] stringArray) {
		this.aType = aType;
		this.intArray = intArray;
		this.stringArray = stringArray;
	}

}
