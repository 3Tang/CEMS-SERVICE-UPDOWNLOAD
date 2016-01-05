package com.vrv.cems.service.updownload.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.sys.common.util.CommentedProperties;
import com.sys.common.util.IOUtils;

/** 
 *   <B>说       明</B>:读取config.properties工具类
 *
 * @author  作  者  名：chenjinlong<br/>
 *		   E-mail ：chenjinlong@vrvmail.com.cn
 
 * @version 版   本  号：V1.0<br/>
 *          创建时间：2014年8月16日 下午12:40:41 
 */
public class ConfigPropertiesUtils {
	private static Logger log = Logger.getLogger(ConfigPropertiesUtils.class);
	
	private CommentedProperties prop = new CommentedProperties();
	
	public ConfigPropertiesUtils(){
		try { 
			String configPath = prop.getPropertyFromCemsDat("path.config.properties");
			File file = new File(configPath);
			prop.load(file);
		} catch (IOException e) {
			log.error("加载config.properties文件失败",e);
		}
	}
	public String setProperty(String key, String value)  {
	    return prop.setProperty(key, value, "");
	}
	public void store(){
		FileOutputStream fos = null;
	    try {
			String path =  prop.getPropertyFromCemsDat("path.config.properties");
			fos = new FileOutputStream( new File(path) );
			prop.store( fos ,false);
		} catch (IOException e) {
			log.error("存储config.properties文件失败", e);
			throw new RuntimeException();
		}finally{
			IOUtils.close( fos );
		}
	}
	public  String getValue(String key){
		try{
			return prop.getProperty(key);
		}catch(Exception e){
			log.error("获取["+key+"]失败");
			return null;
		}
	}
}
