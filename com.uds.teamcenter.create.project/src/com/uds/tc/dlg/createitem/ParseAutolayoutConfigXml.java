
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
	 * 解析String类型的xml
	 * 
	 * @param xmlString
	 *            String类型的xml
	 * @throws DocumentException
	 *             String类型的xml格式不正确
	 */
	public static BeanParseAutoLayoutXmlResult xmlStringToLayoutData(String xmlString, AutoLayout autoLayout)
			throws DocumentException {
		Document document = null;
		document = DocumentHelper.parseText(xmlString);
		return parseXml(document, autoLayout);
	}

	/**
	 * 解析xml文件,当为绝对路径路径时去找绝对路径的xml文件,当为相对路径时以src目录(项目路径/bin)为根目录去找xml文件
	 * 
	 * @param xmlPath
	 *            xml文件路径
	 * @throws DocumentException
	 *             xml文件结构不正确
	 * @throws FileNotFoundException
	 *             xml文件没有找到
	 */
	public static BeanParseAutoLayoutXmlResult xmlFileToLayoutData(String xmlPath, AutoLayout autoLayout)
			throws DocumentException, FileNotFoundException {
		SAXReader reader = new SAXReader();
		Document document = null;
		// 如果是绝对路径
		if (Pattern.matches(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.REGEX_FILE_PATH_WINDOW, xmlPath)) {
			File file = new File(xmlPath);
			if (file.exists())
				document = reader.read(file);
			else
				throw new FileNotFoundException(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ERROR_FILE_NOT_FOUND + xmlPath);
		}
		// 如果是相对路径
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
	 * 解析xml内容
	 * 
	 * @param document
	 */
	public static BeanParseAutoLayoutXmlResult parseXml(Document document, AutoLayout autoLayout) {
		BeanParseAutoLayoutXmlResult result = null;
		if (document != null) {
			// autolayout元素标签
			Element root = document.getRootElement();
			//autolayout下的子标签数量
			int rowsCount = root.nodeCount();
			Composite composite = autoLayout.getComposite();
			result = new BeanParseAutoLayoutXmlResult();
			String colsCountS = root.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_COLSCOUNT);
			//列数(autolayout标签的 cols属性值)
			int colsCount = colsCountS == null ? com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.DEFAULT_COLS_COUNT : Integer.parseInt(colsCountS);
			if(colsCount<com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.DEFAULT_COLSCOUNT)
				colsCount=com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.DEFAULT_COLSCOUNT;
			result.setColsCount(colsCount);
			//初始化行与列的数据容器
			List<List<DataControl>> allDataControls = new ArrayList<List<DataControl>>();
			if (composite != null) {
				// 遍历autolayout元素标签下的所有子标签
				for (int rowIndex = 0; rowIndex < rowsCount; rowIndex++) {
					Node rowNode = root.node(rowIndex);
					// 如果是row元素标签
					if ((rowNode instanceof Element) && (com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ELEMENT_TAG_ROW.equals(rowNode.getName()))) {
						List<DataControl> dataControls = new ArrayList<DataControl>();
						Element rowElement = (Element) rowNode;
						int controlsCount = rowElement.nodeCount();
						int usedColsCount = 0;
						// 遍历row元素标签下的所有子标签
						for (int controlIndex = 0; controlIndex < controlsCount; controlIndex++) {
							Node controlNode = rowElement.node(controlIndex);
							// 如果是control元素标签
							if ((controlNode instanceof Element)
									&& (com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ELEMENT_TAG_CONTROL.equals(controlNode.getName()))) {
								DataControl dataControl = null;
								//control元素
								Element controlElement = (Element) controlNode;
								String rowsSpanS = controlElement.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_ROWSSPAN);
								// 所占行数
								int rowsSpan = rowsSpanS == null ? com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.DEFAULT_ROWSSPAN
										: Integer.parseInt(rowsSpanS);
								String colsSpanS = controlElement.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_COLSSPAN);
								// 所占列数
								int colsSpan = colsSpanS == null ? com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.DEFAULT_COLSSPAN
										: Integer.parseInt(colsSpanS);
								usedColsCount += colsSpan;
								//Identifier
								String id = controlElement.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_ID);
								String isRequired = controlElement.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_REQUIRED);
								String isEmphasis = controlElement.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_EMPHASIS);
								String lov = controlElement.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_LOV);
								String format = controlElement.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_FORMAT);
								
								// 当控件列数和大于总列数时调整最后一个控件所占列数并跳出当前行的布局,
								if (usedColsCount > colsCount) {
									colsSpan -= usedColsCount - colsCount;
								}
								// 控件类型
								String type = controlElement.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_TYPE);
								// 按钮控件
								if (ControlTypes.BUTTON.getValue().equals(type)) {
									dataControl = new DataButton(composite, rowsSpan, colsSpan);
								}
								// 自动文本下拉框控件
								else if (ControlTypes.COMBO.getValue().equals(type)) {
									dataControl = new DataCombo(composite, rowsSpan, colsSpan);
								}
								// 标签控件
								else if (ControlTypes.LABEL.getValue().equals(type)) {
									dataControl = new DataCLabel(composite, rowsSpan, colsSpan);
								}
								// 输入框控件
								else if (ControlTypes.TEXT.getValue().equals(type)) {
									dataControl = new DataText(composite, rowsSpan, colsSpan);
								}
								//时间控件
								else if(ControlTypes.DATE.getValue().equals(type)){
									dataControl = new DataDate(composite, rowsSpan, colsSpan);
								}
								else{
									continue;//出现错误
								}
								//控件标识
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
								
								
								//control标签下子标签的数量
								int paramsCount = controlElement.nodeCount();
								for (int paramIndex = 0; paramIndex < paramsCount; paramIndex++) {
									Node paramNode = controlElement.node(paramIndex);
									// 如果是param元素标签
									if ((paramNode instanceof Element) && (com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ELEMENT_TAG_PARAM.equals(paramNode.getName()))) {
										//param元素
										Element paramElement = (Element) paramNode;
										String name=paramElement.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_NAME);
										String value=paramElement.attributeValue(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.ATTRIBUTE_TAG_VALUE);
										if(name!=null&&!com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.EMPTY_STRING.equals(name.trim())&&value!=null&&!com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.EMPTY_STRING.equals(value.trim())){
											Control control=dataControl.getControl();
											//如果参数是内容
											if(ParamNames.CONTENT.getValue().equals(name)){
												if(dataControl instanceof DataCombo){
													((DataCombo)dataControl).setAutoComboItems(value.split(com.uds.tc.dlg.createitem.Constants.ConstantXmlAutoLayout.MARK_ARRAY_SPLIT));
												}
												else{
													SetControlText(control,value);													
												}
											}
											//如果是参数是是否可编辑
											if(ParamNames.ENABLED.getValue().equals(name)){
												control.setEnabled(Boolean.parseBoolean(value));
											}
											//如果参数是控件宽度
											if(ParamNames.WIDTH.getValue().equals(name)){
												int widthValue=Integer.parseInt(value);
												if(widthValue<ConstantsAutoLayout.SWT_DEFAULT)
													widthValue=ConstantsAutoLayout.SWT_DEFAULT;
												dataControl.setWidth(widthValue);
											}
											//如果参数是控件高度
											if(ParamNames.HEIGHT.getValue().equals(name)){
												int heightValue=Integer.parseInt(value);
												if(heightValue<ConstantsAutoLayout.SWT_DEFAULT)
													heightValue=ConstantsAutoLayout.SWT_DEFAULT;
												dataControl.setHeight(heightValue);
											}
											//如果是水平对齐方式
											if(ParamNames.H_ALIGNMENT.getValue().equals(name)){
												dataControl.sethAlignment(toAlignment(value));
											}
											//如果是垂直对齐方式
											if(ParamNames.V_ALIGNMENT.getValue().equals(name)){
												dataControl.setvAlignment(toAlignment(value));
											}
										}
									}
								}
								//添加一行中某一列的数据
								if (dataControl != null)
									dataControls.add(dataControl);
								//如果所占列数满了,取消后面列的解析,直接跳出
								if (usedColsCount >= colsCount) {
									break;
								}
							}
						}
						// 当布局列数总和小于总列数,扩展最后一个控件
						if (usedColsCount < colsCount) {
							DataControl dataControl= dataControls.get(dataControls.size()-1);
							dataControl.setColsSpan(dataControl.getColsSpan()+(colsCount-usedColsCount));
						}
						//添加一行的数据信息
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
