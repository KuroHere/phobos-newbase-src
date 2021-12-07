// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.listeners;

import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.api.event.bus.*;

public abstract class CPacketPlayerListener extends SubscriberImpl
{
    public CPacketPlayerListener() {
        this(10);
    }
    
    public CPacketPlayerListener(final int priority) {
        this.listeners.add(new EventListener<PacketEvent.Send<CPacketPlayer>>(PacketEvent.Send.class, priority, CPacketPlayer.class) {
            @Override
            public void invoke(final PacketEvent.Send<CPacketPlayer> event) {
                CPacketPlayerListener.this.onPacket(event);
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Send<CPacketPlayer.Position>>(PacketEvent.Send.class, priority, CPacketPlayer.Position.class) {
            @Override
            public void invoke(final PacketEvent.Send<CPacketPlayer.Position> event) {
                CPacketPlayerListener.this.onPosition(event);
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Send<CPacketPlayer.Rotation>>(PacketEvent.Send.class, priority, CPacketPlayer.Rotation.class) {
            @Override
            public void invoke(final PacketEvent.Send<CPacketPlayer.Rotation> event) {
                CPacketPlayerListener.this.onRotation(event);
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Send<CPacketPlayer.PositionRotation>>(PacketEvent.Send.class, priority, CPacketPlayer.PositionRotation.class) {
            @Override
            public void invoke(final PacketEvent.Send<CPacketPlayer.PositionRotation> event) {
                CPacketPlayerListener.this.onPositionRotation(event);
            }
        });
    }
    
    protected abstract void onPacket(final PacketEvent.Send<CPacketPlayer> p0);
    
    protected abstract void onPosition(final PacketEvent.Send<CPacketPlayer.Position> p0);
    
    protected abstract void onRotation(final PacketEvent.Send<CPacketPlayer.Rotation> p0);
    
    protected abstract void onPositionRotation(final PacketEvent.Send<CPacketPlayer.PositionRotation> p0);
}
