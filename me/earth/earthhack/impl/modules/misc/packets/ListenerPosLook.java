//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.packets;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.movement.packetfly.*;
import me.earth.earthhack.impl.modules.player.freecam.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerPosLook extends ModuleListener<Packets, PacketEvent.Receive<SPacketPlayerPosLook>>
{
    private static final ModuleCache<PacketFly> PACKET_FLY;
    private static final ModuleCache<Freecam> FREE_CAM;
    
    public ListenerPosLook(final Packets module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, -1000, SPacketPlayerPosLook.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketPlayerPosLook> event) {
        if (event.isCancelled() || ListenerPosLook.mc.player == null || ListenerPosLook.PACKET_FLY.isEnabled() || ListenerPosLook.FREE_CAM.isEnabled() || !((Packets)this.module).fastTeleports.getValue()) {
            return;
        }
        event.setCancelled(true);
        final SPacketPlayerPosLook packet = event.getPacket();
        final boolean xFlag = packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.X);
        final boolean yFlag = packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Y);
        final boolean zFlag = packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Z);
        final boolean yawFlag = packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Y_ROT);
        final boolean pitFlag = packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.X_ROT);
        final double x = packet.getX() + (xFlag ? ListenerPosLook.mc.player.posX : 0.0);
        final double y = packet.getY() + (yFlag ? ListenerPosLook.mc.player.posY : 0.0);
        final double z = packet.getZ() + (zFlag ? ListenerPosLook.mc.player.posZ : 0.0);
        final float yaw = packet.getYaw() + (yawFlag ? ListenerPosLook.mc.player.rotationYaw : 0.0f);
        final float pit = packet.getPitch() + (pitFlag ? ListenerPosLook.mc.player.rotationPitch : 0.0f);
        ListenerPosLook.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(packet.getTeleportId()));
        Managers.ROTATION.setBlocking(true);
        ListenerPosLook.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(MathHelper.clamp(x, -3.0E7, 3.0E7), y, MathHelper.clamp(z, -3.0E7, 3.0E7), yaw, pit, false));
        Managers.ROTATION.setBlocking(false);
        if (((Packets)this.module).asyncTeleports.getValue()) {
            this.execute(x, y, z, yaw, pit, xFlag, yFlag, zFlag);
        }
        ListenerPosLook.mc.addScheduledTask(() -> this.execute(x, y, z, yaw, pit, xFlag, yFlag, zFlag));
        PacketUtil.loadTerrain();
    }
    
    private void execute(final double x, final double y, final double z, final float yaw, final float pitch, final boolean xFlag, final boolean yFlag, final boolean zFlag) {
        if (!xFlag) {
            ListenerPosLook.mc.player.motionX = 0.0;
        }
        if (!yFlag) {
            ListenerPosLook.mc.player.motionY = 0.0;
        }
        if (!zFlag) {
            ListenerPosLook.mc.player.motionZ = 0.0;
        }
        ListenerPosLook.mc.player.setPositionAndRotation(x, y, z, yaw, pitch);
    }
    
    static {
        PACKET_FLY = Caches.getModule(PacketFly.class);
        FREE_CAM = Caches.getModule(Freecam.class);
    }
}
