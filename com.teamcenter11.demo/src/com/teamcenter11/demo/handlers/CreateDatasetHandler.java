package com.teamcenter11.demo.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.teamcenter.rac.util.MessageBox;

public class CreateDatasetHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		MessageBox.post("��ӭ����Dataset", "��ʾ", MessageBox.INFORMATION);
		return null;
	}

}
