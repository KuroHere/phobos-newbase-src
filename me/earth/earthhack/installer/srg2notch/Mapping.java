// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer.srg2notch;

import java.util.*;
import java.io.*;

public class Mapping
{
    private final Map<String, String> classes;
    private final Map<String, String> fields;
    private final Map<String, List<MethodMapping>> methods;
    private final Map<String, String> constants;
    
    public Mapping(final Map<String, String> classes, final Map<String, String> fields, final Map<String, List<MethodMapping>> methods, final Map<String, String> constants) {
        this.classes = classes;
        this.fields = fields;
        this.methods = methods;
        this.constants = constants;
    }
    
    public Map<String, String> getClasses() {
        return this.classes;
    }
    
    public Map<String, String> getFields() {
        return this.fields;
    }
    
    public Map<String, List<MethodMapping>> getMethods() {
        return this.methods;
    }
    
    public Map<String, String> getConstants() {
        return this.constants;
    }
    
    public static Mapping fromResource(final String name) throws IOException {
        final Map<String, List<MethodMapping>> methods = new HashMap<String, List<MethodMapping>>();
        final Map<String, String> classes = new HashMap<String, String>();
        final Map<String, String> fields = new HashMap<String, String>();
        final Map<String, String> constants = new HashMap<String, String>();
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Mapping.class.getClassLoader().getResourceAsStream(name))))) {
            String line;
            while ((line = br.readLine()) != null) {
                final String[] mapping = line.split(",");
                final String s = mapping[0];
                switch (s) {
                    case "class": {
                        classes.put(mapping[2], mapping[1]);
                        continue;
                    }
                    case "field": {
                        if (!mapping[2].startsWith("field")) {
                            final String[] notch = MappingUtil.splitField(mapping[1]);
                            final String ownerConstant = notch[0] + "/" + mapping[2];
                            constants.put(ownerConstant, notch[1]);
                            continue;
                        }
                        final String fn = MappingUtil.splitField(mapping[1])[1];
                        fields.put(mapping[2], fn);
                        continue;
                    }
                    case "func": {
                        final String[] mnn = MappingUtil.splitMethod(mapping[1]);
                        final String[] mns = MappingUtil.splitMethod(mapping[2]);
                        if (!mns[1].startsWith("func")) {
                            continue;
                        }
                        methods.computeIfAbsent(mns[1], v -> new ArrayList(1)).add(new MethodMapping(mnn[0], mnn[1], mnn[2]));
                        continue;
                    }
                }
            }
        }
        return new Mapping(classes, fields, methods, constants);
    }
}
