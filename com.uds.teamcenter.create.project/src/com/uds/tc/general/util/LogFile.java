 /**************************************************************************************************                                      
 *                                               ∞Ê»®πÈUDSÀ˘”–£¨2015
 **************************************************************************************************                             
 *  
 *        Function Description
 *        
 **************************************************************************************************
 * Date           Author                   History  
 * 29-Oct-2015    ChenChun               Initial
 * 
 **************************************************************************************************/


package com.uds.tc.general.util;

public class LogFile {

	private String m_filePath;
	private java.io.File m_file;
	private java.io.FileWriter m_writer;
	public LogFile(String filePath)	{
		if(filePath != null)
			m_filePath = filePath;
		else
			m_filePath = "";
		m_file = new java.io.File(m_filePath);
	}
	public boolean Init(){
		if(CheckWritable()){
			try{
				m_writer = new java.io.FileWriter(m_file,true);
				return true;
			}catch(java.io.IOException ex){
				
			}
			
		}
		return false;
	}
	public void Close(){
		if(m_writer != null){
			try{
				m_writer.close();
				m_writer = null;
			}catch(java.io.IOException ex){
				
			}

		}

	}
	private boolean CanWrite(){
		if(m_writer != null){
			return true;
		}
		return false;
	}
	
	private boolean CheckWritable(){
		if(m_file.exists())	{
			m_file.setWritable(true);
			return m_file.canWrite();
		}
		try{
			boolean isSuccess = m_file.createNewFile();
			if(isSuccess)
				m_file.setWritable(true);
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		return m_file.canWrite();

	}
	public void write(String content){
		if(this.CanWrite()){
			try{
				m_writer.write(content);
				m_writer.write("\r\n");
				m_writer.flush();
			}catch(java.io.IOException ex){
				
			}
			
		}
		else{
			System.out.println(content);
		}
	}
}
