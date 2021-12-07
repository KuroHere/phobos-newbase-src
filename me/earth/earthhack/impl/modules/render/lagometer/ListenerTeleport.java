// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.lagometer;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;

final class ListenerTeleport extends ModuleListener<LagOMeter, PacketEvent.Post<CPacketConfirmTeleport>>
{
    public ListenerTeleport(final LagOMeter module) {
        super(module, (Class<? super Object>)PacketEvent.Post.class, CPacketConfirmTeleport.class);
    }
    
    public void invoke(final PacketEvent.Post<CPacketConfirmTeleport> event) {
        ((LagOMeter)this.module).teleported.set(true);
    }
}
