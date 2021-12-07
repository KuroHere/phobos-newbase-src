//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.buildheight;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.core.mixins.network.client.*;

final class ListenerPlaceBlock extends ModuleListener<BuildHeight, PacketEvent.Send<CPacketPlayerTryUseItemOnBlock>>
{
    public ListenerPlaceBlock(final BuildHeight module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketPlayerTryUseItemOnBlock.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketPlayerTryUseItemOnBlock> event) {
        final CPacketPlayerTryUseItemOnBlock packet = event.getPacket();
        if (packet.getPos().getY() >= 255 && (!((BuildHeight)this.module).crystals.getValue() || ListenerPlaceBlock.mc.player.getHeldItem(packet.getHand()).getItem() == Items.END_CRYSTAL) && packet.getDirection() == EnumFacing.UP) {
            ((ICPacketPlayerTryUseItemOnBlock)packet).setFacing(EnumFacing.DOWN);
        }
    }
}
