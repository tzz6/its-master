package com.its.framework.serialize.meta;

public class FieldMeta {
	private String name;
	private FieldType type;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FieldType getType() {
		return this.type;
	}

	public void setType(FieldType type) {
		this.type = type;
	}
}