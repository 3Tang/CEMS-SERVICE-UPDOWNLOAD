package com.vrv.cems.service.updownload.cache; 

import org.apache.log4j.Logger;

import com.vrv.cems.service.updownload.bean.PatchIndex;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/** 
 *   <B>说       明</B>: 管理 补丁文件索引 信息
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月27日 上午11:18:16 
 */
public class PatchIndexCache { 
	private static final Logger LOGGER = Logger.getLogger(PatchIndexCache.class); 
	private static final String CACHEPRE = "patchindex_";
	private CacheManager cacheManager = IndexCacheManager.getCUpgradeCacheManager(); 
	private Cache getCache( String path ){ 
		int hashCode = Math.abs( path.hashCode() );
		return cacheManager.getCache( CACHEPRE +( hashCode % 10 ) );
	}
	public void put( PatchIndex updatePatch ){
		Cache cache = getCache( updatePatch.getPath() );
		cache.put( new Element(  updatePatch.getPath() , updatePatch ) ); 
	}
	public void flush(){
		for (int i = 0; i < 10 ; i++) {
			cacheManager.getCache(CACHEPRE+ i).flush();
		}
	}
	public PatchIndex get( PatchIndex updatePatch ){
		return get( updatePatch.getPath() );
	}
	private PatchIndex get( String key ){
		Cache cache = getCache( key );
		Element element = cache.get( key );
		if( element == null ){
			if( LOGGER.isDebugEnabled() ){
				LOGGER.debug( "未命中 补丁索引 缓存 key="+key);
			}
			return null;
		}
		if( LOGGER.isDebugEnabled() ){
			LOGGER.debug( "命中 补丁索引 缓存 key="+key);
		}
		return (PatchIndex)element.getObjectValue();
	} 
}
 