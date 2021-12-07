//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.boatfly;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

final class ListenerEntityTeleport extends ModuleListener<BoatFly, PacketEvent.Receive<SPacketEntityTeleport>>
{
    public ListenerEntityTeleport(final BoatFly module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketEntityTeleport.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketEntityTeleport> event) {
        if (ListenerEntityTeleport.mc.player.getRidingEntity() != null && ((BoatFly)this.module).noForceBoatMove.getValue() && event.getPacket().getEntityId() == ListenerEntityTeleport.mc.player.getRidingEntity().getEntityId()) {
            event.setCancelled(true);
        }
    }
}
