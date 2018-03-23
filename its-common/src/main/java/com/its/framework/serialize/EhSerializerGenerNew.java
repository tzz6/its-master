package com.its.framework.serialize;

import com.its.framework.serialize.meta.ClassMeta;
import com.its.framework.serialize.meta.ClassMetas;
import com.its.framework.serialize.meta.FieldMeta;
import com.its.framework.serialize.meta.FieldType;
import com.its.framework.serialize.meta.FieldTypeHelper;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang.StringUtils;
@SuppressWarnings({"unused"})
public class EhSerializerGenerNew {
	private static final String SERIALIZER_PACKAGE = "package com.its.framework.serialize;\n";
	private static final String SERIALIZER_IMPORT = "import com.its.framework.serialize.reader.*;\nimport com.its.framework.serialize.writer.*;\n";
	private static final String MAIN_CLASS_DECLARE = "public final class C%d implements IEhSerializer<%s> {\n";
	private static final String SUB_CLASS_DECLARE = "private final IEhSerializer<%s> %sSerializer = new IEhSerializer<%s>() {\n";
	private static final String ENUM_CLASS_DECLARE = "private final IEhSerializer<%s> %sSerializer = EnumSerialize.getSerializer(%s.class);\n";
	private static final String SERIALIZE_METHOD_DECLARE = "public final void serialize(final Writer writer, final %s o) {\n";
	private static final String DESERIALIZE_METHOD_DECLARE = "public final %s deSerialize(final Reader reader) {\n";
	private static final String COLLECTION_WRITER_1 = "writer.writeCollection(%s, new ICollectionWriter<%s>() {\n";
	private static final String COLLECTION_WRITER_2 = "public final void writeItem(final %s item) {\n";
	private static final String COLLECTION_READER_1 = "reader.readCollection(new ICollectionReader<%s>() {\n";
	private static final String COLLECTION_READER_2 = "public final %s readItem() {\n";
	private static final String MAP_WRITER_1 = "writer.writeMap(%s, new IMapWriter<%s, %s>() {\n";
	private static final String MAP_WRITER_2 = "public final void writeItem(final %s key, final %s value) {\n";
	private static final String MAP_READER_1 = "reader.readMap(new IMapReader<%s, %s>() {\n";
	private static final String MAP_READER_2 = "public final %s readKey() {\n";
	private static final String MAP_READER_3 = "public final %s readValue() {\n";
	private static final String ARRAY_WRITER_1 = "writer.writeArray(%s, new IArrayWriter<%s>() {\n";
	private static final String ARRAY_WRITER_2 = "public final void writeItem(final %s item) {\n";
	private static final String ARRAY_READER_1 = "reader.readArray(new IArrayReader<%s>() {\n";
	private static final String ARRAY_READER_2 = "public final %s[] createArray(final int size) {\n";
	private static final String ARRAY_READER_3 = "public final %s readItem() {\n";
	private static final String LARGE_MAP_WRITER_1 = "writer.writeLargeMap(%s, new ILargeMapWriter<%s, %s>() {\n";
	private static final String LARGE_MAP_WRITER_2 = "public final void writeItem(final Writer writer, final %s key, final %s value) {\n";
	private static final String LARGE_MAP_READER_1 = "reader.readLargeMap(new ILargeMapReader<%s, %s>() {\n";
	private static final String LARGE_MAP_READER_2 = "public final %s readKey(final Reader reader) {\n";
	private static final String LARGE_MAP_READER_3 = "public final %s readValue(final Reader reader) {\n";
	private static final String LARGE_SET_WRITER_1 = "writer.writeLargeSet(%s, new ILargeSetWriter<%s>() {\n";
	private static final String LARGE_SET_WRITER_2 = "public final void writeItem(final Writer writer, final %s item) {\n";
	private static final String LARGE_SET_READER_1 = "reader.readLargeSet(new ILargeSetReader<%s>() {\n";
	private static final String LARGE_SET_READER_2 = "public final %s readItem(final Reader reader) {\n";
	private static AtomicInteger classIndex = new AtomicInteger();
	private ClassMetas cms;
	private ClassMeta cm;
	private Set<String> fields;
	private StringBuilder code;

	public EhSerializerGenerNew(ClassMetas cms) {
		this.cms = cms;
		this.code = new StringBuilder();
	}

