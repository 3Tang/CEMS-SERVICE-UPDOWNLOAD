package com.vrv.cems.service.updownload; 

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.junit.Test;

import com.vrv.cems.service.base.interfaces.IUpdownloadService;
import com.vrv.cems.service.base.util.ByteBufferUtils;
import com.vrv.cems.service.base.util.HttpClientUtils;
import com.vrv.cems.service.updownload.util.StreamStingUtils;

/** 
 *   <B>说       明</B>：补丁服务推送内容测试类
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年4月17日 下午2:08:43 
 */
public class PatchServiceTest {
	@Test
	public void test() throws IllegalStateException, IOException{
		String data= "{url:'g1/M00/25/29/wKgAGFTL5pGAU0G3AAACOhghcI0618.xml',crc:'edbc45e4'}";
		
		HttpResponse connect = HttpClientUtils.connect("http://192.168.0.132:8080/CEMS-SERVICE-UPDOWNLOAD/udload?maxCode="+
				IUpdownloadService.SERVICE_CODE+"&minCode="+IUpdownloadService.PATCH_XML, 
				ByteBufferUtils.string2ByteBuffer(data));
		InputStream inputStream = connect.getEntity().getContent();
		String result = StreamStingUtils.getStreamString(inputStream);
		System.out.println("返回结果："+result);
	}
}
 