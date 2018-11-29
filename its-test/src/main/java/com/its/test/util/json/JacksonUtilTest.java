package com.its.test.util.json;

import org.junit.Test;

import com.its.common.utils.json.JacksonUtil;
import com.its.model.dao.domain.User;

public class JacksonUtilTest {

	@Test
	public void test() {
		try {
			User us = new User();
			us.setId(123L);
			us.setName("aa");
			us.setPassword("cc");
			String json = JacksonUtil.nonDefaultMapper().toJson(us);//bean转Json
			System.out.println(json);
			json = "{\"id\":123,\"name\":\"aa\",\"password\":\"cc\"}"; 
			User u = JacksonUtil.nonDefaultMapper().fromJson(json, User.class);//Json转bean
			System.out.println(u.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
