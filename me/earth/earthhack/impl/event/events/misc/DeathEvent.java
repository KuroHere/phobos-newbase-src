// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.misc;

import net.minecraft.entity.*;

public class DeathEvent
{
    private final EntityLivingBase entity;
    
    public DeathEvent(final EntityLivingBase entity) {
        this.entity = entity;
    }
    
    public EntityLivingBase getEntity() {
        return this.entity;
    }
}
