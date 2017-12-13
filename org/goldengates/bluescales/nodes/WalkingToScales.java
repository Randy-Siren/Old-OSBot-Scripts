package org.goldengates.bluescales.nodes;

import org.goldengates.bluescales.GoldenScales;
import org.goldengates.bluescales.api.Node;
import org.goldengates.bluescales.data.PathData;
import org.goldengates.bluescales.data.UserData;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.webwalk.INodeRouteFinder;
import org.osbot.rs07.event.WebWalkEvent;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.Condition;

public class WalkingToScales extends Node {

	public WalkingToScales(Script s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean validate() throws InterruptedException {
		if (!PathData.LOOTING_AREA.contains(s.myPosition())) {
			if (s.getSkills().getDynamic(Skill.HITPOINTS) >= s.getSkills().getStatic(Skill.HITPOINTS) * 0.30) {
				if (s.myPosition().getY() < 5000) {
					if (s.getInventory().getAmount(UserData.foodName) == UserData.foodAmount
							&& (!UserData.useShortcut && s.getInventory().getAmount("Dusty key") == 1)
							&& s.getInventory().contains("Falador teleport")) {
						return true;
					}
				} else {
					if ((!UserData.useShortcut && s.getInventory().getAmount("Dusty key") == 1)
							&& s.getInventory().contains("Falador teleport")) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void execute() throws InterruptedException {
		RS2Object ladder = s.getObjects().closest(new Area(2880, 3390, 2890, 3400), "Ladder");
		RS2Object wall = s.getObjects().closest("Crumbling wall");
		/** ABOVE GROUND **/
		if (s.myPosition().getY() < 5000) {
			if (ladder != null && ladder.isVisible() && ladder.getPosition().distance(s.myPosition()) < 10) {
				GoldenScales.status = "Climbing down...";
				ladder.interact("Climb-down");
				while (s.myPlayer().isAnimating())
					MethodProvider.sleep(MethodProvider.random(500, 1000));
			} else if (ladder != null && ladder.getPosition().distance(s.myPosition()) > 10) {
				GoldenScales.status = "Walking to ladder...";
				s.walking.walk(ladder);
			}
			if (s.myPosition().getX() > 2935) {
				if (wall != null && wall.isVisible() && wall.getPosition().distance(s.myPosition()) < 8) {
					GoldenScales.status = "Climbing over...";
					wall.interact("Climb-over");
					MethodProvider.sleep(MethodProvider.random(1000, 1500));
					while (s.myPlayer().isAnimating())
						MethodProvider.sleep(MethodProvider.random(500, 1000));
				} else if (wall != null && wall.getPosition().distance(s.myPosition()) >= 8) {
					GoldenScales.status = "Walking to wall...";
					s.walking.walk(wall);
				}
			} else {
				if (ladder == null) {
					GoldenScales.status = "Traversing path to ladder...";
					s.getWalking().webWalk(new Area(2882, 3395, 2885, 3399));
				}
			}
		}
		/** BELOW GROUND **/
		else {
			if (UserData.useShortcut) {
				RS2Object pipe = s.getObjects().closest("Obstacle pipe");
				if (!s.myPlayer().isAnimating()) {
					pipe.interact("Squeeze-through");
					MethodProvider.sleep(MethodProvider.random(1500, 2500));
				}
			} else {
				RS2Object dustyGate = s.getObjects().closest(new Area(2924, 9803, 2924, 9803), "Gate");
				if (PathData.THIRD_GATE_AREA.contains(s.myPosition())) {
					if (!s.myPlayer().isMoving()) {
						GoldenScales.status = "Using Dusty key on Gate...";
						s.getInventory().interact("Use", "Dusty key");
						MethodProvider.sleep(MethodProvider.random(750, 1500));
						dustyGate.interact();
						MethodProvider.sleep(MethodProvider.random(1500, 2500));
					}
				} else {
					GoldenScales.status = "Walking to scales...";
					s.walking.webWalk(new Area(2924, 9803, 2924, 9803));
				}
			}
		}
	}

}
