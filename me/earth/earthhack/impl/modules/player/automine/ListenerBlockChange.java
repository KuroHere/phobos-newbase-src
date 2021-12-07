//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.automine;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

final class ListenerBlockChange extends ModuleListener<AutoMine, PacketEvent.Receive<SPacketBlockChange>>
{
    public ListenerBlockChange(final AutoMine module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketBlockChange.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketBlockChange> event) {
        final SPacketBlockChange packet = event.getPacket();
        ListenerBlockChange.mc.addScheduledTask(() -> {
            if (((AutoMine)this.module).constellation != null && ((AutoMine)this.module).constellation.isAffected(packet.getBlockPosition(), packet.getBlockState())) {
                ((AutoMine)this.module).constellation = null;
            }
        });
    }
}
