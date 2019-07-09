package com.its.test.util.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.its.test.util.json.entity.Role;
import com.its.test.util.json.entity.User;
/**
 * 
 * @author tzz
 */
public class JacksonTest {
	private JsonGenerator jsonGenerator = null;
	private ObjectMapper objectMapper = null;
	private User bean = null;

	@SuppressWarnings("deprecation")
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
		objectMapper = new ObjectMapper();
		try {
			jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@After
	public void destory() {
		try {
			if (jsonGenerator != null) {
				jsonGenerator.flush();
			}
			if (!jsonGenerator.isClosed()) {
				jsonGenerator.close();
			}
			jsonGenerator = null;
			objectMapper = null;
			bean = null;
			System.gc();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final void print(String string) {
		System.out.println(string);
	}

	@Test
	public void testObject2JSON() {
		try {
			print("***********************jsonGenerator*******************************");
			jsonGenerator.writeObject(bean);
			print("***********************ObjectMapper*******************************");
			print(objectMapper.writeValueAsString(bean));
			// 转换Java List集合到JSON
			print("***********************Java List >>> JSON*******************************");
			List<User> list = new ArrayList<User>();
			list.add(bean);
			bean.setName("jack");
			list.add(bean);
			print(objectMapper.writeValueAsString(list));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testObject2JSON2() {
	    try {
	        // Map对象转JSON
	        print("*************************Java Map >>> JSON*******************************");
	        Map<String, Object> map = new HashMap<String, Object>(16);
	        map.put("A", bean);
	        bean.setName("jack");
	        map.put("B", bean);
	        map.put("name", "json");
	        map.put("bool", Boolean.TRUE);
	        map.put("int", new Integer(1));
	        map.put("arr", new String[] { "a", "b" });
	        map.put("func", "function(i){ return this.arr[i]; }");
	        print(objectMapper.writeValueAsString(map));
	        
	        print("*************************其他*******************************");
	        String[] arr = { "a", "b", "c" };
	        System.out.println("jsonGenerator");
	        String str = "hello world jackson!";
	        // byte
	        jsonGenerator.writeBinary(str.getBytes());
	        // boolean
	        jsonGenerator.writeBoolean(true);
	        // null
	        jsonGenerator.writeNull();
	        // float
	        jsonGenerator.writeNumber(2.2f);
	        // char
	        jsonGenerator.writeRaw("c");
	        // String
	        jsonGenerator.writeRaw(str, 5, 10);
	        // String
	        jsonGenerator.writeRawValue(str, 5, 5);
	        // String
	        jsonGenerator.writeString(str);
	        jsonGenerator.writeTree(JsonNodeFactory.instance.pojoNode(str));
	        System.out.println();
	        
	        // Object
	        jsonGenerator.writeStartObject();// {
	        // user:{
	        jsonGenerator.writeObjectFieldStart("user");
	        // name:jackson
	        jsonGenerator.writeStringField("name", "jackson");
	        // sex:true
	        jsonGenerator.writeBooleanField("sex", true);
	        // age:22
	        jsonGenerator.writeNumberField("age", 22);
	        // }
	        jsonGenerator.writeEndObject();
	        
	        // infos:[
	        jsonGenerator.writeArrayFieldStart("infos");
	        // 22
	        jsonGenerator.writeNumber(22);
	        // this is array
	        jsonGenerator.writeString("this is array");
	        // ]
	        jsonGenerator.writeEndArray();
	        
	        // }
	        jsonGenerator.writeEndObject();
	        
	        // complex Object
	        // {
	        jsonGenerator.writeStartObject();
	        // user:{bean}
	        jsonGenerator.writeObjectField("user", bean);
	        // infos:[array]
	        jsonGenerator.writeObjectField("infos", arr);
	        // }
	        jsonGenerator.writeEndObject();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	/** Json字符串转成Java对象 */
	@SuppressWarnings("unchecked")
	@Test
	public void testJSON2Object() {
		try {
			// Json转Bean
			String objectJson = "{\"id\":1,\"name\":\"Test\",\"age\":20,\"sex\":\"1\",\"birthday\":1543406407033,\"roles\":[{\"id\":1,\"name\":\"角色1\"},{\"id\":2,\"name\":\"角色2\"}]}";
			User user = objectMapper.readValue(objectJson, User.class);
			print(user.getName());
			user = objectMapper.readValue(objectMapper.writeValueAsString(bean), User.class);
			print(user.getRoles().get(0).getName());
			// Json转List
			List<User> list = new ArrayList<User>();
			list.add(bean);
			bean.setName("jack");
			list.add(bean);
			List<LinkedHashMap<String, Object>> users = objectMapper.readValue(objectMapper.writeValueAsString(list), List.class);
			for (int i = 0; i < list.size(); i++) {
	            Map<String, Object> map = users.get(i);
	            Set<String> set = map.keySet();
	            for (Iterator<String> it = set.iterator();it.hasNext();) {
	                String key = it.next();
	                System.out.println(key + ":" + map.get(key));
	            }
	        }
			print("************************Json转Map********************************");
			// Json转Map
			Map<String, User> maps = new HashMap<>(16);
			maps.put("user1", user);
			maps.put("user2", user);
			Map<String, Map<String, Object>> userMaps = objectMapper.readValue(objectMapper.writeValueAsString(maps), Map.class);
			print(userMaps.get("user1").toString());
//			jsonObject = JSONObject.fromObject(mapJson);
//
//			Map<String, Class<?>> clazzMap = new HashMap<String, Class<?>>();// JSON字符串不能直接转化为map对象，要想取得map中的键对应的值需要别的方式，
//			clazzMap.put("user1", User.class);
//			clazzMap.put("user2", User.class);
//			Map<String, User> jsonToMap = (Map<String, User>) JSONObject.toBean(jsonObject, Map.class, clazzMap);
//			System.out.println(jsonToMap.get("user2").getName()); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
