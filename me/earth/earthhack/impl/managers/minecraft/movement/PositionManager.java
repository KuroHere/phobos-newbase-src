//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft.movement;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.api.event.bus.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;

public class PositionManager extends SubscriberImpl implements Globals
{
    private boolean blocking;
    private volatile int teleportID;
    private volatile double last_x;
    private volatile double last_y;
    private volatile double last_z;
    private volatile boolean onGround;
    
    public PositionManager() {
        this.listeners.add(new EventListener<PacketEvent.Receive<SPacketPlayerPosLook>>(PacketEvent.Receive.class, Integer.MIN_VALUE, SPacketPlayerPosLook.class) {
            @Override
            public void invoke(final PacketEvent.Receive<SPacketPlayerPosLook> event) {
                final SPacketPlayerPosLook packet = event.getPacket();
                double x = packet.getX();
                double y = packet.getY();
                double z = packet.getZ();
                if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.X)) {
                    x += Globals.mc.player.posX;
                }
                if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Y)) {
                    y += Globals.mc.player.posY;
                }
                if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Z)) {
                    z += Globals.mc.player.posZ;
                }
                PositionManager.this.last_x = MathHelper.clamp(x, -3.0E7, 3.0E7);
                PositionManager.this.last_y = y;
                PositionManager.this.last_z = MathHelper.clamp(z, -3.0E7, 3.0E7);
                PositionManager.this.onGround = false;
                PositionManager.this.teleportID = packet.getTeleportId();
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Post<CPacketPlayer.Position>>(PacketEvent.Post.class, Integer.MIN_VALUE, CPacketPlayer.Position.class) {
            @Override
            public void invoke(final PacketEvent.Post<CPacketPlayer.Position> event) {
                PositionManager.this.readCPacket(event.getPacket());
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Post<CPacketPlayer.PositionRotation>>(PacketEvent.Post.class, Integer.MIN_VALUE, CPacketPlayer.PositionRotation.class) {
            @Override
            public void invoke(final PacketEvent.Post<CPacketPlayer.PositionRotation> event) {
                PositionManager.this.readCPacket(event.getPacket());
            }
        });
    }
    
    public int getTeleportID() {
        return this.teleportID;
    }
    
    public double getX() {
        return this.last_x;
    }
    
    public double getY() {
        return this.last_y;
    }
    
    public double getZ() {
        return this.last_z;
    }
    
    public boolean isOnGround() {
        return this.onGround;
    }
    
    public AxisAlignedBB getBB() {
        final double x = this.last_x;
        final double y = this.last_y;
        final double z = this.last_z;
        final float w = PositionManager.mc.player.width / 2.0f;
        final float h = PositionManager.mc.player.height;
        return new AxisAlignedBB(x - w, y, z - w, x + w, y + h, z + w);
    }
    
    public Vec3d getVec() {
        return new Vec3d(this.last_x, this.last_y, this.last_z);
    }
    
    public void readCPacket(final CPacketPlayer packetIn) {
        this.last_x = packetIn.getX(PositionManager.mc.player.posX);
        this.last_y = packetIn.getY(PositionManager.mc.player.posY);
        this.last_z = packetIn.getZ(PositionManager.mc.player.posZ);
        this.setOnGround(packetIn.isOnGround());
    }
    
    public double getDistanceSq(final Entity entity) {
        return this.getDistanceSq(entity.posX, entity.posY, entity.posZ);
    }
    
    public double getDistanceSq(final double x, final double y, final double z) {
        final double xDiff = this.last_x - x;
        final double yDiff = this.last_y - y;
        final double zDiff = this.last_z - z;
        return xDiff * xDiff + yDiff * yDiff + zDiff * zDiff;
    }
    
    public boolean canEntityBeSeen(final Entity entity) {
        return PositionManager.mc.world.rayTraceBlocks(new Vec3d(this.last_x, this.last_y + PositionManager.mc.player.getEyeHeight(), this.last_z), new Vec3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ), false, true, false) == null;
    }
    
    public void set(final double x, final double y, final double z) {
        this.last_x = x;
        this.last_y = y;
        this.last_z = z;
    }
    
    public void setOnGround(final boolean onGround) {
        this.onGround = onGround;
    }
    
    public void setBlocking(final boolean blocking) {
        this.blocking = blocking;
    }
    
    public boolean isBlocking() {
        return this.blocking;
    }
}
