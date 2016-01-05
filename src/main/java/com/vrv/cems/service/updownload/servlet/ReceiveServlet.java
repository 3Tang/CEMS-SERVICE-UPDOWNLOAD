/**   
* @Title: ReceiveServlet.java 
* @Package com.vrv.cems.service.updownload.servlet 
* @Description: TODO(用一句话描述该文件做什么) 
* @author tangtieqiao
		   tangtieqiao@vrvmail.com.cn
* @date 2015年8月25日 下午6:57:22 
* @version V1.0   
*/
package com.vrv.cems.service.updownload.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * @ClassName: ReceiveServlet 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author tangtieqiao
			tangtieqiao@vrvmail.com.cn
 * @date 2015年8月25日 下午6:57:22 
 *  
 */
public class ReceiveServlet extends HttpServlet {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -4373502783096579254L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.getParameter("maxCode");
		
		String string="Server Response";  
	 
		 	InputStream is = new ByteArrayInputStream(string.getBytes());  
		 	PrintWriter pw=response.getWriter();
		 	pw.write(string);
		 	pw.flush();
		 	
		 	pw.close();
	}
}
