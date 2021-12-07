// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.freecam;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;

final class ListenerOverlay extends ModuleListener<Freecam, SuffocationEvent>
{
    public ListenerOverlay(final Freecam module) {
        super(module, (Class<? super Object>)SuffocationEvent.class);
    }
    
    public void invoke(final SuffocationEvent event) {
        event.setCancelled(true);
    }
}
