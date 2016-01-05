package com.vrv.cems.service.updownload.service; 

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sys.common.fastdfs.FastDFSClient;
import com.vrv.cems.service.updownload.bean.CUpGradeIndex;
import com.vrv.cems.service.updownload.bean.CrcMark;
import com.vrv.cems.service.updownload.cache.CUpGradeIndexCache;
import com.vrv.cems.service.updownload.cache.IndexCrcCache;
import com.vrv.cems.service.updownload.config.SystemConfig;
import com.vrv.cems.service.updownload.factory.FastDFSClientFactory;
import com.vrv.cems.service.updownload.util.PropertyUtils;
import com.vrv.cems.service.updownload.util.ZipFileUtil;

/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月27日 下午3:20:50 
 */
public class CUpGradeService {
	private static final Logger LOGGER = Logger.getLogger(PatchDownLoadService.class); 
	private CUpGradeIndexCache cUpGradeIndexCache = new CUpGradeIndexCache();
	private IndexCrcCache indexCrcCache = new IndexCrcCache();
	private FastDFSClient fastDFSClient = FastDFSClientFactory.createFastDFSClient();
	/**
	 * 接收 升级服务的信息
	*/
	public void pushIndexInfo( CUpGradeIndex cUpGradeIndex ){
		try {
			//验证 缓存 中是否与存在 相同的索引，或 相同的crc
			LOGGER.info("1.验证 信息在缓存中是否存在");
			LOGGER.info("@@FILPATH:"+cUpGradeIndex.getPath());
			CUpGradeIndex cGradeIndexCache = cUpGradeIndexCache.get( cUpGradeIndex );
			if( cGradeIndexCache != null ){
				if( cGradeIndexCache.equals( cUpGradeIndex )){
					LOGGER.info(" 1.1已存在");
					return ;
				}
				if( cGradeIndexCache.getCrc().equals( cUpGradeIndex.getCrc() )){
					LOGGER.info(" 1.1已存在");
					cUpGradeIndexCache.put( cUpGradeIndex );
					return ;
				}
			}
			//从fastdfs文件服务器中拉取文件 
			LOGGER.info("2.从FastDFS上下载文件:["+cUpGradeIndex.getUrl()+"]");
			byte[] zipBytes = fastDFSClient.downloadFile( cUpGradeIndex.getUrl() ) ;
			if( zipBytes == null ){
				LOGGER.info(" 2.1文件不存在:["+cUpGradeIndex.getUrl()+"]");
				return ;
			}
			
			LOGGER.info("zip文件路径"+ SystemConfig.getCupgradePathPre() +cUpGradeIndex.getPath());
			String pathUtl = null;
			if(cUpGradeIndex.getPath().startsWith("/cems")){
				pathUtl = cUpGradeIndex.getPath();
			}else{
				pathUtl = SystemConfig.getCupgradePathPre() +cUpGradeIndex;
			}
			LOGGER.info("@@@截取后zip文件路径"+pathUtl);
			File zipFile =  new File( pathUtl );
			if( !zipFile.getParentFile().exists() ){
				zipFile.getParentFile().mkdirs() ;
			}
			FileUtils.writeByteArrayToFile( zipFile , zipBytes , false );
			//解压文件
			LOGGER.info("3.解压文件到:["+zipFile.getParent());
			ZipFileUtil.doCompress( zipFile.getAbsolutePath() , zipFile.getParent() );
			
			//删除原先ZIP文件
			LOGGER.info("4.删除升级包:["+zipFile.getPath()+"]");
			FileUtils.deleteQuietly( zipFile );
			//刷新缓存
			
			cUpGradeIndexCache.put( cUpGradeIndex );
			
			//更新index.crc文件
			
			String parentFilePath = SystemConfig.getIndexPathPre();
			String locationCrc = parentFilePath + "index.crc"; 
			LOGGER.info("###开始更新index.crc文件，localtionCrc:"+locationCrc);
			upDateindexCrc(cUpGradeIndex,locationCrc);
			
		} catch (Exception e) {
			LOGGER.error( cUpGradeIndex == null ? "" : cUpGradeIndex.toString() , e );
		}
	}
	/**
	 * 
	 * 日期：		2015年7月2日
	 * 方法名称：	upDateindexCrc
	 * 文件名称：	CUpGradeService.java
	 * 返回类型:		void
	 * 作用：		TODO:跟新index文件的crc值
	 * 目标：		@param cUpGradeIndex
	 * 目标：		@param locationCrc
	 */
	private void upDateindexCrc(CUpGradeIndex cUpGradeIndex, String locationCrc) {
		String crc = cUpGradeIndex.getCrc();
		Integer type = cUpGradeIndex.getType();
		if(null == type){
			LOGGER.info("type不存在，不更新index.crc");
			return;
		}
		// /cems/index/update-
		LOGGER.info("###type存在："+type);
		// xx/xx/WEB-INFO/classes/
//		String url = CUpGradeService.class.getResource("/").getPath();
//		LOGGER.info(url);
//		String crcUrl = url.substring(0, url.lastIndexOf("/WEB-INF"));
//		LOGGER.info(crcUrl);
		LOGGER.info("###index.crc路径："+locationCrc);
		if(type == 0){
		    PropertyUtils.updateProperties("update-cupgrade-base", crc, locationCrc);
		}else if(type == 1){
			PropertyUtils.updateProperties("update-cupgrade-custom", crc, locationCrc);
		}

	}
	/**
	 * 从 升级服务中拉取信息
	 */
	public void pullIndexInfo( List<CUpGradeIndex> cUpGradeIndexList ){
		if( cUpGradeIndexList == null || cUpGradeIndexList.size() == 0){
			return ;
		}
		int size = cUpGradeIndexList.size();
		for (int i = 0; i < size ; i++) {
			pushIndexInfo( cUpGradeIndexList.get( i ) );
		}
	}
	/**
	 * 从客户端升级服务中拉取信息
	 */
	public void pullIndexInfo( String crc ,String url , CrcMark indexCrcType){
		try {
			if( crc.equals( indexCrcCache.get( indexCrcType ) )){
				LOGGER.info("文件["+url+"]未发生变化");
				return ;
			}
			//下载索引 文件 
			LOGGER.info("1.从["+url+"]下载索引文件");
			byte[] cupgradeIndexBytes = fastDFSClient.downloadFile( url );
			
			String xml = "";
			if(indexCrcType.getKey().equals("base_cupgrade")){
				xml = "update-cupgrade-base.xml";
			}else{
				xml = "update-cupgrade-custom.xml";
			}
			File updateCUpgradeBaseXml = new File(SystemConfig.getIndexPathPre()+xml);
			if(cupgradeIndexBytes == null){
				 LOGGER.error("从url:["+url+"]下载的文件为空");
				 return;
			 }
			 try{
				 //把下载的内容放到本地路径下
				 FileUtils.writeByteArrayToFile( updateCUpgradeBaseXml ,cupgradeIndexBytes );
			 } catch (Exception e){
				 LOGGER.error("拷贝update-cupgrade-base.xml内容到文件报错!", e);
			 }
			
			LOGGER.info("2.解析索引文件");
			List<CUpGradeIndex> cUpGradeIndexs = parseCUpGrade( cupgradeIndexBytes );
			if(CollectionUtils.isNotEmpty(cUpGradeIndexs)){
				LOGGER.info("/t2.1索引文件中升级包个数:"+cUpGradeIndexs.size());
			}else{
				LOGGER.info("/t2.1索引文件中内容为空!");
			}
			
			//更新 文件 及 缓存 
			LOGGER.info("3.更新本地索引文件和缓存");
			pullIndexInfo( cUpGradeIndexs );
			//更新 crc
			LOGGER.info("4.更新缓存中的文件CRC");
			indexCrcCache.put( indexCrcType , crc);
		} catch (Exception e) {
			String errorMessage = "crc="+crc+"\turl="+url+"\tCrcMark="+(indexCrcType == null ? "" :indexCrcType.toString());
			LOGGER.error( errorMessage , e );
		}
	}
	
