package com.vrv.cems.service.updownload; 

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.junit.Test;

import com.vrv.cems.service.base.interfaces.IUpdownloadService;
import com.vrv.cems.service.base.util.ByteBufferUtils;
import com.vrv.cems.service.base.util.HttpClientUtils;

/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年4月17日 下午2:10:13 
 */
public class PatchDownLoadTest {
	@Test
	public void test(){
		Map<String, String> params = new HashMap<String, String>();
		params.put("maxCode", IUpdownloadService.SERVICE_CODE);
		params.put("minCode", "3");
		params.put("data", "{osType:'window',filePath:'patch/index/PackIndex.ocx',devOnlyId:'05sao',contentType:0}" );
//		params.put("data", "dataVales" );
		String data = "{'osType':'window','filePath':'patch/index/PackIndex.ocx','devOnlyId':'05sao','contentType':'0'}";
//		HttpPostRequest httpPostRequest = new HttpPostRequest();
//		httpPostRequest.setParams( params );
//		httpPostRequest.setUrl("http://localhost:8080/CEMS-SERVICE-UPDOWNLOAD/udload");
//		String result = httpPostRequest.doRequest();
		String maxCode = IUpdownloadService.SERVICE_CODE;
		int minCode = 3;
		HttpResponse response = HttpClientUtils.connect("http://localhost:8080/CEMS-SERVICE-UPDOWNLOAD/udload?maxCode="+maxCode+"&minCode="+minCode, ByteBufferUtils.string2ByteBuffer(data));
		HttpEntity httpEntity = response.getEntity();
		
		System.out.println( "eeeeeee");
	}
	
	
}
 