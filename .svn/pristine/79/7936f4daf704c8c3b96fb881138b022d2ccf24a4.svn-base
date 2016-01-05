package com.vrv.cems.service.updownload.util; 

import java.io.FileInputStream;   
import java.io.FileNotFoundException;   
import java.io.FileOutputStream;   
import java.io.IOException;   
import java.util.Properties; 

import org.apache.log4j.Logger;

import com.vrv.cems.service.updownload.business.CupGradeDownLoadProcess;


/** 
 *   <B>说       明</B>:	更新配置文件的工具类
 *
 * @author  作  者  名：zhaodj<br/>
 *		   E-mail ：zhaodongjie@vrvmail.com.cn
 
 * @version 版   本  号：V1.0<br/>
 *          创建时间：2015年7月2日 上午10:31:57 
 */
/**  
* 实现对Java配置文件Properties的读取、写入与更新操作  
*/  
public class PropertyUtils {   
    
	private static final Logger logger = Logger.getLogger(CupGradeDownLoadProcess.class);
	  
    /**  
     * 增加属性文件值  
     *   
     * @param key  
     * @param value  
     */  
    public static void addProperties(String key, String value, String file) {  
        Properties iniFile = getProperties(file);  
        FileOutputStream oFile = null;  
        try {  
            iniFile.put(key, value);  
            oFile = new FileOutputStream(file, true);  
            iniFile.store(oFile, "modify properties file");  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (oFile != null) {  
                    oFile.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    /**  
     * 读取配置文件  
     *   
     * @return  
     */  
    public static Properties getProperties(String filePath) {  
        Properties pro = null;  
        // 从文件mdxbu.properties中读取网元ID和模块ID信息  
        FileInputStream in = null;  
        try {  
            in = new FileInputStream(filePath);  
            pro = new Properties();  
            pro.load(in);  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return pro;  
    }  
  
    /**  
     * 保存属性到文件中  
     *   
     * @param pro  
     * @param file  
     */  
    public static void saveProperties(Properties pro, String file) {  
        if (pro == null) {  
            return;  
        }  
        FileOutputStream oFile = null;  
        try {  
            oFile = new FileOutputStream(file, false);  
            pro.store(oFile, "modify properties file");  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (oFile != null) { 
                	oFile.flush(); 
                    oFile.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    /**  
     * 修改属性文件  
     *   
     * @param key  
     * @param value  
     */  
    public static void updateProperties(String key, String value, String file) {  
        // key为空则返回  
        if (key == null || "".equalsIgnoreCase(key)) {  
            return;  
        }  
        Properties pro = getProperties(file);  
        if (pro == null) {  
            pro = new Properties();  
        }  
        pro.put(key, value);  
  
        // 保存属性到文件中  
        saveProperties(pro, file);  
    }  
  
    
}   
 