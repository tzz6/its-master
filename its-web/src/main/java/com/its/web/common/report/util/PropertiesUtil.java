package com.its.web.common.report.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;

public class PropertiesUtil {

	/**
	 * 根据资源文件路径读取信息
	 * 
	 * @param path
	 * @param key
	 * @return
	 */
	public static String getValue(String path, String key) {

		String result = "";
		try {
			if (StringUtils.isNotBlank(path)) {
				Properties properties = new Properties();
				InputStream inputStream = PropertiesUtil.class.getResourceAsStream(path);
				properties.load(inputStream);
				result = properties.getProperty(key);
				inputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String getPath(String name) {
		String result = "";
		if (StringUtils.isNotBlank(name)) {
			result = PropertiesUtil.class.getClassLoader().getResource(name).getPath();
		}
		return result;
	}
	
	/**
	 * 读取资源文件
	 * 
	 * @param path
	 * @return
	 */
	public static Map<String, String> getPropertiesValue(String path) {

		Map<String, String> map = null;
		try {
			if (StringUtils.isNotBlank(path)) {
				map = new HashMap<String, String>();
				Properties properties = new Properties();
				InputStream inputStream = PropertiesUtil.class.getResourceAsStream(path);
				properties.load(inputStream);
				for (Iterator<Object> iterator = properties.keySet().iterator(); iterator.hasNext();) {
					String key = convertToStr(iterator.next());
					String value = convertUnicodeToUtf8(properties.get(key));
					map.put(key, value);
				}
				inputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	private static String convertToStr(Object obj) {
		String str = "";
		if (obj != null) {
			str = obj.toString().trim();
		}
		return str;
	}

	private static String convertUnicodeToUtf8(Object obj) {
		try {
			return new String(convertToStr(obj).getBytes("ISO8859_1"), "utf-8");
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
}
