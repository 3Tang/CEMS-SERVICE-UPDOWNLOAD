package com.vrv.cems.service.updownload.web.listener; 

import java.text.ParseException; 
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;  
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;  

import com.vrv.cems.service.updownload.config.TaskConfig; 

/** 
 *   <B>说       明</B>: 对定时获取升级索引任务、定时获取补丁索引任务 、定时获取策略索引任务
 *   				做管理，并提供开始，重启，停止功能。
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年4月8日 上午11:10:16 
 */
public class TaskManager {
	private static final  Logger LOGGER = Logger.getLogger(TaskManager.class);
	private static final TaskManager taskManager = new TaskManager();
	private static volatile Map<String , TriggerKey> triggerKeyMap = new HashMap<String , TriggerKey>();
	private static volatile Scheduler taskScheduler ;
	private TaskManager(){
		try {
			SchedulerFactory sf = new StdSchedulerFactory();
			taskScheduler = sf.getScheduler();
		} catch (Exception e) {
		}
	}
	public static TaskManager getInstance(){
		return taskManager;
	}
	/**
	 * 重启
	 */
	public synchronized void reStart( List<TaskConfig> listTaskConfigs ){
		try { 
			if( !taskScheduler.isStarted() ){
				start( listTaskConfigs );
				return ;
			}
		} catch (SchedulerException e1) {
			e1.printStackTrace();
		}
		try {
			for (int i = 0; i < listTaskConfigs.size(); i++) {
				TaskConfig taskConfig = listTaskConfigs.get( i );
				
				TriggerKey triggerKey = triggerKeyMap.get( taskConfig.getName() );
				CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule( taskConfig.getCycle() );
				Trigger cronTrigger = TriggerBuilder.newTrigger().withIdentity( triggerKey ).withSchedule( cronScheduleBuilder ).build();
				
				taskScheduler.rescheduleJob(triggerKey,cronTrigger);
				
			} 
		} catch (Exception e) {
			LOGGER.error("定时任务 重启失败",  e );
			throw new RuntimeException( e );
		} 
	} 
	/**
	 * 开始 SystemConfig systemConfig
	 */
	private synchronized void start(  List<TaskConfig> listTaskConfigs ){  
			try {
				if( listTaskConfigs == null || listTaskConfigs.size() == 0){
					return ;
				}
				triggerKeyMap.clear();
				for (int i = 0; i < listTaskConfigs.size(); i++) {
					TaskConfig taskConfig = listTaskConfigs.get( i );
					
					JobDetailImpl upgradeJobDetail = new JobDetailImpl();
					upgradeJobDetail.setName( taskConfig.getName() );
					upgradeJobDetail.setJobClass( taskConfig.getJobClass()  );
					CronTriggerImpl cUpGradeIndexTrigger = new CronTriggerImpl();
					cUpGradeIndexTrigger.setName( taskConfig.getTrigger() ); 
					cUpGradeIndexTrigger.setCronExpression( taskConfig.getCycle() ); 
					cUpGradeIndexTrigger.setStartTime( new Date() );
					taskScheduler.scheduleJob( upgradeJobDetail , cUpGradeIndexTrigger );
					//添加任务中
					triggerKeyMap.put( taskConfig.getName() ,  cUpGradeIndexTrigger.getKey() );
					
				}
				taskScheduler.start();
			} catch( ParseException e ){
				
			}catch (SchedulerException e) {
				e.printStackTrace();
			} 
	} 
}
 