//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.network;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.core.ducks.entity.*;

public class PhysicsUtil implements Globals
{
    public static void runPhysicsTick() {
        final int lastSwing = ((IEntityLivingBase)PhysicsUtil.mc.player).getTicksSinceLastSwing();
        final int useCount = ((IEntityLivingBase)PhysicsUtil.mc.player).getActiveItemStackUseCount();
        final int hurtTime = PhysicsUtil.mc.player.hurtTime;
        final float prevSwingProgress = PhysicsUtil.mc.player.prevSwingProgress;
        final float swingProgress = PhysicsUtil.mc.player.swingProgress;
        final int swingProgressInt = PhysicsUtil.mc.player.swingProgressInt;
        final boolean isSwingInProgress = PhysicsUtil.mc.player.isSwingInProgress;
        final float rotationYaw = PhysicsUtil.mc.player.rotationYaw;
        final float prevRotationYaw = PhysicsUtil.mc.player.prevRotationYaw;
        final float renderYawOffset = PhysicsUtil.mc.player.renderYawOffset;
        final float prevRenderYawOffset = PhysicsUtil.mc.player.prevRenderYawOffset;
        final float rotationYawHead = PhysicsUtil.mc.player.rotationYawHead;
        final float prevRotationYawHead = PhysicsUtil.mc.player.prevRotationYawHead;
        final float cameraYaw = PhysicsUtil.mc.player.cameraYaw;
        final float prevCameraYaw = PhysicsUtil.mc.player.prevCameraYaw;
        final float renderArmYaw = PhysicsUtil.mc.player.renderArmYaw;
        final float prevRenderArmYaw = PhysicsUtil.mc.player.prevRenderArmYaw;
        final float renderArmPitch = PhysicsUtil.mc.player.renderArmPitch;
        final float prevRenderArmPitch = PhysicsUtil.mc.player.prevRenderArmPitch;
        final float walk = PhysicsUtil.mc.player.distanceWalkedModified;
        final float prevWalk = PhysicsUtil.mc.player.prevDistanceWalkedModified;
        final double chasingPosX = PhysicsUtil.mc.player.chasingPosX;
        final double prevChasingPosX = PhysicsUtil.mc.player.prevChasingPosX;
        final double chasingPosY = PhysicsUtil.mc.player.chasingPosY;
        final double prevChasingPosY = PhysicsUtil.mc.player.prevChasingPosY;
        final double chasingPosZ = PhysicsUtil.mc.player.chasingPosZ;
        final double prevChasingPosZ = PhysicsUtil.mc.player.prevChasingPosZ;
        final float limbSwingAmount = PhysicsUtil.mc.player.limbSwingAmount;
        final float prevLimbSwingAmount = PhysicsUtil.mc.player.prevLimbSwingAmount;
        final float limbSwing = PhysicsUtil.mc.player.limbSwing;
        ((IEntityPlayerSP)PhysicsUtil.mc.player).superUpdate();
        ((IEntityLivingBase)PhysicsUtil.mc.player).setTicksSinceLastSwing(lastSwing);
        ((IEntityLivingBase)PhysicsUtil.mc.player).setActiveItemStackUseCount(useCount);
        PhysicsUtil.mc.player.hurtTime = hurtTime;
        PhysicsUtil.mc.player.prevSwingProgress = prevSwingProgress;
        PhysicsUtil.mc.player.swingProgress = swingProgress;
        PhysicsUtil.mc.player.swingProgressInt = swingProgressInt;
        PhysicsUtil.mc.player.isSwingInProgress = isSwingInProgress;
        PhysicsUtil.mc.player.rotationYaw = rotationYaw;
        PhysicsUtil.mc.player.prevRotationYaw = prevRotationYaw;
        PhysicsUtil.mc.player.renderYawOffset = renderYawOffset;
        PhysicsUtil.mc.player.prevRenderYawOffset = prevRenderYawOffset;
        PhysicsUtil.mc.player.rotationYawHead = rotationYawHead;
        PhysicsUtil.mc.player.prevRotationYawHead = prevRotationYawHead;
        PhysicsUtil.mc.player.cameraYaw = cameraYaw;
        PhysicsUtil.mc.player.prevCameraYaw = prevCameraYaw;
        PhysicsUtil.mc.player.renderArmYaw = renderArmYaw;
        PhysicsUtil.mc.player.prevRenderArmYaw = prevRenderArmYaw;
        PhysicsUtil.mc.player.renderArmPitch = renderArmPitch;
        PhysicsUtil.mc.player.prevRenderArmPitch = prevRenderArmPitch;
        PhysicsUtil.mc.player.distanceWalkedModified = walk;
        PhysicsUtil.mc.player.prevDistanceWalkedModified = prevWalk;
        PhysicsUtil.mc.player.chasingPosX = chasingPosX;
        PhysicsUtil.mc.player.prevChasingPosX = prevChasingPosX;
        PhysicsUtil.mc.player.chasingPosY = chasingPosY;
        PhysicsUtil.mc.player.prevChasingPosY = prevChasingPosY;
        PhysicsUtil.mc.player.chasingPosZ = chasingPosZ;
        PhysicsUtil.mc.player.prevChasingPosZ = prevChasingPosZ;
        PhysicsUtil.mc.player.limbSwingAmount = limbSwingAmount;
        PhysicsUtil.mc.player.prevLimbSwingAmount = prevLimbSwingAmount;
        PhysicsUtil.mc.player.limbSwing = limbSwing;
        ((IEntityPlayerSP)PhysicsUtil.mc.player).invokeUpdateWalkingPlayer();
    }
}
