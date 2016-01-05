package com.vrv.cems.service.updownload.business; 
 
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.vrv.cems.service.base.bean.ResultMsgBean;
import com.vrv.cems.service.base.interfaces.IUpdownloadService;
import com.vrv.cems.service.updownload.bean.CUpGradeIndex;
import com.vrv.cems.service.updownload.service.CUpGradeService;
 
/** 
 *   <B>说       明</B>:客户端升级服务推送update-cupgrade-base|custom.xml内容
 *
 * @author  作  者  名：    zhangwenli
 *		    E-mail ：zhangwenli@vrvmail.com.cn
 
 * @version 版   本  号：V1.0 <br/>
 *          创建时间：2015年1月14日 上午11:09:59 
 */
public class CUpGradeServiceProcess extends BusinessProcess{
	private static final Logger LOGGER = Logger.getLogger(CUpGradeServiceProcess.class);  
	public CUpGradeServiceProcess(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
	}

	@Override
	public synchronized void process() {
		//返回值
		ResultMsgBean result = new ResultMsgBean(IUpdownloadService.SERVICE_CODE, IUpdownloadService.UPGRADE_INFO);
		String jdata = "" ;
		LOGGER.info("进入【4】接口");
		try {
			jdata = (String) getRequest().getAttribute("data");
			/*
			 *内容格式
			 *{"fdfsPath":"", "fileName":"",
			 * "crc":"", "packType":""
			 * "productType":"", "osType":"",
			 * "type":"", "range":""} 
			 */
			if( LOGGER.isInfoEnabled() ){
				LOGGER.info( "jdata:\t" +jdata );
			}
			JSONObject jdataJsonObject = JSONObject.fromObject( jdata );
			String crc = jdataJsonObject.getString("crc");
			String url = jdataJsonObject.getString("fdfsPath");
			String range = jdataJsonObject.getString("range");
//			String version = jdataJsonObject.getString("version");
			String packType = jdataJsonObject.getString("packType");
			String type = "0".equals(  jdataJsonObject.getString("type") ) ? "base" : "custom"; //base custom
			String produtcType = jdataJsonObject.getString("productType");
			String osType = jdataJsonObject.getString("osType");
			String fileName = jdataJsonObject.getString("fileName");
			String filePath = packType+"/"+type+"/"+produtcType+"/"+osType+"/" +fileName ;
			LOGGER.info("@@filePath:"+filePath);
			CUpGradeIndex cUpGradeIndex = new CUpGradeIndex();
			Map<String, String> rangeMap = parseRange( range );
			cUpGradeIndex.setCrc( crc );
			cUpGradeIndex.setPath( filePath );
			cUpGradeIndex.setUrl( url );
			cUpGradeIndex.setOrg( rangeMap.get( "org" ) );
			cUpGradeIndex.setIp( rangeMap.get( "ip" ) );
//			cUpGradeIndex.setVersion( version );
			cUpGradeIndex.setType(Integer.parseInt(jdataJsonObject.getString("type")));
			
			if( LOGGER.isDebugEnabled() ){
				LOGGER.debug( "getCrc:" +cUpGradeIndex.getCrc() );
				LOGGER.debug( "getIp:" +cUpGradeIndex.getIp() );
				LOGGER.debug( "getOrg:" +cUpGradeIndex.getOrg() );
				LOGGER.debug( "getPath:" +cUpGradeIndex.getPath() );
				LOGGER.debug( "getUrl:" +cUpGradeIndex.getUrl() );
				LOGGER.debug( "getVersion:" +cUpGradeIndex.getVersion() );
			}
			// 处理接收到的内容,主要判断文件CRC是否与本地保存的CRC一致
			LOGGER.info("开始处理接收到的数据");
			CUpGradeService cUpGradeService = new CUpGradeService();
			cUpGradeService.pushIndexInfo( cUpGradeIndex); 
			
			result.setResult("0");
		} catch (Exception e) {
			result.setResult("1");
			LOGGER.error("客户端升级服务推送处理失败jdata="+jdata, e );
		} finally{
			try {
				getResponse().getWriter().print( result.toJson() );
				getResponse().getWriter().flush();
			} catch (Exception e2) {
				LOGGER.error("输出有误", e2 );
			}
		}
	} 
	
	private Map<String, String> parseRange( String range ){
		Map<String, String> rangeMap = new HashMap<String, String>(); 
		try {
			 Document document = DocumentHelper.parseText( range );
			 Element rootElement = document.getRootElement();
			 rangeMap.put("org", rootElement.element("devOrgObj").element("execute").getText() );
			 rangeMap.put("ip", rootElement.element("ipRangeObj").element("execute").getText() );
		} catch (Exception e) {
			LOGGER.error( "解析 出错:range="+range , e );
			throw new RuntimeException( e );
		}
		 return rangeMap;
	}
}
 