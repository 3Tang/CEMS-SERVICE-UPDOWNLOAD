/**   
 * @Title: XmlUtil.java 
 * @Package com.vrv.cems.service.updownload.util 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author tangtieqiao
		   tangtieqiao@vrvmail.com.cn
 * @date 2015年9月2日 下午4:31:15 
 * @version V1.0   
 */
package com.vrv.cems.service.updownload.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.sys.common.util.StringUtils;
import com.vrv.cems.service.updownload.bean.UpLoadFile;

/**
 * @ClassName: XmlUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tangtieqiao tangtieqiao@vrvmail.com.cn
 * @date 2015年9月2日 下午4:31:15
 * 
 */
public class XmlOperator {
	final ReadWriteLock lock = new ReentrantReadWriteLock();
	private static Log logger = LogFactory.getLog(XmlOperator.class);
	private File file;

	private XmlOperator() {
	};

	private static final XmlOperator instance = new XmlOperator();

	public static XmlOperator getInstance() {
		return instance;
	}

	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @Title: toFileNode
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param pat
	 * @param @param root 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public static void toFileNode(UpLoadFile pat, Element root) {
		Element ele = root.addElement("file");
		ele.addElement("id").addText(pat.getId());
		ele.addElement("name").addText(pat.getName());
		ele.addElement("crc").addText(pat.getCrc());
		ele.addElement("url").addText(pat.getUrl());
		ele.addElement("path").addText(pat.getPath());
		ele.addElement("isUpFastFDS").addText(pat.getIsUpFastFDS());

	}

	public void addIndexXML(UpLoadFile pat) throws Exception {
		Document doc = null;
		try {
			 String xmlStr = xml2String();
			 doc = DocumentHelper.parseText(xmlStr);
			Element filesElement = doc.getRootElement();// 向外取数据，获取xml的根节点
			toFileNode(pat, filesElement);
		} catch (DocumentException e) {
			doc = DocumentHelper.createDocument();
			doc.setXMLEncoding("utf-8");
			Element root = doc.addElement("files");
			toFileNode(pat, root);
		} finally {
			writeXML(file, doc);
		}

	}

	public void writeXML(File file, Document doc) {
		/* FileOutputStream fos = new FileOutputStream(temp); */
		try {
			lock.writeLock().lock();
			FileOutputStream fos = new FileOutputStream(file);
			OutputFormat outputFormat = OutputFormat.createPrettyPrint();
			OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
			XMLWriter writer = new XMLWriter(osw, outputFormat);
			writer.write(doc);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			logger.error("写入xml异常", e);
		} finally {
			lock.writeLock().unlock();
		}
	}

	@SuppressWarnings("unchecked")
	public <E> List xmlToObject(Class<E> clazz, String name) {
		String xmlStr = xml2String();
		List<Object> ojbList = new ArrayList<Object>();
		try {
			Document document = DocumentHelper.parseText(xmlStr);
			Element rootElement = document.getRootElement();
			String rootStrV = rootElement.getStringValue();
			String rootStrT = rootElement.getText();
			List<Element> elements = rootElement.elements(name);
			for (Element nameElement : elements) {
				Field[] field = clazz.getDeclaredFields();
				Object rootObj = clazz.newInstance();

				if (nameElement != null) {
					/*
					 * Class<?> cls =fiel.getType(); boolean bool
					 * =cls.isAssignableFrom(List.class); if(bool){
					 * ParameterizedType pt
					 * =(ParameterizedType)fiel.getGenericType(); cls
					 * =(Class<?>)pt.getActualTypeArguments()[0]; } if(bool){
					 * List<Object> list=new ArrayList<Object>(); List<Element>
					 * eleList=element.elements(); for (Element element2 :
					 * eleList) { list.add(valueWrap(cls,element2)); } String
					 * methodName="set"+upperFirst(fiel.getName());
					 * if(containsMethod(clazz,methodName)){
					 * clazz.getMethod(methodName, List.class).invoke(rootObj,
					 * list); } }
					 */
					rootObj = valueWrap(clazz, nameElement);
					/*
					 * String methodName="set"+upperFirst(fiel.getName());
					 * if(containsMethod(clazz,methodName)){
					 * clazz.getMethod(methodName, clazz).invoke(rootObj, obj);
					 * 
					 * }
					 */

					ojbList.add((E) rootObj);
				}

			}
			return ojbList;
		} catch (DocumentException e) {
			logger.error("解析xml异常", e);
		} catch (Exception e) {
			logger.error("对象转换异常", e);
		}
		return null;

	}

	private static Object valueWrap(final Class<?> cls, final Element element)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		Field[] fie = cls.getDeclaredFields();
		Object obj = cls.newInstance();
		for (Field fi : fie) {
			Element ele = element.element(fi.getName());
			String value = "";
			if (ele == null) {
				value = element.getText();
				if (StringUtils.isBlank(value)) {
					value = element.attributeValue(fi.getName());
				}
			} else {
				value = ele.getText();
				if (StringUtils.isBlank(value)) {
					value = ele.attributeValue(fi.getName());
				}
			}
			String methodName = "set" + upperFirst(fi.getName());
			if (containsMethod(cls, methodName)) {
				cls.getMethod(methodName, String.class).invoke(obj, value);
			}
		}
		return obj;
	}

	public static Boolean containsMethod(Class<?> classz, String methodName) {
		Boolean falg = false;
		Method[] methods = classz.getMethods();
		for (Method method : methods) {
			if (equals(methodName, method.getName())) {
				falg = true;
				break;
			}
		}
		return falg;
	}

	public static String upperFirst(String s) {
		String str = null;
		if (null != s) {
			if (s.length() == 1) {
				str = s.toUpperCase();
			} else {
				str = s.substring(0, 1).toUpperCase() + s.substring(1);
			}
		}
		return str;
	}

	public static boolean equals(String s1, String s2) {
		if (s1 == s2)
			return true;
		if (null == s1)
			return false;
		return s1.equals(s2);
	}

	public  String xml2String() {
		FileInputStream xmlIn = null;
		StringBuffer out = new StringBuffer();
		try {
			lock.readLock().lock();
			xmlIn = new FileInputStream(file);
			byte[] b = new byte[4096];
			for (int n; (n = xmlIn.read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		finally
		{
			lock.readLock().unlock();
		}
		return out.toString();
	}

	public void updateXMLbyId(String id, UpLoadFile pat) {
		String xmlStr = xml2String();
		Document document = null;
		try {
			document = DocumentHelper.parseText(xmlStr);
		} catch (DocumentException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		Element rootElement = document.getRootElement();
		String rootStrV = rootElement.getStringValue();
		String rootStrT = rootElement.getText();
		List<Element> elements = rootElement.elements("file");

		for (Element ele : elements) {
			Element ide = ele.element("id");
			if (id.equalsIgnoreCase(ide.getTextTrim())) {
				Element nameElement = ele.element("name");
				Element crcElement = ele.element("crc");
				Element urlElement = ele.element("url");
				Element pathElement = ele.element("path");
				Element isUpElement = ele.element("isUpFastFDS");

				nameElement.setText(pat.getName());
				crcElement.setText(pat.getCrc());
				urlElement.setText(pat.getUrl());
				pathElement.setText(pat.getPath());
				isUpElement.setText(pat.getIsUpFastFDS());
			}
		}

		writeXML(file, document);

	}
}
