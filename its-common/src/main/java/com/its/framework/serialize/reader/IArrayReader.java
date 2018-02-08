package com.its.framework.serialize.reader;

public abstract interface IArrayReader<T> {
	public abstract T[] createArray(int paramInt);

	public abstract T readItem();
}
