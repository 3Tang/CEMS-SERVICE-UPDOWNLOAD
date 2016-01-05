/**   
 * @Title: FileUtil.java 
 * @Package com.vrv.cems.service.updownload.util 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author tangtieqiao
		   tangtieqiao@vrvmail.com.cn
 * @date 2015年8月28日 上午10:18:34 
 * @version V1.0   
 */
package com.vrv.cems.service.updownload.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * @ClassName: FileUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tangtieqiao tangtieqiao@vrvmail.com.cn
 * @date 2015年8月28日 上午10:18:34
 * 
 */
public class FileUtil {
	static Logger logger = Logger.getLogger(FileUtil.class);

	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			logger.info("创建目录" + destDirName + "失败，目标目录已经存在");
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		// 创建目录
		if (dir.mkdirs()) {
			dir.setExecutable(true);// 设置可执行权限
			dir.setReadable(true);// 设置可读权限
			dir.setWritable(true);
			logger.info("创建目录" + destDirName + "成功！");
			logger.info("目录canExecute" + dir.canExecute());
			logger.info("目录canRead" + dir.canRead());
			logger.info("目录canWrite" + dir.canWrite());
			return true;
		} else {
			logger.info("创建目录" + destDirName + "失败！");
			return false;
		}
	}

	public static synchronized boolean createFile(String destFileName) {
		File file = new File(destFileName);
		if (file.exists()) {
			logger.info("创建单个文件" + destFileName + "失败，目标文件已存在！");
			return false;
		}
		if (destFileName.endsWith(File.separator)) {
			logger.info("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
			return false;
		}
		// 判断目标文件所在的目录是否存在
		if (!file.getParentFile().exists()) {
			// 如果目标文件所在的目录不存在，则创建父目录
			logger.info("目标文件所在目录不存在，准备创建它！");
			if (!file.getParentFile().mkdirs()) {
				logger.info("创建目标文件所在目录失败！");
				return false;
			}
		}
		// 创建目标文件
		try {
			if (file.createNewFile()) {
				logger.info("创建单个文件" + destFileName + "成功！");
				return true;
			} else {
				logger.info("创建单个文件" + destFileName + "失败！");
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("创建单个文件" + destFileName + "失败！" + e.getMessage());
			return false;
		}
	}

	public static String getFileType(String fileName) {
		String[] fileNames = fileName.split("\\.");
		int fileNameLength = fileNames.length;
		if (fileNameLength < 2) {
			return fileName;
		}
		if (fileNameLength >= 2) {
			return fileNames[fileNameLength - 1];
		}

		return "";

	}

	/**
	 * @Title: copyFile
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param ins
	 * @param @param storeFileName 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public static String copyFile(InputStream ins, String storeFileName) {
		String size = "";
		try {
			/*
			 * PrintStream pw = new PrintStream(new BufferedOutputStream( new
			 * FileOutputStream(storeFileName))); while (ins.read()>0) {
			 * pw.write(ins.read()); } pw.flush(); pw.close();
			 */
			FileOutputStream fos = new FileOutputStream(storeFileName);
			byte[] buffer = new byte[1024];
			while (ins.read(buffer) > 0) {
				fos.write(buffer, 0, buffer.length);
			}
			fos.flush();
			fos.close();

			logger.info("文件生成成功");
			File storeFile = new File(storeFileName);
			// 获得文件的长度
			size = String.valueOf(FileUtils.sizeOf(storeFile));
		} catch (Exception e) {
			logger.error("文件生成失败，请查看后台日志" + e.getMessage());

		}
		return size;
	}

	/**
	 * @throws FileNotFoundException 
	 * @Title: FileLock
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param temp
	 * @param @return 参数说明
	 * @return InputStream 返回类型
	 * @throws
	 */
	public static RandomAccessFile FileLock(String filePath) throws FileNotFoundException {
		File file = new File(filePath);
		RandomAccessFile out = new RandomAccessFile(file, "rw");
		try {
			FileChannel fcout = out.getChannel();
			FileLock flout = null;
			while (true) {
				try {
					flout = fcout.tryLock();
					break;
				} catch (Exception e) {
					logger.info("有其他线程正在操作该文件，当前线程休眠1000毫秒");
					Thread.sleep(1000);
				}

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return out;
	}
}