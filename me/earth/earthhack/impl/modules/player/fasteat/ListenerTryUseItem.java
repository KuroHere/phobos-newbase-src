//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.fasteat;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.modules.player.fasteat.mode.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.network.*;

final class ListenerTryUseItem extends ModuleListener<FastEat, PacketEvent.Send<CPacketPlayerTryUseItem>>
{
    public ListenerTryUseItem(final FastEat module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketPlayerTryUseItem.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketPlayerTryUseItem> event) {
        if (((FastEat)this.module).mode.getValue() == FastEatMode.Update && ((FastEat)this.module).isValid(ListenerTryUseItem.mc.player.getHeldItem(event.getPacket().getHand()))) {
            NetworkUtil.sendPacketNoEvent((Packet<?>)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    }
}
