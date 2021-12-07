// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.logger;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;

final class ListenerReceive extends ModuleListener<Logger, PacketEvent.Receive<?>>
{
    public ListenerReceive(final Logger module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, Integer.MIN_VALUE);
    }
    
    public void invoke(final PacketEvent.Receive<?> event) {
        if (((Logger)this.module).incoming.getValue()) {
            ((Logger)this.module).logPacket(event.getPacket(), "Receiving ", event.isCancelled());
        }
    }
}
