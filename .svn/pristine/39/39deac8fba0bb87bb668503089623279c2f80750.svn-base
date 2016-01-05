package com.vrv.cems.service.updownload.service; 
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.vrv.cems.service.updownload.bean.CUpGradeIndex;
import com.vrv.cems.service.updownload.cache.CUpGradeIndexCache;
import com.vrv.cems.service.updownload.util.PathParseUtil;

/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月29日 上午11:58:56 
 */
public class CUpGradeInfoService { 
	private String devOnlyId ;
	@SuppressWarnings("unused")
	private String osType ;
	private String productType ;
	private RangeService rangeService = new RangeService();
	private CUpGradeIndexCache cUpGradeIndexCache = new CUpGradeIndexCache();
	public CUpGradeInfoService(String devOnlyId, String osType,String productType){
		this.productType  = productType;
		this.devOnlyId  = devOnlyId;
		this.osType  = osType;
	}
	public String getInfo(){
		//利用producttype 去ehcache 中查询数据 
		List<CUpGradeIndex> cUpGradeIndexs ;
		if( "all".equals( productType )){
			cUpGradeIndexs = cUpGradeIndexCache.queryAll();
		}else{
			cUpGradeIndexs = cUpGradeIndexCache.queryByProductType( productType );
		}
		//根据  devonlyid 进行 判断  
		if( cUpGradeIndexs == null || cUpGradeIndexs.size() ==0 ){
			return "";
		}
		Map<String, CUpgradeInfoReturn > cuMap = new HashMap<String, CUpgradeInfoReturn>();
		int size = cUpGradeIndexs.size();
		Random random = new Random();
		for (int i = 0; i < size ; i++) {
			if( !rangeService.existByOrg(devOnlyId)){
				continue;
			}
			CUpGradeIndex cUpGradeIndex = cUpGradeIndexs.get( i );
			//验证 是否 在 此 区域 未实现  以及 是否 升级 
			int j = random.nextInt( 2);
			CUpgradeInfoReturn cupInfoReturn = new CUpgradeInfoReturn( PathParseUtil.getProductType( cUpGradeIndex.getPath() ),  j ); //目前先写
			if( PathParseUtil.isCustom( cUpGradeIndex.getPath() )){
				cupInfoReturn.setCustomVersion( cUpGradeIndex.getVersion() );
			}else{
				cupInfoReturn.setBaseVersion( cUpGradeIndex.getVersion() );
			}
			cuMap.put( PathParseUtil.getName( cUpGradeIndex.getPath() ) ,  cupInfoReturn );
		}
		//组装 数据 
		StringBuilder cupGradeinfo = new StringBuilder();
		cupGradeinfo.append("{");
		cupGradeinfo.append("\"probeTime\":100");
		if( cuMap.size() == 0){
			return cupGradeinfo.append(",\"data\":[]}").toString();
		}
		cupGradeinfo.append(",\"data\":[");
		Iterator<CUpgradeInfoReturn> infoReturns = cuMap.values().iterator() ;
		boolean isWhile = true ;
		do {
			CUpgradeInfoReturn cupgradeInfo = infoReturns.next();
			cupGradeinfo.append("{");
			cupGradeinfo.append( "\"productType\":\""+ cupgradeInfo.getProductType() +"\"" );
			cupGradeinfo.append( ",\"baseVersion\":\""+ (cupgradeInfo.getBaseVersion() == null ? "" : cupgradeInfo.getBaseVersion())+"\"" );
			cupGradeinfo.append( ",\"customVersion\":\""+ (cupgradeInfo.getCustomVersion() == null ? "" : cupgradeInfo.getCustomVersion()) +"\"" );
			cupGradeinfo.append( ",\"flag\":"+ cupgradeInfo.getFlag() +"" );
			cupGradeinfo.append("}");
			isWhile = infoReturns.hasNext() ; 
			if( isWhile == true ){
				cupGradeinfo.append(",") ;
			}
		} while (isWhile);
		cupGradeinfo.append("]}");
		return cupGradeinfo.toString();
	}
	private class CUpgradeInfoReturn{
		private String productType;
		private String baseVersion;
		private String customVersion;
		private int flag;
		
		public String getProductType() {
			return productType;
		} 
		public String getBaseVersion() {
			return baseVersion;
		}
		public void setBaseVersion(String baseVersion) {
			this.baseVersion = baseVersion;
		}
		public String getCustomVersion() {
			return customVersion;
		}
		public void setCustomVersion(String customVersion) {
			this.customVersion = customVersion;
		} 
		public int getFlag() {
			return flag;
		} 
		public CUpgradeInfoReturn(String productType,   int flag) { 
			this.productType = productType; 
			this.flag = flag;
		} 
	}
}
 