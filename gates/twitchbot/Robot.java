package gates.twitchbot;

import java.util.Arrays;

import org.osbot.script.Script;
import org.osbot.script.mouse.RectangleDestination;
import org.osbot.script.rs2.map.Position;
import org.osbot.script.rs2.model.*;

public class Robot {

	private Script s;

	public Robot(Script s) {
		this.s = s;
	}

	public void handleCommand(String c) throws InterruptedException {
		if (c != null) {
			String[] array;
			if (c.split(" ").length > 1) {
				array = c.split(" ");

				if (c.toLowerCase().contains("move")) {
					move(array);
				}
				if (c.toLowerCase().contains("interactnpc")) {
					interactNpc(array);
				}
				if (c.toLowerCase().contains("interactobj")) {
					interactObj(array);
				}
				if (c.toLowerCase().contains("interactground")) {
					interactGround(array);
				}
				if (c.toLowerCase().contains("interactitem")) {
					interactItem(array);
				}
				if (c.toLowerCase().contains("talk")) {
					talk(array);
				}
				if (c.toLowerCase().contains("useitem")) {
					useItem(array);
				}
				/*
				 * Disabled Temporarily if
				 * (c.toLowerCase().contains("dropitem")) { dropItem(array); }
				 */

				if (c.toLowerCase().contains("useitemobj")) {
					useItemObj(array);
				}
				if (c.toLowerCase().contains("useitemnpc")) {
					useItemNpc(array);
				}
				if (c.toLowerCase().contains("lclick")) {
					s.client.moveMouseTo(
							new RectangleDestination(
									Integer.parseInt(array[1]), Integer
											.parseInt(array[2]), 10, 10),
							false, true, false);
				}
				if (c.toLowerCase().contains("rclick")) {
					s.client.moveMouseTo(
							new RectangleDestination(
									Integer.parseInt(array[1]), Integer
											.parseInt(array[2]), 10, 10),
							false, true, true);
				}
			}
		}
	}

	private void move(String[] array) {
		String dir = array[2];
		try {
			if (dir.equalsIgnoreCase("n"))
				s.walk(new Position(s.myX(), s.myY()
						+ Integer.parseInt(array[1]), 0));
			if (dir.equalsIgnoreCase("e"))
				s.walk(new Position(s.myX() + Integer.parseInt(array[1]), s
						.myY(), 0));
			if (dir.equalsIgnoreCase("w"))
				s.walk(new Position(s.myX() - Integer.parseInt(array[1]), s
						.myY(), 0));
			if (dir.equalsIgnoreCase("s"))
				s.walk(new Position(s.myX(), s.myY()
						- Integer.parseInt(array[1]), 0));
		} catch (NumberFormatException | InterruptedException e) {
		}
	}

	private void interactNpc(String[] array) throws InterruptedException {
		String name = array[1].replace("_", " ");
		String action = array[2];
		NPC npc = s.closestNPCForName(name);
		if (npc != null
				&& Arrays.asList(npc.getDefinition().getActions()).contains(
						action)) {
			npc.interact(action);
		}
	}

	private void interactObj(String[] array) throws InterruptedException {
		String name = array[1].replace("_", " ");
		String action = array[2];
		RS2Object obj = s.closestObjectForName(name);
		if (obj != null) {
			obj.interact(action);
		}
	}

	private void interactGround(String[] array) throws InterruptedException {
		String name = array[1].replace("_", " ");
		String action = array[2];
		GroundItem g = s.closestGroundItemForName(name);
		if (g != null) {
			g.interact(action);
		}
	}

	private void interactItem(String[] array) throws InterruptedException {
		String name = array[1].replace("_", " ");
		String action = array[2];
		if (!action.equalsIgnoreCase("Drop"))
			s.client.getInventory().interactWithName(name, action);
	}

	private void talk(String[] array) throws InterruptedException {
		String name = array[1].replace("_", " ").replace("]", "");
		NPC npc = s.closestNPCForName(name);
		if (npc != null
				&& Arrays.asList(npc.getDefinition().getActions()).contains(
						"Talk-to")) {
			npc.interact("Talk-to");
		}
	}

	private void useItem(String[] array) throws InterruptedException {
		String item1 = array[1].replace("_", " ");
		String item2 = array[2].replace("_", " ");
		s.client.getInventory().interactWithName(item1, "Use");
		s.sleep(250);
		s.client.getInventory().interactWithName(item2, "Use");
	}

	/*
	 * Disabled Temporarily private void dropItem(String[] array) throws
	 * InterruptedException { String name = array[1].replace("_", " ");
	 * s.client.getInventory().interactWithName(name, "Drop"); }
	 */

	private void useItemObj(String[] array) throws InterruptedException {
		String item = array[1].replace("_", " ");
		String name = array[2].replace("_", " ");
		RS2Object obj = s.closestObjectForName(name);
		s.client.getInventory().interactWithName(item, "Use");
		s.sleep(250);
		s.client.moveMouseTo(new RectangleDestination(obj.getMouseDestination()
				.getBoundingBox()), false, true, false);
	}

	private void useItemNpc(String[] array) throws InterruptedException {
		String item = array[1].replace("_", " ");
		String name = array[2].replace("_", " ");
		NPC npc = s.closestNPCForName(name);
		s.client.getInventory().interactWithName(item, "Use");
		s.sleep(250);
		npc.interact(npc.getDefinition().getActions()[0]);
	}
}

// * Move [steps] [direction]: [Move 5 N]
// * Talk [npc_name]: [Talk Banker]
// * InteractNpc [npc] [action]: [InteractNpc Hill_giant Attack]
// * InteractObj [object] [action]: [InteractObj Bank_booth Bank]
// * InteractGround [grounditem] [action]: [InteractGround Big_bones Take]
// * InteractItem [item] [action]: [InteractItem Lobster Eat]
// * UseItem [item_name1] [item_name2]: [UseItem Logs Tinderbox]
// * DropItem [item_name]: [DropItem Logs]
// * UseItemObject [item_name] [object_name]: [UseItemObj Raw_shrimps Fire]
// * UseItemNpc [item_name] [npc_name]: [UseItemNpc Lobster Hill_giant]
// * LClick [MouseX] [MouseY]: [LClick 50 70]
// * RClick [MouseX] [MouseY]: [RClick 50 70]