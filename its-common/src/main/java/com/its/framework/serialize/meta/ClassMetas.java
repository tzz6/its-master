package com.its.framework.serialize.meta;

import java.util.ArrayList;
import java.util.List;

public class ClassMetas {
	private List<ClassMeta> classes;

	public List<ClassMeta> getClasses() {
		return this.classes;
	}

	public void addClass(ClassMeta cm) {
		if (this.classes == null) {
			this.classes = new ArrayList();
		}

		if (!contains(cm.getName()))
			this.classes.add(cm);
	}

	public boolean contains(String className) {
		if ((this.classes == null) || (className == null)) {
			return false;
		}

		for (ClassMeta cm : this.classes) {
			if (className.equals(cm.getName())) {
				return true;
			}
		}

		return false;
	}

	public ClassMeta getMainClass() {
		return (ClassMeta) this.classes.get(0);
	}

	public ClassMeta getClass(int index) {
		if ((index >= 0) && (index < this.classes.size())) {
			return (ClassMeta) this.classes.get(index);
		}
		return null;
	}

	public int getClassCount() {
		return this.classes == null ? 0 : this.classes.size();
	}

	public void setClasses(List<ClassMeta> classes) {
		this.classes = classes;
	}
}
