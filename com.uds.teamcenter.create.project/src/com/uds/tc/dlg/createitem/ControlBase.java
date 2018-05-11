

/**************************************************************************************************                                      
 *                                               ��Ȩ��UDS���У�2016
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
	//���ڱ�ʾ�ؼ�
	protected String m_identifier;
	public String GetIdentifier(){
		if(m_identifier != null)
			return m_identifier;
		
		return "";
	}
	public void SetIdentifier(String id){
		m_identifier = id;
	}
	//��ʾ�ÿؼ�Ϊ������
	public boolean m_isRequired = false;
	//��ʾ�ÿؼ���Ҫ�ر��ʾ,��label�ؼ���ɫ�仯
	public boolean m_isEmphasis = false;
	//����lov
	public String m_lov = null;
	//���ڿؼ��ĸ�ʽ
	public String m_format = null;
}
