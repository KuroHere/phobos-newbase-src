// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.phase;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;

final class ListenerSuffocation extends ModuleListener<Phase, SuffocationEvent>
{
    public ListenerSuffocation(final Phase module) {
        super(module, (Class<? super Object>)SuffocationEvent.class);
    }
    
    public void invoke(final SuffocationEvent event) {
        event.setCancelled(true);
    }
}
