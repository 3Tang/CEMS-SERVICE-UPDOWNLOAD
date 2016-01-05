package com.vrv.cems.service.updownload; 

import org.junit.Ignore;
import org.junit.Test;

import com.vrv.cems.service.base.bean.cache.DeviceCache;
import com.vrv.cems.service.base.interfaces.ICacheService; 
import com.vrv.voa.client.ServiceFactory;

/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年5月7日 下午3:48:13 
 */
@Ignore
public class CacheTest {
	@Test
	public void testCache(){
		try {
			ICacheService iCacheService = ServiceFactory.getService( ICacheService.class );
			
			DeviceCache saveCache = new DeviceCache();
			saveCache.setDevOnlyId("014785236.54") ;
			saveCache.setIp("192.168.24.54");
			saveCache.setMask("255.0.0.0") ;
			saveCache.setOrganizationId("7joa;adfkfdslkfdsalkfsadk;l");
			iCacheService.saveDevice( ICacheService.SERVICE_CODE , ICacheService.SAVEDEVICE , saveCache );
			
			
			DeviceCache deviceCache = iCacheService.queryDeviceByDevOnlyId( ICacheService.SERVICE_CODE , ICacheService.QUERYDEVICEBYDEVONLYID ,  "014785236.54" );
			
			
			
			System.out.println(  deviceCache.getOrganizationId()  );
			System.out.println(  deviceCache.getIp() );
			System.out.println(  deviceCache.getMask()   );
			//return divice ;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
 