	public String codeGen(boolean isGenSerializeCode) {
		this.code.append("package com.its.framework.serialize;\n");
		this.code.append("import com.its.framework.serialize.reader.*;\nimport com.its.framework.serialize.writer.*;\n");

		this.cm = this.cms.getMainClass();
		this.code.append(String.format("public final class C%d implements IEhSerializer<%s> {\n",
				new Object[] { Integer.valueOf(classIndex.incrementAndGet()), this.cm.getName() }));
		genClassSerializer(isGenSerializeCode);

		for (int i = 1; i < this.cms.getClassCount(); i++) {
			this.cm = this.cms.getClass(i);
			if (ClassUtils.getClass(this.cm.getName()).isEnum()) {
				genEnumSerializer();
			} else {
				this.code.append(
						String.format("private final IEhSerializer<%s> %sSerializer = new IEhSerializer<%s>() {\n",
								new Object[] { this.cm.getName(), this.cm.getSimpleName(), this.cm.getName() }));
				genClassSerializer(isGenSerializeCode);
				this.code.append("};\n");
			}
		}

		this.code.append("}");
		return this.code.toString();
	}

	private void genClassSerializer(boolean isGenSerializeCode) {
		this.fields = ClassUtils.getFieldNames(ClassUtils.getClass(this.cm.getName()));
		new SerializeCodeGener().codeGen(isGenSerializeCode);
		new DeSerializeCodeGener().codeGen();
	}

	private void genEnumSerializer() {
		this.code.append(
				String.format("private final IEhSerializer<%s> %sSerializer = EnumSerialize.getSerializer(%s.class);\n",
						new Object[] { this.cm.getName(), this.cm.getSimpleName(), this.cm.getName() }));
	}

	class DeSerializeCodeGener {
		private StringBuilder fieldCode = new StringBuilder();

		DeSerializeCodeGener() {
		}

		public void codeGen() {
			EhSerializerGenerNew.this.code.append(String.format("public final %s deSerialize(final Reader reader) {\n",
					new Object[] { EhSerializerGenerNew.this.getClass().getName() }));
			EhSerializerGenerNew.this.code.append("if (reader.readNullTag()) {\n");
			EhSerializerGenerNew.this.code.append(String.format("%s o = new %s();\n", new Object[] {
					EhSerializerGenerNew.this.getClass().getName(), EhSerializerGenerNew.this.getClass().getName() }));

			for (FieldMeta field : EhSerializerGenerNew.this.cm.getFields()) {
				this.fieldCode.setLength(0);

				genFieldDeSerializeCode(field, field.getType(), false);

				if (EhSerializerGenerNew.this.fields.contains(field.getName())) {
					EhSerializerGenerNew.this.code.append("o.set").append(StringUtils.capitalize(field.getName()))
							.append("(");
					DataType dataType = DataTypeHelper.getDataType(field.getType());
					if ((dataType == DataType.dt_COLLECTION) || (dataType == DataType.dt_MAP)
							|| (dataType == DataType.dt_LARGE_MAP) || (dataType == DataType.dt_LARGE_SET)) {
						EhSerializerGenerNew.this.code.append("(").append(FieldTypeHelper.getTypeName(field.getType()))
								.append(")");
					}
					EhSerializerGenerNew.this.code.append(this.fieldCode).append(");\n");
				} else {
					EhSerializerGenerNew.this.code.append(this.fieldCode).append(";\n");
				}
			}

			EhSerializerGenerNew.this.code.append("return o;\n");
			EhSerializerGenerNew.this.code.append("} else {\n");
			EhSerializerGenerNew.this.code.append("return null;\n");
			EhSerializerGenerNew.this.code.append("}}\n");
		}

		private void genFieldDeSerializeCode(FieldMeta field, FieldType fieldType, boolean subMethod) {
			this.fieldCode.append(subMethod ? "return " : "");

			DataType dataType = DataTypeHelper.getDataType(fieldType);
			switch (dataType.ordinal()) {
			case 1:
				genCollectionFieldDeSerializeCode(field, fieldType, subMethod);
				break;
			case 2:
				genMapFieldDeSerializeCode(field, fieldType, subMethod);
				break;
			case 3:
				genArrayFieldDeSerializeCode(field, fieldType, subMethod);
				break;
			case 4:
				genLargeMapFieldDeSerializeCode(field, fieldType, subMethod);
				break;
			case 5:
				genLargeSetFieldDeSerializeCode(field, fieldType, subMethod);
				break;
			case 6:
				genObjectFieldDeSerializeCode(field, fieldType, subMethod);
				break;
			default:
				this.fieldCode.append("reader.read").append(dataType).append("()");
			}

			this.fieldCode.append(subMethod ? ";\n" : "");
		}

