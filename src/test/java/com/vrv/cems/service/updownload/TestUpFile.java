/**   
* @Title: TestUpFile.java 
* @Package com.vrv.cems.service.updownload 
* @Description: TODO(用一句话描述该文件做什么) 
* @author tangtieqiao
		   tangtieqiao@vrvmail.com.cn
* @date 2015年8月25日 下午4:35:30 
* @version V1.0   
*/
package com.vrv.cems.service.updownload;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.vrv.cems.service.base.interfaces.IUpdownloadService;

/** 
 * @ClassName: TestUpFile 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author tangtieqiao
			tangtieqiao@vrvmail.com.cn
 * @date 2015年8月25日 下午4:35:30 
 *  
 */
public class TestUpFile {

	@Test
	public void test()
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("maxCode", IUpdownloadService.SERVICE_CODE);
		params.put("minCode", "3");
		HttpPostRequest httpPostRequest = new HttpPostRequest();
		httpPostRequest.setParams( params );
		httpPostRequest.setUrl("http://localhost:8080/CEMS-SERVICE-UPDOWNLOAD/upFile");
		String requestBody = httpPostRequest.doRequest();
	}
}
