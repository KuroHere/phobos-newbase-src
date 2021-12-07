//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass;

import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.modules.client.pingbypass.packets.*;
import me.earth.earthhack.impl.commands.packet.util.*;
import io.netty.util.*;

final class ListenerCustomPayload extends EventListener<PacketEvent.Receive<SPacketCustomPayload>>
{
    private final PayloadManager manager;
    
    public ListenerCustomPayload(final PayloadManager manager) {
        super(PacketEvent.Receive.class, SPacketCustomPayload.class);
        this.manager = manager;
    }
    
    @Override
    public void invoke(final PacketEvent.Receive<SPacketCustomPayload> event) {
        if (event.getPacket().getChannelName().equalsIgnoreCase("PingBypass")) {
            try {
                this.manager.onPacket(event.getPacket());
                event.setCancelled(true);
            }
            finally {
                BufferUtil.releaseBuffer((ReferenceCounted)event.getPacket().getBufferData());
            }
        }
    }
}
