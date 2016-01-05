package com.vrv.cems.service.updownload.bean; 

import java.io.Serializable;

/** 
 *   <B>说       明</B>: 
 *   下载文件时的索引文件，用于同客户端的参数做比较
 *   验证 是否提供下载。
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月27日 上午10:21:55 
 */
public class CUpGradeIndex implements Serializable{
	 
	private static final long serialVersionUID = -2039517853421903120L;
	/**
	 * 本地存储 路径
	 */
	private String path ;
	/**
	 * 文件的crc 用于是否再次从fastdfs下载文件，
	 * 同升级服务的参数做比较
	 */
	private String crc ;
	/**
	 * FastDFS路径
	 */
	private transient String url ;
	/**
	 * 升级包发布范围中的按照组织机构
	 */
	private String org;
	/**
	 * 升级包发布范围中的按照IP范围
	 */
	private String ip ;
	/**
	 * 升级包发布版本
	 */
	private String version ;
	private int type;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getCrc() {
		return crc;
	}
	public void setCrc(String crc) {
		this.crc = crc;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	} 
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	} 
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((crc == null) ? 0 : crc.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((org == null) ? 0 : org.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CUpGradeIndex other = (CUpGradeIndex) obj;
		if (crc == null) {
			if (other.crc != null)
				return false;
		} else if (!crc.equals(other.crc))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (org == null) {
			if (other.org != null)
				return false;
		} else if (!org.equals(other.org))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	} 
	
	public CUpGradeIndex() { }
	public CUpGradeIndex(String path, String crc, String org, String ip , String version , int type) { 
		this.path = path;
		this.crc = crc;
		this.org = org;
		this.ip = ip;
		this.version = version;
		this.type = type;
	}
}
 