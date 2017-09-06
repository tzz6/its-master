package com.its.servers.facade.hessian.sys.service;

import java.util.List;
import java.util.Map;

import com.its.model.mybatis.dao.domain.SysUser;


public interface HsSysUserFacade {
	
	public int getSysUserCount(Map<String, Object> map);

	public List<SysUser> getSysUserList(Map<String, Object> map);
}
