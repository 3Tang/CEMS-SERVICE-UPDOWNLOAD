package com.vrv.cems.service.updownload.business;  

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils; 
import org.apache.log4j.Logger;

import com.vrv.cems.service.updownload.service.PatchDownLoadService; 
import com.vrv.cems.service.updownload.util.ByteConvertUtils;
import com.vrv.cems.service.updownload.util.StreamStingUtils;

/** 
 *   <B>说       明</B>: 用于提供补丁文件内容及补丁文件信息下载 功能 
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月28日 上午10:26:10 
 */
public class PatchDownLoadProcess extends BusinessProcess {
	private static final Logger LOGGER = Logger.getLogger(PatchDownLoadProcess.class);
	public PatchDownLoadProcess(HttpServletRequest request,HttpServletResponse response) {
		super(request, response);
	}

	@Override
	public void process() {
		LOGGER.info( "开始调用PatchDownLoadProcess。。。。");
		String jData = (String) getRequest().getAttribute("data");
		LOGGER.info( "jData="+jData);
		JSONObject paramJson = JSONObject.fromObject( jData );
		String contentType = paramJson.getString("contentType");
		String filePath = paramJson.getString("filePath");
		String devOnlyId = paramJson.getString("devOnlyId");
		String size ;
		try {
			size = paramJson.getString("size");
		} catch (Exception e) {
			size="0";
		}
		if( StringUtils.isBlank( size )){
			size="0";
		}
		 
		if( LOGGER.isInfoEnabled() ){
			LOGGER.info( "jData:\t"+ jData );
			LOGGER.info( "size:\t"+ size  );
			LOGGER.info( "contentType:\t"+ contentType  );
			LOGGER.info( "filePath:\t"+ filePath  );
			LOGGER.info( "devOnlyId:\t"+ devOnlyId  );
		}
		
	    try {
	    	PatchDownLoadService patchDownLoadService = new PatchDownLoadService( filePath , devOnlyId);
	    	if( "0".equals( contentType )){
				String fileInfo = patchDownLoadService.getFileInfo();
				getResponse().getWriter().write( fileInfo == null ? "" : fileInfo );
			}else if( "1".equals( contentType )){
				ByteArrayOutputStream outputStream = patchDownLoadService.getFileStream( Long.parseLong( StringUtils.trim( size ) ));
				getResponse().setHeader("Content-Length", String.valueOf( outputStream.size() ));
				getResponse().getOutputStream().write( outputStream.toByteArray(), 0 ,  outputStream.size() );
			}
	    	getResponse().flushBuffer();
		} catch (Exception e) {
			String errorMessage =  "补丁文件下载下载失败contentType="+contentType+ "jData:\t"+ jData+"size:\t"+ size ;
			LOGGER.error( errorMessage  ,e );
		}	
		
	}

}
 