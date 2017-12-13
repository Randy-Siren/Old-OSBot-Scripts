package gates.twitchbot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.osbot.script.Script;

public class ChatBot {

	private final String server = "irc.twitch.tv";
	private final String nick = "TwitchPlaysOS";
	private final String login = "TwitchPlaysOS";
	private final String pass = "oauth:ck0r3yy60r5i2inkgyrpgixki0hdtxc";
	private final String channel = "#twitchplaysos";
	Socket socket;
	BufferedWriter writer;
	BufferedReader reader;
	String line;
	String command;
	boolean connected = false;
	Robot robot;
	Script s;

	public ChatBot(Script s) throws IOException {
		this.s = s;
		robot = new Robot(s);
		socket = new Socket(server, 6667);
		writer = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream()));
		reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		writer.write("PASS " + pass + "\r\n");
		writer.write("NICK " + nick + "\r\n");
		writer.write("USER " + login + " 8 * : TwitchBot\r\n");
		writer.flush();
		while ((line = reader.readLine()) != null)
			if (line.indexOf("004") >= 0)
				break;
			else if (line.indexOf("433") >= 0)
				s.log("Nickname is already in use.");
		writer.write("JOIN " + channel + "\r\n");
		writer.flush();
		line = "";
		connected = true;
		s.log("Connecting to " + channel);
	}

	public void run() throws InterruptedException {
		if (connected) {
			try {
				while ((line = reader.readLine()) != null) {
					if (line.toLowerCase().contains("ping")) {
						writer.write("PONG " + line.substring(5) + "\r\n");
						/*
						 * writer.write("PRIVMSG " + channel +
						 * " :I got pinged!\r\n");
						 */
						writer.flush();
					} else {
						if (line.contains("PRIVMSG")) {
							String message = line.split("!")[0]
									.replace(":", "")
									+ ": "
									+ line.split(":")[2];
							System.out.println(message);
							if (isACommand(message)) {
								command = message.split(": ")[1];
								robot.handleCommand(command.replace("[", "")
										.replace("]", ""));
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isConnectedToTwitch() {
		return socket.isConnected();
	}

	public boolean isConnectedToChannel() {
		return connected;
	}

	public boolean isACommand(String str) {
		return str.contains("[") && str.charAt(str.length() - 1) == ']';
	}

	public String getServer() {
		return server;
	}

	public String getNick() {
		return nick;
	}

	public String getLogin() {
		return login;
	}

	public String getPass() {
		return pass;
	}

	public String getChannel() {
		return channel;
	}

	public String getLine() {
		return line;
	}

	public String getCommand() {
		return command;
	}

	public Socket getSocket() {
		return socket;
	}
}
