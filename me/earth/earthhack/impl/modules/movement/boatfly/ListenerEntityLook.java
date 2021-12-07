//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.boatfly;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;

final class ListenerEntityLook extends ModuleListener<BoatFly, PacketEvent.Receive<SPacketEntity.S16PacketEntityLook>>
{
    public ListenerEntityLook(final BoatFly module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketEntity.S16PacketEntityLook.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketEntity.S16PacketEntityLook> event) {
        if (ListenerEntityLook.mc.player.getRidingEntity() != null && ((BoatFly)this.module).noForceBoatMove.getValue() && event.getPacket().getEntity((World)ListenerEntityLook.mc.world) == ListenerEntityLook.mc.player.getRidingEntity()) {
            event.setCancelled(true);
        }
    }
}
