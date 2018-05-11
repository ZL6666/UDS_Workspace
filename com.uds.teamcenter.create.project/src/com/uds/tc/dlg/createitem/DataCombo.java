
/**************************************************************************************************                                      
 *                                               版权归UDS所有，2017
 **************************************************************************************************                             
 *  
 *        Function Description
 *        
 **************************************************************************************************
 * Date           Author                   History  
 * 13-Jul-2017    ChenChun                LOV显示本地化值
 **************************************************************************************************/
package com.uds.tc.dlg.createitem;

import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class DataCombo extends DataControl {
	private AutoCompleteTextCombo autoCombo;
	private Map<String,String> m_contents;
	public DataCombo(Composite parent, int rowsSpan, int colsSpan) {
		super(parent, rowsSpan, colsSpan);
		autoCombo=new AutoCompleteTextCombo(parent, SWT.BORDER);
	}
	@Override
	public Control getControl() {
		return autoCombo;
	}
	public void setAutoComboItems(String[] contents){
		if(autoCombo!=null)
			autoCombo.setItems(contents);
	}
	public void setAutoComboLovItems(Map<String,String> contents){
		m_contents = contents;
		if(autoCombo!=null){
			Set<String> keySet = contents.keySet();
			autoCombo.setItems(keySet.toArray(new String[keySet.size()]));
		}
	}
	@Override
	public String getInput() {
		// TODO Auto-generated method stub
		if(autoCombo != null){
			String val = autoCombo.getText();
			if(m_contents != null){
				if(m_contents.containsKey(val)){
					return m_contents.get(val);
				}
			}
			return autoCombo.getText();
		}
		return null;
	}
	@Override
	public void clearText() {
		// TODO Auto-generated method stub
		if(autoCombo != null){
			autoCombo.setText("");
		}
	}
}
