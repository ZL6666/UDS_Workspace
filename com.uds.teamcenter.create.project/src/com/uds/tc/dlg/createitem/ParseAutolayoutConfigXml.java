
/**************************************************************************************************                                      
 *                                               ��Ȩ��UDS���У�2016
 **************************************************************************************************                             
 *  
 *        Function Description
 *        
 **************************************************************************************************
 * Date           Author                   History  
 * 14-Apr-2016    ChenChun               Initial
 * 
 **************************************************************************************************/

package com.uds.tc.dlg.createitem;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.Alignment;
import com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ControlTypes;
import com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ParamNames;
import com.uds.tc.dlg.createitem.Constants.ConstantsAutoLayout;


public class ParseAutolayoutConfigXml {
	/**
	 * ����String���͵�xml
	 * 
	 * @param xmlString
	 *            String���͵�xml
	 * @throws DocumentException
	 *             String���͵�xml��ʽ����ȷ
	 */
	public static BeanParseAutoLayoutXmlResult xmlStringToLayoutData(String xmlString, AutoLayout autoLayout)
			throws DocumentException {
		Document document = null;
		document = DocumentHelper.parseText(xmlString);
		return parseXml(document, autoLayout);
	}

	/**
	 * ����xml�ļ�,��Ϊ����·��·��ʱȥ�Ҿ���·����xml�ļ�,��Ϊ���·��ʱ��srcĿ¼(��Ŀ·��/bin)Ϊ��Ŀ¼ȥ��xml�ļ�
	 * 
	 * @param xmlPath
	 *            xml�ļ�·��
	 * @throws DocumentException
	 *             xml�ļ��ṹ����ȷ
	 * @throws FileNotFoundException
	 *             xml�ļ�û���ҵ�
	 */
	public static BeanParseAutoLayoutXmlResult xmlFileToLayoutData(String xmlPath, AutoLayout autoLayout)
			throws DocumentException, FileNotFoundException {
		SAXReader reader = new SAXReader();
		Document document = null;
		// ����Ǿ���·��
		if (Pattern.matches(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.REGEX_FILE_PATH_WINDOW, xmlPath)) {
			File file = new File(xmlPath);
			if (file.exists())
				document = reader.read(file);
			else
				throw new FileNotFoundException(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ERROR_FILE_NOT_FOUND + xmlPath);
		}
		// ��������·��
		else {
			//URL url = ClassLoader.getSystemResource(xmlPath);
			URL url = ParseAutolayoutConfigXml.class.getClassLoader().getResource(xmlPath);
			if (url == null) {
				//String urlError = ClassLoader.getSystemResource(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.EMPTY_STRING).getPath();
				String urlError = ParseAutolayoutConfigXml.class.getClassLoader().getResource("/").getPath();
				throw new FileNotFoundException(
						com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.HINT_BASE_DIRECTORY + urlError
								+ com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ERROR_FILE_NOT_FOUND + xmlPath);
			} else {
				document = reader.read(url);
			}
		}
		return parseXml(document, autoLayout);
	}

