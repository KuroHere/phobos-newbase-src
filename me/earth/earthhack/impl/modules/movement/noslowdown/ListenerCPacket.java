// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.noslowdown;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;

final class ListenerCPacket extends CPacketPlayerListener
{
    private final NoSlowDown module;
    
    public ListenerCPacket(final NoSlowDown module) {
        this.module = module;
    }
    
    @Override
    protected void onPacket(final PacketEvent.Send<CPacketPlayer> event) {
        this.module.onPacket(event.getPacket());
    }
    
    @Override
    protected void onPosition(final PacketEvent.Send<CPacketPlayer.Position> event) {
        this.module.onPacket(event.getPacket());
    }
    
    @Override
    protected void onRotation(final PacketEvent.Send<CPacketPlayer.Rotation> event) {
        this.module.onPacket(event.getPacket());
    }
    
    @Override
    protected void onPositionRotation(final PacketEvent.Send<CPacketPlayer.PositionRotation> event) {
        this.module.onPacket(event.getPacket());
    }
}
