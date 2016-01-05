package com.vrv.cems.service.updownload.business; 
  
import java.io.IOException;  

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 
import net.sf.json.JSONObject; 

import org.apache.log4j.Logger; 

import com.vrv.cems.service.updownload.config.ServicePortConfig;  
import com.vrv.cems.service.updownload.service.PolicyService;
/** 
 *   <B>说       明</B>:策略分发服务推送
 *
 * @author  作  者  名：    zhangwenli
 *		    E-mail ：zhangwenli@vrvmail.com.cn
 
 * @version 版   本  号：V1.0 <br/>
 *          创建时间：2015年1月15日 下午2:18:03 
 */ 
public class PolicyServiceProcess extends BusinessProcess {
	private static final Logger LOGGER = Logger.getLogger(PolicyServiceProcess.class); 
	public PolicyServiceProcess(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
	}
	@Override
	public synchronized void process() {
		JSONObject resultjson = new JSONObject();
		resultjson.accumulate("maxCode", ServicePortConfig.UD_MAXCODE )
				  .accumulate("minCode", ServicePortConfig.UP_POLICY_PUSH_SERVICE_MINCODE )
				  .accumulate("desc", "")
				  .accumulate("jdata", "[]");
		String jdata = "";
		 try {
			 jdata = (String) getRequest().getAttribute("data");
			 
			 if( LOGGER.isInfoEnabled() ){
				 LOGGER.info( "data="+jdata);
			 }
			 JSONObject paramJson = JSONObject.fromObject( jdata ); 
			 
			 PolicyService patchService = new PolicyService( paramJson.getString("crc"), paramJson.getString("url")  );
			 patchService.pushIndexInfo();
			 
			 resultjson.put("result", "0");
			} catch (Exception e) {  
				LOGGER.error( "策略服务文件列表xml推送接口 有错jdata="+jdata , e );
				resultjson.put("result", "1");
			}finally{ 
				try {
					getResponse().getWriter().write(resultjson.toString());
				} catch (IOException e) {
					LOGGER.error("输出有误", e );
				}
			} 
	}    
}
 