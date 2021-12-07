// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.automine.util;

import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;

public interface IConstellation
{
    default void update(final IAutomine automine) {
    }
    
    boolean isAffected(final BlockPos p0, final IBlockState p1);
    
    boolean isValid(final IBlockAccess p0, final boolean p1);
    
    default boolean cantBeImproved() {
        return true;
    }
}
