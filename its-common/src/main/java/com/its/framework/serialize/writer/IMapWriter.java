package com.its.framework.serialize.writer;

public abstract interface IMapWriter<K, V> {
	public abstract void writeItem(K paramK, V paramV);
}
