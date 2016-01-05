package com.vrv.cems.service.updownload.task; 
  
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.sys.common.ftp.FTPClient;
import com.sys.common.ftp.FTPClientBuilder;
import com.sys.common.ftp.exception.FTPClientException;
import com.vrv.cems.service.updownload.config.SystemConfig;
import com.vrv.cems.service.updownload.service.PolicyService;
 

/** 
 *   <B>说       明</B>: 用于下载 策略 端的索引文件 任务
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月26日 下午2:45:12 
 */
public class PolicyIndexInfoTask implements Job {
	private static final Logger LOGGER = Logger.getLogger(PolicyIndexInfoTask.class);  
	
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		URL url2 = PolicyIndexInfoTask.class.getClassLoader().getResource( "ftp.properties"  );
		FTPClient ftpClient = FTPClientBuilder.build(  url2.getFile() );
		try { 
			 InputStream inputStream = ftpClient.download( SystemConfig.getPolicyFtpPath() );
			 if( inputStream == null ){
				 return ;
			 }
			 Properties crcProperties = new Properties();
			 crcProperties.load( inputStream ); 
			 String crc = crcProperties.getProperty("crc");
			 String url = crcProperties.getProperty("url");
			 if( LOGGER.isInfoEnabled() ){
				 LOGGER.info( "策略端任务数据  crc="+crc+"  url="+ url);
			 }  
			 PolicyService patchService = new PolicyService(crc, url);
			 patchService.pushIndexInfo();
		} catch( IOException e ){
			LOGGER.error( e );
		}catch (FTPClientException e) {
			LOGGER.error( e );
		} 
	} 
}
 