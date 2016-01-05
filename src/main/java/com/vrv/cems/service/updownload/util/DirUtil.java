package com.vrv.cems.service.updownload.util;


public class DirUtil {

	public static String getTempDir() {
		String dir= System.getProperty("java.io.tmpdir");
		dir=dir.replace("\\", "/");
		if(!dir.endsWith("/"))
			dir+="/";		
		return dir;
	}
	
	public static String getAppDir() {
		String dir= System.getProperty("user.dir");
		dir=dir.replace("\\", "/");	
		return dir;
	}
	
}
