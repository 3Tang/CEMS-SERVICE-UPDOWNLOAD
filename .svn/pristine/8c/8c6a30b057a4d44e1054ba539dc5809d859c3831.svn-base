package com.vrv.cems.service.updownload.util; 

import java.io.File; 
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry; 
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;

/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月27日 下午1:29:14 
 */
public class ZipFileUtil {
	/**
	 * 解压
	 * @param srcPath 原路径
	 * @param destPath 目标路径
	 */
	public static void doCompress(String zipPath , String destPath){
		File srcFile = new File( zipPath );
		if( !srcFile.exists()){
			return;
		}
		File destFile = new File( destPath );
		if( !destFile.exists() ){
			destFile.mkdirs();
		} 
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile( srcFile );
			Enumeration<ZipArchiveEntry> enumeration = zipFile.getEntries();
			while( enumeration.hasMoreElements() ){
				ZipArchiveEntry zipArchiveEntry = enumeration.nextElement();
				File parentFile = new File( destPath+File.separator+zipArchiveEntry.getName() );
				if( zipArchiveEntry.isDirectory() ){
					parentFile.mkdirs();
					continue;
				}else{
					parentFile.createNewFile();
				}
				FileOutputStream fileOutputStream = new FileOutputStream( parentFile ,false );
				InputStream inputStream = zipFile.getInputStream(  zipArchiveEntry );
				IOUtils.copy( inputStream , fileOutputStream );
				IOUtils.closeQuietly( inputStream );
				IOUtils.closeQuietly( fileOutputStream );
			}
		} catch (Exception e) {
			throw new RuntimeException( e );
		}finally{
			try {
				zipFile.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} 
	}
	public static void main(String[] args) {
		ZipFileUtil.doCompress("D:/jbpm-4.4.zip","D:/");
	}
}
 