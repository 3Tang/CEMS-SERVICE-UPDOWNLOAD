package com.vrv.cems.service.updownload.task; 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.sys.common.util.IPAddressUtils;
import com.sys.common.util.security.CRCUtils;
import com.vrv.cems.service.base.bean.ResultMsgBean;
import com.vrv.cems.service.base.interfaces.ICEMSService;
import com.vrv.cems.service.base.interfaces.IUpdownloadService;
import com.vrv.cems.service.base.interfaces.impl.CEMSServiceImpl;
import com.vrv.cems.service.base.util.ByteBufferUtils;
import com.vrv.cems.service.updownload.service.WebConfigService;
import com.vrv.cems.service.updownload.util.ConfigPropertiesUtils;
import com.vrv.cems.service.updownload.util.ConfigureUtil;
import com.vrv.cems.service.updownload.util.JsonUtil;

/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：zhaodj<br/>
 *		   E-mail ：zhaodongjie@vrvmail.com.cn
 
 * @version 版   本  号：V1.0<br/>
 *          创建时间：2015年7月23日 下午3:54:32 
 */
public class UpdatePolicyXMLTask implements Job {
	
	
	private static final Logger log = Logger.getLogger(UpdatePolicyXMLTask.class);  
	
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException{
		log.info("1.policy.xml开始");
		
		String hsotIp=ConfigureUtil.getServiceId();
		String serverId=IPAddressUtils.ip2UUID(hsotIp);
		String serviceCode = IUpdownloadService.SERVICE_CODE;
		String localCRC = null ;
		
		String policyPath = WebConfigService.class.getClassLoader().getResource( "policy.xml" ).getPath();
		File file = new File(policyPath);
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			localCRC = CRCUtils.getCRC32StringValue(is);
			log.info("2.本地policy.xml文件CRC:"+localCRC);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			log.error("2.policy.xml文件找不到:"+policyPath,e1);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			log.error("2.policy.xml文件CRC计算失败!",e2);
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("serverId", serverId);
		jsonObject.put("serviceCode", serviceCode);
		jsonObject.put("crc", localCRC);
		String dataCrc = CRCUtils.getCRC32StringValue(jsonObject.toString());
		//调用CEMS平台服务接口[3]
		ByteBuffer dataTS = null;
		try {
			log.info("3.开始调用CEMS平台服务接口!");
			ICEMSService cemsService = cemsService = new CEMSServiceImpl();
			dataTS = cemsService.getDataTS(ICEMSService.SERVICE_CODE, ICEMSService.POLICY_XML, 
					dataCrc, false, ByteBufferUtils.string2ByteBuffer(jsonObject.toString()), false, "", 0);
		} catch (Exception e) {
			log.error("调用CEMS平台服务接口失败!",e);
		}
		JSONObject jsonObj = ByteBufferUtils.byteBuffer2Json(dataTS);
		 ResultMsgBean bean = (ResultMsgBean) JSONObject.toBean(jsonObj,ResultMsgBean.class);
		 log.info("4.调用CEMS平台接口返回数据:"+jsonObj.toString());
		 JSONObject json  = (JSONObject) bean.getJdata().get(0);
		 
		 String result = bean.getResult();
		 
		 log.info("json++:"+json.toString());
//		 String jdata = JsonUtil.getJsonValueByKey(json,"jdata");
		
		 String isChange = null;
		 String policyXml = null;
		 
		 if("0".equals(result)){
			 log.info("调用CEMS平台接口[3]返回成功!");
			 isChange = JsonUtil.getJsonValueByKey(json.toString(),"isChange");
			 policyXml = JsonUtil.getJsonValueByKey(json.toString(),"policyXml");
		 }else if("1".equals(result)){
			 log.info("调用CEMS平台接口[3]返回失败!");
		 }
		  
		 Document document = null;
		 
		 
		if("1".equals(isChange)){
			log.info("policy.xml有更新内容!");
			try {
				document = DocumentHelper.parseText(policyXml);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("获取的policyXml内容不能格式化为document",e);
			}
			
			
			OutputFormat format = OutputFormat.createPrettyPrint();  
        	// 利用格式化类对编码进行设置  
        	format.setEncoding("UTF-8");
        	FileOutputStream output;
        	XMLWriter writer = null  ;
			try {
				output = new FileOutputStream(file);
				writer = new XMLWriter(output, format); 
				writer.write(document);  
	        	writer.flush(); 
	        	
			} catch (FileNotFoundException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
				log.error("获取输出流出错!",e3);
			} catch (UnsupportedEncodingException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
				log.error("获取XML输出流出错!",e4);
			} catch (IOException e5) {
				// TODO Auto-generated catch block
				e5.printStackTrace();
				log.error("XML写出出错!",e5);
			}finally{
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
        
			
			
			
			try {
				//更新polic.xml文件调用招聘接口在这里做.
				
				//加载polic.xml文件内容
				File policyFile = new File( WebConfigService.getPolicyFilePath() );
				//获取到策论内容
				String policyContent = FileUtils.readFileToString( policyFile , "UTF-8" );
				WebConfigService webConfigService = new WebConfigService(policyContent);
				//执行策论的内容
				webConfigService.changeConfig();
				log.info("重新起动定时任务成功!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
		}
		log.info("policy.xml没有更新内容!");
		 
		 
		
	}

}
 