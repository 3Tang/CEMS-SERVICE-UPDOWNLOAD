package com.vrv.cems.service.updownload.web.listener; 

import java.io.File; 
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.FileUtils;

import com.vrv.cems.service.base.SystemConstants;
import com.vrv.cems.service.configure.quartzJob.QuartzConfigure;
import com.vrv.cems.service.register.impl.RegisterServiceImpl;
import com.vrv.cems.service.updownload.service.WebConfigService;

/** 
 *   <B>说       明</B>:服务启动注册
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年2月2日 下午1:34:19 
 */
public class SystemInitListener implements ServletContextListener {

	
	public void contextInitialized(ServletContextEvent sce) { 
		//修改策略配置
		try {
			//更新polic.xml文件调用招聘接口在这里做.
			
			//加载polic.xml文件内容
			File policyFile = new File( WebConfigService.getPolicyFilePath() );
			//获取到策论内容
			String policyContent = FileUtils.readFileToString( policyFile , "UTF-8" );
			WebConfigService webConfigService = new WebConfigService(policyContent);
			//执行策论的内容
			webConfigService.changeConfig();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*//服务注册*/
		RegisterServiceImpl.registerService( SystemConstants.FLAG_CONTAINER_TOMCAT );
		QuartzConfigure quartzConfigure = new QuartzConfigure();
		quartzConfigure.init(SystemConstants.FLAG_CONTAINER_TOMCAT); 
	}

	
	public void contextDestroyed(ServletContextEvent sce) {  }

}
 