//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

final class ListenerExplosion extends ModuleListener<AutoCrystal, PacketEvent.Receive<SPacketExplosion>>
{
    public ListenerExplosion(final AutoCrystal module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, Integer.MIN_VALUE, SPacketExplosion.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketExplosion> event) {
        if (((AutoCrystal)this.module).explosionThread.getValue() && !event.getPacket().getAffectedBlockPositions().isEmpty()) {
            ((AutoCrystal)this.module).threadHelper.schedulePacket(event);
        }
    }
}
