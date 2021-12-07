//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.transfomer.patch.patches;

import me.earth.earthhack.impl.core.transfomer.patch.*;
import me.earth.earthhack.impl.core.util.*;
import me.earth.earthhack.impl.core.*;
import java.util.*;
import org.objectweb.asm.tree.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.misc.*;
import org.objectweb.asm.*;
import java.lang.reflect.*;

public class BlockPosPatch extends ArgumentPatch
{
    public BlockPosPatch() {
        super("et", "net.minecraft.util.math.BlockPos", "leijurvpos");
    }
    
    @Override
    protected void applyPatch(final ClassNode node) {
        final OffsetPatch[] array;
        final OffsetPatch[] patches = array = new OffsetPatch[] { new OffsetPatch(Direction.UP, "b", "(I)Let;", "up", "up", true), new OffsetPatch(Direction.UP, "a", "()Let;", "up", "up", false), new OffsetPatch(Direction.DOWN, "c", "(I)Let;", "down", "down", true), new OffsetPatch(Direction.DOWN, "b", "()Let;", "down", "down", false), new OffsetPatch(Direction.NORTH, "d", "(I)Let;", "north", "north", true), new OffsetPatch(Direction.NORTH, "c", "()Let;", "north", "north", false), new OffsetPatch(Direction.SOUTH, "e", "(I)Let;", "south", "south", true), new OffsetPatch(Direction.SOUTH, "d", "()Let;", "south", "south", false), new OffsetPatch(Direction.EAST, "g", "(I)Let;", "east", "east", true), new OffsetPatch(Direction.EAST, "f", "()Let;", "east", "east", false), new OffsetPatch(Direction.WEST, "f", "(I)Let;", "west", "west", true), new OffsetPatch(Direction.WEST, "e", "()Let;", "west", "west", false) };
        for (final OffsetPatch patch : array) {
            this.patch(node, patch);
        }
        this.patchOffset(node);
    }
    
    private void patchOffset(final ClassNode node) {
        final MethodNode offset = AsmUtil.findMappedMethod(node, "a", "(Lfa;)Let;", "offset", "offset", "(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;");
        final MethodNode offsetI = AsmUtil.findMappedMethod(node, "a", "(Lfa;I)Let;", "offset", "offset", "(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/math/BlockPos;");
        if (offset == null || offsetI == null) {
            Core.LOGGER.error("Couldn't find " + offset + " or " + offsetI + "!");
            return;
        }
        offset.instructions.clear();
        boolean newFound = false;
        for (int i = 0; i < offsetI.instructions.size(); ++i) {
            final AbstractInsnNode insn = offsetI.instructions.get(i);
            if (insn instanceof TypeInsnNode && insn.getOpcode() == 187 && ((TypeInsnNode)insn).desc.equals(node.name)) {
                newFound = true;
            }
            else {
                if (!newFound || insn instanceof FrameNode || insn instanceof LabelNode || (insn instanceof VarInsnNode && ((VarInsnNode)insn).var == 2)) {
                    continue;
                }
                if (insn.getOpcode() == 104) {
                    continue;
                }
            }
            offset.instructions.add(insn.clone((Map)Collections.emptyMap()));
        }
    }
    
    private void patch(final ClassNode cn, final OffsetPatch op) {
        final MethodNode mn = AsmUtil.findMappedMethod(cn, op.notch, op.notchDesc, op.searge, op.mcp, op.srgMcpDesc);
        final Method x = ReflectionUtil.getMethod(Vec3i.class, "p", "getX", "getX", (Class<?>[])new Class[0]);
        final Method y = ReflectionUtil.getMethod(Vec3i.class, "q", "getY", "getY", (Class<?>[])new Class[0]);
        final Method z = ReflectionUtil.getMethod(Vec3i.class, "r", "getZ", "getZ", (Class<?>[])new Class[0]);
        if (mn == null) {
            Core.LOGGER.error("Couldn't find " + op + " in BlockPos!");
            return;
        }
        mn.instructions.clear();
        mn.visitCode();
        if (op.notchDesc.startsWith("(I)")) {
            final Label L1 = new Label();
            mn.visitVarInsn(21, 1);
            mn.visitJumpInsn(154, L1);
            mn.visitVarInsn(25, 0);
            mn.visitInsn(176);
            mn.visitLabel(L1);
        }
        mn.visitTypeInsn(187, cn.name);
        mn.visitInsn(89);
        mn.visitVarInsn(25, 0);
        mn.visitMethodInsn(182, cn.name, x.getName(), "()I", false);
        switch (op.direction) {
            case UP:
            case DOWN: {
                mn.visitVarInsn(25, 0);
                mn.visitMethodInsn(182, cn.name, y.getName(), "()I", false);
                this.addOrSub(mn, op);
                mn.visitVarInsn(25, 0);
                mn.visitMethodInsn(182, cn.name, z.getName(), "()I", false);
                break;
            }
            case NORTH:
            case SOUTH: {
                mn.visitVarInsn(25, 0);
                mn.visitMethodInsn(182, cn.name, y.getName(), "()I", false);
                mn.visitVarInsn(25, 0);
                mn.visitMethodInsn(182, cn.name, z.getName(), "()I", false);
                this.addOrSub(mn, op);
                break;
            }
            case EAST:
            case WEST: {
                this.addOrSub(mn, op);
                mn.visitVarInsn(25, 0);
                mn.visitMethodInsn(182, cn.name, y.getName(), "()I", false);
                mn.visitVarInsn(25, 0);
                mn.visitMethodInsn(182, cn.name, z.getName(), "()I", false);
                break;
            }
        }
        mn.visitMethodInsn(183, cn.name, "<init>", "(III)V", false);
        mn.visitInsn(176);
        mn.visitMaxs(0, 0);
        mn.visitEnd();
    }
    
    private void addOrSub(final MethodNode mn, final OffsetPatch op) {
        if (op.notchDesc.startsWith("(I)")) {
            mn.visitVarInsn(21, 1);
        }
        else {
            mn.visitInsn(4);
        }
        mn.visitInsn((op.direction.offset > 0) ? 96 : 100);
    }
    
    private static final class OffsetPatch
    {
        public final Direction direction;
        public final String notch;
        public final String notchDesc;
        public final String searge;
        public final String mcp;
        public final String srgMcpDesc;
        
        public OffsetPatch(final Direction direction, final String notch, final String notchDesc, final String searge, final String mcp, final boolean takesInt) {
            this.direction = direction;
            this.notch = notch;
            this.notchDesc = notchDesc;
            this.searge = searge;
            this.mcp = mcp;
            this.srgMcpDesc = (takesInt ? "(I)Lnet/minecraft/util/math/BlockPos;" : "()Lnet/minecraft/util/math/BlockPos;");
        }
        
        @Override
        public String toString() {
            return this.notch + this.notchDesc + " -> " + this.mcp + this.srgMcpDesc + " (" + this.searge + ")";
        }
    }
    
    private enum Direction
    {
        UP(1), 
        DOWN(-1), 
        NORTH(-1), 
        SOUTH(1), 
        WEST(-1), 
        EAST(1);
        
        public final int offset;
        
        private Direction(final int offset) {
            this.offset = offset;
        }
    }
}
