package com.its.test.velocity;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.junit.Test;

import com.its.model.dao.domain.User;

/**
 * 
 * @author tzz
 */
public class VelocityUtilTest {

	@Test
	public void generate() {
		try {
		    //P3C:后台输送给页面的变量必须加$!{var}——中间的感叹号。 说明：如果var等于null或者不存在，那么${var}会直接显示在页面
			//注意配置velocity.properties中的模板路径
			Velocity.init("src/main/resources/velocity.properties");
			VelocityContext velocityContext = new VelocityContext();
			// 简单字符串
//			velocityContext.put("xAxisName", "HelloWorld");
			velocityContext.put("yAxisName", "HelloWorld");

			// Bean对象
			User user = new User();
			user.setId(1L);
			user.setName("a1");
			velocityContext.put("user", user);
			
			// List对象
			List<User> users = new ArrayList<User>();
			User user2 = new User();
			user2.setId(2L);
			user2.setName("a2");
			users.add(user);
			users.add(user2);
			velocityContext.put("users", users);

			// Map对象
			Map<String, String> maps = new HashMap<String, String>(16);
			maps.put("1", "Map对象1");
			maps.put("2", "Map对象2");
			velocityContext.put("result", maps);
			
			//if else
			velocityContext.put("flag", true);
			
			//测试赋值
			velocityContext.put("maxValue", 100);
			
			//测试格式化日期
			velocityContext.put("dateTools", new DateTools());
			velocityContext.put("date", new Date());
			
			Template template = Velocity.getTemplate("user.vm");
			StringWriter writer = new StringWriter();
			template.merge(velocityContext, writer);
			System.out.println(writer.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
