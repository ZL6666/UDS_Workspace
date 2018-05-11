package com.estun.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.teamcenter.rac.aif.AIFClipboard;
import com.teamcenter.rac.aif.AIFDesktop;
import com.teamcenter.rac.aif.AIFPortal;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentSchedule;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.services.rac.projectmanagement.ScheduleManagementService;
import com.teamcenter.services.rac.projectmanagement._2008_06.ScheduleManagement.ScheduleDeliverableData;

public class creatitem extends AbstractHandler {

	static TCSession session = null;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		AbstractAIFUIApplication app = AIFUtility.getCurrentApplication();
		
		InterfaceAIFComponent selcom = app.getTargetComponent();
		session = (TCSession) app.getSession();
		if (selcom instanceof TCComponentSchedule) {
			List<TCComponent> items = getClipBoard();
			
			if (items == null || items.size()==0) {
				MessageBox.post("请复制需要交付的零组件！","错误",1);
				return null;
			}

			try {
				TCComponentSchedule schedule = (TCComponentSchedule) selcom;
				
				addScheduleDeliverableData(schedule, items);
				
				MessageBox.post("插入交付件成功!","提示",2);
			} catch (Exception e) {
				e.printStackTrace();
				MessageBox.post("失败！"+e.getLocalizedMessage(),"error",1);
			}
			
		} else {
			MessageBox.post("请选择时间表进行操作！！","错误",1);
		}
		
		System.out.println(selcom);
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<TCComponent> getClipBoard() {
		ArrayList<TCComponent> clipComps = new ArrayList<TCComponent>();
		AIFClipboard clipboard = AIFPortal.getClipboard();
		Vector<TCComponent> compVector = clipboard.toVector();
		if (compVector==null) {
//			MessageBox.post("请先复制相关交付件","ERROR",1);
			return null;
		}
		for (int i = 0; i < compVector.size(); i++) {
			clipComps.add((TCComponent) compVector.get(i));
		}
		return clipComps;
	}

	// 添加时间表交付物
		public static void addScheduleDeliverableData(TCComponentSchedule newSchedule,List<TCComponent> vec) throws TCException {
			session = (TCSession) AIFDesktop.getActiveDesktop().getCurrentApplication().getSession();
			Vector<ScheduleDeliverableData> data = new Vector<ScheduleDeliverableData>();
			for (int i = 0; i < vec.size(); i++) 
			{
				TCComponentItem item =(TCComponentItem) vec.get(i);
				String type = item.getType();
//				 TCComponentItemType item_type = (TCComponentItemType) session.getTypeComponent(type);
//				 TCComponentItem item =item_type.find(fileBean.getProperty("item_id"));
//				 if(item==null)
//				 {
//					 continue;
//				 }
				TCComponentItemRevision itemRevision = item.getLatestItemRevision();
				String name = item.getProperty("object_name");
				ScheduleDeliverableData localObject = new ScheduleDeliverableData();
				((ScheduleDeliverableData) localObject).deliverableName = name;
				((ScheduleDeliverableData) localObject).deliverableType = type;
				((ScheduleDeliverableData) localObject).deliverableReference = itemRevision;
				((ScheduleDeliverableData) localObject).schedule = newSchedule;
				data.add(localObject);
			}
			ScheduleDeliverableData[] datas = new ScheduleDeliverableData[data.size()];
			for (int i = 0; i < data.size(); i++) 
			{
				datas[i] = data.get(i);
			}
			ScheduleManagementService localScheduleManagementService = ScheduleManagementService.getService(session);
			localScheduleManagementService.createScheduleDeliverableTemplates(datas);

		}
}
