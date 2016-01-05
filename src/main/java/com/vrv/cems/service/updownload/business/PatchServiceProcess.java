package com.vrv.cems.service.updownload.business; 
 
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.vrv.cems.service.base.bean.ResultMsgBean;
import com.vrv.cems.service.base.interfaces.IUpdownloadService;
import com.vrv.cems.service.updownload.service.PatchService;
/** 
 *   <B>说       明</B>:补丁服务推送xml
 *
 * @author  作  者  名：    zhangwenli
 *		    E-mail ：zhangwenli@vrvmail.com.cn
 
 * @version 版   本  号：V1.0 <br/>
 *          创建时间：2015年1月15日 下午2:18:03 
 */ 
public class PatchServiceProcess extends BusinessProcess {
	private static final Logger LOGGER = Logger.getLogger(PatchServiceProcess.class); 
	public PatchServiceProcess(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}
	@Override
	public synchronized void process() {
		//返回值
		ResultMsgBean result = new ResultMsgBean(IUpdownloadService.SERVICE_CODE, IUpdownloadService.PATCH_XML);
		String jdata = "";
		 try {
			 jdata = (String) getRequest().getAttribute("data");
			 if( LOGGER.isInfoEnabled() ){
				 LOGGER.info( "jdata="+jdata);
			 }
			 //将参数转成Json
			 JSONObject paramJson = JSONObject.fromObject( jdata );
			 if(paramJson != null){
				 if(LOGGER.isInfoEnabled()){
					 LOGGER.info("【5】接口接到的数据:"+paramJson.toString());
				 }
				 PatchService patchService = new PatchService( paramJson.getString("crc"), paramJson.getString("url") );
				 patchService.pushIndexInfo();
				 result.setResult("0");
				 result.setDesc("success");
			 }else{
				 result.setResult("1");
				 result.setDesc("fail");
				 LOGGER.error("jdata is null");
			 }
			 
		} catch (Exception e) {  
			LOGGER.error( "补丁服务文件列表xml推送 有错jdata="+jdata , e );
			result.setResult("1");
		}finally{
			try {
				if(LOGGER.isInfoEnabled()){
					LOGGER.info("【"+IUpdownloadService.PATCH_XML+"】接口返回值:"+result.toJson());
				}
				getResponse().getWriter().write( result.toJson() ); 
			} catch (IOException e) {
				LOGGER.error( "【"+IUpdownloadService.PATCH_XML+"】接口返回值报错:" , e );
			}
		} 
	}  
	@SuppressWarnings("unused")
	public String getRequestData(InputStream input ){ 
		int ReadedbyteDataCount = 0;
		byte[] tmp = null;
		ByteArrayOutputStream bytearray = new ByteArrayOutputStream();
		byte[] buf = new byte[4096];
		int len = -1;
		try {
			while ((len = input.read(buf, 0, 4096)) != -1) {
				bytearray.write(buf, 0, len);
				ReadedbyteDataCount += len;
			}
		} catch (OutOfMemoryError e) {
			bytearray.reset();
			LOGGER.error("Not enough memory:" + e.getMessage());
		} catch (IOException e) {
			bytearray.reset();
			LOGGER.error(e);
		} finally {
			try {
				bytearray.close();
			} catch (IOException e) {
				LOGGER.error(e);
			}
		}
		tmp = bytearray.toByteArray();
		try {
			return new String(tmp, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e);
		}
		return null;
	}
	 
}
 