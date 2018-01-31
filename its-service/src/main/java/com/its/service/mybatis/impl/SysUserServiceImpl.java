package com.its.service.mybatis.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.its.common.utils.BaseException;
import com.its.core.mybatis.dao.mapper.SysUserMapper;
import com.its.model.mybatis.dao.domain.SysUser;
import com.its.service.mybatis.SysUserService;


@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

	private static final Log log = LogFactory.getLog(SysUserServiceImpl.class);

	@Autowired
	private SysUserMapper sysUserMapper;

	public List<SysUser> getSysUserListByStCode(String stCode) {
		try {
			List<SysUser> lst = sysUserMapper.getSysUserListByStCode(stCode);
			return lst;
		} catch (Exception e) {
			log.error("后台查询用户服务错误", e);
			throw new BaseException("后台查询用户服务错误", e);
		}
	}

	@Override
	public void insertSysUser(SysUser sysUser) {
		try {
			sysUserMapper.insertSysUser(sysUser);
		} catch (Exception e) {
			log.error("后台用新增用户服务错误", e);
			throw new BaseException("后台用新增用户服务错误", e);
		}
	}
	
	@Override
	public void updateSysUser(SysUser sysUser) {
		try {
			sysUserMapper.updateSysUser(sysUser);
		} catch (Exception e) {
			log.error("后台用编辑用户服务错误", e);
			throw new BaseException("后台用编辑用户服务错误", e);
		}
	}

	@Override
	public int getSysUserCount(Map<String, Object> map) {
		try {
			return sysUserMapper.getSysUserCount(map);
		} catch (Exception e) {
			log.error("后台查询用户服务错误", e);
			throw new BaseException("后台查询用户服务错误", e);
		}
	}

	@Override
	public List<SysUser> getSysUserList(Map<String, Object> map) {
		try {
			List<SysUser> lst = sysUserMapper.getSysUserList(map);
			return lst;
		} catch (Exception e) {
			log.error("后台查询用户服务错误", e);
			throw new BaseException("后台查询用户服务错误", e);
		}
	}

	@Override
	public SysUser getSysUserByStId(SysUser sysUser) {
		try {
			return  sysUserMapper.getSysUserByStId(sysUser);
		} catch (Exception e) {
			log.error("后台查询用户服务错误", e);
			throw new BaseException("后台查询用户服务错误", e);
		}
	}

	@Override
	public void deleteSysUser(List<String> stIdList) {
		try {
			sysUserMapper.deleteSysUser(stIdList);
		} catch (Exception e) {
			log.error("后台删除用户服务错误", e);
			throw new BaseException("后台删除用户服务错误", e);
		}
		
	}
	
	@Override
	public List<SysUser> getSysUserByMap(Map<String, Object> map) {
		try {
			List<SysUser> lst = sysUserMapper.getSysUserByMap(map);
			return lst;
		} catch (Exception e) {
			log.error("后台查询用户服务错误", e);
			throw new BaseException("后台查询用户服务错误", e);
		}
	}

	@Override
	public void updateSysUserPassword(SysUser sysUser) {
		try {
			sysUserMapper.updateSysUserPassword(sysUser);
		} catch (Exception e) {
			log.error("后台更新用户服务错误", e);
			throw new BaseException("后台更新用户服务错误", e);
		}
	}

	@Override
	public List<SysUser> getAllSysUserList() {
		try {
			return sysUserMapper.getAllSysUserList();
		} catch (Exception e) {
			log.error("后台查询用户服务错误", e);
			throw new BaseException("后台查询用户服务错误", e);
		}
	}

}
