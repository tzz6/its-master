package com.its.service.mybatis.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.its.common.utils.BaseException;
import com.its.core.mybatis.dao.mapper.SysMenuMapper;
import com.its.model.mybatis.dao.domain.SysMenu;
import com.its.service.mybatis.SysMenuService;


@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {

	private static final Log log = LogFactory.getLog(SysMenuService.class);

	@Autowired
	private SysMenuMapper sysMenuMapper;

	public List<SysMenu> getSysMenus() {
		try {
			List<SysMenu> list = sysMenuMapper.getSysMenus();
			return list;
		} catch (Exception e) {
			log.error("后台查询SysMenu错误", e);
			throw new BaseException("后台查询SysMenu错误", e);
		}
	}

	@Override
	public List<SysMenu> getSysMenuListFirst(String lang) {
		try {
			List<SysMenu> list = sysMenuMapper.getSysMenuListFirst(lang);
			return list;
		} catch (Exception e) {
			log.error("后台查询SysMenu错误", e);
			throw new BaseException("后台查询SysMenu错误", e);
		}
	}

	@Override
	public List<SysMenu> getSysMenuListByParentMenuId(String parentMenuId, String lang) {
		try {
			List<SysMenu> list = sysMenuMapper.getSysMenuListByParentMenuId(parentMenuId, lang);
			return list;
		} catch (Exception e) {
			log.error("后台查询SysMenu错误", e);
			throw new BaseException("后台查询SysMenu错误", e);
		}
	}

	@Override
	public int getSysMenuCount(Map<String, Object> map) {
		try {
			return sysMenuMapper.getSysMenuCount(map);
		} catch (Exception e) {
			log.error("后台查询SysMenu错误", e);
			throw new BaseException("后台查询SysMenu错误", e);
		}
	}

	@Override
	public List<SysMenu> getSysMenuList(Map<String, Object> map) {
		try {
			return sysMenuMapper.getSysMenuList(map);
		} catch (Exception e) {
			log.error("后台查询SysMenu错误", e);
			throw new BaseException("后台查询SysMenu错误", e);
		}
	}

	@Override
	public List<SysMenu> getSysMenuListByUser(Map<String, Object> map) {
		try {
			List<SysMenu> list = sysMenuMapper.getSysMenuListByUser(map);
			return list;
		} catch (Exception e) {
			log.error("后台查询SysMenu错误", e);
			throw new BaseException("后台查询SysMenu错误", e);
		}
	}

	@Override
	public List<SysMenu> getInterceptorUserMenus(Map<String, Object> map) {
		try {
			List<SysMenu> list = sysMenuMapper.getInterceptorUserMenus(map);
			return list;
		} catch (Exception e) {
			log.error("后台查询SysMenu错误", e);
			throw new BaseException("后台查询SysMenu错误", e);
		}
	}

}
