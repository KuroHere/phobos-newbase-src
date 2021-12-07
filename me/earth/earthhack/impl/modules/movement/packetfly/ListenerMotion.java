//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.packetfly;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.modules.movement.packetfly.util.*;

final class ListenerMotion extends ModuleListener<PacketFly, MotionUpdateEvent>
{
    public ListenerMotion(final PacketFly module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.PRE) {
            ListenerMotion.mc.player.motionX = 0.0;
            ListenerMotion.mc.player.motionY = 0.0;
            ListenerMotion.mc.player.motionZ = 0.0;
            if (((PacketFly)this.module).mode.getValue() != Mode.Setback && ((PacketFly)this.module).teleportID.get() == 0) {
                if (((PacketFly)this.module).checkPackets(6)) {
                    ((PacketFly)this.module).sendPackets(0.0, 0.0, 0.0, true);
                }
                return;
            }
            final boolean isPhasing = ((PacketFly)this.module).isPlayerCollisionBoundingBoxEmpty();
            double ySpeed;
            if (ListenerMotion.mc.player.movementInput.jump && (isPhasing || !MovementUtil.isMoving())) {
                if (((PacketFly)this.module).antiKick.getValue() && !isPhasing) {
                    ySpeed = (((PacketFly)this.module).checkPackets((((PacketFly)this.module).mode.getValue() == Mode.Setback) ? 10 : 20) ? -0.032 : 0.062);
                }
                else {
                    ySpeed = ((((PacketFly)this.module).yJitter.getValue() && ((PacketFly)this.module).zoomies) ? 0.061 : 0.062);
                }
            }
            else if (ListenerMotion.mc.player.movementInput.sneak) {
                ySpeed = ((((PacketFly)this.module).yJitter.getValue() && ((PacketFly)this.module).zoomies) ? -0.061 : -0.062);
            }
            else {
                ySpeed = (isPhasing ? 0.0 : (((PacketFly)this.module).checkPackets(4) ? (((PacketFly)this.module).antiKick.getValue() ? -0.04 : 0.0) : 0.0));
            }
            if (((PacketFly)this.module).phase.getValue() == Phase.Full && isPhasing && MovementUtil.isMoving() && ySpeed != 0.0) {
                ySpeed /= 2.5;
            }
            final double high = (((PacketFly)this.module).xzJitter.getValue() && ((PacketFly)this.module).zoomies) ? 0.25 : 0.26;
            final double low = (((PacketFly)this.module).xzJitter.getValue() && ((PacketFly)this.module).zoomies) ? 0.03 : 0.031;
            final double[] dirSpeed = MovementUtil.strafe((((PacketFly)this.module).phase.getValue() == Phase.Full && isPhasing) ? low : high);
            if (((PacketFly)this.module).mode.getValue() == Mode.Increment) {
                if (((PacketFly)this.module).lastFactor >= ((PacketFly)this.module).factor.getValue()) {
                    ((PacketFly)this.module).lastFactor = 1.0f;
                }
                else {
                    final PacketFly packetFly = (PacketFly)this.module;
                    final float lastFactor = packetFly.lastFactor + 1.0f;
                    packetFly.lastFactor = lastFactor;
                    if (lastFactor > ((PacketFly)this.module).factor.getValue()) {
                        ((PacketFly)this.module).lastFactor = ((PacketFly)this.module).factor.getValue();
                    }
                }
            }
            else {
                ((PacketFly)this.module).lastFactor = ((PacketFly)this.module).factor.getValue();
            }
            for (int i = 1; i <= ((((PacketFly)this.module).mode.getValue() == Mode.Factor || ((PacketFly)this.module).mode.getValue() == Mode.Slow || ((PacketFly)this.module).mode.getValue() == Mode.Increment) ? ((PacketFly)this.module).lastFactor : 1.0f); ++i) {
                final double conceal = (ListenerMotion.mc.player.posY < ((PacketFly)this.module).concealY.getValue() && !MovementUtil.noMovementKeys()) ? ((PacketFly)this.module).conceal.getValue() : 1.0;
                ListenerMotion.mc.player.motionX = dirSpeed[0] * i * conceal * ((PacketFly)this.module).xzSpeed.getValue();
                ListenerMotion.mc.player.motionY = ySpeed * i * ((PacketFly)this.module).ySpeed.getValue();
                ListenerMotion.mc.player.motionZ = dirSpeed[1] * i * conceal * ((PacketFly)this.module).xzSpeed.getValue();
                ((PacketFly)this.module).sendPackets(ListenerMotion.mc.player.motionX, ListenerMotion.mc.player.motionY, ListenerMotion.mc.player.motionZ, ((PacketFly)this.module).mode.getValue() != Mode.Setback);
            }
            final PacketFly packetFly2 = (PacketFly)this.module;
            ++packetFly2.zoomTimer;
            if (((PacketFly)this.module).zoomTimer > ((PacketFly)this.module).zoomer.getValue()) {
                ((PacketFly)this.module).zoomies = !((PacketFly)this.module).zoomies;
                ((PacketFly)this.module).zoomTimer = 0;
            }
        }
    }
}
