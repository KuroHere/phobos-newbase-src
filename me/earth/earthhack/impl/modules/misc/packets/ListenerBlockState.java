//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.packets;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

final class ListenerBlockState extends ModuleListener<Packets, PacketEvent.Receive<SPacketBlockChange>>
{
    public ListenerBlockState(final Packets module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, Integer.MIN_VALUE, SPacketBlockChange.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketBlockChange> event) {
        if (((Packets)this.module).fastBlockStates.getValue()) {
            final SPacketBlockChange p = event.getPacket();
            ((Packets)this.module).stateMap.put(p.getBlockPosition(), p.getBlockState());
            ListenerBlockState.mc.addScheduledTask(() -> ((Packets)this.module).stateMap.remove(p.getBlockPosition()));
        }
    }
}
