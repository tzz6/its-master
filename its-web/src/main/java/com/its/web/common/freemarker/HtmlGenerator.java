package com.its.web.common.freemarker;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.apache.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class HtmlGenerator {
	
	private static final Logger logger = Logger.getLogger(HtmlGenerator.class);

	/**
	 * Generate html string
	 * 
	 * @param path模板路径
	 * @param templateName模板名
	 * @param variables参数
	 * @return
	 */
	public static String generate(String path, String templateName, Map<String, Object> variables) {
		BufferedWriter writer = null;
		String htmlContent = "";
		Configuration config = null;
		Template template = null;
		try {
			config = FreemarkerConfiguration.getConfiguation(path);
			template = config.getTemplate(templateName);
			template.setEncoding("UTF-8");
			StringWriter stringWriter = new StringWriter();
			writer = new BufferedWriter(stringWriter);
			template.process(variables, writer);

			htmlContent = stringWriter.toString();
			writer.flush();
		} catch (IOException e) {
			logger.error("IOException", e);
		} catch (TemplateException e) {
			logger.error("TemplateException", e);
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					logger.error("IOException", e);
				}
		}
		return htmlContent;
	}
}