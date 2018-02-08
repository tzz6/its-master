package com.its.framework.serialize.specific;

import com.esotericsoftware.kryo.util.UnsafeUtil;
import com.its.framework.serialize.SerializeException;
import com.its.framework.serialize.reader.Reader;
import com.its.framework.serialize.writer.Writer;
import java.util.BitSet;
import sun.misc.Unsafe;

public class BitSetSerializer {
	private Unsafe unsafe;
	private long sizeIsStickyOffset;
	private long wordsOffset;
	private long wordsInUseOffset;

	private BitSetSerializer() {
		this.unsafe = UnsafeUtil.unsafe();
		try {
			this.sizeIsStickyOffset = this.unsafe.objectFieldOffset(BitSet.class.getDeclaredField("sizeIsSticky"));
			this.wordsOffset = this.unsafe.objectFieldOffset(BitSet.class.getDeclaredField("words"));
			this.wordsInUseOffset = this.unsafe.objectFieldOffset(BitSet.class.getDeclaredField("wordsInUse"));
		} catch (Exception e) {
			throw new SerializeException("initialize BitSet serializer error.", e);
		}
	}

	public static BitSetSerializer getInstance() {
		return BitSetSerializerHolder.instance;
	}

	public final void writeBigDecimal(Writer writer, BitSet value) {
		boolean sizeIsSticky = this.unsafe.getBoolean(value, this.sizeIsStickyOffset);
		long[] words = (long[]) (long[]) this.unsafe.getObject(value, this.wordsOffset);
		int wordsInUse = this.unsafe.getInt(value, this.wordsInUseOffset);

		writer.writeBoolean(sizeIsSticky);
		writer.writeLongs(words);
		writer.writeInt(wordsInUse);
	}

	public final BitSet readBitSet(Reader reader) {
		BitSet bs = new BitSet();

		boolean sizeIsSticky = reader.readBoolean();
		long[] words = reader.readLongs();
		int wordsInUse = reader.readInt();

		this.unsafe.putBoolean(bs, this.sizeIsStickyOffset, sizeIsSticky);
		this.unsafe.putObject(bs, this.wordsOffset, words);
		this.unsafe.putInt(bs, this.wordsInUseOffset, wordsInUse);

		return bs;
	}

	private static class BitSetSerializerHolder {
		static BitSetSerializer instance = new BitSetSerializer();
	}
}
