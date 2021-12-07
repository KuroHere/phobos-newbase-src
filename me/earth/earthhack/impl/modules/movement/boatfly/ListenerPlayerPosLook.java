//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.boatfly;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.gui.*;

final class ListenerPlayerPosLook extends ModuleListener<BoatFly, PacketEvent.Receive<SPacketPlayerPosLook>>
{
    public ListenerPlayerPosLook(final BoatFly module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketPlayerPosLook.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketPlayerPosLook> event) {
        if (((BoatFly)this.module).noForceRotate.getValue() && ListenerPlayerPosLook.mc.player.getRidingEntity() != null && !(ListenerPlayerPosLook.mc.currentScreen instanceof GuiMainMenu) && !(ListenerPlayerPosLook.mc.currentScreen instanceof GuiDisconnected) && !(ListenerPlayerPosLook.mc.currentScreen instanceof GuiDownloadTerrain) && !(ListenerPlayerPosLook.mc.currentScreen instanceof GuiConnecting) && !(ListenerPlayerPosLook.mc.currentScreen instanceof GuiMultiplayer)) {
            event.setCancelled(true);
        }
    }
}
