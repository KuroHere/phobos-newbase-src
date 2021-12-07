// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.phase;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;

final class ListenerCPackets extends CPacketPlayerListener
{
    private final Phase module;
    
    public ListenerCPackets(final Phase module) {
        this.module = module;
    }
    
    @Override
    protected void onPacket(final PacketEvent.Send<CPacketPlayer> event) {
        this.module.onPacket(event);
    }
    
    @Override
    protected void onPosition(final PacketEvent.Send<CPacketPlayer.Position> event) {
        this.module.onPacket((PacketEvent.Send<? extends CPacketPlayer>)event);
    }
    
    @Override
    protected void onRotation(final PacketEvent.Send<CPacketPlayer.Rotation> event) {
        this.module.onPacket((PacketEvent.Send<? extends CPacketPlayer>)event);
    }
    
    @Override
    protected void onPositionRotation(final PacketEvent.Send<CPacketPlayer.PositionRotation> event) {
        this.module.onPacket((PacketEvent.Send<? extends CPacketPlayer>)event);
    }
}
