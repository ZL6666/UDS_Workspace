
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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.uds.teamcenter.project.common.DatePickerCombo;


public class DataDate extends DataControl{
	private DatePickerCombo m_date;
	@SuppressWarnings("unused")
	private String m_pattern;
	
	public DataDate(Composite parent, int rowsSpan, int colsSpan) {
		super(parent, rowsSpan, colsSpan);
		// TODO Auto-generated constructor stub
		this.m_date = new DatePickerCombo(parent,SWT.BORDER);
		
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat();
			//yyyyMMddHHmmss
			String pattern = "yyyy年MM月dd日";
			dateFormat.applyPattern(pattern);
			this.m_date.SetDateFormatObj(dateFormat);
		}catch(Exception ex){
			String msg = ex.getMessage();
			System.console().writer().println(msg);
		}
	}

	@Override
	public Control getControl() {
		// TODO Auto-generated method stub
		return m_date;
	}

	@Override
	public String getInput() {
		// TODO Auto-generated method stub
		if(m_date != null){
			try{				
				String value = m_date.getText();
				return value;
				
			}catch(Exception ex){
				String msg = ex.getMessage();
				System.console().writer().println(msg);
			}
		}
		return null;
	}

	@Override
	public void clearText() {
		// TODO Auto-generated method stub
		if(m_date != null){
			m_date.clearSelection();
		}
	}

	public void SetDate(int year, int month, int day){
		if(m_date != null){
			String format = "yyyy/M/d";
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			String value = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
			try{
				Date dt = dateFormat.parse(value);
				SetDate(dt);
	
			}catch(Exception ex){
				String msg = ex.getMessage();
				System.console().writer().println(msg);
			}
		}
	}
	public void SetDate(Date dt){
		if(m_date != null){
			m_date.setDate(dt);
		}
	}
	public void SetPattern(String pattern){
		m_pattern = pattern;
		if(m_date != null){
			m_date.SetPattern(pattern);
		}
	}
}
