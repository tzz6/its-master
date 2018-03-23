package com.its.framework.serialize.compiler;
@SuppressWarnings({"unchecked"})
public class DynamicClassUtil {
	public static <T> T javaCodeToObject(String javaCode) {
		try {
			String fullClassName = getJavaFullClassName(javaCode);
			DynamicEngine de = DynamicEngine.getInstance();
			return (T) de.javaCodeToObject(fullClassName, javaCode);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isJavaCode(String code) {
		return (code.startsWith("package ")) && (code.contains("public class "));
	}

	private static String getJavaFullClassName(String javaCode) {
		int idx1 = javaCode.indexOf("package");
		int idx2 = javaCode.indexOf(';', idx1);
		String packageName = javaCode.substring(idx1 + 7, idx2).trim();

		idx1 = javaCode.indexOf("class ");
		idx2 = javaCode.indexOf("implements ", idx1);
		if (idx2 < 0) {
			idx2 = javaCode.indexOf("extends ", idx1);
		}
		String className = javaCode.substring(idx1 + 5, idx2).trim();
		return packageName + "." + className;
	}
}
