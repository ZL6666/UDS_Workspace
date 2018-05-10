package com.uds.teamcenter.create.project.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.teamcenter.rac.aif.AIFDesktop;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
import com.uds.teamcenter.project.Service.TmplateReq;
import com.uds.teamcenter.project.beans.TmplateData;

public class folderhandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		AbstractAIFUIApplication app = AIFUtility.getCurrentApplication();
		InterfaceAIFComponent selcom = app.getTargetComponent();
		
		if(!(selcom instanceof TCComponentFolder)){
			MessageBox.post("请选择文件夹进行操作","错误",1);
			return null;
		}
		
		String m_commandId = "TC_PROJMGR__getFolderTemplate";
		
		TmplateReq tmplate = new TmplateReq(m_commandId);
		
		String data;
		try {
			data = tmplate.Data();
			TCSession session = (TCSession) AIFDesktop.getActiveDesktop().getCurrentApplication().getSession();
			TmplateData tmplateData = new TmplateData();
			String tmplates[] = {};
			//如果成功返回
			if(data.startsWith("ok")){
				data = data.substring(2);
				
				JsonParser jp = new JsonParser();
				JsonElement je = jp.parse(data);
				JsonObject all = je.getAsJsonObject();
				JsonArray tmp = all.get("tmplates").getAsJsonArray();
				
				int co = tmp.size();
				tmplates = new String[co];
				for (int i = 0; i < co; i++) {
					je = tmp.get(i);
					JsonObject jo = je.getAsJsonObject();
					
					tmplates[i] = jo.get("tUid").toString().replace("\"", "").trim();

				}
				
				tmplateData.setTmplates_uid(tmplates);
				
				String createInClient = all.get("createInClient").toString().replace("\"", "").trim();
				tmplateData.setCreateInClient(createInClient);
				String folderType = all.get("folderType").toString().replace("\"", "").trim();
				
				if(folderType.equals("")||folderType==null){
					tmplateData.setFolderType("Folder");
				} else {
					tmplateData.setFolderType(folderType);
				}

				String linkProject = all.get("linkProject").toString().replace("\"", "").trim();
				tmplateData.setLinkProject(linkProject);//0代表不关联，1代表关联
				
				// 
				new CreatWay(session,selcom,tmplateData.getCreateInClient(),tmplateData.getTmplates_uid(),tmplateData.getFolderType(),tmplateData.getLinkProject()); 
				
				return tmplateData;
			} else {
				MessageBox.post("失败的请求！","失败信息",MessageBox.ERROR);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		
	}

}
