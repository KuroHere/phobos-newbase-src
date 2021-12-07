// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.velocity;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;

final class ListenerBlockPush extends ModuleListener<Velocity, BlockPushEvent>
{
    public ListenerBlockPush(final Velocity module) {
        super(module, (Class<? super Object>)BlockPushEvent.class, 1000);
    }
    
    public void invoke(final BlockPushEvent event) {
        if (((Velocity)this.module).blocks.getValue()) {
            event.setCancelled(true);
        }
    }
}
