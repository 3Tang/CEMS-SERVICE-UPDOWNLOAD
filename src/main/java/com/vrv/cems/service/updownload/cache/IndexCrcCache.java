package com.vrv.cems.service.updownload.cache; 

import org.apache.log4j.Logger;

import com.vrv.cems.service.updownload.bean.CrcMark;

import net.sf.ehcache.Cache; 
import net.sf.ehcache.Element;

/** 
 *   <B>说       明</B>:索引文件CRC 缓存
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月27日 下午4:20:14 
 */
public class IndexCrcCache {
	private static final Logger LOGGER = Logger.getLogger(IndexCrcCache.class); 
	private static final String CACHENAME = "indexCrc";
	private Cache cache = IndexCacheManager.getCUpgradeCacheManager().getCache( CACHENAME ) ; 
	
	public void put( CrcMark indexCrcType , String crc  ){ 
		cache.put( new Element(  indexCrcType.getKey() ,crc ) );
		cache.flush();
	}
	public String get( CrcMark indexCrcType ){
		Element element = cache.get( indexCrcType.getKey() );
		if( element == null ){
			if( LOGGER.isDebugEnabled() ){
				LOGGER.debug( "未命中 索引文件CRC 缓存 key="+indexCrcType.getKey());
			}
			return null ;
		}
		if( LOGGER.isDebugEnabled() ){
			LOGGER.debug( "命中 索引文件CRC 缓存 key="+indexCrcType.getKey() );
		}
		return (String)element.getObjectValue();
	}
	
}
 