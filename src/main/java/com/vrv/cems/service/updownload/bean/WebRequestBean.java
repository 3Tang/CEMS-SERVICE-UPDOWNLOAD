package com.vrv.cems.service.updownload.bean; 
/** 
 *   <B>说       明</B>:服务请求bean
 *
 * @author  作  者  名：chenjinlong<br/>
 *		    E-mail ：chenjinlong@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年7月8日 下午1:42:30 
 */
public class WebRequestBean extends RequestBean {

	private static final long serialVersionUID = -6484542419018480357L;

	public String key;
	
	/**
	 * 是否加密
	 */
	public boolean isEncrypt;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isEncrypt() {
		return isEncrypt;
	}

	public void setEncrypt(boolean isEncrypt) {
		this.isEncrypt = isEncrypt;
	}

	public WebRequestBean(){}
	
	public WebRequestBean(String maxCode, String minCode, String checkCode, boolean isZip, 
			boolean isEncrypt, byte[] data, String key, String flag){
		this.maxCode = maxCode;
		this.minCode = minCode;
		this.checkCode = checkCode;
		this.isZip = isZip;
		this.isEncrypt = isEncrypt;
		this.data = data;
		this.key = key;
		this.flag = flag;
	}
}
 