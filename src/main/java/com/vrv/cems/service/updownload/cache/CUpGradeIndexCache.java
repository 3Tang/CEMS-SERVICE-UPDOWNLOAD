package com.vrv.cems.service.updownload.cache; 

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vrv.cems.service.updownload.bean.CUpGradeIndex; 
import com.vrv.cems.service.updownload.util.PathParseUtil;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/** 
 *   <B>说       明</B>: 管理 升级文件索引 信息
 *   	命中 规则 
 *   		首先 根据 path 计算 出 余数 用来 找出 具体的桶 （0-5）
 *   		其次根据 path 计算 出 产品类型 找出 产品类型区域 （single,double）  
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月27日 上午11:18:16 
 */
public class CUpGradeIndexCache {
	private static final Logger LOGGER = Logger.getLogger(CUpGradeIndexCache.class); 
	private CacheManager cacheManager = IndexCacheManager.getCUpgradeCacheManager() ;
	private static final String CACHE_NAMEPRE_SINGLE = "cupgradeindex_single_";
	private static final String CACHE_NAMEPRE_DOUBLE = "cupgradeindex_double_";
	private Cache getCache( String path ){
		if(path.endsWith(".zip") || path.endsWith(".ZIP")){
			path = path.substring(0 , path.lastIndexOf(".") );
		}
		int hashCode = Math.abs( path.hashCode() );
		if( isSingleByProduct( getProductByPath( path ) ) ){
			return cacheManager.getCache( CACHE_NAMEPRE_SINGLE +( hashCode % 5 ) );
		}else{
			return cacheManager.getCache( CACHE_NAMEPRE_DOUBLE +( hashCode % 5 ) );
		}
	}
	public void put( CUpGradeIndex downIndex ){
		Cache cache = getCache( downIndex.getPath() );
		cache.put( new Element(  downIndex.getPath() , downIndex ) );
		cache.flush();
	}
	public CUpGradeIndex get( CUpGradeIndex downIndex ){
		return get( downIndex.getPath() );
	}
	/**
	 * 
	 * @param key  路径 
	 * @return
	 */
	public CUpGradeIndex get( String key ){
		Cache cache = getCache( key );
		Element element = cache.get( key );
		if( element == null ){
			if( LOGGER.isDebugEnabled() ){
				LOGGER.debug( "未命中 升级索引 缓存 key="+key);
			}
			return null;
		}
		if( LOGGER.isDebugEnabled() ){
			LOGGER.debug( " 命中 升级索引缓存 key="+key);
		}
		return (CUpGradeIndex)element.getObjectValue();
	}  
	private String getProductByPath(String path ){
		return PathParseUtil.getProductType( path );
	}
	private boolean isSingleByProduct( String productName ){
		int yushu = productName.hashCode() % 2 ;
		if( yushu == 1 ){
			return false ;
		}
		return true ;
	}
	/**
	 * 
	 * @param productType 产品 类型
	 * @return
	 */
	public List<CUpGradeIndex> queryByProductType( String productType ){
		List<CUpGradeIndex> cupGradeIndexs = new LinkedList<CUpGradeIndex>();
		boolean singleArea = isSingleByProduct( productType );
		String cacheNamePre = "";
		if( singleArea ){
			cacheNamePre  = CACHE_NAMEPRE_SINGLE;
		}else{
			cacheNamePre = CACHE_NAMEPRE_DOUBLE ;
		}
		for (int i = 0; i < 5; i++) {
			Cache cache = cacheManager.getCache(  cacheNamePre + i  ); 
			@SuppressWarnings("rawtypes")
			List keyList =  cache.getKeys();
			for (int j = 0; j < keyList.size(); j++) {
				Element element = cache.get( keyList.get( i ) );
				CUpGradeIndex cUpGradeIndex = (CUpGradeIndex) element.getObjectValue();
				cupGradeIndexs.add( cUpGradeIndex );
			}
		}
		return cupGradeIndexs;
	}
	public List<CUpGradeIndex> queryAll(){
		List<CUpGradeIndex> cupGradeIndexs = new LinkedList<CUpGradeIndex>();
		for (int i = 0; i < 5; i++) {
			Cache cache = cacheManager.getCache(  CACHE_NAMEPRE_SINGLE + i  );
			@SuppressWarnings("rawtypes")
			List keyList =  cache.getKeys();
			for (int j = 0; j < keyList.size(); j++) {
				Element element = cache.get( keyList.get( i ) );
				cupGradeIndexs.add( (CUpGradeIndex) element.getObjectKey() );
			}
		}
		for (int i = 0; i < 5; i++) {
			Cache cache = cacheManager.getCache(  CACHE_NAMEPRE_DOUBLE + i  );
			@SuppressWarnings("rawtypes")
			List keyList =  cache.getKeys();
			for (int j = 0; j < keyList.size(); j++) {
				Element element = cache.get( keyList.get( i ) );
				cupGradeIndexs.add( (CUpGradeIndex) element.getObjectKey() );
			}
		}
		return cupGradeIndexs ;
	}
}
 