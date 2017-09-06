package com.its.web.common.cache;

import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.common.util.StringUtils;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

/**
 * EhCache  页面缓存过滤器
 *
 */
public class PageCachingFilter extends SimplePageCachingFilter {

	private final static String FILTER_URL_PATTERNS = "patterns";
	private static String[] cacheURLs;

	private void init() throws CacheException {
		String patterns = filterConfig.getInitParameter(FILTER_URL_PATTERNS);
		cacheURLs = StringUtils.split(patterns, ",");
	}

	@Override
	protected void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) {
		try {
			if (cacheURLs == null) {
				init();
			}

			String url = request.getRequestURI();
			boolean flag = false;
			if (cacheURLs != null && cacheURLs.length > 0) {
				for (String cacheURL : cacheURLs) {
					if (url.contains(cacheURL.trim())) {
						flag = true;
						break;
					}
				}
			}
			if (flag) {// 包含我们指定要缓存的url 就缓存该页面，否则执行正常的页面转向
				String query = request.getQueryString();
				if (query != null) {
					query = "?" + query;
					url = url + query;
				}
				System.out.println("当前请求被缓存：" + url);
				super.doFilter(request, response, chain);
			} else {
				chain.doFilter(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	private boolean headerContains(final HttpServletRequest request, final String header, final String value) {
		logRequestHeaders(request);
		final Enumeration accepted = request.getHeaders(header);
		while (accepted.hasMoreElements()) {
			final String headerValue = (String) accepted.nextElement();
			if (headerValue.indexOf(value) != -1) {
				return true;
			}
		}
		return false;
	}

	/** 兼容ie6/7 gzip压缩*/
	@Override
	protected boolean acceptsGzipEncoding(HttpServletRequest request) {
		boolean ie6 = headerContains(request, "User-Agent", "MSIE 6.0");
		boolean ie7 = headerContains(request, "User-Agent", "MSIE 7.0");
		return acceptsEncoding(request, "gzip") || ie6 || ie7;
	}
}
