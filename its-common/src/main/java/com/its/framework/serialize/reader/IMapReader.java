package com.its.framework.serialize.reader;

public abstract interface IMapReader<K, V> {
	public abstract K readKey();

	public abstract V readValue();
}
