//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

final class ListenerKeepAlive extends ModuleListener<PingBypass, PacketEvent.Receive<SPacketKeepAlive>>
{
    public ListenerKeepAlive(final PingBypass module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketKeepAlive.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketKeepAlive> event) {
        final SPacketKeepAlive packet = event.getPacket();
        if (!((PingBypass)this.module).handled && packet.getId() > 0L && packet.getId() < 1000L) {
            ((PingBypass)this.module).startTime = System.currentTimeMillis() - ((PingBypass)this.module).startTime;
            ((PingBypass)this.module).serverPing = (int)packet.getId();
            ((PingBypass)this.module).ping = ((PingBypass)this.module).startTime;
            event.setCancelled(((PingBypass)this.module).handled = true);
        }
    }
}