		private void genCollectionFieldDeSerializeCode(FieldMeta field, FieldType fieldType, boolean subMethod) {
			FieldType itemType = fieldType.getGenericTypes()[0];
			String itemTypeName = FieldTypeHelper.getTypeName(itemType);
			this.fieldCode.append(String.format("reader.readCollection(new ICollectionReader<%s>() {\n",
					new Object[] { itemTypeName }));
			this.fieldCode.append(String.format("public final %s readItem() {\n", new Object[] { itemTypeName }));
			genFieldDeSerializeCode(field, itemType, true);
			this.fieldCode.append("}})");
		}

		private void genMapFieldDeSerializeCode(FieldMeta field, FieldType fieldType, boolean subMethod) {
			FieldType keyType = fieldType.getGenericTypes()[0];
			FieldType valueType = fieldType.getGenericTypes()[1];
			String keyTypeName = FieldTypeHelper.getTypeName(keyType);
			String valueTypeName = FieldTypeHelper.getTypeName(valueType);
			this.fieldCode.append(String.format("reader.readMap(new IMapReader<%s, %s>() {\n",
					new Object[] { keyTypeName, valueTypeName }));
			this.fieldCode.append(String.format("public final %s readKey() {\n", new Object[] { keyTypeName }));
			genFieldDeSerializeCode(field, keyType, true);
			this.fieldCode.append("}\n");
			this.fieldCode.append(String.format("public final %s readValue() {\n", new Object[] { valueTypeName }));
			genFieldDeSerializeCode(field, valueType, true);
			this.fieldCode.append("}})");
		}

		private void genArrayFieldDeSerializeCode(FieldMeta field, FieldType fieldType, boolean subMethod) {
			FieldType itemType = null;
			if (fieldType.getGenericTypes() == null) {
				itemType = new FieldType();
				itemType.setName(fieldType.getName());
			} else {
				itemType = fieldType.getGenericTypes()[0];
			}

			String itemTypeName = FieldTypeHelper.getTypeName(itemType);
			this.fieldCode.append(
					String.format("reader.readArray(new IArrayReader<%s>() {\n", new Object[] { itemTypeName }));
			this.fieldCode.append(
					String.format("public final %s[] createArray(final int size) {\n", new Object[] { itemTypeName }));

			int index = itemTypeName.indexOf("[]");
			if (index != -1) {
				String arrayTypeName = new StringBuilder().append(itemTypeName.substring(0, index)).append("[size]")
						.append(itemTypeName.substring(index)).toString();
				this.fieldCode.append(String.format("return new %s;\n", new Object[] { arrayTypeName }));
			} else {
				this.fieldCode.append(String.format("return new %s[size];\n", new Object[] { itemTypeName }));
			}
			this.fieldCode.append("}\n");

			this.fieldCode.append(String.format("public final %s readItem() {\n", new Object[] { itemTypeName }));
			genFieldDeSerializeCode(field, itemType, true);
			this.fieldCode.append("}\n");
			this.fieldCode.append("})");
		}

		private void genLargeMapFieldDeSerializeCode(FieldMeta field, FieldType fieldType, boolean subMethod) {
			FieldType keyType = fieldType.getGenericTypes()[0];
			FieldType valueType = fieldType.getGenericTypes()[1];
			String keyTypeName = FieldTypeHelper.getTypeName(keyType);
			String valueTypeName = FieldTypeHelper.getTypeName(valueType);
			this.fieldCode.append(String.format("reader.readLargeMap(new ILargeMapReader<%s, %s>() {\n",
					new Object[] { keyTypeName, valueTypeName }));
			this.fieldCode.append(
					String.format("public final %s readKey(final Reader reader) {\n", new Object[] { keyTypeName }));
			genFieldDeSerializeCode(field, keyType, true);
			this.fieldCode.append("}\n");
			this.fieldCode.append(String.format("public final %s readValue(final Reader reader) {\n",
					new Object[] { valueTypeName }));
			genFieldDeSerializeCode(field, valueType, true);
			this.fieldCode.append("}})");
		}

