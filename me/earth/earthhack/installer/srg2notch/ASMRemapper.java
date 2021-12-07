// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer.srg2notch;

import me.earth.earthhack.installer.srg2notch.remappers.*;
import me.earth.earthhack.impl.core.util.*;
import org.objectweb.asm.tree.*;

public class ASMRemapper
{
    private final Remapper[] reMappers;
    
    public ASMRemapper() {
        (this.reMappers = new Remapper[5])[0] = new ClassRemapper();
        this.reMappers[1] = new FieldRemapper();
        this.reMappers[2] = new MethodRemapper();
        this.reMappers[3] = new InstructionRemapper();
        this.reMappers[4] = new AnnotationRemapper();
    }
    
    public byte[] transform(final byte[] clazz, final Mapping mapping) {
        ClassNode cn;
        try {
            cn = AsmUtil.read(clazz, new int[0]);
        }
        catch (IllegalArgumentException e) {
            return clazz;
        }
        for (final Remapper remapper : this.reMappers) {
            remapper.remap(cn, mapping);
        }
        return AsmUtil.write(cn, new int[0]);
    }
}
