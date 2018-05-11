
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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class DataButton extends DataControl {
	private Button button;
	private String buttonText;
	public DataButton(Composite parent, int rowsSpan, int colsSpan) {
		super(parent, rowsSpan, colsSpan);
		this.hAlignment=SWT.CENTER;
		button=new Button(parent, SWT.NONE);
		width=SWT.DEFAULT;
		height=SWT.DEFAULT;
		
	}
	public void setButtonText(String buttonText) {
		if(button!=null)
			button.setText(buttonText);
	}
	public String getButtonText() {
		return buttonText;
	}
	public Button getButtonControl(){
		return button;
	}
	@Override
	public Control getControl() {
		return button;
	}
	@Override
	public String getInput() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void clearText() {
		// TODO Auto-generated method stub
		
	}
}
