//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.packets;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.core.mixins.network.server.*;
import net.minecraft.entity.*;

final class ListenerEntity extends SPacketEntityListener
{
    private final Packets packets;
    
    public ListenerEntity(final Packets packets) {
        super(-2147483647);
        this.packets = packets;
    }
    
    @Override
    protected void onPacket(final PacketEvent.Receive<SPacketEntity> event) {
        this.onEvent(event);
    }
    
    @Override
    protected void onPosition(final PacketEvent.Receive<SPacketEntity.S15PacketEntityRelMove> event) {
        this.onEvent((PacketEvent.Receive<? extends SPacketEntity>)event);
    }
    
    @Override
    protected void onRotation(final PacketEvent.Receive<SPacketEntity.S16PacketEntityLook> event) {
        this.onEvent((PacketEvent.Receive<? extends SPacketEntity>)event);
    }
    
    @Override
    protected void onPositionRotation(final PacketEvent.Receive<SPacketEntity.S17PacketEntityLookMove> event) {
        this.onEvent((PacketEvent.Receive<? extends SPacketEntity>)event);
    }
    
    private void onEvent(final PacketEvent.Receive<? extends SPacketEntity> event) {
        if (event.isCancelled() || !this.packets.fastEntities.getValue()) {
            return;
        }
        final SPacketEntity packet = event.getPacket();
        final Entity e = Managers.ENTITIES.getEntity(((ISPacketEntity)packet).getEntityId());
        if (e == null) {
            return;
        }
        event.setCancelled(true);
        final Entity entity = e;
        entity.serverPosX += packet.getX();
        final Entity entity2 = e;
        entity2.serverPosY += packet.getY();
        final Entity entity3 = e;
        entity3.serverPosZ += packet.getZ();
        final double x = e.serverPosX / 4096.0;
        final double y = e.serverPosY / 4096.0;
        final double z = e.serverPosZ / 4096.0;
        if (!e.canPassengerSteer()) {
            final float yaw = packet.isRotating() ? (packet.getYaw() * 360 / 256.0f) : e.rotationYaw;
            final float pitch = packet.isRotating() ? (packet.getPitch() * 360 / 256.0f) : e.rotationPitch;
            e.setPositionAndRotationDirect(x, y, z, yaw, pitch, 3, false);
            e.onGround = packet.getOnGround();
        }
    }
}
