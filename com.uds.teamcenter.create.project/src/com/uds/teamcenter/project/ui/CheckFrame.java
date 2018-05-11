package com.uds.teamcenter.project.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
import com.uds.teamcenter.create.project.handler.ResourceManager;
import com.uds.teamcenter.create.project.handler.WaitingDialog;
import com.uds.teamcenter.project.beans.XmlBean;

public class CheckFrame extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static AbstractAIFUIApplication app = null;
	static TCSession session = null;
	private static final Dimension HGAP10 = new Dimension(10, 1);
	private static final Dimension VGAP10 = new Dimension(1, 10);
	private static final Dimension HGAP15 = new Dimension(15, 1);
	// private static final Dimension HGAP30 = new Dimension(30, 1);

	private final ResourceManager resourceManager = new ResourceManager(
			this.getClass());

	// private final JList list;

	// private JPanel prefixList;
	// 每一个sheet为一个jpanel，方便监听顶节点事件
	private List<JPanel> prefixL = new ArrayList<JPanel>();
	private JButton btn1;
	private JButton btn2;

	private Action prefixAction;
	// private static JCheckBox headcb;
	private static List<JCheckBox> head = new ArrayList<JCheckBox>();
	private JCheckBox cb;
	List<String> lj = new ArrayList<String>();
	private static Vector<TCComponentItem> vec = null;
	private static boolean isset = true;
	private static boolean exist = true;
	private WaitingDialog wDlg = null;
	static String ErrMsg = "";
	static String ExistMsg = "";

	@SuppressWarnings("static-access")
	public CheckFrame(Vector<TCComponentItem> vector) {
		this.vec = vector;
	}

	public void cf(List<XmlBean> xblist) {
		final JFrame frame = new JFrame();
		prefixL = new ArrayList<JPanel>();
		head = new ArrayList<JCheckBox>();
		// 设置用户在此窗体上发起 "close" 时默认执行的操作
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("齐备性检查");
		// public CheckFrame() {
		setLayout(new BorderLayout());

		// JLabel description = new
		// JLabel(resourceManager.getString("ListDemo.description"));
		// add(description, BorderLayout.NORTH);

		final JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(Box.createRigidArea(HGAP10));
		add(centerPanel, BorderLayout.CENTER);
		app = AIFUtility.getCurrentApplication();
		session = (TCSession) app.getSession();

		// Add the control panel (holds the prefix/suffix list and prefix/suffix
		// checkboxes)
		centerPanel.add(createControlPanel(xblist));

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
		btnPanel.add(Box.createRigidArea(VGAP10));
		btn1 = new JButton("确认");
		btn2 = new JButton("取消");
		btnPanel.add(btn1);
		btnPanel.add(btn2);
		centerPanel.add(btnPanel);
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// getAllJCheckBoxValue(centerPanel, lj);
				try {
					wDlg = new WaitingDialog(app.getDesktop().getFrame(),
							"执行中", "正在检查,请稍等...");
					wDlg.showDialog();
					wDlg.setModal(true);

					ErrMsg = "";
					ExistMsg = "";

					isset = true;
					exist = true;

					isset = getAllJCheckBoxValue(centerPanel, lj);

					// if(ErrMsg.equals("")&&ExistMsg.equals("")){
					// isset = true;
					// }

					if (isset) {
						wDlg.setVisible(false);
						// itemDs.setProperty("j2_check", "true");
						MessageBox.post("检查完成，文件齐备！", "信息",
								MessageBox.INFORMATION);
						frame.dispose();
					} else {
						wDlg.setVisible(false);

						int n = JOptionPane.showConfirmDialog(null,
								"检查完成，存在缺失文件，请查看日志。", "提示",
								JOptionPane.YES_NO_OPTION);
						if (n == JOptionPane.YES_OPTION) {
							createReport("错误信息", "以下对象未发布" + "\n" + ErrMsg
									+ "\n" + "不符合项有:" + "\n" + ExistMsg);
						} else if (n == JOptionPane.NO_OPTION) {
							// ......
							frame.dispose();
						}
						frame.dispose();
					}
				} catch (TCException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		// 循环添加顶节点监听事件
		for (int i = 0; i < head.size(); i++) {
			final int temp = i;
			head.get(i).addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					if (((JCheckBox) head.get(temp)).isSelected()) {
						try {
							setAllJCheckBox(prefixL.get(temp), true);
						} catch (TCException e) {
							e.printStackTrace();
						}
					} else {
						try {
							setAllJCheckBox(prefixL.get(temp), false);
						} catch (TCException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
		frame.getContentPane().add(centerPanel);
		frame.setPreferredSize(new Dimension(600, 300));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	@SuppressWarnings("serial")
	private JPanel createControlPanel(List<XmlBean> xblist) {
		JPanel controlPanel = new JPanel() {
			private final Insets insets = new Insets(0, 4, 10, 10);

			public Insets getInsets() {
				return insets;
			}
		};
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
		for (int i = 0; i < xblist.size(); i++) {
			// 1
			JPanel prefixPanel = new JPanel();
			prefixPanel.setLayout(new BoxLayout(prefixPanel, BoxLayout.Y_AXIS));
			String a = xblist.get(i).getSheetname();
			head.add((JCheckBox) prefixPanel.add(new JCheckBox(resourceManager
					.getString(a))));
			prefixL.add(new JPanel() {
				private final Insets insets = new Insets(0, 4, 0, 0);

				public Insets getInsets() {
					return insets;
				}
			});
			prefixL.get(i).setLayout(
					new BoxLayout(prefixL.get(i), BoxLayout.Y_AXIS));
			JScrollPane scrollPane = new JScrollPane(prefixL.get(i));
			scrollPane.getVerticalScrollBar().setUnitIncrement(10);
			prefixPanel.add(scrollPane);
			prefixPanel.add(Box.createRigidArea(HGAP10));
			controlPanel.add(prefixPanel);
			controlPanel.add(Box.createRigidArea(HGAP15));
			for (int t = 0; t < xblist.get(i).getItemBean().size(); t++) {
				String iname = xblist.get(i).getItemBean().get(t).getItemname();
				cb = (JCheckBox) prefixL.get(i).add(new JCheckBox(iname));
				cb.setSelected(false);
				cb.addActionListener(prefixAction);
				cb.addFocusListener(listFocusListener);
			}
		}
		return controlPanel;
	}

	private final FocusListener listFocusListener = new FocusAdapter() {
		public void focusGained(FocusEvent e) {
			JComponent c = (JComponent) e.getComponent();
			c.scrollRectToVisible(new Rectangle(0, 0, c.getWidth(), c
					.getHeight()));
		}
	};

	public static boolean getAllJCheckBoxValue(Container ct, List<String> list)throws TCException {
		if (list == null || list.size() <= 0) {
			list = new ArrayList<String>();
		}
		int count = ct.getComponentCount();
		exist = true;
		for (int i = 0; i < count; i++) {
			exist = true;
			Component c = ct.getComponent(i);
			if (c instanceof JCheckBox) {
				JCheckBox a = (JCheckBox) c;
				int headCount = head.size();
				boolean hTemp = true;
				//判断跳过检查顶节点
				for (int k = 0; k < headCount; k++) {
					if(a.getText().equals(head.get(k).getText())){
						hTemp = false;
					}
				}
				
				if (((JCheckBox) c).isSelected()&& (hTemp)) {
					list.add(a.getText());
					System.out.println("123" + a.getText());
					// String icode = a.getText().split("/")[0];
					String itemName = a.getText();
					System.out.println("2223" + itemName);
					// 如果没有文件，直接报错
					if (vec.size() == 0) {
						exist = false;
					}
					String name = "";
					for (int j = 0; j < vec.size(); j++) {
						TCComponentItem item = vec.get(j);
						
						name = item.getProperty("object_name");
						if (name.contains(itemName) || name.equals(itemName)) {
							vec.remove(j);
							j--;
							
							// exist = false;
							TCComponentItemRevision tcdatarev = item.getLatestItemRevision();

							if (tcdatarev != null) {
								boolean flag = idHasStatus(tcdatarev);
								if (!flag) {
									isset = false;
									// MessageBox.post(a.getText()+"对象未发布！",
									// "错误",MessageBox.ERROR);
									// return;
									ErrMsg = ErrMsg + a.getText() + "\n";
								}
							} else {
								// exist = false;
								ErrMsg = ErrMsg + a.getText() + "\n";
								// MessageBox.post(a.getText()+"对象下无文档版本对象！",
								// "错误",MessageBox.ERROR);
								// return;
							}
							break;
						} else {
							exist = false;
						}
					}

					if (!exist) {
						isset = false;
						ExistMsg = ExistMsg + a.getText() + "\n";
					}

				}
			} else if (c instanceof Container) {
				getAllJCheckBoxValue((Container) c, list);
			}
		}
		// itemDs.setProperty("j2_check", "true");
		return isset;
	}

	public static void setAllJCheckBox(Container ct, boolean statu)
			throws TCException {

		int count = ct.getComponentCount();
		for (int i = 0; i < count; i++) {
			Component c = ct.getComponent(i);
			if (c instanceof JCheckBox) {
				((JCheckBox) c).setSelected(statu);
			} else if (c instanceof Container) {
				setAllJCheckBox((Container) c, statu);
			}
		}
		// itemDs.setProperty("j2_check", "true");
		return;
	}

	public static boolean idHasStatus(TCComponentItemRevision component)
			throws TCException {
		boolean flag = false;
		TCComponent[] components = component
				.getReferenceListProperty("release_status_list");
		System.out.println("状态2" + components.length);

		if (components.length != 0) {
			flag = true;
		}
		// for (int i = 0; i < components.length; i++) {
		// String type = components[i].getProperty("object_name");
		// System.out.println("状态类型"+type);
		// if("H2_R".equals(type)){
		// flag= true;
		// break;
		// }
		// }
		return flag;
	}

	// 打印出文本信息
	public static void createReport(String FileName, String warning) {
		try {
			String s = "";
			String Temp = "c:\\temp\\";
			File TempDir = new File(Temp);
			if (!TempDir.exists()) {
				TempDir.mkdir();
			}
			if (TempDir.exists()) {
				File NewFile = new File(Temp + FileName + ".txt");
				if (NewFile.exists()) {
					NewFile.delete();
				}
				BufferedReader in4 = new BufferedReader(new StringReader(
						warning));
				PrintWriter out1 = new PrintWriter(new BufferedWriter(
						new FileWriter(Temp + FileName + ".txt")));
				while ((s = in4.readLine()) != null) {
					out1.println(s);
				}
				out1.close();

				String openCMD[] = { "cmd.exe", "/c", "start",
						NewFile.getAbsolutePath() };
				Runtime.getRuntime().exec(openCMD);
			} else {
				System.out.println("临时文件夹创建失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
