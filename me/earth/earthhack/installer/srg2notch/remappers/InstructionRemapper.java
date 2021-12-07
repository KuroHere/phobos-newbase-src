// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer.srg2notch.remappers;

import me.earth.earthhack.installer.srg2notch.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import java.util.*;

public class InstructionRemapper implements Remapper
{
    @Override
    public void remap(final ClassNode cn, final Mapping mapping) {
        for (final MethodNode mn : cn.methods) {
            for (int i = 0; i < mn.instructions.size(); ++i) {
                final AbstractInsnNode insn = mn.instructions.get(i);
                this.remapInsn(insn, mapping);
            }
        }
    }
    
    private void remapInsn(final AbstractInsnNode insn, final Mapping mapping) {
        if (insn instanceof MethodInsnNode) {
            final MethodInsnNode min = (MethodInsnNode)insn;
            min.owner = mapping.getClasses().getOrDefault(min.owner, min.owner);
            min.desc = MappingUtil.mapDescription(min.desc, mapping);
            min.name = MappingUtil.map(min.owner, min.name, min.desc, mapping);
        }
        else if (insn instanceof FieldInsnNode) {
            final FieldInsnNode f = (FieldInsnNode)insn;
            f.owner = mapping.getClasses().getOrDefault(f.owner, f.owner);
            f.desc = MappingUtil.mapDescription(f.desc, mapping);
            if (f.getOpcode() == 178 && !f.name.startsWith("field")) {
                final String constant = f.owner + "/" + f.name;
                f.name = mapping.getConstants().getOrDefault(constant, f.name);
            }
            else {
                f.name = mapping.getFields().getOrDefault(f.name, f.name);
            }
        }
        else if (insn instanceof TypeInsnNode) {
            final TypeInsnNode t = (TypeInsnNode)insn;
            if (t.desc.contains(";")) {
                t.desc = MappingUtil.mapDescription(t.desc, mapping);
            }
            else {
                t.desc = mapping.getClasses().getOrDefault(t.desc, t.desc);
            }
        }
        else if (insn instanceof MultiANewArrayInsnNode) {
            final MultiANewArrayInsnNode mainsn = (MultiANewArrayInsnNode)insn;
            mainsn.desc = MappingUtil.mapDescription(mainsn.desc, mapping);
        }
        else if (insn instanceof InvokeDynamicInsnNode) {
            final InvokeDynamicInsnNode dyn = (InvokeDynamicInsnNode)insn;
            dyn.desc = MappingUtil.mapDescription(dyn.desc, mapping);
            dyn.name = MappingUtil.map(null, dyn.name, dyn.desc, mapping);
            dyn.bsm = MappingUtil.map(dyn.bsm, mapping);
            if (dyn.bsmArgs != null) {
                final Object[] args = new Object[dyn.bsmArgs.length];
                for (int i = 0; i < dyn.bsmArgs.length; ++i) {
                    Object arg = dyn.bsmArgs[i];
                    if (arg == null) {
                        args[i] = null;
                    }
                    else {
                        if (arg instanceof Type) {
                            arg = MappingUtil.map((Type)arg, mapping);
                        }
                        else {
                            if (!(arg instanceof Handle)) {
                                throw new ClassCastException("InvokeDynamic Arg " + arg.getClass().getName() + " : " + arg + " was not a Handle or a Type!");
                            }
                            arg = MappingUtil.map((Handle)arg, mapping);
                        }
                        args[i] = arg;
                    }
                }
                dyn.bsmArgs = args;
            }
        }
        else if (insn instanceof LdcInsnNode) {
            final LdcInsnNode ldc = (LdcInsnNode)insn;
            if (ldc.cst instanceof Type) {
                ldc.cst = MappingUtil.map((Type)ldc.cst, mapping);
            }
        }
        else if (insn instanceof FrameNode) {
            final FrameNode frame = (FrameNode)insn;
            frame.local = MappingUtil.map(frame.local, mapping);
            frame.stack = MappingUtil.map(frame.stack, mapping);
        }
    }
}
