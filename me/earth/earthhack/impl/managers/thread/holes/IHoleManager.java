// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.thread.holes;

import java.util.*;
import net.minecraft.util.math.*;

public interface IHoleManager
{
    void setSafe(final List<BlockPos> p0);
    
    void setUnsafe(final List<BlockPos> p0);
    
    void setLongHoles(final List<BlockPos> p0);
    
    void setBigHoles(final List<BlockPos> p0);
    
    void setFinished();
}
