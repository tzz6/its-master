package com.its.framework.serialize.reader;

public abstract interface ILargeSetReader<T> {
	public abstract T readItem(Reader paramReader);
}
