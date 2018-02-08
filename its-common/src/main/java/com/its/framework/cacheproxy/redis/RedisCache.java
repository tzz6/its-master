package com.its.framework.cacheproxy.redis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.its.common.redis.ShardedJedisSentinelPool;
import com.its.framework.cacheproxy.CPException;
import com.its.framework.cacheproxy.ICache;
import com.its.framework.cacheproxy.IKeyVisitor;
import com.its.framework.cacheproxy.serial.DefaultSerializeImpl;
import com.its.framework.cacheproxy.serial.EhSerializeImpl;
import com.its.framework.cacheproxy.serial.ICacheSerialize;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.Pool;
import redis.clients.util.SafeEncoder;

public class RedisCache implements ICache, InitializingBean {
	private static Logger logger = LoggerFactory.getLogger(RedisCache.class);
	private static final int DEFAULT_IDLE_COUNT = 10;
	private static final int DEFAULT_MAX_WAIT_MILLIS = 5000;
	private static final byte[] SCAN_INIT_CURSOR = SafeEncoder.encode("0");
	private Pool<ShardedJedis> pool;
	private RedisConfig config;
	private ICacheSerialize serialize;

	public RedisCache() {
	}

	public RedisCache(String sentinels, String masters) {
		this.config = new RedisConfig();
		this.config.setServers(sentinels);
		this.config.setMasters(masters);
		this.config.setSentinel(true);
		init();
	}

	public RedisCache(String servers) {
		this.config = new RedisConfig();
		this.config.setServers(servers);
		this.config.setSentinel(false);
		init();
	}

