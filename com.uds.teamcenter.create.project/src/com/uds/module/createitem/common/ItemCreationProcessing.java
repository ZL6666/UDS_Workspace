/**************************************************************************************************                                      
 *                                               版权归UDS所有，2016
 **************************************************************************************************                             
 *  
 *        Function Description
 *        
 **************************************************************************************************
 * Date           Author                   History  
 * 14-Apr-2016    ChenChun               Initial
 * 12-Jun-2017    ChenChun               采用UDS platform2.0版本
 **************************************************************************************************/

package com.uds.module.createitem.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentItemType;
import com.teamcenter.rac.kernel.TCComponentProject;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.kernel.TCTypeService;
import com.teamcenter.rac.util.MessageBox;
import com.uds.tc.dlg.createitem.AutoLayout;
import com.uds.tc.dlg.createitem.BeanParseAutoLayoutXmlResult;
import com.uds.tc.dlg.createitem.DataButton;
import com.uds.tc.dlg.createitem.DataCLabel;
import com.uds.tc.dlg.createitem.DataCombo;
import com.uds.tc.dlg.createitem.DataControl;
import com.uds.tc.dlg.createitem.DataDate;
import com.uds.tc.dlg.createitem.ParseAutolayoutConfigXml;
import com.uds.tc.general.util.LogFile;
import com.uds.teamcenter.create.project.handler.WaitingDialog;
import com.uds.teamcenter.project.Service.TmplateReq;
import com.uds.teamcenter.project.common.Common;

public class ItemCreationProcessing {
	private LogFile m_log = null;
	private String m_title = null;
	public String m_selectedCompUid = null;
	public static AbstractAIFUIApplication app = null;
//	public TCSession m_session = null;
	public TCComponent m_selCompObj = null;
	public static Map<String, String> itemDatamap;
	public static String okId = "UdsOkId";
	public static String applyId = "UdsApplyId";
	public static String clearId = "UdsClearId";
	public static String cancelId = "UdsCancelId";
	public TCComponent cmp;
	public TCComponentFolder homeFloder;
//	public TCSession session;
	public String panduanname;
	public TCComponentFolder xinjianmuban;
	public String foldertype;
	public static BeanParseAutoLayoutXmlResult result = null;
	public AutoLayout autoLayout = null;
	public String itemIdentifier = null;
	public String xmlString = "";
	// public Map<String,String> GetCreatMap(){
	// return itemDatamap;
	// }
	public String createInClient = "";
	public ArrayList<TCComponent> prj_cmps = new ArrayList<TCComponent>();
	public TCComponent selectcmp = null;
	
	private WaitingDialog wDlg = null;

	public ItemCreationProcessing(LogFile m_log, String title, TCComponent cmp, TCComponentFolder homeFloder,
//			TCSession session, 
			String panduanname, String type, String createInClient) {
		m_title = title;
		this.cmp = cmp;
		this.homeFloder = homeFloder;
//		this.session = session;
		this.panduanname = panduanname;
		foldertype = type;
		this.createInClient = createInClient;
		this.m_log = m_log;
		this.prj_cmps.clear();
	}

	public ItemCreationProcessing(LogFile m_log, String title, TCComponent cmp, TCComponentFolder homeFloder,
//			TCSession session, 
			String panduanname, String type, String createInClient, TCComponent selectcmp) {
		m_title = title;
		this.cmp = cmp;
		this.homeFloder = homeFloder;
//		this.session = session;
		this.panduanname = panduanname;
		foldertype = type;
		this.createInClient = createInClient;
		this.m_log = m_log;
		this.selectcmp = selectcmp;
		this.prj_cmps.clear();
		
	}

