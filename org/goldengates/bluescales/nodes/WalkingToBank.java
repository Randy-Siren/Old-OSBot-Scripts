package org.goldengates.bluescales.nodes;

import org.goldengates.bluescales.GoldenScales;
import org.goldengates.bluescales.api.Node;
import org.goldengates.bluescales.data.PathData;
import org.goldengates.bluescales.data.UserData;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

public class WalkingToBank extends Node {

	public WalkingToBank(Script s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean validate() throws InterruptedException {
		if (!Banks.FALADOR_WEST.contains(s.myPosition())) {
			if (s.myPosition().getY() > 5000) {
				if (s.getInventory().isFull() && s.getInventory().getAmount(UserData.foodName) == 0
						&& s.getInventory().contains("Blue dragon scale")) {
					return true;
				} else if (s.myPlayer().getCurrentHealth() < s.myPlayer().getMaximumHealth() * 0.30
						&& !s.getInventory().contains(UserData.foodName)) {
					return true;
				}
			} else {
				if (s.getInventory().getAmount(UserData.foodName) != UserData.foodAmount
						|| (!UserData.useShortcut && s.getInventory().getAmount("Dusty key") != 1)
						|| !s.getInventory().contains("Falador teleport")) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void execute() throws InterruptedException {
		/** UNDER GROUND **/
		if (s.myPosition().getY() > 5000) {
			GoldenScales.status = "Teleporting...";
			s.getInventory().getItem("Falador teleport").interact("Break");
			MethodProvider.sleep(MethodProvider.random(1500, 2500));
			while (s.myPlayer().isAnimating()) {
				MethodProvider.sleep(MethodProvider.random(500, 1000));
			}
			MethodProvider.sleep(MethodProvider.random(500, 750));
		}
		/** ABOVE GROUND **/
		else {
			GoldenScales.status = "Walking to bank...";
			s.walking.webWalk(Banks.FALADOR_WEST);
		}
	}

}
