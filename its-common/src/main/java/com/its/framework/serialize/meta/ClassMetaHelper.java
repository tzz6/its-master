package com.its.framework.serialize.meta;

import com.its.framework.serialize.EhSerializer;
import com.its.framework.serialize.IEhSerializer;
import com.its.framework.serialize.reader.Reader;
import com.its.framework.serialize.writer.Writer;
@SuppressWarnings({"rawtypes","unchecked"})
public class ClassMetaHelper {
	public static byte[] serialize(ClassMetas cms) {
		IEhSerializer serializer = EhSerializer.getSerializer(ClassMetas.class);
		Writer writer = new Writer();
		serializer.serialize(writer, cms);
		writer.flush();
		return writer.toBytes();
	}

	public static ClassMetas deSerialize(byte[] data) {
		IEhSerializer serializer = EhSerializer.getSerializer(ClassMetas.class);
		return (ClassMetas) serializer.deSerialize(new Reader(data));
	}
}
