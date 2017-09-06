package com.its.web.common.report.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Jasper模板配置
 *
 */
public class TemplateProperties {
	private static final Logger logger = Logger.getLogger(TemplateProperties.class);
	public Map<String, JasperReport> map = null;
	private static TemplateProperties templateProperties;

	public static TemplateProperties getTemplateProperties() {
		if (templateProperties == null)
			templateProperties = new TemplateProperties();
		return templateProperties;
	}

	@SuppressWarnings("rawtypes")
	private TemplateProperties() {
		InputStream is = null;
		String name = "";
		try {
			if (map == null) {
				map = new HashMap<String, JasperReport>();
			}
			is = this.getClass().getClassLoader().getResourceAsStream("report/Template.Properties");
			Properties props = new Properties();
			props.load(is);
			Enumeration en = props.keys();

			while (en.hasMoreElements()) {
				name = en.nextElement().toString();
				String path = props.getProperty(name);
				path = PropertiesUtil.class.getClassLoader().getResource(path).getPath();
				JasperReport jr = (JasperReport) JRLoader.loadObjectFromFile(path);
				map.put(name, jr);
			}

		} catch (Exception e) {
			logger.error("模板名称name= " + name);
			logger.error("初始化模板出错:", e);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
			}
		}
	}
}
