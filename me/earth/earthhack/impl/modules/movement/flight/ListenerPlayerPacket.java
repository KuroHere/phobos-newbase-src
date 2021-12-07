//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.flight;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.modules.movement.flight.mode.*;
import me.earth.earthhack.impl.core.mixins.network.client.*;

final class ListenerPlayerPacket extends CPacketPlayerListener implements Globals
{
    private final Flight module;
    
    public ListenerPlayerPacket(final Flight module) {
        this.module = module;
    }
    
    @Override
    protected void onPacket(final PacketEvent.Send<CPacketPlayer> event) {
        this.onCPacket(event);
    }
    
    @Override
    protected void onPosition(final PacketEvent.Send<CPacketPlayer.Position> event) {
        this.onCPacket((PacketEvent.Send<? extends CPacketPlayer>)event);
    }
    
    @Override
    protected void onRotation(final PacketEvent.Send<CPacketPlayer.Rotation> event) {
        this.onCPacket((PacketEvent.Send<? extends CPacketPlayer>)event);
    }
    
    @Override
    protected void onPositionRotation(final PacketEvent.Send<CPacketPlayer.PositionRotation> event) {
        this.onCPacket((PacketEvent.Send<? extends CPacketPlayer>)event);
    }
    
    private void onCPacket(final PacketEvent.Send<? extends CPacketPlayer> packet) {
        if (this.module.mode.getValue() == FlightMode.AAC) {
            if (ListenerPlayerPacket.mc.player.fallDistance > 3.8f) {
                packet.getPacket().setOnGround(true);
                ListenerPlayerPacket.mc.player.fallDistance = 0.0f;
            }
        }
        else if (this.module.mode.getValue() == FlightMode.ConstantiamNew && this.module.constNewStage == 0) {
            packet.setCancelled(true);
        }
        if (this.module.mode.getValue() != FlightMode.Constantiam || this.module.clipped) {}
    }
}
