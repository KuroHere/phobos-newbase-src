// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer.srg2notch.remappers;

import org.objectweb.asm.tree.*;
import me.earth.earthhack.installer.srg2notch.*;
import java.util.*;

public class ClassRemapper implements Remapper
{
    @Override
    public void remap(final ClassNode cn, final Mapping mapping) {
        if (cn.superName != null) {
            cn.superName = mapping.getClasses().getOrDefault(cn.superName, cn.superName);
        }
        if (cn.interfaces != null && !cn.interfaces.isEmpty()) {
            final List<String> interfaces = new ArrayList<String>(cn.interfaces.size());
            for (final String i : cn.interfaces) {
                interfaces.add(mapping.getClasses().getOrDefault(i, i));
            }
            cn.interfaces = interfaces;
        }
        if (cn.signature != null) {
            cn.signature = MappingUtil.mapSignature(cn.signature, mapping);
        }
    }
}
