// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.boatfly;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

final class ListenerServerVehicleMove extends ModuleListener<BoatFly, PacketEvent.Receive<SPacketMoveVehicle>>
{
    public ListenerServerVehicleMove(final BoatFly module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketMoveVehicle.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketMoveVehicle> event) {
        if (((BoatFly)this.module).noVehicleMove.getValue()) {
            event.setCancelled(true);
        }
    }
}