	public String Processing() {
		prj_cmps.clear();
		String createType = "";
		
		itemIdentifier = createType;
		app = AIFUtility.getCurrentApplication();
		try {

			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {

					// 对话框标题
					String title = "创建对象";
					if (m_title != null) {
						title = m_title;
					}

					try {
						// AutoLayout autoLayout = new AutoLayout(title);
						autoLayout = new AutoLayout(title);
						// BeanParseAutoLayoutXmlResult result = null;

						// 从本地读信息
						/*
						 * try { result =
						 * ParseAutolayoutConfigXml.xmlFileToLayoutData(
						 * "XmlAutolayout.xml", autoLayout); } catch
						 * (DocumentException e) { e.printStackTrace(); } catch
						 * (FileNotFoundException e) { e.printStackTrace(); }
						 */

						String m_commandId = "TC_PROJMGR__getFolderAttrInfo";
						TmplateReq tmplateReq = new TmplateReq(m_commandId);
						
						wDlg = new WaitingDialog(app.getDesktop().getFrame(),
								"正在创建界面,请稍等...", "正在创建界面,请稍等...");
						wDlg.showDialog();
						wDlg.setModal(true);
						
						xmlString = tmplateReq.Data();
						if (xmlString.startsWith("ok")) {
							xmlString = xmlString.replace("ok", "").trim();

						} else {
							MessageBox.post("失败的请求！！", "错误", MessageBox.ERROR);
						}
						
						wDlg.setVisible(false);
//						wDlg.dispose();
						
						createFrame();
						/*if(state){
							MessageBox.post("创建成功","成功",2);
						} else {
							MessageBox.post("创建失败","失败",1);
						}*/
						
					} catch (Exception ex) {
//						String msg = "Processing has error:" + ex.getLocalizedMessage();
//						m_log.write(msg);
						ex.printStackTrace();
//						MessageBox.post(msg, m_title, MessageBox.ERROR);
					}
				}
			});
			
