package com.vrv.cems.service.updownload.statics; 

import com.vrv.cems.service.base.SystemConstants;
import com.vrv.cems.service.updownload.util.DirUtil;

/** 
 *   <B>说       明</B>:系统静态常量
 *
 * @author  作  者  名：chenjinlong<br/>
 *		    E-mail ：chenjinlong@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年3月27日 上午9:26:57 
 */
public class SystemStatics extends SystemConstants{
	
	/*客户端请求路径相关**********************************************************************************************/
	/**
	 * 客户端请求TCP转发路径中的maxCode
	 */
	public static String URL_MAXCODE = "maxCode";
	/**
	 * 客户端请求TCP转发路径中的minCode
	 */
	public static String URL_MINCODE = "minCode";
	/**
	 * 客户端请求TCP转发路径中的checkCode
	 */
	public static String URL_CHECKCODE = "checkCode";
	/**
	 * 客户端请求TCP转发路径中的isZip
	 */
	public static String URL_ISZIP = "isZip";
	/**
	 * 客户端请求TCP转发路径中的sessionId
	 */
	public static String URL_SESSIONID = "sessionId";
	/**
	 * 客户端请求TCP转发路径中的msgCode
	 */
	public static String URL_MSGCODE = "msgCode";
	/**
	 * 客户端请求TCP转发路径中的areaId
	 */
	public static String URL_AREAID = "areaId";
	/*CEMS请求路径相关**********************************************************************************************/
	/**
	 * CEMS请求路径中的isEncrypt
	 */
	public static String URL_ISENCRYPT = "isEncrypt";
	/**
	 * CEMS请求路径中的key
	 */
	public static String URL_KEY = "key";
	/**
	 * CEMS请求路径中的flag
	 */
	public static String URL_FLAG = "flag";
	/**
	 *  服务本地的文件未上传FastFDS
	 */
	public static String FILE_NOT_UPFASTFDS="0";
	/**
	 *  服务本地的文件已上传FastFDS
	 */
	public static String FILE_IS_UPFASTFDS="1";
	/**
	 *  服务本地索引文件存放位置
	 */
	public static String INDEX_XML_PATH=DirUtil.getAppDir()+ "/upDownService/index.xml";
	
}
 