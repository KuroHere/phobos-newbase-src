// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.movement;

import me.earth.earthhack.api.event.events.*;
import net.minecraft.entity.*;

public class WaterPushEvent extends Event
{
    private final Entity entity;
    
    public WaterPushEvent(final Entity entity) {
        this.entity = entity;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
}
