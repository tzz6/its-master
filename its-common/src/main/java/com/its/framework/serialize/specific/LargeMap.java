package com.its.framework.serialize.specific;

import com.its.framework.serialize.reader.ILargeMapReader;
import com.its.framework.serialize.reader.ILargeSetReader;
import com.its.framework.serialize.reader.Reader;
import com.its.framework.serialize.writer.ILargeMapWriter;
import com.its.framework.serialize.writer.ILargeSetWriter;
import com.its.framework.serialize.writer.Writer;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LargeMap<K, V> implements Map<K, V> {
	static Object NULL = new Object();
	private MapSegment<K, V>[] segments;
	private int segmentSizeMod;
	private boolean serialValue;
	private ILargeSetReader<K> largeSetReader;
	private ILargeMapReader<K, V> largeMapReader;

	public LargeMap() {
		this(1024);
	}

	public LargeMap(int segmentSize) {
		int segmentSizeOf2Power = 1;
		int size = segmentSizeOf2Power << 1;
		while (size < segmentSize) {
			size <<= 1;
			segmentSizeOf2Power++;
		}
		this.segmentSizeMod = (size - 1);
		this.segments = new MapSegment[size];
		this.serialValue = true;
	}

	LargeMap(int segmentSize, boolean serialValue) {
		this(segmentSize);
		this.serialValue = serialValue;
	}

	public int size() {
		int totalSize = 0;
		for (MapSegment segment : this.segments) {
			if (segment != null) {
				totalSize += segment.getMap().size();
			}
		}
		return totalSize;
	}

	public boolean isEmpty() {
		boolean empty = true;
		for (MapSegment segment : this.segments) {
			if ((segment == null) || (segment.isEmpty()))
				continue;
			empty = false;
			break;
		}

		return empty;
	}

	public boolean containsKey(Object key) {
		Map map = getSegmentMap(key, false);
		return (map != null) && (map.containsKey(key));
	}

	public boolean containsValue(Object value) {
		for (MapSegment segment : this.segments) {
			if ((segment != null) && (segment.getMap().containsValue(value))) {
				return true;
			}

		}

		return false;
	}

	public V get(Object key) {
		Map<K, V> map = getSegmentMap(key, false);
		return map == null ? null : map.get(key);
	}

	public V put(K key, V value) {
		Map<K, V> map = getSegmentMap(key, true);
		if (value == null) {
			throw new NullPointerException("value is null, key: " + key);
		}
		return map.put(key, value);
	}

	public V remove(Object key) {
		Map<K, V> map = getSegmentMap(key, false);
		return map == null ? null : map.remove(key);
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		for (Map.Entry entry : m.entrySet()) {
			Object key = entry.getKey();
			Object value = entry.getValue();
			Map map = getSegmentMap(key, true);
			map.put(key, value);
		}
	}

	public void clear() {
		for (int i = 0; i <= this.segmentSizeMod; i++)
			this.segments[i] = null;
	}

	public Set<K> keySet() {
		Set keys = new HashSet();
		for (MapSegment segment : this.segments) {
			if (segment != null) {
				keys.addAll(segment.getMap().keySet());
			}
		}
		return keys;
	}

	public Collection<V> values() {
		Set values = new HashSet();
		for (MapSegment segment : this.segments) {
			if (segment != null) {
				values.addAll(segment.getMap().values());
			}
		}
		return values;
	}

	public Set<Map.Entry<K, V>> entrySet() {
		Set enties = new HashSet();
		for (MapSegment segment : this.segments) {
			if (segment != null) {
				enties.addAll(segment.getMap().entrySet());
			}
		}
		return enties;
	}

	private Map<K, V> getSegmentMap(Object key, boolean createSegment) {
		MapSegment segment = getSegment(key, createSegment);
		return segment == null ? null : segment.getMap();
	}

	private MapSegment<K, V> getSegment(Object key, boolean createSegment) {
		if (key == null) {
			throw new NullPointerException("key is null.");
		}
		int h = key.hashCode() & this.segmentSizeMod;

		MapSegment segment = this.segments[h];
		if ((segment == null) && (createSegment)) {
			segment = new MapSegment(this);
			this.segments[h] = segment;
		}

		return segment;
	}

	public void serialize(Writer writer, ILargeMapWriter<K, V> largeMapWriter) {
		Writer tmpWriter1 = new Writer();
		Writer tmpWriter2 = new Writer();

		int segmentCount = 0;
		for (int i = 0; i <= this.segmentSizeMod; i++) {
			MapSegment segment = this.segments[i];
			if (segment != null) {
				MapSegment.SerialData serialData = segment.getSerialData(tmpWriter2, largeMapWriter);
				if (serialData != null) {
					tmpWriter1.writeInt(i);
					tmpWriter1.writeBytes(serialData.getData(), serialData.getSize());
					segmentCount++;
				}
			}
		}

		tmpWriter1.flush();
		writer.writeInt(this.segmentSizeMod);
		writer.writeInt(segmentCount);
		if (segmentCount > 0)
			writer.writeBytes(tmpWriter1.getBuffer(), tmpWriter1.position());
	}

	void serializeSet(Writer writer, ILargeSetWriter<K> largeSetWriter) {
		Writer tmpWriter1 = new Writer();
		Writer tmpWriter2 = new Writer();

		int segmentCount = 0;
		for (int i = 0; i <= this.segmentSizeMod; i++) {
			MapSegment segment = this.segments[i];
			if (segment != null) {
				MapSegment.SerialData serialData = segment.getSerialData(tmpWriter2, largeSetWriter);
				if (serialData != null) {
					tmpWriter1.writeInt(i);
					tmpWriter1.writeBytes(serialData.getData(), serialData.getSize());
					segmentCount++;
				}
			}
		}

		tmpWriter1.flush();
		writer.writeInt(this.segmentSizeMod);
		writer.writeInt(segmentCount);
		if (segmentCount > 0)
			writer.writeBytes(tmpWriter1.getBuffer(), tmpWriter1.position());
	}

	public void deSerialize(Reader reader, ILargeMapReader<K, V> largeMapReader) {
		this.largeMapReader = largeMapReader;
		deSerialize(reader);
	}

	void deSerializeSet(Reader reader, ILargeSetReader<K> largeSetReader) {
		this.largeSetReader = largeSetReader;
		deSerialize(reader);
	}

	private void deSerialize(Reader reader) {
		this.segmentSizeMod = reader.readInt();
		int segmentCount = reader.readInt();

		this.segments = new MapSegment[this.segmentSizeMod + 1];

		if (segmentCount > 0) {
			byte[] data = reader.readBytes();
			Reader tmpReader = new Reader(data);

			for (int i = 0; i < segmentCount; i++) {
				int index = tmpReader.readInt();
				data = tmpReader.readBytes();
				this.segments[index] = new MapSegment(this, data);
			}
		}
	}

	boolean isSerialValue() {
		return this.serialValue;
	}

	ILargeSetReader<K> getLargeSetReader() {
		return this.largeSetReader;
	}

	ILargeMapReader<K, V> getLargeMapReader() {
		return this.largeMapReader;
	}
}
