package com.teamcenter8.demo.command.newmyfolder;

import java.awt.Frame;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class NewMyFolderCommand extends AbstractAIFCommand {
	private Frame parentFrame;
	private AbstractAIFApplication application;
	public  InterfaceAIFComponent targetArray;
	public TCSession session;
	public NewMyFolderCommand(Frame frame, AbstractAIFApplication abstractaifapplication){
		try{
			parentFrame = frame;
			application = abstractaifapplication;
			targetArray = application.getTargetComponent();
			session = (TCSession) abstractaifapplication.getSession();
			if(targetArray != null){
				AbstractAIFDialog abstracttccommanddialog = new   NewMyFolderDialog(this,true);
				if(abstracttccommanddialog != null){
					setRunnable(abstracttccommanddialog);
				} 
			}else{
				MessageBox.post("请选择对象", "提示 ",MessageBox.WARNING);
			}
		}catch(Exception exception){
			MessageBox.post(frame, exception);
		}
	}

}
