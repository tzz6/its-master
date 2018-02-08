package com.its.framework.serialize;

import com.its.framework.serialize.meta.FieldType;
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

public class DataTypeHelper {
    public static final byte COLLECTION_ARRAYLIST = 1;
    public static final byte COLLECTION_HASHSET = 2;
    public static final byte COLLECTION_OTHER = -1;
    public static final byte MAP_HASHMAP = 1;
    public static final byte MAP_OTHER = -1;
    private static Map<String, DataType> types = initDataTypes();

    public DataTypeHelper() {
    }

    public static DataType getDataType(FieldType fieldType) {
        DataType dataType = null;
        if (fieldType.isArray()) {
            dataType = (DataType)types.get(getArrayTypeName(fieldType));
            if (dataType == null) {
                dataType = DataType.dt_ARRAY;
            }
        } else {
            dataType = (DataType)types.get(fieldType.getName());
        }

        if (dataType == null) {
            Class<?> clazz = fieldType.getName() == null ? Object.class : ClassUtils.getClass(fieldType.getName());
            if (Collection.class.isAssignableFrom(clazz)) {
                dataType = DataType.dt_COLLECTION;
            } else if (Map.class.isAssignableFrom(clazz)) {
                dataType = DataType.dt_MAP;
            }
        }

        return dataType == null ? DataType.dt_OTHER : dataType;
    }

    private static String getArrayTypeName(FieldType fieldType) {
        String typeName = fieldType.getName() + "[]";
        if (fieldType.getGenericTypes() != null) {
            FieldType componentType = fieldType.getGenericTypes()[0];
            if (!componentType.isArray()) {
                typeName = componentType.getName() + "[]";
            }
        }

        return typeName;
    }

    private static Map<String, DataType> initDataTypes() {
        Map<String, DataType> types = new HashMap<String, DataType>();
        types.put(Boolean.TYPE.getCanonicalName(), DataType.dt_boolean);
        types.put(Boolean.class.getCanonicalName(), DataType.dt_BOOLEAN);
        types.put(boolean[].class.getCanonicalName(), DataType.dt_booleans);
        types.put(Boolean[].class.getCanonicalName(), DataType.dt_BOOLEANs);
        types.put(Byte.TYPE.getCanonicalName(), DataType.dt_byte);
        types.put(Byte.class.getCanonicalName(), DataType.dt_BYTE);
        types.put(byte[].class.getCanonicalName(), DataType.dt_bytes);
        types.put(Byte[].class.getCanonicalName(), DataType.dt_BYTEs);
        types.put(Short.TYPE.getCanonicalName(), DataType.dt_short);
        types.put(Short.class.getCanonicalName(), DataType.dt_SHORT);
        types.put(short[].class.getCanonicalName(), DataType.dt_shorts);
        types.put(Short[].class.getCanonicalName(), DataType.dt_SHORTs);
        types.put(Integer.TYPE.getCanonicalName(), DataType.dt_int);
        types.put(Integer.class.getCanonicalName(), DataType.dt_INT);
        types.put(int[].class.getCanonicalName(), DataType.dt_ints);
        types.put(Integer[].class.getCanonicalName(), DataType.dt_INTs);
        types.put(Long.TYPE.getCanonicalName(), DataType.dt_long);
        types.put(Long.class.getCanonicalName(), DataType.dt_LONG);
        types.put(long[].class.getCanonicalName(), DataType.dt_longs);
        types.put(Long[].class.getCanonicalName(), DataType.dt_LONGs);
        types.put(Float.TYPE.getCanonicalName(), DataType.dt_float);
        types.put(Float.class.getCanonicalName(), DataType.dt_FLOAT);
        types.put(float[].class.getCanonicalName(), DataType.dt_floats);
        types.put(Float[].class.getCanonicalName(), DataType.dt_FLOATs);
        types.put(Double.TYPE.getCanonicalName(), DataType.dt_double);
        types.put(Double.class.getCanonicalName(), DataType.dt_DOUBLE);
        types.put(double[].class.getCanonicalName(), DataType.dt_doubles);
        types.put(Double[].class.getCanonicalName(), DataType.dt_DOUBLEs);
        types.put(Character.TYPE.getCanonicalName(), DataType.dt_char);
        types.put(Character.class.getCanonicalName(), DataType.dt_CHAR);
        types.put(char[].class.getCanonicalName(), DataType.dt_chars);
        types.put(Character[].class.getCanonicalName(), DataType.dt_CHARs);
        types.put(String.class.getCanonicalName(), DataType.dt_STRING);
        types.put(String[].class.getCanonicalName(), DataType.dt_STRINGs);
        types.put(Date.class.getCanonicalName(), DataType.dt_DATE);
        types.put(Date[].class.getCanonicalName(), DataType.dt_DATEs);
        types.put(BigDecimal.class.getCanonicalName(), DataType.dt_BIG_DECIMAL);
        types.put(BigDecimal[].class.getCanonicalName(), DataType.dt_BIG_DECIMALs);
        types.put(BitSet.class.getCanonicalName(), DataType.dt_BIT_SET);
        types.put(BitSet[].class.getCanonicalName(), DataType.dt_BIT_SETs);
        types.put(Collection.class.getCanonicalName(), DataType.dt_COLLECTION);
        types.put(List.class.getCanonicalName(), DataType.dt_COLLECTION);
        types.put(ArrayList.class.getCanonicalName(), DataType.dt_COLLECTION);
        types.put(Set.class.getCanonicalName(), DataType.dt_COLLECTION);
        types.put(HashSet.class.getCanonicalName(), DataType.dt_COLLECTION);
        types.put(Map.class.getCanonicalName(), DataType.dt_MAP);
        types.put(HashMap.class.getCanonicalName(), DataType.dt_MAP);
        types.put(LargeMap.class.getCanonicalName(), DataType.dt_LARGE_MAP);
        types.put(LargeSet.class.getCanonicalName(), DataType.dt_LARGE_SET);
        return types;
    }
}
