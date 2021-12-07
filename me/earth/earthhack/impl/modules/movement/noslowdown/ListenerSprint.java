// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.noslowdown;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;

final class ListenerSprint extends ModuleListener<NoSlowDown, SprintEvent>
{
    public ListenerSprint(final NoSlowDown module) {
        super(module, (Class<? super Object>)SprintEvent.class);
    }
    
    public void invoke(final SprintEvent event) {
        if (((NoSlowDown)this.module).sprint.getValue() && ((NoSlowDown)this.module).items.getValue()) {
            event.setCancelled(true);
        }
    }
}
