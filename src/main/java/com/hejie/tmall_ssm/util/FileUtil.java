package com.hejie.tmall_ssm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * <p>工具类:FileUtil </p>
 * <p>Description:文件操作工具类 </p>
 * @author 何杰
 * @date 2019年8月30日
 * @version 1.0
 * @since JDK 1.8
 */
public class FileUtil {
	
	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	private static final int BUFFER_SIZE = 16 * 1024;

	/*
	 * 替换路径(换成目标路径)
	 */
	public static File replaceDir(File file, String targetDir) {
		mkDir(targetDir);
		String path = file.getAbsolutePath();
		String newPath = path.replace(file.getParent(), targetDir);
		//System.out.println("新路径：" + newPath);
		logger.info("新路径 < " + newPath + " > ");
		return new File(newPath);
	}
	
	/*
	 * 创建文件夹
	 */
	public static void mkDir(String dir) {
		File file = new File(dir);
		
		if (!file.exists()) {
			file.mkdir();
			logger.info("文件夹 < " + dir + " > 创建成功");
		}

	}
	
	/*
	 * 复制文件(到目标路径)
	 */
	public static void copyFile(File file, String targetDir) {
		
		if (!isNormal(file, "file")) {
			return;
		}

		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		try {
			File newFile = replaceDir(file, targetDir);	
			fis = new FileInputStream(file);
			fos = new FileOutputStream(newFile);
			byte[] buffer = new byte[1024];
			int num;
			
			while ((num = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, num);
			}
			
			logger.debug("复制文件成功");
			
		} catch(IOException e) {
			
			e.printStackTrace();
			logger.error("复制文件失败，错误信息 < " + e.getMessage() + " > ");
		} finally {
			try {
				Objects.requireNonNull(fos).close();
				Objects.requireNonNull(fis).close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	/*
	 * 复制目录(到目标路径)
	 */
	public static void copyDir(File dir, String targetDir) {
		
		if (!isNormal(dir, "dir")) {
			return;
		}
		
		File newFile = replaceDir(dir, targetDir);	
		newFile.mkdir();
		logger.debug("复制文件夹成功");		
	}
	
	/*
	 * 复制目录下所有内容到目标路径
	 */
	public static void copyAll(File dir, String targetDir) {	
		File[] files = dir.listFiles();

		for (File file : files) {

			if (file.isDirectory()) {
				copyDir(file, targetDir);
				copyAll(file, targetDir + "\\" + file.getName());
			} else {
				copyFile(file, targetDir);
			}

		}	
	}
	
	/*
	 * 判断文件或文件夹是否符合要求(即是否存在且为参数所指类型)
	 */
	public static boolean isNormal(File source, String format) {
		
		if (!source.exists()) {
			logger.error("源目标 < " + source.getAbsolutePath() + " > 不存在，请确认！");
			return false;
		}
		
		switch (format)
		{
			case "file":
				if (!source.isFile()) {
					logger.error("源目标 < " + source.getAbsolutePath() + " > 非文件格式，请确认！");
					return false;
				}
				break;
			case "dir":
				if (!source.isDirectory()) {
					logger.error("源目标 < " + source.getAbsolutePath() + " > 非文件夹格式，请确认！");
					return false;
				}
				break;
			default :
				return true;
		}
		
		return true;
	}
	
	/*
	 * 从输入流中获取字符串
	 */
	public static String getString(InputStream is) {
		StringBuilder sBuilder = new StringBuilder();
		
		try {
			BufferedReader bReader = new BufferedReader(new InputStreamReader(is, "GBK"));
			String line;
			
			while((line = bReader.readLine()) != null) {
				sBuilder.append(line);
			}
			
			bReader.close();
			logger.info("获取内容为 < " + sBuilder.toString() + " > ");
			
		} catch (IOException e) {
			
			e.printStackTrace();
			logger.error("获取内容失败，错误信息 < " + e.getMessage() + " > ");
		}
		
		return sBuilder.toString();
	}
	
	/*
	 * 获取文件后缀名(自主选择大小写)
	 */
	public static String getFileSuffix(File file, String UorLcase) {
		String suffixName;
		
		if (!isNormal(file, "file")) {
			return null;
		}
		
		String fileName = file.getName();
		suffixName = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		if ("up".equals(UorLcase)) {
			suffixName = suffixName.toUpperCase();
		} else {
			suffixName = suffixName.toLowerCase();
		}
		
		logger.info("文件后缀名为 < " + suffixName + " > ");
		
		return suffixName;
	}

	/*
	 * 获取文件后缀名(默认小写)
	 */
	public static String getFileSuffix(String fileName) {
		String suffixName;
		suffixName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		logger.info("文件后缀名为 < " + suffixName + " > ");
		return suffixName;
	}
	
	/*
	 * 创建多级目录
	 */
	public static void createMultievelDir(String path) {
		
		try {
			StringTokenizer st = new StringTokenizer(path, "/");
			String path1 = st.nextToken() + "/";
			String path2 = path1;
			
			while (st.hasMoreTokens()) {
				path1 = st.nextToken() + "/";
				path2 += path1;
				mkDir(path2);
			}
			
			logger.debug("创建多级目录成功");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error("创建多级目录失败，错误信息 < " + e.getMessage() + " > ");
		}	
	}
	
	/*
	 * 删除文件或目录下所有目录和文件
	 */
	public static void deleteAll(String dirPath) {
		File source = new File(dirPath);
		
		try {
			
			if (!source.exists()) {
				return;
			}
			
			if (source.isFile()) {
				source.delete();
				logger.info("文件 < " + source.getAbsolutePath() + " > 删除成功");
				return;
			}
			
			File[] files = source.listFiles();

			for (File file : files) {
				deleteAll(file.getAbsolutePath());
			}
			
			source.delete();
			logger.info("文件 < " + source.getAbsolutePath() + " > 删除成功");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error("删除失败，错误信息 < " + e.getMessage() + " > ");
		}	
	}
	
	/*
	 * 重命名文件或文件夹
	 */
	public static void renameSrc(String oldPath, String newPath) {
		File oldFile = new File(oldPath);
		File newFile = new File(newPath);
		
		try {
			boolean success = oldFile.renameTo(newFile);
			
			if (success) {
				logger.debug("重命名成功");
			} else {
				logger.error("重命名失败");
			}
			
		} catch (RuntimeException e) {
			
			e.printStackTrace();
		}
	}
	
	/*
	 * 加载配置文件（Properties）
	 */
	public static Properties loadProperties(String filePath) {
		Properties properties = null;
		
		try (InputStream is = FileUtil.class.getClassLoader().getResourceAsStream(filePath);) {
			properties = new Properties();
			properties.load(is);
			logger.debug("加载配置文件成功");
			
		} catch (IOException e) {
			
			e.printStackTrace();
			logger.error("加载配置文件失败，错误信息 < " + e.getMessage() + " > ");
		}
		
		return properties;
	}
	
	/*
	 * 根据key获取配置文件中属性值 - (传入默认值，当无此key时返回默认值)
	 */
	public static String getValueByProp(Properties properties, String key, String def) {
		String value = def;
		
		if (properties.containsKey(key)) {
			value = properties.getProperty(key);
		}
		
		logger.debug("获取属性成功 < " + key + " : " + value + " > ");
		return value;
	}

	/*
	 * 上传文件
	 */
	public static void uploadFile(File src, String upDir) {
		
		if (!isNormal(src, "file")) {
			return;
		}
		
		String fileName = src.getName();
		
		File newFile = null;
		
		try {
			newFile = new File(upDir + "/" + StringUtil.getUniqueNo() + fileName.substring(fileName.lastIndexOf(".")));
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("获取文件上传地址失败，错误信息 < " + e.getMessage() + " > ");
		}
		
		try (
				InputStream is = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
				OutputStream os = new BufferedOutputStream(new FileOutputStream(Objects.requireNonNull(newFile)), BUFFER_SIZE);
		) {
			byte[] buffer = new byte[BUFFER_SIZE];
			
			while (is.read(buffer) > 0) {
				os.write(buffer);
			}
			
			logger.debug("文件上传成功，上传路径 < " + newFile.getAbsolutePath() + " >");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error("上传文件失败，错误信息 < " + e.getMessage() + " > ");
		}
	}
	
}
