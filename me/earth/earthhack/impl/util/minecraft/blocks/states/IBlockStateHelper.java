//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft.blocks.states;

import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;

public interface IBlockStateHelper extends IBlockAccess
{
    default void addAir(final BlockPos pos) {
        this.addBlockState(pos, Blocks.AIR.getDefaultState());
    }
    
    void addBlockState(final BlockPos p0, final IBlockState p1);
    
    void delete(final BlockPos p0);
    
    void clearAllStates();
}
