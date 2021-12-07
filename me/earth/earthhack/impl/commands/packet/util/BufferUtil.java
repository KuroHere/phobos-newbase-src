// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.util;

import io.netty.util.*;
import java.lang.reflect.*;
import java.util.*;

public class BufferUtil
{
    public static void release(final List<Object> objects) {
        for (final Object o : objects) {
            if (o instanceof ReferenceCounted) {
                releaseBuffer((ReferenceCounted)o);
            }
        }
    }
    
    public static void release(final Object... objects) {
        for (final Object o : objects) {
            if (o instanceof ReferenceCounted) {
                releaseBuffer((ReferenceCounted)o);
            }
        }
    }
    
    public static void releaseFields(final Object o) {
        for (Class<?> clazz = o.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            for (final Field f : clazz.getDeclaredFields()) {
                if (ReferenceCounted.class.isAssignableFrom(f.getType())) {
                    try {
                        f.setAccessible(true);
                        final ReferenceCounted buffer = (ReferenceCounted)f.get(o);
                        if (buffer != null) {
                            releaseBuffer(buffer);
                        }
                    }
                    catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    public static List<Object> saveReleasableFields(final Object object) {
        final List<Object> objects = new ArrayList<Object>(2);
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            for (final Field field : clazz.getDeclaredFields()) {
                if (ReferenceCounted.class.isAssignableFrom(field.getType())) {
                    try {
                        field.setAccessible(true);
                        objects.add(field.get(object));
                    }
                    catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return objects;
    }
    
    public static void releaseBuffer(final ReferenceCounted buffer) {
        buffer.release(buffer.refCnt());
    }
}
