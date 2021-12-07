//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.speed;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.util.math.*;
import net.minecraft.client.entity.*;

final class ListenerExplosion extends ModuleListener<Speed, PacketEvent.Receive<SPacketExplosion>>
{
    public ListenerExplosion(final Speed module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketExplosion.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketExplosion> event) {
        if (((Speed)this.module).explosions.getValue() && MovementUtil.isMoving() && Managers.NCP.passed(((Speed)this.module).lagTime.getValue())) {
            final SPacketExplosion packet = event.getPacket();
            final BlockPos pos = new BlockPos(packet.getX(), packet.getY(), packet.getZ());
            if (ListenerExplosion.mc.player.getDistanceSq(pos) < 100.0 && (!((Speed)this.module).directional.getValue() || !MovementUtil.isInMovementDirection(packet.getX(), packet.getY(), packet.getZ()))) {
                final double speed = Math.sqrt(packet.getMotionX() * packet.getMotionX() + packet.getMotionZ() * packet.getMotionZ());
                ((Speed)this.module).lastExp = (((Speed)this.module).expTimer.passed(((Speed)this.module).coolDown.getValue()) ? speed : (speed - ((Speed)this.module).lastExp));
                if (((Speed)this.module).lastExp > 0.0) {
                    ((Speed)this.module).expTimer.reset();
                    ListenerExplosion.mc.addScheduledTask(() -> {
                        final Speed speed2 = (Speed)this.module;
                        speed2.speed += ((Speed)this.module).lastExp * ((Speed)this.module).multiplier.getValue();
                        final Speed speed3 = (Speed)this.module;
                        speed3.distance += ((Speed)this.module).lastExp * ((Speed)this.module).multiplier.getValue();
                        if (ListenerExplosion.mc.player.motionY > 0.0) {
                            final EntityPlayerSP player = ListenerExplosion.mc.player;
                            player.motionY *= ((Speed)this.module).vertical.getValue();
                        }
                    });
                }
            }
        }
    }
}
