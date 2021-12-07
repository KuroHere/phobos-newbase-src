//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.noslowdown;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.network.*;
import net.minecraft.item.*;

final class ListenerTryUseItemOnBlock extends ModuleListener<NoSlowDown, PacketEvent.Post<CPacketPlayerTryUseItemOnBlock>>
{
    public ListenerTryUseItemOnBlock(final NoSlowDown module) {
        super(module, (Class<? super Object>)PacketEvent.Post.class, CPacketPlayerTryUseItemOnBlock.class);
    }
    
    public void invoke(final PacketEvent.Post<CPacketPlayerTryUseItemOnBlock> event) {
        final Item item = ListenerTryUseItemOnBlock.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem();
        if (((NoSlowDown)this.module).superStrict.getValue() && (item instanceof ItemFood || item instanceof ItemBow || item instanceof ItemPotion)) {
            NetworkUtil.send((Packet<?>)new CPacketHeldItemChange(ListenerTryUseItemOnBlock.mc.player.inventory.currentItem));
        }
    }
}
