package com.uds.teamcenter.create.project.handler;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
import com.uds.teamcenter.project.Service.TmplateReq;
import com.uds.teamcenter.project.beans.ItemBean;
import com.uds.teamcenter.project.beans.XmlBean;
import com.uds.teamcenter.project.common.Common;
import com.uds.teamcenter.project.ui.CheckFrame;

public class CheckReady extends AbstractHandler {

	String dlgTitle = "�ļ��뱸�Լ��";
	AbstractAIFUIApplication app = null;
	TCSession session = null;
	InterfaceAIFComponent selComp = null;
	File file = null;
	String localDir = "c:\\Temp\\";
	TCComponentFolder itemfd = null;
	Vector<TCComponentItem> vec;
	String[] folders = null;
	
	private WaitingDialog wDlg = null;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			app = AIFUtility.getCurrentApplication();
			session = (TCSession) app.getSession();
			selComp = app.getTargetComponent();
			folders = Common.GetPreferences("UDS_ENV_FOLDER_EXCLUDE");
			if (selComp instanceof TCComponentFolder) {
				itemfd = (TCComponentFolder) selComp;
				vec = new Vector<>();
				vec.clear();
				Vector<TCComponentItem> vector = traversal(itemfd);
				String type = itemfd.getType();
				String tcType[] = Common.GetPreferences("UDS_ENV_FOLDER_TYPE");
//				if (tcType.startsWith("error")) {
//					tcType = tcType.replace("error", "");
//					MessageBox.post(tcType, "����", 1);
//					return null;
//				}
				boolean typerea = true;
				for(int i = 0 ;i<tcType.length;i++){
					if (type.equals(tcType[i])) {
						
						typerea = false;
						break;
					}
				}
				
				if(typerea){
					MessageBox.post("�ļ������Ͳ���" + "����������ԣ�", "����", 1);
					return null;
				}
				// �ӷ�������ȡxml
				
				String m_commandId = "TC_PROJMGR__getDocCheckList";
				TmplateReq tmplateReq = new TmplateReq(m_commandId);
				
				wDlg = new WaitingDialog(app.getDesktop().getFrame(),
						"���ڴ�������,���Ե�...", "���ڴ�������,���Ե�...");
				wDlg.showDialog();
				wDlg.setModal(true);
				
				String res = tmplateReq.Data();
				if (res.startsWith("ok")) {
					res = res.replace("ok", "").trim();
				} else {
					MessageBox.post("ʧ�ܵ�����", "����", MessageBox.ERROR);
					return null;
				}
				
				wDlg.setVisible(false);
				// ��ȡ��ת��String
				Reader rr = new StringReader(res);
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder domBuilder = builderFactory
						.newDocumentBuilder();
				Document document = domBuilder.parse(new InputSource(rr));
				NodeList sheetList = document.getElementsByTagName("Sheet");
				System.out.println("һ����" + sheetList.getLength() + "��Sheet");
				List<XmlBean> xblist = new ArrayList<XmlBean>();
				// ����ÿһ��sheet�ڵ�
				for (int i = 0; i < sheetList.getLength(); i++) {
					System.out.println("=================���濪ʼ������" + (i + 1)
							+ "��sheet������=================");
					// ͨ�� item(i)���� ��ȡһ��sheet�ڵ㣬sheetList������ֵ��0��ʼ
					Node sheet = sheetList.item(i);
					NodeList childNodes = sheet.getChildNodes();
					// ��ȡsheet�ڵ���������Լ���
					NamedNodeMap attrs = sheet.getAttributes();
					System.out.println("�� " + (i + 1) + "��Sheet����"
							+ attrs.getLength() + "������");
					String sheetname = "";
					// ����sheet������
					for (int j = 0; j < attrs.getLength(); j++) {
						// ͨ��item(index)������ȡsheet�ڵ��ĳһ������
						Node attr = attrs.item(j);
						// ��ȡ������
						System.out.print("��������" + attr.getNodeName());
						// ��ȡ����ֵ
						System.out.println("--����ֵ" + attr.getNodeValue());
						if (attr.getNodeName().equals("TableName")) {
							sheetname = attr.getNodeValue();
						}
					}
					Vector<ItemBean> vc = new Vector<ItemBean>();
					for (int k = 0; k < childNodes.getLength(); k++) {
						// ���ֳ�text���͵�node�Լ�element���͵�node
						if (childNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
							NamedNodeMap childattrs = childNodes.item(k)
									.getAttributes();
							String itemcode = "";
							String itemname = "";
							for (int j = 0; j < childattrs.getLength(); j++) {
								// ͨ��item(index)������ȡsheet�ڵ��ĳһ������
								Node attr = childattrs.item(j);
								// ��ȡ������
								System.out.print("��������" + attr.getNodeName());
								// ��ȡ����ֵ
								System.out.println("--����ֵ"
										+ attr.getNodeValue());
								if (attr.getNodeName().equals("ItemCode")) {
									itemcode = attr.getNodeValue();
								} else if (attr.getNodeName()
										.equals("ItemName")) {
									itemname = attr.getNodeValue();
								}
							}
							ItemBean ib = new ItemBean(itemcode, itemname);
							vc.add(ib);
						}
					}
					XmlBean xb = new XmlBean(sheetname, vc);
					xblist.add(xb);
				}
				CheckFrame checkframe = new CheckFrame(vector);
				checkframe.cf(xblist);
			} else {
				MessageBox.post("��ѡ��Ĳ����ļ��У���ѡ���ļ��У�", "����", 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ������Ŀ�ļ���
	 * 
	 * @param tcfolder
	 * @return
	 * @throws TCException
	 */
	private Vector<TCComponentItem> traversal(TCComponentFolder tcfolder)
			throws TCException {
		
		AIFComponentContext[] childlines = tcfolder.getChildren();
		for (int i = 0; i < childlines.length; i++) {
			if (childlines[i].getComponent() instanceof TCComponentFolder) {
				TCComponentFolder tf1 = (TCComponentFolder) childlines[i].getComponent();
				int state = 0;
				if (folders != null) {
					for (int j = 0; j < folders.length; j++) {
						if (tf1.getProperty("object_name").equals(folders[j])) {
							state = 1;
							break;
						}
					}
				}
				
				if (state == 1) {
					continue;
				}
				traversal(tf1);
			} else if (childlines[i].getComponent() instanceof TCComponentItem) {
				TCComponentItem tf2 = (TCComponentItem) childlines[i].getComponent();
				vec.add(tf2);
			}
		}
		return vec;
	}

}
