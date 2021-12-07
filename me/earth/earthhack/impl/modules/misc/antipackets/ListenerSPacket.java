// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.antipackets;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;

final class ListenerSPacket extends ModuleListener<AntiPackets, PacketEvent.Receive<?>>
{
    public ListenerSPacket(final AntiPackets module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class);
    }
    
    public void invoke(final PacketEvent.Receive<?> event) {
        ((AntiPackets)this.module).onPacket(event, true);
    }
}
