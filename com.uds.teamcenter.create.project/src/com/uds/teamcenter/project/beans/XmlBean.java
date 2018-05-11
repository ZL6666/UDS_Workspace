package com.uds.teamcenter.project.beans;

import java.util.Vector;

public class XmlBean {

	private String sheetname;
	private Vector<ItemBean> itemBean;
	
	public String getSheetname() {
		return sheetname;
	}
	public void setSheetname(String sheetname) {
		this.sheetname = sheetname;
	}
	public Vector<ItemBean> getItemBean() {
		return itemBean;
	}
	public void setItemBean(Vector<ItemBean> itemBean) {
		this.itemBean = itemBean;
	}
	public XmlBean(String sheetname, Vector<ItemBean> itemBean) {
		super();
		this.sheetname = sheetname;
		this.itemBean = itemBean;
	}
	public XmlBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
