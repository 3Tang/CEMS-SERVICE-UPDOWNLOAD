package com.vrv.cems.service.updownload.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.sys.common.util.CommentedProperties;
import com.vrv.cems.service.updownload.statics.SystemStatics;


/**
 * <B>说 明</B>:用于读取系统配置文件config.properties的工具单元类
 * 
 * @author 作 者 名  ：chenjun<br/>
 *         E-mail ：chenjun@vrvmail.com.cn
 *         
 * @version 版 本 号  ：V1.0.20140725<br/>
 *          创建时间：2014-7-28 下午01:04:01
 */
public final class ReadConfigFileUtil {
	private static Logger log = Logger.getLogger(ReadConfigFileUtil.class);
	private static CommentedProperties props = null;
	private static Properties prop = new Properties();
	static InputStream ins = null;

   public static Properties getProperties(String filePath)
   {
	   FileInputStream fileInputStream = null;
		Properties prop = null;
		try {
			 fileInputStream = new FileInputStream(filePath);
			 prop = new Properties();
			 prop.load(fileInputStream);
		} catch (FileNotFoundException e) {
			log.error("在线服务中未找到"+filePath+".properties文件！", e);
		} catch (IOException e) {
			log.error("在线服务加载"+filePath+".properties文件失败！", e);
		}finally{
			if(fileInputStream!=null){
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
   }
	/**
	 * 根据给定的key键，读取器对应的值value
	 * @param key
	 * @return value
	 */
	public static String get(String filePath, String key) {
		InputStream ins = null;
		try {
			File file = new File(filePath);
			ins = new FileInputStream(file);
			prop.load(ins);
		} catch (Exception e) {
			throw new RuntimeException(e + "【加载配置文件失败】");
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return prop.getProperty(key);
	}

	/**
	 * 根据指定的
	 * @param key
	 * @param value
	 */
	public static void set(String filePath,String key, String value) {
		try {
			prop.setProperty(key, value);
			CommentedProperties cp = new CommentedProperties();
			FileInputStream is = new FileInputStream(filePath);
			cp.load(is,"UTF-8");
			//默认日志级别
			String localValue = cp.getProperty(key);
			if(!localValue.equals(value)&value!=null){
				Writer pw = new PrintWriter(new File(filePath));
				//设置新的日志级别
				cp.setProperty(key, value);
				cp.store(pw,true);
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			log.error("设置属性["+key+"],值["+value+"]失败",e);
			throw new RuntimeException(e + "为属性【" + key + "】，写入【" + value + "】失败");
		}
	}



	public static void writePropsFile(String filePath) throws IOException {
		OutputStream outputStream = new FileOutputStream(filePath);
		props.store(outputStream);
		outputStream.close();
	}
	
}
