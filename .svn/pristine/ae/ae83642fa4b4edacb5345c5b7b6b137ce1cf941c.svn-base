package com.vrv.cems.service.updownload.util; 

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.vrv.cems.service.updownload.bean.RequestBean;
import com.vrv.cems.service.updownload.bean.WebRequestBean;
import com.vrv.cems.service.updownload.statics.SystemStatics;

/** 
 *   <B>说       明</B>:解析Request中的请求参数工具类
 *
 * @author  作  者  名：chenjinlong<br/>
 *		    E-mail ：chenjinlong@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年3月30日 下午4:00:04 
 */
public class RequestParseUtils {
	private static Logger log = Logger.getLogger(RequestParseUtils.class);
	/**
	 * 把HttpRequest中的参数转成RequestBean
	 * @param HttpServletRequest
	 * @return RequestBean
	 */
	public static RequestBean parseRequest(HttpServletRequest request){
		RequestBean requestBean = new RequestBean();
		try {
			InputStream inputStream = request.getInputStream();
			
			if(inputStream == null || inputStream.available()<1){
				return null;
			}
			byte[] content = dataStream2Byte(inputStream);
			byte[] flagByte = Arrays.copyOfRange(content, 0, 4);
			String flag = ByteConvertUtils.getString(flagByte);
			requestBean.setFlag(flag);
			//version
			byte[] versionByte = Arrays.copyOfRange(content, 4, 8);
			int version = ByteConvertUtils.getInt(versionByte);
			requestBean.setVersion(version);
			//size
			byte[] sizeByte = Arrays.copyOfRange(content, 8, 12);
			int size = ByteConvertUtils.getInt(sizeByte);
			requestBean.setSize(size);
			//crc
			byte[] crcByte = Arrays.copyOfRange(content, 12, 16);
			String checkCode = ByteConvertUtils.toHexString(crcByte);
			while(checkCode.startsWith("0")){
				checkCode = checkCode.substring(1,checkCode.length());
			}
			requestBean.setCheckCode(checkCode);
			//sessionId
			byte[] sessionIdByte = Arrays.copyOfRange(content, 16, 32);
			String sessionId = ByteConvertUtils.toHexString(sessionIdByte);
			requestBean.setSessionId(sessionId);
			//msgCode
			byte[] msgCodeByte = Arrays.copyOfRange(content, 32, 36);
			int msgCode = ByteConvertUtils.getInt(msgCodeByte);
			requestBean.setMsgCode(msgCode);
			//maxCode 00 03 01 00 ==== 00 01 03 00
			byte[] maxCodeByte = Arrays.copyOfRange(content, 36, 40);
			String maxCode = ByteConvertUtils.toHexString(maxCodeByte);
			requestBean.setMaxCode(maxCode.toUpperCase());
			//minCode
			byte[] minCodeByte = Arrays.copyOfRange(content, 40, 44);
			int minCode = ByteConvertUtils.getInt(minCodeByte);
			requestBean.setMinCode(String.valueOf(minCode));
			//headSize
			byte[] headSizeByte = Arrays.copyOfRange(content, 44, 46);
			int headSize = ByteConvertUtils.getShort(headSizeByte);
			requestBean.setHeadSize(headSize);
			//type
			byte[] typeByte = Arrays.copyOfRange(content, 46, 48);
			int type = ByteConvertUtils.getShort(typeByte);
			requestBean.setZip(type == 1);
			//count
			byte[] countByte = Arrays.copyOfRange(content, 48, 50);
			int count = ByteConvertUtils.getShort(countByte);
			requestBean.setCount(count);
			//index
			byte[] indexByte = Arrays.copyOfRange(content, 50, 52);
			int index = ByteConvertUtils.getShort(indexByte);
			requestBean.setIndex(index);
			//data
			byte[] dataByte = Arrays.copyOfRange(content, headSize, content.length);
			requestBean.setData(dataByte);
		} catch (IOException e) {
			log.error("解析TCP头文件报错");
		}
		return requestBean;
	}
	
	/**
	 * 把HttpRequest中的参数转成RequestBean
	 * @param HttpServletRequest
	 * @return RequestBean
	 */
	public static WebRequestBean parseWebRequest(HttpServletRequest request){
		try {
			String maxCode = request.getParameter(SystemStatics.URL_MAXCODE);
			String minCode = request.getParameter(SystemStatics.URL_MINCODE);
			String checkCode = request.getParameter(SystemStatics.URL_CHECKCODE);
			boolean isZip = request.getParameter(SystemStatics.URL_ISZIP) != null;
			boolean isEncrypt = request.getParameter(SystemStatics.URL_ISENCRYPT) != null;
			String key = request.getParameter(SystemStatics.URL_KEY);
			String flag = request.getParameter(SystemStatics.URL_FLAG);
			byte[] data = dataStream2Byte(request.getInputStream());
			return new WebRequestBean(maxCode, minCode, checkCode, isZip, isEncrypt, data, key, flag);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 输入流转成字节数组
	 */
	public static byte[] dataStream2Byte(InputStream inputStream){
		
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
			log.error("Not enough memory: "+e);
			return null;
		}catch (IOException e) {
			byteArray.reset();
			return null;
		}finally{
			try{
				byteArray.close();
			}catch(IOException e){
				log.error("byteArry close error: "+e);
			}
		}
		if(byteArray.size() != 0){
			returnByte = byteArray.toByteArray();
		}
		
		return returnByte;
	}
	
	public static InputStream byte2Stream(byte [] data){
		return new ByteArrayInputStream(data);
	}
}
 