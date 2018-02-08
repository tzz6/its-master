package com.its.framework.serialize.meta;

import org.apache.commons.lang.StringUtils;

public class FieldType {
	private String name;
	private boolean array;
	private FieldType[] genericTypes;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isArray() {
		return this.array;
	}

	public boolean getArray() {
		return this.array;
	}

	public void setArray(boolean array) {
		this.array = array;
	}

	public FieldType[] getGenericTypes() {
		return this.genericTypes;
	}

	public void setGenericTypes(FieldType[] genericTypes) {
		this.genericTypes = genericTypes;
	}

	public void addGenericType(FieldType genericType) {
		int len = this.genericTypes == null ? 0 : this.genericTypes.length;
		FieldType[] newTypes = new FieldType[len + 1];
		for (int i = 0; i < len; i++) {
			newTypes[i] = this.genericTypes[i];
		}
		newTypes[len] = genericType;

		this.genericTypes = newTypes;
	}

	public String getSimpleName() {
		int index = this.name.lastIndexOf('.');
		if (index > 0) {
			return StringUtils.uncapitalize(this.name.substring(index + 1));
		}
		return StringUtils.uncapitalize(this.name);
	}
}
