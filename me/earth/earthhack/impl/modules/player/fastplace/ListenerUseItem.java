//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.fastplace;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

final class ListenerUseItem extends ModuleListener<FastPlace, PacketEvent.Send<CPacketPlayerTryUseItem>>
{
    private boolean sending;
    
    public ListenerUseItem(final FastPlace module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketPlayerTryUseItem.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketPlayerTryUseItem> event) {
        if (!((FastPlace)this.module).doubleEat.getValue() || this.sending || event.isCancelled() || !(ListenerUseItem.mc.player.getHeldItem(event.getPacket().getHand()).getItem() instanceof ItemFood)) {
            return;
        }
        this.sending = true;
        ListenerUseItem.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(event.getPacket().getHand()));
        this.sending = false;
        ListenerUseItem.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
    }
}
