package com.its.web.common.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XssHttpServletRequest extends HttpServletRequestWrapper {

	boolean isUpData = false;// 判断是否是上传 上传忽略

	public XssHttpServletRequest(HttpServletRequest servletRequest) {
		super(servletRequest);
		String contentType = servletRequest.getContentType();
		if (null != contentType)
			isUpData = contentType.startsWith("multipart");
	}

	@Override
	public String getHeader(String name) {
		return cleanXss(super.getHeader(name));
	}

	@Override
	public String getQueryString() {
		return cleanXss(super.getQueryString());
	}

	@Override
	public String getParameter(String name) {
		return cleanXss(super.getParameter(name));
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		if (values != null) {
			int length = values.length;
			String[] escapseValues = new String[length];
			for (int i = 0; i < length; i++) {
				escapseValues[i] = cleanXss(values[i]);
			}
			return escapseValues;
		}
		return super.getParameterValues(name);
	}

	@Override
	public String getServletPath() {
		String uri = super.getServletPath();
		uri = cleanXss(uri);
		uri = uri.replaceAll("%3C", "&lt;").replaceAll("%3E", "&gt;");
		return uri;
	}

	public String inputHandlers(ServletInputStream servletInputStream) {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(servletInputStream, Charset.forName("UTF-8")));
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (servletInputStream != null) {
				try {
					servletInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		/* 不能过滤单引号与双引号，会导致JSON转换失败 */
		String value = sb.toString();
		value = cleanXss(value);
		return value;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (isUpData) {
			return super.getInputStream();
		} else {
			final ByteArrayInputStream bais = new ByteArrayInputStream(
					inputHandlers(super.getInputStream()).getBytes());
			return new ServletInputStream() {
				public int read() throws IOException {
					return bais.read();
				}

				@Override
				public boolean isFinished() {
					return false;
				}

				@Override
				public boolean isReady() {
					return false;
				}

				@Override
				public void setReadListener(ReadListener readListener) {

				}
			};
		}

	}

	public String cleanXss(String str) {
		if (str == null) {
			return null;
		}
		String htmlStr = str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
//		String htmlStr = StringEscapeUtils.escapeHtml(str);
		return htmlStr;
	}
}
