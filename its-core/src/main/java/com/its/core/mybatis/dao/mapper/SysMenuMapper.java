package com.its.core.mybatis.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.its.model.mybatis.dao.domain.SysMenu;


@Repository("sysMenuMapper")
public interface SysMenuMapper {

	public List<SysMenu> getSysMenus();

	public List<SysMenu> getSysMenuListFirst(@Param("lang") String lang);

	public List<SysMenu> getSysMenuListByParentMenuId(@Param("parentMenuId") String parentMenuId, @Param("lang") String lang);

	public int getSysMenuCount(Map<String, Object> map);

	public List<SysMenu> getSysMenuList(Map<String, Object> map);

	public List<SysMenu> getSysMenuListByUser(Map<String, Object> map);

	public List<SysMenu> getInterceptorUserMenus(Map<String, Object> map);
}
