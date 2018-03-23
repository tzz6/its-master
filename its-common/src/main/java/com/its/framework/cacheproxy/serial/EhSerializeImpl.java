package com.its.framework.cacheproxy.serial;

import com.its.framework.cacheproxy.CPException;
import com.its.framework.serialize.ClassUtils;
import com.its.framework.serialize.EhSerializer;
import com.its.framework.serialize.IEhSerializer;
import com.its.framework.serialize.reader.Reader;
import com.its.framework.serialize.writer.Writer;
@SuppressWarnings({"rawtypes","unchecked"})
public class EhSerializeImpl implements ICacheSerialize {
	public byte[] encode(Object obj) {
		try {
			Class clazz = obj.getClass();
			IEhSerializer ehSerializer = EhSerializer.getSerializer(clazz);
			Writer writer = new Writer();
			writer.writeString(ClassUtils.getClassName(clazz));
			ehSerializer.serialize(writer, obj);
			writer.flush();
			return writer.toBytes();
		} catch (Exception e) {
			throw new CPException(e);
		}
	}

	public Object decode(byte[] data) {
		try {
			Reader reader = new Reader(data);
			String className = reader.readString();
			Class clazz = ClassUtils.getClass(className);
			IEhSerializer ehSerializer = EhSerializer.getSerializer(clazz);
			return ehSerializer.deSerialize(reader);
		} catch (Exception e) {
			throw new CPException(e);
		}
	}
}
