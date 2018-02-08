package com.its.framework.serialize.compiler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

public class JavaClassObject extends SimpleJavaFileObject {
	private final ByteArrayOutputStream bos = new ByteArrayOutputStream();
	private final String className;

	public JavaClassObject(String name, JavaFileObject.Kind kind) {
		super(URI.create("string:///" + name.replace('.', '/') + kind.extension), kind);
		this.className = name;
	}

	public byte[] getBytes() {
		return this.bos.toByteArray();
	}

	public OutputStream openOutputStream() throws IOException {
		return this.bos;
	}

	public String getClassName() {
		return this.className;
	}
}