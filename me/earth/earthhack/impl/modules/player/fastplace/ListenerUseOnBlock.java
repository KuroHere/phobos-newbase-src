//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.fastplace;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;

final class ListenerUseOnBlock extends ModuleListener<FastPlace, PacketEvent.Send<CPacketPlayerTryUseItemOnBlock>>
{
    public ListenerUseOnBlock(final FastPlace module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketPlayerTryUseItemOnBlock.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketPlayerTryUseItemOnBlock> event) {
        if ((((FastPlace)this.module).bypass.getValue() && ListenerUseOnBlock.mc.player.getHeldItem(event.getPacket().getHand()).getItem() == Items.EXPERIENCE_BOTTLE) || (((FastPlace)this.module).foodBypass.getValue() && ListenerUseOnBlock.mc.player.getHeldItem(event.getPacket().getHand()).getItem() instanceof ItemFood)) {
            if (Managers.ACTION.isSneaking() || ((FastPlace)this.module).bypassContainers.getValue()) {
                event.setCancelled(true);
            }
            else {
                final BlockPos pos = event.getPacket().getPos();
                final IBlockState state = ListenerUseOnBlock.mc.world.getBlockState(pos);
                if (!SpecialBlocks.BAD_BLOCKS.contains(state.getBlock()) && !SpecialBlocks.SHULKERS.contains(state.getBlock())) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
