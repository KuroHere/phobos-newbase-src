//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.nuker;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.minecraft.blocks.mine.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.block.*;
import java.util.*;

final class ListenerMultiChange extends ModuleListener<Nuker, PacketEvent.Receive<SPacketMultiBlockChange>>
{
    public ListenerMultiChange(final Nuker module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketMultiBlockChange.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketMultiBlockChange> event) {
        if (((Nuker)this.module).instant.getValue() && ((Nuker)this.module).rotate.getValue() != Rotate.Normal) {
            final SPacketMultiBlockChange packet = event.getPacket();
            final Set<BlockPos> toAttack = new HashSet<BlockPos>();
            final Set<Block> blocks = ((Nuker)this.module).getBlocks();
            if (blocks.isEmpty()) {
                return;
            }
            for (final SPacketMultiBlockChange.BlockUpdateData data : packet.getChangedBlocks()) {
                if (blocks.contains(data.getBlockState().getBlock()) && BlockUtil.getDistanceSqDigging((Entity)ListenerMultiChange.mc.player, data.getPos()) <= MathUtil.square(((Nuker)this.module).range.getValue())) {
                    toAttack.add(data.getPos());
                }
            }
            if (!toAttack.isEmpty()) {
                ListenerMultiChange.mc.addScheduledTask(() -> {
                    if (!(ListenerMultiChange.mc.player.getActiveItemStack().getItem() instanceof ItemFood)) {
                        final BlockPos pos = (BlockPos)toAttack.stream().findFirst().get();
                        final int slot = MineUtil.findBestTool(pos);
                        if (slot != -1) {
                            Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
                                final int lastSlot = ListenerMultiChange.mc.player.inventory.currentItem;
                                InventoryUtil.switchTo(slot);
                                toAttack.iterator();
                                final Iterator iterator;
                                while (iterator.hasNext()) {
                                    final BlockPos p = iterator.next();
                                    ((Nuker)this.module).attack(p);
                                }
                                InventoryUtil.switchTo(lastSlot);
                            });
                        }
                    }
                });
            }
        }
    }
}
