// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.math.path;

import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.raytrace.*;
import java.util.*;

public interface Pathable
{
    BlockPos getPos();
    
    Entity getFrom();
    
    Ray[] getPath();
    
    void setPath(final Ray... p0);
    
    int getMaxLength();
    
    boolean isValid();
    
    void setValid(final boolean p0);
    
    List<BlockingEntity> getBlockingEntities();
}
