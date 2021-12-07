// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.listeners;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.api.event.bus.*;

public abstract class SPacketEntityListener extends SubscriberImpl implements Globals
{
    public SPacketEntityListener() {
        this(10);
    }
    
    public SPacketEntityListener(final int priority) {
        this.listeners.add(new EventListener<PacketEvent.Receive<SPacketEntity>>(PacketEvent.Receive.class, priority, SPacketEntity.class) {
            @Override
            public void invoke(final PacketEvent.Receive<SPacketEntity> event) {
                SPacketEntityListener.this.onPacket(event);
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Receive<SPacketEntity.S15PacketEntityRelMove>>(PacketEvent.Receive.class, priority, SPacketEntity.S15PacketEntityRelMove.class) {
            @Override
            public void invoke(final PacketEvent.Receive<SPacketEntity.S15PacketEntityRelMove> event) {
                SPacketEntityListener.this.onPosition(event);
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Receive<SPacketEntity.S16PacketEntityLook>>(PacketEvent.Receive.class, priority, SPacketEntity.S16PacketEntityLook.class) {
            @Override
            public void invoke(final PacketEvent.Receive<SPacketEntity.S16PacketEntityLook> event) {
                SPacketEntityListener.this.onRotation(event);
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Receive<SPacketEntity.S17PacketEntityLookMove>>(PacketEvent.Receive.class, priority, SPacketEntity.S17PacketEntityLookMove.class) {
            @Override
            public void invoke(final PacketEvent.Receive<SPacketEntity.S17PacketEntityLookMove> event) {
                SPacketEntityListener.this.onPositionRotation(event);
            }
        });
    }
    
    protected abstract void onPacket(final PacketEvent.Receive<SPacketEntity> p0);
    
    protected abstract void onPosition(final PacketEvent.Receive<SPacketEntity.S15PacketEntityRelMove> p0);
    
    protected abstract void onRotation(final PacketEvent.Receive<SPacketEntity.S16PacketEntityLook> p0);
    
    protected abstract void onPositionRotation(final PacketEvent.Receive<SPacketEntity.S17PacketEntityLookMove> p0);
}
