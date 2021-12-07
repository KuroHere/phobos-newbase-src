//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft;

import me.earth.earthhack.api.util.interfaces.*;
import org.lwjgl.input.*;
import me.earth.earthhack.impl.event.events.movement.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.util.math.*;

public class MovementUtil implements Globals
{
    public static boolean isMoving() {
        return MovementUtil.mc.player.field_191988_bg != 0.0 || MovementUtil.mc.player.moveStrafing != 0.0;
    }
    
    public static boolean anyMovementKeys() {
        return MovementUtil.mc.player.movementInput.forwardKeyDown || MovementUtil.mc.player.movementInput.backKeyDown || MovementUtil.mc.player.movementInput.leftKeyDown || MovementUtil.mc.player.movementInput.rightKeyDown || MovementUtil.mc.player.movementInput.jump || MovementUtil.mc.player.movementInput.sneak;
    }
    
    public static boolean noMovementKeys() {
        return !MovementUtil.mc.player.movementInput.forwardKeyDown && !MovementUtil.mc.player.movementInput.backKeyDown && !MovementUtil.mc.player.movementInput.rightKeyDown && !MovementUtil.mc.player.movementInput.leftKeyDown;
    }
    
    public static boolean noMovementKeysOrJump() {
        return noMovementKeys() && !Keyboard.isKeyDown(MovementUtil.mc.gameSettings.keyBindJump.getKeyCode());
    }
    
