package com.vrv.cems.service.updownload.service;  
 
import java.io.File; 
import java.util.List; 

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils; 
import org.apache.log4j.Logger; 

import com.vrv.cems.service.updownload.config.LogConfig; 
import com.vrv.cems.service.updownload.config.SystemConfig;
import com.vrv.cems.service.updownload.config.TaskConfig;
import com.vrv.cems.service.updownload.config.listener.LogConfigListener;
import com.vrv.cems.service.updownload.config.listener.TaskConfigListener;
import com.vrv.cems.service.updownload.parse.PolicyParse;

/** 
 *   <B>说       明</B>:用于修改配置，并生效。
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年4月10日 下午1:48:52 
 */
public class WebConfigService {
	private static final Logger LOGGER = Logger.getLogger(WebConfigService.class);
	private LogConfig logConfig ;
	private List<TaskConfig> taskConfigs ;
	private String policyXMLContent ;
	public static String getPolicyFilePath(){ 
		return WebConfigService.class.getClassLoader().getResource( "policy.xml" ).getPath();
	}
	public WebConfigService( String policyXMLContent ){
		this.policyXMLContent = policyXMLContent;
	}
	/**
	 * 根据配置文件，修改配置实体，并生效。
	 */
	public void changeConfig(){ 
		try {
			PolicyParse policyParse = new PolicyParse(policyXMLContent);
			logConfig = policyParse.getLogConfig();
			taskConfigs = policyParse.getTaskConfigs() ;
			//触发监听
			LogConfigListener logConfigListener = new LogConfigListener(logConfig) ;
			logConfigListener.changeLog4j();
			//触发任务
			TaskConfigListener taskConfigListener = new TaskConfigListener();
			taskConfigListener.reStart( taskConfigs ); 
			
			//存储文件
			File xmlFileq = new File( WebConfigService.getPolicyFilePath() );
			FileUtils.write( xmlFileq , policyXMLContent, "UTF-8" ,false );
			
			//存储相关值
			SystemConfig.setPolicyFtpPath( policyParse.getPolicyFtpPath() );
			PropertiesConfiguration pc = new PropertiesConfiguration( SystemConfig.getConfigPath() );
			SystemConfig.setRootPath( pc.getString("file.rootPath"));
		} catch (Exception e) {
			LOGGER.error( e );
		} 
	}
}
 