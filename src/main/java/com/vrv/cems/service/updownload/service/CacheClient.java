package com.vrv.cems.service.updownload.service; 

/** 
 *   <B>说       明</B>:  
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年1月28日 上午10:36:33 
 */
import org.apache.log4j.Logger;
import com.vrv.cems.service.base.bean.cache.DeviceCache;
import com.vrv.cems.service.base.interfaces.ICacheService;
import com.vrv.cems.service.updownload.bean.Device;
import com.vrv.voa.client.ServiceFactory;


/** 
 *   <B>说       明</B>: 缓存服务接口客户端测试类 CacheClientTest
 *
 * @author  作  者  名：代成<br/>
 *		    E-mail ：daicheng@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.20140815<br/>
 *          创建时间：2014年8月15日 17:01:41 
 */
public class CacheClient {
	private static final Logger LOGGER = Logger.getLogger(CacheClient.class); 
	public Device getDevice( String devOnlyId ) {
		//VRV-VDP-064-缓存数据结构设计-CEMS.doc
		//VRV-VDP-067-产品(项目)接口文档-CEMS-缓存服务接口.doc
		try {
			ICacheService iCacheService = ServiceFactory.getService( ICacheService.class );
			DeviceCache deviceCache = iCacheService.queryDeviceByDevOnlyId( ICacheService.SERVICE_CODE , ICacheService.QUERYDEVICEBYDEVONLYID ,  devOnlyId );
			//new Device( map.get("organizationId"), map.get("ip"), map.get("netWork") );
			
			if( deviceCache == null ){
				return null ;
			}
			if( deviceCache.getOrganizationId() == null && 
			    deviceCache.getIp() == null && 
			    deviceCache.getMask() == null ){
				return null ;
			}
			Device divice = new Device( deviceCache.getOrganizationId() , deviceCache.getIp() , deviceCache.getMask() );
			return divice ;
		} catch (Exception e) {
			LOGGER.error( "devOnlyId="+devOnlyId , e );
			return null ;
		}
		/*
		public static final String SERVER_IP = "192.168.0.133";
		public static final int SERVER_PORT = 8090;
		public static final int TIMEOUT = 30000;
		TTransport transport = null;
		transport = new TFramedTransport(new TSocket(SERVER_IP,
				SERVER_PORT, TIMEOUT));
		// 协议要和服务端一致
		TProtocol protocol = new TBinaryProtocol(transport);
		CacheService.Client client = new CacheService.Client(
				protocol);
		transport.open();
		//组装data对象
		HashData data = new HashData();
		data.setMaxCode(ICacheService.SERVICE_CODE);
		data.setMinCode(ICacheService.IS_HASH_DATA_EXIST_TS);
		data.setKey("device:deviceID");
		data.setKeyType(1);//保存类型;0-服务私有，1-公共（格式--“table:dataid”）
		//组装jdata值
		List<HashItem> jdata = new ArrayList<HashItem>(); 
		jdata.add(new HashItem("ip", "")); 
		jdata.add(new HashItem("organizationId", ""));
		jdata.add(new HashItem("netWork", ""));
		data.setJdata(jdata);  
		Map<String, String> aa = client.getHashDataTS(IPolicyService.SERVICE_CODE, ICacheService.GET_HASH_DATA_TS, data, null);*/ 
	}  
}