			return null;
		} catch (Exception ex) {
			String msg = "Processing has error:" + ex.getMessage();
			m_log.write(msg);
			return msg;
		}

	}

	public void createFrame() {
		autoLayout = new AutoLayout("创建对象");
		try {
			result = ParseAutolayoutConfigXml.xmlStringToLayoutData(xmlString, autoLayout);

			// 构造对话框
			List<List<DataControl>> allControls = result.getAllDataContorls();
			int colsCount = result.getColsCount();

			// 传递的数据
			Map<String, DataControl> items = new HashMap<String, DataControl>();
			if (allControls != null) {
				for (int i = 0; i < allControls.size(); i++) {
					List<DataControl> rowControls = allControls.get(i);
					if (rowControls != null && rowControls.size() > 0) {
						for (int j = 0; j < rowControls.size(); j++) {
							DataControl control = rowControls.get(j);
							if (control != null && !"".equals(control.GetIdentifier())) {
								items.put(control.GetIdentifier(), control);
							}
						}
					}
				}
			}

			// 新增button
			List<DataControl> lastButtons = new ArrayList<DataControl>();
			String okText = "确定";
			String applyText = "应用";
			String clearText = "清除";
			String cancelText = "取消";

			Composite composite = autoLayout.getComposite();
			DataControl okBtn = null;
			DataControl applyBtn = null;
			DataControl clearBtn = null;
			DataControl cancelBtn = null;

			int rowSpan = 1;
			int colSpan = 1;
			int btnWidth = 100;

			if (colsCount == 1) {
				okBtn = new DataButton(composite, rowSpan, colSpan);
			} else if (colsCount == 2) {
				okBtn = new DataButton(composite, rowSpan, colSpan);
				cancelBtn = new DataButton(composite, rowSpan, colSpan);

			} else if (colsCount == 3) {
				okBtn = new DataButton(composite, rowSpan, colSpan);
				clearBtn = new DataButton(composite, rowSpan, colSpan);
				cancelBtn = new DataButton(composite, rowSpan, colSpan);
			} else if (colsCount == 4) {
				okBtn = new DataButton(composite, rowSpan, colSpan);
				applyBtn = new DataButton(composite, rowSpan, colSpan);
				clearBtn = new DataButton(composite, rowSpan, colSpan);
				cancelBtn = new DataButton(composite, rowSpan, colSpan);
			} else {
				if (colsCount >= 60) {
					colSpan = 15;
				} else if (colsCount <= 10) {
					colSpan = 1;
					btnWidth = 60;
				} else {
					colSpan = colsCount / 4;
				}

				int emptySpan = colsCount - colSpan * 4;
				if (emptySpan > colSpan) {
					DataControl empty = new DataCLabel(composite, rowSpan, emptySpan - colSpan);
					lastButtons.add(empty);
				}

				okBtn = new DataButton(composite, rowSpan, colSpan);
				applyBtn = new DataButton(composite, rowSpan, colSpan);
				clearBtn = new DataButton(composite, rowSpan, colSpan);
				cancelBtn = new DataButton(composite, rowSpan, colSpan);
			}

			if (okBtn != null) {
				okBtn.SetIdentifier(okId);
				ParseAutolayoutConfigXml.SetControlText(okBtn.getControl(), okText);
				okBtn.setWidth(btnWidth);

				SystemButtonActionListener okLst = new SystemButtonActionListener();
				okLst.m_dlg = autoLayout;
				okLst.m_controlId = okBtn.GetIdentifier();
				okLst.m_items = items;
				okLst.m_selCompUid = m_selectedCompUid;
				okLst.m_selItemType = itemIdentifier;
				okLst.m_selCompObj = m_selCompObj;

				ItemCreationProcessing.AddActionListener(okBtn.getControl(), okLst);

				lastButtons.add(okBtn);
			}
			if (applyBtn != null) {
				applyBtn.SetIdentifier(applyId);
				ParseAutolayoutConfigXml.SetControlText(applyBtn.getControl(), applyText);
				applyBtn.setWidth(btnWidth);

				SystemButtonActionListener applyLst = new SystemButtonActionListener();
				applyLst.m_dlg = autoLayout;
				applyLst.m_controlId = applyBtn.GetIdentifier();
				applyLst.m_items = items;
				applyLst.m_selCompUid = m_selectedCompUid;
				applyLst.m_selItemType = itemIdentifier;
				applyLst.m_selCompObj = m_selCompObj;

				ItemCreationProcessing.AddActionListener(applyBtn.getControl(), applyLst);

				lastButtons.add(applyBtn);
			}
			if (clearBtn != null) {
				clearBtn.SetIdentifier(clearId);
				ParseAutolayoutConfigXml.SetControlText(clearBtn.getControl(), clearText);
				clearBtn.setWidth(btnWidth);

				SystemButtonActionListener clearLst = new SystemButtonActionListener();
				clearLst.m_dlg = autoLayout;
				clearLst.m_controlId = clearBtn.GetIdentifier();
				clearLst.m_items = items;
				clearLst.m_selCompUid = m_selectedCompUid;
				clearLst.m_selItemType = itemIdentifier;
				clearLst.m_selCompObj = m_selCompObj;

				ItemCreationProcessing.AddActionListener(clearBtn.getControl(), clearLst);

				lastButtons.add(clearBtn);
			}
			if (cancelBtn != null) {
				cancelBtn.SetIdentifier(cancelId);
				ParseAutolayoutConfigXml.SetControlText(cancelBtn.getControl(), cancelText);
				cancelBtn.setWidth(btnWidth);

				SystemButtonActionListener cancelLst = new SystemButtonActionListener();
				cancelLst.m_dlg = autoLayout;
				cancelLst.m_controlId = cancelBtn.GetIdentifier();
				cancelLst.m_items = items;
				cancelLst.m_selCompUid = m_selectedCompUid;
				cancelLst.m_selItemType = itemIdentifier;
				cancelLst.m_selCompObj = m_selCompObj;

				ItemCreationProcessing.AddActionListener(cancelBtn.getControl(), cancelLst);

				lastButtons.add(cancelBtn);
			}
			if (lastButtons.size() > 0) {
				allControls.add(lastButtons);
			}

//			ItemCreationProcessing.PreDialogProcessing(allControls, m_session);
			ItemCreationProcessing.PreDialogProcessing(allControls);

			autoLayout.open(allControls, colsCount);
			try {
				Common common = new Common();
				if ((autoLayout.getDialogResult().toString()).equals("OK") ) {
//					MessageBox.post("成功", m_title, MessageBox.INFORMATION);
					common.Message("成功");
				} else if ((autoLayout.getDialogResult().toString()).equals("OKFailure")) {
//					MessageBox.post("失败", m_title, MessageBox.ERROR);
					common.Message("失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void PreDialogProcessing(List<List<DataControl>> allControls){
		for (int row = 0; row < allControls.size(); row++) {
			List<DataControl> controls = allControls.get(row);
			for (int col = 0; col < controls.size(); col++) {
				DataControl control = controls.get(col);
				if (control.m_isEmphasis) {
					if (control instanceof DataCLabel) {
						DataCLabel label = (DataCLabel) control;
						label.SetEmphasis();
					}
				}
				if (control instanceof DataCombo) {
					if (control.m_lov != null) {
						TCSession session = (TCSession) app.getSession();
						Map<String, String> lovs = Common.GetLovValLocalVal(session, control.m_lov);
						if (lovs != null) {
							// 由于获取的是值与本地化值，这里需要反向成本地化值与值
							Map<String, String> contents = new LinkedHashMap<String, String>();
							Set<String> get = lovs.keySet();
							for (String item : get) {
								String key = lovs.get(item);
								String val = item;
								if (contents.containsKey(key)) {

								} else {
									contents.put(key, val);
								}
							}
							DataCombo combo = (DataCombo) control;
							combo.setAutoComboLovItems(contents);
						}
					}
				} else if (control instanceof DataDate) {
					if (control.m_format != null && !"".equals(control.m_format)) {
						DataDate date = (DataDate) control;
						date.SetPattern(control.m_format);
					}
				}
			}
		}
	}

	public static void AddActionListener(Control contrl, SelectionListener listener) {
		if (contrl instanceof Button) {
			Button ctrl = (Button) contrl;
			ctrl.addSelectionListener(listener);
		}
	}

	protected class SystemButtonActionListener implements SelectionListener {

		public AutoLayout m_dlg = null;
		public Map<String, DataControl> m_items = null;
		public String m_controlId = null;
		public String m_selCompUid = null;
		public String m_selItemType = null;
		public TCComponent m_selCompObj = null;

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {

		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			if (okId.equals(m_controlId)) {
				if (m_dlg != null && CheckInputs()) {

//					if ("1".equals(createInClient)) {
					DoCreation("ok");
						if (selectcmp != null) {
							prj_cmps.add(xinjianmuban);
							int size = prj_cmps.size();
							TCComponent[] prj_array = (TCComponent[]) prj_cmps.toArray(new TCComponent[size]);

							TCComponentProject targetPrj = (TCComponentProject) selectcmp;
							try {
								targetPrj.assignToProject(prj_array);
							} catch (TCException e) {
								e.printStackTrace();
							}
						}
						m_dlg.CloseDialog(AutoLayout.DialogResult.OK);
//					}

				}
			} else if (cancelId.equals(m_controlId)) {
				if (m_dlg != null) {
					m_dlg.CloseDialog(AutoLayout.DialogResult.CANCEL);
				}

			} else if (applyId.equals(m_controlId)) {

				if (m_dlg != null && CheckInputs()) {
					DoCreation("apply");

					if (selectcmp != null) {
//						TCComponent[] cmps = { xinjianmuban };
						prj_cmps.add(xinjianmuban);
						int size = prj_cmps.size();
						TCComponent[] prj_array = (TCComponent[]) prj_cmps.toArray(new TCComponent[size]);

						TCComponentProject targetPrj = (TCComponentProject) selectcmp;
						try {
							targetPrj.assignToProject(prj_array);
						} catch (TCException e) {
							e.printStackTrace();
						}
					}
					ClearItemData();
				}

			} else if (clearId.equals(m_controlId)) {
				if (m_dlg != null) {
					ClearItemData();
					return;
				}
			}
		}

		void DoCreation(String state) {
//			CreateItemData itemData = new CreateItemData();
//			itemData.m_allData = GetItemsData(state);
			GetItemsData(state);
			// 20170615刷新TC以便显示出新对象
			if (m_selCompObj != null) {
				try {
					m_selCompObj.refresh();
				} catch (TCException e) {
					e.printStackTrace();
				}
			}

		}

		boolean CheckInputs() {
			String msg = "";
			if (m_items != null && m_items.size() > 0) {
				for (Map.Entry<String, DataControl> entry : m_items.entrySet()) {
					DataControl control = entry.getValue();
					if (control != null && control.m_isRequired) {
						String text = control.getInput();
						text = text.trim();
						if ("".equals(text)) {
							msg += entry.getKey() + "\n";
						}
					}
				}
			}
			if (msg.equals("")) {
				return true;
			} else {
				String info = "以下数据没有提供：\n" + msg;
				MessageBox.post(info,"提示",MessageBox.INFORMATION);
				return false;
			}
		}

		void ClearItemData() {
			if (m_items != null && m_items.size() > 0) {
				for (Map.Entry<String, DataControl> entry : m_items.entrySet()) {
					DataControl control = entry.getValue();
					if (control != null) {
						control.clearText();
					}
				}
			}
		}

		synchronized Map<String, String> GetItemsData(String state) {
			
			if (m_items != null && m_items.size() > 0) {
				itemDatamap = new HashMap<String, String>();
				for (Map.Entry<String, DataControl> entry : m_items.entrySet()) {
					DataControl control = entry.getValue();
					if (control != null) {
						String key[] = entry.getKey().split(",");
						int count = key.length;
						for(int i = 0; i < count; i++){
							itemDatamap.put(key[i], control.getInput());
						}
					}
				}
				
				TCComponent component = (TCComponent) cmp;
				TCSession session = (TCSession)app.getSession();
//				if (state.equals("ok")) {
					wDlg = new WaitingDialog(app.getDesktop().getFrame(),
							"正在创建,请稍等...", "正在创建,请稍等...");
					wDlg.showDialog();
					wDlg.setModal(true);
//				}
				
				bianli(component, homeFloder, session);
				
				try {
					wDlg.setVisible(false);
					xinjianmuban.setProperties(itemDatamap);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return itemDatamap;
			}
			return null;
		}
	}

	/*protected class CreateItemData {
		// Identifier,DataControl
		public Map<String, String> m_allData = null;

	}*/

	synchronized TCComponentFolder bianli(TCComponent cmp, TCComponentFolder new_folder, TCSession session) {
		try {
			if (cmp instanceof TCComponentFolder) {
				TCComponentFolder folder = (TCComponentFolder) cmp;
				String name = folder.getStringProperty("object_name");
				String desc = folder.getStringProperty("object_desc");
				// 第三个参数需修改
				TCComponentFolder new_created_folder = Common.create(name, desc, cmp.getType());// 新建出模板1

				if (panduanname.equals(name)) {
					xinjianmuban = new_created_folder;
				}

				new_folder.add("contents", new_created_folder);
				AIFComponentContext[] context = folder.getChildren();
				if (context.length > 0) {// 遍历
					for (int i = 0; i < context.length; i++) {
						TCComponent cmp1 = (TCComponent) context[i].getComponent();
						bianli(cmp1, new_created_folder, session);
					}
				}
			} else if (cmp instanceof TCComponentItem) {
				TCComponentItem item = (TCComponentItem) cmp;
				TCTypeService typeService = session.getTypeService();
				TCComponentItemType TypeCmp =(TCComponentItemType) typeService.getTypeComponent(cmp.getType());
				TCComponentItemRevision revision = (TypeCmp.findItems(item.getProperty("item_id"))[0]).getLatestItemRevision();
				
				String new_id = TypeCmp.getNewID();
//				String SourceName = item.getProperty("object_name");
//				String SourceID = item.getProperty("item_id");
//				String rev_id = ((TCComponentItemType) TypeCmp).getNewRev(null);
				String rev_id = revision.getProperty("current_revision_id");
				TCComponentItem Item = revision.saveAsItem(null, rev_id);
				
				if (Item != null) {
					TCComponent[] component = Item.getRelatedComponents("IMAN_specification");
					for (int i = 0; i < component.length; i++) {
						component[i].setProperty("object_name", new_id + rev_id);
					}
					if (component != null) {

					}
					new_folder.add("contents", Item);
				}
				//
			} else if (cmp instanceof TCComponentDataset) {
				TCComponentDataset dataset = (TCComponentDataset) cmp;

				String SourceName = dataset.getProperty("object_name");
				TCComponentDataset newdataset = dataset.saveAs(SourceName);

				if (newdataset != null) {
					new_folder.add("contents", newdataset);
				}

			} else if (cmp instanceof TCComponentForm) {
				TCComponentForm form = (TCComponentForm) cmp;
				String SourceName = form.getProperty("object_name");
				TCComponentForm newform = form.saveAs(SourceName);
				if (newform != null) {
					new_folder.add("contents", newform);
				}

			}
		} catch (TCException e) {
			e.printStackTrace();
		}
		return new_folder;
	}

	void GetData(TCComponent cmp) {
		try {
			AIFComponentContext[] Context = cmp.getChildren();
			for (int i = 0; i < Context.length; i++) {
				TCComponent SubCmp = (TCComponent) Context[i].getComponent();
				if (((SubCmp instanceof TCComponentFolder)) || ((SubCmp instanceof TCComponentItem))) {
					prj_cmps.add(SubCmp);
				}
				GetData(SubCmp);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