		private void genLargeSetFieldDeSerializeCode(FieldMeta field, FieldType fieldType, boolean subMethod) {
			FieldType itemType = fieldType.getGenericTypes()[0];
			String itemTypeName = FieldTypeHelper.getTypeName(itemType);
			this.fieldCode.append(
					String.format("reader.readLargeSet(new ILargeSetReader<%s>() {\n", new Object[] { itemTypeName }));
			this.fieldCode.append(
					String.format("public final %s readItem(final Reader reader) {\n", new Object[] { itemTypeName }));
			genFieldDeSerializeCode(field, itemType, true);
			this.fieldCode.append("}})");
		}

		private void genObjectFieldDeSerializeCode(FieldMeta field, FieldType fieldType, boolean subMethod) {
			if (fieldType.getName() == EhSerializerGenerNew.this.cms.getMainClass().getName()) {
				this.fieldCode.append("deSerialize(reader)");
			} else if (EhSerializerGenerNew.this.cms.contains(fieldType.getName())) {
				String serializerName = new StringBuilder().append(fieldType.getSimpleName()).append("Serializer")
						.toString();
				this.fieldCode.append(serializerName).append(".deSerialize(reader)");
			} else {
				this.fieldCode.append("reader.readObject()");
			}
		}
	}

	class SerializeCodeGener {
		SerializeCodeGener() {
		}

		public void codeGen(boolean isGenSerializeCode) {
			EhSerializerGenerNew.this.code
					.append(String.format("public final void serialize(final Writer writer, final %s o) {\n",
							new Object[] { EhSerializerGenerNew.this.getClass().getName() }));
			if (isGenSerializeCode) {
				EhSerializerGenerNew.this.code.append("if (writer.writeNullTag(o)) {\n");

				for (FieldMeta field : EhSerializerGenerNew.this.cm.getFields()) {
					if (EhSerializerGenerNew.this.fields.contains(field.getName())) {
						String fieldVar = "o.get" + StringUtils.capitalize(field.getName()) + "()";
						genFieldSerializeCode(field, field.getType(), fieldVar);
					}
				}

				EhSerializerGenerNew.this.code.append("}\n");
			}
			EhSerializerGenerNew.this.code.append("}\n");
		}

		private void genFieldSerializeCode(FieldMeta field, FieldType fieldType, String fieldVar) {
			DataType dataType = DataTypeHelper.getDataType(fieldType);
			switch (dataType.ordinal()) {
			case 1:
				genCollectionFieldSerializeCode(field, fieldType, fieldVar);
				break;
			case 2:
				genMapFieldSerializeCode(field, fieldType, fieldVar);
				break;
			case 3:
				genArrayFieldSerializeCode(field, fieldType, fieldVar);
				break;
			case 4:
				genLargeMapFieldSerializeCode(field, fieldType, fieldVar);
				break;
			case 5:
				genLargeSetFieldSerializeCode(field, fieldType, fieldVar);
				break;
			case 6:
				genObjectFieldSerializeCode(field, fieldType, fieldVar);
				break;
			default:
				EhSerializerGenerNew.this.code.append("writer.write").append(dataType).append("(").append(fieldVar)
						.append(");\n");
			}
		}

		private void genCollectionFieldSerializeCode(FieldMeta field, FieldType fieldType, String fieldVar) {
			FieldType itemType = fieldType.getGenericTypes()[0];
			String itemTypeName = FieldTypeHelper.getTypeName(itemType);
			EhSerializerGenerNew.this.code
					.append(String.format("writer.writeCollection(%s, new ICollectionWriter<%s>() {\n",
							new Object[] { fieldVar, itemTypeName }));
			EhSerializerGenerNew.this.code.append(
					String.format("public final void writeItem(final %s item) {\n", new Object[] { itemTypeName }));
			genFieldSerializeCode(field, itemType, "item");
			EhSerializerGenerNew.this.code.append("}});\n");
		}

