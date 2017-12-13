package org.goldengates.buildascript.workshop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractListModel;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import org.goldengates.buildascript.data.GUIData;
import org.goldengates.buildascript.data.WorkshopData;
import org.osbot.rs07.script.Script;

public class Settings {

	Script script;
	int line = -10;
	public int insertLine = 0;

	public Settings(Script buildit) {
		this.script = buildit;
	}

	public void addNewAction(Action a, int index) {
		WorkshopData.actions.add(index, a);
		WorkshopData.values.add(index, a.createListText());
		fixList();
		if (insertLine < WorkshopData.actions.size())
			insertLine = WorkshopData.actions.size();
		if (a.getAType() == 0 && !WorkshopData.scriptName.equals(a.getStringArray()[0])) {
			WorkshopData.scriptName = a.getStringArray()[0];
		}
		if (a.getAType() == 1 && !WorkshopData.author.equals(a.getStringArray()[0])) {
			WorkshopData.author = a.getStringArray()[0];
		}
	}

	public void fixList() {
		GUIData.list.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;

			public int getSize() {
				return WorkshopData.values.size();
			}

			public String getElementAt(int index) {
				return WorkshopData.values.get(index);
			}
		});
	}

	public void deleteLine(int line) {
		WorkshopData.actions.remove(line);
		WorkshopData.values.remove(line);
		insertLine = insertLine - 1;
		fixList();
	}

	public void runActions() throws InterruptedException {
		for (int i = 0; i < WorkshopData.actions.size(); i++) {
			if (line != -10) {
				i = line;
				line = -10;
			}
			GUIData.list.setSelectedIndex(i);
			WorkshopData.actions.get(i).runAction(script);
		}
	}

	public void saveAction(String f) throws IOException {
		String[] file2 = f.split(".xml");
		File file = new File(file2[0] + ".xml");
		BufferedWriter bW = new BufferedWriter(new FileWriter(file));
		String x = "<WorkScript>";
		String s = "";
		for (Action a : WorkshopData.actions) {
			if (a != null) {
				s = s + "<Action><ActionID>" + a.getAType() + "</ActionID><IntArray> ";
				for (int i = 0; i < a.getIntArray().length; i++) {
					if (i < a.getIntArray().length - 1) {
						s = s + a.getIntArray()[i] + ", ";
					} else {
						s = s + a.getIntArray()[i];
					}
				}
				s = s + "</IntArray><StringArray>";
				for (int i = 0; i < a.getStringArray().length; i++) {
					if (i < a.getStringArray().length - 1) {
						s = s + a.getStringArray()[i] + ", ";
					} else {
						s = s + a.getStringArray()[i];
					}
				}
				s = s + "</StringArray></Action>";
				bW.newLine();
			}
		}
		x = x + s + "</WorkScript>";
		bW.write(x);
		bW.close();
		System.out.println("Saved.");
	}

	public void loadAction(File f) throws SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc = dBuilder.parse(f);

		doc.getDocumentElement().normalize();
		NodeList nodes = doc.getElementsByTagName("Action");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String actionNumString = getValue("ActionID", element);
				String[] intArrayStringArray = getValue("IntArray", element).split(",");
				int[] actionIntArray = new int[5];
				for (int x = 0; x < 5; x++) {
					actionIntArray[x] = Integer.parseInt(intArrayStringArray[x].trim());
				}
				String[] stringArrayStringArray = getValue("StringArray", element).split(",");
				String[] actionStringArray = new String[stringArrayStringArray.length];
				for (int x = 0; x < stringArrayStringArray.length; x++) {
					actionStringArray[x] = stringArrayStringArray[x].trim();
				}
				addNewAction(new Action(script, Integer.parseInt(actionNumString), actionIntArray, actionStringArray),
						WorkshopData.actions.size());
			}
		}

	}

	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}
}