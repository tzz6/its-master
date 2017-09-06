package com.its.web.common.freemarker;

import java.io.File;
import java.io.IOException;


import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

/**
 * Freemark配置
 * 
 */
public class FreemarkerConfiguration {

	private static Configuration config = null;

	/**
	 * 获取 FreemarkerConfiguration
	 * 
	 */
	public static synchronized Configuration getConfiguation(String path) {
		if (config == null) {
			setConfiguation(path);
		}
		return config;
	}

	/**
	 * 设置配置
	 */
	@SuppressWarnings("deprecation")
	private static void setConfiguation(String path) {
		config = new Configuration();
		try {
			config.setDirectoryForTemplateLoading(new File(path));
			config.setObjectWrapper(new DefaultObjectWrapper());
			config.setDefaultEncoding("UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}