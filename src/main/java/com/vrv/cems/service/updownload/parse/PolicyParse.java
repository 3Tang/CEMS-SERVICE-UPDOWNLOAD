package com.vrv.cems.service.updownload.parse; 

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.vrv.cems.service.updownload.config.LogConfig;
import com.vrv.cems.service.updownload.config.TaskConfig;

/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年4月17日 下午4:57:14 
 */
public class PolicyParse {
	private String policyXMLContent;
	private Document document ;
	private List<TaskConfig> taskConfigs = new ArrayList<TaskConfig>();
	private LogConfig logConfig = new LogConfig();
	private String policyFtpPath;
	public PolicyParse(String policyXMLContent) {
		this.policyXMLContent = policyXMLContent;
		init();
		
	}
	private void init(){
		createDocument();
		parsePolicyFtpPath();
		parseTaskConfig();
		parseLog();
		document.clearContent();
	}
	
	private void parseLog(){
		//设置 日志 信息
		logConfig.setLogLevel( document.selectSingleNode("/root/logBean/@logLevel").getText() );
		logConfig.setLogPath( document.selectSingleNode("/root/logBean/@logPath").getText() );
	}
	private void parsePolicyFtpPath(){
		policyFtpPath = document.selectSingleNode("/root/params/paramBean[@key='policy_path_pre']").getText() ;
	}
	@SuppressWarnings("unchecked")
	private void parseTaskConfig(){
		//设置 任务 信息
		List<Element> timerNode = document.selectNodes("//root/timers/timerBean");
		for (int i = 0; i < timerNode.size(); i++) {
			TaskConfig taskConfig = new TaskConfig();
			taskConfig.setCycle( timerNode.get( i ).element("cycle").getText() );
			taskConfig.setGroup( timerNode.get( i ).element("group").getText() );
			taskConfig.setName( timerNode.get( i ).element("name").getText() );
			taskConfig.setTrigger( timerNode.get( i ).element("trigger").getText() );
			taskConfigs.add( taskConfig );
		}
	}
	
	private void createDocument(){
		try {
			SAXReader saxReader =new SAXReader();
			document = saxReader.read( new ByteArrayInputStream( policyXMLContent.getBytes("UTF-8")) );
		} catch (UnsupportedEncodingException e) { 
			e.printStackTrace();
		} catch (DocumentException e) { 
			e.printStackTrace();
		} 
	}
	
	public List<TaskConfig> getTaskConfigs() {
		return taskConfigs;
	}
	public LogConfig getLogConfig() {
		return logConfig;
	}
	public String getPolicyFtpPath() {
		return policyFtpPath;
	}
	
	
	
}
 