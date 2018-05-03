package com.its.test.util.json;

import org.junit.Test;

import com.its.common.utils.json.JsonMapper;
import com.its.model.dao.domain.User;

public class JsonMapperTest {

	@Test
	public void test() {
		try {
			User us = new User();
			us.setId(123L);
			us.setName("aa");
			us.setPassword("cc");
//			String json = JsonMapper.nonDefaultMapper().toJson(us);
			String json = "{\"id\":123,\"name\":\"aa\",\"password\":\"cc\"}";
			System.out.println(json);
			User u = JsonMapper.nonDefaultMapper().fromJson(json, User.class);
			System.out.println(u.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
