package com.uds.tc.dlg.createitem;

import java.util.List;

public class BeanParseAutoLayoutXmlResult {
 	private List<List<DataControl>> allDataContorls;
	private int colsCount;
	@Override
	public String toString() {
		return "BeanParseAutoLayoutXmlResult [allDataContorls=" + allDataContorls + ", colsCount=" + colsCount + "]";
	}
	public List<List<DataControl>> getAllDataContorls() {
		return allDataContorls;
	}
	public int getColsCount() {
		return colsCount;
	}
	public void setAllDataContorls(List<List<DataControl>> allDataContorls) {
		this.allDataContorls = allDataContorls;
	}
	public void setColsCount(int colsCount) {
		this.colsCount = colsCount;
	}
}
