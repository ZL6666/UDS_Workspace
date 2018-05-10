package com.uds.teamcenter.project.ui;

import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
import com.uds.module.createitem.common.ItemCreationManager;

public class MainPageUI1 extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AIFComponentContext[] muban = null;
	public JPanel p1 = null;
	public JPanel p2 = null;
	public TCComponentFolder tcFolder;
	public JPanel jp = null;
	public TCComponentFolder xinjianmuban;
	public JButton next = null;
	public JButton cancel = null;
	public JLabel p1jl1 = null;
	public JLabel p1jl2 = null;
	public JLabel p1jl3 = null;
	public TextField p1t11 = null;
	public TextField p1t12 = null;
	public TextField p1t13 = null;
	public TextField p1t14 = null;
	public JButton p1jb1 = null;
	public JLabel p2jl = null;
	@SuppressWarnings("rawtypes")
	public JComboBox p2b = null;
	public String panduanuid = null;
	public String folderType;
	public TCComponent[] tcComponent;
	public TCComponent cmp;
	public String panduanname;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MainPageUI1(final TCSession session, final InterfaceAIFComponent selcom, String[] uid,
			final String Type, final String createInClient) {
		folderType = Type;

		setSize(600, 250);
		jp = new JPanel();
		p1 = new JPanel();
		p2 = new JPanel();
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		jp.setLayout(null);
		jp.setSize(500, 250);

		p2.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black, 1), "指定模板", 0, 0,
				null, Color.BLACK));
		p2.setBounds(40, 20, 500, 100);
		p2.setLayout(null);

		p2jl = new JLabel("项目文件夹结构模板");
		p2jl.setBounds(10, 35, 200, 40);
		p2.add(p2jl);

		p2b = new JComboBox();
		try {
			tcComponent = session.stringToComponent(uid);

			for (int i = 0; i < tcComponent.length; i++) {

				if (tcComponent[i] instanceof TCComponentFolder) {
					try {
						p2b.addItem(tcComponent[i].getProperty("object_name"));
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}
		} catch (TCException e1) {
			e1.printStackTrace();
		}
		p2b.setBounds(250, 40, 180, 30);
		p2b.setSelectedItem(null);
		p2.add(p2b);

		next = new JButton();
		next.setText("下一步");
		next.setBounds(140, 140, 120, 40);

		cancel = new JButton();
		cancel.setText("取消");
		cancel.setBounds(340, 140, 120, 40);

		jp.add(p2);
		jp.add(next);
		jp.add(cancel);

		getContentPane().add(jp);

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionevent) {
				dispose();
			}
		});

		next.addActionListener(new ActionListener() {

			@SuppressWarnings({ "unused", "static-access", "deprecation" })
			@Override
			public void actionPerformed(ActionEvent actionevent) {

				if (p2b.getSelectedIndex() < 0) {
					MessageBox.post("请选择项目文件夹结构模板", "错误提示", 2);
					return;
				} else {

					cmp = (TCComponent) tcComponent[p2b.getSelectedIndex()];

					try {
						TCComponentFolder tcComponentFolder = new TCComponentFolder();
						TCComponentFolder homeFloder = tcComponentFolder.getHomeFolder(session);
						AbstractAIFUIApplication app = AIFUtility.getCurrentApplication();
						
						
						TCComponentFolder sel = (TCComponentFolder)selcom;
						panduanname = ((TCComponentFolder) cmp).getStringProperty("object_name");
						
						dispose();
						
						ItemCreationManager opr = new ItemCreationManager(cmp,sel,session,panduanname,cmp.getType(),createInClient);

					} catch (TCException e) {
						e.printStackTrace();
					}

				}
			}
		});

	}

}
