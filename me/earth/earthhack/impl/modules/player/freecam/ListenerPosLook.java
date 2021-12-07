//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.freecam;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.entity.*;

final class ListenerPosLook extends ModuleListener<Freecam, PacketEvent.Receive<SPacketPlayerPosLook>>
{
    public ListenerPosLook(final Freecam module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketPlayerPosLook.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketPlayerPosLook> event) {
        ListenerPosLook.mc.addScheduledTask(() -> {
            if (ListenerPosLook.mc.player == null) {
                return;
            }
            else {
                PacketUtil.handlePosLook((SPacketPlayerPosLook)event.getPacket(), (Entity)((((Freecam)this.module).getPlayer() == null) ? ListenerPosLook.mc.player : ((Freecam)this.module).getPlayer()), false);
                ((Freecam)this.module).getPlayer().onGround = true;
                return;
            }
        });
        event.setCancelled(true);
    }
}
