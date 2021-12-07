// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer.srg2notch.remappers;

import me.earth.earthhack.installer.srg2notch.*;
import org.objectweb.asm.tree.*;
import java.util.*;

public class MethodRemapper implements Remapper
{
    @Override
    public void remap(final ClassNode cn, final Mapping mapping) {
        for (final MethodNode mn : cn.methods) {
            mn.desc = MappingUtil.mapDescription(mn.desc, mapping);
            mn.name = MappingUtil.map(null, mn.name, mn.desc, mapping);
            if (mn.signature != null) {
                mn.signature = MappingUtil.mapSignature(mn.signature, mapping);
            }
            if (mn.tryCatchBlocks != null) {
                for (final TryCatchBlockNode t : mn.tryCatchBlocks) {
                    t.type = mapping.getClasses().getOrDefault(t.type, t.type);
                }
            }
            if (mn.exceptions != null && !mn.exceptions.isEmpty()) {
                final List<String> exceptions = new ArrayList<String>(mn.exceptions.size());
                for (final String e : mn.exceptions) {
                    exceptions.add(mapping.getClasses().getOrDefault(e, e));
                }
                mn.exceptions = exceptions;
            }
            if (mn.localVariables != null) {
                for (final LocalVariableNode l : mn.localVariables) {
                    l.desc = MappingUtil.mapDescription(l.desc, mapping);
                    if (l.signature != null) {
                        l.signature = MappingUtil.mapSignature(l.signature, mapping);
                    }
                }
            }
        }
    }
}
