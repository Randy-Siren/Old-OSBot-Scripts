package org.goldengates.bluescales.nodes;

import org.goldengates.bluescales.GoldenScales;
import org.goldengates.bluescales.api.Node;
import org.goldengates.bluescales.data.UserData;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.script.Script;

public class Banking extends Node {

	public Banking(Script s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean validate() throws InterruptedException {

		if (Banks.FALADOR_WEST.contains(s.myPlayer())) {
			return (s.getInventory().getAmount(UserData.foodName) != UserData.foodAmount)
					|| (!UserData.useShortcut && s.getInventory().getAmount("Dusty key") != 1)
					|| (s.getInventory().getAmount("Falador teleport") == 0);
		}
		return false;
	}

	@Override
	public void execute() throws InterruptedException {
		if (s.getBank().isOpen()) {
			s.getBank().depositAllExcept("Falador teleport", "Dusty key", UserData.foodName);
			withdrawDustyKey(UserData.useShortcut, s.getInventory().getAmount("Dusty key"));
			withdrawFaladorTeleport(s.getInventory().getAmount("Falador teleport"));
			withdrawFood(s.getInventory().getAmount(UserData.foodName));
		} else {
			GoldenScales.status = "Opening Bank...";
			s.getBank().open();
		}
	}

	private void withdrawDustyKey(boolean useShortcut, long currentAmount) {
		if (UserData.useShortcut) {
			if (currentAmount > 0) {
				GoldenScales.status = "Depositing all Dusty keys...";
				s.getBank().depositAll("Dusty key");
			}
		} else {
			if (currentAmount < 1) {
				GoldenScales.status = "Withdrawing 1 Dusty key...";
				s.getBank().withdraw("Dusty key", 1);
			} else if (currentAmount > 1) {
				int depositAmount = (int) (currentAmount - 1);
				GoldenScales.status = "Depositing " + depositAmount + " Dusty key(s)...";
				s.getBank().deposit("Dusty key", depositAmount);
			}
		}
	}

	private void withdrawFaladorTeleport(long currentAmount) {
		if (currentAmount < 1) {
			GoldenScales.status = "Withdrawing Falador teleport...";
			s.getBank().withdraw("Falador teleport", 1);
		}
	}

	private void withdrawFood(long currentAmount) {
		if (currentAmount < UserData.foodAmount) {
			int withdrawAmount = (int) (UserData.foodAmount - currentAmount);
			GoldenScales.status = "Withdrawing " + withdrawAmount + " " + UserData.foodName;
			s.getBank().withdraw(UserData.foodName, withdrawAmount);
		} else if (currentAmount > UserData.foodAmount) {
			int depositAmount = (int) (currentAmount - UserData.foodAmount);
			GoldenScales.status = "Depositing " + depositAmount + " " + UserData.foodName;
			s.getBank().deposit(UserData.foodName, depositAmount);
		}
	}

}
