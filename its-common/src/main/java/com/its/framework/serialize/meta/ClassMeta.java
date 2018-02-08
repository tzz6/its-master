package com.its.framework.serialize.meta;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class ClassMeta {
	private String name;
	private List<FieldMeta> fields;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FieldMeta> getFields() {
		return this.fields;
	}

	public void addField(FieldMeta field) {
		if (this.fields == null) {
			this.fields = new ArrayList<FieldMeta>();
		}
		this.fields.add(field);
	}

	public void setFields(List<FieldMeta> fields) {
		this.fields = fields;
	}

	public String getSimpleName() {
		int index = this.name.lastIndexOf('.');
		if (index > 0) {
			return StringUtils.uncapitalize(this.name.substring(index + 1));
		}
		return StringUtils.uncapitalize(this.name);
	}
}
