package com.its.servers.facade.hessian.sys.service;


import java.util.List;
import java.util.Map;
import java.util.Set;

import com.its.model.bean.QueryResultData;
import com.its.model.dao.domain.Department;
import com.its.model.dao.domain.Role;
import com.its.model.dao.domain.User;


public interface HsUserService {

	User login(String userName, String password);
	
	Set<Role> queryRoleByUserName(String userName);
	
	QueryResultData<User> queryUserByPage(int pageNum, int pageSize, Map<String, Object> conditionMap);
	
	QueryResultData<User> queryAllUser(Map<String, Object> conditionMap);
	
	List<Role> findAllRole();
	
	List<Department> findAllDepartment();
	
	void saveUser(String username, String password, String regDateStr, String sex, String departmentId, String roles);
	
	User deleteUser(User user);
}
