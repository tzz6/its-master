package com.its.servers.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.its.model.dao.domain.Department;
import com.its.model.dao.domain.Role;
import com.its.service.DepartmentService;
import com.its.service.RoleService;


@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

	private RoleService roleService;

	/** 注入RoleService */
	@Resource(name = "roleService")
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private DepartmentService departmentService;

	/** 注入DepartmentService */
	@Resource(name = "departmentService")
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	/** 到达添加页面 */
	@RequestMapping(value="addRole", method = RequestMethod.GET)
	public ModelAndView toAddRole() {
		// 获取所有的部门信息，用来设置所属的部门
		List<Department> departments = departmentService.findAll();
		ModelAndView model = new ModelAndView();
		model.addObject("departments", departments);
		model.setViewName("/role/addRole");
		return model;
	}

	/** 保存 */
	@RequestMapping(value="save", method = RequestMethod.POST)
	public String saveRole(@RequestParam("dId") Long dId, Role role) {
		// 获取所在的部门id-->得到相应的部门
		Department d = departmentService.findDepartment(dId);
		role.setDepartment(d);
		roleService.addRole(role);
		// 跳转到list
		return "redirect:/role/list";
	}

	/** 角色列表list */
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView model = new ModelAndView();
		List<Role> roles = roleService.findAll();
		model.addObject("roles", roles);
		model.setViewName("/role/roleList");
		return model;
	}

	/** 角色列表list */
	@RequestMapping(value = "/delete/{roleId}")
	public String delete(@PathVariable("roleId") Long roleId) {
		Role role = roleService.getEntity(roleId);
		roleService.deleteEntity(role);
		return "redirect:/role/list";
	}
}
