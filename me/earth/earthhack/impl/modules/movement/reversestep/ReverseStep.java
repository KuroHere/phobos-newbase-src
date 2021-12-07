//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.reversestep;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.block.*;

public class ReverseStep extends Module
{
    protected boolean jumped;
    protected boolean waitForOnGround;
    protected int packets;
    
    public ReverseStep() {
        super("ReverseStep", Category.Movement);
        this.listeners.add(new ListenerMotion(this));
    }
    
    protected double getNearestBlockBelow() {
        double y = ReverseStep.mc.player.posY;
        while (y > 0.0) {
            if (ReverseStep.mc.world.getBlockState(new BlockPos(ReverseStep.mc.player.posX, y, ReverseStep.mc.player.posZ)).getBlock().getDefaultState().getCollisionBoundingBox((IBlockAccess)ReverseStep.mc.world, new BlockPos(0, 0, 0)) != null) {
                if (ReverseStep.mc.world.getBlockState(new BlockPos(ReverseStep.mc.player.posX, y, ReverseStep.mc.player.posZ)).getBlock() instanceof BlockSlab) {
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
}
