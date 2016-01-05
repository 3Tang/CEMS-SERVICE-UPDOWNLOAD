package com.vrv.cems.service.updownload.service; 

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sys.common.fastdfs.FastDFSClient;
import com.vrv.cems.service.updownload.bean.CrcMark;
import com.vrv.cems.service.updownload.bean.PatchIndex;
import com.vrv.cems.service.updownload.cache.IndexCrcCache;
import com.vrv.cems.service.updownload.cache.PatchIndexCache;
import com.vrv.cems.service.updownload.config.SystemConfig;
import com.vrv.cems.service.updownload.factory.FastDFSClientFactory;

/** 
 *   <B>说       明</B>:用于提取 补丁 服务 推及拉的操作
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月26日 下午3:35:46 
 */
public class PatchService {
	private String crcParam ;
	private String urlParam ;
	private final String patchIndexPath ; 
	private Map<String, PatchIndex> updatePatchMap = new ConcurrentHashMap<String, PatchIndex>();
	private IndexCrcCache indexCrcCache = new IndexCrcCache();
	private PatchIndexCache patchIndexCache = new PatchIndexCache();
	private static final Logger log = Logger.getLogger(PatchService.class);
	public PatchService(String crc, String url) { 
		this.crcParam = crc;
		this.urlParam = url;
		String parentFilePath = SystemConfig.getIndexPathPre(); 
		patchIndexPath = parentFilePath + "update-patch.xml"; 
	}
	/**
	 * 接收 补丁服务推送的xml信息
	 */
	public void pushIndexInfo(){
		try {
			 String currentIndexCrc = indexCrcCache.get(CrcMark.PATCH);
			 log.info("【update-patch.xml】的CRC对比-----接到的:"+crcParam+"=====本地CRC:"+currentIndexCrc);
			 if( StringUtils.isNotBlank(currentIndexCrc) && crcParam.equalsIgnoreCase( currentIndexCrc )){
				 log.info("文件【update-patch.xml】CRC值未发生变化");
				 return ;
			 }
			 
			 //1.删除本地保存的update-patch.xml
			 log.info("1.删除本地保存的update-patch.xml,路径为:"+patchIndexPath);
			 File batchIndexFile = new File( patchIndexPath );  
			 if( batchIndexFile.exists() ){
				 FileUtils.deleteQuietly( batchIndexFile );
			 }
			 
			 //2.从FastDFS下载最新的update-patch.xml
			 log.info("2.从FastDFS:【"+urlParam+"】下载最新的update-patch.xml");
			 FastDFSClient fastDFSClient = FastDFSClientFactory.createFastDFSClient();
			 byte[] downLoadByte = fastDFSClient.downloadFile(urlParam);
			 if(downLoadByte == null){
				 log.error("从url:["+urlParam+"]下载的文件为空");
				 return;
			 }
			 try{
				 //把下载的内容放到本地路径下
				 FileUtils.writeByteArrayToFile( batchIndexFile ,downLoadByte );
			 } catch (Exception e){
				 log.error("拷贝update-patch.xml内容到文件报错!", e);
			 }
			 
			 //3.将新的update-patch.xml中的文件信息解析到Map中
			 log.info("3.将新的update-patch.xml中的文件信息解析到Map中");
			 parseUpdatePath();
			 
			 //4.同缓存 中的数据做 对比文件,过滤出需要更新的补丁
			 log.info("4.同缓存 中的数据做 对比文件,过滤出需要更新的补丁");
			 comparePatch();
			 
			 //5.根据Map中的新补丁信息、下载补丁
			 log.info("5.根据Map中的新补丁信息、下载补丁");
			 Iterator<PatchIndex> updatePatchIterator = updatePatchMap.values().iterator();
			 while( updatePatchIterator.hasNext() ){
				 PatchIndex updatePatch = updatePatchIterator.next();
				 byte[] destFileBytes = fastDFSClient.downloadFile( updatePatch.getUrl() );
				 if( destFileBytes == null ){
					 log.info("文件【"+updatePatch.getName()+"】不存在,路径为:"+updatePatch.getUrl());
					 continue;
				 }
				 File destFile = new File(SystemConfig.getPatchPathPre()+updatePatch.getPath());
				 //先删除旧补丁
				 FileUtils.deleteQuietly( destFile );
				 if( !destFile.getParentFile().exists() ){
					 destFile.getParentFile().mkdirs();
				 }
				 if( destFile.isDirectory() ){
					 destFile.mkdir();
				 }else{
					 FileUtils.writeByteArrayToFile( destFile , destFileBytes);
					 log.info(" 5.1补丁保存路径为:"+destFile.getPath());
				 } 
				 // 把最新的补丁信息放到缓存中
				 patchIndexCache.put( updatePatch );
			 } 
			 //6.更新update-patch的CRC值
			 log.info("6.更新update-patch的CRC值");
			 indexCrcCache.put( CrcMark.PATCH , crcParam );
			 patchIndexCache.flush();
		} catch (Exception e) {
			log.error("接或收补丁服务出现错误",e);
			throw new RuntimeException( e ) ;
		}
	}
	/**
	 * 从 补丁服务中拉取信息
	 */
	public void pullIndexInfo(){
		pushIndexInfo();
	}
	/**
	 *  解析update-patch.xml
	 */
	private void parseUpdatePath(){
		try {
			 SAXReader saxReader = new SAXReader();
			 Document document = saxReader.read( new File( patchIndexPath ) );
			 Element rootElement = document.getRootElement();
			 @SuppressWarnings("unchecked")
			 Iterator<Element> elementIterator = rootElement.elementIterator("file");
			 while( elementIterator.hasNext() ){
				 Element element = elementIterator.next();
				 PatchIndex updatePatch = new PatchIndex();
				 updatePatch.setName( element.element("name").getText() );
				 updatePatch.setCrc( element.element("crc").getText() );
				 updatePatch.setPath( element.element("path").getText() );
				 updatePatch.setUrl( element.element("url").getText() );
				 updatePatchMap.put(updatePatch.getName() , updatePatch);
			 }
		} catch (Exception e) {
			log.error("读取最新update-patch.xml文件有误",e);
			throw new RuntimeException( e ) ;
		}
	}
	
	/**
	 * 比较索引文件
	 */
	private void comparePatch(){ 
		
		//Map中的 最新补丁信息
		Iterator<String> keySet = updatePatchMap.keySet().iterator() ;
		List<String> deleteKey = new LinkedList<String>();
		while( keySet.hasNext()){
			//以补丁名称作为key,补丁名也是唯一的
			String key = keySet.next();
			PatchIndex updatePatch = updatePatchMap.get( key );
			PatchIndex cacheUpdatePatch = patchIndexCache.get(updatePatch);
			if( updatePatch.equals( cacheUpdatePatch )  ){
				deleteKey.add( key );
			}
		}
		Iterator<String> iteratorKey = deleteKey.iterator();
		
		//保证map中存的补丁信息是本地缓存中不存在的
		while( iteratorKey.hasNext() ){
			updatePatchMap.remove( iteratorKey.next() );
		}
	}
	
}
 