package com.its.framework.serialize;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
@SuppressWarnings({"rawtypes","unchecked","unused"})
class EhSerializerGener {
	private static final String SERIALIZER_PACKAGE = "com.its.omp.core.serial.impl.eh";
	private static final String SERIALIZER_PACKAGE_DOT = "com.its.omp.core.serial.impl.eh.";
	private static final String SERIALIZER_INTF = "IEhSerializer";
	private static AtomicInteger classIndex = new AtomicInteger();
	private EhSerializerGener parentGener;
	private StringBuilder code;
	private Class<?> clazz;
	private ParameterizedTypeImpl genericType;
	private Map<String, Type> classGenericTypes;
	private Field[] fields;
	private Map<String, SubSerializer> subSerializers;
	private int genericVarIndex;

	public EhSerializerGener(Class<?> clazz) {
		this(clazz, null, null);
	}

	public EhSerializerGener(Class<?> clazz, Type genericType) {
		this(clazz, genericType, null);
	}

	private EhSerializerGener(Class<?> clazz, Type genericType, EhSerializerGener parentGener) {
		this.clazz = clazz;
		this.parentGener = parentGener;
		this.fields = ClassUtils.getFields(clazz);
		processGenericType(clazz, genericType);

		this.subSerializers = new HashMap();
		this.code = new StringBuilder();
	}

	private void processGenericType(Class<?> clazz, Type genericType) {
		this.classGenericTypes = new HashMap();

		if ((genericType instanceof ParameterizedTypeImpl)) {
			this.genericType = ((ParameterizedTypeImpl) genericType);
			getGenericType(clazz, this.genericType, this.classGenericTypes);
		}

		Class theClass = clazz;
		Class parentClass = theClass.getSuperclass();
		while (parentClass != Object.class) {
			Map<String, Type> tmpTypeMap = new HashMap<String, Type>();
			getGenericType(parentClass, theClass.getGenericSuperclass(), tmpTypeMap);

			for (Map.Entry<String, Type> entry : tmpTypeMap.entrySet()) {
				Type type = (Type) this.classGenericTypes.get(theClass.toString() + entry.getValue());
				if (type == null) {
					type = (Type) entry.getValue();
				}
				this.classGenericTypes.put(entry.getKey(), type);
			}

			theClass = parentClass;
			parentClass = parentClass.getSuperclass();
		}
	}

	private void getGenericType(Class<?> clazz, Type genericType, Map<String, Type> typeMap) {
		if ((clazz != null) && ((genericType instanceof ParameterizedTypeImpl))) {
			Type[] genericTypes = clazz.getTypeParameters();
			Type[] actualTypes = ((ParameterizedTypeImpl) genericType).getActualTypeArguments();

			for (int i = 0; i < genericTypes.length; i++)
				typeMap.put(clazz.toString() + genericTypes[i], actualTypes[i]);
		}
	}

	public String codeGen() {
		this.code.append("package ").append("com.its.omp.core.serial.impl.eh").append(";\n");
		this.code.append("import com.its.omp.core.serial.impl.eh.reader.*;\n");
		this.code.append("import com.its.omp.core.serial.impl.eh.writer.*;\n");
		String className = "C" + classIndex.incrementAndGet();
		return codeGen(className, true);
	}

	private String codeGen(String className, boolean publicClass) {
		if (publicClass) {
			this.code.append("public final class ").append(className).append(" implements ").append("IEhSerializer");
			this.code.append("<" + getClassTypeName() + "> {\n");
		} else {
			this.code.append(className).append("\n");
		}

		new SerializeCodeGener().codeGen();
		new DeSerializeCodeGener().codeGen();

		if (publicClass) {
			for (SubSerializer subSerializer : this.subSerializers.values()) {
				this.code.append(subSerializer.getJavaCode()).append("\n");
			}
		}

		this.code.append("}");
		return this.code.toString();
	}

	private String getClassTypeName() {
		return ClassUtils.getClassName(this.genericType == null ? this.clazz : this.genericType);
	}

