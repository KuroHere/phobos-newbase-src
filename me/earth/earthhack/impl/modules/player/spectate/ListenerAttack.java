//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.spectate;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.core.ducks.network.*;

final class ListenerAttack extends ModuleListener<Spectate, PacketEvent.Send<CPacketUseEntity>>
{
    public ListenerAttack(final Spectate module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketUseEntity.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketUseEntity> event) {
        if (event.getPacket().getEntityID() == ListenerAttack.mc.player.getEntityId()) {
            event.setCancelled(true);
        }
    }
}
