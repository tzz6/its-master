package com.its.web.controller.ws;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.its.servers.facade.cxf.sys.service.CXFSysUserFacade;
import com.its.servers.facade.cxf.sys.service.SysUser;
import com.its.servers.facade.cxf.sys.service.impl.CxfSysUserFacadeImpl;
import com.its.web.model.Datagrid;

@Controller
public class CXFController {

	/** CXF调用列表页 */
	@RequestMapping(value = "/ws/cxfIndex", method = RequestMethod.GET)
	public String cxfIndex(ModelMap model) throws MalformedURLException {
		model.put("url", "/ws/cxfManager");
		return "/ws/wsManager";
	}

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
	@RequestMapping("/ws/cxfManager")
	public @ResponseBody Datagrid<SysUser> cxfManager(HttpServletRequest request,
			@RequestParam(value = "stCode", required = false) String stCode, @RequestParam(value = "page") Integer page,
			@RequestParam(value = "rows") Integer rows) throws MalformedURLException {
		String wsUserServiceUrl = "http://127.0.0.1:8080/cxf/cxfSysUserFacade?wsdl";
		URL url = new URL(wsUserServiceUrl);
		CxfSysUserFacadeImpl wsSysUserFacadeImpl = new CxfSysUserFacadeImpl(url);
		CXFSysUserFacade wsSysUserFacade = wsSysUserFacadeImpl.getCXFSysUserFacadeImplPort();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stCode", stCode);
		int total = wsSysUserFacade.getSysUserCount(stCode);
		int startNum = (page - 1) * rows;
		map.put("startNum", startNum);
		map.put("rows", rows);
		List<SysUser> result = wsSysUserFacade.getSysUserList(stCode, page, rows);
		Datagrid<SysUser> datagrid = new Datagrid<SysUser>(total, result);
		return datagrid;
	}
}
