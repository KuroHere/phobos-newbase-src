// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.login.client.*;

final class ListenerLogin extends ModuleListener<PingBypass, PacketEvent.Send<CPacketLoginStart>>
{
    public ListenerLogin(final PingBypass module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketLoginStart.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketLoginStart> event) {
        ((PingBypass)this.module).friendSerializer.clear();
        ((PingBypass)this.module).serializer.clear();
    }
}
