//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.nohandshake;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.commands.packet.util.*;
import io.netty.util.*;
import me.earth.earthhack.impl.core.mixins.network.client.*;
import io.netty.buffer.*;
import net.minecraft.network.*;

final class ListenerCustomPayload extends ModuleListener<NoHandShake, PacketEvent.Send<CPacketCustomPayload>>
{
    public ListenerCustomPayload(final NoHandShake module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketCustomPayload.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketCustomPayload> event) {
        final CPacketCustomPayload packet = event.getPacket();
        if (packet.getChannelName().equals("MC|Brand")) {
            final PacketBuffer buffer = packet.getBufferData();
            BufferUtil.releaseBuffer((ReferenceCounted)buffer);
            ((ICPacketCustomPayload)packet).setData(new PacketBuffer(Unpooled.buffer()).writeString("vanilla"));
        }
    }
}
