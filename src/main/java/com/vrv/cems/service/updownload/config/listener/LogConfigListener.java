package com.vrv.cems.service.updownload.config.listener; 

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.vrv.cems.service.updownload.config.LogConfig; 

/** 
 *   <B>说       明</B>: 用于 对 日志 的监听 做 修改
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年4月17日 下午2:59:30 
 */
public class LogConfigListener {
	private static final Logger LOGGER = Logger.getLogger(LogConfigListener.class);
	private LogConfig logConfig ; 
	

	public LogConfigListener( LogConfig logConfig ) { 
		this.logConfig = logConfig;
	}
	/**
	 * 实现 根据 策略文件的logPath配置信息，改变日志的存放位置
	 */
	public void changeLog4j(){
		if( this.logConfig == null ){
			return ;
		}
		try {
			String  log4jPath = LogConfigListener.class.getClassLoader().getResource("log4j.properties").getPath();
			File log4j = new File(log4jPath);
			PropertyConfigurator.configureAndWatch( log4j.getAbsolutePath() , 60*1000);
			PropertiesConfiguration pc = new PropertiesConfiguration( log4j );
			pc.setProperty("log4j.rootLogger", logConfig.getLogLevel() +",CONSOLE,FILE");
			pc.setProperty("log4j.appender.FILE.File", logConfig.getLogPath() );
			pc.save(); 
		} catch (ConfigurationException e) { 
			LOGGER.error("修改日志失败" , e);
			throw new RuntimeException( e );
		} 
	} 
}
 