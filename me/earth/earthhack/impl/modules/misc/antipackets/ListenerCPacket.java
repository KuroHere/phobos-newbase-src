// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.antipackets;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;

final class ListenerCPacket extends ModuleListener<AntiPackets, PacketEvent.Send<?>>
{
    public ListenerCPacket(final AntiPackets module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class);
    }
    
    public void invoke(final PacketEvent.Send<?> event) {
        ((AntiPackets)this.module).onPacket(event, false);
    }
}
