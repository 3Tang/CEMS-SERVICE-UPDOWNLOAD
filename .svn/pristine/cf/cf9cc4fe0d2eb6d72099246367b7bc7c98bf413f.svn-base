package com.vrv.cems.service.updownload; 

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.vrv.cems.service.base.interfaces.IUpdownloadService;

/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年4月17日 下午2:06:08 
 */
public class CUpGradeInfoTest {
	@Test
	public void test(){
		Map<String, String> httpParams = new HashMap<String, String>();
		httpParams.put("maxCode", IUpdownloadService.SERVICE_CODE);
		httpParams.put("minCode", "1");
		httpParams.put("data","{devOnlyId:'rwrerwewaaaaa23113123re',osType:'window',productType:'vdp'}" );
		
		HttpPostRequest httpPostRequest = new HttpPostRequest();
		httpPostRequest.setParams( httpParams );
		httpPostRequest.setUrl("http://localhost:8080/CEMS-SERVICE-UPDOWNLOAD/udload");
		String requestBody = httpPostRequest.doRequest();
		System.out.println( "eeeeeee");
		System.out.println( requestBody );
	}
}
 