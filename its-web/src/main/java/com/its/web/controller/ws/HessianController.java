package com.its.web.controller.ws;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.its.model.mybatis.dao.domain.SysUser;
import com.its.servers.facade.hessian.sys.service.HsSysUserFacade;
import com.its.web.model.Datagrid;

@Controller
public class HessianController {

	@Autowired
	private HsSysUserFacade hsSysUserFacade;

	/** Hessian调用列表页 */
	@RequestMapping(value = "/ws/hessianIndex", method = RequestMethod.GET)
	public String hsIndex(ModelMap model) throws MalformedURLException {
		model.put("url", "/ws/hessianManager");
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
	 */
	@RequestMapping("/ws/hessianManager")
	public @ResponseBody Datagrid<SysUser> hessianManager(HttpServletRequest request,
			@RequestParam(value = "stCode", required = false) String stCode, @RequestParam(value = "page") Integer page,
			@RequestParam(value = "rows") Integer rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stCode", stCode);
		int total = hsSysUserFacade.getSysUserCount(map);
		int startNum = (page - 1) * rows;
		map.put("startNum", startNum);
		map.put("rows", rows);
		List<SysUser> result = hsSysUserFacade.getSysUserList(map);
		Datagrid<SysUser> datagrid = new Datagrid<SysUser>(total, result);
		return datagrid;
	}
}
