//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.announcer;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.modules.misc.announcer.util.*;
import net.minecraft.item.*;

final class ListenerPlace extends ModuleListener<Announcer, PacketEvent.Post<CPacketPlayerTryUseItemOnBlock>>
{
    public ListenerPlace(final Announcer module) {
        super(module, (Class<? super Object>)PacketEvent.Post.class, CPacketPlayerTryUseItemOnBlock.class);
    }
    
    public void invoke(final PacketEvent.Post<CPacketPlayerTryUseItemOnBlock> event) {
        if (((Announcer)this.module).place.getValue()) {
            final CPacketPlayerTryUseItemOnBlock packet = event.getPacket();
            final ItemStack stack = ListenerPlace.mc.player.getHeldItem(packet.getHand());
            if (stack.getItem() instanceof ItemBlock || stack.getItem() instanceof ItemEndCrystal) {
                ((Announcer)this.module).addWordAndIncrement(AnnouncementType.Place, stack.getDisplayName());
            }
        }
    }
}
