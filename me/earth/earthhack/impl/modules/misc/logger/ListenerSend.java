// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.logger;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.modules.misc.logger.util.*;

final class ListenerSend extends ModuleListener<Logger, PacketEvent.Send<?>>
{
    public ListenerSend(final Logger module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, Integer.MIN_VALUE);
    }
    
    public void invoke(final PacketEvent.Send<?> event) {
        if (((Logger)this.module).outgoing.getValue() && ((Logger)this.module).mode.getValue() == LoggerMode.Normal) {
            ((Logger)this.module).logPacket(event.getPacket(), "Sending ", event.isCancelled());
        }
    }
}
