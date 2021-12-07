// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.portals;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;

final class ListenerTeleport extends ModuleListener<Portals, PacketEvent.Send<CPacketConfirmTeleport>>
{
    public ListenerTeleport(final Portals module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketConfirmTeleport.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketConfirmTeleport> event) {
        if (((Portals)this.module).godMode.getValue()) {
            event.setCancelled(true);
        }
    }
}
