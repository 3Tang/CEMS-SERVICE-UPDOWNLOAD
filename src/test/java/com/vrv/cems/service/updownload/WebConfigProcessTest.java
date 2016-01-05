package com.vrv.cems.service.updownload; 

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.mchange.v1.io.InputStreamUtils;
import com.vrv.cems.service.base.interfaces.IUpdownloadService;
import com.vrv.cems.service.base.util.ByteBufferUtils; 

/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：daiyijun<br/>
 *		    E-mail ：daiyijun@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2015年4月17日 上午11:59:57 
 */
public class WebConfigProcessTest {
	@Test
	public void test(){
		Map<String, String> httpParams = new HashMap<String, String>();
		httpParams.put("maxCode", IUpdownloadService.SERVICE_CODE);
		/*httpParams.put("data", getFileContent() );*/
		httpParams.put("minCode", "1000");
		HttpPostRequest httpPostRequest = new HttpPostRequest();
		httpPostRequest.setParams( httpParams );
		httpPostRequest.setUrl("http://localhost:8080/CEMS-SERVICE-UPDOWNLOAD/udload" );
		String requestBody = httpPostRequest.doRequest();
		System.out.println( "eeeeeee");
		System.out.println( requestBody ); 
	}
	
/*	private String getFileContent() { 
		try {
			System.out.println( WebConfigProcessTest.class.getClassLoader().getResource("").toString());
			InputStream inputStream = WebConfigProcessTest.class.getClassLoader().getResourceAsStream("policy.xml");
			ReadableByteChannel readableByteChannel = Channels.newChannel( inputStream );
			List<ByteBuffer> linkedListBuffer = new LinkedList<ByteBuffer>();
			while( true ){
				ByteBuffer byteBuffer = ByteBuffer.allocate( 100 );
				int readCount = readableByteChannel.read( byteBuffer );
				byteBuffer.flip();
				linkedListBuffer.add( byteBuffer );
				if( readCount == -1){
					break ;
				}
			}
			readableByteChannel.close();
			inputStream.close();
			int totalCount = 0 ;
			for (int i = 0; i < linkedListBuffer.size(); i++) {
				int remaining = linkedListBuffer.get( i ).remaining(); 
				totalCount =totalCount+remaining;
			}
			ByteBuffer byteBuffer = ByteBuffer.allocate(totalCount );
			for (int i = 0; i < linkedListBuffer.size(); i++) {
				byteBuffer.put( linkedListBuffer.get( i ) );
			}
			byteBuffer.flip();
			return ByteBufferUtils.byteBuffer2String( byteBuffer );
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException( e ); 
		} 
	}*/
}
 