	public RedisCache(RedisConfig config) {
		this.config = config;
		init();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.pool == null)
			init();
	}

	// 初始化
	private void init() {
		if (this.config.getServers() == null) {
			throw new CPException("servers is null, format: ip1:port1,ip2:port2");
		}
		if ((this.config.isSentinel()) && (this.config.getMasters() == null)) {
			throw new CPException("sentinel masters is null, format: master1,master2");
		}

		GenericObjectPoolConfig opc = new GenericObjectPoolConfig();
		opc.setMaxTotal(this.config.getPoolSize());
		opc.setMaxIdle(this.config.getPoolSize() > DEFAULT_IDLE_COUNT ? DEFAULT_IDLE_COUNT : this.config.getPoolSize());
		opc.setMaxWaitMillis(DEFAULT_MAX_WAIT_MILLIS);
		List<String> masters = null;
		if (this.config.isSentinel()) {
			Set<String> sentinels = new HashSet<String>(split(this.config.getServers()));
			masters = split(this.config.getMasters());
			this.pool = new ShardedJedisSentinelPool(masters, sentinels, opc, this.config.getPassword());
		} else {
			List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
			for (String server : split(this.config.getServers())) {
				String[] shard = server.split(":");
				JedisShardInfo jedisShard = new JedisShardInfo(shard[0], Integer.parseInt(shard[1]));
				jedisShard.setPassword(this.config.getPassword());
				shards.add(jedisShard);
			}
			this.pool = new ShardedJedisPool(opc, shards);
		}

		if (this.serialize == null)
			if (this.config.isEhSerialize())
				this.serialize = new EhSerializeImpl();
			else
				this.serialize = new DefaultSerializeImpl();
	}

	private List<String> split(String s) {
		List<String> list = new ArrayList<String>();
		String sep = s.contains(",") ? "," : " ";
		String[] ss = s.split(sep);
		for (String as : ss) {
			as = as.trim();
			if (as.length() > 0) {
				list.add(as);
			}
		}
		return list;
	}

	public void set(String key, Object value) throws CPException {
		set(key, value, this.config.getTtl());
	}

	/**
	 * @param key
	 * @param value
	 * @param ttl 有效的秒数
	 */
	public void set(String key, Object value, int ttl) throws CPException {
		byte[] keydata = encodeKey(key);
		byte[] valuedata = encodeValue(value);

		call(new IJedisCallback() {
			public void onCall(ShardedJedis sjedis) throws CPException {
				if (ttl > 0)
					sjedis.setex(keydata, ttl, valuedata);
				else
					sjedis.set(keydata, valuedata);
			}
		});
	}

	public void set(Map<String, ?> kv) throws CPException {
		set(kv, this.config.getTtl());
	}

	public void set(Map<String, ?> kv, int ttl) throws CPException {
		call(new IJedisCallback() {
			public void onCall(ShardedJedis sjedis) throws CPException {
				Map<Jedis, List<byte[]>> shardedKvs = RedisCache.this.shardKeyValues(kv, sjedis);

				if (ttl > 0)
					for (Map.Entry<Jedis, List<byte[]>> entry : shardedKvs.entrySet()) {
						byte[][] keysvalues = (byte[][]) ((List) entry.getValue())
								.toArray(new byte[((List) entry.getValue()).size()][]);
						Pipeline pipeline = ((Jedis) entry.getKey()).pipelined();
						pipeline.mset(keysvalues);

						boolean isKey = false;
						for (byte[] key : keysvalues) {
							isKey = !isKey;
							if (isKey) {
								pipeline.expire(key, ttl);
							}
						}

						pipeline.sync();
					}
				else
					for (Map.Entry entry : shardedKvs.entrySet()) {
						byte[][] keysvalues = (byte[][]) ((List) entry.getValue())
								.toArray(new byte[((List) entry.getValue()).size()][]);
						((Jedis) entry.getKey()).mset(keysvalues);
					}
			}
		});
	}

	public boolean setnx(String key, Object value) throws CPException {
		return setnx(key, value, this.config.getTtl());
	}

	public boolean setnx(String key, Object value, int ttl) throws CPException {
		byte[] keydata = encodeKey(key);
		byte[] valuedata = encodeValue(value);

		return ((Boolean) call(new IJedisResultCallback() {
			public Boolean onCall(ShardedJedis sjedis) throws CPException {
				Long flag = sjedis.setnx(keydata, valuedata);
				boolean result = (flag != null) && (flag.equals(Long.valueOf(1L)));
				if ((result) && (ttl > 0)) {
					sjedis.expire(keydata, ttl);
				}
				return Boolean.valueOf(result);
			}
		})).booleanValue();
	}

	public <T> T getSet(String key, Object value) throws CPException {
		return getSet(key, value, this.config.getTtl());
	}

	public <T> T getSet(String key, Object value, int ttl) throws CPException {
		byte[] keydata = encodeKey(key);
		byte[] valuedata = encodeValue(value);

		return call(new IJedisResultCallback<T>() {
			public T onCall(ShardedJedis sjedis) throws CPException {
				byte[] value = sjedis.getSet(keydata, valuedata);
				if (ttl > 0) {
					sjedis.expire(keydata, ttl);
				}
				if (value == null) {
					return null;
				}
				return (T) RedisCache.this.decodeValue(value);
			}
		});
	}

	public <T> T get(String key) throws CPException {
		byte[] keydata = encodeKey(key);

		return call(new IJedisResultCallback<T>() {
			public T onCall(ShardedJedis sjedis) throws CPException {
				byte[] data = sjedis.get(keydata);
				if (data == null) {
					return null;
				}
				return (T) RedisCache.this.decodeValue(data);
			}
		});
	}

	public <T> Map<String, T> get(Collection<String> keys) throws CPException {
		return (Map) call(new IJedisResultCallback() {
			public Map<String, T> onCall(ShardedJedis sjedis) throws CPException {
				Map result = new HashMap();
				Map<Jedis, List<byte[]>> shardedKeys = RedisCache.this.shardKeys(keys, sjedis);

				for (Map.Entry entry : shardedKeys.entrySet()) {
					byte[][] keydatas = (byte[][]) ((List) entry.getValue())
							.toArray(new byte[((List) entry.getValue()).size()][]);
					List valuedatas = ((Jedis) entry.getKey()).mget(keydatas);

					int i = 0;
					for (int n = keydatas.length; i < n; i++) {
						byte[] data = (byte[]) valuedatas.get(i);
						if ((data != null) && (data.length > 0)) {
							String key = RedisCache.this.decodeKey(keydatas[i]);

							Object value = RedisCache.this.decodeValue(data);
							result.put(key, value);
						}
					}
				}
				return result;
			}
		});
	}

	public Collection<String> keys(String pattern) throws CPException {
		Set<String> result = new HashSet<String>();
		byte[] patterndata = encodePattern(pattern);

		call(new IJedisCallback() {
			public void onCall(ShardedJedis sjedis) throws CPException {
				for (Jedis jedis : sjedis.getAllShards()) {
					Set<byte[]> keys = jedis.keys(patterndata);

					for (byte[] keydata : keys)
						result.add(RedisCache.this.decodeKey(keydata));
				}
			}
		});
		return result;
	}

	public void keys(String pattern, IKeyVisitor keyVisitor) throws CPException {
		ScanParams params = new ScanParams();
		params.match(encodePattern(pattern));
		params.count(1000);

		call(new IJedisCallback() {
			public void onCall(ShardedJedis sjedis) throws CPException {
				for (Jedis jedis : sjedis.getAllShards())
					scanKeys(jedis, params);
			}

			private void scanKeys(Jedis jedis, ScanParams params) {
				byte[] cursor = RedisCache.SCAN_INIT_CURSOR;
				do {
					ScanResult sr = jedis.scan(cursor, params);
					List<byte[]> result = sr.getResult();
					if (result != null) {
						for (byte[] keydata : result) {
							keyVisitor.onVisi(RedisCache.this.decodeKey(keydata));
						}
					}
					cursor = sr.getCursorAsBytes();
				} while (!Arrays.equals(RedisCache.SCAN_INIT_CURSOR, cursor));
			}
		});
	}

	public boolean exists(String key) throws CPException {
		byte[] keydata = encodeKey(key);

		return ((Boolean) call(new IJedisResultCallback() {
			public Boolean onCall(ShardedJedis sjedis) throws CPException {
				return sjedis.exists(keydata);
			}
		})).booleanValue();
	}

	public long ttl(String key) throws CPException {
		byte[] keydata = encodeKey(key);

		return ((Long) call(new IJedisResultCallback() {
			public Long onCall(ShardedJedis sjedis) throws CPException {
				return sjedis.ttl(keydata);
			}
		})).longValue();
	}

	public void setTtl(String key, int ttl) throws CPException {
		byte[] keydata = encodeKey(key);

		call(new IJedisCallback() {
			public void onCall(ShardedJedis sjedis) throws CPException {
				sjedis.expire(keydata, ttl);
			}
		});
	}

	public void remove(String key) throws CPException {
		byte[] keydata = encodeKey(key);

		call(new IJedisCallback() {
			public void onCall(ShardedJedis sjedis) throws CPException {
				sjedis.del(keydata);
			}
		});
	}

	public void remove(Collection<String> keys) throws CPException {
		call(new IJedisCallback() {
			public void onCall(ShardedJedis sjedis) throws CPException {
				Map<Jedis, List<byte[]>> shardedKeys = RedisCache.this.shardKeys(keys, sjedis);
				for (Map.Entry<Jedis, List<byte[]>> entry : shardedKeys.entrySet()) {
					byte[][] keydatas = (byte[][]) ((List) entry.getValue())
							.toArray(new byte[((List) entry.getValue()).size()][]);
					((Jedis) entry.getKey()).del(keydatas);
				}
			}
		});
	}

	public void push(String key, String[] values) {
		byte[] keydata = encodeKey(key);
		byte[][] valuedatas = encodeKeys(values);

		call(new IJedisCallback() {
			public void onCall(ShardedJedis sjedis) throws CPException {
				sjedis.rpush(keydata, valuedatas);
			}
		});
	}

	public void push(String key, int ttl, String[] values) {
		byte[] keydata = encodeKey(key);
		byte[][] valuedatas = encodeKeys(values);

		call(new IJedisCallback() {
			public void onCall(ShardedJedis sjedis) throws CPException {
				sjedis.rpush(keydata, valuedatas);
				if (ttl > 0)
					sjedis.expire(keydata, ttl);
			}
		});
	}

	public String pop(String key) {
		byte[] keydata = encodeKey(key);

		return (String) call(new IJedisResultCallback() {
			public String onCall(ShardedJedis sjedis) throws CPException {
				byte[] value = sjedis.lpop(keydata);
				return value == null ? null : RedisCache.this.decodeKey(value);
			}
		});
	}

	public List<String> members(String key) {
		byte[] keydata = encodeKey(key);

		return (List) call(new IJedisResultCallback() {
			public List<String> onCall(ShardedJedis sjedis) throws CPException {
				List<byte[]> values = sjedis.lrange(keydata, 0L, -1L);
				if (values == null) {
					return null;
				}

				List list = new ArrayList(values.size());
				for (byte[] value : values) {
					list.add(RedisCache.this.decodeKey(value));
				}
				return list;
			}
		});
	}

	public long getCounter(String key) {
		byte[] keydata = encodeKey(key);

		return ((Long) call(new IJedisResultCallback() {
			public Long onCall(ShardedJedis sjedis) throws CPException {
				byte[] data = sjedis.get(keydata);
				return Long.valueOf(data == null ? 0L : Long.parseLong(RedisCache.this.decodeKey(data)));
			}
		})).longValue();
	}

	public long incCounter(String key) {
		byte[] keydata = encodeKey(key);

		return ((Long) call(new IJedisResultCallback() {
			public Long onCall(ShardedJedis sjedis) throws CPException {
				return sjedis.incr(keydata);
			}
		})).longValue();
	}

	public long incCounter(String key, int ttl) {
		byte[] keydata = encodeKey(key);

		return ((Long) call(new IJedisResultCallback() {
			public Long onCall(ShardedJedis sjedis) throws CPException {
				long counter = sjedis.incr(keydata).longValue();
				sjedis.expire(keydata, ttl);
				return Long.valueOf(counter);
			}
		})).longValue();
	}

	public long decCounter(String key) {
		byte[] keydata = encodeKey(key);

		return ((Long) call(new IJedisResultCallback() {
			public Long onCall(ShardedJedis sjedis) throws CPException {
				return sjedis.decr(keydata);
			}
		})).longValue();
	}

	public long decCounter(String key, int ttl) {
		byte[] keydata = encodeKey(key);

		return ((Long) call(new IJedisResultCallback() {
			public Long onCall(ShardedJedis sjedis) throws CPException {
				long counter = sjedis.decr(keydata).longValue();
				sjedis.expire(keydata, ttl);
				return Long.valueOf(counter);
			}
		})).longValue();
	}

	public void zadd(String key, String value, double score) {
		zadd(key, value, score, this.config.getTtl());
	}

	public void zadd(String key, String value, double score, int ttl) {
		byte[] keydata = encodeKey(key);
		byte[] valuedata = encodeKey(value);

		call(new IJedisCallback() {
			public void onCall(ShardedJedis sjedis) throws CPException {
				sjedis.zadd(keydata, score, valuedata);
				if (ttl > 0)
					sjedis.expire(keydata, ttl);
			}
		});
	}

	public void zrem(String key, String value) {
		byte[] keydata = encodeKey(key);
		byte[] valuedata = encodeKey(value);

		call(new IJedisCallback() {
			public void onCall(ShardedJedis sjedis) throws CPException {
				sjedis.zrem(keydata, new byte[][] { valuedata });
			}
		});
	}

	public Set<String> zrange(String key, long start, long end) {
		byte[] keydata = encodeKey(key);

		return (Set) call(new IJedisResultCallback() {
			public Set<String> onCall(ShardedJedis sjedis) throws CPException {
				Set result = null;
				Set<byte[]> resultdata = sjedis.zrange(keydata, start, end);
				if ((resultdata != null) && (resultdata.size() > 0)) {
					result = new HashSet(resultdata.size());
					for (byte[] data : resultdata) {
						result.add(RedisCache.this.decodeKey(data));
					}
				}
				return result;
			}
		});
	}

	public Set<String> zrangeByScore(String key, double min, double max) {
		byte[] keydata = encodeKey(key);

		return (Set) call(new IJedisResultCallback() {
			public Set<String> onCall(ShardedJedis sjedis) throws CPException {
				Set result = null;
				Set<byte[]> resultdata = sjedis.zrangeByScore(keydata, min, max);
				if ((resultdata != null) && (resultdata.size() > 0)) {
					result = new HashSet(resultdata.size());
					for (byte[] data : resultdata) {
						result.add(RedisCache.this.decodeKey(data));
					}
				}
				return result;
			}
		});
	}

	public long zrank(String key, String value) {
		byte[] keydata = encodeKey(key);
		byte[] valuedata = encodeKey(value);

		return ((Long) call(new IJedisResultCallback() {
			public Long onCall(ShardedJedis sjedis) throws CPException {
				Long rank = sjedis.zrank(keydata, valuedata);
				return Long.valueOf(rank == null ? -1L : rank.longValue());
			}
		})).longValue();
	}

	public void hset(String key, String field, String value) {
		hset(key, field, value, this.config.getTtl());
	}

	public void hset(String key, String field, String value, int ttl) {
		byte[] keydata = encodeKey(key);
		byte[] fielddata = encodeKey(field);
		byte[] valuedata = encodeKey(value);

		call(new IJedisCallback() {
			public void onCall(ShardedJedis sjedis) throws CPException {
				sjedis.hset(keydata, fielddata, valuedata);
				if (ttl > 0)
					sjedis.expire(keydata, ttl);
			}
		});
	}

	public void hdel(String key, String field) {
		byte[] keydata = encodeKey(key);
		byte[] fielddata = encodeKey(field);

		call(new IJedisCallback() {
			public void onCall(ShardedJedis sjedis) throws CPException {
				sjedis.hdel(keydata, new byte[][] { fielddata });
			}
		});
	}

	public Set<String> hkeys(String key) {
		byte[] keydata = encodeKey(key);

		return (Set) call(new IJedisResultCallback() {
			public Set<String> onCall(ShardedJedis sjedis) throws CPException {
				Set<byte[]> keysdata = sjedis.hkeys(keydata);
				if (keysdata == null) {
					return null;
				}
				Set keys = new HashSet();
				for (byte[] data : keysdata) {
					keys.add(RedisCache.this.decodeKey(data));
				}
				return keys;
			}
		});
	}

	public String hget(String key, String field) {
		byte[] keydata = encodeKey(key);
		byte[] fielddata = encodeKey(field);

		return (String) call(new IJedisResultCallback() {
			public String onCall(ShardedJedis sjedis) throws CPException {
				byte[] data = sjedis.hget(keydata, fielddata);
				return RedisCache.this.decodeKey(data);
			}
		});
	}

	public Map<String, String> hgetAll(String key) {
		byte[] keydata = encodeKey(key);

		return (Map) call(new IJedisResultCallback() {
			public Map<String, String> onCall(ShardedJedis sjedis) throws CPException {
				Map<byte[], byte[]> map = sjedis.hgetAll(keydata);
				if (map == null) {
					return null;
				}
				Map result = new HashMap();
				for (Map.Entry entry : map.entrySet()) {
					result.put(RedisCache.this.decodeKey((byte[]) entry.getKey()),
							RedisCache.this.decodeKey((byte[]) entry.getValue()));
				}
				return result;
			}
		});
	}

	public void sadd(String key, String member) {
		byte[] keydata = encodeKey(key);
		byte[] memberdata = encodeKey(member);

		call(new IJedisCallback() {
			public void onCall(ShardedJedis sjedis) throws CPException {
				sjedis.sadd(keydata, new byte[][] { memberdata });
			}
		});
	}

	public void srem(String key, String member) {
		byte[] keydata = encodeKey(key);
		byte[] memberdata = encodeKey(member);

		call(new IJedisCallback() {
			public void onCall(ShardedJedis sjedis) throws CPException {
				sjedis.srem(keydata, new byte[][] { memberdata });
			}
		});
	}

	public Set<String> smembers(String key) {
		byte[] keydata = encodeKey(key);

		return (Set) call(new IJedisResultCallback() {
			public Set<String> onCall(ShardedJedis sjedis) throws CPException {
				Set<byte[]> memberdataList = sjedis.smembers(keydata);
				if (memberdataList == null) {
					return null;
				}
				Set memberList = new HashSet();
				for (byte[] data : memberdataList) {
					memberList.add(RedisCache.this.decodeKey(data));
				}
				return memberList;
			}
		});
	}

	public boolean sismember(String key, String member) {
		byte[] keydata = encodeKey(key);
		byte[] memberdata = encodeKey(member);

		return ((Boolean) call(new IJedisResultCallback() {
			public Boolean onCall(ShardedJedis sjedis) throws CPException {
				return sjedis.sismember(keydata, memberdata);
			}
		})).booleanValue();
	}

	public void setSerialize(ICacheSerialize serialize) {
		this.serialize = serialize;
	}

	private byte[] encodeKey(String key) {
		if (key == null) {
			throw new CPException("cache key can not be null.");
		}
		return SafeEncoder.encode(key);
	}

	private byte[][] encodeKeys(String[] keys) {
		byte[][] keydatas = new byte[keys.length][];

		int i = 0;
		for (int n = keys.length; i < n; i++) {
			keydatas[i] = encodeKey(keys[i]);
		}

		return keydatas;
	}

	private String decodeKey(byte[] data) {
		if (data == null) {
			return null;
		}
		return SafeEncoder.encode(data);
	}

	private byte[] encodeValue(Object obj) {
		if (obj == null) {
			throw new CPException("cache value can not be null.");
		}
		return this.serialize.encode(obj);
	}

	private Object decodeValue(byte[] data) {
		if (data == null) {
			return null;
		}
		return this.serialize.decode(data);
	}

	private void call(IJedisCallback jc) throws CPException {
		ShardedJedis sjedis = null;
		try {
			sjedis = (ShardedJedis) this.pool.getResource();
			trycall(sjedis, jc);
			returnJedis(sjedis, false);
		} catch (Exception e) {
			returnJedis(sjedis, true);
			throw new CPException("call redis error.", e);
		}
	}

	private void trycall(ShardedJedis sjedis, IJedisCallback jc) throws Exception {
		try {
			jc.onCall(sjedis);
		} catch (JedisConnectionException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("redis connection broken.", e);
			}
			sjedis.disconnect();
			jc.onCall(sjedis);
		}
	}

	private <T> T call(IJedisResultCallback<T> jrc) throws CPException {
		ShardedJedis sjedis = null;
		try {
			sjedis = (ShardedJedis) this.pool.getResource();
			Object result = trycall(sjedis, jrc);
			returnJedis(sjedis, false);
			return (T) result;
		} catch (Exception e) {
			returnJedis(sjedis, true);
			throw new CPException("call redis error.", e);
		}
	}

	private <T> T trycall(ShardedJedis sjedis, IJedisResultCallback<T> jrc) throws Exception {
		try {
			return jrc.onCall(sjedis);
		} catch (JedisConnectionException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("redis connection broken.", e);
			}
			sjedis.disconnect();
		}
		return jrc.onCall(sjedis);
	}

	private void returnJedis(ShardedJedis sjedis, boolean error) {
		try {
			if (error)
				this.pool.returnBrokenResource(sjedis);
			else
				this.pool.returnResource(sjedis);
		} catch (Exception e) {
			if (logger.isWarnEnabled())
				logger.warn("return sharded jedis to pool error.", e);
		}
	}

	private Map<Jedis, List<byte[]>> shardKeyValues(Map<String, ?> kv, ShardedJedis sjedis) {
		Map shardedKvs = new HashMap();
		for (Map.Entry entry : kv.entrySet()) {
			byte[] keydata = encodeKey((String) entry.getKey());
			byte[] valuedata = encodeValue(entry.getValue());

			Jedis jedis = (Jedis) sjedis.getShard(keydata);
			List list = (List) shardedKvs.get(jedis);
			if (list == null) {
				list = new ArrayList();
				shardedKvs.put(jedis, list);
			}
			list.add(keydata);
			list.add(valuedata);
		}

		return shardedKvs;
	}

	private Map<Jedis, List<byte[]>> shardKeys(Collection<String> keys, ShardedJedis sjedis) {
		Map shardedKeys = new HashMap();
		for (String key : keys) {
			byte[] keydata = encodeKey(key);
			Jedis jedis = (Jedis) sjedis.getShard(keydata);
			List list = (List) shardedKeys.get(jedis);
			if (list == null) {
				list = new ArrayList();
				shardedKeys.put(jedis, list);
			}
			list.add(keydata);
		}

		return shardedKeys;
	}

	private byte[] encodePattern(String pattern) {
		if ((pattern == null) || (pattern.length() == 0)) {
			pattern = "*";
		}
		return encodeKey(pattern);
	}

	public void testShard(String key) {
		byte[] keydata = encodeKey(key);

		call(new IJedisCallback() {
			public void onCall(ShardedJedis sjedis) throws CPException {
				Jedis jedis = (Jedis) sjedis.getShard(keydata);
				String info = jedis.info();
				int index1 = info.indexOf("tcp_port:");
				int index2 = info.indexOf("\n", index1);
				String port = info.substring(index1 + 9, index2).trim();
				if (("6370".equals(port)) || ("6371".equals(port)))
					port = "1";
				else {
					port = "2";
				}
				System.out.println(key + ":" + port);
			}
		});
	}
}
