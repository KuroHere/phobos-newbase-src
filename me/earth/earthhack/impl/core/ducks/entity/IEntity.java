// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.ducks.entity;

import me.earth.earthhack.impl.commands.packet.util.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.*;

public interface IEntity extends Dummy
{
    boolean inWeb();
    
    EntityType getType();
    
    long getDeathTime();
    
    boolean isPseudoDead();
    
    void setPseudoDead(final boolean p0);
    
    StopWatch getPseudoTime();
    
    long getTimeStamp();
    
    default boolean isDummy() {
        return false;
    }
    
    void setDummy(final boolean p0);
}
