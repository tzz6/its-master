package com.its.framework.serialize.specific;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.its.framework.serialize.SerializeException;
import com.its.framework.serialize.reader.ILargeMapReader;
import com.its.framework.serialize.reader.ILargeSetReader;
import com.its.framework.serialize.reader.Reader;
import com.its.framework.serialize.writer.ILargeMapWriter;
import com.its.framework.serialize.writer.ILargeSetWriter;
import com.its.framework.serialize.writer.Writer;
@SuppressWarnings({"rawtypes","unchecked"})
class MapSegment<K, V> {
	private static Logger logger = LoggerFactory.getLogger(MapSegment.class);
	private SerialData serialData;
	private Map<K, V> map;
	private LargeMap<K, V> largeMap;

	public MapSegment(LargeMap<K, V> largeMap) {
		this(largeMap, null);
	}

	public MapSegment(LargeMap<K, V> largeMap, byte[] serialData) {
		this.largeMap = largeMap;
		if (serialData != null)
			this.serialData = new SerialData(serialData);
	}

	public synchronized SerialData getSerialData(Writer writer, ILargeMapWriter<K, V> largeMapWriter) {
		if ((this.map != null) && (this.map.size() > 0)) {
			while (true) {
				try {
					writer.clear();
					int size = this.map.size();
					if (size == 0) {
						break;
					}
					writer.writeInt(size);

					Iterator<Entry<K, V>> localIterator = this.map.entrySet().iterator();
					if (!localIterator.hasNext())
						continue;
					Entry<K, V> entry = localIterator.next();
					largeMapWriter.writeItem(writer, entry.getKey(), entry.getValue());
					size--;
//					continue;

					if (size != 0)
						continue;
					writer.flush();
					this.serialData = new SerialData(writer.getBuffer(), writer.position());
					break;

//					continue;
				} catch (Exception e) {
					logger.warn("serial map error.", e);
				}
			}
		}

		return this.serialData;
	}

	public synchronized SerialData getSerialData(Writer writer, ILargeSetWriter<K> largeSetWriter) {
		if ((this.map != null) && (this.map.size() > 0)) {
			while (true) {
				try {
					writer.clear();
					int size = this.map.size();
					if (size == 0) {
						break;
					}
					writer.writeInt(size);

					Iterator<K> localIterator = this.map.keySet().iterator();
					if (!localIterator.hasNext())
						continue;
					K key = localIterator.next();
					largeSetWriter.writeItem(writer, key);
					size--;

					if (size != 0)
						continue;
					writer.flush();
					this.serialData = new SerialData(writer.getBuffer(), writer.position());
					break;

				} catch (Exception e) {
					logger.warn("serial map error.", e);
				}
			}
		}

		return this.serialData;
	}

	public synchronized Map<K, V> getMap() {
		if (this.map == null) {
			if (this.serialData != null)
				deSerialize();
			else {
				this.map = new HashMap();
			}
		}

		return this.map;
	}

	public synchronized boolean isEmpty() {
		boolean mapIsEmpty = (this.map == null) || (this.map.isEmpty());
		boolean dataIsEmpty = this.serialData == null;
		return (mapIsEmpty) && (dataIsEmpty);
	}

	private void deSerialize() {
		try {
			Reader reader = new Reader(this.serialData.getData(), this.serialData.getSize());
			int size = reader.readInt();
			HashMap map = new HashMap(size);

			if (this.largeMap.isSerialValue()) {
				ILargeMapReader largeMapReader = this.largeMap.getLargeMapReader();
				for (int i = 0; i < size; i++) {
					Object key = largeMapReader.readKey(reader);
					Object value = largeMapReader.readValue(reader);
					map.put(key, value);
				}
			} else {
				Object NULL = LargeMap.NULL;
				ILargeSetReader largeSetReader = this.largeMap.getLargeSetReader();
				for (int i = 0; i < size; i++) {
					Object key = largeSetReader.readItem(reader);
					map.put(key, NULL);
				}
			}

			this.map = map;
			this.serialData = null;
		} catch (Exception e) {
			showSerialInfo();
			throw new SerializeException(e);
		}
	}

	private void showSerialInfo() {
		try {
			Reader reader = new Reader(this.serialData.getData(), this.serialData.getSize());
			int size = reader.readInt();
			String s = "data buffer size: %d, size: %d, map size: %d";
			System.out.println(String.format(s, new Object[] { Integer.valueOf(this.serialData.getData().length),
					Integer.valueOf(this.serialData.getSize()), Integer.valueOf(size) }));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class SerialData {
		private byte[] data;
		private int size;

		public SerialData(byte[] data) {
			this(data, data.length);
		}

		public SerialData(byte[] data, int size) {
			this.data = data;
			this.size = size;
		}

		public byte[] getData() {
			return this.data;
		}

		public int getSize() {
			return this.size;
		}
	}
}
