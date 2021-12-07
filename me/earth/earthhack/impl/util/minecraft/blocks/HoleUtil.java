//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft.blocks;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import java.util.*;

public class HoleUtil implements Globals
{
    private static final Vec3i[] OFFSETS_2x2;
    private static final Block[] NO_BLAST;
    
    public static boolean[] isHole(final BlockPos pos, final boolean above) {
        final boolean[] result = { false, true };
        if (!BlockUtil.isAir(pos) || !BlockUtil.isAir(pos.up()) || (above && !BlockUtil.isAir(pos.up(2)))) {
            return result;
        }
        return is1x1(pos, result);
    }
    
    public static boolean[] is1x1(final BlockPos pos) {
        return is1x1(pos, new boolean[] { false, true });
    }
    
    public static boolean[] is1x1(final BlockPos pos, final boolean[] result) {
        for (final EnumFacing facing : EnumFacing.values()) {
            if (facing != EnumFacing.UP) {
                final BlockPos offset = pos.offset(facing);
                final IBlockState state = HoleUtil.mc.world.getBlockState(offset);
                if (state.getBlock() != Blocks.BEDROCK) {
                    if (Arrays.stream(HoleUtil.NO_BLAST).noneMatch(b -> b == state.getBlock())) {
                        return result;
                    }
                    result[1] = false;
                }
            }
        }
        result[0] = true;
        return result;
    }
    
    public static boolean is2x1(final BlockPos pos) {
        return is2x1(pos, true);
    }
    
    public static boolean is2x1(final BlockPos pos, final boolean upper) {
        if (upper && (!BlockUtil.isAir(pos) || !BlockUtil.isAir(pos.up()) || BlockUtil.isAir(pos.down()))) {
            return false;
        }
        int airBlocks = 0;
        for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
            final BlockPos offset = pos.offset(facing);
            if (BlockUtil.isAir(offset)) {
                if (!BlockUtil.isAir(offset.up())) {
                    return false;
                }
                if (BlockUtil.isAir(offset.down())) {
                    return false;
                }
                for (final EnumFacing offsetFacing : EnumFacing.HORIZONTALS) {
                    if (offsetFacing != facing.getOpposite()) {
                        final IBlockState state = HoleUtil.mc.world.getBlockState(offset.offset(offsetFacing));
                        if (Arrays.stream(HoleUtil.NO_BLAST).noneMatch(b -> b == state.getBlock())) {
                            return false;
                        }
                    }
                }
                ++airBlocks;
            }
            if (airBlocks > 1) {
                return false;
            }
        }
        return airBlocks == 1;
    }
    
    public static boolean is2x2Partial(final BlockPos pos) {
        final Set<BlockPos> positions = new HashSet<BlockPos>();
        for (final Vec3i vec : HoleUtil.OFFSETS_2x2) {
            positions.add(pos.add(vec));
        }
        boolean airBlock = false;
        for (final BlockPos holePos : positions) {
            if (!BlockUtil.isAir(holePos) || !BlockUtil.isAir(holePos.up()) || BlockUtil.isAir(holePos.down())) {
                return false;
            }
            if (BlockUtil.isAir(holePos.up(2))) {
                airBlock = true;
            }
            for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
                final BlockPos offset = holePos.offset(facing);
                if (!positions.contains(offset)) {
                    final IBlockState state = HoleUtil.mc.world.getBlockState(offset);
                    if (Arrays.stream(HoleUtil.NO_BLAST).noneMatch(b -> b == state.getBlock())) {
                        return false;
                    }
                }
            }
        }
        return airBlock;
    }
    
    public static boolean is2x2(final BlockPos pos) {
        return is2x2(pos, true);
    }
    
    public static boolean is2x2(final BlockPos pos, final boolean upper) {
        if (upper && !BlockUtil.isAir(pos)) {
            return false;
        }
        if (is2x2Partial(pos)) {
            return true;
        }
        final BlockPos l = pos.add(-1, 0, 0);
        final boolean airL = BlockUtil.isAir(l);
        if (airL && is2x2Partial(l)) {
            return true;
        }
        final BlockPos r = pos.add(0, 0, -1);
        final boolean airR = BlockUtil.isAir(r);
        return (airR && is2x2Partial(r)) || ((airL || airR) && is2x2Partial(pos.add(-1, 0, -1)));
    }
    
    static {
        OFFSETS_2x2 = new Vec3i[] { new Vec3i(0, 0, 0), new Vec3i(1, 0, 0), new Vec3i(0, 0, 1), new Vec3i(1, 0, 1) };
        NO_BLAST = new Block[] { Blocks.BEDROCK, Blocks.OBSIDIAN, Blocks.ANVIL, Blocks.ENDER_CHEST };
    }
}
