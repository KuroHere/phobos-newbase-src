// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.entitycontrol;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;

final class ListenerAI extends ModuleListener<EntityControl, AIEvent>
{
    public ListenerAI(final EntityControl module) {
        super(module, (Class<? super Object>)AIEvent.class);
    }
    
    public void invoke(final AIEvent event) {
        if (((EntityControl)this.module).noAI.getValue()) {
            event.setCancelled(true);
        }
    }
}
