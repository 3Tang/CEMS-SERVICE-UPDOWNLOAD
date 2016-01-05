package com.vrv.cems.service.updownload.business; 
 
import java.io.IOException; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;  

import net.sf.json.JSONObject; 

import org.apache.log4j.Logger;  

import com.vrv.cems.service.updownload.config.ServicePortConfig;
import com.vrv.cems.service.updownload.service.WebConfigService;

/** 
 *   <B>说       明</B>: 启用单线程的原因，在于SystemConfig是单例的。
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年4月10日 上午10:04:59 
 */
public class WebConfigProcess extends BusinessProcess {
	private static final Logger LOGGER = Logger.getLogger(WebConfigService.class); 
	public WebConfigProcess(HttpServletRequest request, HttpServletResponse response) {
		super(request, response); 
	}
 
	@Override
	public synchronized void process() { 
		boolean isError =false ;
		try { 
			
			String data = (String) getRequest().getAttribute("data");
			//使数据生效
			WebConfigService webConfigService = new  WebConfigService( data );
			webConfigService.changeConfig();
			 
		} catch (Exception e) {
			isError = true ;
			LOGGER.error("修改文件发生错误",e );
		}finally{
			JSONObject resultjson = new JSONObject();
			resultjson.accumulate("maxCode", ServicePortConfig.UD_MAXCODE )
					  .accumulate("minCode", ServicePortConfig.UD_CUPGRADE_PUSH_SERVICE_MINCODE )
					  .accumulate("desc", "")
					  .accumulate("jdata", "[]")
					  .accumulate("result", (isError ? "1" : "0"));
			try {
				System.out.println( getResponse() );
				getResponse().getWriter().print( resultjson.toString() );
				getResponse().getWriter().flush();
			} catch (IOException e) {
				LOGGER.error("输出有误", e );
			}
			
		} 
	} 
}
 