	/**
	 * 解析索引文件
	 */
	private List<CUpGradeIndex> parseCUpGrade( byte[] cupgradeIndexBytes  ){
		List<CUpGradeIndex> cUpGradeIndexs = new LinkedList<CUpGradeIndex>();
		ByteArrayInputStream byteArrayInputStream  = null ;
		try {
			SAXReader saxReader = new SAXReader();
			byteArrayInputStream = new ByteArrayInputStream( cupgradeIndexBytes ) ;
			Document document = saxReader.read( byteArrayInputStream );
			Element rootElement = document.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> elements = rootElement.elements("file");
			for (int i = 0; i < elements.size() ; i++) {
				Element childElement = elements.get( i );
				CUpGradeIndex cUpGradeIndex = new CUpGradeIndex(); 
				cUpGradeIndex.setCrc( childElement.element("crc").getText() );
				cUpGradeIndex.setUrl( childElement.element("url").getText() );
				cUpGradeIndex.setPath( childElement.element("path").getText() );
				if(childElement.element("version") != null){
					cUpGradeIndex.setVersion( childElement.element("version").getText() );
				}
				Map<String, String> rangeMap = parseRange( childElement.element("range").getText() ); 
				cUpGradeIndex.setOrg( rangeMap.get( "org" ) );
				cUpGradeIndex.setIp( rangeMap.get( "ip" ) );
				cUpGradeIndexs.add( cUpGradeIndex );
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly( byteArrayInputStream );
		} 
		return cUpGradeIndexs ;
	}
	
	/**
	 * 解析发布范围
	 */
	private Map<String, String> parseRange( String range ){
		Map<String, String> rangeMap = new HashMap<String, String>(); 
		try {
			 Document document = DocumentHelper.parseText( range );
			 Element rootElement = document.getRootElement();
			 rangeMap.put("org", rootElement.element("devOrgObj").element("execute").getText() );
			 rangeMap.put("ip", rootElement.element("ipRangeObj").element("execute").getText() );
		} catch (Exception e) { 
			throw new RuntimeException( e );
		}
		 return rangeMap;
	}
}
 