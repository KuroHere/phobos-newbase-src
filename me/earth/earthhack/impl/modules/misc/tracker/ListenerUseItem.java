//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.tracker;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.init.*;

final class ListenerUseItem extends ModuleListener<Tracker, PacketEvent.Post<CPacketPlayerTryUseItem>>
{
    public ListenerUseItem(final Tracker module) {
        super(module, (Class<? super Object>)PacketEvent.Post.class, CPacketPlayerTryUseItem.class);
    }
    
    public void invoke(final PacketEvent.Post<CPacketPlayerTryUseItem> event) {
        final CPacketPlayerTryUseItem p = event.getPacket();
        if (ListenerUseItem.mc.player.getHeldItem(p.getHand()).getItem() == Items.EXPERIENCE_BOTTLE) {
            ((Tracker)this.module).awaitingExp.incrementAndGet();
        }
    }
}
