//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.blink;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.modules.player.blink.mode.*;

final class ListenerPacket extends ModuleListener<Blink, PacketEvent.Send<?>>
{
    public ListenerPacket(final Blink module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class);
    }
    
    public void invoke(final PacketEvent.Send<?> event) {
        if (((Blink)this.module).packetMode.getValue().shouldCancel(event.getPacket())) {
            ListenerPacket.mc.addScheduledTask(() -> ((Blink)this.module).packets.add(event.getPacket()));
            event.setCancelled(true);
        }
    }
}
