package com.uds.teamcenter.project.ui;


import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentQuery;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
import com.uds.module.createitem.common.ItemCreationManager;
import com.uds.teamcenter.project.common.SearchUtils;


public class MainPageUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel p1 = null;
	private JPanel p2 = null;
	public  AIFComponentContext[] muban =null;
	private JPanel jp = null;
	public TCComponentFolder tcFolder;
	private JButton next = null;
	private JButton cancel = null;
	private JLabel p1jl1 = null;
	private JLabel p1jl2 = null;
	private JLabel p1jl3 = null;
	private TextField p1t11 = null;
	private TextField p1t12 = null;
	private TextField p1t13 = null;
	private JTable p1tb = null;
	private JButton p1jb1 = null;
	private JLabel p2jl = null;
	@SuppressWarnings("rawtypes")
	private JComboBox p2b = null;
	private JScrollPane scroll =null;
	private  int tbseclet;
	private TCComponent[] resultComponents ;
	public String panduanname;
	public TCComponent cmp;
	public TCComponent[] tcComponent;
	public String folderType;
	
	
	
	
	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	public MainPageUI(final TCSession session, final InterfaceAIFComponent selcom, String[] uid,
			final String Type, final String createInClient) {
		folderType = Type;

		setSize(600, 500);
		jp = new JPanel();
		p1 = new JPanel();
		p2 = new JPanel();
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		jp.setLayout(null);
		jp.setSize(500, 500);
		p1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 1), "指定项目", 0, 0,
				null, Color.BLACK));
		p1.setBounds(40, 25, 500, 250);
		p1.setLayout(null);

		p1jl1 = new JLabel("项目ID");
		p1jl1.setBounds(10, 30, 50, 40);
		p1.add(p1jl1);

		p1t11 = new TextField();
		p1t11.setBounds(60, 40, 100, 20);
		p1.add(p1t11);
		
		p1jl2=new JLabel("项目名称");
		p1jl2.setBounds(210,30,70,40);
		p1.add(p1jl2);
		
		p1t12=new TextField();
		p1t12.setBounds(280, 40, 100, 20);
		p1.add(p1t12);
		
		p1jl3=new JLabel("创建者");
		p1jl3.setBounds(10,70,50,40);
		p1.add(p1jl3);
		
		p1t13=new TextField();
		p1t13.setBounds(60, 80, 160, 20);
		p1.add(p1t13);

		p1jb1 = new JButton("查询");
		p1jb1.setBounds(400, 80, 80, 20);
		p1.add(p1jb1);
		
		
		
		String[] columnNames = { "项目 ID", "项目名称", "创建者"};
		Object[][] dataObject = new Object[0][3];
		DefaultTableModel newTableModel = new DefaultTableModel(dataObject, columnNames) ;	
		p1tb =new JTable(newTableModel)
				{

					@Override
					public boolean isCellEditable(int i, int j) {
						return false;
					}
			
				}; 
		scroll = new JScrollPane(p1tb);
		scroll.setBounds(10,130,450,100);
		p1.add(scroll);
		
		
		p2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 1), "指定模板", 0, 0,
				null, Color.BLACK));
		p2.setBounds(40, 287, 500, 100);
		p2.setLayout(null);
  
		p2jl =new JLabel("项目文件夹结构模板");
		p2jl.setBounds(10,35,200,40);
		p2.add(p2jl);
		
		
		p2b =new JComboBox();

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
		
		p2b.setBounds(250,40,180,30);
		p2b.setSelectedItem(null);
		p2.add(p2b);
		
		next = new JButton();
		next.setText("下一步");
		next.setBounds(140, 400, 120, 40);

		cancel = new JButton();
		cancel.setText("取消");
		cancel.setBounds(340, 400, 120, 40);

		jp.add(p1);
		jp.add(p2);
		jp.add(next);
		jp.add(cancel);

		getContentPane().add(jp);
		
		p1jb1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String[] strings = { "项目 ID","项目名称" ,"项目用户"}; 
				String[] strings2 = { p1t11.getText(),p1t12.getText() ,p1t13.getText()};
		        List<String> list = new ArrayList<String>();
		        List<String> list1 =  new ArrayList<String>();
		        for (int i = 0; i < 3; i++) {
					if (strings[i]!=null&&!"".equals(strings2[i])) {
						list.add(strings[i]);
						list1.add(strings2[i]);
					
					}
				}
		      
				
				
				String[] tiaojian =(String[]) list.toArray(new String[list.size()]);
                String[] zhi =(String[]) list1.toArray(new String[list1.size()]);
				try {
					TCComponentQuery query = SearchUtils.getTCComponentQuery("项目...", session);
					 resultComponents = SearchUtils.getSearchResult(query, tiaojian, zhi);
					
					
					String[] columnNames = { "项目 ID", "项目名称", "创建者"};
					Object[][] dataObject = new Object[resultComponents.length][3];
					
 
			        
					for (int i = 0; i < resultComponents.length; i++) {
						dataObject[i][0]=resultComponents[i].getProperty("project_id")	;
						dataObject[i][1]=resultComponents[i].getProperty("project_name")	;
						dataObject[i][2]=resultComponents[i].getReferenceProperty("owning_user").getProperty("user_id");	;
					
					}

					DefaultTableModel newTableModel = new DefaultTableModel(dataObject, columnNames) ;	
					p1tb.setModel(newTableModel);

				

				} catch (TCException e1) {
					e1.printStackTrace();
				}
			

			}
		});
		
		
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionevent) {
				dispose();
			}});
		
		next.addActionListener(new ActionListener() {

			@SuppressWarnings({ "unused", "static-access", "deprecation" })
			@Override
			public void actionPerformed(ActionEvent actionevent) {
				if (p2b.getSelectedIndex()<0||tbseclet<0) {
					MessageBox.post("请选择制定项目和项目文件夹结构模板", "错误提示", 2);	
					return;
				}else {
				
                   TCComponent selectcmp = resultComponents[tbseclet];

					cmp = (TCComponent) tcComponent[p2b.getSelectedIndex()];
					TCComponent tcComponent1;

					try {
						TCComponentFolder tcComponentFolder = new TCComponentFolder();
						TCComponentFolder homeFloder = tcComponentFolder.getHomeFolder(session);
						TCComponentFolder sel = (TCComponentFolder) selcom;
						panduanname = ((TCComponentFolder) cmp)
								.getStringProperty("object_name");
						
						dispose();
						
						ItemCreationManager opr = new ItemCreationManager(cmp, sel, session, panduanname, folderType, createInClient, selectcmp);
					

					} catch (TCException e) {
						e.printStackTrace();
					}

				
					
				}	
			}			
		});

	}

	

	 
	
}
