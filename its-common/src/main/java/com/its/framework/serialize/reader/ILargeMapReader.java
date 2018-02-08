package com.its.framework.serialize.reader;

public abstract interface ILargeMapReader<K, V> {
	public abstract K readKey(Reader paramReader);

	public abstract V readValue(Reader paramReader);
}
