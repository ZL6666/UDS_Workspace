package com.estun.schedule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.kernel.TCComponentSchedule;
import com.teamcenter.rac.kernel.TCSession;

public class changeRoleUI extends AbstractAIFDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -773992661402503397L;
	
	TCComponentSchedule schedule = null;
	TCSession session = null;
	
	public changeRoleUI(TCComponentSchedule schedule, TCSession session) {
		this.schedule = schedule;
		this.session = session;
	}

	/*public void screat(){
		this.setTitle("请输入用户登录密码");
		this.setLayout(null);
		this.setSize(300, 200);
		
		JTextField jtf = new JTextField("密码");
		jtf.setBounds(25, 20, 50, 50);
		jtf.setVisible(true);
		jtf.setBorder(null);
		jtf.setEditable(false);
		
		final JTextField pwd = new JTextField();
		pwd.setBounds(90, 20, 100, 50);
		jtf.setVisible(true);
		
		JButton ok = new JButton("确认");
		ok.setBounds(20, 80, 100, 50);
		ok.setVisible(true);
		this.setLocationRelativeTo(null);
//		this.setAlwaysOnTop(true);
		ok.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				changeRoleUI.this.password = pwd.getText();
				changeRoleUI.this.getContentPane().removeAll();
				changeRoleUI.this.dispose();
				UI();
			}
		});
		
		JButton cancel = new JButton("取消");
		cancel.setBounds(150, 80, 100, 50);
		cancel.setVisible(true);
		
		cancel.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				changeRoleUI.this.getContentPane().removeAll();
				changeRoleUI.this.dispose();
			}
		});
		
		this.getContentPane().add(jtf);
		this.getContentPane().add(pwd);
		this.getContentPane().add(ok);
		this.getContentPane().add(cancel);
		this.setVisible(true);
		
		
	}*/
	
	public void UI(){
		this.setTitle("批量替换任务角色");
		this.setLayout(null);
		this.setSize(600, 400);
		final JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(0);
		jfc.setFileFilter(new FileNameExtensionFilter("支持的文件类型：*.xls,*.xlsx",new String[] { "xls", "xlsx" }));
		jfc.setApproveButtonText("确定");
		jfc.setBounds(0, 0, 580, 360);
		jfc.setVisible(true);
		this.getContentPane().add(jfc);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		jfc.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				if (event.getActionCommand().equals("ApproveSelection")) {
					File file = jfc.getSelectedFile();
					changeRoleUI.this.setAlwaysOnTop(false);
					changeRoleAction cAction = new changeRoleAction(file, schedule, session);
					cAction.change();
					changeRoleUI.this.dispose();
					
				} else if (event.getActionCommand().equals("CancelSelection")) {
					changeRoleUI.this.dispose();
				}
			}
		});
		
	}

}
