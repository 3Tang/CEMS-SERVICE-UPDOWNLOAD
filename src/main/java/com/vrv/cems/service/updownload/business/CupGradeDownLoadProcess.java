package com.vrv.cems.service.updownload.business; 

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils; 
import org.apache.log4j.Logger;

import com.vrv.cems.service.updownload.service.CUpGradeDownLoadService; 
/** 
 *   <B>说       明</B>:下载客户端升级文件
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月28日 上午11:54:26 
 */
public class CupGradeDownLoadProcess extends BusinessProcess {
	private static final Logger LOGGER = Logger.getLogger(CupGradeDownLoadProcess.class);
	public CupGradeDownLoadProcess(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response); 
	}

	@Override
	public void process() { 
		String jData = (String) getRequest().getAttribute("data"); 
		JSONObject paramJson = JSONObject.fromObject( jData );
		String contentType = paramJson.getString("contentType");
		String devOnlyId = paramJson.getString("devOnlyId");
		String size = paramJson.getString("size");
		String packType="cUpgrade";
		String filePath = paramJson.getString("filePath");
		if( StringUtils.isBlank( size )){
			size = "0";
		} 
		if( LOGGER.isInfoEnabled() ){
			LOGGER.info( "jData:\t"+ jData );
			LOGGER.info( "size:\t"+ size  );
			LOGGER.info( "filePath:\t"+ filePath  );
			LOGGER.info( "contentType:\t"+ contentType  );
			LOGGER.info( "devOnlyId:\t"+ devOnlyId  ); 
		}
	    try {
	    	CUpGradeDownLoadService cupGradeDownLoadService = new CUpGradeDownLoadService( filePath ,devOnlyId);
	    	if( "0".equals( contentType )){
				String fileInfo = cupGradeDownLoadService.getFileInfo();
				getResponse().getWriter().write( fileInfo == null ? "" : fileInfo);
			}else if( "1".equals( contentType )){
				ByteArrayOutputStream outputStream = cupGradeDownLoadService.getFileStream( Long.parseLong( StringUtils.trim( size ) ) );
				getResponse().setHeader("Content-Length",  String.valueOf( outputStream.size() ));
				LOGGER.info("当前文件的文件流大小:"+String.valueOf( outputStream.size() ));
				getResponse().getOutputStream().write( outputStream.toByteArray() , 0 , outputStream.size() );
			}
	    	getResponse().flushBuffer();
		} catch (Exception e) {
			String errorMessage =  "客户端文件下载下载失败contentType="+contentType+ ",jData:"+ jData+",size:"+ size ;
			LOGGER.error( errorMessage  ,e ); 
		}	
	}

}
 