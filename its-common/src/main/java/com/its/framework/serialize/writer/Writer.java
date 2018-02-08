package com.its.framework.serialize.writer;

import com.esotericsoftware.kryo.io.Output;
import com.its.framework.serialize.ClassUtils;
import com.its.framework.serialize.EhSerializer;
import com.its.framework.serialize.IEhSerializer;
import com.its.framework.serialize.bit.BitWriter;
import com.its.framework.serialize.specific.BigDecimalSerializer;
import com.its.framework.serialize.specific.BitSetSerializer;
import com.its.framework.serialize.specific.LargeMap;
import com.its.framework.serialize.specific.LargeSet;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class Writer {
	public static final boolean OPTIMIZE = true;
	private final Output output = new Output(4096, -1);
	private final BitWriter bitWriter = new BitWriter();

	public final boolean writeNullTag(Object obj) {
		boolean notNull = obj != null;
		writeBoolean(notNull);
		return notNull;
	}

	public final void writeBoolean(boolean value) {
		if (value)
			this.bitWriter.write1();
		else
			this.bitWriter.write0();
	}

	public final void writeBOOLEAN(Boolean value) {
		if (value == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			writeBoolean(value.booleanValue());
		}
	}

	public final void writeBooleans(boolean[] values) {
		if (values == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(values.length, true);
			for (boolean value : values)
				writeBoolean(value);
		}
	}

	public final void writeBOOLEANs(Boolean[] values) {
		if (values == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(values.length, true);
			for (Boolean value : values)
				writeBOOLEAN(value);
		}
	}

	public final void writeByte(byte value) {
		this.output.writeByte(value);
	}

	public final void writeBYTE(Byte value) {
		if (value == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeByte(value.byteValue());
		}
	}

	public final void writeBytes(byte[] values) {
		if (values == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(values.length, true);
			this.output.writeBytes(values);
		}
	}

	public final void writeBytes(byte[] values, int count) {
		if (values == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(count, true);
			this.output.writeBytes(values, 0, count);
		}
	}

	public final void writeBYTEs(Byte[] values) {
		if (values == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(values.length, true);
			for (Byte value : values)
				writeBYTE(value);
		}
	}

	public final void writeShort(short value) {
		this.output.writeShort(value);
	}

	public final void writeSHORT(Short value) {
		if (value == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeShort(value.shortValue());
		}
	}

	public final void writeShorts(short[] values) {
		if (values == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(values.length, true);
			this.output.writeShorts(values);
		}
	}

	public final void writeSHORTs(Short[] values) {
		if (values == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(values.length, true);
			for (Short value : values)
				writeSHORT(value);
		}
	}

	public final void writeInt(int value) {
		this.output.writeVarInt(value, true);
	}

	public final void writeINT(Integer value) {
		if (value == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(value.intValue(), true);
		}
	}

	public final void writeInts(int[] values) {
		if (values == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(values.length, true);
			for (int value : values)
				this.output.writeVarInt(value, true);
		}
	}

	public final void writeINTs(Integer[] values) {
		if (values == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(values.length, true);
			for (Integer value : values)
				writeINT(value);
		}
	}

	public final void writeLong(long value) {
		this.output.writeVarLong(value, true);
	}

	public final void writeLONG(Long value) {
		if (value == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarLong(value.longValue(), true);
		}
	}

	public final void writeLongs(long[] values) {
		if (values == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(values.length, true);
			for (long value : values)
				this.output.writeVarLong(value, true);
		}
	}

	public final void writeLONGs(Long[] values) {
		if (values == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(values.length, true);
			for (Long value : values)
				writeLONG(value);
		}
	}

	public final void writeFloat(float value) {
		this.output.writeFloat(value);
	}

	public final void writeFLOAT(Float value) {
		if (value == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeFloat(value.floatValue());
		}
	}

	public final void writeFloats(float[] values) {
		if (values == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(values.length, true);
			this.output.writeFloats(values);
		}
	}

	public final void writeFLOATs(Float[] values) {
		if (values == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(values.length, true);
			for (Float value : values)
				writeFLOAT(value);
		}
	}

	public final void writeDouble(double value) {
		this.output.writeDouble(value);
	}

	public final void writeDOUBLE(Double value) {
		if (value == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeDouble(value.doubleValue());
		}
	}

	public final void writeDoubles(double[] values) {
		if (values == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(values.length, true);
			this.output.writeDoubles(values);
		}
	}

	public final void writeDOUBLEs(Double[] values) {
		if (values == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(values.length, true);
			for (Double value : values)
				writeDOUBLE(value);
		}
	}

	public final void writeChar(char value) {
		this.output.writeChar(value);
	}

	public final void writeCHAR(Character value) {
		if (value == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeChar(value.charValue());
		}
	}

	public final void writeChars(char[] values) {
		if (values == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(values.length, true);
			this.output.writeChars(values);
		}
	}

	public final void writeCHARs(Character[] values) {
		if (values == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(values.length, true);
			for (Character value : values)
				writeCHAR(value);
		}
	}

	public final void writeString(String value) {
		this.output.writeString(value);
	}

	public final void writeStrings(String[] values) {
		if (values == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(values.length, true);
			for (String value : values)
				this.output.writeString(value);
		}
	}

	public final void writeDate(Date value) {
		if (value == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarLong(value.getTime(), true);
		}
	}

	public final void writeDates(Date[] values) {
		if (values == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(values.length, true);
			for (Date value : values)
				writeDate(value);
		}
	}

	public final void writeBigDecimal(BigDecimal value) {
		if (value == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			BigDecimalSerializer.getInstance().writeBigDecimal(this.output, value);
		}
	}

	public final void writeBigDecimals(BigDecimal[] values) {
		if (values == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(values.length, true);
			for (BigDecimal value : values)
				writeBigDecimal(value);
		}
	}

	public final void writeBitSet(BitSet value) {
		if (value == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			BitSetSerializer.getInstance().writeBigDecimal(this, value);
		}
	}

	public final void writeBitSets(BitSet[] values) {
		if (values == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(values.length, true);
			for (BitSet value : values)
				writeBitSet(value);
		}
	}

	public final <T> void writeCollection(Collection<T> value, ICollectionWriter<T> collectionWriter) {
		Iterator localIterator;
		if (value == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(value.size(), true);

			Class clazz = value.getClass();
			if (clazz == ArrayList.class) {
				this.output.writeByte(1);
			} else if (clazz == HashSet.class) {
				this.output.writeByte(2);
			} else {
				this.output.writeByte(-1);
				this.output.writeString(ClassUtils.getClassName(clazz));
			}

			for (localIterator = value.iterator(); localIterator.hasNext();) {
				Object item = localIterator.next();
				collectionWriter.writeItem((T) item);
			}
		}
	}

	public final <K, V> void writeMap(Map<K, V> value, IMapWriter<K, V> mapWriter) {
		if (value == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			this.output.writeVarInt(value.size(), true);

			Class clazz = value.getClass();
			if (clazz == HashMap.class) {
				this.output.writeByte(1);
			} else {
				this.output.writeByte(-1);
				this.output.writeString(ClassUtils.getClassName(clazz));
			}

			for (Map.Entry<K, V> entry : value.entrySet())
				mapWriter.writeItem(entry.getKey(), entry.getValue());
		}
	}

	public final <T> void writeArray(T[] value, IArrayWriter<T> arrayWriter) {
		if (value == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			int size = value.length;
			this.output.writeVarInt(size, true);

			for (int i = 0; i < size; i++)
				arrayWriter.writeItem(value[i]);
		}
	}

	public final <K, V> void writeLargeMap(LargeMap<K, V> value, ILargeMapWriter<K, V> mapWriter) {
		if (value == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			value.serialize(this, mapWriter);
		}
	}

	public final <T> void writeLargeSet(LargeSet<T> value, ILargeSetWriter<T> setWriter) {
		if (value == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();
			value.serialize(this, setWriter);
		}
	}

	public final void writeObject(Object value) {
		if (value == null) {
			this.bitWriter.write0();
		} else {
			this.bitWriter.write1();

			Class clazz = value.getClass();
			this.output.writeString(ClassUtils.getClassName(clazz));
			if (clazz != Object.class)
				EhSerializer.getSerializer(clazz).serialize(this, value);
		}
	}

	public final void flush() {
		byte[] bits = this.bitWriter.toBytes();
		this.output.write(bits);
	}

	public final byte[] getBuffer() {
		return this.output.getBuffer();
	}

	public final byte[] toBytes() {
		return this.output.toBytes();
	}

	public final int position() {
		return this.output.position();
	}

	public final void clear() {
		this.output.clear();
		this.bitWriter.initialize();
	}
}
