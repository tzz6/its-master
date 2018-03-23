package com.its.framework.cacheproxy;

import com.its.framework.cacheproxy.redis.RedisCache;
import com.its.framework.cacheproxy.redis.RedisConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import redis.clients.jedis.Jedis;
@SuppressWarnings({"rawtypes","unchecked","resource","unused"})
public class Test {
	public static final String SCRIPT_1 = "local keys = redis.call(\"keys\", KEYS[1]);\nlocal MAX_TTL = tonumber(KEYS[2]);\nlocal count = 1; \nlocal result = {};\nif (keys and (table.maxn(keys) > 0)) then\n  for index, key in ipairs(keys) do\n    local key = keys[index];\n    local ttl = redis.call(\"ttl\", key);\n    if (ttl > MAX_TTL) then\n      result[count] = key;\n      count = count +  1; \n    end\n  end \nend \nreturn result;";
	public static final String SCRIPT_2 = "local MAX_TTL = tonumber(ARGV[2]);\nlocal resultSet = {};\nlocal keys = {};\nlocal done = false;\nlocal cursor = \"0\"\nlocal index = 1;\nrepeat\n  local result = redis.call(\"SCAN\", cursor, \"match\", ARGV[1], \"count\", 1000)\n  cursor = result[1];\n  keys = result[2];\n  for i, key in ipairs(keys) do\n    local ttl = redis.call(\"ttl\", key);\n    if ttl > MAX_TTL then\n      resultSet[index] = key;\n      index = index + 1;\n    end\n  end\n  if cursor == \"0\" then\n    done = true;\n  end\nuntil done\nreturn resultSet;";

	public static void main(String[] args) throws Exception {
		test4();
	}

	public static void testluascript() {
		Jedis jedis = new Jedis("10.0.132.15", 8084, 100000);
		long time = System.currentTimeMillis();
		String[] params = { "*", "0" };
		ArrayList<String> result = (ArrayList<String>) jedis.eval(
				"local MAX_TTL = tonumber(ARGV[2]);\nlocal resultSet = {};\nlocal keys = {};\nlocal done = false;\nlocal cursor = \"0\"\nlocal index = 1;\nrepeat\n  local result = redis.call(\"SCAN\", cursor, \"match\", ARGV[1], \"count\", 1000)\n  cursor = result[1];\n  keys = result[2];\n  for i, key in ipairs(keys) do\n    local ttl = redis.call(\"ttl\", key);\n    if ttl > MAX_TTL then\n      resultSet[index] = key;\n      index = index + 1;\n    end\n  end\n  if cursor == \"0\" then\n    done = true;\n  end\nuntil done\nreturn resultSet;",
				0, params);
		for (String key : result) {
			if (!key.startsWith("s")) {
				System.out.println(key);
			}
		}
		System.out.println(System.currentTimeMillis() - time);
	}

	public static void test1() {
		RedisConfig cfg = new RedisConfig().setServers("10.0.14.64:6390,10.0.14.64:6391,10.0.14.64:6392")
				.setMasters("shard1,shard2").setEhSerialize(true);
		RedisCache cache = new RedisCache(cfg);

		for (int i = 0; i < 3000; i++)
			cache.testShard("key_" + i);
	}

	public static void test2() {
		RedisConfig cfg = new RedisConfig().setServers("10.0.14.64:6390,10.0.14.64:6391,10.0.14.64:6392")
				.setMasters("shard1,shard2").setEhSerialize(true);
		ICache cache = new RedisCache(cfg);

		Map<String, String> kv = new HashMap<String, String>();
		for (int i = 0; i < 1000; i++) {
			String key = "key_" + i;
			String value = "value_" + i;
			kv.put(key, value);
		}
		cache.set(kv);

		AtomicInteger counter = new AtomicInteger();
		cache.keys("key_1*", new IKeyVisitor() {
			public void onVisi(String key) {
				System.out.println(key);
				counter.incrementAndGet();
				Object value = cache.get(key);
			}
		});
		System.out.println("ok" + counter.get());
	}

	public static void test3() {
		RedisConfig cfg = new RedisConfig().setServers("10.0.14.64:6390,10.0.14.64:6391,10.0.14.64:6392")
				.setMasters("shard1,shard2").setEhSerialize(true);
		RedisCache cache = new RedisCache(cfg);
		System.out.println(cache.getCounter("counter_1"));
		System.out.println(cache.incCounter("counter_1"));
		System.out.println(cache.decCounter("counter_1"));
		System.out.println(cache.decCounter("counter_1"));
		System.out.println(cache.decCounter("counter_1"));
		System.out.println("ok");

		for (int i = 0; i < 1000; i++) {
			cache.push("key_list", new String[] { "value_" + i });
		}
		List values = cache.members("key_list");
		System.out.println(values);
	}

	public static void test4() {
		for (int i = 0; i < 200; i++) {
			int index = new Random().nextInt(10);
			new Thread(new Runnable() {
				public void run() {
					Jedis jedis = new Jedis("10.0.132.15", 8084, 1000000);
					for (int x = 0; x < 10; x++) {
						Map mem = new HashMap();
						String keyPre = "key_" + index + "_";
						for (int i = 0; i < 10000; i++) {
							mem.put(keyPre + i, Double.valueOf(System.currentTimeMillis()));
						}
						jedis.zadd("key1", mem);
						System.out.println(keyPre);
					}
				}
			}).start();
		}
	}
}
