package com.its.servers.facade.axis.sys.service;

import java.util.List;

import com.its.model.mybatis.dao.domain.SysUser;

public interface AxisSysUserFacade {

	public int getSysUserCount(String stCode);

	public List<SysUser> getSysUserList(String stCode, Integer page, Integer rows);
}
