//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.packets;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

final class ListenerBlockMulti extends ModuleListener<Packets, PacketEvent.Receive<SPacketMultiBlockChange>>
{
    public ListenerBlockMulti(final Packets module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, Integer.MIN_VALUE, SPacketMultiBlockChange.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketMultiBlockChange> event) {
        if (((Packets)this.module).fastBlockStates.getValue()) {
            final SPacketMultiBlockChange p = event.getPacket();
            for (final SPacketMultiBlockChange.BlockUpdateData d : p.getChangedBlocks()) {
                ((Packets)this.module).stateMap.put(d.getPos(), d.getBlockState());
            }
            ListenerBlockMulti.mc.addScheduledTask(() -> {
                p.getChangedBlocks();
                final SPacketMultiBlockChange.BlockUpdateData[] array;
                int j = 0;
                for (int length2 = array.length; j < length2; ++j) {
                    final SPacketMultiBlockChange.BlockUpdateData d2 = array[j];
                    ((Packets)this.module).stateMap.remove(d2.getPos());
                }
            });
        }
    }
}
