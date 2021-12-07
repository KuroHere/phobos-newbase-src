// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.logoutspots;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;

final class ListenerDisconnect extends ModuleListener<LogoutSpots, DisconnectEvent>
{
    public ListenerDisconnect(final LogoutSpots module) {
        super(module, (Class<? super Object>)DisconnectEvent.class);
    }
    
    public void invoke(final DisconnectEvent event) {
        ((LogoutSpots)this.module).spots.clear();
    }
}
