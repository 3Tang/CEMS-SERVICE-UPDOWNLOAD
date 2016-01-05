/**   
 * @Title: UpFileTestServlet.java 
 * @Package com.vrv.cems.service.updownload.servlet 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author tangtieqiao
		   tangtieqiao@vrvmail.com.cn
 * @date 2015年8月25日 下午4:30:19 
 * @version V1.0   
 */
package com.vrv.cems.service.updownload.servlet;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.sys.common.fastdfs.FastDFSClient;
import com.sys.common.fastdfs.FastDFSClientException;
import com.sys.common.util.UUIDUtils;
import com.sys.common.util.json.JsonUtils;
import com.sys.common.util.path.FilePathSegmentPolicy;
import com.sys.common.util.security.Base64Utils;
import com.sys.common.util.security.CRCUtils;
import com.vrv.cems.service.base.bean.ResultMsgBean;
import com.vrv.cems.service.base.interfaces.IBlockService;
import com.vrv.cems.service.base.interfaces.IUpdownloadService;
import com.vrv.cems.service.updownload.*;
import com.vrv.cems.service.updownload.bean.RequestBean;
import com.vrv.cems.service.updownload.bean.UpFileBean;
import com.vrv.cems.service.updownload.bean.UpLoadFile;
import com.vrv.cems.service.updownload.config.SystemConfig;
import com.vrv.cems.service.updownload.factory.FastDFSClientFactory;
import com.vrv.cems.service.updownload.statics.SystemStatics;
import com.vrv.cems.service.updownload.util.CommomThreadPool;
import com.vrv.cems.service.updownload.util.DirUtil;
import com.vrv.cems.service.updownload.util.FileUtil;
import com.vrv.cems.service.updownload.util.ReadConfigFileUtil;
import com.vrv.cems.service.updownload.util.XmlOperator;

/**
 * @ClassName: UpFileTestServlet
 * @Description: 上传处理部分
 * @author tangtieqiao tangtieqiao@vrvmail.com.cn
 * @date 2015年8月25日 下午4:30:19
 * 
 */
@WebServlet(name="uploadFile",urlPatterns={"/pages/uploadFile.do"})
public class UpFileTestServlet extends HttpServlet {
	private static final Logger LOGGER = Logger
			.getLogger(UpFileTestServlet.class);

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 3029474455593490550L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String devOnlyId="";
		String userOnlyId="";
		String devType="";
		String productType="";
		String fileName="";
		String ip="";
		String crc="";
		// 参数初始化
		final String INDEX_XML_PATH = getServletConfig().getServletContext()
				.getRealPath("/") + "\\" + "UpFileIndex\\index.xml";
		
		
		File indexFile = new File(INDEX_XML_PATH);
		if (!indexFile.exists()) {
			FileUtil.createFile(INDEX_XML_PATH);
		}

		String filePath="D:/";
		String testFilePaht = "D:/saveFiletest.txt";
		FileUtil.createFile(testFilePaht);
		// 初始化xml操作类
		XmlOperator.getInstance().setFile(indexFile);
		String configPath = SystemConfig.getConfigPath();
		String hostIp = ReadConfigFileUtil.get(SystemConfig.getConfigPath(),
				"service.ip");
		String servletUrl = request.getRequestURL().toString();
		String hostServiceName = ReadConfigFileUtil.get(
				SystemConfig.getConfigPath(), "service.name");

		String serverId = Base64Utils.encode2string(hostIp + ":"
				+ hostServiceName);

		UpFileBean upFileBean = null;
		InputStream ins = null;
		String urlMaxCode = "";
		String urlMinCode = "";
		String minCode = "";
		String data = "";
		String size = "";

		byte[] fileData = null;

