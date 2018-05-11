package com.teamcenter11.demo.command;

import java.awt.Frame;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class CreateFolderCommand extends AbstractAIFCommand {
	private Frame parentFrame;
	private AbstractAIFApplication application;
	public InterfaceAIFComponent targerArray;
	public TCSession session;
	
	public CreateFolderCommand(Frame frame,AbstractAIFApplication abstractaifapplication){
		try {
			parentFrame = frame;
			application = abstractaifapplication;
			targerArray = application.getTargetComponent();
			session = (TCSession) abstractaifapplication.getSession();
			if(targerArray!=null){
				AbstractAIFDialog abstracttccommanddialog = new CreateFolderDialog(this,true);
				if(abstracttccommanddialog!=null){
					setRunnable(abstracttccommanddialog);
				}
			}else{
				MessageBox.post("��ѡ�����", "��ʾ", MessageBox.WARNING);
			}
		} catch (Exception e) {
			MessageBox.post(frame, e);
		}
	}
	
}