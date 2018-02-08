package com.its.framework.serialize.writer;

public abstract interface ILargeSetWriter<T> {
	public abstract void writeItem(Writer paramWriter, T paramT);
}
