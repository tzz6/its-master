package com.its.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.its.core.hibernate.dao.BaseDao;
import com.its.model.dao.domain.Department;
import com.its.service.DepartmentService;

/**
 * DepartmentServiceImpl实现类
 * 
 * @author Administrator
 * 
 */
@Service("departmentService")
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements DepartmentService {

	@Override
	@Resource(name = "departmentDao")
	public void setBaseDao(BaseDao<Department> baseDao) {
		super.setBaseDao(baseDao);
	}

	/** 保存Department */
	public void saveDepartment(Department d) {
		saveEntity(d);
	}

	/** 查找Department */
	public Department findDepartment(Long id) {
		return getEntity(id);
	}

}
