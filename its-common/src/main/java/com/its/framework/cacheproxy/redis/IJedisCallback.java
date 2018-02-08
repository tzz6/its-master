package com.its.framework.cacheproxy.redis;

import com.its.framework.cacheproxy.CPException;
import redis.clients.jedis.ShardedJedis;

abstract interface IJedisCallback {
	public abstract void onCall(ShardedJedis paramShardedJedis) throws CPException;
}