	private Type getFieldGenericType(Field field) {
		String key = field.getDeclaringClass().toString() + field.getGenericType();
		Type fieldGenericType = (Type) this.classGenericTypes.get(key);

		if (fieldGenericType == null) {
			Type type = field.getGenericType();
			if ((type instanceof ParameterizedTypeImpl)) {
				ParameterizedTypeImpl genType = (ParameterizedTypeImpl) type;
				Type[] actualTypes = genType.getActualTypeArguments();
				boolean flag = false;

				for (int i = 0; i < actualTypes.length; i++) {
					key = field.getDeclaringClass().toString() + actualTypes[i];
					type = (Type) this.classGenericTypes.get(key);
					if (type != null) {
						actualTypes[i] = type;
						flag = true;
					}
				}

				if (flag) {
					fieldGenericType = ParameterizedTypeImpl.make(genType.getRawType(), actualTypes,
							genType.getOwnerType());
				}
			}
		}

		return fieldGenericType;
	}

	private Map<String, SubSerializer> getRootSubSerializers() {
		EhSerializerGener gener = this;
		while (gener.parentGener != null) {
			gener = gener.parentGener;
		}
		return gener.subSerializers;
	}

	private int getNextVarIndex() {
		EhSerializerGener gener = this;
		while (gener.parentGener != null) {
			gener = gener.parentGener;
		}
		return ++gener.genericVarIndex;
	}

	static class SubSerializer {
		private String serializerName;
		private String javaCode;

		public SubSerializer(String serializerName, String javaCode) {
			this.serializerName = serializerName;
			this.javaCode = javaCode;
		}

		public String getSerializerName() {
			return this.serializerName;
		}

		public String getJavaCode() {
			return this.javaCode;
		}
	}

	class DeSerializeCodeGener {
		DeSerializeCodeGener() {
		}

		public void codeGen() {
			String className = EhSerializerGener.this.getClassTypeName();
			EhSerializerGener.this.code.append("public final " + className + " deSerialize(final Reader reader) {\n");
			EhSerializerGener.this.code.append("if (reader.readBoolean()) {\n");
			EhSerializerGener.this.code.append(className + " o = new " + className + "();\n");

			for (Field field : EhSerializerGener.this.fields) {
				String fieldVar = "o." + ClassUtils.getFieldSetMethod(field);
				Type fieldGenericType = EhSerializerGener.this.getFieldGenericType(field);

				if (fieldGenericType == null) {
					genFieldDeSerialCode(field, fieldVar, field.getType(), field.getGenericType());
				} else {
					Class fieldType = null;
					if ((fieldGenericType instanceof ParameterizedTypeImpl))
						fieldType = ((ParameterizedTypeImpl) fieldGenericType).getRawType();
					else {
						fieldType = (Class) fieldGenericType;
					}
					genFieldDeSerialCode(field, fieldVar, fieldType, fieldGenericType);
				}
			}

			EhSerializerGener.this.code.append("return o;\n");
			EhSerializerGener.this.code.append("} else {\n");
			EhSerializerGener.this.code.append("return null;\n");
			EhSerializerGener.this.code.append("}\n");
			EhSerializerGener.this.code.append("}\n");
		}

		private void genFieldDeSerialCode(Field field, String fieldVar, Class<?> fieldType, Type genericType) {
			if (fieldType == genericType) {
				genericType = null;
			}

			DataType dataType = DataTypeUtils.getDataType(fieldType);
			switch (dataType.ordinal()) {
			case 1:
				genCollectionFieldDeSerializeCode(field, fieldVar, fieldType, genericType);
				break;
			case 2:
				genMapFieldDeSerializeCode(field, fieldVar, fieldType, genericType);
				break;
			case 3:
				genArrayFieldDeSerializeCode(field, fieldVar, fieldType, genericType);
				break;
			case 4:
				genLargeMapFieldDeSerializeCode(field, fieldVar, fieldType, genericType);
				break;
			case 5:
				genLargeSetFieldDeSerializeCode(field, fieldVar, fieldType, genericType);
				break;
			case 6:
				genObjectFieldDeSerializeCode(field, fieldVar, fieldType, genericType);
				break;
			default:
				if (fieldVar == null)
					EhSerializerGener.this.code.append("reader.read").append(dataType).append("();\n");
				else
					EhSerializerGener.this.code.append(fieldVar + "(reader.read").append(dataType).append("());\n");
			}
		}

