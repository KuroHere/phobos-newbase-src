// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.packetfly;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;

final class ListenerWorldClient extends ModuleListener<PacketFly, WorldClientEvent.Load>
{
    public ListenerWorldClient(final PacketFly module) {
        super(module, (Class<? super Object>)WorldClientEvent.Load.class);
    }
    
    public void invoke(final WorldClientEvent.Load event) {
        ((PacketFly)this.module).clearValues();
    }
}
