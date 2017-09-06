package com.its.servers.facade.hessian.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.its.model.mybatis.dao.domain.SysUser;
import com.its.servers.facade.hessian.sys.service.HsSysUserFacade;
import com.its.service.mybatis.SysUserService;

@Service(value = "hsSysUserFacade")
public class HsSysUserFacadeImpl implements HsSysUserFacade {

	protected final transient Logger log = Logger.getLogger(HsSysUserFacadeImpl.class);
	
	@Autowired
	private SysUserService sysUserService;

	@Override
	public int getSysUserCount(Map<String, Object> map) {
		log.info("Hessian Service");
		return sysUserService.getSysUserCount(map);
	}

	@Override
	public List<SysUser> getSysUserList(Map<String, Object> map) {
		log.info("Hessian Service");
		return sysUserService.getSysUserList(map);
	}

}
