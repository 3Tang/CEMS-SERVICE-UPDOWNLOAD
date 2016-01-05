package com.vrv.cems.service.updownload.web.filter; 

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 
import org.apache.commons.lang.StringUtils;

import com.vrv.cems.service.base.util.ByteBufferUtils;
import com.vrv.cems.service.updownload.parse.ParamParamBean;
import com.vrv.cems.service.updownload.parse.ServletRequestDecode;
import com.vrv.cems.service.updownload.parse.ServletResponseDecode;
import com.vrv.cems.service.updownload.util.EncryptUtil;

/** 
 *   <B>说       明</B>:添加 编码过滤器 ，并对request,response做包装
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年2月5日 上午9:55:01 
 */
public class ParseFilter implements Filter {
	private String charset ;

	public void init(FilterConfig filterConfig) throws ServletException {
		String tempCharset = filterConfig.getInitParameter("charset");
		if( StringUtils.isBlank( tempCharset ) ){
			charset = "UTF-8";
		}else{
			charset = tempCharset;
		} 
	}


	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String tempCharset =  request.getCharacterEncoding();
		if( StringUtils.isNotBlank( tempCharset ) ){
			request.setCharacterEncoding( charset );
		}
		//组装参数
		ParamParamBean encryptParamBean = new ParamParamBean();
		encryptParamBean.setCheckCode( request.getParameter("checkCode") );
		if( request.getParameter("flag") != null ){
			encryptParamBean.setEncryptFlag( Integer.parseInt( request.getParameter("flag") ));
		}
		encryptParamBean.setEncryptKey(  request.getParameter("checkCode") );
		encryptParamBean.setSessionId( request.getParameter("sessionId") ); 
		encryptParamBean.setZip( Boolean.getBoolean( request.getParameter("isZip") ));
		encryptParamBean.setEncrypte( Boolean.getBoolean( request.getParameter("isEncrypt") ));
		//对response,request做包装
		ServletRequestDecode servletRequestDecode = new ServletRequestDecode((HttpServletRequest) request , encryptParamBean ) ;
		ServletResponseDecode servletResponseDecode = new ServletResponseDecode((HttpServletResponse) response);
		chain.doFilter( servletRequestDecode , servletResponseDecode);
		//对字符响应流做加密操作
		if( !servletResponseDecode.isUseOutputStream() ){
			byte[] bytes =servletResponseDecode.getPrintData();
			EncryptUtil encryptUtil = new EncryptUtil( encryptParamBean );
			String encoderStr = encryptUtil.encoder( ByteBufferUtils.byteBuffer2String( ByteBuffer.wrap( bytes ) ) );
			response.getWriter().print( encoderStr );
			response.getWriter().flush();
		}
	}

	
	public void destroy() {
	}

}
 