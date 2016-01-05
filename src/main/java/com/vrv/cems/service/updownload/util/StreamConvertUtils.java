package com.vrv.cems.service.updownload.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <B>说 明</B>:流转换工具类
 * 
 * @author 作 者 名：zhaodj<br/>
 *         E-mail ：zhaodongjie@vrvmail.com.cn
 * 
 * @version 版 本 号：V1.0<br/>
 *          创建时间：2015年7月1日 上午9:27:50
 */
public class StreamConvertUtils {
	/**
	 * 将一个字符串转化为输入流
	 */
	public static InputStream getStringStream(String sInputString) {
		if (sInputString != null && !sInputString.trim().equals("")) {
			try {
				ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(
						sInputString.getBytes());
				return tInputStringStream;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将一个输入流转化为字符串
	 */
	public static String getStreamString(InputStream tInputStream) {
		if (tInputStream != null) {
			try {
				BufferedReader tBufferedReader = new BufferedReader(
						new InputStreamReader(tInputStream, "UTF-8"));
				StringBuffer tStringBuffer = new StringBuffer();
				String sTempOneLine = new String("");
				while ((sTempOneLine = tBufferedReader.readLine()) != null) {
					tStringBuffer.append(sTempOneLine);
				}
				return tStringBuffer.toString();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/*
	 * inputstream 2 byte[]
	 */
	public static InputStream byte2Input(byte[] buf) {
		return new ByteArrayInputStream(buf);
	}

	/*
	 * byte[] 2 inputstream
	 */
	public static byte[] input2byte(InputStream inputStream) throws IOException {
		byte[] returnByte = null;
		
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		int length = -1;
		try {
			while((length = inputStream.read(buffer, 0, 1024)) != -1){
				byteArray.write(buffer, 0, length);
			}
		} catch(OutOfMemoryError e){
			byteArray.reset();
//			log.error("Not enough memory: "+e);
			return null;
		}catch (IOException e) {
			byteArray.reset();
			return null;
		}finally{
			try{
				byteArray.close();
			}catch(IOException e){
//				log.error("byteArry close error: "+e);
			}
		}
		if(byteArray.size() != 0){
			returnByte = byteArray.toByteArray();
		}
		return returnByte;
	}

}
