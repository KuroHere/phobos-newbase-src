// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.ducks.block;

import net.minecraft.block.state.*;

public interface IBlock
{
    void setHarvestLevelNonForge(final String p0, final int p1);
    
    String getHarvestToolNonForge(final IBlockState p0);
    
    int getHarvestLevelNonForge(final IBlockState p0);
}
