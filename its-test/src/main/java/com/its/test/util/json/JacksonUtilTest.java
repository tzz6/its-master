package com.its.test.util.json;

import org.junit.Test;

import com.its.common.utils.json.JacksonUtil;
import com.its.model.dao.domain.User;
/**
 * 
 * @author tzz
 */
public class JacksonUtilTest {

	@Test
	public void test() {
		try {
			User us = new User();
			us.setId(123L);
			us.setName("aa");
			us.setPassword("cc");
			//bean转Json
			String json = JacksonUtil.nonDefaultMapper().toJson(us);
			System.out.println(json);
			json = "{\"id\":123,\"name\":\"aa\",\"password\":\"cc\"}"; 
			//Json转bean
			User u = JacksonUtil.nonDefaultMapper().fromJson(json, User.class);
			System.out.println(u.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
