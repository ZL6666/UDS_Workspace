package com.uds.teamcenter11.demo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateItem extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateItem frame = new CreateItem();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CreateItem() {
		setTitle("创建Item");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 277, 148);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblItem = new JLabel("Item名称：");
		lblItem.setFont(new Font("宋体", Font.PLAIN, 12));
		lblItem.setBounds(33, 21, 76, 25);
		contentPane.add(lblItem);
		
		textField = new JTextField();
		textField.setBounds(127, 22, 87, 23);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton enterBtn = new JButton("确定");
		enterBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		enterBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		enterBtn.setBounds(43, 62, 59, 25);
		contentPane.add(enterBtn);
		
		JButton cancelBtn = new JButton("取消");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		cancelBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		cancelBtn.setBounds(148, 62, 59, 25);
		contentPane.add(cancelBtn);
	}
}
