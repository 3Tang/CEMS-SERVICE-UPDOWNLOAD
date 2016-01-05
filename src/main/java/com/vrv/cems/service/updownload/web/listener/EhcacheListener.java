package com.vrv.cems.service.updownload.web.listener; 
 
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener; 

import org.apache.log4j.Logger;

import com.vrv.cems.service.updownload.cache.IndexCacheManager;
 
/** 
 *   <B>说       明</B>: 监听 服务的启动与停止，并实现ehcache的自动管理
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月27日 上午10:53:06 
 */
public class EhcacheListener implements ServletContextListener {
	private static final  Logger LOGGER = Logger.getLogger(EhcacheListener.class);
	
	public void contextInitialized(ServletContextEvent sce) { 
		
	}

	
	public void contextDestroyed(ServletContextEvent sce) {  
		try {
			IndexCacheManager.getCUpgradeCacheManager().shutdown();
			if( LOGGER.isDebugEnabled() ){
				LOGGER.debug( "关闭缓存 end..." );
			}
		} catch (Exception e) {
			LOGGER.debug( "关闭缓存 出错 " );
		}
//		try {
//			IndexCacheManager.getPatchCacheManager().shutdown();
//			if( LOGGER.isDebugEnabled() ){
//				LOGGER.debug( "关闭补丁缓存 end..." );
//			}
//		} catch (Exception e) {
//			LOGGER.debug( "关闭补丁缓存  出错 " );
//		}
	}
}
 