package com.teamcenter11.demo.command;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.aif.InterfaceAIFOperationListener;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.rac.util.iTextField;

public class CreateFolderDialog extends AbstractAIFDialog {
	
	private TCSession session = null;
	private TCComponent tccomponent = null;
	
	public CreateFolderDialog(CreateFolderCommand createFolderCommand,boolean b){
		session = createFolderCommand.session;
		tccomponent = (TCComponent) createFolderCommand.targerArray;
		initUI();
	}
	
	public void initUI(){
	    setTitle("创建文件夹对话框");
	    Dimension dimension = new Dimension();
	    dimension.setSize(300,70);
	    setPreferredSize(dimension);
	    JPanel parentPanel = new JPanel(new FlowLayout());
	    final iTextField itext = new iTextField(20);
	    JLabel label = new JLabel("文件夹名称:");
	    JButton button = new JButton("确定");
	    button.addActionListener(new ActionListener(){
	        @Override
	         public void actionPerformed(ActionEvent e) {
		    //调用Operatio	
	        	startOperation(itext.getText());
	         }
	     });
	    parentPanel.add(label);
	    parentPanel.add(itext);
	    parentPanel.add(button);
	    getContentPane().add(parentPanel);
	    pack();	
	    centerToScreen(1.0D, 0.75D);	
	}
	
	public void startOperation(String folderName){
		AbstractAIFOperation operation = new CreateFolderOperation(session, tccomponent,folderName);
		operation.addOperationListener(new InterfaceAIFOperationListener(){

			@Override
			public void endOperation() {
				MessageBox.post( "Folder创建完成", "提示",MessageBox.INFORMATION);
				
			}

			@Override
			public void startOperation(String s) {
				// TODO Auto-generated method stub
				
			}
			
		});
		session.queueExcludedOperation(operation);
	}
}
