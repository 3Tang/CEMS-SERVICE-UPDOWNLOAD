package com.vrv.cems.service.updownload.service; 

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException; 

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sys.common.util.security.CRCUtils;
import com.vrv.cems.service.updownload.config.SystemConfig;

/** 
 *   <B>说       明</B>: 提供 下载 补丁功能 
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月28日 上午10:36:33 
 */
public class PolicyDownLoadService {
	private static final Logger LOGGER = Logger.getLogger(PolicyDownLoadService.class); 
	
	private String fileName ;
	private String devOnlyId ;
	
	public PolicyDownLoadService(String filePath ,String devOnlyId) { 
		this.fileName = filePath;
		this.devOnlyId = devOnlyId ;
	}
	/**
	 *  获取 文件 流 
	 *  当文件路径所指明的文件不存在时，对输出流不做处理（后期可能返回异常，由前台处理 返回某个标识符给调用者）
	 * @param offsetSize 文件流开始的位置 
	 */
	public ByteArrayOutputStream getFileStream( long offsetSize ){
		if( StringUtils.isBlank( fileName )){
			throw new IllegalArgumentException("fileName不能为空");
		}
		File policyFile = new File( SystemConfig.getPolicyPathPre()+ fileName );
		if( !policyFile.exists() ){
			throw new IllegalArgumentException("磁盘上不存在此文件");
		}
		long totalSize = FileUtils.sizeOf( policyFile );
		if(totalSize < offsetSize){
			throw new IllegalArgumentException("totalSize 需要大于 offsetSize");
		}
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024*8);
			FileInputStream policyFileInputStream = new FileInputStream( policyFile );
			IOUtils.copyLarge( policyFileInputStream ,  byteArrayOutputStream , offsetSize , totalSize - offsetSize );
			IOUtils.closeQuietly( policyFileInputStream );
			//存储下载记录
			DownLoadInfoService downLoadInfoService = DownLoadInfoService.getInstance();
			downLoadInfoService.save(devOnlyId, fileName );
			return byteArrayOutputStream;
		} catch (IOException e) {
			String errorMessager = "获取 文件 流有误filePath="+fileName+"\toffsetSize="+offsetSize;
			LOGGER.error( errorMessager , e );
			throw new RuntimeException( e );
		}
	}
	/**
	 * 获取文件信息
	 * @return 
	 * 1、 null 表示 文件路径所指明的文件不存在 （后期可能返回异常，由前台处理 返回某个标识符给调用者）
	 * 2、{ crc:"????",size:"????" } 表示 文件信息格式 
	 */
	public String getFileInfo(){
		if( StringUtils.isBlank( fileName )){
			throw new IllegalArgumentException("fileName不能为空"); 
		}
		File policyFile = new File( SystemConfig.getPolicyPathPre()+ fileName );
		if( !policyFile.exists() ){
			throw new IllegalArgumentException("磁盘上不存在此文件");
		}
		try {
			FileInputStream policyFileInputStream = new FileInputStream( policyFile );
			String crc = CRCUtils.getCRC32StringValue( policyFileInputStream );
			IOUtils.closeQuietly( policyFileInputStream );
			long size = FileUtils.sizeOf( policyFile );
			//查找其他下载地址
			DownLoadInfoService downLoadInfoService = DownLoadInfoService.getInstance();
			String ips = downLoadInfoService.getIps( devOnlyId, fileName ) ;
			return "{\"crc\":\""+crc+"\",\"size\":\""+size+"\",\"ip\":\""+ips+"\"}"; 
		} catch (IOException e) { 
			String errorMessager = "获取 文件 信息有误filePath="+fileName;
			LOGGER.error( errorMessager , e );
			throw new RuntimeException( e );
		}
	}
}
 