package com.its.test.redis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
/**
 * 单机简单例子程序
 *
 */
public class JedisTest {
	private Jedis jedis;

	@Before
	public void initJedis() {
		jedis = new Jedis("vm-02-ip", 6379);
		jedis.auth("123456");//设置密码
	}

	/** 添加 */
	public void addValue(String key, String value) {
		jedis.set(key, value);
	}

	/** 拼接 */
	public void append(String key, String value) {
		jedis.append(key, value);
	}

	/** 设置多个键值对 */
	public void mset() {
		jedis.mset("name", "t", "age", "26", "qq", "123456");
	}

	/** 根据key删除 */
	public void deleteValue(String key) {
		jedis.del(key);
	}

	/** 根据key获取Value */
	public String getValue(String key) {
		return jedis.get(key);
	}

	/** 修改key */
	public void renameKey(String oldkey, String newkey) {
		jedis.rename(oldkey, newkey);
	}

	@Test
	public void testAddValue() {
		try {
			String key = "key";
			addValue(key, "abc");
			String value = getValue(key);
			System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAppend() {
		String key = "key";
		testAddValue();
		append(key, "--ccc");
		String value = getValue(key);
		System.out.println(value);
	}

	@Test
	public void testMset() {
		mset();
		System.out.println(getValue("name") + "-" + getValue("age") + "-" + getValue("qq"));
	}

	@Test
	public void testDeleteValue() {
		String key = "key";
		deleteValue(key);
		String value = getValue(key);
		System.out.println(value);
	}

	@Test
	public void testRenameKey() {
		String key = "key1";
		String newkey = "key2";
		addValue(key, "abc");
		renameKey(key, newkey);
		String value = getValue(newkey);
		System.out.println(value);
	}

	/** Map */
	@Test
	public void testMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "tzz");
		map.put("age", "26");
		map.put("qq", "123456");
		jedis.hmset("user", map);
		// 取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
		// 第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
		List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
		System.out.println(rsmap);

		// 删除map中的某个键值
		jedis.hdel("user", "age");
		System.out.println(jedis.hmget("user", "age")); // 因为删除了，所以返回的是null
		System.out.println(jedis.hlen("user")); // 返回key为user的键中存放的值的个数2
		System.out.println(jedis.exists("user"));// 是否存在key为user的记录 返回true
		System.out.println(jedis.hkeys("user"));// 返回map对象中的所有key
		System.out.println(jedis.hvals("user"));// 返回map对象中的所有value

		Iterator<String> iter = jedis.hkeys("user").iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key + ":" + jedis.hmget("user", key));
		}
	}

	/** 操作List */
	@Test
	public void testList() {
		// 开始前，先移除所有的内容
		jedis.del("test-list");
		System.out.println(jedis.lrange("test-list", 0, -1));
		jedis.lpush("test-list", "1");
		jedis.lpush("test-list", "22");
		jedis.lpush("test-list", "333");
		// 再取出所有数据jedis.lrange是按范围取出，
		// 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
		System.out.println(jedis.lrange("test-list", 0, -1));

		jedis.del("test-list");
		jedis.rpush("test-list", "1");
		jedis.rpush("test-list", "22");
		jedis.rpush("test-list", "333");
		System.out.println(jedis.lrange("test-list", 0, -1));
	}

	/** Set */
	@Test
	public void testSet() {
		// 添加
		jedis.sadd("test-set", "a", "b");
		jedis.sadd("test-set", "b");
		jedis.sadd("test-set", "c");
		jedis.sadd("test-set", "d");
		jedis.sadd("test-set", "e");
		// 移除noname
		jedis.srem("test-set", "who");
		System.out.println(jedis.smembers("test-set"));// 获取所有加入的value
		System.out.println(jedis.sismember("test-set", "who"));// 判断 who
																// 是否是user集合的元素
		System.out.println(jedis.srandmember("test-set"));
		System.out.println(jedis.scard("test-set"));// 返回集合的元素个数
	}

	@Test
	public void test() throws InterruptedException {
		// jedis 排序
		// 注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）
		jedis.del("a");// 先清除数据，再加入数据进行测试
		jedis.rpush("a", "1");
		jedis.lpush("a", "6");
		jedis.lpush("a", "3");
		jedis.lpush("a", "9");
		System.out.println(jedis.lrange("a", 0, -1));// [9, 3, 6, 1]
		System.out.println(jedis.sort("a")); // [1, 3, 6, 9] //输入排序后结果
		System.out.println(jedis.lrange("a", 0, -1));
	}

}
