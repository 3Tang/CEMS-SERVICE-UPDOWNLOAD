package com.vrv.cems.service.updownload.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.sys.common.util.json.JsonUtils;
import com.vrv.cems.service.base.interfaces.ITCPService;
import com.vrv.cems.service.updownload.bean.RequestBean;
import com.vrv.cems.service.updownload.business.BusinessProcess;
import com.vrv.cems.service.updownload.factory.BusinessProcessFactory;
import com.vrv.cems.service.updownload.util.RequestParseUtils;
/** 
 *   <B>说       明</B>:上传下载Servlet
 *
 * @author  作  者  名：dongyifei<br/>
 *		    E-mail ：dongyifei@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2014年9月30日 下午2:43:57 
 */
public class UpLoad extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(UpLoad.class);
	
	private static final long serialVersionUID = -7117401969811748716L;
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestBean requestBean = null;
		
		try {
			LOGGER.info("1.接到["+request.getRemoteAddr()+"]的请求");
			
			//获取URL中的MaxCode
			String urlMaxCode = request.getParameter("maxCode");
			//判断maxCode是否为空
			if(StringUtils.isBlank(urlMaxCode)){
				LOGGER.error("MaxCode ERROR ：[MaxCode is null]");
				response.getWriter().print("MaxCode ERROR ：[MaxCode is null]");
				return;
			}
			
			LOGGER.info("2.UrlMaxCode:"+urlMaxCode);
			if(urlMaxCode.equalsIgnoreCase(ITCPService.SERVICE_CODE)){
				LOGGER.info(" 2.1处理客户端请求");
				//解析请求头中的参数数据
				String data = request.getParameter("downData");
				LOGGER.info("base64前："+data);
//				data = URLDecoder.decode(data);
//				data = Base64Utils.decode2string(data);
				LOGGER.info("downData:"+data);
				requestBean = JsonUtils.json2Object(data, RequestBean.class);//(WebRequestBean) RequestParseUtils.parseRequest(request);
				request.setAttribute("sessionId", requestBean.getSessionId());
			}else{
				// 服务调用
				LOGGER.info(" 2.1处理服务请求");
				requestBean = RequestParseUtils.parseWebRequest(request);
			}
			
			//需要做一些处理，判断CRC，包括解密处理
			
			//request设置属性
			request.setAttribute("flag", requestBean.getFlag());
			request.setAttribute("maxCode", requestBean.getMaxCode());
			request.setAttribute("minCode", requestBean.getMinCode());
			request.setAttribute("crc", requestBean.getCheckCode());
			request.setAttribute("isZip", requestBean.isZip);
			request.setAttribute("data", new String(requestBean.getData()));
			LOGGER.info("###向request中设置属性结束！");
			
			BusinessProcess businessProcess = BusinessProcessFactory.createBusinessProcess(request, response);
			LOGGER.info( businessProcess );
			businessProcess.process(); 
			
		} catch (Exception e) {
			LOGGER.error( "调用出错" , e );
		}
	}
}
