package com.its.framework.serialize;

import com.its.framework.serialize.specific.LargeMap;
import com.its.framework.serialize.specific.LargeSet;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class DataTypeUtils {
	public static final byte COLLECTION_ARRAYLIST = 1;
	public static final byte COLLECTION_HASHSET = 2;
	public static final byte COLLECTION_OTHER = -1;
	public static final byte MAP_HASHMAP = 1;
	public static final byte MAP_OTHER = -1;
	private static Map<Class<?>, DataType> types = initDataTypes();

	public DataTypeUtils() {
	}

	public static DataType getDataType(Class<?> clazz) {
		DataType type = (DataType) types.get(clazz);
		if (type == null) {
			if (Collection.class.isAssignableFrom(clazz)) {
				type = DataType.dt_COLLECTION;
			} else if (Map.class.isAssignableFrom(clazz)) {
				type = DataType.dt_MAP;
			} else if (clazz.isArray()) {
				type = DataType.dt_ARRAY;
			} else {
				type = DataType.dt_OTHER;
			}
		}

		return type;
	}

	private static Map<Class<?>, DataType> initDataTypes() {
		Map<Class<?>, DataType> types = new HashMap<Class<?>, DataType>();
		types.put(Boolean.TYPE, DataType.dt_boolean);
		types.put(Boolean.class, DataType.dt_BOOLEAN);
		types.put(boolean[].class, DataType.dt_booleans);
		types.put(Boolean[].class, DataType.dt_BOOLEANs);
		types.put(Byte.TYPE, DataType.dt_byte);
		types.put(Byte.class, DataType.dt_BYTE);
		types.put(byte[].class, DataType.dt_bytes);
		types.put(Byte[].class, DataType.dt_BYTEs);
		types.put(Short.TYPE, DataType.dt_short);
		types.put(Short.class, DataType.dt_SHORT);
		types.put(short[].class, DataType.dt_shorts);
		types.put(Short[].class, DataType.dt_SHORTs);
		types.put(Integer.TYPE, DataType.dt_int);
		types.put(Integer.class, DataType.dt_INT);
		types.put(int[].class, DataType.dt_ints);
		types.put(Integer[].class, DataType.dt_INTs);
		types.put(Long.TYPE, DataType.dt_long);
		types.put(Long.class, DataType.dt_LONG);
		types.put(long[].class, DataType.dt_longs);
		types.put(Long[].class, DataType.dt_LONGs);
		types.put(Float.TYPE, DataType.dt_float);
		types.put(Float.class, DataType.dt_FLOAT);
		types.put(float[].class, DataType.dt_floats);
		types.put(Float[].class, DataType.dt_FLOATs);
		types.put(Double.TYPE, DataType.dt_double);
		types.put(Double.class, DataType.dt_DOUBLE);
		types.put(double[].class, DataType.dt_doubles);
		types.put(Double[].class, DataType.dt_DOUBLEs);
		types.put(Character.TYPE, DataType.dt_char);
		types.put(Character.class, DataType.dt_CHAR);
		types.put(char[].class, DataType.dt_chars);
		types.put(Character[].class, DataType.dt_CHARs);
		types.put(String.class, DataType.dt_STRING);
		types.put(String[].class, DataType.dt_STRINGs);
		types.put(Date.class, DataType.dt_DATE);
		types.put(Date[].class, DataType.dt_DATEs);
		types.put(BigDecimal.class, DataType.dt_BIG_DECIMAL);
		types.put(BitSet.class, DataType.dt_BIT_SET);
		types.put(BitSet[].class, DataType.dt_BIT_SETs);
		types.put(BigDecimal[].class, DataType.dt_BIG_DECIMALs);
		types.put(Collection.class, DataType.dt_COLLECTION);
		types.put(List.class, DataType.dt_COLLECTION);
		types.put(ArrayList.class, DataType.dt_COLLECTION);
		types.put(Set.class, DataType.dt_COLLECTION);
		types.put(HashSet.class, DataType.dt_COLLECTION);
		types.put(Map.class, DataType.dt_MAP);
		types.put(HashMap.class, DataType.dt_MAP);
		types.put(LargeMap.class, DataType.dt_LARGE_MAP);
		types.put(LargeSet.class, DataType.dt_LARGE_SET);
		return types;
	}
}
