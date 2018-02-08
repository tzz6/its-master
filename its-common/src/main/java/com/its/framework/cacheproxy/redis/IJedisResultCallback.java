package com.its.framework.cacheproxy.redis;

import com.its.framework.cacheproxy.CPException;
import redis.clients.jedis.ShardedJedis;

abstract interface IJedisResultCallback<T> {
	public abstract T onCall(ShardedJedis paramShardedJedis) throws CPException;
}