		LOGGER.info("1,进入UpFileTestServlet的service方法");
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField()) {
					// 获取前台的参数
					if ("devOnlyId".equals(item.getFieldName())) {
						devOnlyId = item.getString();
					}
					if ("userOnlyId".equals(item.getFieldName())) {
						userOnlyId = item.getString();
					}
					if ("devType".equals(item.getFieldName())) {
						devType = item.getString();
						LOGGER.info("2,获取前台的data的数据为:" + data);
					}
					if ("productType".equals(item.getFieldName())) {
						devType = item.getString();
						LOGGER.info("2,获取前台的data的数据为:" + data);
					}
					if ("Filename".equals(item.getFieldName())) {
						fileName = item.getString();
						LOGGER.info("2,获取前台的data的数据为:" + data);
					}
					if ("ip".equals(item.getFieldName())) {
						devType = item.getString();
						LOGGER.info("2,获取前台的data的数据为:" + data);
					}
					if ("ip".equals(item.getFieldName())) {
						devType = item.getString();
						LOGGER.info("2,获取前台的data的数据为:" + data);
					}

				} else {
					// 上传文件信息处理
					//String fileName = item.getName();
					/* fileData = item.get(); */
					ins = item.getInputStream();
					fileData = item.get();
					 File saveFile=new File(filePath,fileName);
					 item.write(saveFile);//向文件中写入数据
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
      

		// 判断maxCode是否为空
	/*	if (StringUtils.isBlank(urlMaxCode)) {
			LOGGER.error("MaxCode ERROR ：[MaxCode is null]");
			response.getWriter().print("MaxCode ERROR ：[MaxCode is null]");
			return;
		}*/

	/*	// 返回结果
		ResultMsgBean result = new ResultMsgBean(
				IUpdownloadService.SERVICE_CODE, minCode);
		// 验证maxCode和minCode
		if (urlMaxCode.equals(IUpdownloadService.SERVICE_CODE)) {
			if (urlMinCode.equals(IUpdownloadService.UPGRADE_INFO)) {

				if (StringUtils.isNotBlank(data)) {
					// Json2JavaBean
					upFileBean = JsonUtils.json2Object(data, UpFileBean.class);
				}
				// 从完整文件名中获取文件类型
				String clientUpFileType = FileUtil.getFileType(upFileBean
						.getFileName());
				String appDir = this.getServletConfig().getServletContext()
						.getRealPath("/");
				String dirName = appDir + "UpFile" + "/"
						+ FilePathSegmentPolicy.pathByNumberAndCharacter();
				dirName = dirName.replace("\\", "/");

				// 先建目录
				FileUtil.createDir(dirName);
				String uuid = UUIDUtils.get32UUID();
				
				String url = "";
				// 转存本地的路径和完整文件名
				final String storeFileName = dirName + "/" + uuid + "."
						+ clientUpFileType;// 路径用常量
				String storePath = servletUrl.replace("UpFile", "downFile")
						+ "/" + uuid + "." + clientUpFileType;
				// 在建文件,考虑是不是可以整合
				FileUtil.createFile(storeFileName);

				// 上传文件写入服务本地文件
				size = FileUtil.copyFile(ins, storeFileName);

				// 转存服务本地成功后要写本地索引文件
				crc = (crc == null) ? "null" : crc;
				UpLoadFile upfile = new UpLoadFile(uuid,
						upFileBean.getFileName(), crc, url, storeFileName,
						SystemStatics.FILE_NOT_UPFASTFDS);
				try {
					XmlOperator.getInstance().addIndexXML(upfile);
				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}

				InetAddress addr = InetAddress.getLocalHost();
				hostIp = addr.getHostAddress().toString();

				final FastDFSClient fastDFSClient = FastDFSClientFactory
						.createFastDFSClient();
				if (Integer.parseInt(size) > 0) {
					CommomThreadPool.joinThreadPool(new Runnable() {
						public void run() {
							// 读取本地xml索引文件,获取上传文件JavaBean
							File indexFile = new File(INDEX_XML_PATH);
							if (indexFile.exists()) {
								List<UpLoadFile> upFileBeans = XmlOperator
										.getInstance().xmlToObject(
												UpLoadFile.class, "file");
								for (UpLoadFile fileBean : upFileBeans) {
									File targetFile = new File(fileBean
											.getPath());
									String fdsUrl = "";
									if (targetFile.exists()) {
										try {
											fdsUrl = fastDFSClient
													.uploadFile(targetFile);
											fileBean.setUrl(fdsUrl);
											// fileBean.setCrc(crc);
											fileBean.setIsUpFastFDS(SystemStatics.FILE_IS_UPFASTFDS);
											// 更新服务本地索引文件
											if (StringUtils.isNotBlank(fdsUrl)) {
												XmlOperator
														.getInstance()
														.updateXMLbyId(
																fileBean.getId(),
																fileBean);
											}
										} catch (FastDFSClientException e) {
											// TODO 自动生成的 catch 块
											e.printStackTrace();
										}
									}
								}
							} else {
								LOGGER.error("本地索引文件不存在!");
							}
						}
					});

				}// 请求在线服务失败的处理(根据失败类型进行不同处理)

				result.setJdata(JSONArray.fromObject("[{\"fileName\":\""
						+ upFileBean.getFileName() + "\",\"size\":\"" + size
						+ "\",\"storePath\":\"" + storePath
						+ "\",\"serverId\":\"" + serverId + "\"}]"));
				result.setResult("0");
				result.setDesc("文件上传成功!");
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(result.toJson());

			} else {
				try {
					LOGGER.error("MinCode:" + minCode + "不存在");
					result.setDesc("MinCode:" + minCode + "不存在");
					result.setResult("1");
					response.getWriter().write(result.toJson());
				} catch (IOException e) {
					LOGGER.error("返回信息报错!", e);
				}
			}

		}
	}*/
	}
}
