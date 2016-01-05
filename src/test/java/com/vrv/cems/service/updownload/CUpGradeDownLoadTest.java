package com.vrv.cems.service.updownload; 

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
 *          创建时间：2015年4月17日 下午2:04:21 
 */
public class CUpGradeDownLoadTest {
	@Test
	public void test(){
		Map<String, String> httpParams = new HashMap<String, String>();
		httpParams.put("maxCode", IUpdownloadService.SERVICE_CODE);
		httpParams.put("minCode", "2");
		httpParams.put("data", "{"
		 		+ "osType:'windows',"
		 		+ "productType:'vdp',"
		 		+ "filePath:'cUpgrade/base/vdp/windows/35.0/UpgradeIndex.xml.enc.zip',"
		 		+ "devOnlyId:'31f77825c62498111ee1a58586ac105b',"
		 		+ "contentType:'1',"
		 		+ "size:'0',"
		 		+ "type:'base'}"  );
//		String jdata ={"size":"0","osType":"windows","productType":"vdp","filePath":"cUpgrade/base/vdp/windows/35.0/UpgradeIndex.xml.enc.zip","devOnlyId":"31f77825c62498111ee1a58586ac105b","contentType":"1","type":"base"}";
		HttpPostRequest httpPostRequest = new HttpPostRequest();
		httpPostRequest.setParams( httpParams );
		httpPostRequest.setUrl("http://localhost:8080/CEMS-SERVICE-UPDOWNLOAD/udload");
		String requestBody = httpPostRequest.doRequest();
		System.out.println( "eeeeeee");
		System.out.println( requestBody );
	}
	
	
	@Test
	public void test2() throws IllegalStateException, IOException {
		String data = "{"
						+"size:'0',"
						+"osType:'windows',"
						+"productType:'vdp',"
						+"filePath:'cUpgrade/base/vdp/windows/35.0/UpgradeIndex.xml.enc.zip',"
						+"devOnlyId:'31f77825c62498111ee1a58586ac105b',"
						+"contentType:'1',"
						+"type:'base'}";
		
		HttpResponse connect = HttpClientUtils.connect("http://192.168.119.220:18081/CEMS-SERVICE-UPDOWNLOAD/udload?maxCode="+
				IUpdownloadService.SERVICE_CODE+"&minCode=2", 
				ByteBufferUtils.string2ByteBuffer(data));
		System.out.println(ByteBufferUtils.inputStream2ByteArray(connect.getEntity().getContent()));
	}
	
	
}
 