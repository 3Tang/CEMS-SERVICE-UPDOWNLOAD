/**   
 * @Title: TestResponseRec.java 
 * @Package com.vrv.cems.service.updownload 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author tangtieqiao
		   tangtieqiao@vrvmail.com.cn
 * @date 2015年8月25日 下午7:52:08 
 * @version V1.0   
 */
package com.vrv.cems.service.updownload;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

import com.vrv.cems.service.base.interfaces.IUpdownloadService;

/**
 * @ClassName: TestResponseRec
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tangtieqiao tangtieqiao@vrvmail.com.cn
 * @date 2015年8月25日 下午7:52:08
 * 
 */
public class TestResponseRec {

	@Test
	public void test() {
		/*Map<String, String> params = new HashMap<String, String>();
		params.put("maxCode", IUpdownloadService.SERVICE_CODE);
		params.put("minCode", "3");
		HttpPostRequest httpPostRequest = new HttpPostRequest();
		httpPostRequest.setParams(params);
		httpPostRequest
				.setUrl("http://localhost:8080/CEMS-SERVICE-UPDOWNLOAD/upFile");
		String requestBody = httpPostRequest.doRequest();*/
		
		String url="http://localhost:8080/CEMS-SERVICE-UPDOWNLOAD/test";
		
		HttpClient client = new HttpClient();
		
		PostMethod post = new PostMethod(url);
		
		 NameValuePair name = new NameValuePair( "name" , "ld" );  
	      NameValuePair pass = new NameValuePair( "password" , "ld" );  
	      post.setRequestBody( new NameValuePair[]{name,pass});  
	      try {
			int status = client.executeMethod(post);
		} catch (HttpException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}  
	      try {
			System.out.println(post.getResponseBodyAsStream());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}  
	      post.releaseConnection();  
	
	}
}
