package com.vrv.cems.service.updownload.task; 

import java.nio.ByteBuffer;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger; 
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException; 

import com.vrv.cems.service.base.interfaces.IPatchService;
import com.vrv.cems.service.updownload.config.ServicePortConfig; 
import com.vrv.cems.service.updownload.service.PatchService; 
import com.vrv.cems.service.updownload.util.ByteBufferUtil;
import com.vrv.voa.client.ServiceFactory;

/** 
 *   <B>说       明</B>: 用于下载 补丁 端的索引文件
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月26日 下午2:45:12 
 */
public class PatchIndexInfoTask implements Job {
	private static final Logger LOGGER = Logger.getLogger(CUpGradeIndexInfoTask.class); 
	
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException { 
		String result ="";
		//组装 参数
		JSONObject paramJsonObject = new JSONObject(); 
		paramJsonObject.put("maxCode", ServicePortConfig.PATCH_MAXCODE );
		paramJsonObject.put("minCode", ServicePortConfig.PATCH_FILEINFO_MINCODE );  
		paramJsonObject.put("jdata", "[{crc:\"\"}]" );
		try { 
			 ByteBuffer inParam = ByteBuffer.wrap( paramJsonObject.toString().getBytes() );
			 IPatchService iPatchService =ServiceFactory.getService( IPatchService.class );
			 ByteBuffer buffer = iPatchService.getDataTS( ServicePortConfig.PATCH_MAXCODE , ServicePortConfig.PATCH_FILEINFO_MINCODE, "", false , inParam ,false,"",0);
			 result = ByteBufferUtil.byteBufferToString(buffer) ;
			 LOGGER.info( "拉取patch端信息："+result );
			 JSONObject patchJsonObject = JSONObject.fromObject( result ); 
			 //补丁端 处理失败时，本服务不做处理
			 if( "1".equals( patchJsonObject.getString("result") )){
				 return ;
			 }
			 patchJsonObject = patchJsonObject.getJSONArray("jdata").getJSONObject( 0 );
			 if( StringUtils.isBlank(  patchJsonObject.getString("crc") )){
				 return ;
			 }
			 //解析 补丁服务 返回能数 
			 PatchService patchService = new PatchService(patchJsonObject.getString("crc") ,patchJsonObject.getString("url"));
			 patchService.pullIndexInfo();
		} catch (Exception e) {
			LOGGER.error("result="+result, e );
		} 
	} 
}
 