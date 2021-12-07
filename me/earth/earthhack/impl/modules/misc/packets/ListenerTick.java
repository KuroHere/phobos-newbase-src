//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.packets;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.misc.packets.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.network.*;

final class ListenerTick extends ModuleListener<Packets, TickEvent>
{
    public ListenerTick(final Packets module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (!event.isSafe()) {
            ((Packets)this.module).stateMap.clear();
            return;
        }
        if (!((Packets)this.module).crashing.get() && ((Packets)this.module).bookCrash.getValue() != BookCrashMode.None) {
            ((Packets)this.module).startCrash();
        }
        for (int i = 0; i < ((Packets)this.module).offhandCrashes.getValue(); ++i) {
            ListenerTick.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, BlockPos.ORIGIN, EnumFacing.UP));
        }
    }
}
