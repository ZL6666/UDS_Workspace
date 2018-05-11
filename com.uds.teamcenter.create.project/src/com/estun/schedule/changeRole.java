package com.estun.schedule;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponentSchedule;
import com.teamcenter.rac.kernel.TCComponentScheduleTask;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;


public class changeRole extends AbstractHandler {
	
	TCSession session = null;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		AbstractAIFUIApplication app = AIFUtility.getCurrentApplication();
		
		InterfaceAIFComponent selcom = app.getTargetComponent();
		session = (TCSession) app.getSession();
		selcom.getType();
		if (!(selcom instanceof TCComponentScheduleTask)) {
			MessageBox.post("请在时间表管理器中选择时间表进行操作","错误",1);
			return null;
		}

		try {
			TCComponentScheduleTask scheduleTask = (TCComponentScheduleTask) selcom;
			TCComponentSchedule schedule = ((TCComponentSchedule)scheduleTask.getReferenceProperty("schedule_tag"));

			changeRoleUI chUi = new changeRoleUI(schedule, session);
			chUi.UI();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
