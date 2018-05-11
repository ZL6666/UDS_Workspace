package com.uds.tc.dlg.createitem;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.uds.tc.dlg.createitem.Constants.ConstantsDataText;
import com.uds.tc.dlg.createitem.Constants.ConstantsDataText.DataTextStyle;

public class DataText extends DataControl {
	private Text text;
	private boolean isEnabled;
	public DataText(Composite parent, int rowsSpan, int colsSpan) {
		super(parent, rowsSpan, colsSpan);
		this.height=ConstantsDataText.DEFAULT_TEXT_HEIGHT;
		this.isEnabled=true;
		text=new Text(parent, SWT.BORDER);
		text.setEnabled(isEnabled);
	}
	public Text getText() {
		return text;
	}
	public void setText(String content){
		if(this.isEnabled)
			text.setText(content);
	}
	public void toggleEnable(){
		this.isEnabled=!text.isEnabled();
		this.text.setEnabled(this.isEnabled);;
	}
	@Override
	public Control getControl() {
		return text;
	}
	public void setTextStyle(DataTextStyle style){
		switch(style){
		case DEFAULT:
			this.height=ConstantsDataText.DEFAULT_TEXT_HEIGHT;
			break;
		case HEIGHTEN:
			this.height=ConstantsDataText.DEFAULT_TEXT_HEIGHTEN;
			break;
		}
	}
	@Override
	public String getInput() {
		// TODO Auto-generated method stub
		if(text != null){
			return text.getText();
		}
		return null;
	}
	@Override
	public void clearText() {
		// TODO Auto-generated method stub
		if(text != null){
			text.setText("");
		}
	}
	
	
}
