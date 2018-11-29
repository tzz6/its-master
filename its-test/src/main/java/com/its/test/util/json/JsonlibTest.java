package com.its.test.util.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.its.model.dao.domain.Department;
import com.its.test.util.json.entity.Role;
import com.its.test.util.json.entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONFunction;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.PropertyFilter;
import net.sf.json.xml.XMLSerializer;

/**
 * 用json-lib转换java对象到JSON字符串 读取json字符串到java对象，序列化jsonObject到xml
 * json-lib-version: json-lib-2.3-jdk15.jar 依赖包: commons-beanutils.jar
 * commons-collections-3.2.jar ezmorph-1.0.3.jar commons-lang.jar 
 * commons-logging.jar
 */
public class JsonlibTest {
	private JSONArray jsonArray = null;
	private JSONObject jsonObject = null;

	private User bean = null;

	@Before
	public void init() {
		jsonArray = new JSONArray();
		jsonObject = new JSONObject();
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

	@After
	public void destory() {
		jsonArray = null;
		jsonObject = null;
		bean = null;
		System.gc();
	}

	public final void print(String string) {
		System.out.println(string);
	}

	// Java对象转JSON
	@Test
	public void testObject2JSON() {
		// Java Bean对象转JSON
		print(JSONObject.fromObject(bean).toString());// Java Bean >>> JSON Object
		print(JSONArray.fromObject(bean).toString());// Java Bean >>> JSON Array,array会在最外层套上[]
		print(JSONSerializer.toJSON(bean).toString());// Java Bean >>> JSON  Object

		// 转换Java List集合到JSON
		// 如果你是转换List集合，一定得用JSONArray或是JSONSrializer提供的序列化方法。如果你用JSONObject.fromObject方法转换List会出现异常，
		// 通常使用JSONSrializer这个JSON序列化的方法，它会自动识别你传递的对象的类型，然后转换成相应的JSON字符串。
		print("***********************Java List >>> JSON*******************************");
		List<User> list = new ArrayList<User>();
		list.add(bean);
		bean.setName("jack");
		list.add(bean);
		print(JSONArray.fromObject(list).toString());
		print(JSONSerializer.toJSON(list).toString());

		// Map对象转JSON
		// Map集合有JavaBean、String、Boolean、Integer、以及Array和js的function函数的字符串。
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
		print(JSONObject.fromObject(map).toString());// Java Map >>> JSON Object
		print(JSONArray.fromObject(map).toString());// Java Map >>> JSON Array
		print(JSONSerializer.toJSON(map).toString());// Java Map >>> JSON Object

		// 将更多类型转换成JSON
		// 这里还有一个JSONFunction的对象，可以转换JavaScript的function。可以获取方法参数和方法体。同时，还可以用JSONObject、JSONArray构建Java对象，完成Java对象到JSON字符串的转换。
		String[] sa = { "a", "b", "c" };
		print("==============Java StringArray >>> JSON Array ==================");
		print(JSONArray.fromObject(sa).toString());
		print(JSONSerializer.toJSON(sa).toString());
		Object[] o = { 1, "a", true, 'A', sa };
		print("==============Java Object Array >>> JSON Array ==================");
		print(JSONArray.fromObject(o).toString());
		print(JSONSerializer.toJSON(o).toString());
		print("==============Java String >>> JSON ==================");
		print(JSONArray.fromObject("['json','is','easy']").toString());
		print(JSONObject.fromObject("{'json':'is easy'}").toString());
		print(JSONSerializer.toJSON("['json','is','easy']").toString());
		print("==============Java JSONObject >>> JSON ==================");
		jsonObject = new JSONObject().element("string", "JSON").element("integer", "1").element("double", "2.0")
				.element("boolean", "true");
		print(JSONSerializer.toJSON(jsonObject).toString());

		print("==============Java JSONArray >>> JSON ==================");
		jsonArray = new JSONArray().element("JSON").element("1").element("2.0").element("true");
		print(JSONSerializer.toJSON(jsonArray).toString());

		print("==============Java JSONArray JsonConfig#setArrayMode >>> JSON ==================");
		List<String> input = new ArrayList<>();
		input.add("JSON");
		input.add("1");
		input.add("2.0");
		input.add("true");
		JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(input);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setArrayMode(JsonConfig.MODE_OBJECT_ARRAY);
		Object[] output = (Object[]) JSONSerializer.toJava(jsonArray, jsonConfig);
		System.out.println(output[0]);

		print("==============Java JSONFunction >>> JSON ==================");
		String str = "{'func': function( param ){ doSomethingWithParam(param); }}";
		JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(str);
		JSONFunction func = (JSONFunction) jsonObject.get("func");
		print(func.getParams()[0]);
		print(func.getText());

	}

	// 注意的是使用了JsonConfig这个对象，这个对象可以在序列化的时候对JavaObject的数据进行处理、过滤等
	// 上面的jsonConfig的registerJsonValueProcessor方法可以完成对象值的处理和修改，比如处理生日为null时，给一个特定的值。
	// 同样setJsonPropertyFilter和setJavaPropertyFilter都是完成对转换后的值的处理。
	@Test
	public void test2JSONJsonConfig() {
		print("========================JsonConfig========================");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Department.class, new JsonValueProcessor() {
			public Object processArrayValue(Object value, JsonConfig jsonConfig) {
				if (value == null) {
					return new Date();
				}
				return value;
			}

			public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
				print("key:" + key);
				return value + "##修改过的日期";
			}

		});
		jsonObject = JSONObject.fromObject(bean, jsonConfig);

