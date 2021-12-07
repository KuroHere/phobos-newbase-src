//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.timer;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.modules.player.timer.mode.*;
import me.earth.earthhack.impl.util.minecraft.*;

final class ListenerPlayerPackets extends CPacketPlayerListener implements Globals
{
    private final Timer timer;
    
    public ListenerPlayerPackets(final Timer timer) {
        this.timer = timer;
    }
    
    @Override
    protected void onPacket(final PacketEvent.Send<CPacketPlayer> event) {
        this.onEvent(event);
    }
    
    @Override
    protected void onPosition(final PacketEvent.Send<CPacketPlayer.Position> event) {
        if (!Managers.POSITION.isBlocking()) {
            this.onEvent(event);
        }
    }
    
    @Override
    protected void onRotation(final PacketEvent.Send<CPacketPlayer.Rotation> event) {
        if (!Managers.ROTATION.isBlocking()) {
            this.onEvent(event);
        }
    }
    
    @Override
    protected void onPositionRotation(final PacketEvent.Send<CPacketPlayer.PositionRotation> event) {
        if (!Managers.ROTATION.isBlocking() && !Managers.POSITION.isBlocking()) {
            this.onEvent(event);
        }
    }
    
    private void onEvent(final PacketEvent<?> event) {
        if (this.timer.mode.getValue() == TimerMode.Blink && Managers.NCP.passed(this.timer.lagTime.getValue())) {
            if (this.timer.packets != 0 && this.timer.letThrough.getValue() != 0 && this.timer.packets % this.timer.letThrough.getValue() == 0) {
                final Timer timer = this.timer;
                ++timer.packets;
                return;
            }
            if (MovementUtil.noMovementKeys() && ListenerPlayerPackets.mc.player.motionX < 0.001 && ListenerPlayerPackets.mc.player.motionY < 0.001 && ListenerPlayerPackets.mc.player.motionZ < 0.001) {
                event.setCancelled(true);
                this.timer.pSpeed = 1.0f;
                final Timer timer2 = this.timer;
                ++timer2.packets;
                return;
            }
            if (this.timer.packets > this.timer.offset.getValue() && this.timer.sent < this.timer.maxPackets.getValue()) {
                this.timer.pSpeed = this.timer.speed.getValue();
                final Timer timer3 = this.timer;
                --timer3.packets;
                final Timer timer4 = this.timer;
                ++timer4.sent;
                return;
            }
        }
        this.timer.pSpeed = 1.0f;
        this.timer.sent = 0;
        this.timer.packets = 0;
    }
}
