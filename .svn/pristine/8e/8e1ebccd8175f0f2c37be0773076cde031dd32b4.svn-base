package com.vrv.cems.service.updownload.util; 

import org.apache.log4j.Logger;

import com.vrv.cems.service.updownload.cache.CUpGradeIndexCache;

/** 
 *   <B>说       明</B>: 解析 路径 工具类
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月29日 下午2:53:22 
 */
public class PathParseUtil {
	private static final Logger LOGGER = Logger.getLogger(CUpGradeIndexCache.class);
	
	/**
	 * 根据 路径获取 产品 名称
	 * @param path 路径
	 * @return
	 */
	 public static String getName( String path ){
		 	String[] fileNames = path.split("/") ;
		 	String fileName = fileNames[ fileNames.length -1 ] ; 
		 	if( LOGGER.isDebugEnabled() ){
				LOGGER.debug( "path="+path +"\t fileName="+fileName);
			}
			return  fileName ;
	 }
	/**
	 * 根据 路径获取 产品 类型
	 * @param path 路径
	 * @return
	 */
	 public static String getProductType( String path ){
		 	String[] fileNames = path.split("/") ;
		 	String productType = fileNames[1] ;
		 	if( LOGGER.isDebugEnabled() ){
				LOGGER.debug( "path="+path +"\t productType="+productType);
			}
			return productType ;
	 }
	 /**
	  * 根据 路径 判断  是否定制级包 
	  * @param path 路径
	  * @return true 是 定制包, false 不是定制包
	  */
	 public static boolean isCustom( String path ){
		 	String[] fileName = path.split("/") ;
			String fileType = fileName[0];
			boolean isCustom = false ;
			if( fileType == "custom" ){
				isCustom = true ;
			}
			if( LOGGER.isDebugEnabled() ){
				LOGGER.debug( "path="+path +"\tisCustom="+isCustom);
			}
			return isCustom ;
	 }
}
 