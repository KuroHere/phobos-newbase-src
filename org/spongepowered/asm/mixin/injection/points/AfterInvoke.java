// 
// Decompiled by Procyon v0.6-prerelease
// 

package org.spongepowered.asm.mixin.injection.points;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import java.util.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;

@AtCode("INVOKE_ASSIGN")
public class AfterInvoke extends BeforeInvoke
{
    public AfterInvoke(final InjectionPointData data) {
        super(data);
    }
    
    @Override
    protected boolean addInsn(final InsnList insns, final Collection<AbstractInsnNode> nodes, AbstractInsnNode insn) {
        final MethodInsnNode methodNode = (MethodInsnNode)insn;
        if (Type.getReturnType(methodNode.desc) == Type.VOID_TYPE) {
            return false;
        }
        insn = InjectionPoint.nextNode(insns, insn);
        if (insn instanceof VarInsnNode && insn.getOpcode() >= 54) {
            insn = InjectionPoint.nextNode(insns, insn);
        }
        nodes.add(insn);
        return true;
    }
}