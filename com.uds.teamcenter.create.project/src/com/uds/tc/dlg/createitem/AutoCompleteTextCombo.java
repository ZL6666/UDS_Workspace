package com.uds.tc.dlg.createitem;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class AutoCompleteTextCombo extends Combo  {
	/*
	  不能让AutoCompleteTextCombo实现KeyListener然后在添加KeyListener事件
	,如果这样做在keyReleased方法中getText获取的内容将为空 
	*/
	private AutoCompleteTextCombo self;
	private String[] contents;
	private boolean isFirst=true;
	private int caretPosition;
	public AutoCompleteTextCombo(Composite parent, int style) {
		super(parent, style);
		self=this;
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent keyevent) {
				if(contents!=null&&contents.length>0){
					if(keyevent.keyCode!=SWT.ARROW_LEFT
							&&keyevent.keyCode!=SWT.ARROW_RIGHT
							&&keyevent.keyCode!=SWT.ARROW_UP
							&&keyevent.keyCode!=SWT.ARROW_DOWN
							)
					{
						String typeNumberName=self.getText();
						self.removeAll();
						for(String typeNumberContent:contents)
						{
							if(typeNumberContent.contains(typeNumberName)){
								self.add(typeNumberContent);
							}
						}
						if(self.getItemCount()==0){
							self.setItems(contents);
						}
						self.setVisibleItemCount(self.getItemCount());
						if(!self.getListVisible())
							self.setListVisible(true);
						self.setText(typeNumberName);
						if(keyevent.keyCode==SWT.BS)
							self.setSelection(new Point(caretPosition-1,caretPosition-1));
						else
							self.setSelection(new Point(caretPosition+1,caretPosition+1));
					}
				}
			}
			@Override
			public void keyPressed(KeyEvent keyevent) {
				caretPosition=self.getCaretPosition();
				if(isFirst){
					contents=self.getItems();
					isFirst=false;
				}
			}
		});
	}
	@Override
	protected void checkSubclass() {
	}
}
