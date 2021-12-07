// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.mcp;

import me.earth.earthhack.impl.util.misc.*;
import me.earth.earthhack.vanilla.*;
import java.util.*;
import java.io.*;

public class MappingProvider
{
    private static final Map<String, String> CLASS_MAPPINGS;
    private static final Map<String, String> FIELD_MAPPINGS;
    private static final boolean VANILLA;
    
    public static String field(final Class<?> clazz, final String fieldName) {
        if (MappingProvider.VANILLA) {
            return MappingProvider.FIELD_MAPPINGS.get(clazz.getName() + "." + fieldName);
        }
        return MappingProvider.FIELD_MAPPINGS.get(fieldName);
    }
    
    public static String simpleName(final Class<?> clazz) {
        if (!MappingProvider.VANILLA) {
            return clazz.getSimpleName();
        }
        final String name = className(clazz);
        if (name == null) {
            return clazz.getSimpleName();
        }
        return ReflectionUtil.getSimpleName(name);
    }
    
    public static String className(final Class<?> clazz) {
        if (MappingProvider.VANILLA) {
            return MappingProvider.CLASS_MAPPINGS.get(clazz.getName());
        }
        return clazz.getName();
    }
    
    static {
        VANILLA = (Environment.getEnvironment() == Environment.VANILLA);
        final int vs = 4458;
        CLASS_MAPPINGS = (MappingProvider.VANILLA ? new HashMap<String, String>(vs) : Collections.emptyMap());
        final int fs = 15886;
        FIELD_MAPPINGS = new HashMap<String, String>(fs);
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(MappingProvider.class.getClassLoader().getResourceAsStream("mappings/mappings.csv"))))) {
            String line;
            while ((line = br.readLine()) != null) {
                final String[] mapping = line.replace("/", ".").split(",");
                if (mapping.length <= 0) {
                    continue;
                }
                if (MappingProvider.VANILLA && mapping[0].equals("class")) {
                    MappingProvider.CLASS_MAPPINGS.put(mapping[1], mapping[2]);
                }
                else {
                    if (!mapping[0].equals("field")) {
                        continue;
                    }
                    if (MappingProvider.VANILLA) {
                        MappingProvider.FIELD_MAPPINGS.put(mapping[1], mapping[3]);
                    }
                    else {
                        MappingProvider.FIELD_MAPPINGS.put(mapping[2], mapping[3]);
                    }
                }
            }
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
