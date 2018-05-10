/**************************************************************************************************                                      
 *                                               版权归UDS所有，2016
 **************************************************************************************************                             
 *  
 *        Function Description
 *        
 **************************************************************************************************
 * Date           Author                   History  
 * 14-Apr-2016    ChenChun               Initial
 * 
 **************************************************************************************************/


package com.uds.module.createitem.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.uds.tc.dlg.createitem.AutoLayout;
import com.uds.tc.dlg.createitem.DataButton;
import com.uds.tc.dlg.createitem.DataCLabel;
import com.uds.tc.dlg.createitem.DataCombo;
import com.uds.tc.dlg.createitem.DataControl;
import com.uds.tc.dlg.createitem.ParseAutolayoutConfigXml;
import com.uds.tc.general.util.LogFile;

public class ItemCreationTypeSelection {
	private LogFile m_log = null;
	private String m_title = null;

	public static String okId = "UdsOkId";
	public static String cancelId = "UdsCancelId";
	
	//选择内容 key - 返回的item 类型，value - 显示内容
	public Map<String,String> m_itemTypeSelections = null;
	
	public ItemCreationTypeSelection(LogFile m_log, String title){
		this.m_log = m_log;
		m_title = title;
		}
	public String GetItemType(){
		if(m_itemTypeSelections != null && m_itemTypeSelections.size() > 0){
			try{
				OptionDialogRunning optionDlg = new OptionDialogRunning();
				Display.getDefault().syncExec(optionDlg);
				
				if(optionDlg.m_selectedText != null){
					for(Map.Entry<String, String> entry : m_itemTypeSelections.entrySet()){
						if(optionDlg.m_selectedText.equals(entry.getValue())){
							return entry.getKey();
						}
					}
				}
				
			}catch(Exception ex){
				String msg = ex.getMessage();
				m_log.write(msg);
			}
		}
		return null;
	}
	
	protected class OptionDialogRunning implements Runnable{
		public String m_selectedText = null;
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//对话框标题
			String title = "创建对象";
			if(m_title != null){
				title = m_title;
			}
			
			try{
				AutoLayout autoLayout = new AutoLayout(title);
				//构造对话框
				List<List<DataControl>> allControls = new ArrayList<List<DataControl>>();
				int colsCount = 2;
				
				Composite composite = autoLayout.getComposite();
				//选择控件
				String itemOptionLabel = "Item 类型";
				DataCLabel label1 = new DataCLabel(composite, 1, 1);
				ParseAutolayoutConfigXml.SetControlText(label1.getControl(), itemOptionLabel);
				
				label1.SetEmphasis();
				
				DataCombo option1 = new DataCombo(composite, 1, 1);
				String[] optionVals = m_itemTypeSelections.values().toArray(new String[m_itemTypeSelections.size()]);				
				option1.setAutoComboItems(optionVals);
				
				List<DataControl> rowControls = new ArrayList<DataControl>();
				rowControls.add(label1);
				rowControls.add(option1);
				allControls.add(rowControls);
				
				//系统按钮
				String okText = "确定";
				String cancelText = "取消";
				
				DataButton okBtn = new DataButton(composite,1,1);
				okBtn.SetIdentifier(okId);
				ParseAutolayoutConfigXml.SetControlText(okBtn.getControl(), okText);
				
				SystemButtonActionListener okLst = new SystemButtonActionListener();
				okLst.m_dlg = autoLayout;
				okLst.m_controlId = okBtn.GetIdentifier();	
				okLst.m_optionControl = option1;
			//	ItemCreationProcessing.AddActionListener(okBtn.getControl(), okLst);
				
				DataButton cancelBtn = new DataButton(composite,1,1);
				cancelBtn.SetIdentifier(cancelId);
				ParseAutolayoutConfigXml.SetControlText(cancelBtn.getControl(), cancelText);
				
				SystemButtonActionListener cancelLst = new SystemButtonActionListener();
				cancelLst.m_dlg = autoLayout;
				cancelLst.m_controlId = cancelBtn.GetIdentifier();							
				ItemCreationProcessing.AddActionListener(cancelBtn.getControl(), cancelLst);
				
				List<DataControl> rowControls2 = new ArrayList<DataControl>();
				rowControls2.add(okBtn);
				rowControls2.add(cancelBtn);
				allControls.add(rowControls2);
				
				autoLayout.setShellWidth(300);
				autoLayout.open(allControls, colsCount);
				if(autoLayout.getDialogResult() == AutoLayout.DialogResult.OK){
					String inputText = okLst.m_selectedText;
					m_selectedText = inputText;	
				}else if(autoLayout.getDialogResult() == AutoLayout.DialogResult.OKFailure){
					;
				}
			}catch(Exception ex){
				String msg = ex.getMessage();
				m_log.write(msg);
			}
		
		}
	}
	protected class SystemButtonActionListener implements SelectionListener{
		public AutoLayout m_dlg = null;
		public String m_controlId = null;
		public DataCombo m_optionControl = null;
		public String m_selectedText = null;
		
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub
			if(okId.equals(m_controlId)){
				if(m_dlg != null && m_optionControl != null){
					String inputText = m_optionControl.getInput();
					m_selectedText = inputText;	
					m_dlg.CloseDialog(AutoLayout.DialogResult.OK);
				}
			}else if(cancelId.equals(m_controlId)){
				if(m_dlg != null){
					m_dlg.CloseDialog(AutoLayout.DialogResult.CANCEL);
				}
			}
		}
		
	}
}
