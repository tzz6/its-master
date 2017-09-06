package com.its.test.service;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.its.model.dao.domain.User;
import com.its.service.UserService;
import com.its.test.base.BaseTest;

public class TestUserService extends BaseTest{

	@Autowired
	private UserService userService;
	
	@Test
	public void testAddUser(){
		User user = new User();
		user.setName("测试AOP");
		user.setPassword("123456");
		user.setRegDate(new Date());
		userService.addUser(user);
		userService.findAllUser();
	}
}
