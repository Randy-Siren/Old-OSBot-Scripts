package org.goldengates.buildascript.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import org.goldengates.buildascript.data.*;
import org.goldengates.buildascript.workshop.*;
import org.goldengates.buildascript.workshop.Action;
import org.goldengates.buildascript.BuildAScript;
import org.goldengates.buildascript.api.*;
import org.osbot.rs07.script.Script;
import org.xml.sax.SAXException;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	public static Container contentPane;
	Script script;
	JScrollPane pane;

	public GUI(Script script) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 490, 500);
		contentPane = new JPanel();
		contentPane = getContentPane();
		contentPane.setLayout(null);
		setTitle("Build-a-Script Workshop");
		GUIData.menuBar = new JMenuBar();
		setResizable(false);
		setJMenuBar(GUIData.menuBar);
		addWorkShopMenu();
		GUIData.mnAddAction = new JMenu("Add Action");
		GUIData.menuBar.add(GUIData.mnAddAction);
		this.script = script;

		addPaintMenu();
		addBankMenu();
		addGroundItemMenu();
		addItemMenu();
		addKeyboardMenu();
		addMouseMenu();
		addNPCMenu();
		addRS2ObjectMenu();
		addTabMenu();
		addJumpToLoopMenu();
		addWaitMenu();
		addWalkingMenu();
		addOtherMenu();
		addOptionsMenu();
		GUIData.list = new JList<String>();
		GUIData.list.setModel(new AbstractListModel<String>() {

			private static final long serialVersionUID = 1L;

			public int getSize() {
				return WorkshopData.values.size();
			}

			public String getElementAt(int index) {
				return WorkshopData.values.get(index);
			}
		});
		GUIData.list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GUIData.list.addMouseMotionListener(new MouseMotionAdapter() {
			@SuppressWarnings({ "rawtypes", "unused" })
			@Override
			public void mouseMoved(MouseEvent e) {
				JList l = (JList) e.getSource();
				ListModel m = l.getModel();
				int index = l.locationToIndex(e.getPoint());
				if (index > -1) {
					l.setToolTipText("Line number: " + index);
				}
			}
		});
		GUIData.list.setVisibleRowCount(10);
		GUIData.list.setMinimumSize(new Dimension(100, 500));
		pane = new JScrollPane(GUIData.list);
		pane.setViewportView(GUIData.list);

		pane.setBounds(10, 20, 409, 353);
		contentPane.add(pane);

		GUIData.lblGoldengates = new JLabel(
				"GoldenGates                                          " + "More Features to Come");
		GUIData.lblGoldengates.setBounds(10, 410, 500, 16);
		contentPane.add(GUIData.lblGoldengates);

		GUIData.upButton = new JButton("^");
		GUIData.upButton.setBounds(425, 150, 50, 30);
		contentPane.add(GUIData.upButton);
		GUIData.upButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (WorkshopData.values.size() > 0 && !GUIData.list.isSelectionEmpty()) {
					int indexOfSelected = GUIData.list.getSelectedIndex();
					if (indexOfSelected - 1 >= 0) {
						Collections.swap(WorkshopData.actions, indexOfSelected, indexOfSelected - 1);
						Collections.swap(WorkshopData.values, indexOfSelected, indexOfSelected - 1);
						GUIData.list.setSelectedIndex(indexOfSelected - 1);
						GUIData.list.updateUI();
					}
				}
			}
		});

		GUIData.downButton = new JButton("v");
		GUIData.downButton.setBounds(425, 200, 50, 30);
		contentPane.add(GUIData.downButton);
		GUIData.downButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (WorkshopData.values.size() > 0 && !GUIData.list.isSelectionEmpty()) {
					int indexOfSelected = GUIData.list.getSelectedIndex();
					if (indexOfSelected + 1 < WorkshopData.values.size()) {
						Collections.swap(WorkshopData.actions, indexOfSelected, indexOfSelected + 1);
						Collections.swap(WorkshopData.values, indexOfSelected, indexOfSelected + 1);
						GUIData.list.setSelectedIndex(indexOfSelected + 1);
						GUIData.list.updateUI();
					}
				}
			}
		});
		setVisible(true);
	}

	public void addWorkShopMenu() {
		GUIData.mnWorkshop = new JMenu("Workshop");
		GUIData.menuBar.add(GUIData.mnWorkshop);
		//
		GUIData.mntmStartScript = new JMenuItem("Start Script");
		GUIData.mnWorkshop.add(GUIData.mntmStartScript);
		GUIData.mntmStartScript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WorkshopData.guiWait = false;
			}
		});
		GUIData.mntmSaveScript = new JMenuItem("Save Script");
		GUIData.mnWorkshop.add(GUIData.mntmSaveScript);
		GUIData.mntmSaveScript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int i = chooser.showSaveDialog(GUI.this);
				if (i == 0) {
					File file = chooser.getSelectedFile();
					try {
						BuildAScript.settings.saveAction(chooser.getSelectedFile().toString());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					script.log("Saving the script: " + file.getName() + ".");
				} else {
					script.log("Cancelled.");
				}
			}
		});
		GUIData.mntmLoadScript = new JMenuItem("Load Script");
		GUIData.mnWorkshop.add(GUIData.mntmLoadScript);
		GUIData.mntmLoadScript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int i = chooser.showOpenDialog(GUI.this);
				if (i == 0) {
					GUIData.list.setModel(new AbstractListModel<String>() {
						private static final long serialVersionUID = 1L;

						public int getSize() {
							return WorkshopData.values.size();
						}

						public String getElementAt(int index) {
							return WorkshopData.values.get(index);
						}
					});
					try {
						BuildAScript.settings.loadAction(chooser.getSelectedFile());
					} catch (SAXException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					script.log("Loading the script: " + chooser.getSelectedFile().getName() + ".");
				} else {
					script.log("Cancelled.");
				}
			}
		});
	}

	public void addPaintMenu() {
		GUIData.mnPaint = new JMenu("Paint");
		GUIData.mnAddAction.add(GUIData.mnPaint);
		//
		GUIData.mntmScriptName = new JMenuItem("Script Name");
		GUIData.mnPaint.add(GUIData.mntmScriptName);
		GUIData.mntmScriptName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WorkshopData.scriptName = JOptionPane.showInputDialog("Script Name:");
			}
		});

		GUIData.mntmAuthor = new JMenuItem("Author");
		GUIData.mnPaint.add(GUIData.mntmAuthor);
		GUIData.mntmAuthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WorkshopData.author = JOptionPane.showInputDialog("Author:");
			}
		});

		GUIData.mntmTrackSkill = new JMenuItem("Track Skill");
		GUIData.mnPaint.add(GUIData.mntmTrackSkill);
		GUIData.mntmTrackSkill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WorkshopData.skillToTrack = Utilities
						.getSkillForString(JOptionPane.showInputDialog("Track Skill (Exact Name):"));
			}
		});
		GUIData.mntmSetPaintColour = new JMenuItem("Set Paint Colour");
		GUIData.mnPaint.add(GUIData.mntmSetPaintColour);
		GUIData.mntmSetPaintColour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WorkshopData.paintColour = JOptionPane.showInputDialog("Colour (Exact Name):").toLowerCase();
				WorkshopData.paintChanged = 0;
			}
		});
		GUIData.mntmSetFontColour = new JMenuItem("Set Font Colour");
		GUIData.mnPaint.add(GUIData.mntmSetFontColour);
		GUIData.mntmSetFontColour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WorkshopData.fontColour = JOptionPane.showInputDialog("Colour (Exact Name):").toLowerCase();
				WorkshopData.fontChanged = 0;
			}
		});
	}

	public void addBankMenu() {
		GUIData.mnBank = new JMenu("Bank");
		GUIData.mnAddAction.add(GUIData.mnBank);
		//
		GUIData.mntmOpen = new JMenuItem("Open");
		GUIData.mnBank.add(GUIData.mntmOpen);
		GUIData.mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuildAScript.settings.addNewAction(new Action(script, 2), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmClose = new JMenuItem("Close");
		GUIData.mnBank.add(GUIData.mntmClose);
		GUIData.mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuildAScript.settings.addNewAction(new Action(script, 3), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmDeposit = new JMenuItem("Deposit");
		GUIData.mnBank.add(GUIData.mntmDeposit);
		GUIData.mntmDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] s = new int[5];
				s[0] = Integer.parseInt(JOptionPane.showInputDialog("Item ID:"));
				BuildAScript.settings.addNewAction(new Action(script, 4, s), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmDepositAllExcept = new JMenuItem("Deposit All Except");
		GUIData.mnBank.add(GUIData.mntmDepositAllExcept);
		GUIData.mntmDepositAllExcept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] s = new int[5];
				s[0] = Integer.parseInt(JOptionPane.showInputDialog("Deposit All BUT Item ID:"));
				BuildAScript.settings.addNewAction(new Action(script, 5, s), BuildAScript.settings.insertLine);
			}
		});
		GUIData.mntmDepositAll = new JMenuItem("Deposit All");
		GUIData.mnBank.add(GUIData.mntmDepositAll);
		GUIData.mntmDepositAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuildAScript.settings.addNewAction(new Action(script, 28), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmWithdraw = new JMenuItem("Withdraw");
		GUIData.mnBank.add(GUIData.mntmWithdraw);
		GUIData.mntmWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] s = new int[5];
				s[0] = Integer.parseInt(JOptionPane.showInputDialog("Item ID:"));
				s[1] = Integer.parseInt(JOptionPane.showInputDialog("Amount"));
				BuildAScript.settings.addNewAction(new Action(script, 6, s), BuildAScript.settings.insertLine);
			}
		});
		GUIData.mntmsetNoted = new JMenuItem("Set Withdrawl to Noted");
		GUIData.mnBank.add(GUIData.mntmsetNoted);
		GUIData.mntmsetNoted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuildAScript.settings.addNewAction(new Action(script, 47), BuildAScript.settings.insertLine);
			}
		});
		GUIData.mntmsetItem = new JMenuItem("Set Withdrawl to Item");
		GUIData.mnBank.add(GUIData.mntmsetItem);
		GUIData.mntmsetItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuildAScript.settings.addNewAction(new Action(script, 48), BuildAScript.settings.insertLine);
			}
		});
	}

	public void addGroundItemMenu() {
		GUIData.mnGrounditem = new JMenu("GroundItem");
		GUIData.mnAddAction.add(GUIData.mnGrounditem);
		//
		GUIData.mntmInteract_1 = new JMenuItem("Interact");
		GUIData.mnGrounditem.add(GUIData.mntmInteract_1);
		GUIData.mntmInteract_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				String[] s = new String[5];
				int x = 0;
				while (x < 5) {
					i[x] = Integer.parseInt(JOptionPane.showInputDialog("GroundItem ID - Enter 0 to stop:"));
					if (i[x] == 0)
						break;
					s[x] = JOptionPane.showInputDialog("Action:");
					x++;
				}
				BuildAScript.settings.addNewAction(new Action(script, 7, i, s), BuildAScript.settings.insertLine);
			}
		});
	}

	public void addItemMenu() {
		GUIData.mnItem = new JMenu("Item");
		GUIData.mnAddAction.add(GUIData.mnItem);
		//
		GUIData.mntmDrop = new JMenuItem("Drop");
		GUIData.mnItem.add(GUIData.mntmDrop);
		GUIData.mntmDrop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("Item To Drop ID:"));
				BuildAScript.settings.addNewAction(new Action(script, 8, i), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmDropAllOf = new JMenuItem("Drop All of 1 Item");
		GUIData.mnItem.add(GUIData.mntmDropAllOf);
		GUIData.mntmDropAllOf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("Item To Drop ID:"));
				BuildAScript.settings.addNewAction(new Action(script, 9, i), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmDropAllItems = new JMenuItem("Drop All of Inventory");
		GUIData.mnItem.add(GUIData.mntmDropAllItems);
		GUIData.mntmDropAllItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuildAScript.settings.addNewAction(new Action(script, 10), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmInteract_2 = new JMenuItem("Interact");
		GUIData.mnItem.add(GUIData.mntmInteract_2);
		GUIData.mntmInteract_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				String[] s = new String[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("Item ID:"));
				s[0] = JOptionPane.showInputDialog("Action:");
				BuildAScript.settings.addNewAction(new Action(script, 11, i, s), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmUse = new JMenuItem("Use");
		GUIData.mnItem.add(GUIData.mntmUse);
		GUIData.mntmUse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("Item ID:"));
				BuildAScript.settings.addNewAction(new Action(script, 12, i), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmUseOnItem = new JMenuItem("Use on Item");
		GUIData.mnItem.add(GUIData.mntmUseOnItem);
		GUIData.mntmUseOnItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("First Item ID:"));
				i[1] = Integer.parseInt(JOptionPane.showInputDialog("Second Item ID:"));
				BuildAScript.settings.addNewAction(new Action(script, 13, i), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmUseOnNpc = new JMenuItem("Use on NPC");
		GUIData.mnItem.add(GUIData.mntmUseOnNpc);
		GUIData.mntmUseOnNpc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				String[] s = new String[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("Item ID:"));
				i[1] = Integer.parseInt(JOptionPane.showInputDialog("NPC ID:"));
				s[0] = JOptionPane.showInputDialog("Action:");
				BuildAScript.settings.addNewAction(new Action(script, 14, i, s), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmUseOnRsobject = new JMenuItem("Use on RS2Object");
		GUIData.mnItem.add(GUIData.mntmUseOnRsobject);
		GUIData.mntmUseOnRsobject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				String[] s = new String[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("Item ID:"));
				i[1] = Integer.parseInt(JOptionPane.showInputDialog("Object ID:"));
				s[0] = JOptionPane.showInputDialog("Action:");
				BuildAScript.settings.addNewAction(new Action(script, 15, i, s), BuildAScript.settings.insertLine);
			}
		});
	}

	public void addKeyboardMenu() {
		GUIData.mnKeyboard = new JMenu("Keyboard");
		GUIData.mnAddAction.add(GUIData.mnKeyboard);
		//
		GUIData.mntmType = new JMenuItem("Type");
		GUIData.mnKeyboard.add(GUIData.mntmType);
		GUIData.mntmType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] s = new String[5];
				s[0] = JOptionPane.showInputDialog("Type:");
				BuildAScript.settings.addNewAction(new Action(script, 16, s), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmPressEnter = new JMenuItem("Press Enter");
		GUIData.mnKeyboard.add(GUIData.mntmPressEnter);
		GUIData.mntmPressEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuildAScript.settings.addNewAction(new Action(script, 17), BuildAScript.settings.insertLine);
			}
		});
	}

	public void addMouseMenu() {
		GUIData.mnMouse = new JMenu("Mouse");
		GUIData.mnAddAction.add(GUIData.mnMouse);
		//
		GUIData.mntmLeftClick = new JMenuItem("Left Click");
		GUIData.mnMouse.add(GUIData.mntmLeftClick);
		GUIData.mntmLeftClick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuildAScript.settings.addNewAction(new Action(script, 18), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmRightClick = new JMenuItem("Right Click");
		GUIData.mnMouse.add(GUIData.mntmRightClick);
		GUIData.mntmRightClick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuildAScript.settings.addNewAction(new Action(script, 19), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmMoveMouse = new JMenuItem("Move Mouse");
		GUIData.mnMouse.add(GUIData.mntmMoveMouse);
		GUIData.mntmMoveMouse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("X co-ord:"));
				i[1] = Integer.parseInt(JOptionPane.showInputDialog("Y co-ord:"));
				BuildAScript.settings.addNewAction(new Action(script, 20, i), BuildAScript.settings.insertLine);
			}
		});
	}

	public void addNPCMenu() {
		GUIData.mnNewMenu = new JMenu("NPC");
		GUIData.mnAddAction.add(GUIData.mnNewMenu);
		//
		GUIData.mntmInteract = new JMenuItem("Interact");
		GUIData.mnNewMenu.add(GUIData.mntmInteract);
		GUIData.mntmInteract.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				String[] s = new String[5];
				int x = 0;
				while (x < 5) {
					i[x] = Integer.parseInt(JOptionPane.showInputDialog("NPC ID - Enter 0 to stop:"));
					if (i[x] == 0)
						break;
					s[x] = JOptionPane.showInputDialog("Action:");
					x++;
				}
				BuildAScript.settings.addNewAction(new Action(script, 21, i, s), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmAttack = new JMenuItem("Attack");
		GUIData.mnNewMenu.add(GUIData.mntmAttack);
		GUIData.mntmAttack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				int x = 0;
				while (x < 5 || i[x] != 0) {
					i[x] = Integer.parseInt(JOptionPane.showInputDialog("NPC ID - Enter 0 to stop:"));
					if (i[x] == 0)
						break;
					x++;
				}
				BuildAScript.settings.addNewAction(new Action(script, 22, i), BuildAScript.settings.insertLine);
			}
		});
	}

	public void addRS2ObjectMenu() {
		GUIData.mnRsobject = new JMenu("RS2Object");
		GUIData.mnAddAction.add(GUIData.mnRsobject);
		//
		GUIData.mntmInteract_3 = new JMenuItem("Interact");
		GUIData.mnRsobject.add(GUIData.mntmInteract_3);
		GUIData.mntmInteract_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				String[] s = new String[5];
				int x = 0;
				while (x < 5) {
					i[x] = Integer.parseInt(JOptionPane.showInputDialog("Object ID - Enter 0 to stop:"));
					if (i[x] == 0)
						break;
					s[x] = JOptionPane.showInputDialog("Action:");
					x++;
				}
				BuildAScript.settings.addNewAction(new Action(script, 23, i, s), BuildAScript.settings.insertLine);
			}
		});
	}

	public void addTabMenu() {
		GUIData.mnTab = new JMenu("Tab");
		GUIData.mnAddAction.add(GUIData.mnTab);
		//
		GUIData.mntmOpenTab = new JMenuItem("Open Tab");
		GUIData.mnTab.add(GUIData.mntmOpenTab);
		GUIData.mntmOpenTab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("Tab ID (Starts at 0):"));
				BuildAScript.settings.addNewAction(new Action(script, 24, i), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmCastSpell = new JMenuItem("Cast Spell");
		GUIData.mnTab.add(GUIData.mntmCastSpell);
		GUIData.mntmCastSpell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("X co-ord of spell:"));
				i[1] = Integer.parseInt(JOptionPane.showInputDialog("Y co-ord of spell:"));
				BuildAScript.settings.addNewAction(new Action(script, 25, i), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmCastSpellOn = new JMenuItem("Cast Spell on NPC");
		GUIData.mnTab.add(GUIData.mntmCastSpellOn);
		GUIData.mntmCastSpellOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				String[] s = new String[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("X co-ord of spell:"));
				i[1] = Integer.parseInt(JOptionPane.showInputDialog("Y co-ord of spell:"));
				i[2] = Integer.parseInt(JOptionPane.showInputDialog("NPC ID:"));
				s[0] = JOptionPane.showInputDialog("Action:");
				BuildAScript.settings.addNewAction(new Action(script, 26, i, s), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmCastSpellOnObject = new JMenuItem("Cast Spell on RS2Object");
		GUIData.mnTab.add(GUIData.mntmCastSpellOnObject);
		GUIData.mntmCastSpellOnObject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				String[] s = new String[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("X co-ord of spell:"));
				i[1] = Integer.parseInt(JOptionPane.showInputDialog("Y co-ord of spell:"));
				i[2] = Integer.parseInt(JOptionPane.showInputDialog("Object ID:"));
				s[0] = JOptionPane.showInputDialog("Action:");
				BuildAScript.settings.addNewAction(new Action(script, 27, i, s), BuildAScript.settings.insertLine);
			}
		});
	}

	public void addJumpToLoopMenu() {
		GUIData.mnJumpToLoop = new JMenu("Jump to Line If");
		GUIData.mnAddAction.add(GUIData.mnJumpToLoop);
		//
		GUIData.mntmPlayerIsIn = new JMenuItem("Player Is In Area");
		GUIData.mnJumpToLoop.add(GUIData.mntmPlayerIsIn);
		GUIData.mntmPlayerIsIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("Lower X of Area:"));
				i[1] = Integer.parseInt(JOptionPane.showInputDialog("Lower Y of Area:"));
				i[2] = Integer.parseInt(JOptionPane.showInputDialog("Higher X of Area:"));
				i[3] = Integer.parseInt(JOptionPane.showInputDialog("Higher Y of Area:"));
				i[4] = Integer.parseInt(JOptionPane.showInputDialog("Jump to Line:"));
				BuildAScript.settings.addNewAction(new Action(script, 29, i), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmPlayerIsNot = new JMenuItem("Player Is Not In Area");
		GUIData.mnJumpToLoop.add(GUIData.mntmPlayerIsNot);
		GUIData.mntmPlayerIsNot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("Lower X of Area:"));
				i[1] = Integer.parseInt(JOptionPane.showInputDialog("Lower Y of Area:"));
				i[2] = Integer.parseInt(JOptionPane.showInputDialog("Higher X of Area:"));
				i[3] = Integer.parseInt(JOptionPane.showInputDialog("Higher Y of Area:"));
				i[4] = Integer.parseInt(JOptionPane.showInputDialog("Jump to Line:"));
				BuildAScript.settings.addNewAction(new Action(script, 30, i), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmPlayerIsIn_1 = new JMenuItem("Player Is In Combat");
		GUIData.mnJumpToLoop.add(GUIData.mntmPlayerIsIn_1);
		GUIData.mntmPlayerIsIn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("Jump to Line:"));
				BuildAScript.settings.addNewAction(new Action(script, 31, i), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmPlayerIsNot_1 = new JMenuItem("Player Is Not In Combat");
		GUIData.mnJumpToLoop.add(GUIData.mntmPlayerIsNot_1);
		GUIData.mntmPlayerIsNot_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("Jump to Line:"));
				BuildAScript.settings.addNewAction(new Action(script, 32, i), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmNpcIsntNull = new JMenuItem("NPC Exists");
		GUIData.mnJumpToLoop.add(GUIData.mntmNpcIsntNull);
		GUIData.mntmNpcIsntNull.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				int x = 0;
				while (x < 4) {
					i[x] = Integer.parseInt(JOptionPane.showInputDialog("NPC ID - Enter 0 to stop:"));
					if (i[x] == 0)
						break;
					x++;
				}
				i[4] = Integer.parseInt(JOptionPane.showInputDialog("Jump to Line:"));
				BuildAScript.settings.addNewAction(new Action(script, 33, i), BuildAScript.settings.insertLine);
			}
		});
		GUIData.mntmNpcIsNull = new JMenuItem("NPC Does Not Exist");
		GUIData.mnJumpToLoop.add(GUIData.mntmNpcIsntNull);
		GUIData.mntmNpcIsNull.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				int x = 0;
				while (x < 4) {
					i[x] = Integer.parseInt(JOptionPane.showInputDialog("NPC ID - Enter 0 to stop:"));
					if (i[x] == 0)
						break;
					x++;
				}
				i[4] = Integer.parseInt(JOptionPane.showInputDialog("Jump to Line:"));
				BuildAScript.settings.addNewAction(new Action(script, 52, i), BuildAScript.settings.insertLine);
			}
		});
		GUIData.mntmObjectIsntNull = new JMenuItem("Object Exists");
		GUIData.mnJumpToLoop.add(GUIData.mntmObjectIsntNull);
		GUIData.mntmObjectIsntNull.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				int x = 0;
				while (x < 4) {
					i[x] = Integer.parseInt(JOptionPane.showInputDialog("Object ID - Enter 0 to stop:"));
					if (i[x] == 0)
						break;
					x++;
				}
				i[4] = Integer.parseInt(JOptionPane.showInputDialog("Jump to Line:"));
				BuildAScript.settings.addNewAction(new Action(script, 53, i), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmObjectIsNull = new JMenuItem("Object Does Not Exist");
		GUIData.mnJumpToLoop.add(GUIData.mntmObjectIsNull);
		GUIData.mntmObjectIsNull.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				int x = 0;
				while (x < 4) {
					i[x] = Integer.parseInt(JOptionPane.showInputDialog("Object ID - Enter 0 to stop:"));
					if (i[x] == 0)
						break;
					x++;
				}
				i[4] = Integer.parseInt(JOptionPane.showInputDialog("Jump to Line:"));
				BuildAScript.settings.addNewAction(new Action(script, 54, i), BuildAScript.settings.insertLine);
			}
		});
		GUIData.mntmGroundItemIsntNull = new JMenuItem("GroundItem Exists");
		GUIData.mnJumpToLoop.add(GUIData.mntmGroundItemIsntNull);
		GUIData.mntmGroundItemIsntNull.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				int x = 0;
				while (x < 4) {
					i[x] = Integer.parseInt(JOptionPane.showInputDialog("GroundItem ID - Enter 0 to stop:"));
					if (i[x] == 0)
						break;
					x++;
				}
				i[4] = Integer.parseInt(JOptionPane.showInputDialog("Jump to Line:"));
				BuildAScript.settings.addNewAction(new Action(script, 55, i), BuildAScript.settings.insertLine);
			}
		});
		GUIData.mntmGroundItemIsNull = new JMenuItem("GroundItem Does Not Exist");
		GUIData.mnJumpToLoop.add(GUIData.mntmGroundItemIsNull);
		GUIData.mntmGroundItemIsNull.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				int x = 0;
				while (x < 4) {
					i[x] = Integer.parseInt(JOptionPane.showInputDialog("GroundItem ID - Enter 0 to stop:"));
					if (i[x] == 0)
						break;
					x++;
				}
				i[4] = Integer.parseInt(JOptionPane.showInputDialog("Jump to Line:"));
				BuildAScript.settings.addNewAction(new Action(script, 56, i), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmInventoryIsFull = new JMenuItem("Inventory Is Full");
		GUIData.mnJumpToLoop.add(GUIData.mntmInventoryIsFull);
		GUIData.mntmInventoryIsFull.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("Jump to Line:"));
				BuildAScript.settings.addNewAction(new Action(script, 34, i), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmInventoryIsNot = new JMenuItem("Inventory Is Not Full");
		GUIData.mnJumpToLoop.add(GUIData.mntmInventoryIsNot);
		GUIData.mntmInventoryIsNot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("Jump to Line:"));
				BuildAScript.settings.addNewAction(new Action(script, 35, i), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmInventoryIsEmpty = new JMenuItem("Inventory Is Empty");
		GUIData.mnJumpToLoop.add(GUIData.mntmInventoryIsEmpty);
		GUIData.mntmInventoryIsEmpty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("Jump to Line:"));
				BuildAScript.settings.addNewAction(new Action(script, 46, i), BuildAScript.settings.insertLine);
			}
		});
		GUIData.mntmInventoryDoesContain = new JMenuItem("Inventory Contains");
		GUIData.mnJumpToLoop.add(GUIData.mntmInventoryDoesContain);
		GUIData.mntmInventoryDoesContain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("Item ID:"));
				i[1] = Integer.parseInt(JOptionPane.showInputDialog("Jump to Line:"));
				BuildAScript.settings.addNewAction(new Action(script, 44, i), BuildAScript.settings.insertLine);
			}
		});
		GUIData.mntmInventoryDoesNotContain = new JMenuItem("Inventory Does Not Contain");
		GUIData.mnJumpToLoop.add(GUIData.mntmInventoryDoesNotContain);
		GUIData.mntmInventoryDoesNotContain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("Item ID:"));
				i[1] = Integer.parseInt(JOptionPane.showInputDialog("Jump to Line:"));
				BuildAScript.settings.addNewAction(new Action(script, 45, i), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmHealthIsBelow = new JMenuItem("Health is Below");
		GUIData.mnJumpToLoop.add(GUIData.mntmHealthIsBelow);
		GUIData.mntmHealthIsBelow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("HP:"));
				i[1] = Integer.parseInt(JOptionPane.showInputDialog("Jump to Line:"));
				BuildAScript.settings.addNewAction(new Action(script, 36, i), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmHealthIsAbove = new JMenuItem("Health Is Above");
		GUIData.mnJumpToLoop.add(GUIData.mntmHealthIsAbove);
		GUIData.mntmHealthIsAbove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("HP:"));
				i[1] = Integer.parseInt(JOptionPane.showInputDialog("Jump to Line:"));
				BuildAScript.settings.addNewAction(new Action(script, 37, i), BuildAScript.settings.insertLine);
			}
		});
	}

	public void addWaitMenu() {
		GUIData.mnNewMenu_1 = new JMenu("Wait");
		GUIData.mnAddAction.add(GUIData.mnNewMenu_1);
		//
		GUIData.mntmSleep_1 = new JMenuItem("Sleep");
		GUIData.mnNewMenu_1.add(GUIData.mntmSleep_1);
		GUIData.mntmSleep_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("Time in ms:"));
				BuildAScript.settings.addNewAction(new Action(script, 38, i), BuildAScript.settings.insertLine);
			}
		});

		/** LEGACY **/
		/*
		 * GUIData.mntmUntilInterfaceShows = new JMenuItem(
		 * "Until Interface Shows");
		 * GUIData.mnNewMenu_1.add(GUIData.mntmUntilInterfaceShows);
		 * GUIData.mntmUntilInterfaceShows.addActionListener(new
		 * ActionListener() { public void actionPerformed(ActionEvent e) {
		 * BuildAScript.settings.addNewAction(new Action(script, 39),
		 * BuildAScript.settings.insertLine); } });
		 */

		GUIData.mntmUntilNotAnimating = new JMenuItem("Until Not Animating (Long)");
		GUIData.mnNewMenu_1.add(GUIData.mntmUntilNotAnimating);
		GUIData.mntmUntilNotAnimating.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuildAScript.settings.addNewAction(new Action(script, 40), BuildAScript.settings.insertLine);
			}
		});
		GUIData.mntmUntilNotAnimatingShort = new JMenuItem("Until Not Animating (Short)");
		GUIData.mnNewMenu_1.add(GUIData.mntmUntilNotAnimatingShort);
		GUIData.mntmUntilNotAnimatingShort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuildAScript.settings.addNewAction(new Action(script, 49), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmSleepTillIdle = new JMenuItem("Until Out of Combat");
		GUIData.mnNewMenu_1.add(GUIData.mntmSleepTillIdle);
		GUIData.mntmSleepTillIdle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuildAScript.settings.addNewAction(new Action(script, 41), BuildAScript.settings.insertLine);
			}
		});
		GUIData.mntmSleepTillNotMoving = new JMenuItem("Until Not Moving");
		GUIData.mnNewMenu_1.add(GUIData.mntmSleepTillNotMoving);
		GUIData.mntmSleepTillNotMoving.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuildAScript.settings.addNewAction(new Action(script, 50), BuildAScript.settings.insertLine);
			}
		});
	}

	public void addWalkingMenu() {
		GUIData.mnWalking = new JMenu("Walking");
		GUIData.mnAddAction.add(GUIData.mnWalking);
		//
		GUIData.mntmTurnRunOn = new JMenuItem("Turn Run On At");
		GUIData.mnWalking.add(GUIData.mntmTurnRunOn);
		GUIData.mntmTurnRunOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = Integer.parseInt(JOptionPane.showInputDialog("Run Energy:"));
				BuildAScript.settings.addNewAction(new Action(script, 42, i), BuildAScript.settings.insertLine);
			}
		});

		GUIData.mntmWalkToCurrent = new JMenuItem("Walk to Current Position");
		GUIData.mnWalking.add(GUIData.mntmWalkToCurrent);
		GUIData.mntmWalkToCurrent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = script.myPosition().getX();
				i[1] = script.myPosition().getY();
				i[2] = script.myPosition().getZ();
				BuildAScript.settings.addNewAction(new Action(script, 43, i), BuildAScript.settings.insertLine);
			}
		});
	}

	public void addOtherMenu() {
		GUIData.mnOther = new JMenu("Other");
		GUIData.mnAddAction.add(GUIData.mnOther);
		//
		GUIData.mntmHopWorld = new JMenuItem("Hop Worlds");
		GUIData.mnOther.add(GUIData.mntmHopWorld);
		GUIData.mntmHopWorld.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] i = new int[5];
				i[0] = (int) (Math.random() * 40 + 338);
				BuildAScript.settings.addNewAction(new Action(script, 51, i), BuildAScript.settings.insertLine);
			}
		});
	}

	public void addOptionsMenu() {
		GUIData.mnOptions = new JMenu("Options");
		GUIData.menuBar.add(GUIData.mnOptions);
		//
		GUIData.mntmAddToIndex = new JMenuItem("Add to Line");
		GUIData.mnOptions.add(GUIData.mntmAddToIndex);
		GUIData.mntmAddToIndex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuildAScript.settings.insertLine = Integer.parseInt(JOptionPane.showInputDialog("Line:"));
			}
		});
		GUIData.mntmRemoveAction = new JMenuItem("Remove Action");
		GUIData.mnOptions.add(GUIData.mntmRemoveAction);
		GUIData.mntmRemoveAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuildAScript.settings.deleteLine(Integer.parseInt(JOptionPane.showInputDialog("Line to Delete:")));
			}
		});

		GUIData.mntmRemoveAllActions = new JMenuItem("Remove All Actions");
		GUIData.mnOptions.add(GUIData.mntmRemoveAllActions);
		GUIData.mntmRemoveAllActions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WorkshopData.actions.clear();
				WorkshopData.values.clear();
				BuildAScript.settings.fixList();
				BuildAScript.settings.insertLine = 0;
			}
		});
	}
}
