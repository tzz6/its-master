package com.its.framework.serialize.writer;

public abstract interface ILargeMapWriter<K, V> {
	public abstract void writeItem(Writer paramWriter, K paramK, V paramV);
}
