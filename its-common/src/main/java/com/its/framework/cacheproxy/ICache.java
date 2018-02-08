package com.its.framework.cacheproxy;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract interface ICache {
	public abstract void set(String paramString, Object paramObject) throws CPException;

	public abstract void set(Map<String, ?> paramMap) throws CPException;

	public abstract void set(String paramString, Object paramObject, int paramInt) throws CPException;

	public abstract void set(Map<String, ?> paramMap, int paramInt) throws CPException;

	public abstract boolean setnx(String paramString, Object paramObject) throws CPException;

	public abstract boolean setnx(String paramString, Object paramObject, int paramInt) throws CPException;

	public abstract <T> T getSet(String paramString, Object paramObject) throws CPException;

	public abstract <T> T getSet(String paramString, Object paramObject, int paramInt) throws CPException;

	public abstract <T> T get(String paramString) throws CPException;

	public abstract <T> Map<String, T> get(Collection<String> paramCollection) throws CPException;

	public abstract boolean exists(String paramString) throws CPException;

	public abstract long ttl(String paramString) throws CPException;

	public abstract void setTtl(String paramString, int paramInt) throws CPException;

	public abstract Collection<String> keys(String paramString) throws CPException;

	public abstract void keys(String paramString, IKeyVisitor paramIKeyVisitor) throws CPException;

	public abstract void remove(String paramString) throws CPException;

	public abstract void remove(Collection<String> paramCollection) throws CPException;

	public abstract void push(String paramString, String[] paramArrayOfString);

	public abstract void push(String paramString, int paramInt, String[] paramArrayOfString);

	public abstract String pop(String paramString);

	public abstract List<String> members(String paramString);

	public abstract long getCounter(String paramString);

	public abstract long incCounter(String paramString);

	public abstract long incCounter(String paramString, int paramInt);

	public abstract long decCounter(String paramString);

	public abstract long decCounter(String paramString, int paramInt);

	public abstract void zadd(String paramString1, String paramString2, double paramDouble);

	public abstract void zadd(String paramString1, String paramString2, double paramDouble, int paramInt);

	public abstract void zrem(String paramString1, String paramString2);

	public abstract Set<String> zrange(String paramString, long paramLong1, long paramLong2);

	public abstract Set<String> zrangeByScore(String paramString, double paramDouble1, double paramDouble2);

	public abstract long zrank(String paramString1, String paramString2);

	public abstract void hset(String paramString1, String paramString2, String paramString3);

	public abstract void hset(String paramString1, String paramString2, String paramString3, int paramInt);

	public abstract void hdel(String paramString1, String paramString2);

	public abstract Set<String> hkeys(String paramString);

	public abstract String hget(String paramString1, String paramString2);

	public abstract Map<String, String> hgetAll(String paramString);

	public abstract void sadd(String paramString1, String paramString2);

	public abstract void srem(String paramString1, String paramString2);

	public abstract Set<String> smembers(String paramString);

	public abstract boolean sismember(String paramString1, String paramString2);
}
