//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.transfomer.patch.patches;

import me.earth.earthhack.impl.core.transfomer.patch.*;
import me.earth.earthhack.impl.core.util.*;
import me.earth.earthhack.impl.core.*;
import org.objectweb.asm.tree.*;

public class Vec3iPatch extends ArgumentPatch
{
    public Vec3iPatch() {
        super("fq", "net.minecraft.util.math.Vec3i", "leijurvpos");
    }
    
    @Override
    protected void applyPatch(final ClassNode cn) {
        final MethodNode x = AsmUtil.findMappedMethod(cn, "p", "()I", "getX", "getX", "()I");
        final MethodNode y = AsmUtil.findMappedMethod(cn, "q", "()I", "getY", "getY", "()I");
        final MethodNode z = AsmUtil.findMappedMethod(cn, "r", "()I", "getZ", "getZ", "()I");
        final MethodNode hashCode = AsmUtil.findMethod(cn, "hashCode", "()I");
        if (x == null || y == null || z == null || hashCode == null) {
            Core.LOGGER.error("Vec3i is missing one of: " + x + ", " + y + ", " + z + ", " + hashCode);
            return;
        }
        hashCode.visitCode();
        hashCode.visitLdcInsn((Object)11206370049L);
        hashCode.visitVarInsn(25, 0);
        hashCode.visitMethodInsn(182, cn.name, x.name, x.desc, false);
        hashCode.visitInsn(133);
        hashCode.visitInsn(97);
        hashCode.visitLdcInsn((Object)8734625L);
        hashCode.visitInsn(105);
        hashCode.visitVarInsn(25, 0);
        hashCode.visitMethodInsn(182, cn.name, y.name, y.desc, false);
        hashCode.visitInsn(133);
        hashCode.visitInsn(97);
        hashCode.visitLdcInsn((Object)2873465L);
        hashCode.visitInsn(105);
        hashCode.visitVarInsn(25, 0);
        hashCode.visitMethodInsn(182, cn.name, z.name, z.desc, false);
        hashCode.visitInsn(133);
        hashCode.visitInsn(97);
        hashCode.visitInsn(136);
        hashCode.visitInsn(172);
        hashCode.visitMaxs(4, 1);
        hashCode.visitEnd();
    }
}
