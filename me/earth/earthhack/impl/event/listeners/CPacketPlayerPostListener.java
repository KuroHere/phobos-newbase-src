// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.listeners;

import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.api.event.bus.*;

public abstract class CPacketPlayerPostListener extends SubscriberImpl
{
    public CPacketPlayerPostListener() {
        this(10);
    }
    
    public CPacketPlayerPostListener(final int priority) {
        this.listeners.add(new EventListener<PacketEvent.Post<CPacketPlayer>>(PacketEvent.Post.class, priority, CPacketPlayer.class) {
            @Override
            public void invoke(final PacketEvent.Post<CPacketPlayer> event) {
                CPacketPlayerPostListener.this.onPacket(event);
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Post<CPacketPlayer.Position>>(PacketEvent.Post.class, priority, CPacketPlayer.Position.class) {
            @Override
            public void invoke(final PacketEvent.Post<CPacketPlayer.Position> event) {
                CPacketPlayerPostListener.this.onPosition(event);
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Post<CPacketPlayer.Rotation>>(PacketEvent.Post.class, priority, CPacketPlayer.Rotation.class) {
            @Override
            public void invoke(final PacketEvent.Post<CPacketPlayer.Rotation> event) {
                CPacketPlayerPostListener.this.onRotation(event);
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Post<CPacketPlayer.PositionRotation>>(PacketEvent.Post.class, priority, CPacketPlayer.PositionRotation.class) {
            @Override
            public void invoke(final PacketEvent.Post<CPacketPlayer.PositionRotation> event) {
                CPacketPlayerPostListener.this.onPositionRotation(event);
            }
        });
    }
    
    protected abstract void onPacket(final PacketEvent.Post<CPacketPlayer> p0);
    
    protected abstract void onPosition(final PacketEvent.Post<CPacketPlayer.Position> p0);
    
    protected abstract void onRotation(final PacketEvent.Post<CPacketPlayer.Rotation> p0);
    
    protected abstract void onPositionRotation(final PacketEvent.Post<CPacketPlayer.PositionRotation> p0);
}