		private void genCollectionFieldDeSerializeCode(Field field, String fieldVar, Class<?> fieldType,
				Type genericType) {
			Type itemClass = ClassUtils.getFieldOneGenericType(genericType, field,
					EhSerializerGener.this.classGenericTypes);
			String itemType = ClassUtils.getClassName(itemClass);
			if (fieldVar != null) {
				EhSerializerGener.this.code.append(fieldVar + "((" + ClassUtils.getClassName(fieldType) + ") ");
			}
			EhSerializerGener.this.code.append("reader.readCollection(new ICollectionReader<" + itemType + ">() {\n");
			EhSerializerGener.this.code.append("public final " + itemType + " readItem() {\n");
			EhSerializerGener.this.code.append("return ");
			genFieldDeSerialCode(field, null, ClassUtils.getTypeClass(itemClass), itemClass);

			EhSerializerGener.this.code.append("}\n");

			if (fieldVar == null)
				EhSerializerGener.this.code.append("});\n");
			else
				EhSerializerGener.this.code.append("}));\n");
		}

		private void genMapFieldDeSerializeCode(Field field, String fieldVar, Class<?> fieldType, Type genericType) {
			Type[] itemClasses = ClassUtils.getFieldTwoGenericType(genericType, field,
					EhSerializerGener.this.classGenericTypes);
			String keyType = ClassUtils.getClassName(itemClasses[0]);
			String valueType = ClassUtils.getClassName(itemClasses[1]);
			if (fieldVar != null) {
				EhSerializerGener.this.code.append(fieldVar + "((" + ClassUtils.getClassName(fieldType) + ") ");
			}

			EhSerializerGener.this.code
					.append("reader.readMap(new IMapReader<" + keyType + ", " + valueType + ">() {\n");
			EhSerializerGener.this.code.append("public final " + keyType + " readKey() {\n");
			EhSerializerGener.this.code.append("return ");
			genFieldDeSerialCode(field, null, ClassUtils.getTypeClass(itemClasses[0]), itemClasses[0]);
			EhSerializerGener.this.code.append("}\n");

			EhSerializerGener.this.code.append("public final " + valueType + " readValue() {\n");
			EhSerializerGener.this.code.append("return ");
			genFieldDeSerialCode(field, null, ClassUtils.getTypeClass(itemClasses[1]), itemClasses[1]);
			EhSerializerGener.this.code.append("}\n");

			if (fieldVar == null)
				EhSerializerGener.this.code.append("});\n");
			else
				EhSerializerGener.this.code.append("}));\n");
		}

		private void genLargeMapFieldDeSerializeCode(Field field, String fieldVar, Class<?> fieldType,
				Type genericType) {
			Type[] itemClasses = ClassUtils.getFieldTwoGenericType(genericType, field,
					EhSerializerGener.this.classGenericTypes);
			String keyType = ClassUtils.getClassName(itemClasses[0]);
			String valueType = ClassUtils.getClassName(itemClasses[1]);
			if (fieldVar != null) {
				EhSerializerGener.this.code.append(fieldVar + "((" + ClassUtils.getClassName(fieldType) + ") ");
			}

			EhSerializerGener.this.code
					.append("reader.readLargeMap(new ILargeMapReader<" + keyType + ", " + valueType + ">() {\n");
			EhSerializerGener.this.code.append("public final " + keyType + " readKey(final Reader reader) {\n");
			EhSerializerGener.this.code.append("return ");
			genFieldDeSerialCode(field, null, ClassUtils.getTypeClass(itemClasses[0]), itemClasses[0]);
			EhSerializerGener.this.code.append("}\n");

			EhSerializerGener.this.code.append("public final " + valueType + " readValue(final Reader reader) {\n");
			EhSerializerGener.this.code.append("return ");
			genFieldDeSerialCode(field, null, ClassUtils.getTypeClass(itemClasses[1]), itemClasses[1]);
			EhSerializerGener.this.code.append("}\n");

			if (fieldVar == null)
				EhSerializerGener.this.code.append("});\n");
			else
				EhSerializerGener.this.code.append("}));\n");
		}

