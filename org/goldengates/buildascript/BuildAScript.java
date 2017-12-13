package org.goldengates.buildascript;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.goldengates.buildascript.api.Time;
import org.goldengates.buildascript.api.Utilities;
import org.goldengates.buildascript.data.WorkshopData;
import org.goldengates.buildascript.gui.GUI;
import org.goldengates.buildascript.workshop.Action;
import org.goldengates.buildascript.workshop.Settings;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(author = "GoldenGates", info = "Create the script of your dream, for free!"
		+ "", name = "Build-a-Script Workshop", version = 1.00, logo = "http://i.imgur.com/GMQODMo.png")
public class BuildAScript extends Script implements MouseListener {

	public static Settings settings;
	public GUI gui;
	public Action action;
	int x = -1;

	boolean hide = false;

	public void onStart() throws InterruptedException {
		log("Started Build-a-Script Workship Version 1.04 BETA");
		settings = new Settings(this);
		gui = new GUI(this);
	}

	public int onLoop() throws InterruptedException {
		while (WorkshopData.guiWait)
			sleep(100);
		if (x == -1) {
			WorkshopData.startTime = System.currentTimeMillis();
			x++;
		} else {
			if (!WorkshopData.guiWait) {
				WorkshopData.paintIDs = false;
				settings.runActions();
				log("Running...");
			}
		}
		return 10;
	}

	@Override
	public void onExit() throws InterruptedException {
		WorkshopData.actions.clear();
		WorkshopData.values.clear();
	}

	private final RenderingHints antialiasing = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
			RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

	Color pColour = new Color(255, 255, 255, 50);
	Color fColour = new Color(0, 0, 0);
	private BasicStroke stroke1 = new BasicStroke(2);
	private Font titleFont = new Font("Adobe Caslon Pro", 0, 16);
	private Font dataFont = new Font("Adobe Caslon Pro", 0, 14);
	private final Rectangle paintBox = new Rectangle(0, 415, 516, 62);

	public void onPaint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		g.setRenderingHints(antialiasing);
		if (!hide) {
			if (!getBank().isOpen()) {
				if (WorkshopData.fontChanged == 0 || WorkshopData.paintChanged == 0) {
					setPaintColor(g);
					setFontColor(g);
				}
				g.setColor(pColour);
				g.fillRect(0, 415, 516, 62);
				g.setColor(Color.black);
				g.setStroke(stroke1);
				g.drawRect(0, 415, 516, 62);
				g.setColor(fColour);
				g.setFont(titleFont);
				g.drawString("" + WorkshopData.scriptName, 182, 415);
				g.setFont(dataFont);
				g.drawString("By: " + WorkshopData.author, 197, 430);
				if (WorkshopData.skillChanged == 1) {
					g.drawString(WorkshopData.skillToTrack + ": ", 10, 450);
					g.drawString(WorkshopData.skillToTrack + "/Hour: ", 10, 465);
					g.drawString("Time TNL:", 300, 450);
				}
				g.drawString("Time: " + Time.format(System.currentTimeMillis() - WorkshopData.startTime), 300, 470);
				if (WorkshopData.paintIDs) {
					paintIDs(g);
				}
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		if (paintBox.contains(e.getPoint())) {
			hide = !hide;
		}
	}

	public Point getInventoryPoint(int slot) {
		int col = slot % 4;
		int row = slot / 4;
		int x = 563 + col * 42;
		int y = 224 + 24 + row * 36;
		return new Point(x, y);
	}

	public void paintIDs(Graphics2D g) {
		if (getTabs().getOpen() == Tab.INVENTORY) {
			g.setColor(Color.white);
			for (int i = 0; i < getInventory().getItems().length; i++) {
				if (getInventory().getItems()[i] == null)
					continue;
				g.drawString(getInventory().getItems()[i].getId() + "", getInventoryPoint(i).x, getInventoryPoint(i).y);
			}
		}
		g.setColor(Color.white);
		g.drawString("Waiting", 7, 106);
		g.drawString(getMouse().getPosition().x + ", " + getMouse().getPosition().y, getMouse().getPosition().x,
				getMouse().getPosition().y);
	}

	public void setPaintColor(Graphics2D g) {
		pColour = Utilities.setPaintColour(WorkshopData.paintColour);
		WorkshopData.paintChanged = 1;
	}

	public void setFontColor(Graphics2D g) {
		fColour = Utilities.setFontColour(WorkshopData.fontColour.toLowerCase());
		WorkshopData.fontChanged = 1;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (paintBox.contains(arg0.getPoint())) {
			hide = !hide;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}