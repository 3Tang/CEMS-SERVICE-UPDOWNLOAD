package com.vrv.cems.service.updownload.config; 

import org.quartz.Job;

import com.vrv.cems.service.updownload.task.CUpGradeIndexInfoTask;
import com.vrv.cems.service.updownload.task.PatchIndexInfoTask;
import com.vrv.cems.service.updownload.task.PolicyIndexInfoTask;
import com.vrv.cems.service.updownload.task.UpdatePolicyXMLTask;

/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年4月17日 下午3:57:03 
 */
public class TaskConfig {
	private String name ;
	private String group;
	private String cycle ;
	private String trigger ;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	public String getTrigger() {
		return trigger;
	}
	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}
	public Class<? extends Job> getJobClass() {
		if( "cUpGradeIndexTask".equals( name )){
			return CUpGradeIndexInfoTask.class;
		}else if("patchIndexTask".equals( name )){
			return PatchIndexInfoTask.class;
		}else if( "policyIndexTask".equals( name )){
			return PolicyIndexInfoTask.class;
		}else if( "updatePolicyXMLTask".equals( name )){
			return UpdatePolicyXMLTask.class;
		}
		return null;
	} 
	
	
}
 