package com.vrv.cems.service.updownload.service; 
 
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.sys.common.util.DateUtils;
import com.vrv.cems.service.updownload.bean.Device;
import com.vrv.cems.service.updownload.bean.DownLoadInfo;
import com.vrv.cems.service.updownload.util.IPUtil;

/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年4月1日 下午3:34:48 
 */
public class DownLoadInfoService {
	private DownLoadInfoService(){}
	private static volatile DownLoadInfoService downLoadInfoService;
	public static synchronized DownLoadInfoService getInstance(){
		if( downLoadInfoService == null ){
			downLoadInfoService = new DownLoadInfoService();
		}
		return downLoadInfoService; 
	}
	private static final Logger LOGGER = Logger.getLogger(DownLoadInfoService.class); 
	private Map<String, List<DownLoadInfo> > downLoadInfoMap = new ConcurrentHashMap<String,  List<DownLoadInfo> >( 103 );
	private RangeService rangeService = new RangeService();
	//存活时间 
	private int keepAliveDay = 5 ;
	//有效的IP范围
	private int ipRange = 10;
	/**
	 * 存储
	 * @param devOnlyIdParam
	 * @param filePath
	 */
	public void save( String devOnlyIdParam , String filePath ){
		if( LOGGER.isInfoEnabled() ){
			LOGGER.info("filePath="+filePath);
		}
		List<DownLoadInfo> downLoadInfoList = downLoadInfoMap.get( filePath );
		if( downLoadInfoList == null ){
			downLoadInfoList = new CopyOnWriteArrayList<DownLoadInfo>();
			downLoadInfoMap.put( filePath , downLoadInfoList );
		}
		Device deviceBean =  queryByDevOnlyId( devOnlyIdParam );
		if( deviceBean == null ){
			if( LOGGER.isInfoEnabled() ){
				LOGGER.info("从缓存中通过devOnlyId未能查找到相关设备;devOnlyIdParam="+devOnlyIdParam);
			}
			return ;
		}
		DownLoadInfo downLoadInfo = new DownLoadInfo();
		downLoadInfo.setDownloadTime( DateUtils.getCurrentTimestamp());
		downLoadInfo.setPath( filePath );
		downLoadInfo.setOrgId( deviceBean.getOrgId() );
		downLoadInfo.setIp( deviceBean.getIp() );
		downLoadInfo.setSubNet( deviceBean.getSubNet() );
		downLoadInfoList.add( downLoadInfo );
	}
	/**
	 * 通过devOnlyId 获取 组织机构ID 和IP 地址  ，并根据组织机构ID和IP地址 做以下验证
	 * 1、判断是否同一组织机构，根据  组织机构ID 验证
	 * 2、判断是否同一网段，根据  组织机构IP 验证，及默认网关
	 * 3、查找距离最近的多个IP地址 距离最近通过变量来判断取上下范围，并且是在某个时间段内，单位以天计算。
	 * 4、下载记录需要根据时间的变化 设置清除点。
	 * 5、区域判断
	 * @param devOnlyIdParam
	 * @param filePath
	 * @return 多个IP地址以逗号分隔 ,或 空 字符串
	 * 	 */
	public String getIps( String devOnlyId , String filePath){
		//判断 是否在同一区域
		if( ! rangeService.existByOrg( devOnlyId )){
			if( LOGGER.isInfoEnabled() ){
				LOGGER.info("devOnlyId 不在区域范围内");
			}
			return "" ;
		};
		List<DownLoadInfo> downLoadInfoList = downLoadInfoMap.get( filePath );
		if( downLoadInfoList == null || downLoadInfoList.size() == 0 ){
			return "" ;
		}
		//查找 
		Device device = queryByDevOnlyId( devOnlyId );
		if( device == null ){
			if( LOGGER.isInfoEnabled() ){
				LOGGER.info("从缓存中通过devOnlyId未能查找到相关设备");
			}
			return "" ;
		}
		//清空已过时数据 
		for (int i = 0; i < downLoadInfoList.size() ; i++) {
			 DownLoadInfo downLoadInfo = downLoadInfoList.get( i );
			 Calendar maxCalendar =  Calendar.getInstance();
			 maxCalendar.setTime( downLoadInfo.getDownloadTime() );
			 maxCalendar.add( Calendar.DAY_OF_MONTH, keepAliveDay );
			 
			 Calendar currentCalendar =  Calendar.getInstance();
			 
			 if( currentCalendar.compareTo( maxCalendar ) > 0 ){
				 downLoadInfoList.remove( i );
			 } 
		}
		
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < downLoadInfoList.size() ; i++) {
			DownLoadInfo downLoadInfo = downLoadInfoList.get( i );
			//验证是否同一组织机构
			if( !StringUtils.equals( downLoadInfo.getOrgId() , device.getOrgId() ) ){
				if( LOGGER.isInfoEnabled() ){
					LOGGER.info(downLoadInfo.getOrgId()+"与"+device.getOrgId()+"不属于同一组织机构");
				}
				continue;
			}
			//验证 是否同一网段
			if( !isSameSegment( downLoadInfo.getIp() ,downLoadInfo.getSubNet(), device.getIp(), device.getSubNet() )){
				if( LOGGER.isInfoEnabled() ){
					LOGGER.info(downLoadInfo.getIp()+"\t"+downLoadInfo.getSubNet()+device.getIp()+"\t"+device.getSubNet()+"不属于同一网段");
				}
				continue;
			}
			//验证 是否在范围内
			if( ! isRange( downLoadInfo.getIp() , device.getIp() )){
				if( LOGGER.isInfoEnabled() ){
					LOGGER.info(downLoadInfo.getIp()+"\t"+device.getIp()+"不属于区域内");
				}
				continue;
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("ip", downLoadInfo.getIp());
			jsonObject.put("downloadTime", downLoadInfo.getDownloadTime());
			jsonArray.add(jsonObject);
		}
		return jsonArray.toString() ;
	} 
	private Device queryByDevOnlyId( String devOnlyId ){
		CacheClient cacheClient = new CacheClient();
		return cacheClient.getDevice( devOnlyId );
	}
	/**
	 * 验证是否 在范围 内
	 * @param ip
	 * @param byIp
	 * @return true 范围内 ; false 范围外
	 */
	private boolean isRange( String ip , String byIp ){
		long ipNum = ipToLong( ip );
		long byIpNum = ipToLong( byIp );
		if( (byIpNum - ipRange) <= ipNum && ipNum <= (byIpNum+ipRange) ){
			return true ;
		}
		return false ;
	}
	/**
	 * 验证 是否属于 同一网段
	 * @param ip 
	 * @param subNet
	 * @param byIp
	 * @param bySubNet
	 * @return true 同一网段 ; false 不同网段
	 */
	private boolean isSameSegment( String ip ,String subNet ,  String byIp ,String bySubNet){
		if(! StringUtils.equals(subNet, bySubNet ) ){
			return false;
		}
		return IPUtil.checkSameSegment( ip ,  byIp , bySubNet) ;
	} 
	/**
     * IP转成数字类型
     * 
     * @param strIP
     * @return
     */
    private long ipToLong(String strIP) {
        long[] ip = new long[4];
        int position1 = strIP.indexOf(".");
        int position2 = strIP.indexOf(".", position1 + 1);
        int position3 = strIP.indexOf(".", position2 + 1);
        ip[0] = Long.parseLong(strIP.substring(0, position1));
        ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(strIP.substring(position3 + 1));
        // ip1*256*256*256+ip2*256*256+ip3*256+ip4
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }
}
 