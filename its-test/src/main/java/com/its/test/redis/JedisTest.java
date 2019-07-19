package com.its.test.redis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
/**
 * 
 * @author tzz
 * @工号: 
 * @date 2019/07/06
 * @Introduce: 单机简单例子程序
 */
public class JedisTest {
	private Jedis jedis;

	@Before
	public void initJedis() {
		jedis = new Jedis("vm-01-ip", 6379);
		String password = "123456";
		//设置密码
		jedis.auth(password);
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

	/** hash 是一个string类型的field和value的映射表，hash特别适合用于存储对象 */
	@Test
	public void testMap() {
		Map<String, String> map = new HashMap<String, String>(16);
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
		// 因为删除了，所以返回的是null
		System.out.println(jedis.hmget("user", "age")); 
		// 返回key为user的键中存放的值的个数2
		System.out.println(jedis.hlen("user")); 
		// 是否存在key为user的记录 返回true
		System.out.println(jedis.exists("user"));
		// 返回map对象中的所有key
		System.out.println(jedis.hkeys("user"));
		// 返回map对象中的所有value
		System.out.println(jedis.hvals("user"));

		Iterator<String> iter = jedis.hkeys("user").iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key + ":" + jedis.hmget("user", key));
		}
	}

    /** List 按照插入顺序排序。你可以添加一个元素到列表的头部（左边）或者尾部（右边） 一个列表最多可以包含 232 - 1 个元素 (4294967295, 每个列表超过40亿个元素) */
    @Test
	public void testList() {
		// 开始前，先移除所有的内容
		jedis.del("test-list");
		System.out.println(jedis.lrange("test-list", 0, -1));
		jedis.lpush("test-list", "1");
		jedis.lpush("test-list", "22");
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

	/** Set 是 String 类型的无序集合。集合成员是唯一的，这就意味着集合中不能出现重复的数据*/
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
        // 获取所有加入的value
        System.out.println(jedis.smembers("test-set"));
        // 判断 who
        System.out.println(jedis.sismember("test-set", "who"));
        // 是否是user集合的元素
        System.out.println(jedis.srandmember("test-set"));
        // 返回集合的元素个数
        System.out.println(jedis.scard("test-set"));
    }

    /**
     * sorted set Redis 有序集合和集合一样也是string类型元素的集合,且不允许重复的成员。
     * 不同的是每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。
     * 有序集合的成员是唯一的,但分数(score)却可以重复
     */
    @Test
    public void testSortedSet() {
        // 添加
        String key = "test-sorted-set";
        jedis.zadd(key, 1, "a");
        jedis.zadd(key, 3, "b");
        jedis.zadd(key, 2, "c");
        jedis.zadd(key, 3, "d");
        jedis.zadd(key, 4, "e");
        // 第一个是key，第二个是起始位置，第三个是结束位置， -1表示取得所有
        System.out.println(jedis.zrange(key, 0, -1));
        // 移除noname
        jedis.zrem(key, "d");
        System.out.println(jedis.zrangeWithScores(key, 0, -1));
    }

	@Test
	public void test() throws InterruptedException {
		// jedis 排序
		// 注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）
	    // 先清除数据，再加入数据进行测试
		jedis.del("a");
		jedis.rpush("a", "1");
		jedis.lpush("a", "6");
		jedis.lpush("a", "3");
		jedis.lpush("a", "9");
		// [9, 3, 6, 1]
		System.out.println(jedis.lrange("a", 0, -1));
		// [1, 3, 6, 9] //输入排序后结果
		System.out.println(jedis.sort("a")); 
		System.out.println(jedis.lrange("a", 0, -1));
	}

}
