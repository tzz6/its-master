package com.its.framework.serialize.specific;

import com.its.framework.serialize.reader.ILargeSetReader;
import com.its.framework.serialize.reader.Reader;
import com.its.framework.serialize.writer.ILargeSetWriter;
import com.its.framework.serialize.writer.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class LargeSet<T> implements Set<T> {
	private LargeMap<T, Object> map;

	public LargeSet() {
		this(1024);
	}

	public LargeSet(int segmentSize) {
		this.map = new LargeMap(segmentSize, false);
	}

	public int size() {
		return this.map.size();
	}

	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	public boolean contains(Object o) {
		return this.map.containsKey(o);
	}

	public Iterator<T> iterator() {
		return this.map.keySet().iterator();
	}

	public Object[] toArray() {
		Set set = this.map.keySet();
		return set.toArray();
	}

	public <E> E[] toArray(E[] a) {
		Set set = this.map.keySet();
		return (E[]) set.toArray(new Object[set.size()]);
	}

	public boolean add(T object) {
		return this.map.put(object, LargeMap.NULL) == null;
	}

	public boolean remove(Object o) {
		return this.map.remove(o) != null;
	}

	public boolean containsAll(Collection<?> c) {
		for (Iterator localIterator = c.iterator(); localIterator.hasNext();) {
			Object o = localIterator.next();
			if (!this.map.containsKey(o)) {
				return false;
			}
		}

		return true;
	}

	public boolean addAll(Collection<? extends T> c) {
		boolean modified = false;
		Iterator<? extends T> e = c.iterator();
		while (e.hasNext()) {
			if (add(e.next())) {
				modified = true;
			}
		}
		return modified;
	}

	public boolean retainAll(Collection<?> c) {
		boolean modified = false;
		Iterator e = iterator();
		while (e.hasNext()) {
			if (!c.contains(e.next())) {
				e.remove();
				modified = true;
			}
		}
		return modified;
	}

	public boolean removeAll(Collection<?> c) {
		boolean modified = false;
		for (Iterator localIterator = c.iterator(); localIterator.hasNext();) {
			Object o = localIterator.next();
			if (remove(o)) {
				modified = true;
			}
		}
		return modified;
	}

	public void clear() {
		this.map.clear();
	}

	public void serialize(Writer writer, ILargeSetWriter<T> largeSetWriter) {
		this.map.serializeSet(writer, largeSetWriter);
	}

	public void deSerialize(Reader reader, ILargeSetReader<T> largeSetReader) {
		this.map.deSerializeSet(reader, largeSetReader);
	}
}
