//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.holetp;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.world.*;
import net.minecraft.block.*;

public class HoleTP extends Module
{
    public static final double[] OFFSETS;
    public final Setting<Boolean> wide;
    public final Setting<Boolean> big;
    protected boolean jumped;
    protected int packets;
    
    public HoleTP() {
        super("HoleTP", Category.Movement);
        this.wide = this.register(new BooleanSetting("2x1s", true));
        this.big = this.register(new BooleanSetting("2x2s", false));
        this.listeners.add(new ListenerMotion(this));
    }
    
    public boolean isInHole() {
        final BlockPos blockPos = new BlockPos(HoleTP.mc.player.posX, HoleTP.mc.player.posY, HoleTP.mc.player.posZ);
        final IBlockState blockState = HoleTP.mc.world.getBlockState(blockPos);
        return this.isBlockValid(blockState, blockPos);
    }
    
    protected boolean isBlockValid(final IBlockState blockState, final BlockPos blockPos) {
        return blockState.getBlock() == Blocks.AIR && HoleTP.mc.player.getDistanceSq(blockPos) >= 1.0 && HoleTP.mc.world.getBlockState(blockPos.up()).getBlock() == Blocks.AIR && HoleTP.mc.world.getBlockState(blockPos.up(2)).getBlock() == Blocks.AIR && (HoleUtil.isHole(blockPos, true)[0] || (HoleUtil.is2x1(blockPos) && this.wide.getValue()) || (HoleUtil.is2x2Partial(blockPos) && this.big.getValue()));
    }
    
    protected double getNearestBlockBelow() {
        double y = HoleTP.mc.player.posY;
        while (y > 0.0) {
            if (HoleTP.mc.world.getBlockState(new BlockPos(HoleTP.mc.player.posX, y, HoleTP.mc.player.posZ)).getBlock().getDefaultState().getCollisionBoundingBox((IBlockAccess)HoleTP.mc.world, new BlockPos(0, 0, 0)) != null) {
                if (HoleTP.mc.world.getBlockState(new BlockPos(HoleTP.mc.player.posX, y, HoleTP.mc.player.posZ)).getBlock() instanceof BlockSlab) {
                    return -1.0;
                }
                return y;
            }
            else {
                y -= 0.001;
            }
        }
        return -1.0;
    }
    
    static {
        OFFSETS = new double[] { 0.42, 0.75 };
    }
}
