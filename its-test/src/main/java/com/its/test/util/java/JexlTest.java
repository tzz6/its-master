package com.its.test.util.java;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.junit.Test;

/***
 * 测试使用Jexl将字符串转换成可执行的Java代码
 *
 */
public class JexlTest {

	class user {
		private Integer id;
		private String name;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public String printUser(JexlTest.user user) {
		String str = user.getId() + "----" + user.getName();
		System.out.println(str);
		return str;
	}

	public Object convertToCode(String jexlExp, Map<String, Object> map) {
		JexlEngine jexl = new JexlEngine();
		Expression e = jexl.createExpression(jexlExp);
		JexlContext jc = new MapContext();
		for (String key : map.keySet()) {
			jc.set(key, map.get(key));
		}
		if (null == e.evaluate(jc)) {
			return "";
		}
		return e.evaluate(jc);
	}

	@Test
	public void test1() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("money", 2100);
			String expression = "money>=2000&&money<=4000";
			Object code = convertToCode(expression, map);
			System.out.println(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test4() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("customersCode", "00002");
			map.put("ShipmentName", "00002");
			map.put("productType", "C909");
			map.put("declaredValue", 100);
			map.put("actualWeight", 2.0);

			String expression = "(customersCode=='00001'||customersCode=='00002')&&(ShipmentName=='00002')&&productType=='C909'"
					+ "&&!(declaredValue>=200&&declaredValue<=400)&&(actualWeight>=1.0&&actualWeight<=3.0)";
			Object code = convertToCode(expression, map);
			System.out.println(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test2() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("GW", 12.356);
			map.put("VW", 10.124);
			map.put("R", 0);
			String expression = "GW-VW>=R?GW:VW";
			Object code = convertToCode(expression, map);
			System.out.println(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test3() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			JexlTest.user user = new JexlTest.user();
			user.setId(1);
			user.setName("T");
			JexlTest jexlTest = new JexlTest();
			map.put("user", user);
			map.put("jexlTest", jexlTest);
			
			String expression = "jexlTest.printUser(user)";
			Object code = convertToCode(expression, map);
			System.out.println(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
