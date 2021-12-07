//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.lagometer;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.entity.*;

final class ListenerPosLook extends ModuleListener<LagOMeter, PacketEvent.Receive<SPacketPlayerPosLook>>
{
    public ListenerPosLook(final LagOMeter module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, Integer.MAX_VALUE, SPacketPlayerPosLook.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketPlayerPosLook> event) {
        ((LagOMeter)this.module).teleported.set(false);
        final Entity player = (Entity)RotationUtil.getRotationPlayer();
        if (player == null) {
            return;
        }
        final SPacketPlayerPosLook packet = event.getPacket();
        double x = packet.getX();
        double y = packet.getY();
        double z = packet.getZ();
        float yaw = packet.getYaw();
        float pitch = packet.getPitch();
        if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.X)) {
            x += player.posX;
        }
        if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Y)) {
            y += player.posY;
        }
        if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Z)) {
            z += player.posZ;
        }
        if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.X_ROT)) {
            pitch += player.rotationPitch;
        }
        if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Y_ROT)) {
            yaw += player.rotationYaw;
        }
        ((LagOMeter)this.module).x = x;
        ((LagOMeter)this.module).y = y;
        ((LagOMeter)this.module).z = z;
        ((LagOMeter)this.module).yaw = yaw;
        ((LagOMeter)this.module).pitch = pitch;
    }
}
