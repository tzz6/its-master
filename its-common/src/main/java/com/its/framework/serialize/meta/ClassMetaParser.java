package com.its.framework.serialize.meta;

import com.its.framework.serialize.ClassUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import sun.reflect.generics.reflectiveObjects.GenericArrayTypeImpl;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;
@SuppressWarnings({"rawtypes","unchecked"})
public class ClassMetaParser {
	private ClassMetas cms;
	private ClassMeta cm;
	private Map<String, Type> classGenericTypes;
	private List<Map<String, Type>> allClassGenericTypes = new ArrayList();

	public static ClassMetas parseClass(Class<?> clazz) {
		ClassMetas cms = new ClassMetas();
		new ClassMetaParser().parse(cms, clazz);
		return cms;
	}

	public void parse(ClassMetas cms, Class<?> clazz) {
		this.cms = cms;
		parse(clazz);

		Set<String> fieldTypes = new HashSet<String>();
		for (FieldMeta fm : this.cm.getFields()) {
			travelClassFieldType(fieldTypes, fm.getType());
		}

		for (String fieldType : fieldTypes)
			if ((fieldType.startsWith("com.its.")) && (!cms.contains(fieldType)))
				try {
					clazz = ClassUtils.getClass(fieldType);
					if (clazz.isEnum()) {
						ClassMeta cm = new ClassMeta();
						cm.setName(fieldType);
						cms.addClass(cm);
					} else if ((!Collection.class.isAssignableFrom(clazz)) && (!Map.class.isAssignableFrom(clazz))) {
						new ClassMetaParser().parse(cms, clazz);
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
	}

	private void travelClassFieldType(Set<String> fieldTypes, FieldType fieldType) {
		String typeName = fieldType.getName();
		if (typeName != null) {
			fieldTypes.add(typeName);
		}

		if (fieldType.getGenericTypes() != null)
			for (FieldType genericType : fieldType.getGenericTypes())
				travelClassFieldType(fieldTypes, genericType);
	}

	private void parse(Class<?> clazz) {
		this.cm = new ClassMeta();
		this.cm.setName(clazz.getCanonicalName());

		Type[] actualTypes = null;
		while (clazz != Object.class) {
			this.classGenericTypes = getClassGenericTypes(actualTypes, clazz);
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if ((Modifier.isStatic(field.getModifiers())) || (Modifier.isFinal(field.getModifiers()))
						|| (Modifier.isTransient(field.getModifiers())))
					continue;
				parseField(field);
			}

			actualTypes = getClassActualTypes(clazz);
			clazz = clazz.getSuperclass();
		}

		this.cms.addClass(this.cm);
	}

	private void parseField(Field field) {
		FieldMeta fm = new FieldMeta();
		fm.setName(field.getName());
		Class fieldType = field.getType();
		FieldType ft = new FieldType();
		ft.setArray(fieldType.isArray());
		Type genericType = field.getGenericType();

		if ((genericType instanceof TypeVariableImpl)) {
			String typeName = getGenericClass(((TypeVariableImpl) genericType).getName());
			ft.setName(typeName);
		} else if ((fieldType.isArray()) || (Collection.class.isAssignableFrom(fieldType))
				|| (Map.class.isAssignableFrom(fieldType)) || ((genericType instanceof GenericArrayTypeImpl))) {
			parseGenericField(fieldType, genericType, ft);
		} else {
			ft.setName(fieldType.getCanonicalName());
		}

		fm.setType(ft);
		this.cm.addField(fm);
	}

	private void parseGenericField(Type type, Type genericType, FieldType fieldType) {
		if ((((type instanceof Class)) && (((Class) type).isArray()))
				|| ((genericType instanceof GenericArrayTypeImpl))) {
			parseArrayField(type, genericType, fieldType);
		} else if ((genericType instanceof TypeVariableImpl)) {
			String typeName = getGenericClass(((TypeVariableImpl) genericType).getName());
			fieldType.setName(typeName);
		} else if ((type instanceof Class)) {
			Class typeClass = (Class) type;
			if (Collection.class.isAssignableFrom(typeClass))
				parseCollectionField(typeClass, genericType, fieldType);
			else if (Map.class.isAssignableFrom(typeClass))
				parseMapField(typeClass, genericType, fieldType);
			else
				fieldType.setName(typeClass.getCanonicalName());
		}
	}

	private void parseCollectionField(Class<?> type, Type genericType, FieldType fieldType) {
		fieldType.setName(type.getCanonicalName());
		ParameterizedTypeImpl pt = (ParameterizedTypeImpl) genericType;
		genericType = pt.getActualTypeArguments()[0];

		parseGenericType(genericType, fieldType);
	}

	private void parseMapField(Class<?> type, Type genericType, FieldType fieldType) {
		fieldType.setName(type.getCanonicalName());
		ParameterizedTypeImpl pt = (ParameterizedTypeImpl) genericType;
		Type keyType = pt.getActualTypeArguments()[0];
		Type valueType = pt.getActualTypeArguments()[1];
		parseGenericType(keyType, fieldType);
		parseGenericType(valueType, fieldType);
	}

	private void parseGenericType(Type genericType, FieldType fieldType) {
		if ((genericType instanceof TypeVariableImpl)) {
			String typeName = getGenericClass(((TypeVariableImpl) genericType).getName());
			FieldType ft = new FieldType();
			ft.setName(typeName);
			fieldType.addGenericType(ft);
		} else if ((genericType instanceof ParameterizedTypeImpl)) {
			ParameterizedTypeImpl pt = (ParameterizedTypeImpl) genericType;
			FieldType ft = new FieldType();
			fieldType.addGenericType(ft);
			parseGenericField(pt.getRawType(), pt, ft);
		} else if ((genericType instanceof GenericArrayTypeImpl)) {
			GenericArrayTypeImpl gat = (GenericArrayTypeImpl) genericType;
			FieldType ft = new FieldType();
			ft.setArray(true);
			fieldType.addGenericType(ft);
			parseGenericField(gat.getGenericComponentType(), gat.getGenericComponentType(), ft);
		} else if ((genericType instanceof Class)) {
			FieldType ft = new FieldType();
			ft.setName(((Class) genericType).getCanonicalName());
			fieldType.addGenericType(ft);
		}
	}

	private void parseArrayField(Type type, Type genericType, FieldType fieldType) {
		if ((genericType instanceof GenericArrayTypeImpl)) {
			genericType = ((GenericArrayTypeImpl) genericType).getGenericComponentType();
			if ((genericType instanceof GenericArrayTypeImpl)) {
				FieldType ft = new FieldType();
				ft.setArray(true);
				fieldType.addGenericType(ft);
				parseGenericField(((GenericArrayTypeImpl) genericType).getGenericComponentType(), genericType, ft);
			} else if ((genericType instanceof TypeVariableImpl)) {
				String typeName = getGenericClass(((TypeVariableImpl) genericType).getName());
				fieldType.setName(typeName);
			} else if ((genericType instanceof ParameterizedTypeImpl)) {
				ParameterizedTypeImpl pt = (ParameterizedTypeImpl) genericType;
				FieldType ft = new FieldType();
				parseGenericField(pt.getRawType(), pt, ft);
				fieldType.addGenericType(ft);
			}
		} else if ((type instanceof Class)) {
			Class clazz = (Class) type;
			if (clazz.getComponentType().isArray()) {
				FieldType ft = new FieldType();
				ft.setArray(true);
				fieldType.addGenericType(ft);
				parseGenericField(clazz.getComponentType(), genericType, ft);
			} else {
				fieldType.setName(((Class) type).getComponentType().getCanonicalName());
			}
		}
	}

	private Map<String, Type> getClassGenericTypes(Type[] actualTypes, Class<?> clazz) {
		Map genericTypes = new HashMap();
		if ((actualTypes != null) && (actualTypes.length > 0)) {
			TypeVariable[] tvs = clazz.getTypeParameters();
			for (int i = 0; i < tvs.length; i++) {
				genericTypes.put(tvs[i].getName(), actualTypes[i]);
			}
		}
		this.allClassGenericTypes.add(genericTypes);
		return genericTypes;
	}

	private String getGenericClass(String genericName) {
		Type t = (Type) this.classGenericTypes.get(genericName);
		String tmpGenericName = (t instanceof Class) ? ((Class) t).getCanonicalName() : t.toString();
		int index = this.allClassGenericTypes.size() - 1;
		while ((t != null) && (!(t instanceof Class))) {
			index--;
			if (index < 0)
				break;
			t = (Type) ((Map) this.allClassGenericTypes.get(index)).get(tmpGenericName);
			tmpGenericName = (t instanceof Class) ? ((Class) t).getCanonicalName() : t.toString();
		}
		return tmpGenericName == null ? genericName : tmpGenericName;
	}

	private Type[] getClassActualTypes(Class<?> clazz) {
		Type genericType = clazz.getGenericSuperclass();
		if ((genericType instanceof ParameterizedTypeImpl)) {
			return ((ParameterizedTypeImpl) genericType).getActualTypeArguments();
		}
		return null;
	}
}
