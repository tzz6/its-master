package com.its.framework.serialize.compiler;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@SuppressWarnings({"rawtypes","unchecked","resource"})
public class DynamicEngine {
	private static final Logger logger = LoggerFactory.getLogger(DynamicEngine.class);
	private static DynamicEngine instance = new DynamicEngine();
	private URLClassLoader parentClassLoader;
	private String classpath;

	public static DynamicEngine getInstance() {
		return instance;
	}

	public DynamicEngine() {
		this.parentClassLoader = ((URLClassLoader) getClass().getClassLoader());
		buildClassPath();
	}

	private void buildClassPath() {
		this.classpath = null;
		StringBuilder sb = new StringBuilder(".");
		sb.append(File.pathSeparator);
		for (URL url : this.parentClassLoader.getURLs()) {
			try {
				String p = URLDecoder.decode(url.getFile(), "utf8");
				sb.append(p).append(File.pathSeparator);
			} catch (UnsupportedEncodingException e) {
				logger.warn("unsupported encoding: utf8", e);
			}
		}
		this.classpath = sb.toString();
	}

	public Object javaCodeToObject(String fullClassName, String javaCode)
			throws IllegalAccessException, InstantiationException {
		long start = System.currentTimeMillis();
		Object instance = null;
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DiagnosticCollector diagnostics = new DiagnosticCollector();
		ClassFileManager fileManager = new ClassFileManager(compiler.getStandardFileManager(diagnostics, null, null));

		List jfiles = new ArrayList();
		jfiles.add(new CharSequenceJavaFileObject(fullClassName, javaCode));

		List options = new ArrayList();
		options.add("-encoding");
		options.add("UTF-8");
		options.add("-classpath");
		options.add(this.classpath);

		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options, null, jfiles);
		boolean success = task.call().booleanValue();
		JavaClassObject jco;
		if (success) {
			DynamicClassLoader dynamicClassLoader = new DynamicClassLoader(this.parentClassLoader);
			jco = fileManager.getMainJavaClassObject();
			List<JavaClassObject> innerClassJcos = fileManager.getInnerClassJavaClassObject();
			if ((innerClassJcos != null) && (innerClassJcos.size() > 0)) {
				for (JavaClassObject inner : innerClassJcos) {
					dynamicClassLoader.loadClass(inner.getClassName(), inner);
				}
			}

			Class clazz = dynamicClassLoader.loadClass(fullClassName, jco);
			if (clazz != null)
				instance = clazz.newInstance();
		} else {
			String error = "";
			List<Diagnostic> diagnosticList = diagnostics.getDiagnostics();
			for (Diagnostic diagnostic : diagnosticList) {
				error = new StringBuilder().append(error).append(compilePrint(diagnostic)).toString();
			}
			logger.error(error);
		}

		long end = System.currentTimeMillis();

		if (logger.isDebugEnabled()) {
			logger.debug(
					new StringBuilder().append("javaCodeToObject use:").append(end - start).append("ms").toString());
		}
		return instance;
	}

	private String compilePrint(Diagnostic<?> diagnostic) {
		StringBuffer res = new StringBuffer();
		res.append(new StringBuilder().append("Code:[").append(diagnostic.getCode()).append("]\n").toString());
		res.append(new StringBuilder().append("Kind:[").append(diagnostic.getKind()).append("]\n").toString());
		res.append(new StringBuilder().append("Position:[").append(diagnostic.getPosition()).append("]\n").toString());
		res.append(new StringBuilder().append("Start Position:[").append(diagnostic.getStartPosition()).append("]\n")
				.toString());
		res.append(new StringBuilder().append("End Position:[").append(diagnostic.getEndPosition()).append("]\n")
				.toString());
		res.append(new StringBuilder().append("Source:[").append(diagnostic.getSource()).append("]\n").toString());
		res.append(
				new StringBuilder().append("Message:[").append(diagnostic.getMessage(null)).append("]\n").toString());
		res.append(
				new StringBuilder().append("LineNumber:[").append(diagnostic.getLineNumber()).append("]\n").toString());
		res.append(new StringBuilder().append("ColumnNumber:[").append(diagnostic.getColumnNumber()).append("]\n")
				.toString());
		return res.toString();
	}
}
