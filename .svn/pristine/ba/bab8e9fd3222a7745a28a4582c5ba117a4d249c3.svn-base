package com.vrv.cems.service.updownload.business; 

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;
  


import com.vrv.cems.service.updownload.service.CUpGradeInfoService;
/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月29日 上午11:26:37 
 */
public class CUpGradeInfoProcess extends BusinessProcess {
	private static final  Logger LOGGER = Logger.getLogger(CUpGradeInfoProcess.class);
	public CUpGradeInfoProcess(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response); 
	}

	@Override
	public void process() { 
		String jData = (String) getRequest().getAttribute("data");
		JSONObject paramJson = JSONObject.fromObject( jData );
		String devOnlyId = paramJson.getString("devOnlyId");
		String osType = paramJson.getString("osType");
		String productType = paramJson.getString("productType");
		if( LOGGER.isInfoEnabled() ){
			LOGGER.info( "devOnlyId:\t"+ devOnlyId );
			LOGGER.info( "osType:\t"+ osType  );
			LOGGER.info( "productType:\t"+ productType  );
		}
		CUpGradeInfoService cUpGradeInfoService = new CUpGradeInfoService(devOnlyId, osType, productType) ;
		String cUpGradeInfo = cUpGradeInfoService.getInfo() ;
		try {
			getResponse().getWriter().print( cUpGradeInfo );
		} catch (IOException e) { 
			e.printStackTrace();
		} 
	}

}
 