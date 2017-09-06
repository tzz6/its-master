package com.its.servers.facade.cxf.sys.service;

import java.util.List;

import javax.jws.WebService;


import com.its.model.mybatis.dao.domain.SysUser;

@WebService
public interface CXFSysUserFacade {

	public int getSysUserCount(String stCode);

	public List<SysUser> getSysUserList(String stCode, Integer page, Integer rows);
}
