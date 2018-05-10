package com.uds.teamcenter.project.beans;

public class TmplateData {
	public String[] tmplates_uid;
	public String createInClient;
	public String folderType;
	public String linkProject;
	
	public String[] getTmplates_uid() {
		return tmplates_uid;
	}
	public void setTmplates_uid(String[] tmplates_uid) {
		this.tmplates_uid = tmplates_uid;
	}
	public String getCreateInClient() {
		return createInClient;
	}
	public void setCreateInClient(String createInClient) {
		this.createInClient = createInClient;
	}
	public String getFolderType() {
		return folderType;
	}
	public void setFolderType(String folderType) {
		this.folderType = folderType;
	}
	public String getLinkProject() {
		return linkProject;
	}
	public void setLinkProject(String linkProject) {
		this.linkProject = linkProject;
	}
}
