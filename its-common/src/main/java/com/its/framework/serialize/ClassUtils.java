//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.its.framework.serialize;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import sun.reflect.generics.reflectiveObjects.GenericArrayTypeImpl;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import sun.reflect.generics.reflectiveObjects.WildcardTypeImpl;
@SuppressWarnings({"rawtypes","unchecked","unused"})
public class ClassUtils {
    private static Map<String, Class<?>> clazzMapper = new ConcurrentHashMap();
    private static Map<Type, String> clazzNameMapper = new ConcurrentHashMap();
    private static Map<String, Constructor<?>> constructors = new ConcurrentHashMap();

    public ClassUtils() {
    }

    public static Class<?> getClass(String className) {
        Class<?> clazz = (Class)clazzMapper.get(className);
        if (clazz == null) {
            try {
                clazz = Class.forName(className);
                clazzMapper.put(className, clazz);
            } catch (ClassNotFoundException var7) {
                int index = className.lastIndexOf(46);
                if (index <= 0) {
                    throw new SerializeException("class for name error: " + className, var7);
                }

                String innerClassName = className.substring(0, index) + '$' + className.substring(index + 1);

                try {
                    clazz = Class.forName(innerClassName);
                    clazzMapper.put(className, clazz);
                } catch (ClassNotFoundException var6) {
                    throw new SerializeException("class for name error: " + innerClassName, var6);
                }
            }
        }

        return clazz;
    }

    public static <T> T newClassInstance(String className) {
        try {
            return (T) getConstructor(className).newInstance();
        } catch (Exception var2) {
            throw new SerializeException("constructor instance error: " + className, var2);
        }
    }

    private static Constructor<?> getConstructor(String className) {
        Constructor<?> constructor = (Constructor)constructors.get(className);
        if (constructor == null) {
            Map var2 = constructors;
            synchronized(constructors) {
                constructor = (Constructor)constructors.get(className);
                if (constructor == null) {
                    try {
                        constructor = getClass(className).getDeclaredConstructor();
                        constructor.setAccessible(true);
                        constructors.put(className, constructor);
                    } catch (Exception var5) {
                        throw new SerializeException("access class [" + className + "]'s constructor error.");
                    }
                }
            }
        }

        return constructor;
    }

    public static String getClassName(Type clazz) {
        String className = (String)clazzNameMapper.get(clazz);
        if (className == null) {
            if (clazz instanceof Class) {
                className = ((Class)clazz).getCanonicalName();
            } else {
                className = clazz.toString();
            }

            clazzNameMapper.put(clazz, className);
        }

        return className;
    }

    public static String getClassNameNoGeneric(Type clazz) {
        return clazz instanceof ParameterizedTypeImpl ? ((ParameterizedTypeImpl)clazz).getRawType().getCanonicalName() : ((Class)clazz).getCanonicalName();
    }

    public static Field[] getFields(Class<?> clazz) {
        ArrayList fields;
        for(fields = new ArrayList(); clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] var2 = clazz.getDeclaredFields();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Field f = var2[var4];
                if (!Modifier.isStatic(f.getModifiers()) && !Modifier.isFinal(f.getModifiers()) && !Modifier.isTransient(f.getModifiers())) {
                    fields.add(f);
                }
            }
        }

        return (Field[])fields.toArray(new Field[fields.size()]);
    }

    public static Set<String> getFieldNames(Class<?> clazz) {
        Field[] fields = getFields(clazz);
        Set<String> fieldNames = new HashSet();
        Field[] var3 = fields;
        int var4 = fields.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Field field = var3[var5];
            fieldNames.add(field.getName());
        }

        return fieldNames;
    }

    public static String getFieldGetMethod(Field field) {
        String fieldName = getFieldName(field.getName());
        if (field.getType() == Boolean.TYPE) {
            try {
                if (field.getDeclaringClass().getDeclaredMethod("is" + fieldName) != null) {
                    return "is" + fieldName + "()";
                }
            } catch (Exception var3) {
                ;
            }
        }

        return "get" + fieldName + "()";
    }

    public static String getFieldSetMethod(Field field) {
        String fieldName = getFieldName(field.getName());
        return "set" + fieldName;
    }

    private static String getFieldName(String fieldName) {
        return fieldName.length() == 1 ? fieldName.toUpperCase() : Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

    public static Type getFieldOneGenericType(Type genericType, Field field, Map<String, Type> classGenericTypes) {
        if (genericType instanceof ParameterizedTypeImpl) {
            ParameterizedTypeImpl pt = (ParameterizedTypeImpl)genericType;
            Type[] types = pt.getActualTypeArguments();
            if (types != null && types.length > 0) {
                return getFieldGenericType(types[0], field, classGenericTypes);
            }
        }

        return Object.class;
    }

    public static Type[] getFieldTwoGenericType(Type genericType, Field field, Map<String, Type> classGenericTypes) {
        if (genericType instanceof ParameterizedTypeImpl) {
            ParameterizedTypeImpl pt = (ParameterizedTypeImpl)genericType;
            Type[] types = pt.getActualTypeArguments();
            if (types != null && types.length > 0) {
                Type[] result = new Type[]{getFieldGenericType(types[0], field, classGenericTypes), getFieldGenericType(types[1], field, classGenericTypes)};
                return result;
            }
        }

        return new Type[]{Object.class, Object.class};
    }

    public static Type getArrayComponentType(Class<?> arrayClass, Type genericType, Field field, Map<String, Type> classGenericTypes) {
        if (genericType instanceof GenericArrayTypeImpl) {
            Type genComType = ((GenericArrayTypeImpl)genericType).getGenericComponentType();
            return getFieldGenericType(genComType, field, classGenericTypes);
        } else {
            return arrayClass.getComponentType();
        }
    }

    private static Type getFieldGenericType(Type type, Field field, Map<String, Type> classGenericTypes) {
        if (type instanceof WildcardTypeImpl) {
            return Object.class;
        } else {
            Type genType = (Type)classGenericTypes.get(field.getDeclaringClass().toString() + type);
            return genType == null ? type : genType;
        }
    }

    public static Class<?> getTypeClass(Type type) {
        if (type instanceof ParameterizedTypeImpl) {
            return ((ParameterizedTypeImpl)type).getRawType();
        } else if (type instanceof GenericArrayTypeImpl) {
            Class<?> componentType = (Class)((GenericArrayTypeImpl)type).getGenericComponentType();
            return Array.newInstance(componentType, 0).getClass();
        } else {
            return (Class)type;
        }
    }
}