		private void genLargeSetFieldDeSerializeCode(Field field, String fieldVar, Class<?> fieldType,
				Type genericType) {
			Type itemClass = ClassUtils.getFieldOneGenericType(genericType, field,
					EhSerializerGener.this.classGenericTypes);
			String itemType = ClassUtils.getClassName(itemClass);
			if (fieldVar != null) {
				EhSerializerGener.this.code.append(fieldVar + "((" + ClassUtils.getClassName(fieldType) + ") ");
			}
			EhSerializerGener.this.code.append("reader.readLargeSet(new ILargeSetReader<" + itemType + ">() {\n");
			EhSerializerGener.this.code.append("public final " + itemType + " readItem(final Reader reader) {\n");
			EhSerializerGener.this.code.append("return ");
			genFieldDeSerialCode(field, null, ClassUtils.getTypeClass(itemClass), itemClass);

			EhSerializerGener.this.code.append("}\n");

			if (fieldVar == null)
				EhSerializerGener.this.code.append("});\n");
			else
				EhSerializerGener.this.code.append("}));\n");
		}

		private void genArrayFieldDeSerializeCode(Field field, String fieldVar, Class<?> fieldType, Type genericType) {
			Type itemClass = ClassUtils.getArrayComponentType(fieldType, genericType, field,
					EhSerializerGener.this.classGenericTypes);
			String itemType = ClassUtils.getClassName(itemClass);

			if (fieldVar != null) {
				EhSerializerGener.this.code.append(fieldVar + "(");
			}
			EhSerializerGener.this.code.append("reader.readArray(new IArrayReader<" + itemType + ">() {\n");
			EhSerializerGener.this.code.append("public final " + itemType + "[] createArray(final int size) {\n");

			EhSerializerGener.this.code.append("return new ");
			String newItemType = ClassUtils.getClassNameNoGeneric(itemClass);
			int index = newItemType.indexOf('[');
			if (index != -1)
				EhSerializerGener.this.code.append(newItemType.substring(0, index)).append("[size]")
						.append(newItemType.substring(index));
			else {
				EhSerializerGener.this.code.append(newItemType).append("[size]");
			}
			EhSerializerGener.this.code.append(";\n");
			EhSerializerGener.this.code.append("}\n");

			EhSerializerGener.this.code.append("public final " + itemType + " readItem() {\n");
			EhSerializerGener.this.code.append("return ");
			genFieldDeSerialCode(field, null, ClassUtils.getTypeClass(itemClass), itemClass);
			EhSerializerGener.this.code.append("}\n");

			if (fieldVar == null)
				EhSerializerGener.this.code.append("});\n");
			else
				EhSerializerGener.this.code.append("}));\n");
		}

		private void genObjectFieldDeSerializeCode(Field field, String fieldVar, Class<?> fieldType, Type genericType) {
			if (fieldVar != null) {
				EhSerializerGener.this.code.append(fieldVar + "(");
			}

			if (fieldType == EhSerializerGener.this.clazz) {
				EhSerializerGener.this.code.append("deSerialize(reader)");
			} else if (fieldType == Object.class) {
				EhSerializerGener.this.code.append("reader.readObject()");
			} else {
				String genericName = ClassUtils.getClassName(genericType == null ? fieldType : genericType);
				String varName = ((EhSerializerGener.SubSerializer) EhSerializerGener.this.getRootSubSerializers()
						.get(genericName)).getSerializerName();
				EhSerializerGener.this.code.append(varName).append(".deSerialize(reader)");
			}

			if (fieldVar == null)
				EhSerializerGener.this.code.append(";\n");
			else
				EhSerializerGener.this.code.append(");\n");
		}
	}

	class SerializeCodeGener {
		SerializeCodeGener() {
		}

