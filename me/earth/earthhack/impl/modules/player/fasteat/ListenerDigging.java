//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.fasteat;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

final class ListenerDigging extends ModuleListener<FastEat, PacketEvent.Send<CPacketPlayerDigging>>
{
    public ListenerDigging(final FastEat module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketPlayerDigging.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketPlayerDigging> event) {
        if (((FastEat)this.module).cancel.getValue() && ListenerDigging.mc.player.getActiveItemStack().getItem() instanceof ItemFood) {
            final CPacketPlayerDigging packet = event.getPacket();
            if (packet.getAction() == CPacketPlayerDigging.Action.RELEASE_USE_ITEM && packet.getFacing() == EnumFacing.DOWN && packet.getPosition().equals((Object)BlockPos.ORIGIN)) {
                event.setCancelled(true);
            }
        }
    }
}
