package com.its.test.util.java;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

/***
 * Aviator是一个高性能、轻量级的 java 语言实现的表达式求值引擎 <br>
 *
 */
public class AviatorTest {
    
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
	

	/** 自定义条件表达式方法-数据打印 */
	public String printUser(AviatorTest.user user) {
		String str = user.getId() + "----" + user.getName();
		System.out.println(str);
		return str;
	}

	/** 自定义条件表达式方法-字符串包含 */
	public boolean indexOf(String str, String value) {
		int result = str.indexOf(value);
		return result >= 0 ? true : false;
	}

    public Object execute(String expression) {
        Object obj = null;
        try {
            obj = AviatorEvaluator.execute(expression);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(obj);
        return obj;
    }

    public Object execute(String expression, Map<String, Object> env) {
        Object obj = null;
        try {
            obj = AviatorEvaluator.execute(expression, env);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(obj);
        return obj;
    }


	@Test
	public void testSimple() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("customersCode", "00002");
			map.put("ShipmentName", "00002");
			map.put("productType", "C909");
			map.put("declaredValue", 100);
			map.put("actualWeight", 2.0);
			String expression = "(customersCode=='00001'||customersCode=='00002')&&(ShipmentName=='00002')&&productType=='C909'"
					+ "&&!(declaredValue>=200&&declaredValue<=400)&&(actualWeight>=1.0&&actualWeight<=3.0)";
			execute(expression, map);
			
			Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("GW", 12.356);
            map2.put("VW", 10.124);
            map2.put("R", 0);
            String expression2 = "GW-VW>=R?GW:VW";
            execute(expression2, map2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    /** 调用内置函数 */
    @Test
    public void testInFunction() {
        execute("sysdate()");
        // 求字符串长度
        execute("string.length('hello')");
        // 判断字符串是否包含字符串
        execute("string.contains('hello','h')");
        // 是否以子串开头
        execute("string.startsWith('hello','h')");
        // 是否以子串结尾
        execute("string.endsWith('hello','llo')");
        // 求n次方
        execute("math.pow(-3,2)");
        // 开平方根
        execute("math.sqrt(14.0)");
        // 正弦函数
        execute("math.sin(20)");
    }
    
    
    /**自定义求和*/
    class MySumFunction extends AbstractFunction {
        @Override
        public AviatorObject call(Map<String, Object> env, AviatorObject a, AviatorObject b, AviatorObject c) {
            Number numA = FunctionUtils.getNumberValue(a, env);
            Number numB = FunctionUtils.getNumberValue(b, env);
            Number numC = FunctionUtils.getNumberValue(c, env);
            return new AviatorDouble(numA.doubleValue() + numB.doubleValue() + numC.doubleValue());
        }
        /**方法名*/
        @Override
        public String getName() {
            return "sum";
        }
    }

    /**调用自定义函数*/
    @Test
    public void testCustomFunction() {
        //注册函数  
        AviatorEvaluator.addFunction(new MySumFunction()); 
        String expression = "sum(a,b,c)";  
        Map<String, Object> params = new HashMap<>();  
        params.put("a", 1);  
        params.put("b", 2);  
        params.put("c", 3);  
        execute(expression, params);  
        execute("sum(1, 2, 3)");  
        execute("sum(sum(1, 2, 3), 2, 3)");  
    }
	
    /**数组和集合*/
    @Test
    public void testArraysAndCollections() {
        final List<String> list = new ArrayList<String>();
        list.add("hello");
        list.add(" world");
        final int[] array = new int[3];
        array[0] = 0;
        array[1] = 1;
        array[2] = 3;
        final Map<String, Date> map = new HashMap<String, Date>();
        map.put("date", new Date());
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("list", list);
        env.put("array", array);
        env.put("mmap", map);
        AviatorTest.user user = new AviatorTest.user();
        user.setId(2);
        env.put("user", user);
        execute("list[0]+list[1]", env);
        execute("'array[0]+array[1]+array[2]=' + (array[0]+array[1]+array[2])", env);
        execute("'today is ' + mmap.date +'aviatorTest.user.id'+user", env);
    }
    
}
