package com.vrv.cems.service.updownload.service; 

import org.apache.commons.lang.StringUtils;

/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月29日 下午5:26:49 
 */
public class RangeService {
	/**
	 * 
	 * @param devOnlyId
	 * @return true 表示 验证通过 
	 * devOnlyId.hashCode()不能整除2就是在同一区域
	 */
	public boolean existByOrg( String devOnlyId){
		if( StringUtils.isBlank( devOnlyId )){
			return false ;
		}
		int hashCode = devOnlyId.hashCode() % 2 ;
		if( hashCode == 1 ){
			return true ;
		}
		return false ;
	} 
}
 