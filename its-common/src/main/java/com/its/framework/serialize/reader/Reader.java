package com.its.framework.serialize.reader;

import com.esotericsoftware.kryo.io.Input;
import com.its.framework.serialize.ClassUtils;
import com.its.framework.serialize.EhSerializer;
import com.its.framework.serialize.IEhSerializer;
import com.its.framework.serialize.bit.BitReader;
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
import java.util.Map;

public final class Reader {
	private static final int BIT_TRUE = 1;
	private final Input input;
	private final BitReader bitReader;
	private static final Object OBJ = new Object();

	public Reader() {
		this(new byte[1]);
	}

	public Reader(byte[] data) {
		this(data, data.length);
	}

	public Reader(byte[] data, int count) {
		this.input = new Input(data, 0, count);
		this.bitReader = new BitReader(data, count);
	}

	public final boolean readNullTag() {
		return this.bitReader.read() == BIT_TRUE;
	}

	public final boolean readBoolean() {
		return this.bitReader.read() == 1;
	}

	public final Boolean readBOOLEAN() {
		if (this.bitReader.read() == 1) {
			return Boolean.valueOf(this.bitReader.read() == 1);
		}
		return null;
	}

	public final boolean[] readBooleans() {
		if (this.bitReader.read() == 1) {
			int size = this.input.readVarInt(true);
			boolean[] values = new boolean[size];
			for (int i = 0; i < size; i++) {
				values[i] = readBoolean();
			}
			return values;
		}
		return null;
	}

	public final Boolean[] readBOOLEANs() {
		if (this.bitReader.read() == 1) {
			int size = this.input.readVarInt(true);
			Boolean[] values = new Boolean[size];
			for (int i = 0; i < size; i++) {
				values[i] = readBOOLEAN();
			}
			return values;
		}
		return null;
	}

	public final byte readByte() {
		return this.input.readByte();
	}

	public final Byte readBYTE() {
		if (this.bitReader.read() == 1) {
			return Byte.valueOf(this.input.readByte());
		}
		return null;
	}

	public final byte[] readBytes() {
		if (this.bitReader.read() == 1) {
			int size = this.input.readVarInt(true);
			return this.input.readBytes(size);
		}
		return null;
	}

	public final Byte[] readBYTEs() {
		if (this.bitReader.read() == 1) {
			int size = this.input.readVarInt(true);
			Byte[] values = new Byte[size];
			for (int i = 0; i < size; i++) {
				values[i] = readBYTE();
			}
			return values;
		}
		return null;
	}

	public final short readShort() {
		return this.input.readShort();
	}

	public final Short readSHORT() {
		if (this.bitReader.read() == 1) {
			return Short.valueOf(this.input.readShort());
		}
		return null;
	}

	public final short[] readShorts() {
		if (this.bitReader.read() == 1) {
			int size = this.input.readVarInt(true);
			return this.input.readShorts(size);
		}
		return null;
	}

	public final Short[] readSHORTs() {
		if (this.bitReader.read() == 1) {
			int size = this.input.readVarInt(true);
			Short[] values = new Short[size];
			for (int i = 0; i < size; i++) {
				values[i] = readSHORT();
			}
			return values;
		}
		return null;
	}

	public final int readInt() {
		return this.input.readVarInt(true);
	}

	public final Integer readINT() {
		if (this.bitReader.read() == 1) {
			return Integer.valueOf(this.input.readVarInt(true));
		}
		return null;
	}

	public final int[] readInts() {
		if (this.bitReader.read() == 1) {
			int size = this.input.readVarInt(true);
			int[] values = new int[size];
			for (int i = 0; i < size; i++) {
				values[i] = this.input.readVarInt(true);
			}
			return values;
		}
		return null;
	}

	public final Integer[] readINTs() {
		if (this.bitReader.read() == 1) {
			int size = this.input.readVarInt(true);
			Integer[] values = new Integer[size];
			for (int i = 0; i < size; i++) {
				values[i] = readINT();
			}
			return values;
		}
		return null;
	}

	public final long readLong() {
		return this.input.readVarLong(true);
	}

	public final Long readLONG() {
		if (this.bitReader.read() == 1) {
			return Long.valueOf(this.input.readVarLong(true));
		}
		return null;
	}

	public final long[] readLongs() {
		if (this.bitReader.read() == 1) {
			int size = this.input.readVarInt(true);
			long[] values = new long[size];
			for (int i = 0; i < size; i++) {
				values[i] = this.input.readVarLong(true);
			}
			return values;
		}
		return null;
	}

	public final Long[] readLONGs() {
		if (this.bitReader.read() == 1) {
			int size = this.input.readVarInt(true);
			Long[] values = new Long[size];
			for (int i = 0; i < size; i++) {
				values[i] = readLONG();
			}
			return values;
		}
		return null;
	}

	public final float readFloat() {
		return this.input.readFloat();
	}

	public final Float readFLOAT() {
		if (this.bitReader.read() == 1) {
			return Float.valueOf(this.input.readFloat());
		}
		return null;
	}

	public final float[] readFloats() {
		if (this.bitReader.read() == 1) {
			int size = this.input.readVarInt(true);
			return this.input.readFloats(size);
		}
		return null;
	}

	public final Float[] readFLOATs() {
		if (this.bitReader.read() == 1) {
			int size = this.input.readVarInt(true);
			Float[] values = new Float[size];
			for (int i = 0; i < size; i++) {
				values[i] = readFLOAT();
			}
			return values;
		}
		return null;
	}

	public final double readDouble() {
		return this.input.readDouble();
	}

	public final Double readDOUBLE() {
		if (this.bitReader.read() == 1) {
			return Double.valueOf(this.input.readDouble());
		}
		return null;
	}

