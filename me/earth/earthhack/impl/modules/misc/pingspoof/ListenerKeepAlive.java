// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.pingspoof;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.pingbypass.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerKeepAlive extends ModuleListener<PingSpoof, PacketEvent.Send<CPacketKeepAlive>>
{
    private static final ModuleCache<PingBypass> PINGBYPASS;
    
    public ListenerKeepAlive(final PingSpoof module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketKeepAlive.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketKeepAlive> event) {
        if (!ListenerKeepAlive.PINGBYPASS.isEnabled() && ((PingSpoof)this.module).keepAlive.getValue()) {
            ((PingSpoof)this.module).onPacket(event.getPacket());
            event.setCancelled(true);
        }
    }
    
    static {
        PINGBYPASS = Caches.getModule(PingBypass.class);
    }
}
