package com.its.test.util.bean;

import org.junit.Test;

import com.its.common.utils.bean.ReflectUtil;
import com.its.model.dao.domain.User;

public class ReflectUtilTest {

	@Test
	public void test() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setName("AAA");
		User user2 = new User();
		ReflectUtil.fieldCopy(user, user2);
		System.out.println("*****************");
		System.out.println(user2.getId());
		System.out.println(user2.getName());
		System.out.println("*****************");
		ReflectUtil.getFields(user2);
		System.out.println("*****************");
		ReflectUtil.setFieldValue(user2, "id", 2L);
		ReflectUtil.setFieldValue(user2, "name", "BBB");
		System.out.println(user2.getId());
		System.out.println(user2.getName());
	}
}
