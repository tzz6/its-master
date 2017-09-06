package com.its.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.its.core.hibernate.dao.BaseDao;
import com.its.model.bean.QueryResultData;
import com.its.model.dao.domain.Role;
import com.its.model.dao.domain.User;
import com.its.service.UserService;

/***
 * UserServiceImpl UserServie 实现类
 * 
 * @author Administrator
 * 
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	/** 注入UserDaoImpl */
	/***
	 * 重写该方法，注入UserDao
	 */
	@Override
	@Resource(name = "userDao")
	public void setBaseDao(BaseDao<User> baseDao) {
		super.setBaseDao(baseDao);
	}

	/** 单值检索 */
	public Object uniqueResult(String hql, Serializable... serializables) {
		return uniqueResult(hql, serializables);
	}

	/** 获取所有的User */
	public List<User> findAllUser() {
		List<User> userList = findAll();
		// 强制初始化Role
		for (User user : userList) {
			user.getRoles().size();
		}
		return userList;
	}
	
	public void addUser(User user) {
		saveEntity(user);
	}
	
	@Override
	public User login(String userName, String password) {
		String hql = "FROM User WHERE name = ? and password = ?";
		List<User> users = findEntityByHQL(hql, userName, password);
		if (users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}
	
	@Override
	public Set<Role> queryRoleByUserName(String userName) {
		String hql = "FROM User WHERE name = ?";
		List<User> users = findEntityByHQL(hql, userName);
		if (users != null && users.size() > 0) {
			return users.get(0).getRoles();
		}
		return null;
	}
	
	@Override
	public QueryResultData<User> queryUserByPage(int pageNum, int pageSize, Map<String, Object> conditionMap) {
		String hql = builderQueryUserWhere(conditionMap);

		int totalCount = findEntityByHQL(hql).size();
		if (pageNum <= 0) {
			pageNum = 1;
		}
		if (pageSize <= 0) {
			pageSize = 10;
		}
		int offset = pageSize * (pageNum - 1);
		int endOffset = pageSize;
		
		List<User> users = findEntityByPageHQL(hql, offset, endOffset);
		QueryResultData<User> page = new QueryResultData<User>(totalCount, users);
		return page;
	}
	
	@Override
	public QueryResultData<User> queryAllUser(Map<String, Object> conditionMap) {
		String hql = builderQueryUserWhere(conditionMap);
		List<User> users = findEntityByHQL(hql);
		QueryResultData<User> page = new QueryResultData<User>(users.size(), users);
		return page;
	}

	private String builderQueryUserWhere(Map<String, Object> conditionMap) {
		String hql = "FROM User u ";
		StringBuilder whereStr = null;
		boolean isAnd = false;
		if (conditionMap != null) {
			whereStr = new StringBuilder();
			whereStr.append("WHERE ");
			String username = (String) conditionMap.get("username");
			String sex = (String) conditionMap.get("sex");
			String startDate = (String) conditionMap.get("startDate");
			String endDate = (String) conditionMap.get("endDate");
			if (username != null && !"".equals(username)) {
				isAnd = whereAddAnd(whereStr, isAnd);
				whereStr.append("u.name like '%" + username + "%'");
			}
			if (sex != null && !"".equals(sex)) {
				isAnd = whereAddAnd(whereStr, isAnd);
				whereStr.append("u.sex = '" + sex + "'");
			}
			if (startDate != null && !"".equals(startDate)) {
				isAnd = whereAddAnd(whereStr, isAnd);
				whereStr.append("u.regDate >= '" + startDate + "'");
			}
			if (endDate != null && !"".equals(endDate)) {
				isAnd = whereAddAnd(whereStr, isAnd);
				whereStr.append("u.regDate <= '" + endDate + "'");
			}
			hql = hql + whereStr.toString();
		}
		return hql;
	}
	
	private boolean whereAddAnd(StringBuilder whereStr, boolean isAnd) {
		if(isAnd){
			whereStr.append(" and ");
		}
		return true;
	}
}
