//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.pistonaura;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

final class ListenerMultiBlockChange extends ModuleListener<PistonAura, PacketEvent.Receive<SPacketMultiBlockChange>>
{
    public ListenerMultiBlockChange(final PistonAura module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketMultiBlockChange.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketMultiBlockChange> event) {
        if (!((PistonAura)this.module).multiChange.getValue()) {
            return;
        }
        ListenerMultiBlockChange.mc.addScheduledTask(() -> {
            if (((PistonAura)this.module).current != null) {
                final SPacketMultiBlockChange packet = (SPacketMultiBlockChange)event.getPacket();
                packet.getChangedBlocks();
                final SPacketMultiBlockChange.BlockUpdateData[] array;
                final int length = array.length;
                int i = 0;
                while (i < length) {
                    final SPacketMultiBlockChange.BlockUpdateData data = array[i];
                    if (((PistonAura)this.module).checkUpdate(data.getPos(), data.getBlockState(), ((PistonAura)this.module).current.getRedstonePos(), Blocks.REDSTONE_BLOCK, Blocks.REDSTONE_TORCH) || ((PistonAura)this.module).checkUpdate(data.getPos(), data.getBlockState(), ((PistonAura)this.module).current.getPistonPos(), (Block)Blocks.PISTON, (Block)Blocks.STICKY_PISTON)) {
                        ((PistonAura)this.module).current.setValid(false);
                    }
                    else {
                        ++i;
                    }
                }
            }
        });
    }
}
