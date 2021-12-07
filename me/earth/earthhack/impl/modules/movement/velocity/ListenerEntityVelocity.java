//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.velocity;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.core.mixins.network.server.*;

final class ListenerEntityVelocity extends ModuleListener<Velocity, PacketEvent.Receive<SPacketEntityVelocity>>
{
    public ListenerEntityVelocity(final Velocity module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, -1000000, SPacketEntityVelocity.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketEntityVelocity> event) {
        if (((Velocity)this.module).knockBack.getValue() && ListenerEntityVelocity.mc.player != null) {
            final ISPacketEntityVelocity velocity = event.getPacket();
            if (velocity.getEntityID() == ListenerEntityVelocity.mc.player.getEntityId()) {
                if (((Velocity)this.module).horizontal.getValue() == 0.0f && ((Velocity)this.module).vertical.getValue() == 0.0f) {
                    event.setCancelled(true);
                }
                else {
                    velocity.setX((int)(velocity.getX() * ((Velocity)this.module).horizontal.getValue()));
                    velocity.setX((int)(velocity.getX() * ((Velocity)this.module).vertical.getValue()));
                    velocity.setX((int)(velocity.getX() * ((Velocity)this.module).horizontal.getValue()));
                }
            }
        }
    }
}
