//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.packets;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.*;

final class ListenerVelocity extends ModuleListener<Packets, PacketEvent.Receive<SPacketEntityVelocity>>
{
    public ListenerVelocity(final Packets module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, Integer.MIN_VALUE, SPacketEntityVelocity.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketEntityVelocity> event) {
        if (event.isCancelled() || !((Packets)this.module).fastVelocity.getValue()) {
            return;
        }
        final SPacketEntityVelocity packet = event.getPacket();
        final Entity entity = Managers.ENTITIES.getEntity(packet.getEntityID());
        if (entity != null) {
            event.setCancelled(((Packets)this.module).cancelVelocity.getValue());
            entity.setVelocity(packet.getMotionX() / 8000.0, packet.getMotionY() / 8000.0, packet.getMotionZ() / 8000.0);
        }
    }
}
