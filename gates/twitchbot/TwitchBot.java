package gates.twitchbot;

import java.awt.*;
import java.io.IOException;

import org.osbot.script.Script;
import org.osbot.script.ScriptManifest;

@ScriptManifest(author = "GoldenGates", info = "Twitch action handler for GoldenGates and Amazon"
		+ "", name = "Twitch Bot", version = 1.00)
public class TwitchBot extends Script {

	private ChatBot chatBot = null;

	public void onStart() {
		try {
			log("Starting Chat Bot...");
			chatBot = new ChatBot(this);
		} catch (IOException e) {
			log("Unable to start Chat Bot!");
			e.printStackTrace();
		}
		while (!chatBot.isConnectedToTwitch())
			;
	}

	public int onLoop() throws InterruptedException {
		chatBot.run();
		return 50;
	}

	public void onExit() {
		try {
			chatBot.getSocket().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Color lineColor = new Color(255, 255, 255, 60);

	public void onPaint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;

		for (int i = 0; i <= 750; i += 50) {
			g.setColor(lineColor);
			g.drawLine(i, 0, i, 502);
			g.setColor(Color.WHITE);
			g.drawString("(" + i + ")", i + 2, 12);
		}
		for (int i = 0; i <= 500; i += 50) {
			g.setColor(lineColor);
			g.drawLine(0, i, 750, i);
			g.setColor(Color.WHITE);
			g.drawString("(" + i + ")", 3, i - 2);
		}
	}

}