	/**
	 * ����xml����
	 * 
	 * @param document
	 */
	public static BeanParseAutoLayoutXmlResult parseXml(Document document, AutoLayout autoLayout) {
		BeanParseAutoLayoutXmlResult result = null;
		if (document != null) {
			// autolayoutԪ�ر�ǩ
			Element root = document.getRootElement();
			//autolayout�µ��ӱ�ǩ����
			int rowsCount = root.nodeCount();
			Composite composite = autoLayout.getComposite();
			result = new BeanParseAutoLayoutXmlResult();
			String colsCountS = root.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_COLSCOUNT);
			//����(autolayout��ǩ�� cols����ֵ)
			int colsCount = colsCountS == null ? com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.DEFAULT_COLS_COUNT : Integer.parseInt(colsCountS);
			if(colsCount<com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.DEFAULT_COLSCOUNT)
				colsCount=com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.DEFAULT_COLSCOUNT;
			result.setColsCount(colsCount);
			//��ʼ�������е���������
			List<List<DataControl>> allDataControls = new ArrayList<List<DataControl>>();
			if (composite != null) {
				// ����autolayoutԪ�ر�ǩ�µ������ӱ�ǩ
				for (int rowIndex = 0; rowIndex < rowsCount; rowIndex++) {
					Node rowNode = root.node(rowIndex);
					// �����rowԪ�ر�ǩ
					if ((rowNode instanceof Element) && (com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ELEMENT_TAG_ROW.equals(rowNode.getName()))) {
						List<DataControl> dataControls = new ArrayList<DataControl>();
						Element rowElement = (Element) rowNode;
						int controlsCount = rowElement.nodeCount();
						int usedColsCount = 0;
						// ����rowԪ�ر�ǩ�µ������ӱ�ǩ
						for (int controlIndex = 0; controlIndex < controlsCount; controlIndex++) {
							Node controlNode = rowElement.node(controlIndex);
							// �����controlԪ�ر�ǩ
							if ((controlNode instanceof Element)
									&& (com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ELEMENT_TAG_CONTROL.equals(controlNode.getName()))) {
								DataControl dataControl = null;
								//controlԪ��
								Element controlElement = (Element) controlNode;
								String rowsSpanS = controlElement.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_ROWSSPAN);
								// ��ռ����
								int rowsSpan = rowsSpanS == null ? com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.DEFAULT_ROWSSPAN
										: Integer.parseInt(rowsSpanS);
								String colsSpanS = controlElement.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_COLSSPAN);
								// ��ռ����
								int colsSpan = colsSpanS == null ? com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.DEFAULT_COLSSPAN
										: Integer.parseInt(colsSpanS);
								usedColsCount += colsSpan;
								//Identifier
								String id = controlElement.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_ID);
								String isRequired = controlElement.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_REQUIRED);
								String isEmphasis = controlElement.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_EMPHASIS);
								String lov = controlElement.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_LOV);
								String format = controlElement.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_FORMAT);
								
								// ���ؼ������ʹ���������ʱ�������һ���ؼ���ռ������������ǰ�еĲ���,
								if (usedColsCount > colsCount) {
									colsSpan -= usedColsCount - colsCount;
								}
								// �ؼ�����
								String type = controlElement.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_TYPE);
								// ��ť�ؼ�
								if (ControlTypes.BUTTON.getValue().equals(type)) {
									dataControl = new DataButton(composite, rowsSpan, colsSpan);
								}
								// �Զ��ı�������ؼ�
								else if (ControlTypes.COMBO.getValue().equals(type)) {
									dataControl = new DataCombo(composite, rowsSpan, colsSpan);
								}
								// ��ǩ�ؼ�
								else if (ControlTypes.LABEL.getValue().equals(type)) {
									dataControl = new DataCLabel(composite, rowsSpan, colsSpan);
								}
								// �����ؼ�
								else if (ControlTypes.TEXT.getValue().equals(type)) {
									dataControl = new DataText(composite, rowsSpan, colsSpan);
								}
								//ʱ��ؼ�
								else if(ControlTypes.DATE.getValue().equals(type)){
									dataControl = new DataDate(composite, rowsSpan, colsSpan);
								}
								else{
									continue;//���ִ���
								}
								//�ؼ���ʶ
								if(id != null){
									dataControl.SetIdentifier(id);
								}
								if(isRequired != null){
									boolean required = Boolean.parseBoolean(isRequired);
									dataControl.m_isRequired = required;
								}
								if(isEmphasis != null){
									boolean emphasis = Boolean.parseBoolean(isEmphasis);
									dataControl.m_isEmphasis = emphasis;
								}
								if(lov != null){
									dataControl.m_lov = lov;
								}
								if(format != null){
									dataControl.m_format = format;
								}
								
								
								//control��ǩ���ӱ�ǩ������
								int paramsCount = controlElement.nodeCount();
								for (int paramIndex = 0; paramIndex < paramsCount; paramIndex++) {
									Node paramNode = controlElement.node(paramIndex);
									// �����paramԪ�ر�ǩ
									if ((paramNode instanceof Element) && (com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ELEMENT_TAG_PARAM.equals(paramNode.getName()))) {
										//paramԪ��
										Element paramElement = (Element) paramNode;
										String name=paramElement.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_NAME);
										String value=paramElement.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_VALUE);
										if(name!=null&&!com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.EMPTY_STRING.equals(name.trim())&&value!=null&&!com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.EMPTY_STRING.equals(value.trim())){
											Control control=dataControl.getControl();
											//�������������
											if(ParamNames.CONTENT.getValue().equals(name)){
												if(dataControl instanceof DataCombo){
													((DataCombo)dataControl).setAutoComboItems(value.split(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.MARK_ARRAY_SPLIT));
												}
												else{
													SetControlText(control,value);													
												}
											}
											//����ǲ������Ƿ�ɱ༭
											if(ParamNames.ENABLED.getValue().equals(name)){
												control.setEnabled(Boolean.parseBoolean(value));
											}
											//��������ǿؼ����
											if(ParamNames.WIDTH.getValue().equals(name)){
												int widthValue=Integer.parseInt(value);
												if(widthValue<ConstantsAutoLayout.SWT_DEFAULT)
													widthValue=ConstantsAutoLayout.SWT_DEFAULT;
												dataControl.setWidth(widthValue);
											}
											//��������ǿؼ��߶�
											if(ParamNames.HEIGHT.getValue().equals(name)){
												int heightValue=Integer.parseInt(value);
												if(heightValue<ConstantsAutoLayout.SWT_DEFAULT)
													heightValue=ConstantsAutoLayout.SWT_DEFAULT;
												dataControl.setHeight(heightValue);
											}
											//�����ˮƽ���뷽ʽ
											if(ParamNames.H_ALIGNMENT.getValue().equals(name)){
												dataControl.sethAlignment(toAlignment(value));
											}
											//����Ǵ�ֱ���뷽ʽ
											if(ParamNames.V_ALIGNMENT.getValue().equals(name)){
												dataControl.setvAlignment(toAlignment(value));
											}
										}
									}
								}
								//���һ����ĳһ�е�����
								if (dataControl != null)
									dataControls.add(dataControl);
								//�����ռ��������,ȡ�������еĽ���,ֱ������
								if (usedColsCount >= colsCount) {
									break;
								}
							}
						}
						// �����������ܺ�С��������,��չ���һ���ؼ�
						if (usedColsCount < colsCount) {
							DataControl dataControl= dataControls.get(dataControls.size()-1);
							dataControl.setColsSpan(dataControl.getColsSpan()+(colsCount-usedColsCount));
						}
						//���һ�е�������Ϣ
						allDataControls.add(dataControls);
					}
				}
				result.setAllDataContorls(allDataControls);
			}
		}
		return result;
	}
	public static int toAlignment(String alignmentString){
		if(Alignment.LEFT.getValue().equals(alignmentString))
			return SWT.LEFT;
		else if(Alignment.RIGHT.getValue().equals(alignmentString))
			return SWT.RIGHT;
		else
			return SWT.CENTER;
	}
	public static boolean SetControlText(Control control, String value){
		try {
			Method method= control.getClass().getMethod(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.METHOD_NAME_SET_TEXT, String.class);
			method.invoke(control, value);
			return true;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}
/*	public static void main(String[] args) {
		try {
			AutoLayout autoLayout = new AutoLayout();
			BeanParseAutoLayoutXmlResult result = xmlFileToLayoutData("XmlAutolayout.xml", autoLayout);
			autoLayout.open("gaga", result.getAllDataContorls(), result.getColsCount());
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//System.out.println(Enum.valueOf(ParamNames.class, "content").getValue());
	}*/
}
