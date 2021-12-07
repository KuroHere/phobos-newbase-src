//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.speedmine;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.modules.player.speedmine.mode.*;
import net.minecraft.init.*;

final class ListenerMultiBlockChange extends ModuleListener<Speedmine, PacketEvent.Receive<SPacketMultiBlockChange>>
{
    public ListenerMultiBlockChange(final Speedmine module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketMultiBlockChange.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketMultiBlockChange> event) {
        final SPacketMultiBlockChange packet = event.getPacket();
        if ((((Speedmine)this.module).mode.getValue() != MineMode.Smart || ((Speedmine)this.module).sentPacket) && ((Speedmine)this.module).mode.getValue() != MineMode.Instant && ((Speedmine)this.module).mode.getValue() != MineMode.Civ) {
            for (final SPacketMultiBlockChange.BlockUpdateData data : packet.getChangedBlocks()) {
                if (data.getPos().equals((Object)((Speedmine)this.module).pos) && data.getBlockState().getBlock() == Blocks.AIR) {
                    ListenerMultiBlockChange.mc.addScheduledTask((Speedmine)this.module::reset);
                }
            }
        }
    }
}
