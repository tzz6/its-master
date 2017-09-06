package com.its.web.controller.templet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.its.model.dao.domain.User;
import com.its.service.UserService;
import com.its.web.controller.login.BaseController;

@Controller
@RequestMapping("/templet")
public class FreemarkerController extends BaseController {

	@Autowired
	private UserService userService;

	/** freemarker用户信息列表 */
	@RequestMapping("/freemarkerList")
	public String freemarkerList(ModelMap model) {
		// 获取有的User
		List<User> users = userService.findAllUser();
		model.put("users", users);
		return "/freemarker/user_list";
	}
}