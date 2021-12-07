// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft.movement;

import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import java.util.*;
import me.earth.earthhack.api.event.bus.api.*;

public class NoMotionUpdateService extends SubscriberImpl
{
    private boolean awaiting;
    
    public NoMotionUpdateService() {
        this.listeners.add(new EventListener<MotionUpdateEvent>(MotionUpdateEvent.class, Integer.MIN_VALUE) {
            @Override
            public void invoke(final MotionUpdateEvent event) {
                if (event.isCancelled()) {
                    return;
                }
                if (event.getStage() == Stage.PRE) {
                    NoMotionUpdateService.this.setAwaiting(true);
                }
                else {
                    if (NoMotionUpdateService.this.isAwaiting()) {
                        Bus.EVENT_BUS.post(new NoMotionUpdateEvent());
                    }
                    NoMotionUpdateService.this.setAwaiting(false);
                }
            }
        });
        this.listeners.addAll(new CPacketPlayerListener() {
            @Override
            protected void onPacket(final PacketEvent.Send<CPacketPlayer> event) {
                NoMotionUpdateService.this.setAwaiting(false);
            }
            
            @Override
            protected void onPosition(final PacketEvent.Send<CPacketPlayer.Position> event) {
                NoMotionUpdateService.this.setAwaiting(false);
            }
            
            @Override
            protected void onRotation(final PacketEvent.Send<CPacketPlayer.Rotation> event) {
                NoMotionUpdateService.this.setAwaiting(false);
            }
            
            @Override
            protected void onPositionRotation(final PacketEvent.Send<CPacketPlayer.PositionRotation> event) {
                NoMotionUpdateService.this.setAwaiting(false);
            }
        }.getListeners());
    }
    
    public void setAwaiting(final boolean awaiting) {
        this.awaiting = awaiting;
    }
    
    public boolean isAwaiting() {
        return this.awaiting;
    }
}
