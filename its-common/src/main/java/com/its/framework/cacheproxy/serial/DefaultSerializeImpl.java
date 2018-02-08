package com.its.framework.cacheproxy.serial;

import com.its.framework.cacheproxy.CPException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DefaultSerializeImpl implements ICacheSerialize {
	public byte[] encode(Object obj) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bos);
			os.writeObject(obj);
			os.close();
			bos.close();
			return bos.toByteArray();
		} catch (Exception e) {
			throw new CPException(e);
		}
	}

	public Object decode(byte[] data) {
		try {
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));
			Object obj = in.readObject();
			in.close();
			return obj;
		} catch (Exception e) {
			throw new CPException(e);
		}
	}
}
