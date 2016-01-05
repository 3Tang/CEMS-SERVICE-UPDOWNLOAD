/**   
 * @Title: FileReadThread.java 
 * @Package com.vrv.cems.service.local 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author tangtieqiao
		   tangtieqiao@vrvmail.com.cn
 * @date 2015年9月14日 下午2:58:22 
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

import com.vrv.cems.service.updownload.util.XmlOperator;

/**
 * @ClassName: FileReadThread
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tangtieqiao tangtieqiao@vrvmail.com.cn
 * @date 2015年9月14日 下午2:58:22
 * 
 */
public class FileReadThread implements Runnable {
	private static Log logger = LogFactory.getLog(FileReadThread.class);
	/*
	 * Title: runDescription:
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		File file = new File("D:/test333.txt");

		// 给该文件加锁
		RandomAccessFile fis = null;
		try {
			fis = new RandomAccessFile(file, "rw");
			FileChannel fcin = fis.getChannel();
			FileLock flin = null;
			logger.info("Read currentThread.name"+Thread.currentThread()  
	                    .getName()); 
			while (true) {
				try {
					flin = fcin.tryLock();
					break;
				} catch (Exception e) {
					logger.info("有其他线程正在操作该文件，当前线程休眠1000毫秒");
					Thread.sleep(1000);
				}
			}

			byte[] buf = new byte[1024];
			StringBuffer sb = new StringBuffer();
			while ((fis.read(buf)) != -1) {
				sb.append(new String(buf, "utf-8"));
				buf = new byte[1024];
			}

			logger.info(sb.toString());

			flin.release();
			fcin.close();
			fis.close();
			fis = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
