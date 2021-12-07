//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.nosoundlag;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

final class ListenerSound extends ModuleListener<NoSoundLag, PacketEvent.Receive<SPacketSoundEffect>>
{
    public ListenerSound(final NoSoundLag module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketSoundEffect.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSoundEffect> event) {
        if (((NoSoundLag)this.module).sounds.getValue() && NoSoundLag.SOUNDS.contains(event.getPacket().getSound())) {
            event.setCancelled(true);
        }
    }
}
