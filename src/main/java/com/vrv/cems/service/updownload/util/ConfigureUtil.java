package com.vrv.cems.service.updownload.util; 

import java.io.File;
import java.util.Properties;



/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：zhaodj<br/>
 *		   E-mail ：zhaodongjie@vrvmail.com.cn
 
 * @version 版   本  号：V1.0<br/>
 *          创建时间：2015年7月24日 下午2:19:54 
 */
public class ConfigureUtil {

	private static Properties  prop =  PropertyUtils.getProperties(ConfigureUtil.class.getClassLoader().getResource("config.properties").getPath());
	public static String getServiceId() {
		return prop.getProperty("server.ip");
			
	}

}
 