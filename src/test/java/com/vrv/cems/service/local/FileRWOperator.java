/**   
* @Title: FileRWOperator.java 
* @Package com.vrv.cems.service.local 
* @Description: TODO(用一句话描述该文件做什么) 
* @author tangtieqiao
		   tangtieqiao@vrvmail.com.cn
* @date 2015年9月16日 下午7:29:22 
* @version V1.0   
*/
package com.vrv.cems.service.local;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/** 
 * @ClassName: FileRWOperator 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author tangtieqiao
			tangtieqiao@vrvmail.com.cn
 * @date 2015年9月16日 下午7:29:22 
 *  
 */
public class FileRWOperator {
	private static Log logger=LogFactory.getLog(FileRWOperator.class);

	private File file;
	
	private final ReadWriteLock lock =new ReentrantReadWriteLock();
	
	public FileRWOperator(File file)
	{
		this.file=file;
	}
	
	//读方法
	public void Read(String opName)
	{
		FileInputStream fis = null;
		try {
			//先上读锁
			 lock.readLock().lock();
			fis = new FileInputStream(file);
            logger.info("Reader is"+opName+"read currentThread.name"+Thread.currentThread().getName()); 
			byte[] buf = new byte[1024];
			StringBuffer sb = new StringBuffer();
			while ((fis.read(buf)) != -1) {
				sb.append(new String(buf, "utf-8"));
				buf = new byte[1024];
			}
			logger.info(sb.toString());
			
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		finally
		{
			if(fis!=null)
			{
				try {
					fis.close();
					//对读操作解锁
					lock.readLock().unlock();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}
	
	//写方法
	public void Write(String opName)
	{	
		FileOutputStream out = null;
		try
		{	
			 if(!file.exists())  
	                file.createNewFile(); 
			 //上写锁
			 lock.writeLock().lock();
			  out=new FileOutputStream(file);        
	            logger.info("Writer is"+opName+"write currentThread.name"+Thread.currentThread().getName()); 
	            for(int i=1;i<=10;i++){            	
	                StringBuffer sb=new StringBuffer();  
	                sb.append("这是第"+i+"行，应该没啥错哈 ");  
	                out.write(sb.toString().getBytes("utf-8")); 
	                out.flush();
	                Thread.sleep(1000);
	            }  	            
	          
		}
		catch(FileNotFoundException e) {    
            e.printStackTrace();    
        } catch (IOException e) {    
            e.printStackTrace();    
        } catch (InterruptedException e) {    
            e.printStackTrace();    
        }
		finally
		{
			if(out!=null)
			{
				try {
					out.close();
					  //解开写锁
		            lock.writeLock().unlock();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		
	}
	

}
