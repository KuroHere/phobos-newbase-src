// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.packetfly;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;

final class ListenerCPacket extends CPacketPlayerListener
{
    private final PacketFly packetFly;
    
    public ListenerCPacket(final PacketFly packetFly) {
        this.packetFly = packetFly;
    }
    
    @Override
    protected void onPacket(final PacketEvent.Send<CPacketPlayer> event) {
        this.packetFly.onPacketSend(event);
    }
    
    @Override
    protected void onPosition(final PacketEvent.Send<CPacketPlayer.Position> event) {
        this.packetFly.onPacketSend((PacketEvent<? extends CPacketPlayer>)event);
    }
    
    @Override
    protected void onRotation(final PacketEvent.Send<CPacketPlayer.Rotation> event) {
        this.packetFly.onPacketSend((PacketEvent<? extends CPacketPlayer>)event);
    }
    
    @Override
    protected void onPositionRotation(final PacketEvent.Send<CPacketPlayer.PositionRotation> event) {
        this.packetFly.onPacketSend((PacketEvent<? extends CPacketPlayer>)event);
    }
}
