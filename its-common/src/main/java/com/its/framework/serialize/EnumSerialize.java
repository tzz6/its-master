package com.its.framework.serialize;

import com.its.framework.serialize.reader.Reader;
import com.its.framework.serialize.writer.Writer;
import java.util.HashMap;
import java.util.Map;

public class EnumSerialize<T extends Enum<?>> implements IEhSerializer<T> {
	private static Map<Class<? extends Enum<?>>, IEhSerializer<?>> enumSerializers = new HashMap();
	private Enum<?>[] enums;

	private EnumSerialize(Class<? extends Enum<?>> clazz) {
		this.enums = ((Enum[]) clazz.getEnumConstants());
	}

	public static <T> IEhSerializer<T> getSerializer(Class<? extends Enum<?>> clazz) {
		IEhSerializer serializer = (IEhSerializer) enumSerializers.get(clazz);
		if (serializer == null) {
			synchronized (enumSerializers) {
				serializer = (IEhSerializer) enumSerializers.get(clazz);
				if (serializer == null) {
					serializer = new EnumSerialize(clazz);
					enumSerializers.put(clazz, serializer);
				}
			}
		}
		return serializer;
	}

	public void serialize(Writer writer, T obj) {
		if (obj == null) {
			writer.writeBoolean(false);
		} else {
			writer.writeBoolean(true);
			writer.writeInt(obj.ordinal());
		}
	}

	public T deSerialize(Reader reader) {
		if (reader.readBoolean()) {
			int value = reader.readInt();
			return (T) this.enums[value];
		}
		return null;
	}
}
