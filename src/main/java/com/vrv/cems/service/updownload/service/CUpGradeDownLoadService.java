package com.vrv.cems.service.updownload.service; 

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.sf.json.JSONArray;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sys.common.util.security.CRCUtils;
import com.vrv.cems.service.base.bean.ResultMsgBean;
import com.vrv.cems.service.base.interfaces.IUpdownloadService;
import com.vrv.cems.service.updownload.config.SystemConfig;

/** 
 *   <B>说       明</B>: 提供 升级包  功能 
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月28日 上午10:36:33 
 */
public class CUpGradeDownLoadService {
	private static final Logger LOGGER = Logger.getLogger(CUpGradeDownLoadService.class); 
	private RangeService rangeService = new RangeService();
	private String filePath ;
	private String devOnlyId;
	
	public CUpGradeDownLoadService(String filePath , String devOnlyId) { 
		this.filePath = filePath;
		this.devOnlyId = devOnlyId;
	}
	/**
	 *  获取 文件 流 
	 *  当文件路径所指明的文件不存在或devonlyid不存在时，对输出流不做处理（后期可能返回异常，由前台处理 返回某个标识符给调用者）
	 * @param offsetSize 文件流开始的位置 
	 */
	public ByteArrayOutputStream getFileStream( long offsetSize ){
//		if(! devOnlyIdExist() ){ 
//			throw new IllegalArgumentException("devonlyid 不存在");
//		}
		if( StringUtils.isBlank( filePath )){
			throw new IllegalArgumentException("filePath不能为空");
		}
		LOGGER.info("###path:"+SystemConfig.getCupgradePathPre()+ filePath);
		File patchFile = new File( SystemConfig.getCupgradePathPre()+ filePath );
		if( !patchFile.exists() ){
			throw new IllegalArgumentException("磁盘上不存在此文件"); 
		}
		long totalSize = FileUtils.sizeOf( patchFile );
		if(totalSize < offsetSize){
			throw new IllegalArgumentException("totalSize 需要大于 offsetSize");
		}
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream( 1024* 8 );
			FileInputStream patchFileInputStream = new FileInputStream( patchFile );
			IOUtils.copyLarge( patchFileInputStream ,  byteArrayOutputStream , offsetSize , totalSize - offsetSize ); 
			IOUtils.closeQuietly( patchFileInputStream );
			//存储下载记录
			DownLoadInfoService downLoadInfoService = DownLoadInfoService.getInstance();
			downLoadInfoService.save(devOnlyId, filePath );
			LOGGER.info("下载文件成功!:"+SystemConfig.getCupgradePathPre()+ filePath);
			return byteArrayOutputStream;
		} catch (IOException e) {
			String errorMessager = "获取 文件 流有误filePath="+filePath+"\tdevOnlyId="+devOnlyId+"\toffsetSize="+offsetSize;
			LOGGER.error( errorMessager , e );
			throw new RuntimeException( e );
		}
	} 
	/**
	 * 获取文件信息
	 * @return 
	 * 1、 null 表示 文件路径所指明的文件不存在 或devonlyid不存在 （后期可能返回异常，由前台处理 返回某个标识符给调用者）
	 * 2、{ crc:"????",size:"????" } 表示 文件信息格式 
	 * 3，
	 */
	public String getFileInfo(){
		ResultMsgBean resultMsgBean = new ResultMsgBean(IUpdownloadService.SERVICE_CODE, IUpdownloadService.DOWN_CLIENT_FILE);
		if(! devOnlyIdExist() ){
			resultMsgBean.setResult("1");
			resultMsgBean.setDesc("devOnlyId=["+devOnlyId+"],不存在");
			LOGGER.error("devOnlyId=["+devOnlyId+"],不存在");
			return resultMsgBean.toJson();
//			throw new IllegalArgumentException("devonlyid 不存在");
		}
		if( StringUtils.isBlank( filePath )){
			resultMsgBean.setResult("1");
			resultMsgBean.setDesc("filePath不能为空");
			LOGGER.error("filePath不能为空");
			return resultMsgBean.toJson();
//			throw new IllegalArgumentException("filePath不能为空");
		}
		File patchFile = new File( SystemConfig.getCupgradePathPre()+ filePath );
		if( !patchFile.exists() ){
			resultMsgBean.setResult("1");
			resultMsgBean.setDesc("文件:["+filePath+",在上传下载服务器上不存在]");
			LOGGER.error("文件:["+filePath+",在上传下载服务器上不存在]");
			return resultMsgBean.toJson();
//			throw new IllegalArgumentException("磁盘上不存在此文件"); 
		}
		try {
			FileInputStream patchFileInputStream = new FileInputStream( patchFile );
			String crc = CRCUtils.getCRC32StringValue( patchFileInputStream );
			IOUtils.closeQuietly( patchFileInputStream );
			long size = FileUtils.sizeOf( patchFile );
			//查找其他下载地址
			DownLoadInfoService downLoadInfoService = DownLoadInfoService.getInstance();
			String ips = downLoadInfoService.getIps( devOnlyId, filePath) ;
			resultMsgBean.setJdata(JSONArray.fromObject("[{\"crc\":\""+crc+"\",\"size\":\""+size+"\",\"ipList\":"+ips+"}]"));
			return resultMsgBean.toJson();
		} catch (IOException e) { 
			String errorMessager = "获取 文件 信息有误filePath="+filePath+"\tdevOnlyId="+devOnlyId;
			LOGGER.error( errorMessager , e );
			throw new RuntimeException( e );
		} 
	}
	private boolean devOnlyIdExist(){
		return rangeService.existByOrg( devOnlyId );
	}
}
 