		private void genMapFieldSerializeCode(FieldMeta field, FieldType fieldType, String fieldVar) {
			FieldType keyType = fieldType.getGenericTypes()[0];
			FieldType valueType = fieldType.getGenericTypes()[1];
			String keyTypeName = FieldTypeHelper.getTypeName(keyType);
			String valueTypeName = FieldTypeHelper.getTypeName(valueType);
			EhSerializerGenerNew.this.code.append(String.format("writer.writeMap(%s, new IMapWriter<%s, %s>() {\n",
					new Object[] { fieldVar, keyTypeName, valueTypeName }));
			EhSerializerGenerNew.this.code
					.append(String.format("public final void writeItem(final %s key, final %s value) {\n",
							new Object[] { keyTypeName, valueTypeName }));
			genFieldSerializeCode(field, keyType, "key");
			genFieldSerializeCode(field, valueType, "value");
			EhSerializerGenerNew.this.code.append("}});\n");
		}

		private void genArrayFieldSerializeCode(FieldMeta field, FieldType fieldType, String fieldVar) {
			FieldType itemType = null;
			if (fieldType.getGenericTypes() == null) {
				itemType = new FieldType();
				itemType.setName(fieldType.getName());
			} else {
				itemType = fieldType.getGenericTypes()[0];
			}

			String itemTypeName = FieldTypeHelper.getTypeName(itemType);
			EhSerializerGenerNew.this.code.append(String.format("writer.writeArray(%s, new IArrayWriter<%s>() {\n",
					new Object[] { fieldVar, itemTypeName }));
			EhSerializerGenerNew.this.code.append(
					String.format("public final void writeItem(final %s item) {\n", new Object[] { itemTypeName }));
			genFieldSerializeCode(field, itemType, "item");
			EhSerializerGenerNew.this.code.append("}});\n");
		}

		private void genLargeMapFieldSerializeCode(FieldMeta field, FieldType fieldType, String fieldVar) {
			FieldType keyType = fieldType.getGenericTypes()[0];
			FieldType valueType = fieldType.getGenericTypes()[1];
			String keyTypeName = FieldTypeHelper.getTypeName(keyType);
			String valueTypeName = FieldTypeHelper.getTypeName(valueType);
			EhSerializerGenerNew.this.code
					.append(String.format("writer.writeLargeMap(%s, new ILargeMapWriter<%s, %s>() {\n",
							new Object[] { fieldVar, keyTypeName, valueTypeName }));
			EhSerializerGenerNew.this.code.append(
					String.format("public final void writeItem(final Writer writer, final %s key, final %s value) {\n",
							new Object[] { keyTypeName, valueTypeName }));
			genFieldSerializeCode(field, keyType, "key");
			genFieldSerializeCode(field, valueType, "value");
			EhSerializerGenerNew.this.code.append("}});\n");
		}

		private void genLargeSetFieldSerializeCode(FieldMeta field, FieldType fieldType, String fieldVar) {
			FieldType itemType = fieldType.getGenericTypes()[0];
			String itemTypeName = FieldTypeHelper.getTypeName(itemType);
			EhSerializerGenerNew.this.code.append(String.format(
					"writer.writeLargeSet(%s, new ILargeSetWriter<%s>() {\n", new Object[] { fieldVar, itemTypeName }));
			EhSerializerGenerNew.this.code
					.append(String.format("public final void writeItem(final Writer writer, final %s item) {\n",
							new Object[] { itemTypeName }));
			genFieldSerializeCode(field, itemType, "item");
			EhSerializerGenerNew.this.code.append("}});\n");
		}

		private void genObjectFieldSerializeCode(FieldMeta field, FieldType fieldType, String fieldVar) {
			if (fieldType.getName() == EhSerializerGenerNew.this.cms.getMainClass().getName()) {
				EhSerializerGenerNew.this.code.append("serialize(writer, " + fieldVar + ");\n");
			} else if (EhSerializerGenerNew.this.cms.contains(fieldType.getName())) {
				String serializerName = fieldType.getSimpleName() + "Serializer";
				EhSerializerGenerNew.this.code.append(serializerName).append(".serialize(writer, ").append(fieldVar)
						.append(");\n");
			} else {
				EhSerializerGenerNew.this.code.append("writer.writeObject(" + fieldVar + ");\n");
			}
		}
	}
}
