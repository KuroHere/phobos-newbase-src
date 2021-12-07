//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.boatfly;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;

final class ListenerEntityRelativeMove extends ModuleListener<BoatFly, PacketEvent.Receive<SPacketEntity.S15PacketEntityRelMove>>
{
    public ListenerEntityRelativeMove(final BoatFly module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketEntity.S15PacketEntityRelMove.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketEntity.S15PacketEntityRelMove> event) {
        if (ListenerEntityRelativeMove.mc.player.getRidingEntity() != null && ((BoatFly)this.module).noForceBoatMove.getValue() && event.getPacket().getEntity((World)ListenerEntityRelativeMove.mc.world) == ListenerEntityRelativeMove.mc.player.getRidingEntity()) {
            event.setCancelled(true);
        }
    }
}
