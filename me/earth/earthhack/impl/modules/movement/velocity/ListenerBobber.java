//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.velocity;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.network.play.*;
import net.minecraft.entity.*;

final class ListenerBobber extends ModuleListener<Velocity, PacketEvent.Receive<SPacketEntityStatus>>
{
    public ListenerBobber(final Velocity module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, Integer.MIN_VALUE, SPacketEntityStatus.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketEntityStatus> event) {
        if (((Velocity)this.module).bobbers.getValue()) {
            final SPacketEntityStatus packet = event.getPacket();
            if (packet.getOpCode() == 31 && !event.isCancelled()) {
                event.setCancelled(true);
                ListenerBobber.mc.addScheduledTask(() -> {
                    if (ListenerBobber.mc.getConnection() != null) {
                        final Entity entity = packet.getEntity((World)ListenerBobber.mc.world);
                        if (entity instanceof EntityFishHook) {
                            final EntityFishHook fishHook = (EntityFishHook)entity;
                            if (fishHook.caughtEntity != null && ListenerBobber.mc.getConnection() != null && !fishHook.caughtEntity.equals((Object)ListenerBobber.mc.player)) {
                                packet.processPacket((INetHandlerPlayClient)ListenerBobber.mc.getConnection());
                            }
                        }
                        else {
                            packet.processPacket((INetHandlerPlayClient)ListenerBobber.mc.getConnection());
                        }
                    }
                });
            }
        }
    }
}
