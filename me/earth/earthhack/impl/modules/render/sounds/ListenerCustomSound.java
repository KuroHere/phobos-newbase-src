//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.sounds;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.modules.render.sounds.util.*;

final class ListenerCustomSound extends ModuleListener<Sounds, PacketEvent.Receive<SPacketCustomSound>>
{
    public ListenerCustomSound(final Sounds module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketCustomSound.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketCustomSound> event) {
        final boolean cancelled = event.isCancelled();
        if (((Sounds)this.module).client.getValue() || !((Sounds)this.module).custom.getValue() || (cancelled && !((Sounds)this.module).cancelled.getValue()) || !((Sounds)this.module).isValid(event.getPacket().getSoundName())) {
            return;
        }
        final SPacketCustomSound packet = event.getPacket();
        final String s = packet.getSoundName();
        ((Sounds)this.module).sounds.put(new CustomSound(packet.getX(), packet.getY(), packet.getZ(), (cancelled ? "Cancelled: " : "") + s), System.currentTimeMillis());
    }
}
