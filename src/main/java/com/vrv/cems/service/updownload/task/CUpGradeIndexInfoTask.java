package com.vrv.cems.service.updownload.task; 
import java.nio.ByteBuffer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.vrv.cems.service.base.bean.ResultMsgBean;
import com.vrv.cems.service.base.interfaces.ICupgradeService;
import com.vrv.cems.service.base.util.ByteBufferUtils;
import com.vrv.cems.service.updownload.bean.CrcMark;
import com.vrv.cems.service.updownload.service.CUpGradeService;
import com.vrv.voa.client.ServiceFactory;
/** 
 *   <B>说       明</B>: 用于下载 补丁 端的索引文件
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 *
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月26日 下午2:45:12 
 */
public class CUpGradeIndexInfoTask implements Job { 
	private static final Logger LOGGER = Logger.getLogger(CUpGradeIndexInfoTask.class); 
	
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			//组装参数
			JSONArray jsonArray = new JSONArray(); 		
			jsonArray.add( ( new JSONObject()).accumulate("type", "0") );
			jsonArray.add( ( new JSONObject()).accumulate("type", "1") );
			//调用 
			ICupgradeService upCupgradeService = ServiceFactory.getService( ICupgradeService.class );
			String paramJson = jsonArray.toString() ;
			if( LOGGER.isInfoEnabled() ){
				LOGGER.info( "输入参数信息："+paramJson );
			}
			ByteBuffer inParam = ByteBuffer.wrap( paramJson.getBytes() );
			ByteBuffer outResult = upCupgradeService.getDataTS(ICupgradeService.SERVICE_CODE ,  ICupgradeService.UPGRADE_INFO,
					"",false,inParam,false,"",0); 
			//转化结果
			String result = ByteBufferUtils.byteBuffer2String(outResult);
			if( LOGGER.isInfoEnabled() ){
				LOGGER.info( "输出结果信息："+result );
			}
			if(StringUtils.isNotBlank(result)){
				ResultMsgBean resultMsgBean = (ResultMsgBean) JSONObject.toBean(JSONObject.fromObject(result), ResultMsgBean.class);
				JSONArray resultJsonArray = JSONArray.fromObject( resultMsgBean.getJdata() );
				if(resultJsonArray != null){
					for (int i = 0; i < resultJsonArray.size() ; i++) {
						JSONObject resultJsonObject = resultJsonArray.getJSONObject( i );
						CrcMark indexCrcType = null ;
						switch ( resultJsonObject.getInt("type") ) {
							case 0: //基础升级包
								indexCrcType = CrcMark.BASE_CUPGRADE ;
								break;
							case 1://定制升级包
								indexCrcType = CrcMark.CUSTOM_CUPGRADE ;
								break;
						}
						if( indexCrcType == null ){
							continue;
						}
						CUpGradeService cUpGradeService = new CUpGradeService(); 
						cUpGradeService.pullIndexInfo( resultJsonObject.getString("crc"), resultJsonObject.getString("url"),  indexCrcType );
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("拉取升级端索引信息 失败", e );
		}
	} 

}
 