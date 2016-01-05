package com.vrv.cems.service.updownload.factory; 
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.vrv.cems.service.base.bean.ResultMsgBean;
import com.vrv.cems.service.base.interfaces.IUpdownloadService;
import com.vrv.cems.service.updownload.business.BusinessProcess;
import com.vrv.cems.service.updownload.business.CUpGradeServiceProcess;
import com.vrv.cems.service.updownload.business.CupGradeDownLoadProcess;
import com.vrv.cems.service.updownload.business.PatchDownLoadProcess;
import com.vrv.cems.service.updownload.business.PatchServiceProcess;
import com.vrv.cems.service.updownload.business.PolicyDownLoadProcess;
import com.vrv.cems.service.updownload.business.PolicyServiceProcess;
import com.vrv.cems.service.updownload.business.WebConfigProcess;
import com.vrv.cems.service.updownload.statics.SystemStatics;

/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：dongyifei<br/>
 *		    E-mail ：dongyifei@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2014年8月22日 下午2:40:49 
 */
public class BusinessProcessFactory {
	
	private static final Logger LOGGER = Logger.getLogger(BusinessProcessFactory.class);
	
	//4  因为对资源有修改操作 ，故添加到单例Map 
	public static BusinessProcess createBusinessProcess(HttpServletRequest request,HttpServletResponse response){
		BusinessProcess businessProcess = null;
		String minCode = String.valueOf(request.getAttribute(SystemStatics.URL_MINCODE));
		//2.下载客户端升级文件				
		if( IUpdownloadService.DOWN_CLIENT_FILE.equals( minCode )){
			businessProcess = new CupGradeDownLoadProcess(request, response) ;
		}
		//3.下载补丁文件
		else if( IUpdownloadService.DOWN_PATCH_FILE.equals( minCode ) ){
			businessProcess = new PatchDownLoadProcess(request, response);
		}
		//4.客户端升级服务推送xml内容
		else if( IUpdownloadService.UPGRADE_INFO.equals( minCode )){ 
			businessProcess =new CUpGradeServiceProcess(request, response) ;
		}
		//5.补丁服务推送update-patch.xml
		else if( IUpdownloadService.PATCH_XML.equals( minCode )){ 
			businessProcess = new PatchServiceProcess(request, response) ;
		}
		//6.策略分发服务推送update-policy.xml
		else if( IUpdownloadService.UPDATE_POLICY_XML.equals( minCode )){
			businessProcess =  new PolicyServiceProcess(request, response);
		}
		//7.下载策略分发文件
		else if( IUpdownloadService.DOWN_PLICY_FILE.equals( minCode ) ){
			businessProcess = new PolicyDownLoadProcess(request, response);
		}
		//1000.服务策略
		else if( IUpdownloadService.POLICY_XML.equals( minCode ) ){
			businessProcess = new WebConfigProcess(request, response) ; 
		}else{
			try {
				LOGGER.error("MinCode:"+minCode+"不存在");
				ResultMsgBean result = new ResultMsgBean(IUpdownloadService.SERVICE_CODE, minCode);
				result.setDesc("MinCode:"+minCode+"不存在");
				result.setResult("1");
				response.getWriter().write(result.toJson());
				return null;
			} catch (IOException e) {
				LOGGER.error("返回信息报错!", e);
			}
		}
		return businessProcess;
	}
}
 