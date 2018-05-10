package com.uds.teamcenter.project.Service;


import com.WebService;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
import com.uds.teamcenter.project.common.Common;


public class TmplateReq {
	
	String m_commandId = "";
	String xmlName = null;
	public TmplateReq(String commandId){
		m_commandId = commandId;
	}
	
	public String Data() throws Exception{
		AbstractAIFUIApplication app = AIFUtility.getCurrentApplication();
		TCSession session = (TCSession)app.getSession();
		String url = "http/WebCommonService.svc?wsdl";
//		String url = "http://192.168.222.7:8723/WebCommonService.svc?wsdl";

		String PreferenceName = "UDS_ENV_UDSSERVER_IP_PORT";
		
		url = url.replace("http", "http://"+Common.GetPreference(PreferenceName));
		
//		String m_commandId = "TC_PROJMGR__getFolderTemplate";
//		String m_commandId = "TC_PROJMGR__getFolderAttrInfo";
//		String m_commandId = "TC_PROJMGR__getDocCheckList";
		if (m_commandId.equals("TC_PROJMGR__getDocCheckList")) {
			String xmls[] = Common.GetPreferences("UDS_ENV_XML_NAME");
			
			if (xmls.length==0) {
				xmlName = "default";
			} else {
				//根据组选择齐备性检查的xml，默认default
				String group = session.getGroup().getFullName();
				
				for(String xml : xmls){
					if (group.contains(xml.split("=")[0])) {
						xmlName = xml.split("=")[1];
					}
				}
			}
			if (xmlName == null) {
				xmlName = "default";
			}
		}
		
		String res = new WebService().SendSpecialCommand3(url, m_commandId, xmlName, null, null);
		
		if (res == null) {
			MessageBox.post("连接失败","错误",1);
			return null;
		}
		
		System.out.println("11111==========="+res);

		return res;
		
	}
}
