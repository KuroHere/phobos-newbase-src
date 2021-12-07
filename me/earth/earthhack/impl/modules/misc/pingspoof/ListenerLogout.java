// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.pingspoof;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.pingbypass.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerLogout extends ModuleListener<PingSpoof, DisconnectEvent>
{
    private static final ModuleCache<PingBypass> PINGBYPASS;
    
    public ListenerLogout(final PingSpoof module) {
        super(module, (Class<? super Object>)DisconnectEvent.class);
    }
    
    public void invoke(final DisconnectEvent event) {
        if (!ListenerLogout.PINGBYPASS.isEnabled()) {
            ((PingSpoof)this.module).clearPackets(false);
        }
    }
    
    static {
        PINGBYPASS = Caches.getModule(PingBypass.class);
    }
}
