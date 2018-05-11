package com.teamcenter11.demo.command;

import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentFolderType;
import com.teamcenter.rac.kernel.TCComponentType;
import com.teamcenter.rac.kernel.TCSession;

public class CreateFolderOperation extends AbstractAIFOperation {
	
	private TCComponent tccomponent = null;
	private String folderName = null;
	private TCSession session = null;
	
	public CreateFolderOperation(TCSession session,TCComponent tccomponent,String folderName){
		this.tccomponent = tccomponent;
		this.folderName = folderName;
		this.session = session;
	}
	
	@Override
	public void executeOperation() throws Exception {
		TCComponentFolderType t = (TCComponentFolderType) session.getTypeComponent("Folder");
		TCComponentFolder f = t.create(folderName, "My Folder Description", "Folder");
		tccomponent.add("contents", f);
		
	}

}
