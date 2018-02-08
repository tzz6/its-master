package com.its.framework.serialize.compiler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

public class ClassFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {
	private List<JavaClassObject> javaClassObjectList;

	public ClassFileManager(StandardJavaFileManager standardManager) {
		super(standardManager);
		this.javaClassObjectList = new ArrayList<JavaClassObject>();
	}

	public JavaClassObject getMainJavaClassObject() {
		if ((this.javaClassObjectList != null) && (this.javaClassObjectList.size() > 0)) {
			int size = this.javaClassObjectList.size();
			return (JavaClassObject) this.javaClassObjectList.get(size - 1);
		}
		return null;
	}

	public List<JavaClassObject> getInnerClassJavaClassObject() {
		if ((this.javaClassObjectList != null) && (this.javaClassObjectList.size() > 0)) {
			int size = this.javaClassObjectList.size();
			if (size == 1) {
				return null;
			}
			return this.javaClassObjectList.subList(0, size - 1);
		}
		return null;
	}

	public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className,
			JavaFileObject.Kind kind, FileObject sibling) throws IOException {
		JavaClassObject jclassObject = new JavaClassObject(className, kind);
		this.javaClassObjectList.add(jclassObject);
		return jclassObject;
	}
}