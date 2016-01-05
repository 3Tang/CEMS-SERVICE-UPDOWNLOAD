package com.vrv.cems.service.updownload.parse; 

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter; 

import javax.servlet.ServletOutputStream; 
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/** 
 *   <B>说       明</B>:对响应流做包装，实现对响应流的加密操作
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年4月16日 下午4:59:48 
 */
public class ServletResponseDecode extends HttpServletResponseWrapper { 
	public ServletResponseDecode(HttpServletResponse response) {
		super(response); 
	}
	private ByteArrayOutputStream baos = new ByteArrayOutputStream();
	private volatile boolean useOutputStream ;
	private PrintWriter printWriter;
	
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		 if ( printWriter != null) {
	            throw new IllegalStateException();
	     }
		 useOutputStream = true ;
		 return super.getOutputStream();
	}
	@Override
	public PrintWriter getWriter() throws IOException {
		if( useOutputStream ){
			 throw new IllegalStateException();
		}
		if( printWriter != null ){
			return printWriter ;
		}
		printWriter = new PrintWriter( baos ); 
		return printWriter;
	}
	public boolean isUseOutputStream() {
		return useOutputStream;
	}
	/**
	 * 返回打印流
	 * @return
	 */
	public byte[] getPrintData() {
		try {
			printWriter.flush();
			baos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
	

}
 