package com.vrv.cems.service.updownload.cache; 

import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import net.sf.ehcache.CacheManager;

/** 
 *   <B>说       明</B>: 缓存 管理 ，用于管理 补丁 、升级、CRC缓存 
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月28日 下午4:18:52 
 */
public class IndexCacheManager {
	
	
	
	static{
		InputStream inputStream = IndexCacheManager.class.getClassLoader().getResourceAsStream("ehcache.xml");
		cupgradeCacheManager = new CacheManager( inputStream );  
		IOUtils.closeQuietly( inputStream );
	}
	private static CacheManager cupgradeCacheManager ;
	/**
	 * 获取 升级索引 和 索引文件CRC 缓存管理 
	 * @return
	 */
	public static CacheManager getCUpgradeCacheManager(){ 
	
		return cupgradeCacheManager;
	}  
	
	public static void main(String[] args) {
		getCUpgradeCacheManager();
	}
	
}
 