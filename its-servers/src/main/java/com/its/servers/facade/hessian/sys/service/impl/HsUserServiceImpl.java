package com.its.servers.facade.hessian.sys.service.impl;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.its.model.bean.QueryResultData;
import com.its.model.dao.domain.Department;
import com.its.model.dao.domain.Role;
import com.its.model.dao.domain.User;
import com.its.servers.facade.hessian.sys.service.HsUserService;
import com.its.service.DepartmentService;
import com.its.service.RoleService;
import com.its.service.UserService;


@Service(value = "hsUserService")
public class HsUserServiceImpl implements HsUserService{

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private DepartmentService departmentService;

	@Override
	public User login(String userName, String password) {
		return userService.login(userName, password);
	}
	
	@Override
	public Set<Role> queryRoleByUserName(String userName) {
		return userService.queryRoleByUserName(userName);
	}
	
	@Override
	public QueryResultData<User> queryUserByPage(int pageNum, int pageSize, Map<String, Object> conditionMap) {
		return userService.queryUserByPage(pageNum, pageSize, conditionMap);
	}
	
	@Override
	public QueryResultData<User> queryAllUser(Map<String, Object> conditionMap) {
		return userService.queryAllUser(conditionMap);
	}
	
	@Override
	public List<Role> findAllRole() {
		return roleService.findAll();
	}
	
	@Override
	public List<Department> findAllDepartment() {
		return departmentService.findAll();
	}
	
	@Override
	public void saveUser(String username, String password, String regDateStr, String sex, String departmentId,
			String roles) {
		User user = new User();
		user.setName(username);
		user.setPassword(password);
		user.setSex(sex);
		// 得到部门id
		// 得到与id对应的Department
		Department d = departmentService.findDepartment(Long.parseLong(departmentId));
		// 设置用户与部门的关系
		user.setDepartment(d);
		String[] roleArry = roles.split(",");
		Long[] roleIds = new Long[roleArry.length];
		for (int i = 0; i < roleArry.length; i++) {
			roleIds[i] = Long.parseLong(roleArry[i]);
		}
		List<Role> roleList = roleService.getByIds(roleIds);
		// 设置User的Role
		user.setRoles(new HashSet<Role>(roleList));
		user.setRegDate(new Date());
		userService.addUser(user);
	}
	
	@Override
	public User deleteUser(User user) {
		userService.deleteEntity(user);
		return user;
	}

}
 