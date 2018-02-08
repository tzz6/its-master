package com.its.framework.serialize.specific;

import java.math.BigDecimal;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;

public class BigDecimalSerializer {
	private DefaultSerializers.BigDecimalSerializer bigDecimalSerializer;

	private BigDecimalSerializer() {
		this.bigDecimalSerializer = new DefaultSerializers.BigDecimalSerializer();
	}

	public static BigDecimalSerializer getInstance() {
		return BigDecimalSerializerHolder.instance;
	}

	public final void writeBigDecimal(Output output, BigDecimal value) {
		this.bigDecimalSerializer.write(null, output, value);
	}

	public final BigDecimal readBigDecimal(Input input) {
		return this.bigDecimalSerializer.read(null, input, BigDecimal.class);
	}

	private static class BigDecimalSerializerHolder {
		static BigDecimalSerializer instance = new BigDecimalSerializer();
	}
}
