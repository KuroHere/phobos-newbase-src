//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.speed;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.client.entity.*;

final class ListenerVelocity extends ModuleListener<Speed, PacketEvent.Receive<SPacketEntityVelocity>>
{
    public ListenerVelocity(final Speed module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketEntityVelocity.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketEntityVelocity> event) {
        final SPacketEntityVelocity packet = event.getPacket();
        final EntityPlayerSP player = ListenerVelocity.mc.player;
        if (player != null && packet.getEntityID() == player.getEntityId() && !((Speed)this.module).directional.getValue() && ((Speed)this.module).velocity.getValue()) {
            final double speed = Math.sqrt(packet.getMotionX() * packet.getMotionX() + packet.getMotionZ() * packet.getMotionZ()) / 8000.0;
            ((Speed)this.module).lastExp = (((Speed)this.module).expTimer.passed(((Speed)this.module).coolDown.getValue()) ? speed : (speed - ((Speed)this.module).lastExp));
            if (((Speed)this.module).lastExp > 0.0) {
                ((Speed)this.module).expTimer.reset();
                ListenerVelocity.mc.addScheduledTask(() -> {
                    final Speed speed2 = (Speed)this.module;
                    speed2.speed += ((Speed)this.module).lastExp * ((Speed)this.module).multiplier.getValue();
                    final Speed speed3 = (Speed)this.module;
                    speed3.distance += ((Speed)this.module).lastExp * ((Speed)this.module).multiplier.getValue();
                    if (ListenerVelocity.mc.player.motionY > 0.0 && ((Speed)this.module).vertical.getValue() != 0.0f) {
                        final EntityPlayerSP player = ListenerVelocity.mc.player;
                        player.motionY *= ((Speed)this.module).vertical.getValue();
                    }
                });
            }
        }
    }
}
