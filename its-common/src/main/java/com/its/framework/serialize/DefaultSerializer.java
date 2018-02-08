package com.its.framework.serialize;

import com.its.framework.serialize.reader.Reader;
import com.its.framework.serialize.writer.Writer;
import java.math.BigDecimal;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class DefaultSerializer {
	DefaultSerializer() {
	}

	static Map<Class<?>, IEhSerializer<?>> init() {
		Map<Class<?>, IEhSerializer<?>> m = new HashMap<Class<?>, IEhSerializer<?>>();
		m.put(Boolean.TYPE, new IEhSerializer<Boolean>() {
			public void serialize(Writer writer, Boolean value) {
				writer.writeBoolean(value);
			}

			public Boolean deSerialize(Reader reader) {
				return reader.readBoolean();
			}
		});
		m.put(Boolean.class, new IEhSerializer<Boolean>() {
			public void serialize(Writer writer, Boolean value) {
				writer.writeBOOLEAN(value);
			}

			public Boolean deSerialize(Reader reader) {
				return reader.readBOOLEAN();
			}
		});
		m.put(boolean[].class, new IEhSerializer<boolean[]>() {
			public void serialize(Writer writer, boolean[] value) {
				writer.writeBooleans(value);
			}

			public boolean[] deSerialize(Reader reader) {
				return reader.readBooleans();
			}
		});
		m.put(Boolean[].class, new IEhSerializer<Boolean[]>() {
			public void serialize(Writer writer, Boolean[] value) {
				writer.writeBOOLEANs(value);
			}

			public Boolean[] deSerialize(Reader reader) {
				return reader.readBOOLEANs();
			}
		});
		m.put(Byte.TYPE, new IEhSerializer<Byte>() {
			public void serialize(Writer writer, Byte value) {
				writer.writeByte(value);
			}

			public Byte deSerialize(Reader reader) {
				return reader.readByte();
			}
		});
		m.put(Byte.class, new IEhSerializer<Byte>() {
			public void serialize(Writer writer, Byte value) {
				writer.writeBYTE(value);
			}

			public Byte deSerialize(Reader reader) {
				return reader.readBYTE();
			}
		});
		m.put(byte[].class, new IEhSerializer<byte[]>() {
			public void serialize(Writer writer, byte[] value) {
				writer.writeBytes(value);
			}

			public byte[] deSerialize(Reader reader) {
				return reader.readBytes();
			}
		});
		m.put(Byte[].class, new IEhSerializer<Byte[]>() {
			public void serialize(Writer writer, Byte[] value) {
				writer.writeBYTEs(value);
			}

			public Byte[] deSerialize(Reader reader) {
				return reader.readBYTEs();
			}
		});
		m.put(Short.TYPE, new IEhSerializer<Short>() {
			public void serialize(Writer writer, Short value) {
				writer.writeShort(value);
			}

			public Short deSerialize(Reader reader) {
				return reader.readShort();
			}
		});
		m.put(Short.class, new IEhSerializer<Short>() {
			public void serialize(Writer writer, Short value) {
				writer.writeSHORT(value);
			}

			public Short deSerialize(Reader reader) {
				return reader.readSHORT();
			}
		});
		m.put(short[].class, new IEhSerializer<short[]>() {
			public void serialize(Writer writer, short[] value) {
				writer.writeShorts(value);
			}

			public short[] deSerialize(Reader reader) {
				return reader.readShorts();
			}
		});
		m.put(Short[].class, new IEhSerializer<Short[]>() {
			public void serialize(Writer writer, Short[] value) {
				writer.writeSHORTs(value);
			}

			public Short[] deSerialize(Reader reader) {
				return reader.readSHORTs();
			}
		});
		m.put(Integer.TYPE, new IEhSerializer<Integer>() {
			public void serialize(Writer writer, Integer value) {
				writer.writeInt(value);
			}

			public Integer deSerialize(Reader reader) {
				return reader.readInt();
			}
		});
		m.put(Integer.class, new IEhSerializer<Integer>() {
			public void serialize(Writer writer, Integer value) {
				writer.writeINT(value);
			}

			public Integer deSerialize(Reader reader) {
				return reader.readINT();
			}
		});
		m.put(int[].class, new IEhSerializer<int[]>() {
			public void serialize(Writer writer, int[] value) {
				writer.writeInts(value);
			}

			public int[] deSerialize(Reader reader) {
				return reader.readInts();
			}
		});
		m.put(Integer[].class, new IEhSerializer<Integer[]>() {
			public void serialize(Writer writer, Integer[] value) {
				writer.writeINTs(value);
			}

			public Integer[] deSerialize(Reader reader) {
				return reader.readINTs();
			}
		});
		m.put(Long.TYPE, new IEhSerializer<Long>() {
			public void serialize(Writer writer, Long value) {
				writer.writeLong(value);
			}

			public Long deSerialize(Reader reader) {
				return reader.readLong();
			}
		});
		m.put(Long.class, new IEhSerializer<Long>() {
			public void serialize(Writer writer, Long value) {
				writer.writeLONG(value);
			}

			public Long deSerialize(Reader reader) {
				return reader.readLONG();
			}
		});
		m.put(long[].class, new IEhSerializer<long[]>() {
			public void serialize(Writer writer, long[] value) {
				writer.writeLongs(value);
			}

			public long[] deSerialize(Reader reader) {
				return reader.readLongs();
			}
		});
		m.put(Long[].class, new IEhSerializer<Long[]>() {
			public void serialize(Writer writer, Long[] value) {
				writer.writeLONGs(value);
			}

			public Long[] deSerialize(Reader reader) {
				return reader.readLONGs();
			}
		});
		m.put(Float.TYPE, new IEhSerializer<Float>() {
			public void serialize(Writer writer, Float value) {
				writer.writeFloat(value);
			}

			public Float deSerialize(Reader reader) {
				return reader.readFloat();
			}
		});
		m.put(Float.class, new IEhSerializer<Float>() {
			public void serialize(Writer writer, Float value) {
				writer.writeFLOAT(value);
			}

			public Float deSerialize(Reader reader) {
				return reader.readFLOAT();
			}
		});
		m.put(float[].class, new IEhSerializer<float[]>() {
			public void serialize(Writer writer, float[] value) {
				writer.writeFloats(value);
			}

			public float[] deSerialize(Reader reader) {
				return reader.readFloats();
			}
		});
		m.put(Float[].class, new IEhSerializer<Float[]>() {
			public void serialize(Writer writer, Float[] value) {
				writer.writeFLOATs(value);
			}

			public Float[] deSerialize(Reader reader) {
				return reader.readFLOATs();
			}
		});
		m.put(Double.TYPE, new IEhSerializer<Double>() {
			public void serialize(Writer writer, Double value) {
				writer.writeDouble(value);
			}

			public Double deSerialize(Reader reader) {
				return reader.readDouble();
			}
		});
		m.put(Double.class, new IEhSerializer<Double>() {
			public void serialize(Writer writer, Double value) {
				writer.writeDOUBLE(value);
			}

			public Double deSerialize(Reader reader) {
				return reader.readDOUBLE();
			}
		});
		m.put(double[].class, new IEhSerializer<double[]>() {
			public void serialize(Writer writer, double[] value) {
				writer.writeDoubles(value);
			}

			public double[] deSerialize(Reader reader) {
				return reader.readDoubles();
			}
		});
		m.put(Double[].class, new IEhSerializer<Double[]>() {
			public void serialize(Writer writer, Double[] value) {
				writer.writeDOUBLEs(value);
			}

			public Double[] deSerialize(Reader reader) {
				return reader.readDOUBLEs();
			}
		});
		m.put(Character.TYPE, new IEhSerializer<Character>() {
			public void serialize(Writer writer, Character value) {
				writer.writeChar(value);
			}

			public Character deSerialize(Reader reader) {
				return reader.readChar();
			}
		});
		m.put(Character.class, new IEhSerializer<Character>() {
			public void serialize(Writer writer, Character value) {
				writer.writeCHAR(value);
			}

			public Character deSerialize(Reader reader) {
				return reader.readCHAR();
			}
		});
		m.put(char[].class, new IEhSerializer<char[]>() {
			public void serialize(Writer writer, char[] value) {
				writer.writeChars(value);
			}

			public char[] deSerialize(Reader reader) {
				return reader.readChars();
			}
		});
		m.put(Character[].class, new IEhSerializer<Character[]>() {
			public void serialize(Writer writer, Character[] value) {
				writer.writeCHARs(value);
			}

			public Character[] deSerialize(Reader reader) {
				return reader.readCHARs();
			}
		});
		m.put(String.class, new IEhSerializer<String>() {
			public void serialize(Writer writer, String value) {
				writer.writeString(value);
			}

			public String deSerialize(Reader reader) {
				return reader.readString();
			}
		});
		m.put(String[].class, new IEhSerializer<String[]>() {
			public void serialize(Writer writer, String[] value) {
				writer.writeStrings(value);
			}

			public String[] deSerialize(Reader reader) {
				return reader.readStrings();
			}
		});
		m.put(Date.class, new IEhSerializer<Date>() {
			public void serialize(Writer writer, Date value) {
				writer.writeDate(value);
			}

			public Date deSerialize(Reader reader) {
				return reader.readDate();
			}
		});
		m.put(Date[].class, new IEhSerializer<Date[]>() {
			public void serialize(Writer writer, Date[] value) {
				writer.writeDates(value);
			}

			public Date[] deSerialize(Reader reader) {
				return reader.readDates();
			}
		});
		m.put(BigDecimal.class, new IEhSerializer<BigDecimal>() {
			public void serialize(Writer writer, BigDecimal value) {
				writer.writeBigDecimal(value);
			}

			public BigDecimal deSerialize(Reader reader) {
				return reader.readBigDecimal();
			}
		});
		m.put(BigDecimal[].class, new IEhSerializer<BigDecimal[]>() {
			public void serialize(Writer writer, BigDecimal[] value) {
				writer.writeBigDecimals(value);
			}

			public BigDecimal[] deSerialize(Reader reader) {
				return reader.readBigDecimals();
			}
		});
		m.put(BitSet.class, new IEhSerializer<BitSet>() {
			public void serialize(Writer writer, BitSet value) {
				writer.writeBitSet(value);
			}

			public BitSet deSerialize(Reader reader) {
				return reader.readBitSet();
			}
		});
		m.put(BitSet[].class, new IEhSerializer<BitSet[]>() {
			public void serialize(Writer writer, BitSet[] value) {
				writer.writeBitSets(value);
			}

			public BitSet[] deSerialize(Reader reader) {
				return reader.readBitSets();
			}
		});
		return m;
	}
}
