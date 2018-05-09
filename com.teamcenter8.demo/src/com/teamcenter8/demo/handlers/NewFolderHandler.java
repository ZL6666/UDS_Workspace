package com.teamcenter8.demo.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter8.demo.actions.NewMyFolderAction;

public class NewFolderHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		//MessageBox.post("我的第一个菜单", "提示", MessageBox.INFORMATION);
		AbstractAIFUIApplication abstractAIFUIApplication =  AIFUtility.getCurrentApplication(); 
		NewMyFolderAction newFolderAction = new NewMyFolderAction(abstractAIFUIApplication, null);
		new Thread(newFolderAction).start();
		return null;
	}

}
