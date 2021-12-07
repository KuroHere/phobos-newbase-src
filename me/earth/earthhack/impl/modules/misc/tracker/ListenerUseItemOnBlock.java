//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.tracker;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;

final class ListenerUseItemOnBlock extends ModuleListener<Tracker, PacketEvent.Post<CPacketPlayerTryUseItemOnBlock>>
{
    public ListenerUseItemOnBlock(final Tracker module) {
        super(module, (Class<? super Object>)PacketEvent.Post.class, CPacketPlayerTryUseItemOnBlock.class);
    }
    
    public void invoke(final PacketEvent.Post<CPacketPlayerTryUseItemOnBlock> event) {
        final CPacketPlayerTryUseItemOnBlock packet = event.getPacket();
        if (ListenerUseItemOnBlock.mc.player.getHeldItem(packet.getHand()).getItem() == Items.END_CRYSTAL) {
            final BlockPos pos = packet.getPos();
            ((Tracker)this.module).placed.add(new BlockPos((double)(pos.getX() + 0.5f), (double)(pos.getY() + 1), (double)(pos.getZ() + 0.5f)));
        }
    }
}
