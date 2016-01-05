package com.vrv.cems.service.updownload.business;  

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils; 
import org.apache.log4j.Logger;

import com.vrv.cems.service.updownload.service.PolicyDownLoadService;

/** 
 *   <B>说       明</B>: 用于提供策略文件内容及策略文件信息下载 功能 
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月28日 上午10:26:10 
 */
public class PolicyDownLoadProcess extends BusinessProcess {
	private static final  Logger LOGGER = Logger.getLogger(PolicyDownLoadProcess.class);
	public PolicyDownLoadProcess(HttpServletRequest request,HttpServletResponse response) {
		super(request, response);
	}

	@Override
	public void process() {
		String jData = (String) getRequest().getAttribute("data");
		JSONObject paramJson = JSONObject.fromObject( jData );
		String contentType = paramJson.getString("contentType");
		String filePath = paramJson.getString("fileName");
		String devOnlyId = paramJson.getString("devOnlyId"); 
		String size = paramJson.getString("size");
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
	    	PolicyDownLoadService policyDownLoadService = new PolicyDownLoadService( filePath , devOnlyId);
	    	if( "0".equals( contentType )){
				String fileInfo = policyDownLoadService.getFileInfo();
				getResponse().getWriter().write( fileInfo == null ? "" : fileInfo );
			}else if( "1".equals( contentType )){
				ByteArrayOutputStream outputStream = policyDownLoadService.getFileStream( Long.parseLong( StringUtils.trim( size ) ));
				getResponse().setHeader("Content-Length", String.valueOf( outputStream.size() ));
				getResponse().getOutputStream().write( outputStream.toByteArray(), 0 ,  outputStream.size() );
			}
	    	getResponse().flushBuffer();
		} catch (Exception e) {
			String errorMessage =  "策略文件下载下载失败contentType="+contentType+ "jData:\t"+ jData+"size:\t"+ size ;
			LOGGER.error( errorMessage  ,e );
		}	 
	}

}
 