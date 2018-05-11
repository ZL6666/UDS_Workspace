/**************************************************************************************************                                      
 *                                               版权归UDS所有，2016
 **************************************************************************************************                             
 *  
 *        Function Description
 *        
 **************************************************************************************************
 * Date           Author                   History  
 * 14-Apr-2016    ChenChun               Initial
 * 
 **************************************************************************************************/


package com.uds.module.createitem.common;

import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class ItemCreationManager {
	
	public static ItemCreationProcessing itemCreation;
	public TCComponent cmp;
	public  TCComponentFolder homeFloder;
	public  TCSession session;
	public String panduanname;
	public  String foldertype;
	public String createInClient="";
	public TCComponent selectcmp=null;
	public ItemCreationManager (TCComponent cmp,TCComponentFolder homeFloder,TCSession session,String panduanname,String type,String createInClient){
		this.cmp=cmp;
		foldertype=type;
		this.homeFloder=homeFloder;
		this.session=session;
		this.panduanname=panduanname;
		this.createInClient=createInClient;
		 DoUserTask();
	}
	public ItemCreationManager (TCComponent cmp,TCComponentFolder homeFloder,TCSession session,String panduanname,String type,String createInClient,TCComponent selectcmp){
		this.cmp=cmp;
		foldertype=type;
		this.homeFloder=homeFloder;
		this.session=session;
		this.panduanname=panduanname;
		this.createInClient=createInClient;
		this.selectcmp=selectcmp;
		 DoUserTask();
	}
	public ItemCreationProcessing getItemCreationProcessing(){
		return itemCreation;
	}
	
	protected void DoUserTask(){
		
		
		String dlgTitle = "创建对象";
		//String success = "完成";
		String failure = "失败";
		try{
			//用户选择的对象
	/*		InterfaceAIFComponent selComp = AIFUtility.getCurrentApplication().getTargetComponent();
			String selUid = selComp.getUid();
			TCComponent selCompObj = null;
			if(selComp instanceof TCComponent){
				selCompObj = (TCComponent)selComp;
			}*/

			itemCreation = new ItemCreationProcessing(null, dlgTitle,cmp,homeFloder,panduanname,cmp.getType(),createInClient,selectcmp);
	/*		itemCreation.m_selectedCompUid = selUid;
			itemCreation.m_selCompObj = selCompObj;*/

			String result = itemCreation.Processing();
			if(result != null && !result.equals("")){
				MessageBox.post(result, dlgTitle, MessageBox.ERROR);		
			}
	/*		if(selCompObj != null){
				selCompObj.refresh();
			}
			*/
		}catch(Exception ex){
//			m_log.write(ex.getLocalizedMessage());
			String msg = failure;
			MessageBox.post(msg, dlgTitle, MessageBox.INFORMATION);	
		}
	
	}
	
//	@Override
//	protected boolean DoPreTask(){
//		return true;
//	}
	

}
