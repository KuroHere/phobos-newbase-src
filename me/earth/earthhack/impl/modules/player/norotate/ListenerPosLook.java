//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.norotate;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.entity.*;

final class ListenerPosLook extends ModuleListener<NoRotate, PacketEvent.Receive<SPacketPlayerPosLook>>
{
    public ListenerPosLook(final NoRotate module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, -5, SPacketPlayerPosLook.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketPlayerPosLook> event) {
        if (((NoRotate)this.module).noForceLook.getValue() && !event.isCancelled()) {
            event.setCancelled(true);
            if (((NoRotate)this.module).async.getValue()) {
                PacketUtil.handlePosLook(event.getPacket(), (Entity)ListenerPosLook.mc.player, true);
            }
            else {
                ListenerPosLook.mc.addScheduledTask(() -> PacketUtil.handlePosLook((SPacketPlayerPosLook)event.getPacket(), (Entity)ListenerPosLook.mc.player, true));
            }
        }
    }
}
