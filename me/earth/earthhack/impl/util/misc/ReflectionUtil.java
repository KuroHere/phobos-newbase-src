// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.misc;

import java.io.*;
import java.net.*;
import java.util.function.*;
import java.lang.reflect.*;
import java.util.*;

public class ReflectionUtil
{
    public static void addToClassPath(final URLClassLoader classLoader, final File file) throws Exception {
        final URL url = file.toURI().toURL();
        addToClassPath(classLoader, url);
    }
    
    public static void addToClassPath(final URLClassLoader classLoader, final URL url) throws Exception {
        final Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        method.setAccessible(true);
        method.invoke(classLoader, url);
    }
    
    public static void iterateSuperClasses(Class<?> clazz, final Consumer<Class<?>> consumer) {
        while (clazz != Object.class) {
            consumer.accept(clazz);
            clazz = clazz.getSuperclass();
        }
    }
    
    public static <T> T getField(final Class<?> clazz, final Object instance, final int index) {
        try {
            final Field field = clazz.getDeclaredFields()[index];
            field.setAccessible(true);
            return (T)field.get(instance);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void setField(final Class<?> clazz, final Object instance, final int index, final Object value) {
        try {
            final Field field = clazz.getDeclaredFields()[index];
            field.setAccessible(true);
            field.set(instance, value);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Field getField(final Class<?> clazz, final String... mappings) throws NoSuchFieldException {
        final int length = mappings.length;
        int i = 0;
        while (i < length) {
            final String s = mappings[i];
            try {
                return clazz.getDeclaredField(s);
            }
            catch (NoSuchFieldException ex) {
                ++i;
                continue;
            }
            break;
        }
        throw new NoSuchFieldException("No Such field: " + clazz.getName() + "-> " + Arrays.toString(mappings));
    }
    
    public static Method getMethodNoParameters(final Class<?> clazz, final String... mappings) {
        final int length = mappings.length;
        int i = 0;
        while (i < length) {
            final String s = mappings[i];
            try {
                return clazz.getDeclaredMethod(s, (Class<?>[])new Class[0]);
            }
            catch (NoSuchMethodException ex) {
                ++i;
                continue;
            }
            break;
        }
        throw new RuntimeException("Couldn't find: " + Arrays.toString(mappings));
    }
    
    public static Method getMethod(final Class<?> clazz, final String notch, final String searge, final String mcp, final Class<?>... parameterTypes) {
        try {
            return clazz.getMethod(searge, parameterTypes);
        }
        catch (NoSuchMethodException e) {
            try {
                return clazz.getMethod(notch, parameterTypes);
            }
            catch (NoSuchMethodException ex) {
                try {
                    return clazz.getMethod(mcp, parameterTypes);
                }
                catch (NoSuchMethodException exc) {
                    throw new RuntimeException(exc);
                }
            }
        }
    }
    
    public static String getSimpleName(final String name) {
        return name.substring(name.lastIndexOf(".") + 1);
    }
}
