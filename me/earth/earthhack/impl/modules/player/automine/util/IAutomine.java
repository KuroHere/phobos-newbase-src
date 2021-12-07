// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.automine.util;

import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import java.util.concurrent.*;

public interface IAutomine
{
    boolean isValid(final IBlockState p0);
    
    void offer(final IConstellation p0);
    
    void attackPos(final BlockPos p0);
    
    void setCurrent(final BlockPos p0);
    
    BlockPos getCurrent();
    
    void setFuture(final Future<?> p0);
    
    float getMinDmg();
    
    float getMaxSelfDmg();
    
    double getBreakTrace();
    
    boolean getNewVEntities();
    
    boolean shouldMineObby();
    
    boolean isSuicide();
    
    boolean canBigCalcsBeImproved();
}
