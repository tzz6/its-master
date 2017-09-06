package com.its.service;

import com.its.model.dao.domain.Department;

/**
 * Departmet接口
 * 
 * @author Administrator
 * 
 */
public interface DepartmentService extends BaseService<Department> {

	/** 保存Department */
	public void saveDepartment(Department d);

	/** 查找Department */
	public Department findDepartment(Long id);

}