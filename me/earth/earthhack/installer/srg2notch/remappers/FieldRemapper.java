// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer.srg2notch.remappers;

import me.earth.earthhack.installer.srg2notch.*;
import java.util.*;
import org.objectweb.asm.tree.*;

public class FieldRemapper implements Remapper
{
    @Override
    public void remap(final ClassNode cn, final Mapping mapping) {
        for (final FieldNode fn : cn.fields) {
            fn.desc = MappingUtil.mapDescription(fn.desc, mapping);
            if (fn.signature != null) {
                fn.signature = MappingUtil.mapSignature(fn.signature, mapping);
            }
            if (this.hasShadowAnnotation(fn)) {
                fn.name = mapping.getFields().getOrDefault(fn.name, fn.name);
            }
        }
    }
    
    private boolean hasShadowAnnotation(final FieldNode fn) {
        if (fn.visibleAnnotations != null) {
            for (final AnnotationNode an : fn.visibleAnnotations) {
                if (an != null && "Lorg/spongepowered/asm/mixin/Shadow;".equals(an.desc)) {
                    return true;
                }
            }
        }
        if (fn.invisibleAnnotations != null) {
            for (final AnnotationNode an : fn.invisibleAnnotations) {
                if (an != null && "Lme/earth/earthhack/installer/srg2notch/RemapFieldName;".equals(an.desc)) {
                    return true;
                }
            }
        }
        return false;
    }
}
