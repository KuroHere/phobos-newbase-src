//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.pingspoof;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.pingbypass.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerClickWindow extends ModuleListener<PingSpoof, PacketEvent.Post<CPacketClickWindow>>
{
    private static final ModuleCache<PingBypass> PINGBYPASS;
    
    public ListenerClickWindow(final PingSpoof module) {
        super(module, (Class<? super Object>)PacketEvent.Post.class, CPacketClickWindow.class);
    }
    
    public void invoke(final PacketEvent.Post<CPacketClickWindow> event) {
        if (((PingSpoof)this.module).transactions.getValue() && !ListenerClickWindow.PINGBYPASS.isEnabled()) {
            ((PingSpoof)this.module).transactionIDs.add(event.getPacket().getActionNumber());
        }
    }
    
    static {
        PINGBYPASS = Caches.getModule(PingBypass.class);
    }
}
