//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.math;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.position.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class RayTraceUtil implements Globals
{
    public static float[] hitVecToPlaceVec(final BlockPos pos, final Vec3d hitVec) {
        final float x = (float)(hitVec.xCoord - pos.getX());
        final float y = (float)(hitVec.yCoord - pos.getY());
        final float z = (float)(hitVec.zCoord - pos.getZ());
        return new float[] { x, y, z };
    }
    
    public static RayTraceResult getRayTraceResult(final float yaw, final float pitch) {
        return getRayTraceResult(yaw, pitch, RayTraceUtil.mc.playerController.getBlockReachDistance());
    }
    
    public static RayTraceResult getRayTraceResultWithEntity(final float yaw, final float pitch, final Entity from) {
        return getRayTraceResult(yaw, pitch, RayTraceUtil.mc.playerController.getBlockReachDistance(), from);
    }
    
    public static RayTraceResult getRayTraceResult(final float yaw, final float pitch, final float distance) {
        return getRayTraceResult(yaw, pitch, distance, (Entity)RayTraceUtil.mc.player);
    }
    
    public static RayTraceResult getRayTraceResult(final float yaw, final float pitch, final float d, final Entity from) {
        final Vec3d vec3d = PositionUtil.getEyePos(from);
        final Vec3d lookVec = RotationUtil.getVec3d(yaw, pitch);
        final Vec3d rotations = vec3d.addVector(lookVec.xCoord * d, lookVec.yCoord * d, lookVec.zCoord * d);
        return Optional.ofNullable(RayTraceUtil.mc.world.rayTraceBlocks(vec3d, rotations, false, false, false)).orElseGet(() -> {
            new RayTraceResult(RayTraceResult.Type.MISS, new Vec3d(0.5, 1.0, 0.5), EnumFacing.UP, BlockPos.ORIGIN);
            return;
        });
    }
    
    public static boolean canBeSeen(final double x, final double y, final double z, final Entity by) {
        return canBeSeen(new Vec3d(x, y, z), by.posX, by.posY, by.posZ, by.getEyeHeight());
    }
    
    public static boolean canBeSeen(final Vec3d toSee, final Entity by) {
        return canBeSeen(toSee, by.posX, by.posY, by.posZ, by.getEyeHeight());
    }
    
    public static boolean canBeSeen(final Vec3d toSee, final double x, final double y, final double z, final float eyeHeight) {
        final Vec3d start = new Vec3d(x, y + eyeHeight, z);
        return RayTraceUtil.mc.world.rayTraceBlocks(start, toSee, false, true, false) == null;
    }
    
    public static boolean canBeSeen(final Entity toSee, final EntityLivingBase by) {
        return by.canEntityBeSeen(toSee);
    }
    
    public static boolean raytracePlaceCheck(final Entity entity, final BlockPos pos) {
        return getFacing(entity, pos, false) != null;
    }
    
    public static EnumFacing getFacing(final Entity entity, final BlockPos pos, final boolean verticals) {
        for (final EnumFacing facing : EnumFacing.values()) {
            final RayTraceResult result = RayTraceUtil.mc.world.rayTraceBlocks(PositionUtil.getEyePos(entity), new Vec3d(pos.getX() + 0.5 + facing.getDirectionVec().getX() * 1.0 / 2.0, pos.getY() + 0.5 + facing.getDirectionVec().getY() * 1.0 / 2.0, pos.getZ() + 0.5 + facing.getDirectionVec().getZ() * 1.0 / 2.0), false, true, false);
            if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK && result.getBlockPos().equals((Object)pos)) {
                return facing;
            }
        }
        if (!verticals) {
            return null;
        }
        if (pos.getY() > RayTraceUtil.mc.player.posY + RayTraceUtil.mc.player.getEyeHeight()) {
            return EnumFacing.DOWN;
        }
        return EnumFacing.UP;
    }
}
