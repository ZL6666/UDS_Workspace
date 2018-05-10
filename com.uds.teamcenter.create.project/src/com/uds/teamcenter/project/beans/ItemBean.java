package com.uds.teamcenter.project.beans;

public class ItemBean {

	private String itemcode;
	private String itemname;
	public String getItemcode() {
		return itemcode;
	}
	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public ItemBean(String itemcode, String itemname) {
		super();
		this.itemcode = itemcode;
		this.itemname = itemname;
	}
	public ItemBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
