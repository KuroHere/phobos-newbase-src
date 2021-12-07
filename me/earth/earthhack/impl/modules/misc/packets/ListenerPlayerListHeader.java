//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.packets;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.*;
import java.util.*;

final class ListenerPlayerListHeader extends ModuleListener<Packets, PacketEvent.Receive<SPacketPlayerListHeaderFooter>>
{
    public ListenerPlayerListHeader(final Packets module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, -2147483647, SPacketPlayerListHeaderFooter.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketPlayerListHeaderFooter> event) {
        if (((Packets)this.module).safeHeaders.getValue()) {
            final SPacketPlayerListHeaderFooter packet = event.getPacket();
            if (packet.getHeader().getFormattedText().isEmpty() || packet.getFooter().getFormattedText().isEmpty()) {
                event.setCancelled(true);
                ListenerPlayerListHeader.mc.addScheduledTask(() -> packet.processPacket((INetHandlerPlayClient)Objects.requireNonNull((INetHandlerPlayClient)ListenerPlayerListHeader.mc.getConnection())));
            }
        }
    }
}
