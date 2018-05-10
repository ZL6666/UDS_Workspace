
/**************************************************************************************************                                      
 *                                               版权归UDS所有，2016
 **************************************************************************************************                             
 *  
 *        Function Description
 *        
 **************************************************************************************************
 * Date           Author                   History  
 * 28-Apr-2016    ZhangYang               Initial
 * 05-May-2016    ChenChun                Add an identifier
 **************************************************************************************************/

package com.uds.tc.dlg.createitem;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.SWT;

public final class Constants {
	public static final class ConstantsAutoLayout{
		public static final int DEFAULT_SHELL_WIDTH=496;
		public static final int DEFAULT_SHELL_HEIGHT=599;
		public static final int DISTANCE_BORDER_WIDTH=10;
		public static final int MARGIN_GRIDLAYOUT_TOP=3;
		public static final int MARGIN_GRIDLAYOUT_BOTTOM=3;
		public static final int MARGIN_GRIDLAYOUT_LEFT=3;
		public static final int MARGIN_GRIDLAYOUT_RIGHT=3;
		public static final String ICON_COMPANY_TRADEMARK="icons/uds.ico";
		public static final double WIDTH_SCREEN;
		public static final double HEIGHT_SCREEN;
		public static final String KEY_COLS_SPAN="列数";
		public static final String KEY_ROWS_SPAN="行数";
		public static final int SWT_DEFAULT=SWT.DEFAULT;
		static{
			Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
			WIDTH_SCREEN=dimension.getWidth();
			HEIGHT_SCREEN=dimension.getHeight();
		}
	}
	public static final class ConstantXmlAutoLayout{
		public static final String ELEMENT_TAG_AUTOLAYOUT="autolayout";
		public static final String ELEMENT_TAG_CONTROL="control";
		public static final String ELEMENT_TAG_HEIGHT="height";
		public static final String ELEMENT_TAG_WIDTH="width";
		public static final String ELEMENT_TAG_CONTENT="content";
		public static final String ELEMENT_TAG_ROW="row";
		public static final String ELEMENT_TAG_PARAM="param";
		public static final String ATTRIBUTE_TAG_NAME="name";
		public static final String ATTRIBUTE_TAG_VALUE="value";
		public static final String ATTRIBUTE_TAG_ROWSSPAN="rowsSpan";
		public static final String ATTRIBUTE_TAG_TYPE="type";
		public static final String ATTRIBUTE_TAG_ID="id";
		public static final String ATTRIBUTE_TAG_REQUIRED="isRequired";
		public static final String ATTRIBUTE_TAG_EMPHASIS="isEmphasis";
		public static final String ATTRIBUTE_TAG_LOV="lov";
		public static final String ATTRIBUTE_TAG_FORMAT="format";
		public static final String ATTRIBUTE_TAG_ENABLED="enabled";
		public static final String ATTRIBUTE_TAG_COLSSPAN="colsSpan";
		public static final String ATTRIBUTE_TAG_COLSCOUNT="cols";
		public static final String REGEX_FILE_PATH_WINDOW="^[a-zA-Z]{1}:((\\\\.+?)|(/.+?))+\\..+$";
		public static final String ERROR_FILE_NOT_FOUND=" file not found:";
		public static final String EMPTY_STRING="";
		public static final String HINT_BASE_DIRECTORY="Base directory:";
		public static final int DEFAULT_ROWSSPAN=1;
		public static final int DEFAULT_COLSCOUNT=1;
		public static final int DEFAULT_COLSSPAN=1;
		public static final int DEFAULT_COLS_COUNT=6;
		public static final int DEFAULT_COL_ZERO=0;
		public static final String MARK_ARRAY_SPLIT=",";
		public static final String METHOD_NAME_SET_TEXT="setText";
		public static enum Alignment{
			LEFT("left"),RIGHT("right"),CENTER("center");
			private final String value;
			private Alignment(String value) {
				this.value=value;
			}
			public final String getValue() {
				return value;
			}
		}
		public static enum ParamNames{
			CONTENT("content"),ENABLED("enabled"),WIDTH("width"),HEIGHT("height"),H_ALIGNMENT("hAlignment"),V_ALIGNMENT("vAlignment");
			private final String value;
			private ParamNames(String value) {
				this.value=value;
			}
			public final String getValue() {
				return value;
			}
		}
		public static enum ControlTypes{
				BUTTON("button"),TEXT("text"),COMBO("combo"),LABEL("label"),DATE("date");
				private final String value;
				private ControlTypes(String value) {
					this.value=value;
				}
				@Override
				public String toString() {
					return value;
				}
				public String getValue() {
					return value;
				}
		};
	}
	public static final class ConstantsDataButton{
		public static enum HorizontalAlignment{CENTER,LEFT,RIGHT};
		public static final int DEFAULT_BUTTON_WIDTH=100;
		public static final int DEFAULT_BUTTON_HEIGHT=35;
	}
	public static final class ConstantsDataText{
		public static enum DataTextStyle{DEFAULT,HEIGHTEN};
		public static final int DEFAULT_TEXT_HEIGHT=19;
		public static final int DEFAULT_TEXT_HEIGHTEN=100;
	}
}
