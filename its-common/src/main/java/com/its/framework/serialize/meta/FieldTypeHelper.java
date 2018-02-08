package com.its.framework.serialize.meta;

public class FieldTypeHelper {
	public static String getTypeName(FieldType fieldType) {
		StringBuilder typeNames = new StringBuilder();
		getTypeName(typeNames, fieldType);
		return typeNames.toString();
	}

	private static void getTypeName(StringBuilder typeNames, FieldType fieldType) {
		if (fieldType.isArray()) {
			getArrayTypeName(typeNames, fieldType);
		} else {
			typeNames.append(fieldType.getName());
			if (fieldType.getGenericTypes() != null) {
				typeNames.append("<");
				for (FieldType ft : fieldType.getGenericTypes()) {
					getTypeName(typeNames, ft);
					typeNames.append(",");
				}
				typeNames.setCharAt(typeNames.length() - 1, '>');
			}
		}
	}

	private static void getArrayTypeName(StringBuilder typeNames, FieldType fieldType) {
		String array = "";
		while ((fieldType != null) && (fieldType.isArray())) {
			array = array + "[]";
			if (fieldType.getGenericTypes() == null)
				break;
			fieldType = fieldType.getGenericTypes()[0];
		}

		if (fieldType != null)
			typeNames.append(getFieldTypeName(fieldType)).append(array);
	}

	private static String getFieldTypeName(FieldType fieldType) {
		String typeName = fieldType.getName();
		if (typeName == null) {
			fieldType = fieldType.getGenericTypes()[0];
			typeName = fieldType.getName();
		}

		if (fieldType.getGenericTypes() != null) {
			typeName = typeName + "<";
			for (FieldType ft : fieldType.getGenericTypes()) {
				if (!typeName.endsWith("<")) {
					typeName = typeName + ",";
				}
				typeName = typeName + getFieldTypeName(ft);
			}
			typeName = typeName + ">";
		}

		return typeName;
	}
}