    public static void setMoveSpeed(final double speed) {
        double forward = MovementUtil.mc.player.movementInput.field_192832_b;
        double strafe = MovementUtil.mc.player.movementInput.moveStrafe;
        float yaw = MovementUtil.mc.player.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            MovementUtil.mc.player.motionX = 0.0;
            MovementUtil.mc.player.motionZ = 0.0;
        }
        else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                }
                else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                }
                else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            MovementUtil.mc.player.motionX = forward * speed * -Math.sin(Math.toRadians(yaw)) + strafe * speed * Math.cos(Math.toRadians(yaw));
            MovementUtil.mc.player.motionZ = forward * speed * Math.cos(Math.toRadians(yaw)) - strafe * speed * -Math.sin(Math.toRadians(yaw));
        }
    }
    
    public static void strafe(final MoveEvent event, final double speed) {
        if (isMoving()) {
            final double[] strafe = strafe(speed);
            event.setX(strafe[0]);
            event.setZ(strafe[1]);
        }
        else {
            event.setX(0.0);
            event.setZ(0.0);
        }
    }
    
    public static double[] strafe(final double speed) {
        return strafe((Entity)MovementUtil.mc.player, speed);
    }
    
    public static double[] strafe(final Entity entity, final double speed) {
        return strafe(entity, MovementUtil.mc.player.movementInput, speed);
    }
    
    public static double[] strafe(final Entity entity, final MovementInput movementInput, final double speed) {
        float moveForward = movementInput.field_192832_b;
        float moveStrafe = movementInput.moveStrafe;
        float rotationYaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * MovementUtil.mc.getRenderPartialTicks();
        if (moveForward != 0.0f) {
            if (moveStrafe > 0.0f) {
                rotationYaw += ((moveForward > 0.0f) ? -45 : 45);
            }
            else if (moveStrafe < 0.0f) {
                rotationYaw += ((moveForward > 0.0f) ? 45 : -45);
            }
            moveStrafe = 0.0f;
            if (moveForward > 0.0f) {
                moveForward = 1.0f;
            }
            else if (moveForward < 0.0f) {
                moveForward = -1.0f;
            }
        }
        final double posX = moveForward * speed * -Math.sin(Math.toRadians(rotationYaw)) + moveStrafe * speed * Math.cos(Math.toRadians(rotationYaw));
        final double posZ = moveForward * speed * Math.cos(Math.toRadians(rotationYaw)) - moveStrafe * speed * -Math.sin(Math.toRadians(rotationYaw));
        return new double[] { posX, posZ };
    }
    
    public static MovementInput inverse(final Entity entity, final double speed) {
        final MovementInput input = new MovementInput();
        input.sneak = entity.isSneaking();
        for (float d = -1.0f; d <= 1.0f; ++d) {
            for (float e = -1.0f; e <= 1.0f; ++e) {
                final MovementInput dummyInput = new MovementInput();
                dummyInput.field_192832_b = d;
                dummyInput.moveStrafe = e;
                dummyInput.sneak = entity.isSneaking();
                final double[] moveVec = strafe(entity, dummyInput, speed);
                if (entity.isSneaking()) {
                    final double[] array = moveVec;
                    final int n = 0;
                    array[n] *= 0.30000001192092896;
                    final double[] array2 = moveVec;
                    final int n2 = 1;
                    array2[n2] *= 0.30000001192092896;
                }
                final double targetMotionX = moveVec[0];
                final double targetMotionZ = moveVec[1];
                if (targetMotionX < 0.0) {
                    if (entity.motionX > targetMotionX) {
                        continue;
                    }
                }
                else if (entity.motionX < targetMotionX) {
                    continue;
                }
                if (targetMotionZ < 0.0) {
                    if (entity.motionZ > targetMotionZ) {
                        continue;
                    }
                }
                else if (entity.motionZ < targetMotionZ) {
                    continue;
                }
                input.field_192832_b = d;
                input.moveStrafe = e;
                break;
            }
        }
        return input;
    }
    
    public static double getDistance2D() {
        final double xDist = MovementUtil.mc.player.posX - MovementUtil.mc.player.prevPosX;
        final double zDist = MovementUtil.mc.player.posZ - MovementUtil.mc.player.prevPosZ;
        return Math.sqrt(xDist * xDist + zDist * zDist);
    }
    
    public static double getDistance3D() {
        final double xDist = MovementUtil.mc.player.posX - MovementUtil.mc.player.prevPosX;
        final double yDist = MovementUtil.mc.player.posY - MovementUtil.mc.player.prevPosY;
        final double zDist = MovementUtil.mc.player.posZ - MovementUtil.mc.player.prevPosZ;
        return Math.sqrt(xDist * xDist + yDist * yDist + zDist * zDist);
    }
    
    public static double getSpeed() {
        return getSpeed(false);
    }
    
    public static double getSpeed(final boolean slowness, double defaultSpeed) {
        if (MovementUtil.mc.player.isPotionActive(MobEffects.SPEED)) {
            final int amplifier = Objects.requireNonNull(MovementUtil.mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier();
            defaultSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        if (slowness && MovementUtil.mc.player.isPotionActive(MobEffects.SLOWNESS)) {
            final int amplifier = Objects.requireNonNull(MovementUtil.mc.player.getActivePotionEffect(MobEffects.SLOWNESS)).getAmplifier();
            defaultSpeed /= 1.0 + 0.2 * (amplifier + 1);
        }
        return defaultSpeed;
    }
    
    public static double getSpeed(final boolean slowness) {
        double defaultSpeed = 0.2873;
        if (MovementUtil.mc.player.isPotionActive(MobEffects.SPEED)) {
            final int amplifier = Objects.requireNonNull(MovementUtil.mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier();
            defaultSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        if (slowness && MovementUtil.mc.player.isPotionActive(MobEffects.SLOWNESS)) {
            final int amplifier = Objects.requireNonNull(MovementUtil.mc.player.getActivePotionEffect(MobEffects.SLOWNESS)).getAmplifier();
            defaultSpeed /= 1.0 + 0.2 * (amplifier + 1);
        }
        return defaultSpeed;
    }
    
    public static double getJumpSpeed() {
        double defaultSpeed = 0.0;
        if (MovementUtil.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
            final int amplifier = MovementUtil.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier();
            defaultSpeed += (amplifier + 1) * 0.1;
        }
        return defaultSpeed;
    }
    
    public static boolean isInMovementDirection(final double x, final double y, final double z) {
        if (MovementUtil.mc.player.motionX != 0.0 || MovementUtil.mc.player.motionZ != 0.0) {
            final BlockPos movingPos = new BlockPos((Entity)MovementUtil.mc.player).add(MovementUtil.mc.player.motionX * 10000.0, 0.0, MovementUtil.mc.player.motionZ * 10000.0);
            final BlockPos antiPos = new BlockPos((Entity)MovementUtil.mc.player).add(MovementUtil.mc.player.motionX * -10000.0, 0.0, MovementUtil.mc.player.motionY * -10000.0);
            return movingPos.distanceSq(x, y, z) < antiPos.distanceSq(x, y, z);
        }
        return true;
    }
    
    public static void constantiamDamage() {
    }
}
