package com.vrv.cems.service.updownload.bean; 
/** 
 *   <B>说       明</B>: crc 标识 码
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月27日 下午4:23:15 
 */
public enum CrcMark {
	/**
	 * 补丁
	 */
	PATCH("patch") ,
	/**
	 * 基本升级
	 */
	BASE_CUPGRADE("base_cupgrade") ,
	/**
	 * 定制升级
	 */
	CUSTOM_CUPGRADE ("custom_cupgrade") ,
	
	/**
	 * 策略
	 */
	POLICY("policy");
	
	private String key;
	
	private CrcMark(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	@Override
	public String toString() { 
		return this.getKey();
	}
}
 