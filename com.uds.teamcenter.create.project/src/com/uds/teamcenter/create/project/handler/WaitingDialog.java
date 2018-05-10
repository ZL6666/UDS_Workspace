package com.uds.teamcenter.create.project.handler;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;

import com.teamcenter.rac.aif.AbstractAIFDialog;

public class WaitingDialog extends AbstractAIFDialog 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Panel panel ;
	
	public WaitingDialog(Frame arg0, String arg1, String message)
	{
		super(arg0, arg1);
		this.setModal(false);
		
		this.setAlwaysOnTop(true);
		//this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		panel = new Panel(new BorderLayout());
		
		Label label = new Label(message);
		panel.add(BorderLayout.CENTER, label);
		
		panel.setPreferredSize(new Dimension(350,60));
		
		this.getContentPane().add(panel);
		
		centerToScreen();

		pack();
		
	}

}