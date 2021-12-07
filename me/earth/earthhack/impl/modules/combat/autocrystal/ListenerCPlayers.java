// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;

final class ListenerCPlayers extends CPacketPlayerListener
{
    private final AutoCrystal module;
    
    public ListenerCPlayers(final AutoCrystal module) {
        this.module = module;
    }
    
    @Override
    protected void onPacket(final PacketEvent.Send<CPacketPlayer> event) {
        this.update(event);
    }
    
    @Override
    protected void onPosition(final PacketEvent.Send<CPacketPlayer.Position> event) {
        this.update((PacketEvent.Send<? extends CPacketPlayer>)event);
    }
    
    @Override
    protected void onRotation(final PacketEvent.Send<CPacketPlayer.Rotation> event) {
        this.update((PacketEvent.Send<? extends CPacketPlayer>)event);
    }
    
    @Override
    protected void onPositionRotation(final PacketEvent.Send<CPacketPlayer.PositionRotation> event) {
        this.update((PacketEvent.Send<? extends CPacketPlayer>)event);
    }
    
    private void update(final PacketEvent.Send<? extends CPacketPlayer> event) {
        if (this.module.multiThread.getValue() && !this.module.isSpoofing && this.module.rotate.getValue() != ACRotate.None && this.module.rotationThread.getValue() == RotationThread.Cancel) {
            this.module.rotationCanceller.onPacket(event);
        }
        else {
            this.module.rotationCanceller.reset();
        }
    }
}
