//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.speedmine;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.modules.player.speedmine.mode.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;

final class ListenerBlockChange extends ModuleListener<Speedmine, PacketEvent.Receive<SPacketBlockChange>>
{
    public ListenerBlockChange(final Speedmine module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketBlockChange.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketBlockChange> event) {
        final SPacketBlockChange packet = event.getPacket();
        if (packet.getBlockPosition().equals((Object)((Speedmine)this.module).pos) && packet.getBlockState().getBlock() == Blocks.AIR && (((Speedmine)this.module).mode.getValue() != MineMode.Smart || ((Speedmine)this.module).sentPacket) && ((Speedmine)this.module).mode.getValue() != MineMode.Instant && ((Speedmine)this.module).mode.getValue() != MineMode.Civ) {
            ListenerBlockChange.mc.addScheduledTask((Speedmine)this.module::reset);
        }
        else if (packet.getBlockPosition().equals((Object)((Speedmine)this.module).pos) && packet.getBlockState() == ListenerBlockChange.mc.world.getBlockState(((Speedmine)this.module).pos) && ((Speedmine)this.module).shouldAbort && ((Speedmine)this.module).mode.getValue() == MineMode.Instant) {
            ListenerBlockChange.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, ((Speedmine)this.module).pos, EnumFacing.DOWN));
            ((Speedmine)this.module).shouldAbort = false;
        }
    }
}
