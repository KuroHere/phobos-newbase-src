// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.boatfly;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;

final class ListenerSteer extends ModuleListener<BoatFly, PacketEvent.Send<CPacketSteerBoat>>
{
    public ListenerSteer(final BoatFly module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketSteerBoat.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketSteerBoat> event) {
        if (((BoatFly)this.module).noSteer.getValue()) {
            event.setCancelled(true);
        }
    }
}
