/**   
* @Title: TestUpLoadFile.java 
* @Package com.vrv.cems.service.updownload 
* @Description: TODO(用一句话描述该文件做什么) 
* @author tangtieqiao
		   tangtieqiao@vrvmail.com.cn
* @date 2015年8月25日 下午3:05:45 
* @version V1.0   
*/
package com.vrv.cems.service.updownload;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.junit.Test;

import com.vrv.cems.service.base.interfaces.IUpdownloadService;

/** 
 * @ClassName: TestUpLoadFile 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author tangtieqiao
			tangtieqiao@vrvmail.com.cn
 * @date 2015年8月25日 下午3:05:45 
 *  
 */
public class TestUpLoadFile {

	@Test
	public void testUpLoadFile()
	{
		String localFile="d:/test.txt";
		String URL_STR="";
		File file = new File(localFile);
		         PostMethod filePost = new PostMethod(URL_STR);
		          HttpClient client = new HttpClient();
		          
		          try {
		             Part[] parts = { new FilePart(file.getName(), file) };
		             filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
		             
		             client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		             
		             int status = client.executeMethod(filePost);
		             if (status == HttpStatus.SC_OK) {
		                 System.out.println("上传成功");
		             } else {
		                 System.out.println("上传失败");
		             }
		         } catch (Exception ex) {
		             ex.printStackTrace();
		         } finally {
		             filePost.releaseConnection();
		        }
	}
	
	
	
	@Test
	public void fileUpTest()
	{
		   String targetURL = null;// TODO 指定URL
		   File targetFile = null;// TODO 指定上传文件
		  
		   String filePath="D:/13.mp3";
		   targetFile = new File(filePath);
		   FileOutputStream fs=null;
		   try {
			 fs=new FileOutputStream(targetFile);
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		   
		   targetURL = "http://localhost:8080/CEMS-SERVICE-UPDOWNLOAD/upFile"; //servleturl
		   PostMethod filePost = new PostMethod(targetURL);
		   long fileLength= targetFile.length();
		   try
		   {
			  filePost.addParameter("maxCode", "00FF0900");
		    //通过以下方法可以模拟页面参数提交
		    filePost.setParameter("maxCode", "00FF0900");
		    filePost.setParameter("minCode", IUpdownloadService.UPGRADE_INFO);
		    filePost.setParameter("data","{"
		    		+"devOnlyId:'8068d280c73041268cb1e16d4db7a184',"
		    		+"userOnlyId:'26d513154d13111a15a15adfeer5e',"//用户属性相关
		    		+"type:'base/custom',"// base：基础版本，custom：定制版本
		    		+"osType:'windows/Linux',"
		    		+"productType:'vdp',"
		    		+"size:'0',"//表示数据流起始点，主要用于断点续传。//如果size为0表示从头开始 写文件；10进制
		    		+"fileName：'13.mp3',"//文件的完整名称(包括文件类型)
		    		+"crc:'3e4i2o'}");

		 
		    Part[] parts = {
		    	      new StringPart("maxCode", "00FF0900"),
		    	      new StringPart("minCode", IUpdownloadService.UPGRADE_INFO),
		    	      new StringPart("data","{"
		  		    		+"devOnlyId:'8068d280c73041268cb1e16d4db7a184',"
				    		+"userOnlyId:'26d513154d13111a15a15adfeer5e',"//用户属性相关
				    		+"type:'base/custom',"// base：基础版本，custom：定制版本
				    		+"osType:'windows/Linux',"
				    		+"productType:'vdp',"
				    		+"size:'0',"//表示数据流起始点，主要用于断点续传。//如果size为0表示从头开始 写文件；10进制
				    		+"fileName：'13.mp3',"//文件的完整名称(包括文件类型)
				    		+"crc:'3e4i2o'}"),
		    	      new FilePart(targetFile.getName(), targetFile) 
		    	  };
		    //FileItemFactory factory = new DiskFileItemFactory(); 
		    filePost.setRequestEntity(new MultipartRequestEntity(parts,filePost.getParams()));
		    HttpClient client = new HttpClient();
		   
		    client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		    int status = client.executeMethod(filePost);
		    
		    if (status == HttpStatus.SC_OK)
		    {
		     System.out.println("上传成功");
		     // 上传成功
		    }
		    else
		    {
		     System.out.println("上传失败");
		     // 上传失败
		    }
		   }
		   catch (Exception ex)
		   {
		    ex.printStackTrace();
		   }
		   finally
		   {
		    filePost.releaseConnection();
		   }
	}
}