		public void codeGen() {
			EhSerializerGener.this.code.append("public final void serialize(final Writer writer, final "
					+ EhSerializerGener.this.getClassTypeName() + " o) {\n");
			EhSerializerGener.this.code.append("if (o == null) {\n");
			EhSerializerGener.this.code.append("writer.writeBoolean(false);\n");
			EhSerializerGener.this.code.append("} else {\n");
			EhSerializerGener.this.code.append("writer.writeBoolean(true);\n");

			for (Field field : EhSerializerGener.this.fields) {
				String fieldVar = "o." + ClassUtils.getFieldGetMethod(field);
				Type fieldGenericType = EhSerializerGener.this.getFieldGenericType(field);

				if (fieldGenericType == null) {
					genFieldSerialCode(field, fieldVar, field.getType(), field.getGenericType());
				} else {
					Class fieldType = null;
					if ((fieldGenericType instanceof ParameterizedTypeImpl))
						fieldType = ((ParameterizedTypeImpl) fieldGenericType).getRawType();
					else {
						fieldType = (Class) fieldGenericType;
					}
					genFieldSerialCode(field, fieldVar, fieldType, fieldGenericType);
				}
			}
			EhSerializerGener.this.code.append("}\n");
			EhSerializerGener.this.code.append("}\n");
		}

		private void genFieldSerialCode(Field field, String fieldVar, Class<?> fieldType, Type genericType) {
			if (fieldType == genericType) {
				genericType = null;
			}

			DataType dataType = DataTypeUtils.getDataType(fieldType);
			switch (dataType.ordinal()) {
			case 1:
				genCollectionFieldSerializeCode(field, fieldVar, fieldType, genericType);
				break;
			case 2:
				genMapFieldSerializeCode(field, fieldVar, fieldType, genericType);
				break;
			case 3:
				genArrayFieldSerializeCode(field, fieldVar, fieldType, genericType);
				break;
			case 4:
				genLargeMapFieldSerializeCode(field, fieldVar, fieldType, genericType);
				break;
			case 5:
				genLargeSetFieldSerializeCode(field, fieldVar, fieldType, genericType);
				break;
			case 6:
				genObjectFieldSerializeCode(field, fieldVar, fieldType, genericType);
				break;
			default:
				EhSerializerGener.this.code.append("writer.write").append(dataType).append("(").append(fieldVar)
						.append(");\n");
			}
		}

		private void genCollectionFieldSerializeCode(Field field, String fieldVar, Class<?> fieldType,
				Type genericType) {
			Type itemClass = ClassUtils.getFieldOneGenericType(genericType, field,
					EhSerializerGener.this.classGenericTypes);
			String itemType = ClassUtils.getClassName(itemClass);
			EhSerializerGener.this.code.append("writer.writeCollection(" + fieldVar + ", new ");
			EhSerializerGener.this.code
					.append("com.its.omp.core.serial.impl.eh.writer.ICollectionWriter<" + itemType + ">() {\n");
			EhSerializerGener.this.code.append("public final void writeItem(final " + itemType + " item) {\n");
			genFieldSerialCode(field, "item", ClassUtils.getTypeClass(itemClass), itemClass);
			EhSerializerGener.this.code.append("}\n");
			EhSerializerGener.this.code.append("});\n");
		}

		private void genMapFieldSerializeCode(Field field, String fieldVar, Class<?> fieldType, Type genericType) {
			Type[] itemClasses = ClassUtils.getFieldTwoGenericType(genericType, field,
					EhSerializerGener.this.classGenericTypes);
			String keyType = ClassUtils.getClassName(itemClasses[0]);
			String valueType = ClassUtils.getClassName(itemClasses[1]);

			EhSerializerGener.this.code.append("writer.writeMap(" + fieldVar + ", new ");
			EhSerializerGener.this.code.append(
					"com.its.omp.core.serial.impl.eh.writer.IMapWriter<" + keyType + ", " + valueType + ">() {\n");
			EhSerializerGener.this.code.append(
					"public final void writeItem(final " + keyType + " key, final " + valueType + " value) {\n");
			genFieldSerialCode(field, "key", ClassUtils.getTypeClass(itemClasses[0]), itemClasses[0]);
			genFieldSerialCode(field, "value", ClassUtils.getTypeClass(itemClasses[1]), itemClasses[1]);
			EhSerializerGener.this.code.append("}\n");
			EhSerializerGener.this.code.append("});\n");
		}

