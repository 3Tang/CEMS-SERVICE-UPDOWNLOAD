package com.vrv.cems.service.updownload.factory; 

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import com.sys.common.fastdfs.FastDFSClient;
import com.sys.common.fastdfs.FastDFSClientBuilder;
import com.sys.common.fastdfs.FastDFSClientException;

/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：dongyifei<br/>
 *		    E-mail ：dongyifei@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2014年8月22日 下午5:54:54 
 */
public class FastDFSClientFactory {
	public static FastDFSClient createFastDFSClient(){
		FastDFSClient fastDFSClient = null;
		try {
			FastDFSClientBuilder fastDFSClientBuilder = new FastDFSClientBuilder();
			//定位配置文件。
			URL url = FastDFSClientBuilder.class.getClassLoader().getResource("fastdfs.properties");
			File configFile = new File(url.toURI());
			//设置配置文件。
			fastDFSClientBuilder.setGlobalConfigFile(configFile);
			//构建fastDFSClient
			fastDFSClient = fastDFSClientBuilder.build();
		} catch (FastDFSClientException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return fastDFSClient;
	}
}
 