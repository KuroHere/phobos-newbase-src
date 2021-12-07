//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.automine;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.init.*;

final class ListenerPlace extends ModuleListener<AutoMine, PacketEvent.Post<CPacketPlayerTryUseItemOnBlock>>
{
    public ListenerPlace(final AutoMine module) {
        super(module, (Class<? super Object>)PacketEvent.Post.class, CPacketPlayerTryUseItemOnBlock.class);
    }
    
    public void invoke(final PacketEvent.Post<CPacketPlayerTryUseItemOnBlock> event) {
        if (ListenerPlace.mc.player.getHeldItem(event.getPacket().getHand()).getItem() == Items.END_CRYSTAL) {
            ((AutoMine)this.module).downTimer.reset();
        }
    }
}
