package com.teamcenter8.demo.actions;

import java.awt.Frame;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter8.demo.command.newmyfolder.NewMyFolderCommand;

public class NewMyFolderAction extends AbstractAIFAction {

	public NewMyFolderAction(AbstractAIFApplication arg0, Frame arg1) {
		super(arg0, arg1, null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
	    try{
	        AbstractAIFCommand abstractaifcommand = new NewMyFolderCommand(
						parent, application
				);

	         abstractaifcommand.executeModal();

	    }catch(Exception exception){
		MessageBox.post(parent, exception);
	    }


	}

}
