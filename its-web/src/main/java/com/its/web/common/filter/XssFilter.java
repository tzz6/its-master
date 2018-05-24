package com.its.web.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * XSS漏洞拦截处理（将JS标签转义）
 *
 */
public class XssFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(new XssHttpServletRequest((HttpServletRequest) request), response);  
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
	
}
