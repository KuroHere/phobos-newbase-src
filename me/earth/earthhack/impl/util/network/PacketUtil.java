//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.network;

import me.earth.earthhack.api.util.interfaces.*;
import java.util.*;
import me.earth.earthhack.impl.core.mixins.network.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.util.math.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.impl.core.ducks.network.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;

public class PacketUtil implements Globals
{
    public static Set<Class<? extends Packet<?>>> getAllPackets() {
        return ((IEnumConnectionState)EnumConnectionState.HANDSHAKING).getStatesByClass().keySet();
    }
    
    public static void handlePosLook(final SPacketPlayerPosLook packetIn, final Entity entity, final boolean noRotate) {
        handlePosLook(packetIn, entity, noRotate, false);
    }
    
    public static void handlePosLook(final SPacketPlayerPosLook packet, final Entity entity, final boolean noRotate, final boolean event) {
        double x = packet.getX();
        double y = packet.getY();
        double z = packet.getZ();
        float yaw = packet.getYaw();
        float pitch = packet.getPitch();
        if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.X)) {
            x += entity.posX;
        }
        else {
            entity.motionX = 0.0;
        }
        if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Y)) {
            y += entity.posY;
        }
        else {
            entity.motionY = 0.0;
        }
        if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Z)) {
            z += entity.posZ;
        }
        else {
            entity.motionZ = 0.0;
        }
        if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.X_ROT)) {
            pitch += entity.rotationPitch;
        }
        if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Y_ROT)) {
            yaw += entity.rotationYaw;
        }
        entity.setPositionAndRotation(x, y, z, noRotate ? entity.rotationYaw : yaw, noRotate ? entity.rotationPitch : pitch);
        final Packet<?> confirm = (Packet<?>)new CPacketConfirmTeleport(packet.getTeleportId());
        final CPacketPlayer posRot = positionRotation(entity.posX, entity.getEntityBoundingBox().minY, entity.posZ, yaw, pitch, false);
        if (event) {
            NetworkUtil.send(confirm);
            Managers.ROTATION.setBlocking(true);
            NetworkUtil.send((Packet<?>)posRot);
            Managers.ROTATION.setBlocking(false);
        }
        else {
            NetworkUtil.sendPacketNoEvent(confirm);
            NetworkUtil.sendPacketNoEvent((Packet<?>)posRot);
        }
        PacketUtil.mc.addScheduledTask(PacketUtil::loadTerrain);
    }
    
    public static void startDigging(final BlockPos pos, final EnumFacing facing) {
        PacketUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, facing));
    }
    
    public static void stopDigging(final BlockPos pos, final EnumFacing facing) {
        PacketUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, facing));
    }
    
    public static void loadTerrain() {
        PacketUtil.mc.addScheduledTask(() -> {
            if (!((INetHandlerPlayClient)PacketUtil.mc.player.connection).isDoneLoadingTerrain()) {
                PacketUtil.mc.player.prevPosX = PacketUtil.mc.player.posX;
                PacketUtil.mc.player.prevPosY = PacketUtil.mc.player.posY;
                PacketUtil.mc.player.prevPosZ = PacketUtil.mc.player.posZ;
                ((INetHandlerPlayClient)PacketUtil.mc.player.connection).setDoneLoadingTerrain(true);
                PacketUtil.mc.displayGuiScreen((GuiScreen)null);
            }
        });
    }
    
    public static CPacketUseEntity attackPacket(final int id) {
        final CPacketUseEntity packet = new CPacketUseEntity();
        ((ICPacketUseEntity)packet).setEntityId(id);
        ((ICPacketUseEntity)packet).setAction(CPacketUseEntity.Action.ATTACK);
        return packet;
    }
    
    public static void sneak(final boolean sneak) {
        PacketUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)PacketUtil.mc.player, sneak ? CPacketEntityAction.Action.START_SNEAKING : CPacketEntityAction.Action.STOP_SNEAKING));
    }
    
    public static void attack(final Entity entity) {
        PacketUtil.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
        PacketUtil.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
    }
    
    public static void attack(final int id) {
        PacketUtil.mc.player.connection.sendPacket((Packet)attackPacket(id));
        PacketUtil.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
    }
    
    public static void swing(final int slot) {
        PacketUtil.mc.player.connection.sendPacket((Packet)new CPacketAnimation(InventoryUtil.getHand(slot)));
    }
    
    public static void place(final BlockPos on, final EnumFacing facing, final int slot, final float x, final float y, final float z) {
        place(on, facing, InventoryUtil.getHand(slot), x, y, z);
    }
    
    public static void place(final BlockPos on, final EnumFacing facing, final EnumHand hand, final float x, final float y, final float z) {
        PacketUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(on, facing, hand, x, y, z));
    }
    
    public static void teleport(final int id) {
        PacketUtil.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(id));
    }
    
    public static void sendAction(final CPacketEntityAction.Action action) {
        PacketUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)PacketUtil.mc.player, action));
    }
    
    public static void click(final int windowIdIn, final int slotIdIn, final int usedButtonIn, final ClickType modeIn, final ItemStack clickedItemIn, final short actionNumberIn) {
        PacketUtil.mc.player.connection.sendPacket((Packet)new CPacketClickWindow(windowIdIn, slotIdIn, usedButtonIn, modeIn, clickedItemIn, actionNumberIn));
    }
    
    public static CPacketPlayer onGround(final boolean onGround) {
        return new CPacketPlayer(onGround);
    }
    
    public static CPacketPlayer position(final double x, final double y, final double z) {
        return position(x, y, z, PacketUtil.mc.player.onGround);
    }
    
    public static CPacketPlayer position(final double x, final double y, final double z, final boolean onGround) {
        return (CPacketPlayer)new CPacketPlayer.Position(x, y, z, onGround);
    }
    
    public static CPacketPlayer rotation(final float yaw, final float pitch, final boolean onGround) {
        return (CPacketPlayer)new CPacketPlayer.Rotation(yaw, pitch, onGround);
    }
    
    public static CPacketPlayer positionRotation(final double x, final double y, final double z, final float yaw, final float pitch, final boolean onGround) {
        return (CPacketPlayer)new CPacketPlayer.PositionRotation(x, y, z, yaw, pitch, onGround);
    }
    
    public static void doOnGround(final boolean onGround) {
        PacketUtil.mc.player.connection.sendPacket((Packet)onGround(onGround));
    }
    
    public static void doY(final double y, final boolean onGround) {
        doY((Entity)PacketUtil.mc.player, y, onGround);
    }
    
    public static void doY(final Entity entity, final double y, final boolean onGround) {
        doPosition(entity.posX, y, entity.posZ, onGround);
    }
    
    public static void doPosition(final double x, final double y, final double z, final boolean onGround) {
        PacketUtil.mc.player.connection.sendPacket((Packet)position(x, y, z, onGround));
    }
    
    public static void doPositionNoEvent(final double x, final double y, final double z, final boolean onGround) {
        NetworkUtil.sendPacketNoEvent((Packet<?>)position(x, y, z, onGround));
    }
    
    public static void doRotation(final float yaw, final float pitch, final boolean onGround) {
        PacketUtil.mc.player.connection.sendPacket((Packet)rotation(yaw, pitch, onGround));
    }
    
    public static void doPosRot(final double x, final double y, final double z, final float yaw, final float pitch, final boolean onGround) {
        PacketUtil.mc.player.connection.sendPacket((Packet)positionRotation(x, y, z, yaw, pitch, onGround));
    }
    
    public static void doPosRotNoEvent(final double x, final double y, final double z, final float yaw, final float pitch, final boolean onGround) {
        NetworkUtil.sendPacketNoEvent((Packet<?>)positionRotation(x, y, z, yaw, pitch, onGround));
    }
}
