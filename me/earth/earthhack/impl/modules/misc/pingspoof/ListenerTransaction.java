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

final class ListenerTransaction extends ModuleListener<PingSpoof, PacketEvent.Send<CPacketConfirmTransaction>>
{
    private static final ModuleCache<PingBypass> PINGBYPASS;
    
    public ListenerTransaction(final PingSpoof module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketConfirmTransaction.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketConfirmTransaction> event) {
        if (!ListenerTransaction.PINGBYPASS.isEnabled() && ((PingSpoof)this.module).transactions.getValue()) {
            if (((PingSpoof)this.module).transactionIDs.remove(event.getPacket().getUid())) {
                return;
            }
            ((PingSpoof)this.module).onPacket(event.getPacket());
            event.setCancelled(true);
        }
    }
    
    static {
        PINGBYPASS = Caches.getModule(PingBypass.class);
    }
}
