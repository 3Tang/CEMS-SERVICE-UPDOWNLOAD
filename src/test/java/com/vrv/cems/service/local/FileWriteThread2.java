/**   
* @Title: FileWriteThread2.java 
* @Package com.vrv.cems.service.local 
* @Description: TODO(用一句话描述该文件做什么) 
* @author tangtieqiao
		   tangtieqiao@vrvmail.com.cn
* @date 2015年9月15日 下午7:44:24 
* @version V1.0   
*/
package com.vrv.cems.service.local;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
 * @ClassName: FileWriteThread2 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author tangtieqiao
			tangtieqiao@vrvmail.com.cn
 * @date 2015年9月15日 下午7:44:24 
 *  
 */
public class FileWriteThread2 implements Runnable{
	private static Log logger=LogFactory.getLog(FileWriteThread2.class);


	private FileRWOperator op;
	private String opName;
	
	public FileWriteThread2(FileRWOperator op,String opName)
	{
		this.op=op;
		this.opName=opName;
	}
	
	/*
	* Title: run
	*Description:  
	* @see java.lang.Runnable#run() 
	*/
	@Override
	public void run() {
		logger.info("current WriteThread is"+Thread.currentThread().getName());
		try
		{
			while(true){
			this.op.Write(opName);;
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
	}
	
	

}
