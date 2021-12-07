//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.packets;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.*;

final class ListenerEntityTeleport extends ModuleListener<Packets, PacketEvent.Receive<SPacketEntityTeleport>>
{
    public ListenerEntityTeleport(final Packets module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, Integer.MIN_VALUE, SPacketEntityTeleport.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketEntityTeleport> event) {
        if (event.isCancelled() || !((Packets)this.module).fastEntityTeleport.getValue()) {
            return;
        }
        final SPacketEntityTeleport packet = event.getPacket();
        final Entity e = Managers.ENTITIES.getEntity(packet.getEntityId());
        if (e == null) {
            return;
        }
        event.setCancelled(((Packets)this.module).cancelEntityTeleport.getValue());
        final double x = packet.getX();
        final double y = packet.getY();
        final double z = packet.getZ();
        EntityTracker.updateServerPosition(e, x, y, z);
        if (!e.canPassengerSteer()) {
            final float yaw = packet.getYaw() * 360 / 256.0f;
            final float pitch = packet.getPitch() * 360 / 256.0f;
            if (Math.abs(e.posX - x) < 0.03125 && Math.abs(e.posY - y) < 0.015625 && Math.abs(e.posZ - z) < 0.03125) {
                if (((Packets)this.module).miniTeleports.getValue() && ((Packets)this.module).cancelEntityTeleport.getValue()) {
                    e.setPositionAndRotation(x, y, z, yaw, pitch);
                    return;
                }
                e.setPositionAndRotationDirect(e.posX, e.posY, e.posZ, yaw, pitch, 0, true);
            }
            else {
                e.setPositionAndRotationDirect(x, y, z, yaw, pitch, 3, true);
            }
            e.onGround = packet.getOnGround();
        }
    }
}
