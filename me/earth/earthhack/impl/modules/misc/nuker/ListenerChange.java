//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.nuker;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.minecraft.blocks.mine.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.util.minecraft.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;

final class ListenerChange extends ModuleListener<Nuker, PacketEvent.Receive<SPacketBlockChange>>
{
    public ListenerChange(final Nuker module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, 11, SPacketBlockChange.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketBlockChange> event) {
        if (((Nuker)this.module).instant.getValue() && ((Nuker)this.module).rotate.getValue() != Rotate.Normal) {
            final SPacketBlockChange packet = event.getPacket();
            final Set<Block> blocks = ((Nuker)this.module).getBlocks();
            if (blocks.isEmpty()) {
                return;
            }
            if (blocks.contains(packet.getBlockState().getBlock()) && BlockUtil.getDistanceSqDigging(packet.getBlockPosition()) <= MathUtil.square(((Nuker)this.module).range.getValue())) {
                ListenerChange.mc.addScheduledTask(() -> {
                    if (!(ListenerChange.mc.player.getActiveItemStack().getItem() instanceof ItemFood)) {
                        final BlockPos pos = packet.getBlockPosition();
                        final int slot = MineUtil.findBestTool(pos);
                        if (slot != -1) {
                            Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
                                final int lastSlot = ListenerChange.mc.player.inventory.currentItem;
                                ((Nuker)this.module).timer.reset();
                                InventoryUtil.switchTo(slot);
                                ((Nuker)this.module).attack(pos);
                                InventoryUtil.switchTo(lastSlot);
                            });
                        }
                    }
                });
            }
        }
    }
}