		print(jsonObject.toString());
		User student = (User) JSONObject.toBean(jsonObject, User.class);
		print(jsonObject.getString("birthday"));
		print(student.toString());

		print("#####################JsonPropertyFilter############################");
		jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				print(source + "%%%" + name + "--" + value);
				// 忽略birthday属性
				if (value != null && Department.class.isAssignableFrom(value.getClass())) {
					return true;
				}
				return false;
			}
		});
		print(JSONObject.fromObject(bean, jsonConfig).toString());
		print("#################JavaPropertyFilter##################");
		jsonConfig.setRootClass(User.class);
		jsonConfig.setJavaPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				print(name + "@" + value + "#" + source);
				if ("id".equals(name) || "email".equals(name)) {
					value = name + "@@";
					return true;
				}
				return false;
			}
		});
		// jsonObject = JSONObject.fromObject(bean, jsonConfig);
		// student = (Student) JSONObject.toBean(jsonObject, Student.class);
		// print(student.toString());
		student = (User) JSONObject.toBean(jsonObject, jsonConfig);
		print("Student:" + student.toString());
	}

	// Json字符串转成Java对象
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Test
	public void testJSON2Object() {
		try {
			// Json转Bean
			String objectJson = "{'age':20,'birthday':{'date':28,'day':3,'hours':18,'minutes':40,'month':10,'seconds':1,'time':1543401601583,'timezoneOffset':-480,'year':118},"
					+ "'id':1,'name':'Test','roles':[{'id':1,'name':'角色1'},{'id':2,'name':'角色2'}],'sex':'1'}";
			print("==============JSON Arry String >>> Java Array ==================");
			jsonObject = JSONObject.fromObject(objectJson);
			User user = (User) JSONObject.toBean(jsonObject, User.class);
			System.out.println(user);
			String arrayJson = "[" + objectJson + "]";
			jsonArray = JSONArray.fromObject(arrayJson);
			print(JSONArray.fromObject(arrayJson).join(""));
			User[] userArray = (User[]) JSONArray.toArray(jsonArray, User.class);
			System.out.println(userArray[0]);
			print("*************************Json转List*******************************");
			// Json转List
			List<User> list = new ArrayList<User>();
			list.add(bean);
			bean.setName("jack");
			list.add(bean);
			arrayJson = JSONArray.fromObject(list).toString();
			jsonArray = JSONArray.fromObject(arrayJson);
			List<User> listBean = JSONArray.toList(jsonArray, User.class);
			System.out.println(listBean.size());
			System.out.println(listBean.get(0).getName());
			print("************************Json转Map********************************");
			// Json转Map
			Map<String, User> maps = new HashMap<>();
			maps.put("user1", user);
			maps.put("user2", user);
			String mapJson = JSONObject.fromObject(maps).toString();
			print(mapJson);
			jsonObject = JSONObject.fromObject(mapJson);

			Map<String, Class<?>> clazzMap = new HashMap<String, Class<?>>();// JSON字符串不能直接转化为map对象，要想取得map中的键对应的值需要别的方式，
			clazzMap.put("user1", User.class);
			clazzMap.put("user2", User.class);
			Map<String, User> jsonToMap = (Map<String, User>) JSONObject.toBean(jsonObject, Map.class, clazzMap);
			System.out.println(jsonToMap.get("user2").getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Java对象转XML
	// 主要运用的是XMLSerializer的write方法，这个方法可以完成java对象到xml的转换，不过你很容易就可以看到这个xml序列化对象，
	// 需要先将java对象转成json对象，然后再将json转换吃xml文档。
	@Test
	public void testObject2XML() {
		XMLSerializer xmlSerializer = new XMLSerializer();
		xmlSerializer.setTypeHintsEnabled(false);
		xmlSerializer.setRootName("root");
		print("==============Java Bean >>> XML ==================");
		// xmlSerializer.setElementName("bean");
		print(xmlSerializer.write(JSONObject.fromObject(bean)));
		String[] sa = { "a", "b", "c" };
		print("==============Java String Array >>> XML ==================");
		print(xmlSerializer.write(JSONArray.fromObject(sa)));
		print(xmlSerializer.write(JSONSerializer.toJSON(sa)));
		Object[] o = { 1, "a", true, 'A', sa };
		print("==============Java Object Array >>> JSON Array ==================");
		print(xmlSerializer.write(JSONArray.fromObject(o)));
		print(xmlSerializer.write(JSONSerializer.toJSON(o)));
		print("==============Java String >>> JSON ==================");
		print(xmlSerializer.write(JSONArray.fromObject("['json','is','easy']")).toString());
		print(xmlSerializer.write(JSONObject.fromObject("{'json':'is easy'}")).toString());
		print(xmlSerializer.write(JSONSerializer.toJSON("['json','is','easy']")).toString());
	}

	// XML转Java对象
	@Test
	public void testXML2Object() {
		XMLSerializer xmlSerializer = new XMLSerializer();
		print("============== XML >>>> Java String Array ==================");
		String[] sa = { "a", "b", "c" };
		jsonArray = (JSONArray) xmlSerializer.read(xmlSerializer.write(JSONArray.fromObject(sa)));
		print(jsonArray.toString());
		String[] s = (String[]) JSONArray.toArray(jsonArray, String.class);
		print(s[0].toString());

		print("==============Java Object Array >>> JSON Array ==================");
		Object[] o = { 1, "a", true, 'A', sa };
		jsonArray = (JSONArray) xmlSerializer.read(xmlSerializer.write(JSONArray.fromObject(o)));
		System.out.println(jsonArray.getInt(0));
		System.out.println(jsonArray.get(1));
		System.out.println(jsonArray.getBoolean(2));
		jsonArray = (JSONArray) xmlSerializer.read(xmlSerializer.write(JSONSerializer.toJSON(o)));
		System.out.println(jsonArray.get(4));

		print("==============Java String >>> JSON ==================");
		jsonArray = (JSONArray) xmlSerializer
				.read(xmlSerializer.write(JSONArray.fromObject("['json','is','easy']")).toString());
		s = (String[]) JSONArray.toArray(jsonArray, String.class);
		print(s[0].toString());
		jsonObject = (JSONObject) xmlSerializer
				.read(xmlSerializer.write(JSONObject.fromObject("{'json':'is easy'}")).toString());
		Object obj = JSONObject.toBean(jsonObject);
		System.out.println(obj);
		jsonArray = (JSONArray) xmlSerializer
				.read(xmlSerializer.write(JSONSerializer.toJSON("['json','is','easy']")).toString());
		s = (String[]) JSONArray.toArray(jsonArray, String.class);
		print(s[1].toString());
	}
}