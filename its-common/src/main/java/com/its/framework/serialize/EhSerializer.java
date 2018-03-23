package com.its.framework.serialize;

import com.its.framework.serialize.compiler.DynamicClassUtil;
import com.its.framework.serialize.meta.ClassMetaParser;
import com.its.framework.serialize.meta.ClassMetas;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@SuppressWarnings({"rawtypes","unchecked"})
public class EhSerializer {
	private static Logger logger = LoggerFactory.getLogger(EhSerializer.class);
	private Map<Class<?>, IEhSerializer<?>> serializers;
	private Map<String, IEhSerializer<?>> compatibleSerializers;
	private Map<Class<?>, ClassMetas> classMetas;

	private EhSerializer() {
		this.serializers = DefaultSerializer.init();
		this.compatibleSerializers = new HashMap();
		this.classMetas = new HashMap();
	}

	public static <T> IEhSerializer<T> getSerializer(Class<?> clazz) {
		return EhSerializerHolder.instance.getEhSerializer(clazz);
	}

	public static ClassMetas getClassMeta(Class<?> clazz) {
		return EhSerializerHolder.instance.generateClassMeta(clazz);
	}

	public static <T> IEhSerializer<T> getCompatibleSerializer(Class<?> clazz, int entityVersion) {
		String key = ClassUtils.getClassName(clazz) + entityVersion;
		return (IEhSerializer) EhSerializerHolder.instance.compatibleSerializers.get(key);
	}

	public static <T> IEhSerializer<T> complieAndGetCompatibleSerializer(Class<?> clazz, int entityVersion,
			ClassMetas cm) {
		return EhSerializerHolder.instance.complieAndGetEhCompatibleSerializer(clazz, entityVersion, cm);
	}

	private <T> IEhSerializer<T> getEhSerializer(Class<?> clazz) {
		IEhSerializer serializer = (IEhSerializer) this.serializers.get(clazz);
		if (serializer == null) {
			synchronized (this.serializers) {
				serializer = (IEhSerializer) this.serializers.get(clazz);
				if (serializer == null) {
					serializer = generateSerializer(clazz);
					this.serializers.put(clazz, serializer);
				}
			}
		}

		return serializer;
	}

	private ClassMetas generateClassMeta(Class<?> clazz) {
		ClassMetas cm = (ClassMetas) this.classMetas.get(clazz);
		if (cm == null) {
			getEhSerializer(clazz);
			cm = (ClassMetas) this.classMetas.get(clazz);
		}
		return cm;
	}

	private IEhSerializer<?> generateSerializer(Class<?> clazz) {
		ClassMetas cm = ClassMetaParser.parseClass(clazz);
		String code = new EhSerializerGenerNew(cm).codeGen(true);
		this.classMetas.put(clazz, cm);
		if (logger.isDebugEnabled()) {
			logger.debug("=== compile serializer: " + clazz.getCanonicalName() + "\n" + code);
		}
		return (IEhSerializer) DynamicClassUtil.javaCodeToObject(code);
	}

	private <T> IEhSerializer<T> complieAndGetEhCompatibleSerializer(Class<?> clazz, int entityVersion, ClassMetas cm) {
		String key = ClassUtils.getClassName(clazz) + entityVersion;
		IEhSerializer serializer = (IEhSerializer) this.compatibleSerializers.get(key);
		if (serializer == null) {
			synchronized (this.compatibleSerializers) {
				serializer = (IEhSerializer) this.compatibleSerializers.get(key);
				if (serializer == null) {
					String code = new EhSerializerGenerNew(cm).codeGen(false);
					if (logger.isInfoEnabled()) {
						logger.info("=== compile compatible serializer: " + key + "\n" + code);
					}

					serializer = (IEhSerializer) DynamicClassUtil.javaCodeToObject(code);
					this.compatibleSerializers.put(key, serializer);
				}
			}
		}

		return serializer;
	}

	static class EhSerializerHolder {
		static EhSerializer instance = new EhSerializer();
	}
}
