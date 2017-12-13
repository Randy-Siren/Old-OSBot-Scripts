package org.goldengates.bluescales.gui;

import java.awt.SystemColor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.goldengates.bluescales.data.UserData;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private void btnStartActionListener() {
		UserData.foodName = txtFieldName.getText().trim();
		UserData.foodAmount = Integer.parseInt(spinnerAmt.getValue().toString());
		dispose();
	}

	public GUI() {
		setTitle("Golden Blue Dragon Scale Looter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtInstructions = new JTextPane();
		txtInstructions.setBackground(SystemColor.control);
		txtInstructions.setText("INSTRUCTIONS:\r\n\r\n- Start in Falador West Bank\r\nOR\r\n- "
				+ "Start anywhere in the path with teleport\r\n\r\n- Keep at least 100 Falador Teleport Tablets in bank"
				+ "\r\n- Have a Dusty Key in bank (if less than 70 Agility)\r\n- GET AN ANTI-FIRE SHIELD PLS TY");
		txtInstructions.setEditable(false);
		txtInstructions.setBounds(10, 11, 414, 147);
		contentPane.add(txtInstructions);

		lblFoodName = new JLabel("Food Name: ");
		lblFoodName.setBounds(10, 169, 73, 14);
		contentPane.add(lblFoodName);

		txtFieldName = new JTextField();
		txtFieldName.setBounds(74, 166, 86, 20);
		contentPane.add(txtFieldName);
		txtFieldName.setColumns(10);
		txtFieldName.setText("Monkfish"); // TODO: Fix Later

		lblAmount = new JLabel("Amount: ");
		lblAmount.setBounds(211, 169, 63, 14);
		contentPane.add(lblAmount);

		spinnerAmt = new JSpinner();
		spinnerAmt.setModel(new SpinnerNumberModel(0, 0, 27, 1));
		spinnerAmt.setBounds(263, 166, 51, 20);
		contentPane.add(spinnerAmt);
		spinnerAmt.setValue(5); // TODO: Fix Later

		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnStartActionListener();
			}
		});
		btnStart.setBounds(10, 221, 414, 29);
		contentPane.add(btnStart);
		
		setVisible(true);
	}

	private JPanel contentPane;
	private JTextPane txtInstructions;
	private JLabel lblFoodName;
	private JTextField txtFieldName;
	private JLabel lblAmount;
	private JSpinner spinnerAmt;
	private JButton btnStart;
}
