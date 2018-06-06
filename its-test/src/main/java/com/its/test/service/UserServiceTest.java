package com.its.test.service;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.its.model.dao.domain.User;
import com.its.service.UserService;
import com.its.test.base.BaseTest;

public class UserServiceTest extends BaseTest{

	@Autowired
	private UserService userService;
	
	@Test
	public void testAddUser(){
		User user = new User();
		user.setName("测试AOP");
		user.setPassword("123456");
		user.setRegDate(new Date());
		userService.addUser(user);
		List<User> users = userService.findAllUser();
		for (User user2 : users) {
			System.out.println(user2.getName());
		}
	}
}
