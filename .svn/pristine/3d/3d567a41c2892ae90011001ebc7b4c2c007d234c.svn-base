package com.vrv.cems.service.updownload.bean; 

import java.io.Serializable;

/** 
 *   <B>说       明</B>:客户端请求辅助bean
 *
 * @author  作  者  名：chenjinlong<br/>
 *		    E-mail ：chenjinlong@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年3月30日 下午4:01:15 
 */
public class RequestBean implements Serializable{
	private static final long serialVersionUID = 5743633796343374996L;
	/**
	 * 标记
	 */
	public String flag;
	/**
	 * 包头版本
	 */
	public int version;
	/**
	 * 数据包大小(除包头)
	 */
	public int size;
	/**
	 * 包头大小
	 */
	public int headSize;
	/**
	 * 主功能号
	 */
	public String maxCode;
	/**
	 * 子功能号
	 */
	public String minCode;
	/**
	 * 校验码
	 */
	public String checkCode;
	/**
	 * 是否压缩
	 */
	public boolean isZip;
	/**
	 * 会话ID
	 */
	public String sessionId;
	/**
	 * 校验码,累加
	 */
	public int  msgCode;
	/**
	 * 服务区域ID
	 */
	public String areaId;
	/**
	 * 请求数据长度
	 */
	public int dataLength;
	/**
	 * 包个数(UDP用)
	 */
	public int count;
	/**
	 * 第几个包(UDP用)
	 */
	public int index;
	/**
	 * 数据内容
	 */
	public byte[] data;
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getHeadSize() {
		return headSize;
	}
	public void setHeadSize(int headSize) {
		this.headSize = headSize;
	}
	public String getMaxCode() {
		return maxCode;
	}
	public void setMaxCode(String maxCode) {
		this.maxCode = maxCode;
	}
	public String getMinCode() {
		return minCode;
	}
	public void setMinCode(String minCode) {
		this.minCode = minCode;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public boolean isZip() {
		return isZip;
	}
	public void setZip(boolean isZip) {
		this.isZip = isZip;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public int getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(int msgCode) {
		this.msgCode = msgCode;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public int getDataLength() {
		return dataLength;
	}
	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public RequestBean(){}
	
	public RequestBean(String maxCode, String minCode, String checkCode,
			boolean isZip, String sessionId, int msgCode, String areaId) {
		this.maxCode = maxCode;
		this.minCode = minCode;
		this.checkCode = checkCode;
		this.isZip = isZip;
		this.sessionId = sessionId;
		this.msgCode = msgCode;
		this.areaId = areaId;
	}
	
	public RequestBean(String maxCode, String minCode, String checkCode,
			boolean isZip, String sessionId, int msgCode, String areaId,
			int dataLength) {
		this.maxCode = maxCode;
		this.minCode = minCode;
		this.checkCode = checkCode;
		this.isZip = isZip;
		this.sessionId = sessionId;
		this.msgCode = msgCode;
		this.areaId = areaId;
		this.dataLength = dataLength;
	}
	@Override
	public String toString() {
		return "RequestBean [maxCode=" + maxCode + ", minCode=" + minCode
				+ ", checkCode=" + checkCode + ", isZip=" + isZip
				+ ", sessionId=" + sessionId + ", msgCode=" + msgCode
				+ ", areaId=" + areaId + "]";
	}
	
}
 