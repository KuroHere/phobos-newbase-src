//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft.movement;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import net.minecraft.util.math.*;

public class RotationManager extends SubscriberImpl implements Globals
{
    private final PositionManager positionManager;
    private boolean blocking;
    private volatile float last_yaw;
    private volatile float last_pitch;
    private float renderYaw;
    private float renderPitch;
    private float renderYawOffset;
    private float prevYaw;
    private float prevPitch;
    private float prevRenderYawOffset;
    private float prevRotationYawHead;
    private float rotationYawHead;
    private int ticksExisted;
    
    public RotationManager() {
        this(Managers.POSITION);
    }
    
    public RotationManager(final PositionManager positionManager) {
        this.positionManager = positionManager;
        this.listeners.add(new EventListener<PacketEvent.Receive<SPacketPlayerPosLook>>(PacketEvent.Receive.class, Integer.MAX_VALUE, SPacketPlayerPosLook.class) {
            @Override
            public void invoke(final PacketEvent.Receive<SPacketPlayerPosLook> event) {
                final SPacketPlayerPosLook packet = event.getPacket();
                float yaw = packet.getYaw();
                float pitch = packet.getPitch();
                if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.X_ROT)) {
                    yaw += Globals.mc.player.rotationYaw;
                }
                if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Y_ROT)) {
                    pitch += Globals.mc.player.rotationPitch;
                }
                if (Globals.mc.player != null) {
                    RotationManager.this.setServerRotations(yaw, pitch);
                }
            }
        });
        this.listeners.add(new EventListener<MotionUpdateEvent>(MotionUpdateEvent.class, Integer.MIN_VALUE) {
            @Override
            public void invoke(final MotionUpdateEvent event) {
                if (event.getStage() == Stage.PRE) {
                    RotationManager.this.set(event.getYaw(), event.getPitch());
                }
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Post<CPacketPlayer>>(PacketEvent.Post.class, CPacketPlayer.class) {
            @Override
            public void invoke(final PacketEvent.Post<CPacketPlayer> event) {
                RotationManager.this.readCPacket(event.getPacket());
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Post<CPacketPlayer.Position>>(PacketEvent.Post.class, CPacketPlayer.Position.class) {
            @Override
            public void invoke(final PacketEvent.Post<CPacketPlayer.Position> event) {
                RotationManager.this.readCPacket(event.getPacket());
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Post<CPacketPlayer.Rotation>>(PacketEvent.Post.class, CPacketPlayer.Rotation.class) {
            @Override
            public void invoke(final PacketEvent.Post<CPacketPlayer.Rotation> event) {
                RotationManager.this.readCPacket(event.getPacket());
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Post<CPacketPlayer.PositionRotation>>(PacketEvent.Post.class, CPacketPlayer.PositionRotation.class) {
            @Override
            public void invoke(final PacketEvent.Post<CPacketPlayer.PositionRotation> event) {
                RotationManager.this.readCPacket(event.getPacket());
            }
        });
    }
    
    public float getServerYaw() {
        return this.last_yaw;
    }
    
    public float getServerPitch() {
        return this.last_pitch;
    }
    
    public void setBlocking(final boolean blocking) {
        this.blocking = blocking;
    }
    
    public boolean isBlocking() {
        return this.blocking;
    }
    
    public void setServerRotations(final float yaw, final float pitch) {
        this.last_yaw = yaw;
        this.last_pitch = pitch;
    }
    
    public void readCPacket(final CPacketPlayer packetIn) {
        ((IEntityPlayerSP)RotationManager.mc.player).setLastReportedYaw(packetIn.getYaw(((IEntityPlayerSP)RotationManager.mc.player).getLastReportedYaw()));
        ((IEntityPlayerSP)RotationManager.mc.player).setLastReportedPitch(packetIn.getPitch(((IEntityPlayerSP)RotationManager.mc.player).getLastReportedPitch()));
        this.setServerRotations(packetIn.getYaw(this.last_yaw), packetIn.getPitch(this.last_pitch));
        this.positionManager.setOnGround(packetIn.isOnGround());
    }
    
    private void set(final float yaw, final float pitch) {
        if (RotationManager.mc.player.ticksExisted == this.ticksExisted) {
            return;
        }
        this.ticksExisted = RotationManager.mc.player.ticksExisted;
        this.prevYaw = this.renderYaw;
        this.prevPitch = this.renderPitch;
        this.prevRenderYawOffset = this.renderYawOffset;
        this.renderYawOffset = this.getRenderYawOffset(yaw, this.prevRenderYawOffset);
        this.prevRotationYawHead = this.rotationYawHead;
        this.rotationYawHead = yaw;
        this.renderYaw = yaw;
        this.renderPitch = pitch;
    }
    
    public float getRenderYaw() {
        return this.renderYaw;
    }
    
    public float getRenderPitch() {
        return this.renderPitch;
    }
    
    public float getRotationYawHead() {
        return this.rotationYawHead;
    }
    
    public float getRenderYawOffset() {
        return this.renderYawOffset;
    }
    
    public float getPrevYaw() {
        return this.prevYaw;
    }
    
    public float getPrevPitch() {
        return this.prevPitch;
    }
    
    public float getPrevRotationYawHead() {
        return this.prevRotationYawHead;
    }
    
    public float getPrevRenderYawOffset() {
        return this.prevRenderYawOffset;
    }
    
    private float getRenderYawOffset(final float yaw, final float offsetIn) {
        float result = offsetIn;
        final double xDif = RotationManager.mc.player.posX - RotationManager.mc.player.prevPosX;
        final double zDif = RotationManager.mc.player.posZ - RotationManager.mc.player.prevPosZ;
        if (xDif * xDif + zDif * zDif > 0.002500000176951289) {
            final float offset = (float)MathHelper.atan2(zDif, xDif) * 57.295776f - 90.0f;
            final float wrap = MathHelper.abs(MathHelper.wrapDegrees(yaw) - offset);
            if (95.0f < wrap && wrap < 265.0f) {
                result = offset - 180.0f;
            }
            else {
                result = offset;
            }
        }
        if (RotationManager.mc.player.swingProgress > 0.0f) {
            result = yaw;
        }
        result = offsetIn + MathHelper.wrapDegrees(result - offsetIn) * 0.3f;
        float offset = MathHelper.wrapDegrees(yaw - result);
        if (offset < -75.0f) {
            offset = -75.0f;
        }
        else if (offset >= 75.0f) {
            offset = 75.0f;
        }
        result = yaw - offset;
        if (offset * offset > 2500.0f) {
            result += offset * 0.2f;
        }
        return result;
    }
}
