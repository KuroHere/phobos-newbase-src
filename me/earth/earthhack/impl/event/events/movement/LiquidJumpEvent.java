// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.movement;

import me.earth.earthhack.api.event.events.*;
import net.minecraft.entity.*;

public class LiquidJumpEvent extends Event
{
    private final EntityLivingBase entity;
    
    public LiquidJumpEvent(final EntityLivingBase entity) {
        this.entity = entity;
    }
    
    public EntityLivingBase getEntity() {
        return this.entity;
    }
}
