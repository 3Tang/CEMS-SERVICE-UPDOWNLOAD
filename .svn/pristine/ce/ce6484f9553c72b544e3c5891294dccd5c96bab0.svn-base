package com.vrv.cems.service.updownload.util; 

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年4月2日 下午2:21:25 
 */
public class IPUtil {
	public static boolean checkSameSegment(String ip1,String ip2, String subNet){ 
         // 判断IPV4是否合法  
         if(!ipValidate(ip1)){  
              return false;  
         }  
         if(!ipValidate(ip2)){  
              return false;  
         }
         int ipValue1 = getIpValue(ip1);  
         int ipValue2 = getIpValue(ip2);  
         int mask = getIpValue( subNet );
         return (mask & ipValue1) == (mask & ipValue2);  
    }  
	 public static int getIpValue(String ipOrMask){  
          byte[] addr = getIpBytes(ipOrMask);  
          int address1  = addr[3] & 0xFF;  
          address1 |= ((addr[2] << 8) & 0xFF00);  
          address1 |= ((addr[1] << 16) & 0xFF0000);  
          address1 |= ((addr[0] << 24) & 0xFF000000);  
          return address1;  
     }  
	 public static byte[] getIpBytes(String ipOrMask){  
          try{  
               String[] addrs = ipOrMask.split("\\.");  
               int length = addrs.length;  
               byte[] addr = new byte[length];  
               for (int index = 0; index < length; index++){  
                    addr[index] = (byte) (Integer.parseInt(addrs[index]) & 0xff);  
               }  
               return addr;  
          }catch (Exception e){  
          }  
          return new byte[4];  
     }  
	private static boolean ipValidate(String addr){  
          if(StringUtils.isBlank( addr )){  
              return false;  
          }  
          return Pattern.matches("((\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})", addr.trim());  
    }  
	
}
 