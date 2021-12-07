//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.media;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import io.netty.buffer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.commands.packet.util.*;
import io.netty.util.*;

final class ListenerTick extends ModuleListener<Media, TickEvent>
{
    public ListenerTick(final Media module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (Media.PING_BYPASS.isEnabled()) {
            if (!((Media)this.module).pingBypassEnabled) {
                ((Media)this.module).cache.clear();
            }
            ((Media)this.module).pingBypassEnabled = true;
            if (ListenerTick.mc.player == null) {
                ((Media)this.module).send = false;
            }
            else if (!((Media)this.module).send) {
                final PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
                buffer.writeShort(0);
                ListenerTick.mc.player.connection.sendPacket((Packet)new CPacketCustomPayload("PingBypass", buffer));
                BufferUtil.releaseBuffer((ReferenceCounted)buffer);
                ((Media)this.module).send = true;
            }
        }
        else if (((Media)this.module).pingBypassEnabled) {
            ((Media)this.module).pingBypassEnabled = false;
            ((Media)this.module).cache.clear();
        }
    }
}
