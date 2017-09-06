package com.its.web.common.report.util;

import java.net.URL;

import org.apache.commons.lang.StringUtils;


/** 
 * @ClassName: JasperPrintPath 
 * @Description: 
 * @author minxinxin@sf-express.com 
 * @date 2014-12-6 上午11:52:36  
 */
public class PrintPath {
	
	public static String getFilePathUrl(String key) {
		return PropertiesUtil.getValue("/Resource.properties", key);
	}

	public static String getResource(){
		URL url =  PrintPath.class.getResource("/");
		String path = url.getPath();
		int num = path.indexOf("WEB-INF");
		return StringUtils.substring(path, 0, num);
	}
	public static void main(String[] args) {
		getResource();
	}
}
