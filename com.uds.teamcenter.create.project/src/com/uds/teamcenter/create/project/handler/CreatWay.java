package com.uds.teamcenter.create.project.handler;

import javax.swing.JFrame;

import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCSession;
import com.uds.teamcenter.project.ui.MainPageUI;
import com.uds.teamcenter.project.ui.MainPageUI1;


public class CreatWay {
 public CreatWay(TCSession session, InterfaceAIFComponent selcom, String createInClient, String[] uid, String folderType, String linkProject){
	 if ("0".equals(linkProject)) {
			MainPageUI1 mainPageUI = new MainPageUI1(session, selcom, uid, folderType, createInClient);
			mainPageUI.setTitle("项目管理 - 创建项目结构文档");
			mainPageUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			mainPageUI.setVisible(true);
			mainPageUI.setLocationRelativeTo(null);
			mainPageUI.setAlwaysOnTop(true);
			}
	 else
	 {
				MainPageUI mainPageUI = new MainPageUI(session, selcom, uid,folderType,createInClient);
				mainPageUI.setTitle("项目管理 - 创建项目结构文档");
				mainPageUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				mainPageUI.setVisible(true);
			    mainPageUI.setLocationRelativeTo(null);
				mainPageUI.setAlwaysOnTop(true);
			}
 }
}
