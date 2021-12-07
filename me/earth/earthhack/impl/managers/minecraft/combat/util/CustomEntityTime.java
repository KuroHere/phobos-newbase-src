// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft.combat.util;

import net.minecraft.entity.*;

public class CustomEntityTime extends EntityTime
{
    private final long customTime;
    
    public CustomEntityTime(final Entity entity, final long customTime) {
        super(entity);
        this.customTime = customTime;
    }
    
    @Override
    public boolean passed(final long ms) {
        return System.currentTimeMillis() - this.time > this.customTime;
    }
}
