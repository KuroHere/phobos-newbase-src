// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer.srg2notch;

import org.objectweb.asm.*;
import java.util.*;
import me.earth.earthhack.impl.util.misc.collections.*;

public class MappingUtil
{
    public static String map(final String owner, final String name, final String desc, final Mapping mapping) {
        final String methodMapping = getMethodMapping(owner, name, desc, mapping);
        return (methodMapping == null) ? name : methodMapping;
    }
    
    private static String getMethodMapping(final String owner, final String name, final String desc, final Mapping mapping) {
        final List<MethodMapping> mappings = mapping.getMethods().get(name);
        if (mappings == null) {
            return null;
        }
        if (mappings.size() == 1) {
            return mappings.get(0).getName();
        }
        double bestFactor = 0.0;
        MethodMapping best = null;
        for (final MethodMapping m : mappings) {
            double matchFactor = 0.0;
            if (m.getDesc().equals(desc)) {
                ++matchFactor;
            }
            if (m.getOwner().equals(owner)) {
                ++matchFactor;
            }
            if (name.contains("_" + m.getName())) {
                matchFactor += 0.5;
            }
            if (best == null || matchFactor > bestFactor) {
                bestFactor = matchFactor;
                best = m;
            }
        }
        return (best == null) ? null : best.getName();
    }
    
    public static List<Object> map(final List<Object> objects, final Mapping mapping) {
        if (objects == null) {
            return null;
        }
        final List<Object> local = new ArrayList<Object>(objects.size());
        for (Object o : objects) {
            if (o instanceof String) {
                final String s = (String)o;
                if (s.startsWith("[")) {
                    o = mapDescription(s, mapping);
                }
                else {
                    o = mapping.getClasses().getOrDefault(s, s);
                }
            }
            local.add(o);
        }
        return local;
    }
    
    public static Handle map(final Handle h, final Mapping mapping) {
        String name = h.getName();
        String owner = h.getOwner();
        String desc = h.getDesc();
        name = getMethodMapping(owner, name, desc, mapping);
        if (name == null) {
            name = mapping.getFields().getOrDefault(h.getName(), h.getName());
        }
        owner = mapping.getClasses().getOrDefault(owner, owner);
        desc = mapDescription(desc, mapping);
        return new Handle(h.getTag(), owner, name, desc, h.isInterface());
    }
    
    public static Type map(final Type type, final Mapping mapping) {
        return Type.getType(mapDescription(type.getDescriptor(), mapping));
    }
    
    public static String[] splitField(final String field) {
        final int i = field.lastIndexOf("/");
        final String owner = field.substring(0, i);
        final String name = field.substring(i + 1);
        return new String[] { owner, name };
    }
    
    public static String[] splitMethod(final String method) {
        final String[] split = method.split("(\\()");
        final int i = split[0].lastIndexOf("/");
        final String owner = split[0].substring(0, i);
        final String name = split[0].substring(i + 1);
        final String desc = "(" + split[1];
        return new String[] { owner, name, desc };
    }
    
    public static String mapDescription(final String desc, final Mapping mapping) {
        final Set<String> classes = matchClasses(desc, ';');
        return map(desc, mapping, classes);
    }
    
    public static String mapSignature(final String signature, final Mapping mapping) {
        final Set<String> classes = matchClasses(signature, '<', ';');
        return map(signature, mapping, classes);
    }
    
    private static Set<String> matchClasses(final String s, final char... separators) {
        boolean collect = false;
        final Set<String> matched = new HashSet<String>();
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            final char c = s.charAt(i);
            if (collect && ArrayUtil.contains(c, separators)) {
                current.append(c);
                matched.add(current.toString());
                current = new StringBuilder();
                collect = false;
            }
            else if (c == 'L' && !collect) {
                collect = true;
                continue;
            }
            if (collect) {
                current.append(c);
            }
        }
        return matched;
    }
    
    private static String map(final String s, final Mapping mapping, final Set<String> classes) {
        String result = s;
        for (final String name : classes) {
            final String clazz = name.substring(0, name.length() - 1);
            final String separator = name.substring(name.length() - 1);
            final String replace = mapping.getClasses().get(clazz);
            if (replace != null) {
                result = result.replace(name, replace + separator);
            }
        }
        return result;
    }
}
