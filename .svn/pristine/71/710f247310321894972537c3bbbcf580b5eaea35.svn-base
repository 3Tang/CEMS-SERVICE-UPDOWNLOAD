package com.vrv.cems.service.updownload.util; 

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：    zhangwenli
 *		    E-mail ：zhangwenli@vrvmail.com.cn
 
 * @version 版   本  号：V1.0 <br/>
 *          创建时间：2015年1月8日 上午11:34:09 
 */
public class JsonUtil {
	/**
	 * 获取json串中FtpPath路径
	 * @param json
	 * @return
	 */
	public static String getFtpPath(String json){
		JSONObject datajson=JSONObject.fromObject(json);
		String ftppath=datajson.getString("ftpPath");
		return ftppath;
	}
	public static String getPath(String json){
		JSONObject datajson=JSONObject.fromObject(json);
		JSONArray jdata=datajson.getJSONArray("jdata");
		JSONObject object=jdata.getJSONObject(0);
		String path=object.getString("path");
		return path;
	}
	/**
	 * 获取json串中FdfsPath路径
	 * @param json
	 * @return
	 */
	public static String getFdfsPath(String json){
		JSONObject datajson=JSONObject.fromObject(json);
		JSONArray jdata=datajson.getJSONArray("jdata");
		JSONObject object=jdata.getJSONObject(0);
		String fdfspath=object.getString("fdfsPath");
		return fdfspath;
	}
	public static String getUploadResult(String json){
		JSONObject datajson=JSONObject.fromObject(json);
		return datajson.getString("result");
	}
	public static String getPubRange(String json){
		JSONObject datajson=JSONObject.fromObject(json);
		return datajson.getString("pubRange");
	}
	public static String getFileName(String json){
		JSONObject datajson=JSONObject.fromObject(json);
		return datajson.getString("fileName");
	}
	public static String getJsonValueByKey(String json,String key){
		JSONObject datajson=JSONObject.fromObject(json);
		return datajson.getString(key);
	}
	
	public static List<String> getRangeList(String json){
		JSONObject datajson=JSONObject.fromObject(json);
		JSONArray jdata=datajson.getJSONArray("jdata");
		List<String> rangeCodes=new ArrayList<String>();
		for(int i=0;i<jdata.size();i++){
			String str=new String(jdata.getJSONObject(i).getString("rangeCode"));
			rangeCodes.add(str);
		}
		return rangeCodes;
	}
}
 