package com.vrv.cems.service.updownload.parse;
public class ParamParamBean {
	private boolean isZip ;
	private String sessionId ; 
	private String checkCode ;
	private String encryptKey ;
	private int encryptFlag ;
	private boolean isEncrypte ;
	
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
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public String getEncryptKey() {
		return encryptKey;
	}
	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}
	public int getEncryptFlag() {
		return encryptFlag;
	}
	public void setEncryptFlag(int encryptFlag) {
		this.encryptFlag = encryptFlag;
	}
	public boolean isEncrypte() {
		return isEncrypte;
	}
	public void setEncrypte(boolean isEncrypte) {
		this.isEncrypte = isEncrypte;
	} 
	
	
}