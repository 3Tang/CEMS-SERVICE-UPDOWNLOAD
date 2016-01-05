package com.vrv.cems.service.updownload.parse; 

 
 
import java.io.IOException; 
import java.nio.ByteBuffer;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;  

import org.apache.log4j.Logger;

import com.mchange.v1.io.InputStreamUtils;
import com.vrv.cems.service.base.util.ByteBufferUtils; 
import com.vrv.cems.service.updownload.util.EncryptUtil; 
/** 
 *   <B>说       明</B>: 对请求做解码操作，目前只对data参数做解码操作
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年4月16日 下午3:10:58 
 */
public class ServletRequestDecode extends HttpServletRequestWrapper {
	private static final  Logger LOGGER = Logger.getLogger(ServletRequestDecode.class);
	private ParamParamBean encryptParamBean; 
	
	
	public ServletRequestDecode(HttpServletRequest request) {
		super(request); 
	}
	public ServletRequestDecode(HttpServletRequest request , ParamParamBean encryptParamBean) {
		this( request );
		this.encryptParamBean = encryptParamBean;
	}
	/**
	 * 只对data 做解析
	 */
	@Override
	public String getParameter( String name ) {  
		LOGGER.info( "name="+name );
		if( !"data".equals( name ) ){
			return super.getParameter(name);
		}
		try {
			ServletInputStream inputStream = super.getRequest().getInputStream(); 
			byte[] bytes = InputStreamUtils.getBytes( inputStream );
			String data = ByteBufferUtils.byteBuffer2String( ByteBuffer.wrap( bytes ) );
			EncryptUtil encryptUtil = new EncryptUtil( this.encryptParamBean );
			return encryptUtil.decoder( data );
		} catch (IOException e) {
			LOGGER.error("解析出错",e);
			return null ;
		}
		
	}
}
 