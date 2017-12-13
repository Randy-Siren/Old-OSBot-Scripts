package slayer.misc;

import org.osbot.script.Script;
import org.osbot.script.rs2.map.Position;
import org.osbot.script.rs2.utility.Area;

import slayer.api.EntityMethods;
import slayer.api.Walking;
import slayer.data.GlobalTaskRelated;
import slayer.data.MiscVar;

public class TaskMethods {

	public static int getTaskID(String task, String master) {
		if (master.equals("Turael")) {
			int x = 0;
			for (String s : MiscVar.turaelNPCList) {
				x++;
				if (task.equals(s.toLowerCase()))
					return x;
			}
		}
		return -1;
	}

	public static String getTaskFromGem(Script script)
			throws InterruptedException {
		String[] message = { "null" };
		if (script.client.getInventory().contains(MiscVar.ENCHANTED_GEM_ID)) {
			while (message[0] == "null") {
				script.client.getInventory().interactWithId(
						MiscVar.ENCHANTED_GEM_ID, "Activate");
				script.sleep(860);
				script.continueDialogue();
				script.sleep(837);
				if (script.client.getInterface(234) != null)
					if (script.client.getInterface(234).getChild(1) != null)
						script.client.getInterface(234).getChild(1).interact();
				script.sleep(860);
				script.continueDialogue();
				script.sleep(837);
				if (script.client.getInterface(242) != null) {
					message = script.client.getInterface(242).getChild(2)
							.getMessage().toLowerCase().split(" ");
					GlobalTaskRelated.task = message[5].replace("s;", "");
					GlobalTaskRelated.amount = Integer.parseInt(message[7]);
					return message[5].replace("s;", "");
				}
			}
		} else {
			script.log("You must start with an enchanted gem in the Inventory!");
			script.stop();
		}
		return null;

	}

	public static void getTaskFromTurael(Script script)
			throws InterruptedException {
		EntityMethods methods = new EntityMethods(script);
		Walking walking = new Walking(script);
		if (script.myPlayer().isInArea(new Area(2200, 4930, 2215, 4942))) {
			if (methods.closestObjectWithAction("Climb-up") != null) {
				methods.closestObjectWithAction("Climb-up")
						.interact("Climb-up");
				script.sleep(1531);
			}
		} else {
			if (script.myPlayer().isInArea(MiscVar.TURAEL_AREA)) {
				if (!script.inDialogue()) {
					script.closestNPCForName("Turael").interact("Talk-to");
					script.sleep(737);
				} else {
					// TODO Talk to Turael for new task
				}
			} else {
				walking.walkPathMM(MiscVar.STAIRS_TO_TURAEL);
			}
		}
	}

	/*
	 * public static void goToVarrockBank(Script script) throws
	 * InterruptedException { Walking walking = new Walking(script); if
	 * (!script.myPlayer().isInArea(new Area(3178, 3420, 3222, 3440))) { if
	 * (script.client.getInventory().contains(8007)) {
	 * script.client.getInventory().interactWithId(8007, "Break");
	 * script.sleep(950); while (script.myPlayer().isAnimating())
	 * script.sleep(500); script.sleep(500); } } else { walking.walkPathMM(new
	 * Position[] { new Position(3206, 3429, 0), new Position(3185, 3436, 0) });
	 * } }
	 */

}
