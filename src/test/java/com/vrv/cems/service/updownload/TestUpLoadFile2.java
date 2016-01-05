/**   
* @Title: TestUpLoadFile2.java 
* @Package com.vrv.cems.service.updownload 
* @Description: TODO(用一句话描述该文件做什么) 
* @author tangtieqiao
		   tangtieqiao@vrvmail.com.cn
* @date 2015年9月7日 下午3:57:53 
* @version V1.0   
*/
package com.vrv.cems.service.updownload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.vrv.cems.service.base.interfaces.IUpdownloadService;

/** 
 * @ClassName: TestUpLoadFile2 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author tangtieqiao
			tangtieqiao@vrvmail.com.cn
 * @date 2015年9月7日 下午3:57:53 
 *  
 */
public class TestUpLoadFile2 {

	@Test
	public void testUpLoadFile()
	{
		   String filePath="D:/test.txt";
		   File targetFile = new File(filePath);
		   FileInputStream fs=null;
		   try {
			 fs=new FileInputStream(targetFile);
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		   
		 String  targetURL = "http://localhost:8080/CEMS-SERVICE-UPDOWNLOAD/upFile"; //servleturl
		 
		 
		  HttpClient httpclient = new DefaultHttpClient();  
          
	        try {  
	      
	            HttpPost httppost = new HttpPost(targetURL);  
	              
	            FileBody bin = new FileBody(targetFile);  
	                
	              
	            String maxCode=IUpdownloadService.SERVICE_CODE;
	            String minCode=IUpdownloadService.UPGRADE_INFO;
	            String data="{"
			    		+"devOnlyId:'8068d280c73041268cb1e16d4db7a184',"
			    		+"userOnlyId:'26d513154d13111a15a15adfeer5e',"//用户属性相关
			    		+"osType:'windows/Linux',"
			    		+"productType:'vdp',"
			    		+"fileName:'test.txt',"//文件的完整名称(包括文件类型)
			    		+"crc:'3e4i2o'}";
	            
	            MultipartEntity reqEntity = new MultipartEntity();  
	              
	            
	            reqEntity.addPart("maxCode", new StringBody(maxCode));
	            reqEntity.addPart("minCode", new StringBody(minCode));
	            reqEntity.addPart("data", new StringBody(data));
	            reqEntity.addPart("file", bin);
	           /* {
	            	   "devOnlyId":"8068d280c73041268cb1e16d4db7a184",
	            	   "userOnlyId":”26d513154d13111a15a15adfeer5e”,//用户属性相关
	            	   "productType":"vdp",
	            	   "fileName"："UpGradePack.zip"，//文件的完整名称(包括文件类型)
	            	   "ip"："192.168.118.124"，//上传设备的ip地址
	            	   "crc":"3e4i2o",//文件流的CRC值
	            	}*/

	            httppost.setEntity(reqEntity);  
	              
	            HttpResponse response = httpclient.execute(httppost);  
	              
	                  
	            int statusCode = response.getStatusLine().getStatusCode();  
	              
	                  
	            if(statusCode == HttpStatus.SC_OK){  
	                      
	                System.out.println("服务器正常响应.....");  
	                  
	                HttpEntity resEntity = response.getEntity();  
	                  
	                  
	                System.out.println(EntityUtils.toString(resEntity));//httpclient自带的工具类读取返回数据  
	                  
	                  
	                  
	                System.out.println(resEntity.getContent());     
	  
	                EntityUtils.consume(resEntity);  
	            }  
	                  
	            } catch (ParseException e) {  
	                // TODO Auto-generated catch block  
	                e.printStackTrace();  
	            } catch (IOException e) {  
	                // TODO Auto-generated catch block  
	                e.printStackTrace();  
	            } finally {  
	                try {   
	                    httpclient.getConnectionManager().shutdown();   
	                } catch (Exception ignore) {  
	                      
	                }  
	            } 
		 
	}
}
