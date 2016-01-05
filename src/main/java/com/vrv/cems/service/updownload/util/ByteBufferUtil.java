package com.vrv.cems.service.updownload.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * <B>说 明</B>:
 * 
 * @author 作 者 名： zhangwenli E-mail ：zhangwenli@vrvmail.com.cn
 * 
 * @version 版 本 号：V1.0 <br/>
 *          创建时间：2015年1月6日 下午4:42:32
 */
public class ByteBufferUtil {
	/**
	 * String数据转 ByteBuffer
	 * 
	 * @param str
	 * @return
	 */
	public static ByteBuffer stringToByteBuffer(String str) {
		return ByteBuffer.wrap(str.getBytes());
	}

	/**
	 * ByteBuffer转String
	 * 
	 * @param buffer
	 * @return
	 */
	public static String byteBufferToString(ByteBuffer buffer) {
		Charset charset = null;
		CharsetDecoder decoder = null;
		CharBuffer charBuffer = null;
		try {
			charset = Charset.forName("UTF-8");
			decoder = charset.newDecoder();
			// charBuffer = decoder.decode(buffer);//用这个的话，只能输出来一次结果，第二次显示为空
			charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
			return charBuffer.toString();
		} catch (Exception ex) {
			throw new RuntimeException("ByteBuffer To String Fail");
		}
	}
}