		private void genLargeMapFieldSerializeCode(Field field, String fieldVar, Class<?> fieldType, Type genericType) {
			Type[] itemClasses = ClassUtils.getFieldTwoGenericType(genericType, field,
					EhSerializerGener.this.classGenericTypes);
			String keyType = ClassUtils.getClassName(itemClasses[0]);
			String valueType = ClassUtils.getClassName(itemClasses[1]);

			EhSerializerGener.this.code.append("writer.writeLargeMap(" + fieldVar + ", new ");
			EhSerializerGener.this.code.append(
					"com.its.omp.core.serial.impl.eh.writer.ILargeMapWriter<" + keyType + ", " + valueType + ">() {\n");
			EhSerializerGener.this.code.append("public final void writeItem(final Writer writer, final " + keyType
					+ " key, final " + valueType + " value) {\n");

			genFieldSerialCode(field, "key", ClassUtils.getTypeClass(itemClasses[0]), itemClasses[0]);
			genFieldSerialCode(field, "value", ClassUtils.getTypeClass(itemClasses[1]), itemClasses[1]);
			EhSerializerGener.this.code.append("}\n");
			EhSerializerGener.this.code.append("});\n");
		}

		private void genLargeSetFieldSerializeCode(Field field, String fieldVar, Class<?> fieldType, Type genericType) {
			Type itemClass = ClassUtils.getFieldOneGenericType(genericType, field,
					EhSerializerGener.this.classGenericTypes);
			String itemType = ClassUtils.getClassName(itemClass);
			EhSerializerGener.this.code.append("writer.writeLargeSet(" + fieldVar + ", new ");
			EhSerializerGener.this.code
					.append("com.its.omp.core.serial.impl.eh.writer.ILargeSetWriter<" + itemType + ">() {\n");
			EhSerializerGener.this.code
					.append("public final void writeItem(final Writer writer, final " + itemType + " item) {\n");
			genFieldSerialCode(field, "item", ClassUtils.getTypeClass(itemClass), itemClass);
			EhSerializerGener.this.code.append("}\n");
			EhSerializerGener.this.code.append("});\n");
		}

		private void genArrayFieldSerializeCode(Field field, String fieldVar, Class<?> fieldType, Type genericType) {
			Type itemClass = ClassUtils.getArrayComponentType(fieldType, genericType, field,
					EhSerializerGener.this.classGenericTypes);
			String itemType = ClassUtils.getClassName(itemClass);

			EhSerializerGener.this.code.append("writer.writeArray(" + fieldVar + ", new ");
			EhSerializerGener.this.code
					.append("com.its.omp.core.serial.impl.eh.writer.IArrayWriter<" + itemType + ">() {\n");
			EhSerializerGener.this.code.append("public final void writeItem(final " + itemType + " item) {\n");
			genFieldSerialCode(field, "item", ClassUtils.getTypeClass(itemClass), itemClass);
			EhSerializerGener.this.code.append("}\n");
			EhSerializerGener.this.code.append("});\n");
		}

		private void genObjectFieldSerializeCode(Field field, String fieldVar, Class<?> fieldType, Type genericType) {
			if (fieldType == EhSerializerGener.this.clazz) {
				EhSerializerGener.this.code.append("serialize(writer, " + fieldVar + ");\n");
			} else if (fieldType == Object.class) {
				EhSerializerGener.this.code.append("writer.writeObject(" + fieldVar + ");\n");
			} else {
				String genericName = ClassUtils.getClassName(genericType == null ? fieldType : genericType);
				String varName = StringUtils.uncapitalize(fieldType.getSimpleName()) + "Serializer";
				if (genericType != null) {
					varName = varName + EhSerializerGener.this.getNextVarIndex();
				}

				Map serializers = EhSerializerGener.this.getRootSubSerializers();
				if (!serializers.containsKey(genericName)) {
					String javaCode = null;
					String varDefine = "private final IEhSerializer<" + genericName + "> " + varName + " = ";

					if (fieldType.isEnum()) {
						javaCode = varDefine + "EnumSerialize.getSerializer(" + ClassUtils.getClassName(fieldType)
								+ ".class);";
					} else {
						varDefine = varDefine + "new IEhSerializer<" + genericName + "> () {";
						EhSerializerGener gener = new EhSerializerGener(fieldType, genericType, EhSerializerGener.this);
						javaCode = gener.codeGen(varDefine, false) + ";";
					}

					serializers.put(genericName, new EhSerializerGener.SubSerializer(varName, javaCode));
				}

				EhSerializerGener.this.code.append(varName).append(".serialize(writer, " + fieldVar + ");\n");
			}
		}
	}
}
