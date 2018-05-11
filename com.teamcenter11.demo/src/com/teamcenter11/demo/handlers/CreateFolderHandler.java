package com.teamcenter11.demo.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter11.demo.actions.CreateFolderAction;

public class CreateFolderHandler extends AbstractHandler {
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
//		MessageBox.post("我的第一个菜单项", "提示", MessageBox.INFORMATION);
		//获得teamcenter中的应用程序，也就是结构管理器
		AbstractAIFUIApplication abstractAIFUIApplication = AIFUtility.getCurrentApplication();
		CreateFolderAction createFolderAction = new CreateFolderAction(abstractAIFUIApplication,null);
		new Thread(createFolderAction).start();
		return null;
	}
}
