package com.its.servers.web.cxf.restful.service;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.its.common.utils.json.JacksonUtil;
import com.its.model.bean.Datagrid;
import com.its.model.mybatis.dao.domain.SysUser;
import com.its.servers.facade.dubbo.sys.service.SysUserFacade;

@Path(value = "/cxfRestfulSysUser")
public class CXFRestfulSysUser {

	@Autowired
	private SysUserFacade sysUserFacade;

	@Context
	private UriInfo uriInfo;

	@Context
	private Request request;

	/**
	 * 用户管理列表数据
	 * 
	 * @param request
	 * @param stCode
	 * @param page
	 * @param rows
	 * @return
	 * @throws MalformedURLException
	 */
	@GET
	@Path("/sysUserManager/{stCode}")
	@Produces(MediaType.APPLICATION_JSON)
	public String doRequest(@PathParam("stCode") String stCode, @Context HttpServletRequest servletRequest,
			@Context HttpServletResponse servletResponse) {
		Integer page = 1;
		Integer rows = 10;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stCode", stCode);
		int total = sysUserFacade.getSysUserCount(map);
		int startNum = (page - 1) * rows;
		map.put("startNum", startNum);
		map.put("rows", rows);
		List<SysUser> result = sysUserFacade.getSysUserList(map);
		Datagrid<SysUser> datagrid = new Datagrid<SysUser>(total, result);

		return JacksonUtil.nonEmptyMapper().toJson(datagrid);
	}

}
