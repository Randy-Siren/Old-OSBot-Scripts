package org.goldengates.bluescales.nodes;

import org.goldengates.bluescales.GoldenScales;
import org.goldengates.bluescales.api.Node;
import org.goldengates.bluescales.data.UserData;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

public class Eating extends Node {

	public Eating(Script s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean validate() throws InterruptedException {
		return s.getInventory().contains(UserData.foodName)
				&& (s.getSkills().getDynamic(Skill.HITPOINTS) < s.getSkills().getStatic(Skill.HITPOINTS) * 0.30
						|| (s.getInventory().isFull()));
	}

	@Override
	public void execute() throws InterruptedException {
		GoldenScales.status = "Eating...";
		s.getInventory().interact("Eat", UserData.foodName);
		MethodProvider.sleep(MethodProvider.random(750, 1000));
		while (s.myPlayer().isAnimating()) {
			MethodProvider.sleep(MethodProvider.random(250, 500));
		}
	}

}
