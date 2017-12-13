package slayer;

import org.osbot.script.Script;
import org.osbot.script.ScriptManifest;

import slayer.data.GlobalTaskRelated;
import slayer.data.MiscVar;
import slayer.data.enums.TuraelTaskData;
import slayer.misc.ComplexNode;
import slayer.misc.TaskMethods;

@ScriptManifest(author = "GoldenGates", info = "Slayer and AIO Fighter. Remember to always have Varrock Tele Runes!", name = "Grande AIO Slayer", version = 0.00)
public class AIOSlayer extends Script {

	TuraelTaskData runecraft;
	ComplexNode[] nodeArray = {};

	public void onStart() {
		try {
			GlobalTaskRelated.currentTaskID = TaskMethods.getTaskID(
					TaskMethods.getTaskFromGem(this),
					GlobalTaskRelated.masterName);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		runecraft = TuraelTaskData.forTaskID(GlobalTaskRelated.currentTaskID);
		log("The task is " + GlobalTaskRelated.task + " and you've to kill "
				+ GlobalTaskRelated.amount + ", the task ID is "
				+ GlobalTaskRelated.currentTaskID);
	}

	public int onLoop() throws InterruptedException {
		// TaskMethods.getTaskFromTurael(this);
		for (ComplexNode n : nodeArray) {
			if (n.activateNode()) {
				if (n.condition()) {
					n.run();
				} else {
					n.executeNode();
				}
			}
		}

		return 1000;
	}

}
