package com.vrv.cems.service.updownload.util; 

import org.apache.log4j.Logger;

import com.vrv.cems.service.updownload.parse.ParamParamBean;

/** 
 *   <B>说       明</B>:加解密工具类
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年4月16日 下午5:24:31 
 */
public class EncryptUtil {
	private static Logger LOGGER = Logger.getLogger( EncryptUtil.class);
	private ParamParamBean encryptParamBean;
	public EncryptUtil( ParamParamBean encryptParamBean ){
		this.encryptParamBean = encryptParamBean;
	}
	/**
	 * 解码
	 * 第一步解压缩，第二步比较CRC值，第三步解密
	 * @return
	 */
	public String decoder( String data ){
		/*ByteBuffer dataByteBuffer = ByteBufferUtils.string2ByteBuffer( data );
		if( encryptParamBean.isZip() ){
			LOGGER.info("开始解压数据");
			dataByteBuffer = Compressor.getInstance(CompressFormat.ZIP).decompress( dataByteBuffer.asReadOnlyBuffer() );
			LOGGER.info("完成解压数据");
		}
		if( StringUtils.isNotBlank( encryptParamBean.getCheckCode() ) ){
			LOGGER.info("开始计算CRC");
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("接到的CRC："+ encryptParamBean.getCheckCode() ); 
			}
			String crc = CRCUtils.getCRC32StringValue( ByteBufferUtils.byteBuffer2String(dataByteBuffer.asReadOnlyBuffer()) );
			if( !StringUtils.equals( crc ,  encryptParamBean.getCheckCode() )){
				LOGGER.error(SystemConstants.ERROR_ANALYZE_CRCCHECK+":CRC验证未通过");
				throw new RuntimeException("CRC验证未通过");
			}
			LOGGER.info("完成计算CRC");
		}
		if( encryptParamBean.isEncrypte() ){
			LOGGER.info("开始解密数据");
			dataByteBuffer = BaseEncryptDecryptUtils.decrypt( encryptParamBean.getEncryptKey(), encryptParamBean.getEncryptFlag() , dataByteBuffer.asReadOnlyBuffer());
			LOGGER.info("完成解密数据");
		}*/
		 
		 
		//return ByteBufferUtils.byteBuffer2String( dataByteBuffer );
		return data;
		
	}
	/**
	 * 编码
	 * 调用者处理：第一步加密，第二步算checkCode，第三步压缩
	 * @return
	 */
	public String encoder( String data ){
		/*ByteBuffer dataByteBuffer = ByteBufferUtils.string2ByteBuffer( data );
		
		if( encryptParamBean.isEncrypte() ){
			LOGGER.info("开始编码数据");
			dataByteBuffer = BaseEncryptDecryptUtils.encrypt( encryptParamBean.getEncryptKey(), encryptParamBean.getEncryptFlag() , dataByteBuffer.asReadOnlyBuffer());
			LOGGER.info("完成编码数据");
		}
		if( encryptParamBean.isZip() ){
			LOGGER.info("开始编码数据");
			dataByteBuffer = Compressor.getInstance(CompressFormat.ZIP).compress(  dataByteBuffer.asReadOnlyBuffer() );
			LOGGER.info("完成编码数据");
		} 
		return ByteBufferUtils.byteBuffer2String( dataByteBuffer );*/
		return data;
	}
}
 