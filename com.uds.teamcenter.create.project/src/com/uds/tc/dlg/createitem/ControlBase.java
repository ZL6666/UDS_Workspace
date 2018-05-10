

/**************************************************************************************************                                      
 *                                               版权归UDS所有，2016
 **************************************************************************************************                             
 *  
 *        Function Description
 *        
 **************************************************************************************************
 * Date           Author                   History  
 * 05-May-2016    ChenChun                Add an identifier
 **************************************************************************************************/

package com.uds.tc.dlg.createitem;

public abstract class ControlBase {
	//用于标示控件
	protected String m_identifier;
	public String GetIdentifier(){
		if(m_identifier != null)
			return m_identifier;
		
		return "";
	}
	public void SetIdentifier(String id){
		m_identifier = id;
	}
	//表示该控件为必填项
	public boolean m_isRequired = false;
	//表示该控件需要特别标示,如label控件颜色变化
	public boolean m_isEmphasis = false;
	//关联lov
	public String m_lov = null;
	//日期控件的格式
	public String m_format = null;
}
