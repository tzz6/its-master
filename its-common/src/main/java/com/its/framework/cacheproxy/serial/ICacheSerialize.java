package com.its.framework.cacheproxy.serial;

public abstract interface ICacheSerialize {
	public abstract byte[] encode(Object paramObject);

	public abstract Object decode(byte[] paramArrayOfByte);
}
