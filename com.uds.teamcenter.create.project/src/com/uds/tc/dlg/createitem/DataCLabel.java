
/**************************************************************************************************                                      
 *                                               ∞Ê»®πÈUDSÀ˘”–£¨2016
 **************************************************************************************************                             
 *  
 *        Function Description
 *        
 **************************************************************************************************
 * Date           Author                   History  
 * 14-Apr-2016    ChenChun               Initial
 * 
 **************************************************************************************************/

package com.uds.tc.dlg.createitem;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class DataCLabel extends DataControl {
	private CLabel label;
	private String labelContent;
	public DataCLabel(Composite parent,int rowsSpan, int colsSpan) {
		super(parent,rowsSpan, colsSpan);
		this.label=new CLabel(parent, SWT.BOLD);
		label.setText(labelContent);
		label.setAlignment(SWT.LEFT);
		this.grabExcessHorizontalSpace=false;
	}
	public void setLabelContent(String labelContent) {
		if(label!=null&&labelContent!=null)
			label.setText(labelContent);
	}
	public String getLabelContent() {
		return labelContent;
	}
	@Override
	public Control getControl() {
		return label;
	}
	@Override
	public String getInput() {
		// TODO Auto-generated method stub
		if(label != null){
			return label.getText();
		}
		return null;
	}
	@Override
	public void clearText() {
		// TODO Auto-generated method stub
		
	}
	public void SetTextColor(int color){
		if(label != null){
			Color color1 = Display.getDefault().getSystemColor(color);
			label.setForeground(color1);
		}
	}
	public void SetEmphasis(){
		SetTextColor(SWT.COLOR_RED);
	}
}
