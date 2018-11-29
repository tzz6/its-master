package com.its.test.util.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.its.test.util.json.entity.Role;
import com.its.test.util.json.entity.User;

public class GsonTest {
	private User bean = null;

	@Before
	public void init() {
		bean = new User();
		bean.setId(1);
		bean.setName("Test");
		bean.setAge(20);
		bean.setSex("1");
		bean.setBirthday(new Date());
		Role role = new Role();
		role.setId(1);
		role.setName("角色1");
		Role role2 = new Role();
		role2.setId(2);
		role2.setName("角色2");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		roles.add(role2);
		bean.setRoles(roles);
	}

	public final void print(String string) {
		System.out.println(string);
	}

	@Test
	public void testObject2JSON() {

		// 1:Gson gson=newGson();
		// 2:通过GsonBuilder 可以配置多种选项
		// Gson gson = new GsonBuilder()
		// .setLenient()// json宽松
		// .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
		// .serializeNulls() //智能null
		// .setPrettyPrinting()// 调教格式
		// .disableHtmlEscaping() //默认是GSON把HTML 转义的
		Gson gson = new Gson();
		print(gson.toJson(bean));
		// // 转换Java List集合到JSON
		print("***********************Java List >>> JSON*******************************");
		List<User> list = new ArrayList<User>();
		list.add(bean);
		bean.setName("jack");
		list.add(bean);
		print(gson.toJson(list));
		// Map对象转JSON
		print("*************************Java Map >>> JSON*******************************");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("A", bean);
		bean.setName("jack");
		map.put("B", bean);
		map.put("name", "json");
		map.put("bool", Boolean.TRUE);
		map.put("int", new Integer(1));
		map.put("arr", new String[] { "a", "b" });
		map.put("func", "function(i){ return this.arr[i]; }");
		print(gson.toJson(map));
	}

	// Json字符串转成Java对象
	@Test
	public void testJSON2Object() {
		Gson gson = new Gson();
		// Json转Bean
		 String objectJson =
		 "{\"id\":1,\"name\":\"Test\",\"age\":20,\"sex\":\"1\",\"birthday\":Nov 29, 2018 10:27:05 AM,\"roles\":[{\"id\":1,\"name\":\"角色1\"},{\"id\":2,\"name\":\"角色2\"}]}";
//		String objectJson = "{\"id\":1,\"name\":\"Test\",\"age\":20,\"sexNo\":\"1\",\"birthday\":1543406407033,\"roles\":[{\"id\":1,\"name\":\"角色1\"},{\"id\":2,\"name\":\"角色2\"}]}";
		User user = gson.fromJson(gson.toJson(bean), User.class);
		print(user.getName());
		// Json转List
		List<User> list = new ArrayList<User>();
		list.add(bean);
		bean.setName("jack");
		list.add(bean);
		Type type = new TypeToken<ArrayList<User>>() {}.getType();
		List<User> userList = gson.fromJson(gson.toJson(list), type);
		print(userList.get(0).getName());
		print("************************Json转Map********************************");
		// Json转Map
		Map<String, User> maps = new HashMap<>();
		maps.put("user1", user);
		maps.put("user2", user);
		Map<String, User> userMaps = gson.fromJson(gson.toJson(maps),new TypeToken<Map<String, User>>() {}.getType());
		print(userMaps.get("user2").getName());
	}

}
