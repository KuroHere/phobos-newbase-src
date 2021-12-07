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

final class ListenerResources extends ModuleListener<PingSpoof, PacketEvent.Send<CPacketResourcePackStatus>>
{
    private static final ModuleCache<PingBypass> PINGBYPASS;
    
    public ListenerResources(final PingSpoof module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketResourcePackStatus.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketResourcePackStatus> event) {
        if (!ListenerResources.PINGBYPASS.isEnabled() && ((PingSpoof)this.module).resources.getValue()) {
            ((PingSpoof)this.module).onPacket(event.getPacket());
            event.setCancelled(true);
        }
    }
    
    static {
        PINGBYPASS = Caches.getModule(PingBypass.class);
    }
}