	public final double[] readDoubles() {
		if (this.bitReader.read() == 1) {
			int size = this.input.readVarInt(true);
			return this.input.readDoubles(size);
		}
		return null;
	}

	public final Double[] readDOUBLEs() {
		if (this.bitReader.read() == 1) {
			int size = this.input.readVarInt(true);
			Double[] values = new Double[size];
			for (int i = 0; i < size; i++) {
				values[i] = readDOUBLE();
			}
			return values;
		}
		return null;
	}

	public final char readChar() {
		return this.input.readChar();
	}

	public final Character readCHAR() {
		if (this.bitReader.read() == 1) {
			return Character.valueOf(this.input.readChar());
		}
		return null;
	}

	public final char[] readChars() {
		if (this.bitReader.read() == 1) {
			int size = this.input.readVarInt(true);
			return this.input.readChars(size);
		}
		return null;
	}

	public final Character[] readCHARs() {
		if (this.bitReader.read() == 1) {
			int size = this.input.readVarInt(true);
			Character[] values = new Character[size];
			for (int i = 0; i < size; i++) {
				values[i] = readCHAR();
			}
			return values;
		}
		return null;
	}

	public final String readString() {
		return this.input.readString();
	}

	public final String[] readStrings() {
		if (this.bitReader.read() == 1) {
			int size = this.input.readVarInt(true);
			String[] values = new String[size];
			for (int i = 0; i < size; i++) {
				values[i] = this.input.readString();
			}
			return values;
		}
		return null;
	}

	public final Date readDate() {
		if (this.bitReader.read() == 1) {
			long time = this.input.readVarLong(true);
			return new Date(time);
		}
		return null;
	}

	public final Date[] readDates() {
		if (this.bitReader.read() == 1) {
			int size = this.input.readVarInt(true);
			Date[] values = new Date[size];
			for (int i = 0; i < size; i++) {
				values[i] = readDate();
			}
			return values;
		}
		return null;
	}

	public final BigDecimal readBigDecimal() {
		if (this.bitReader.read() == 1) {
			return BigDecimalSerializer.getInstance().readBigDecimal(this.input);
		}
		return null;
	}

	public final BigDecimal[] readBigDecimals() {
		if (this.bitReader.read() == 1) {
			int size = this.input.readVarInt(true);
			BigDecimal[] values = new BigDecimal[size];
			for (int i = 0; i < size; i++) {
				values[i] = readBigDecimal();
			}
			return values;
		}
		return null;
	}

	public final BitSet readBitSet() {
		if (this.bitReader.read() == 1) {
			return BitSetSerializer.getInstance().readBitSet(this);
		}
		return null;
	}

	public final BitSet[] readBitSets() {
		if (this.bitReader.read() == 1) {
			int size = this.input.readVarInt(true);
			BitSet[] values = new BitSet[size];
			for (int i = 0; i < size; i++) {
				values[i] = readBitSet();
			}
			return values;
		}
		return null;
	}

	private final int readContainerSize() {
		if (this.bitReader.read() == 1) {
			return this.input.readVarInt(true);
		}
		return -1;
	}

	public final <E, T extends Collection<E>> T readCollection(ICollectionReader<E> collectionReader) {
		int size = readContainerSize();
		if (size == -1) {
			return null;
		}
		Collection list = null;
		switch (this.input.readByte()) {
		case 1:
			list = new ArrayList(size);
			break;
		case 2:
			list = new HashSet(size);
			break;
		default:
			list = (Collection) ClassUtils.newClassInstance(this.input.readString());
		}

		for (int i = 0; i < size; i++) {
			list.add(collectionReader.readItem());
		}

		return (T) list;
	}

	public final <K, V, T extends Map<K, V>> T readMap(IMapReader<K, V> mapReader) {
		int size = readContainerSize();
		if (size == -1) {
			return null;
		}
		Map map = null;
		switch (this.input.readByte()) {
		case 1:
			map = new HashMap(size);
			break;
		default:
			map = (Map) ClassUtils.newClassInstance(this.input.readString());
		}

		for (int i = 0; i < size; i++) {
			map.put(mapReader.readKey(), mapReader.readValue());
		}
		return (T) map;
	}

	public final <T> T[] readArray(IArrayReader<T> arrayReader) {
		int size = readContainerSize();
		if (size == -1) {
			return null;
		}
		Object[] array = arrayReader.createArray(size);
		for (int i = 0; i < size; i++) {
			array[i] = arrayReader.readItem();
		}
		return (T[]) array;
	}

	public final <K, V> LargeMap<K, V> readLargeMap(ILargeMapReader<K, V> mapReader) {
		if (this.bitReader.read() == 1) {
			LargeMap value = new LargeMap();
			value.deSerialize(this, mapReader);
			return value;
		}
		return null;
	}

	public final <T> LargeSet<T> readLargeSet(ILargeSetReader<T> setReader) {
		if (this.bitReader.read() == 1) {
			LargeSet value = new LargeSet();
			value.deSerialize(this, setReader);
			return value;
		}
		return null;
	}

	public final Object readObject() {
		if (this.bitReader.read() == 1) {
			Class clazz = ClassUtils.getClass(this.input.readString());
			if (clazz == Object.class) {
				return OBJ;
			}
			IEhSerializer serializer = EhSerializer.getSerializer(clazz);
			return serializer.deSerialize(this);
		}

		return null;
	}

	public final void setBuffer(byte[] data) {
		setBuffer(data, data.length);
	}

	public final void setBuffer(byte[] data, int length) {
		this.input.setBuffer(data, 0, length);
		this.bitReader.setBuffer(data, length);
	}
}
