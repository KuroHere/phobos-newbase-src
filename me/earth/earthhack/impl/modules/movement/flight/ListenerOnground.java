// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.flight;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;
import me.earth.earthhack.impl.modules.movement.flight.mode.*;

final class ListenerOnground extends ModuleListener<Flight, OnGroundEvent>
{
    public ListenerOnground(final Flight module) {
        super(module, (Class<? super Object>)OnGroundEvent.class);
    }
    
    public void invoke(final OnGroundEvent event) {
        if (((Flight)this.module).animation.getValue() && ((Flight)this.module).mode.getValue() == FlightMode.Normal) {
            event.setCancelled(true);
        }
    }
}
