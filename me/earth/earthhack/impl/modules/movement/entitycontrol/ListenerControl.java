// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.entitycontrol;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;

final class ListenerControl extends ModuleListener<EntityControl, ControlEvent>
{
    public ListenerControl(final EntityControl module) {
        super(module, (Class<? super Object>)ControlEvent.class);
    }
    
    public void invoke(final ControlEvent event) {
        if (((EntityControl)this.module).control.getValue()) {
            event.setCancelled(true);
        }
    }
}
