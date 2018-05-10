package com.uds.teamcenter.open;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

public class project extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		
//		String id = PlatformUI.getWorkbench().getPerspectiveRegistry().findPerspectiveWithLabel("ÏîÄ¿").getId();
		String id = "com.teamcenter.rac.project.ProjectPerspective";
		IWorkbenchWindow window= PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		try {
			PlatformUI.getWorkbench().showPerspective(id, window);
		} catch (WorkbenchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
