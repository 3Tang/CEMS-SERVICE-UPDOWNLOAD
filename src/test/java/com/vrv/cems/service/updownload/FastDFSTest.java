package com.vrv.cems.service.updownload; 

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.sys.common.fastdfs.FastDFSClient;
import com.sys.common.fastdfs.FastDFSClientException;
import com.vrv.cems.service.updownload.factory.FastDFSClientFactory; 

/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年4月17日 下午2:07:47 
 */
public class FastDFSTest {
	@Test
	public void uploadFile() throws FastDFSClientException{
		FastDFSClient fastDFSClient = FastDFSClientFactory.createFastDFSClient();
		String filePath = FastDFSTest.class.getClassLoader().getResource("update-patch.xml").getFile();
		System.out.println(  filePath );
		File uploadFile = new File( filePath ) ;
		String aow = fastDFSClient.uploadFile( uploadFile ) ;
		System.out.println( aow );
	} 
	
	@Test
	public void downloadFile() throws FastDFSClientException
	{
		//g2/M01/F0/81/wKgADVS4Js6AViqvADtTtuNZph8736.xml
		FastDFSClient fastDFSClient = FastDFSClientFactory.createFastDFSClient();
		String urlPath="g1/M00/15/81/wKh2v1aBC8uAbVzLAAYmpSX7k44021.dat";
		String filePath="E:/app/test.dat";
		byte[] fileBytes=fastDFSClient.downloadFile(urlPath);
		FileOutputStream fout;
		try {
			File file=new File(filePath);
			file.setWritable(true);
			fout = new FileOutputStream(file);
			fout.write(fileBytes);
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		

	}
}
 