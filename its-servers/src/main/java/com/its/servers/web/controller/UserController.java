package com.its.servers.web.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.its.model.dao.domain.Department;
import com.its.model.dao.domain.Role;
import com.its.model.dao.domain.User;
import com.its.service.DepartmentService;
import com.its.service.RoleService;
import com.its.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private RoleService roleService;


	/** 到达添加页面 */
	@RequestMapping(value="/addUser", method = RequestMethod.GET)
	public String toAddUser(ModelMap model) {
		// 部门信息
		List<Department> departments = departmentService.findAll();
		model.put("departments", departments);
		// 岗位信息
		List<Role> roles = roleService.findAll();
		model.put("roles", roles);
		return "/user/addUser";
	}

	/** 保存User */
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String save(@RequestParam("departmentId") Long departmentId, @RequestParam("roleIds") Long[] roleIds,
			User user, BindingResult result) {
		// 得到部门id
		// 得到与id对应的Department
		Department d = departmentService.findDepartment(departmentId);
		// 设置用户与部门的关系
		user.setDepartment(d);
		List<Role> roleList = roleService.getByIds(roleIds);
		// 设置User的Role
		user.setRoles(new HashSet<Role>(roleList));
		user.setRegDate(new Date());
		userService.addUser(user);
		return "redirect:/user/list";
	}

	/** 用户信息列表 */
	@RequestMapping("/list")
	public String list(ModelMap model) {
		// 获取有的User
		List<User> users = userService.findAllUser();
		model.put("users", users);
		return "/user/userList";
	}

	/** 删除 */
	@RequestMapping("/delete/{userId}")
	public String delete(@PathVariable("userId") Long userId) {
		// 获取与id对应的User
		User user = userService.getEntity(userId);
		// 删除User
		userService.deleteEntity(user);
		// 跳转到用户信息列表页面
		return "redirect:/user/list";
	}

	/** 到达修改页面 */
	@RequestMapping(value = "/{userId}/toUpdate", method = RequestMethod.GET)
	public String toUpdate(@PathVariable("userId") Long userId, ModelMap model) {
		// 获取 要修改的User
		User user = userService.getEntity(userId);
		// 准备User，添加到Model中用于修改的数据回显
		 model.put("user", user);
		// 部门信息
		List<Department> departments = departmentService.findAll();
		departments.size();
		model.put("departments", departments);
		// 角色信息
		List<Role> roles = roleService.findAll();
		roles.size();
		model.put("roles", roles);
		return "/user/editUser";

	}
}
