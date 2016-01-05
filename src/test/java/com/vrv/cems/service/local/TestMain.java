/**   
* @Title: TestMain.java 
* @Package com.vrv.cems.service.local 
* @Description: TODO(用一句话描述该文件做什么) 
* @author tangtieqiao
		   tangtieqiao@vrvmail.com.cn
* @date 2015年9月14日 下午3:45:24 
* @version V1.0   
*/
package com.vrv.cems.service.local;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** 
 * @ClassName: TestMain 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author tangtieqiao
			tangtieqiao@vrvmail.com.cn
 * @date 2015年9月14日 下午3:45:24 
 *  
 */
public class TestMain {

	/** 
	 * @Title: main 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param args  参数说明 
	 * @return void    返回类型 
	 * @throws 
	 */
	public static void main(String[] args) {
		//共享file对象
		File file=new File("D:/test333.txt"); 
		//操作对象
		FileRWOperator op=new FileRWOperator(file);
		//具有排程功能线程池
	    ScheduledExecutorService service = Executors.newScheduledThreadPool(5);
        //建一个写线程,3个读线程
	    Thread writeThread=new Thread(new FileWriteThread2(op,"writerA"));
	    
	    
	    service.scheduleAtFixedRate(writeThread, 0, 10, TimeUnit.SECONDS);
	    //5 创建3个读任务
	    Thread readTask1 = new Thread(new FileReadThread2(op,"ReaderA"));
	    Thread readTask2 = new Thread(new FileReadThread2(op,"ReaderB"));
	    Thread readTask3 = new Thread(new FileReadThread2(op,"ReaderC"));
	    //6 延时0秒后每秒执行一次task1;
	    service.scheduleAtFixedRate(readTask1, 1, 1, TimeUnit.SECONDS);
	    service.scheduleAtFixedRate(readTask2, 2, 1, TimeUnit.SECONDS);
	    service.scheduleAtFixedRate(readTask3, 3, 1, TimeUnit.SECONDS);
	/*	FileReadThread2 readers[] = new FileReadThread2[5];  
		FileRWOperator operator=new FileRWOperator(file);
        Thread threadsReader[] = new Thread[5];  
        for (int i = 0; i < 5; i++){  
            readers[i] = new FileReadThread2(operator,"reader["+i+"]");  
            threadsReader[i] = new Thread(readers[i]);  
        }  
        FileWriteThread2 writer = new FileWriteThread2();  
        Thread threadWriter = new Thread(writer);  
        for (int i = 0; i < 5; i++){  
            threadsReader[i].start();  
        }  
        threadWriter.start(); */
       
	}

}
