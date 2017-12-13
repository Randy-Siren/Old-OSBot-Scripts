package slayer.gui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import javax.swing.JList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JEditorPane;
import javax.swing.SpinnerNumberModel;

import slayer.AIOSlayer;
import slayer.data.GlobalTaskRelated;
import slayer.data.GuiComponents;
import slayer.data.MiscVar;

public class MainGUI extends JFrame {

	private JPanel cPane;
	AIOSlayer slayer;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI frame = new MainGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainGUI() {
		slayer = new AIOSlayer();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 492);
		cPane = new JPanel();
		cPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(cPane);
		cPane.setLayout(null);
		setResizable(false);
		setTitle("AIO Slayer GUI Setup");
		initComponents();
		addActionListeners();
	}

	public void initComponents() {
		GuiComponents.tPane = new JTabbedPane(JTabbedPane.TOP);
		GuiComponents.tPane.setBounds(0, 0, 432, 407);
		cPane.add(GuiComponents.tPane);

		GuiComponents.mainSettingsTab = new JPanel();
		GuiComponents.tPane.addTab("Main Settings", null,
				GuiComponents.mainSettingsTab, null);
		GuiComponents.mainSettingsTab.setLayout(null);

		GuiComponents.slayerMasterLabel = new JLabel("Slayer Master:");
		GuiComponents.slayerMasterLabel.setFont(new Font("Cambria", Font.BOLD,
				16));
		GuiComponents.slayerMasterLabel.setBounds(12, 23, 107, 16);
		GuiComponents.mainSettingsTab.add(GuiComponents.slayerMasterLabel);

		GuiComponents.slayerMasterCBox = new JComboBox<String>();
		GuiComponents.slayerMasterCBox
				.setModel(new DefaultComboBoxModel<String>(
						new String[] {
								"Turael:..........................................No Requirements",
								"Mazchna:...............................................20+ Combat",
								"Vannaka:................................................40+ Combat",
								"Chaeldar:................................................70+ Combat",
								"Duradel:...................100+ Combat and 50+ Slayer" }));
		GuiComponents.slayerMasterCBox.setFont(new Font("Cambria", Font.PLAIN,
				13));
		GuiComponents.slayerMasterCBox.setBounds(131, 21, 284, 22);
		GuiComponents.mainSettingsTab.add(GuiComponents.slayerMasterCBox);

		GuiComponents.seperator1 = new JSeparator();
		GuiComponents.seperator1.setBounds(12, 54, 403, 2);
		GuiComponents.mainSettingsTab.add(GuiComponents.seperator1);

		GuiComponents.prayersCHBox = new JCheckBox("Use Overhead Prayers");
		GuiComponents.prayersCHBox.setFont(new Font("Cambria", Font.PLAIN, 13));
		GuiComponents.prayersCHBox.setBounds(12, 65, 155, 25);
		GuiComponents.mainSettingsTab.add(GuiComponents.prayersCHBox);

		GuiComponents.lootingCHBox = new JCheckBox("Enable Looting");
		GuiComponents.lootingCHBox.setFont(new Font("Cambria", Font.PLAIN, 13));
		GuiComponents.lootingCHBox.setBounds(12, 95, 155, 25);
		GuiComponents.mainSettingsTab.add(GuiComponents.lootingCHBox);

		GuiComponents.b2pCHBox = new JCheckBox("Use B2P Tabs");
		GuiComponents.b2pCHBox.setFont(new Font("Cambria", Font.PLAIN, 13));
		GuiComponents.b2pCHBox.setBounds(12, 125, 155, 25);
		GuiComponents.mainSettingsTab.add(GuiComponents.b2pCHBox);

		GuiComponents.lootAmmoCHBox = new JCheckBox("Loot Equiped Ammo");
		GuiComponents.lootAmmoCHBox
				.setFont(new Font("Cambria", Font.PLAIN, 13));
		GuiComponents.lootAmmoCHBox.setBounds(171, 65, 155, 25);
		GuiComponents.mainSettingsTab.add(GuiComponents.lootAmmoCHBox);

		GuiComponents.fairyRingsCHBox = new JCheckBox("Use Fairy Rings");
		GuiComponents.fairyRingsCHBox.setFont(new Font("Cambria", Font.PLAIN,
				13));
		GuiComponents.fairyRingsCHBox.setBounds(171, 95, 155, 25);
		GuiComponents.mainSettingsTab.add(GuiComponents.fairyRingsCHBox);

		GuiComponents.taskSkippingCHBox = new JCheckBox("Enable Task Skipping");
		GuiComponents.taskSkippingCHBox.setFont(new Font("Cambria", Font.PLAIN,
				13));
		GuiComponents.taskSkippingCHBox.setBounds(171, 125, 155, 25);
		GuiComponents.mainSettingsTab.add(GuiComponents.taskSkippingCHBox);

		GuiComponents.seperator2 = new JSeparator();
		GuiComponents.seperator2.setBounds(12, 159, 403, 2);
		GuiComponents.mainSettingsTab.add(GuiComponents.seperator2);

		GuiComponents.foodLabel = new JLabel("Food: ");
		GuiComponents.foodLabel.setFont(new Font("Cambria", Font.BOLD, 13));
		GuiComponents.foodLabel.setBounds(12, 174, 37, 16);
		GuiComponents.mainSettingsTab.add(GuiComponents.foodLabel);

		GuiComponents.foodCBox = new JComboBox<String>();
		GuiComponents.foodCBox.setFont(new Font("Cambria", Font.PLAIN, 13));
		GuiComponents.foodCBox.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Tuna", "Lobster", "Swordfish", "Monkfish",
						"Shark" }));
		GuiComponents.foodCBox.setBounds(61, 171, 168, 22);
		GuiComponents.mainSettingsTab.add(GuiComponents.foodCBox);

		GuiComponents.eatAtLabel = new JLabel("Eat At:");
		GuiComponents.eatAtLabel.setFont(new Font("Cambria", Font.BOLD, 13));
		GuiComponents.eatAtLabel.setBounds(12, 204, 40, 16);
		GuiComponents.mainSettingsTab.add(GuiComponents.eatAtLabel);

		GuiComponents.eatAtValueSlider = new JSlider();
		GuiComponents.eatAtValueSlider.setBounds(59, 200, 170, 37);
		GuiComponents.mainSettingsTab.add(GuiComponents.eatAtValueSlider);

		GuiComponents.sliderValueLabel = new JLabel("50");
		GuiComponents.sliderValueLabel.setFont(new Font("Cambria", Font.PLAIN,
				13));
		GuiComponents.sliderValueLabel.setBounds(213, 234, 25, 16);
		GuiComponents.mainSettingsTab.add(GuiComponents.sliderValueLabel);

		GuiComponents.lootListLabel = new JLabel(
				"Loot IDs (Seperate with \",\")");
		GuiComponents.lootListLabel.setFont(new Font("Cambria", Font.BOLD, 13));
		GuiComponents.lootListLabel.setBounds(38, 239, 164, 16);
		GuiComponents.mainSettingsTab.add(GuiComponents.lootListLabel);

		GuiComponents.lootListPane = new JEditorPane();
		GuiComponents.lootListPane.setBounds(12, 260, 217, 104);
		GuiComponents.mainSettingsTab.add(GuiComponents.lootListPane);
		GuiComponents.lootListPane.setEditable(false);
		GuiComponents.lootListPane.setText("Disabled");

		GuiComponents.superAttackCHBox = new JCheckBox("Super Attack");
		GuiComponents.superAttackCHBox.setFont(new Font("Cambria", Font.PLAIN,
				13));
		GuiComponents.superAttackCHBox.setBounds(237, 170, 115, 25);
		GuiComponents.mainSettingsTab.add(GuiComponents.superAttackCHBox);

		GuiComponents.superAttackSpinner = new JSpinner();
		GuiComponents.superAttackSpinner.setModel(new SpinnerNumberModel(0, 0,
				8, 1));
		GuiComponents.superAttackSpinner.setBounds(360, 171, 55, 22);
		GuiComponents.mainSettingsTab.add(GuiComponents.superAttackSpinner);
		GuiComponents.superAttackSpinner.setEnabled(false);

		GuiComponents.superStrCHBox = new JCheckBox("Super Strength");
		GuiComponents.superStrCHBox
				.setFont(new Font("Cambria", Font.PLAIN, 13));
		GuiComponents.superStrCHBox.setBounds(237, 200, 115, 25);
		GuiComponents.mainSettingsTab.add(GuiComponents.superStrCHBox);

		GuiComponents.superStrSpinner = new JSpinner();
		GuiComponents.superStrSpinner.setModel(new SpinnerNumberModel(0, 0, 8,
				1));
		GuiComponents.superStrSpinner.setBounds(360, 201, 55, 22);
		GuiComponents.mainSettingsTab.add(GuiComponents.superStrSpinner);
		GuiComponents.superStrSpinner.setEnabled(false);

		GuiComponents.superDefenceCHBox = new JCheckBox("Super Defence");
		GuiComponents.superDefenceCHBox.setFont(new Font("Cambria", Font.PLAIN,
				13));
		GuiComponents.superDefenceCHBox.setBounds(237, 230, 115, 25);
		GuiComponents.mainSettingsTab.add(GuiComponents.superDefenceCHBox);

		GuiComponents.superDefenceSpinner = new JSpinner();
		GuiComponents.superDefenceSpinner.setModel(new SpinnerNumberModel(0, 0,
				8, 1));
		GuiComponents.superDefenceSpinner.setBounds(360, 231, 55, 22);
		GuiComponents.mainSettingsTab.add(GuiComponents.superDefenceSpinner);
		GuiComponents.superDefenceSpinner.setEnabled(false);

		GuiComponents.rangedCHBox = new JCheckBox("Ranged");
		GuiComponents.rangedCHBox.setFont(new Font("Cambria", Font.PLAIN, 13));
		GuiComponents.rangedCHBox.setBounds(237, 260, 115, 25);
		GuiComponents.mainSettingsTab.add(GuiComponents.rangedCHBox);

		GuiComponents.rangedSpinner = new JSpinner();
		GuiComponents.rangedSpinner
				.setModel(new SpinnerNumberModel(0, 0, 8, 1));
		GuiComponents.rangedSpinner.setBounds(360, 261, 55, 22);
		GuiComponents.mainSettingsTab.add(GuiComponents.rangedSpinner);
		GuiComponents.rangedSpinner.setEnabled(false);

		GuiComponents.loadSettingsButton = new JButton("Load Settings");
		GuiComponents.loadSettingsButton.setFont(new Font("Cambria",
				Font.PLAIN, 14));
		GuiComponents.loadSettingsButton.setBounds(237, 301, 178, 25);
		GuiComponents.mainSettingsTab.add(GuiComponents.loadSettingsButton);

		GuiComponents.saveSettingsButton = new JButton("Save Settings");
		GuiComponents.saveSettingsButton.setFont(new Font("Cambria",
				Font.PLAIN, 14));
		GuiComponents.saveSettingsButton.setBounds(237, 339, 178, 25);
		GuiComponents.mainSettingsTab.add(GuiComponents.saveSettingsButton);

		GuiComponents.taskSkippingTab = new JPanel();
		GuiComponents.tPane.addTab("Task Skipping", null,
				GuiComponents.taskSkippingTab, null);
		GuiComponents.taskSkippingTab.setLayout(null);
		GuiComponents.tPane.setEnabledAt(1, false);

		GuiComponents.taskSkippingList = new JList<String>();
		GuiComponents.taskSkippingList.setMinimumSize(new Dimension(400, 325));
		GuiComponents.taskSkippingListScrollPane = new JScrollPane(
				GuiComponents.taskSkippingList);
		GuiComponents.taskSkippingListScrollPane.setBounds(12, 13, 400, 325);
		GuiComponents.taskSkippingListScrollPane
				.setViewportView(GuiComponents.taskSkippingList);
		GuiComponents.taskSkippingTab
				.add(GuiComponents.taskSkippingListScrollPane);
		GuiComponents.taskSkippingList.setListData(MiscVar.turaelNPCList);

		GuiComponents.holdCrtlLabel = new JLabel(
				"Hold Crtl for Multiple Selections");
		GuiComponents.holdCrtlLabel.setFont(new Font("Cambria", Font.BOLD, 14));
		GuiComponents.holdCrtlLabel.setBounds(116, 350, 199, 16);
		GuiComponents.taskSkippingTab.add(GuiComponents.holdCrtlLabel);

		GuiComponents.taskSettingsTab = new JPanel();
		GuiComponents.tPane.addTab("Task Settings", null,
				GuiComponents.taskSettingsTab, null);
		GuiComponents.taskSettingsTab.setLayout(null);

		GuiComponents.taskSettingsList = new JList<String>();
		GuiComponents.taskSettingsList.setMinimumSize(new Dimension(200, 350));
		GuiComponents.taskSettingsListScrollPane = new JScrollPane(
				GuiComponents.taskSettingsList);
		GuiComponents.taskSettingsListScrollPane.setBounds(12, 13, 200, 350);
		GuiComponents.taskSettingsListScrollPane
				.setViewportView(GuiComponents.taskSettingsList);
		GuiComponents.taskSettingsTab
				.add(GuiComponents.taskSettingsListScrollPane);
		GuiComponents.taskSettingsList.setListData(MiscVar.turaelNPCList);

		GuiComponents.helmetLabel = new JLabel("Helmet:");
		GuiComponents.helmetLabel.setFont(new Font("Cambria", Font.PLAIN, 13));
		GuiComponents.helmetLabel.setBounds(231, 43, 56, 16);
		GuiComponents.taskSettingsTab.add(GuiComponents.helmetLabel);

		GuiComponents.helmetTextField = new JTextField();
		GuiComponents.helmetTextField.setBounds(299, 40, 116, 22);
		GuiComponents.taskSettingsTab.add(GuiComponents.helmetTextField);
		GuiComponents.helmetTextField.setColumns(10);

		GuiComponents.torsoLabel = new JLabel("Torso:");
		GuiComponents.torsoLabel.setFont(new Font("Cambria", Font.PLAIN, 13));
		GuiComponents.torsoLabel.setBounds(231, 72, 56, 16);
		GuiComponents.taskSettingsTab.add(GuiComponents.torsoLabel);

		GuiComponents.torsoTextField = new JTextField();
		GuiComponents.torsoTextField.setBounds(299, 69, 116, 22);
		GuiComponents.taskSettingsTab.add(GuiComponents.torsoTextField);
		GuiComponents.torsoTextField.setColumns(10);

		GuiComponents.legsLabel = new JLabel("Legs:");
		GuiComponents.legsLabel.setFont(new Font("Cambria", Font.PLAIN, 13));
		GuiComponents.legsLabel.setBounds(231, 101, 56, 16);
		GuiComponents.taskSettingsTab.add(GuiComponents.legsLabel);

		GuiComponents.legsTextField = new JTextField();
		GuiComponents.legsTextField.setBounds(299, 98, 116, 22);
		GuiComponents.taskSettingsTab.add(GuiComponents.legsTextField);
		GuiComponents.legsTextField.setColumns(10);

		GuiComponents.shieldLabel = new JLabel("Shield:");
		GuiComponents.shieldLabel.setFont(new Font("Cambria", Font.PLAIN, 13));
		GuiComponents.shieldLabel.setBounds(231, 130, 56, 16);
		GuiComponents.taskSettingsTab.add(GuiComponents.shieldLabel);

		GuiComponents.shieldTextField = new JTextField();
		GuiComponents.shieldTextField.setBounds(299, 127, 116, 22);
		GuiComponents.taskSettingsTab.add(GuiComponents.shieldTextField);
		GuiComponents.shieldTextField.setColumns(10);

		GuiComponents.weaponLabel = new JLabel("Weapon:");
		GuiComponents.weaponLabel.setFont(new Font("Cambria", Font.PLAIN, 13));
		GuiComponents.weaponLabel.setBounds(231, 159, 56, 16);
		GuiComponents.taskSettingsTab.add(GuiComponents.weaponLabel);

		GuiComponents.weaponTextField = new JTextField();
		GuiComponents.weaponTextField.setBounds(299, 156, 116, 22);
		GuiComponents.taskSettingsTab.add(GuiComponents.weaponTextField);
		GuiComponents.weaponTextField.setColumns(10);

		GuiComponents.enterIDsOnlyLabel = new JLabel("Enter IDs Only");
		GuiComponents.enterIDsOnlyLabel.setFont(new Font("Cambria", Font.PLAIN,
				13));
		GuiComponents.enterIDsOnlyLabel.setBounds(283, 205, 81, 16);
		GuiComponents.taskSettingsTab.add(GuiComponents.enterIDsOnlyLabel);

		GuiComponents.addCurrentGearButton = new JButton("Add Current Gear");
		GuiComponents.addCurrentGearButton.setFont(new Font("Cambria",
				Font.PLAIN, 14));
		GuiComponents.addCurrentGearButton.setBounds(231, 263, 184, 25);
		GuiComponents.taskSettingsTab.add(GuiComponents.addCurrentGearButton);

		GuiComponents.applyToSelectionButton = new JButton("Apply to Selection");
		GuiComponents.applyToSelectionButton.setFont(new Font("Cambria",
				Font.PLAIN, 14));
		GuiComponents.applyToSelectionButton.setBounds(231, 301, 184, 25);
		GuiComponents.taskSettingsTab.add(GuiComponents.applyToSelectionButton);

		GuiComponents.applyToAllButton = new JButton("Apply To All");
		GuiComponents.applyToAllButton.setFont(new Font("Cambria", Font.PLAIN,
				13));
		GuiComponents.applyToAllButton.setBounds(231, 339, 184, 25);
		GuiComponents.taskSettingsTab.add(GuiComponents.applyToAllButton);

		GuiComponents.startScriptButton = new JButton("Start Script");
		GuiComponents.startScriptButton.setFont(new Font("Cambria", Font.BOLD,
				15));
		GuiComponents.startScriptButton.setBounds(10, 416, 410, 25);
		cPane.add(GuiComponents.startScriptButton);
	}

	public void addActionListeners() {
		GuiComponents.slayerMasterCBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GlobalTaskRelated.masterName = GuiComponents.slayerMasterCBox
						.getSelectedItem().toString().split(":")[0];
				if (GlobalTaskRelated.masterName.equals("Turael")) {
					GuiComponents.taskSkippingList
							.setListData(MiscVar.turaelNPCList);
					GuiComponents.taskSettingsList
							.setListData(MiscVar.turaelNPCList);
				}

			}
		});
		GuiComponents.prayersCHBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GuiComponents.lootingCHBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GuiComponents.lootingCHBox.isSelected()) {
					GuiComponents.lootListPane.setEditable(true);
					GuiComponents.lootListPane.setText("");
				} else {
					GuiComponents.lootListPane.setEditable(false);
					GuiComponents.lootListPane.setText("Disabled");
				}
			}
		});
		GuiComponents.b2pCHBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GuiComponents.lootAmmoCHBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GuiComponents.fairyRingsCHBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GuiComponents.taskSkippingCHBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GuiComponents.taskSkippingCHBox.isSelected())

					GuiComponents.tPane.setEnabledAt(1, true);
				else
					GuiComponents.tPane.setEnabledAt(1, false);
			}
		});
		GuiComponents.foodCBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GlobalTaskRelated.foodString = GuiComponents.foodCBox
						.getSelectedItem().toString();
			}
		});
		GuiComponents.eatAtValueSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				GuiComponents.sliderValueLabel
						.setText(GuiComponents.eatAtValueSlider.getValue() + "");
			}
		});
		GuiComponents.superAttackCHBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GuiComponents.superAttackCHBox.isSelected())
					GuiComponents.superAttackSpinner.setEnabled(true);
				else
					GuiComponents.superAttackSpinner.setEnabled(false);
			}
		});
		GuiComponents.superStrCHBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GuiComponents.superStrCHBox.isSelected())
					GuiComponents.superStrSpinner.setEnabled(true);
				else
					GuiComponents.superStrSpinner.setEnabled(false);
			}
		});
		GuiComponents.superDefenceCHBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GuiComponents.superDefenceCHBox.isSelected())
					GuiComponents.superDefenceSpinner.setEnabled(true);
				else
					GuiComponents.superDefenceSpinner.setEnabled(false);
			}
		});
		GuiComponents.rangedCHBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GuiComponents.rangedCHBox.isSelected())
					GuiComponents.rangedSpinner.setEnabled(true);
				else
					GuiComponents.rangedSpinner.setEnabled(false);
			}
		});
		GuiComponents.startScriptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyLootListSettings();
				GlobalTaskRelated.eatAtHP = Integer
						.parseInt(GuiComponents.sliderValueLabel.getText());
				GlobalTaskRelated.superAttAmt = Integer
						.parseInt(GuiComponents.superAttackSpinner.getValue()
								.toString());
				GlobalTaskRelated.superStrAmt = Integer
						.parseInt(GuiComponents.superStrSpinner.getValue()
								.toString());
				GlobalTaskRelated.superDefAmt = Integer
						.parseInt(GuiComponents.superDefenceSpinner.getValue()
								.toString());
				GlobalTaskRelated.rngAmt = Integer
						.parseInt(GuiComponents.rangedSpinner.getValue()
								.toString());
			}
		});
	}

	public static void applyLootListSettings() {
		String lootList = "";
		for (String s : GuiComponents.lootListPane.getText().trim().split(" "))
			lootList += s;
		int index = 0;
		for (String s : lootList.trim().split(",")) {
			try {
				GlobalTaskRelated.lootArray[index] = Integer.parseInt(s);
				System.out.println(GlobalTaskRelated.lootArray[index]);
				index++;
			} catch (NumberFormatException n) {
				/*
				 * slayer.log("Error while parsing loot list, restart" +
				 * "the script and confirm there are no letters in the" +
				 * "loot list."); try { slayer.stop(); } catch
				 * (InterruptedException e1) { // TODO Auto-generated catch
				 * block e1.printStackTrace(); }
				 */
			}

		}
	}
}