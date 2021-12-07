//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.boatfly;

import me.earth.earthhack.impl.event.listeners.*;
import net.minecraft.client.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;

final class ListenerCPackets extends CPacketPlayerListener
{
    private final BoatFly module;
    private static final Minecraft mc;
    
    public ListenerCPackets(final BoatFly module) {
        this.module = module;
    }
    
    @Override
    protected void onPacket(final PacketEvent.Send<CPacketPlayer> event) {
        if (this.module.noPosUpdate.getValue() && ListenerCPackets.mc.player.getRidingEntity() != null) {
            event.setCancelled(true);
        }
    }
    
    @Override
    protected void onPosition(final PacketEvent.Send<CPacketPlayer.Position> event) {
        if (this.module.noPosUpdate.getValue() && ListenerCPackets.mc.player.getRidingEntity() != null) {
            event.setCancelled(true);
        }
    }
    
    @Override
    protected void onRotation(final PacketEvent.Send<CPacketPlayer.Rotation> event) {
        if (this.module.noPosUpdate.getValue() && ListenerCPackets.mc.player.getRidingEntity() != null) {
            event.setCancelled(true);
        }
    }
    
    @Override
    protected void onPositionRotation(final PacketEvent.Send<CPacketPlayer.PositionRotation> event) {
        if (this.module.noPosUpdate.getValue() && ListenerCPackets.mc.player.getRidingEntity() != null) {
            event.setCancelled(true);
        }
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
