/**   
* @Title: FileWriteThread.java 
* @Package com.vrv.cems.service.local 
* @Description: TODO(用一句话描述该文件做什么) 
* @author tangtieqiao
		   tangtieqiao@vrvmail.com.cn
* @date 2015年9月14日 下午3:00:38 
* @version V1.0   
*/
package com.vrv.cems.service.local;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
 * @ClassName: FileWriteThread 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author tangtieqiao
			tangtieqiao@vrvmail.com.cn
 * @date 2015年9月14日 下午3:00:38 
 *  
 */
public class FileWriteThread implements Runnable {
	private static Log logger=LogFactory.getLog(FileWriteThread.class);
	/*
	* Title: run
	*Description:  
	* @see java.lang.Runnable#run() 
	*/
	@Override
	public void run() {
		File file=new File("D:/test333.txt");                	           
		try
		{	
			 if(!file.exists())  
	                file.createNewFile(); 
			 RandomAccessFile out = new RandomAccessFile(file, "rw");  
	            FileChannel fcout=out.getChannel();  
	            FileLock flout=null;  
	            while(true){    
	                try {  
	                    flout = fcout.tryLock();  
	                    break;  
	                } catch (Exception e) {  
	                     logger.info("有其他线程正在操作该文件，当前线程休眠1000毫秒");   
	                     Thread.sleep(1000);    
	                }  
	                  
	            }  
	            logger.info("write currentThread.name"+Thread.currentThread()  
	                    .getName()); 
	            for(int i=1;i<=10;i++){  
	            	
	                StringBuffer sb=new StringBuffer();  
	                sb.append("这是第"+i+"行，应该没啥错哈 ");  
	                out.write(sb.toString().getBytes("utf-8")); 
	                Thread.sleep(1000);
	            }  
	              
	            flout.release();  
	            fcout.close();  
	            out.close();  
	            out=null;  
		}
		catch(FileNotFoundException e) {    
            e.printStackTrace();    
        } catch (IOException e) {    
            e.printStackTrace();    
        } catch (InterruptedException e) {    
            e.printStackTrace();    
        }   
	}

}
