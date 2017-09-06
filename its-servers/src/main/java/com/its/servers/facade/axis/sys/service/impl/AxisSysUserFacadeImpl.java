package com.its.servers.facade.axis.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.its.model.mybatis.dao.domain.SysUser;
import com.its.servers.facade.axis.sys.service.AxisSysUserFacade;
import com.its.service.mybatis.SysUserService;

public class AxisSysUserFacadeImpl implements AxisSysUserFacade {

	protected final transient Logger log = Logger.getLogger(AxisSysUserFacadeImpl.class);

	@Autowired
	private SysUserService sysUserService;

	@Override
	public int getSysUserCount(String stCode) {
		log.info("WS Axis Service");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stCode", stCode);
		return sysUserService.getSysUserCount(map);
	}

	@Override
	public List<SysUser> getSysUserList(String stCode, Integer page, Integer rows) {
		log.info("WS Axis Service");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stCode", stCode);
		int startNum = (page - 1) * rows;
		map.put("startNum", startNum);
		map.put("rows", rows);
		return sysUserService.getSysUserList(map);
	}

}
