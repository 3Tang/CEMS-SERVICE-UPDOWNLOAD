package com.vrv.cems.service.updownload.service; 

import java.io.File; 
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;  
import java.util.Map;

import org.apache.commons.io.FileUtils; 
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sys.common.fastdfs.FastDFSClient; 
import com.sys.common.util.security.CRCUtils;
import com.vrv.cems.service.updownload.bean.CrcMark; 
import com.vrv.cems.service.updownload.bean.PolicyIndex;
import com.vrv.cems.service.updownload.cache.IndexCrcCache; 
import com.vrv.cems.service.updownload.config.SystemConfig;
import com.vrv.cems.service.updownload.factory.FastDFSClientFactory;  

/** 
 *   <B>说       明</B>:策略服务文件列表xml推送
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月26日 下午3:35:46 
 */
public class PolicyService {
	private String crcParam ;
	private String urlParam ;
	private final String policyIndexPath ; 
	private Map<String, PolicyIndex> updatePatchMap = new HashMap<String, PolicyIndex>();
	private IndexCrcCache indexCrcCache = new IndexCrcCache(); 
	private static final Logger log = Logger.getLogger(PolicyService.class);
	public PolicyService(String crc, String url) { 
		this.crcParam = crc;
		this.urlParam = url;
		String parentFilePath = SystemConfig.getIndexPathPre();
		policyIndexPath = parentFilePath + "update-policy.xml"; 
	}
	/**
	 * 接收 补丁服务的信息
	 */
	public void pushIndexInfo(){
		try {
			 String currentIndexCrc = indexCrcCache.get(CrcMark.POLICY);
			 if( crcParam.equals( currentIndexCrc )){
				 return ;
			 }
			 //更新 最新补丁端 的索引文件
			 File batchIndexFile = new File( policyIndexPath );  
			 if( batchIndexFile.exists() ){
				 FileUtils.deleteQuietly( batchIndexFile );
			 } 
			 FastDFSClient fastDFSClient = FastDFSClientFactory.createFastDFSClient();  
			 byte[] bytes = fastDFSClient.downloadFile( urlParam ) ; 
			 FileUtils.writeByteArrayToFile( batchIndexFile , bytes );
			 //将索引信息解析到Map中
			 parseUpdatePath(); 
			 //根据 变化点 做拉取、存储文件操作 
			 Iterator<PolicyIndex> updatePolicyIterator = updatePatchMap.values().iterator();
			 while( updatePolicyIterator.hasNext() ){
				 PolicyIndex updatePolicy = updatePolicyIterator.next();
				 File destFile = new File( SystemConfig.getPolicyPathPre()+updatePolicy.getPath());
  				 //验证CRC 是否一样
				 if( destFile.exists() ){
					 FileInputStream f = new FileInputStream( destFile ); 
					 String crc = String.valueOf( CRCUtils.getCRC32Value( f ) );
					 if( updatePolicy.getCrc().equals( crc ) ){
						 log.info("文件已存在并且CRC一致\t"+updatePolicy.getCrc());
						 continue ;
					 }
					 FileUtils.deleteQuietly( destFile );
				 }
				 byte[] destFileBytes = fastDFSClient.downloadFile( updatePolicy.getUrl() );
				 if( destFileBytes == null ){
					 log.info("文件不存在\t"+updatePolicy.getUrl());
					 continue;
				 }
				 if( !destFile.getParentFile().exists() ){
					 destFile.getParentFile().mkdirs();
				 }
				 if( destFile.isDirectory() ){
					 destFile.mkdir();
				 }else{
					 FileUtils.writeByteArrayToFile( destFile , destFileBytes);
				 }  
			 } 
			 indexCrcCache.put( CrcMark.POLICY , crcParam ); 
		} catch (Exception e) {
			log.error("接收策略服务出现错误",e);
			throw new RuntimeException( e ) ;
		}
	} 
	/**
	 *  
	 */
	private void parseUpdatePath(){
		try {
			 SAXReader saxReader = new SAXReader();
			 Document document = saxReader.read( new File( policyIndexPath ) );
			 Element rootElement = document.getRootElement();
			 @SuppressWarnings("unchecked")
			 Iterator<Element> elementIterator = rootElement.elementIterator("file");
			 while( elementIterator.hasNext() ){
				 Element element = elementIterator.next();
				 PolicyIndex updatePolicy = new PolicyIndex();
				 updatePolicy.setName( element.element("name").getText() );
				 updatePolicy.setCrc( element.element("crc").getText() );
				 updatePolicy.setPath( element.element("path").getText() );
				 updatePolicy.setUrl( element.element("url").getText() );
				 updatePatchMap.put(updatePolicy.getName() , updatePolicy);
			 }
		} catch (Exception e) {
			log.error("读取最新索引文件有误",e); 
		}
	} 
	
}
 