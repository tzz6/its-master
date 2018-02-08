package com.its.framework.serialize.compiler;

import java.net.URI;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

public class CharSequenceJavaFileObject extends SimpleJavaFileObject {
	private CharSequence content;

	public CharSequenceJavaFileObject(String className, CharSequence content) {
		super(URI.create("string:///" + className.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension),
				JavaFileObject.Kind.SOURCE);

		this.content = content;
	}

	public CharSequence getCharContent(boolean ignoreEncodingErrors) {
		return this.content;
	}
}
