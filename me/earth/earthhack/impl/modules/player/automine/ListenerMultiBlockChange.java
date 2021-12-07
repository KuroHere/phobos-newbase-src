//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.automine;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

final class ListenerMultiBlockChange extends ModuleListener<AutoMine, PacketEvent.Receive<SPacketMultiBlockChange>>
{
    public ListenerMultiBlockChange(final AutoMine module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketMultiBlockChange.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketMultiBlockChange> event) {
        final SPacketMultiBlockChange packet = event.getPacket();
        ListenerMultiBlockChange.mc.addScheduledTask(() -> {
            if (((AutoMine)this.module).constellation != null) {
                packet.getChangedBlocks();
                final SPacketMultiBlockChange.BlockUpdateData[] array;
                final int length = array.length;
                int i = 0;
                while (i < length) {
                    final SPacketMultiBlockChange.BlockUpdateData data = array[i];
                    if (((AutoMine)this.module).constellation.isAffected(data.getPos(), data.getBlockState())) {
                        ((AutoMine)this.module).constellation = null;
                        break;
                    }
                    else {
                        ++i;
                    }
                }
            }
        });
    }
}
