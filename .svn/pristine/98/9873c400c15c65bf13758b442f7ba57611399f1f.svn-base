package com.vrv.cems.service.updownload.bean; 

import net.sf.json.JSONArray; 

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger; 

/** 
 *   <B>说       明</B>: 设备信息
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年4月2日 下午1:51:32 
 */
public class Device {
	private static final Logger LOGGER = Logger.getLogger(Device.class); 
	/**
	 * 组织机构ID
	 */
	private String orgId ;
	/**
	 * ip地址
	 */
	private String ip ;
	/**
	 * 子网掩码
	 */
	private String subNet ;
	/**
	 * 网卡信息 
	 */
	private String netWork;
	
	
	public Device(String orgId, String ip, String netWork) { 
		this.orgId = orgId;
		this.ip = ip;
		this.netWork = netWork;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getNetWork() {
		return netWork;
	}
	public void setNetWork(String netWork) {
		this.netWork = netWork;
	}
	/**
	 * 根据 newWork 获取 子网掩码
	 * @return 子网掩码
	 */
	public String getSubNet() {
		if( StringUtils.isNotBlank( subNet )){
			return subNet;
		}
		if( StringUtils.isBlank( netWork )){
			return null ;
		}
		try {
			JSONArray jsonArray = JSONArray.fromObject( netWork );
			subNet = jsonArray.getJSONObject(0).getJSONArray("ip").getJSONObject(0).getString("mask");
		} catch (Exception e) {
			LOGGER.error("netWork="+netWork , e ); 
		}
		return subNet;
	} 
}
 