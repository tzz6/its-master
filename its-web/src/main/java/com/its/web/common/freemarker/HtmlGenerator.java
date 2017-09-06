package com.its.web.common.freemarker;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class HtmlGenerator {

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
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return htmlContent;
	}
}