package com.uds.teamcenter.project.common;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.ListOfValuesInfo;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentListOfValues;
import com.teamcenter.rac.kernel.TCComponentListOfValuesType;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCPreferenceService;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.schemas.soa._2006_03.exceptions.ServiceException;
import com.teamcenter.services.rac.core.DataManagementService;
import com.teamcenter.services.rac.core._2008_06.DataManagement.CreateIn;
import com.teamcenter.services.rac.core._2008_06.DataManagement.CreateResponse;

public class Common {
	// 获取单值首选项
	@SuppressWarnings("deprecation")
	public static String GetPreference(String PreferenceName) {
		try {
			TCSession session = (TCSession) AIFUtility.getDefaultSession();

			TCPreferenceService PreferService = session.getPreferenceService();
			String UDSCodeConfigPath = PreferService.getString(
					TCPreferenceService.TC_preference_user, PreferenceName);

			if ((UDSCodeConfigPath == null) || (UDSCodeConfigPath.equals(""))) {
				UDSCodeConfigPath = PreferService
						.getString(TCPreferenceService.TC_preference_group,
								PreferenceName);
			}
			if ((UDSCodeConfigPath == null) || (UDSCodeConfigPath.equals(""))) {
				UDSCodeConfigPath = PreferService.getString(
						TCPreferenceService.TC_preference_site, PreferenceName);
			}
			if ((UDSCodeConfigPath == null) || (UDSCodeConfigPath.equals(""))) {
				// ShowTcErrAndMsg("没有找到首选项配置:" + PreferenceName);
				System.out.println("没有找到首选项配置:" + PreferenceName);
				return "";
			}
			return UDSCodeConfigPath;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	// 获取多值首选项
	@SuppressWarnings("deprecation")
	public static String[] GetPreferences(String PreferenceName) {
		try {
			String PreferencesValues[] = new String[0];
			TCSession session = (TCSession) AIFUtility.getDefaultSession();
			TCPreferenceService PreferService = session.getPreferenceService();
			PreferencesValues = PreferService.getStringArray(TCPreferenceService.TC_preference_user, PreferenceName);
			if ((PreferencesValues == null) || (PreferencesValues.length == 0)) {
				PreferencesValues = PreferService.getStringArray(TCPreferenceService.TC_preference_site, PreferenceName);
			}
			if ((PreferencesValues == null) || (PreferencesValues.length == 0)) {
				// ShowTcErrAndMsg("没有找到首选项配置:" + PreferenceName);
				System.out.println("没有找到首选项配置:" + PreferenceName);
				return new String[0];
			}
			return PreferencesValues;
		} catch (Exception e) {
			e.printStackTrace();
			return new String[0];
		}
	}

	@SuppressWarnings("unchecked")
	public static TCComponentFolder create(String name, String desc, String type)
			throws TCException {
		TCComponentFolder folder = null;
		TCSession session = (TCSession) AIFUtility.getDefaultSession();
		// TCComponentFolderType folderType = (TCComponentFolderType)
		// session.getTypeComponent("Folder");
		// folder = folderType.create(name, desc, type);
		//
		// return folder;
		DataManagementService dm = DataManagementService.getService(session);
		CreateIn FolderDef = new CreateIn();
		FolderDef.data.boName = type; // 文件夹类型
		FolderDef.data.stringProps.put("object_name", name);
		FolderDef.data.stringProps.put("object_desc", desc);

		CreateIn acreatein[] = new CreateIn[1];
		acreatein[0] = FolderDef;
		try {
			CreateResponse Res;
			Res = dm.createObjects(acreatein);
			if (Res.serviceData.sizeOfPartialErrors() > 0) {
				return null;
			}
			TCComponent cmp = Res.serviceData.getCreatedObject(0);
			folder = (TCComponentFolder) cmp;
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return folder;
	}

	/*
	 * 获取LOV的值和描述/本地化值：key=val,val=描述/本地化值 如果描述没有,则本地化值/值作为描述
	 */
	public static Map<String, String> GetLovValLocalVal(TCSession session,
			String lovId) {
		try {
			TcUtilsLovInfo lovinfo = GetLovInfo(session, lovId);
			if (lovinfo != null) {
				// 使用LinkedHashMap保证获取的Lov顺序
				Map<String, String> lovs = new LinkedHashMap<String, String>();
				for (int i = 0; i < lovinfo.vals.length; i++) {
					String key = lovinfo.vals[i];
					String val = key;
					boolean hasDesc = false;
					if (lovinfo.descriptions.length > i) {
						// 描述不能为空
						if (!lovinfo.descriptions[i].trim().isEmpty()) {
							val = lovinfo.descriptions[i];
							hasDesc = true;
						}
					}
					if (!hasDesc) {
						// 本地化值不能为空
						if (!lovinfo.displayNames[i].trim().isEmpty()) {
							val = lovinfo.displayNames[i];
							hasDesc = true;
						}
					}
					lovs.put(key, val);
				}
				return lovs;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private static TcUtilsLovInfo GetLovInfo(TCSession session, String lovId) {
		TcUtilsLovInfo lovinfo = null;
		try {
			TCComponentListOfValuesType componentType = (TCComponentListOfValuesType) session
					.getTypeComponent("ListOfValues");
			TCComponentListOfValues componentValues[] = componentType
					.find(lovId);
			if (componentValues != null && componentValues.length > 0) {
				TCComponentListOfValues compLov = componentValues[0];
				ListOfValuesInfo info = compLov.getListOfValues();
				String[] values = info.getStringListOfValues();
				String[] des = info.getDescriptions();
				String[] display = info.getLOVDisplayValues();
				String[] fullNames = info.getValuesFullNames();
				String[] displayDes = info.getDispDescription();

				lovinfo = new TcUtilsLovInfo();
				lovinfo.descriptions = des;
				lovinfo.fullNames = fullNames;
				lovinfo.vals = values;
				lovinfo.displayNames = display;
				lovinfo.displayDescriptions = displayDes;
			}

		} catch (TCException ex) {
			ex.printStackTrace();
		}
		return lovinfo;
	}
	
	
	public void Message(String msg){
		final JFrame jf = new JFrame(msg);

		ImageIcon icon=new ImageIcon(getClass().getResource("/img/tc_core_4.png"));
		jf.setIconImage(icon.getImage());
		jf.setSize(320, 150);
		jf.setLayout(null);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
		
		
		JLabel jl = new JLabel("",JLabel.CENTER);
		jl.setText(msg + "!!");
		jl.setFont(new Font(null, Font.BOLD, 20));
		jl.setSize(280,40);
		jl.setLocation(10, 10);
		jl.setVisible(true);
		jf.add(jl);
		
		
		JButton jb = new JButton("确定");
		jb.setFont(new Font(null, 0, 16));
		jb.setVisible(true);
		jb.setSize(70, 40);
		jb.setLocation(120,55);
		jf.add(jb);
		jf.setAlwaysOnTop(true);
		jb.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				jf.dispose();
			}
		});
		
	}

}

class TcUtilsLovInfo {
	public String[] vals;
	public String[] descriptions;
	public String[] displayNames;
	public String[] displayDescriptions;
	public String[] fullNames; // displayName + desc
}