package com.vrv.cems.service.updownload.business; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 *   <B>说       明</B>:业务处理
 *
 * @author  作  者  名：dongyifei<br/>
 *		    E-mail ：dongyifei@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2014年8月22日 下午12:11:22 
 */
public abstract class BusinessProcess {
	private HttpServletRequest request;
	private HttpServletResponse response;
	public BusinessProcess(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	public String validate(){
		return "";
	}
	public abstract void process();
	public HttpServletRequest getRequest() {
		return request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
}
 