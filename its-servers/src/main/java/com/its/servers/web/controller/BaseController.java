package com.its.servers.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fasterxml.jackson.databind.ObjectMapper;


public class BaseController {

	/** Date类型数据转换器 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	public void writeJSON(Object status, Object message, HttpServletResponse response) {
		try {
			response.setContentType("text/html; charset=utf-8");

			if (null == status)
				status = "";
			if (null == message)
				message = "";

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", status);
			map.put("message", message);

		    ObjectMapper objectMapper = new ObjectMapper();
//		    JsonGenerator jsonGenerator  = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
//		    jsonGenerator.writeObject(map);  
//		    jsonGenerator.war
			response.getWriter().print(objectMapper.writeValueAsString(map));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	
	@ModelAttribute
	public void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession(true);
	}

	public void responseResult(Object result){
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}