package com.uds.teamcenter.project.Service;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;


public class selectxml {

	/**
	 * 
	 */

	public static String xmlName = "default";
	
	public static String select(String[] xmls){
		final JFrame jf = new JFrame();
		
		jf.setSize(200, 200);
		jf.setLayout(null);
		jf.setLocationRelativeTo(null);
		final JComboBox<String> jcb = new JComboBox<String>();
		
		for(String temp : xmls){
			jcb.addItem(temp);
		}
		jcb.setVisible(true);
		jcb.setBounds(5, 5, 130, 50);
		jf.getContentPane().add(jcb);
		JButton jb = new JButton();
		jb.setText("È·¶¨");
		jb.setVisible(true);
		jb.setBounds(10, 75, 100, 50);
		jb.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				xmlName = jcb.getSelectedItem().toString();
				jf.dispose();
			}
		});
		jf.getContentPane().add(jb);
		jf.setVisible(true);
		
		return xmlName;

	}

	
	public static void main(String[] args) {
		String xmls[] = {"123","234","345"};
		String xxx = selectxml.select(xmls);
		System.out.println("123:::::"+ xxx);
	}
}
