
/**************************************************************************************************                                      
 *                                               ∞Ê»®πÈUDSÀ˘”–£¨2016
 **************************************************************************************************                             
 *  
 *        Function Description
 *        
 **************************************************************************************************
 * Date           Author                   History  
 * 28-Apr-2016    ZhangYang               Initial
 * 05-May-2016    ChenChun                Add an identifier
 **************************************************************************************************/

package com.uds.tc.dlg.createitem;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public abstract class DataControl extends ControlBase {
	protected int rowsSpan;
	protected int colsSpan;
	protected Composite parent;
	protected int hAlignment;
	protected int vAlignment;
	protected boolean grabExcessHorizontalSpace;
	protected boolean grabExcessVerticalSpace;
	protected int height;
	protected int width;
	public DataControl(Composite parent,int rowsSpan,int colsSpan) {
		this.rowsSpan=rowsSpan;
		this.colsSpan=colsSpan;
		this.parent=parent;
		this.grabExcessHorizontalSpace=true;
		this.grabExcessVerticalSpace=true;
		this.hAlignment=SWT.FILL;
		this.vAlignment=SWT.FILL;
		this.height=SWT.DEFAULT;
		this.width=SWT.DEFAULT;
	}
	
	
	public int getRowsSpan() {
		return rowsSpan;
	}


	public void setRowsSpan(int rowsSpan) {
		this.rowsSpan = rowsSpan;
	}


	public int getColsSpan() {
		return colsSpan;
	}


	public void setColsSpan(int colsSpan) {
		this.colsSpan = colsSpan;
	}


	public Composite getParent() {
		return parent;
	}


	public void setParent(Composite parent) {
		this.parent = parent;
	}


	public int gethAlignment() {
		return hAlignment;
	}


	public void sethAlignment(int hAlignment) {
		this.hAlignment = hAlignment;
	}


	public int getvAlignment() {
		return vAlignment;
	}


	public void setvAlignment(int vAlignment) {
		this.vAlignment = vAlignment;
	}


	public boolean isGrabExcessHorizontalSpace() {
		return grabExcessHorizontalSpace;
	}


	public void setGrabExcessHorizontalSpace(boolean grabExcessHorizontalSpace) {
		this.grabExcessHorizontalSpace = grabExcessHorizontalSpace;
	}


	public boolean isGrabExcessVerticalSpace() {
		return grabExcessVerticalSpace;
	}


	public void setGrabExcessVerticalSpace(boolean grabExcessVerticalSpace) {
		this.grabExcessVerticalSpace = grabExcessVerticalSpace;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public abstract Control getControl();
	public abstract String getInput();
	public abstract void clearText();
}
