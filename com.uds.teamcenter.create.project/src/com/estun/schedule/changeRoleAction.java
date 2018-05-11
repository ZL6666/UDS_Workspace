package com.estun.schedule;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentResourceAssignment;
import com.teamcenter.rac.kernel.TCComponentResourcePool;
import com.teamcenter.rac.kernel.TCComponentRole;
import com.teamcenter.rac.kernel.TCComponentSchedule;
import com.teamcenter.rac.kernel.TCComponentScheduleTask;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCComponentUserType;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.services.rac.projectmanagement.ScheduleManagementService;
import com.teamcenter.services.rac.projectmanagement._2011_06.ScheduleManagement.MultipleScheduleLoadResponses;
import com.teamcenter.services.rac.projectmanagement._2012_02.ScheduleManagement.CreatedObjectsContainer;
import com.teamcenter.services.rac.projectmanagement._2012_09.ScheduleManagement.AssignmentCreateContainer;

public class changeRoleAction {
	
	String projct_name = "";
	String role = "";
	String user = "";
	List<Map<String, String>> roles_users = null;
	TCComponentSchedule schedule = null;
	TCSession session = null;
	
	public changeRoleAction(File file, TCComponentSchedule schedule, TCSession session) {
		this.schedule = schedule;
		this.session = session;
		
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			Workbook wb = WorkbookFactory.create(fileInputStream);
			Sheet sheet = wb.getSheetAt(0);
			roles_users = new ArrayList<>();
			Map<String, String> map = null;
			
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				this.role = row.getCell(0).toString();
				this.user = row.getCell(1).toString();
				map = new HashMap<String, String>();
				map.put("role", this.role);
				map.put("user", this.user);
				roles_users.add(map);
			}
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void change(){
		
		try {
//			TCComponentRoleType roleType = (TCComponentRoleType) session.getTypeComponent("Role");
			
			TCComponentUserType usertype = (TCComponentUserType) session.getTypeComponent("User");
//			scheduleTask.getProperty("ResourceAssignment");
			ScheduleManagementService service = ScheduleManagementService.getService(session);
			//获取所有任务
			TCComponent[] scheduleTasks = schedule.getAllTasks();
			
			com.teamcenter.services.rac.projectmanagement._2011_06.ScheduleManagement.LoadScheduleContainer loadContainer = 
					new com.teamcenter.services.rac.projectmanagement._2011_06.ScheduleManagement.LoadScheduleContainer();
			//加载时间表
			loadContainer.scheduleUids = new String[]{schedule.getUid()};
			MultipleScheduleLoadResponses mul = service.loadSchedules(
					new com.teamcenter.services.rac.projectmanagement._2011_06.ScheduleManagement.LoadScheduleContainer[]{loadContainer});
			int taskCount = scheduleTasks.length;
			AssignmentCreateContainer[] containers = new AssignmentCreateContainer[taskCount-1];
			TCComponentResourceAssignment[] ra = new TCComponentResourceAssignment[taskCount-1];
			int role_user_count = roles_users.size();
			//遍历时间表
			for(int i = 0; i < (taskCount-1); i++){
				TCComponentScheduleTask scheduleTask = (TCComponentScheduleTask) scheduleTasks[i+1];
				AIFComponentContext[] isrole = scheduleTask.getRelated("ResourceAssignment");
				TCComponentUser user = null;
				if (isrole!=null && isrole.length != 0) {
					if ((isrole[0]).getComponent() instanceof TCComponentResourcePool) {
						for(int j = 0; j < role_user_count; j++){
							Map<String, String> map = roles_users.get(j);
							String rolename = map.get("role");
							TCComponentRole role2 = ((TCComponentResourcePool)(isrole[0].getComponent())).getRole();
//							TCComponentRole role = (TCComponentRole) roleType.find("Sponsorer");
							
							if (role2.toString().contains(rolename)) {
								String username = map.get("user");
								user = (TCComponentUser) usertype.find(username);
							}
						}
					} else {
						MessageBox.post("存在没有指派角色的任务，或者该任务已经存在用户","错误",1);
						return;
					}
				} else {
					MessageBox.post("存在没有指派角色的任务，或者该任务已经存在用户","错误",1);
					return;
				}
				
				if(!scheduleTask.getProperty("fnd0state").equals("aborted")){
					containers[i] = new AssignmentCreateContainer();
					containers[i].task = scheduleTask;
					containers[i].resource = user;
					ra[i] =(TCComponentResourceAssignment) mul.scheduleData[0].resourceAssignments[i];
				}
			}

			CreatedObjectsContainer res = service.replaceAssignment(schedule, containers, ra);
			
			if (res.serviceData.sizeOfPartialErrors()==0) {
				MessageBox.post("成功","成功",2);
			} else {
				res.serviceData.getPartialError(0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			MessageBox.post("失败","失败",1);
		}
	}
}
