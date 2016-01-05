package com.vrv.cems.service.updownload; 

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年4月17日 上午11:29:52 
 */
public class HttpPostRequest {
	private Map<String, String> params = new HashMap<String, String>();
	private String url ;
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String doRequest(){
		try {
			List<NameValuePair> nameValuePairs = new LinkedList<NameValuePair>();
			Iterator<String> keyIterator = params.keySet().iterator();
			while( keyIterator.hasNext() ){
				String key = keyIterator.next();
				nameValuePairs.add( new BasicNameValuePair( key , params.get( key )) );
			}
			HttpEntity httpEntity = new UrlEncodedFormEntity( nameValuePairs , "utf-8");
			HttpPost httpPost = new HttpPost( url );
			httpPost.setEntity( httpEntity );
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse httpResponse = httpClient.execute( httpPost );
			return EntityUtils.toString(  httpResponse.getEntity() , "utf-8" );
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new RuntimeException( e );
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException( e );
	    }
	}
}
 