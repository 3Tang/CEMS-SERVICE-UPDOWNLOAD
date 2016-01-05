/**   
 * @Title: FileReadThread2.java 
 * @Package com.vrv.cems.service.local 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author tangtieqiao
		   tangtieqiao@vrvmail.com.cn
 * @date 2015年9月14日 下午7:50:53 
 * @version V1.0   
 */
package com.vrv.cems.service.local;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @ClassName: FileReadThread2
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tangtieqiao tangtieqiao@vrvmail.com.cn
 * @date 2015年9月14日 下午7:50:53
 * 
 */
public class FileReadThread2 implements Runnable {
	private static Log logger = LogFactory.getLog(FileReadThread2.class);

	private FileRWOperator op;
	private String opName;
	
	public FileReadThread2(FileRWOperator op,String opName)
	{
		this.op=op;
		this.opName=opName;
	}
	
	/*
	 * Title: runDescription:
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		logger.info("current Thread is"+Thread.currentThread().getName());
		try
		{
			while(true){
			this.op.Read(this.opName);
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
	}

}
