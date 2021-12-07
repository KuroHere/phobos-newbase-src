//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.automine.util;

import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.world.*;

public class EchestConstellation implements IConstellation
{
    private final BlockPos pos;
    
    public EchestConstellation(final BlockPos pos) {
        this.pos = pos;
    }
    
    @Override
    public boolean isAffected(final BlockPos pos, final IBlockState state) {
        return this.pos.equals((Object)pos) && state.getBlock() != Blocks.ENDER_CHEST;
    }
    
    @Override
    public boolean isValid(final IBlockAccess world, final boolean checkPlayerState) {
        return world.getBlockState(this.pos).getBlock() == Blocks.ENDER_CHEST;
    }
    
    @Override
    public boolean cantBeImproved() {
        return false;
    }
}
