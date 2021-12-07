// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.network;

import net.minecraft.entity.*;
import me.earth.earthhack.api.event.events.*;

public class EntityChunkEvent extends StageEvent
{
    private final Entity entity;
    
    public EntityChunkEvent(final Stage stage, final Entity entity) {
        super(stage);
        this.entity = entity;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
}
