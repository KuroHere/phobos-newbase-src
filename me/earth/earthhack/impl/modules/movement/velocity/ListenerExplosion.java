// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.velocity;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.core.mixins.network.server.*;

final class ListenerExplosion extends ModuleListener<Velocity, PacketEvent.Receive<SPacketExplosion>>
{
    public ListenerExplosion(final Velocity module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, -1000000, SPacketExplosion.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketExplosion> event) {
        if (((Velocity)this.module).explosions.getValue()) {
            final ISPacketExplosion explosion = event.getPacket();
            explosion.setX(explosion.getX() * ((Velocity)this.module).horizontal.getValue());
            explosion.setY(explosion.getY() * ((Velocity)this.module).horizontal.getValue());
            explosion.setZ(explosion.getZ() * ((Velocity)this.module).horizontal.getValue());
        }
    }
}
