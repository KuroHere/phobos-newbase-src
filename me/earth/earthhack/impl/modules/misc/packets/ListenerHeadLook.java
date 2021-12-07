//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.packets;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.core.mixins.network.server.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.*;

final class ListenerHeadLook extends ModuleListener<Packets, PacketEvent.Receive<SPacketEntityHeadLook>>
{
    public ListenerHeadLook(final Packets module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketEntityHeadLook.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketEntityHeadLook> event) {
        if (((Packets)this.module).fastHeadLook.getValue() && !event.isCancelled()) {
            final ISPacketEntityHeadLook p = event.getPacket();
            final Entity entity = Managers.ENTITIES.getEntity(p.getEntityId());
            if (entity != null) {
                entity.setRotationYawHead(event.getPacket().getYaw() * 360 / 256.0f);
            }
        }
    }
}
