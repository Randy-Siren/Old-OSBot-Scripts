package org.goldengates.bluescales.nodes;

import org.goldengates.bluescales.GoldenScales;
import org.goldengates.bluescales.api.Node;
import org.goldengates.bluescales.data.PathData;
import org.goldengates.bluescales.data.UserData;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

public class Looting extends Node {

	public Looting(Script s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean validate() throws InterruptedException {
		return PathData.LOOTING_AREA.contains(s.myPosition())
				&& s.getSkills().getDynamic(Skill.HITPOINTS) >= s.getSkills().getStatic(Skill.HITPOINTS) * 0.30
				&& !s.getInventory().isFull();
	}

	@Override
	public void execute() throws InterruptedException {
		if (!s.getSettings().isRunning() && s.getSettings().getRunEnergy() >= 20) {
			GoldenScales.status = "Turning run on...";
			s.getSettings().setRunning(true);
		}
		GoldenScales.status = "Looting...";
		GroundItem scale = s.getGroundItems().closest(PathData.LOOTING_AREA, "Blue dragon scale");
		if (scale != null) {
			int old = (int) s.getInventory().getAmount("Blue dragon scale");
			s.getCamera().toEntity(scale);
			scale.interact("Take");
			MethodProvider.sleep(MethodProvider.random(750, 1000));
			while (s.myPlayer().isMoving()) {
				MethodProvider.sleep(MethodProvider.random(250, 500));
			}
			UserData.lootCount = (int) s.getInventory().getAmount("Blue dragon scale") - old;
		}
	}

}
