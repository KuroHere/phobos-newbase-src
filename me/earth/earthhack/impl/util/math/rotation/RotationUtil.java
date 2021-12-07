//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.math.rotation;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.freecam.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.render.*;
import net.minecraft.client.renderer.culling.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.math.raytrace.*;
import net.minecraft.world.*;
import com.google.common.base.*;
import java.util.*;
import net.minecraft.util.math.*;
import java.util.function.*;
import net.minecraft.block.*;
import com.mojang.realmsclient.gui.*;
import me.earth.earthhack.impl.modules.*;

public class RotationUtil implements Globals
{
    private static final ModuleCache<Freecam> FREECAM;
    
    public static EntityPlayer getRotationPlayer() {
        EntityPlayer rotationEntity = (EntityPlayer)RotationUtil.mc.player;
        if (RotationUtil.FREECAM.isEnabled()) {
            rotationEntity = (EntityPlayer)RotationUtil.FREECAM.get().getPlayer();
        }
        return (EntityPlayer)((rotationEntity == null) ? RotationUtil.mc.player : rotationEntity);
    }
    
    public static float[] getRotations(final BlockPos pos, final EnumFacing facing) {
        return getRotations(pos, facing, (Entity)getRotationPlayer());
    }
    
    public static float[] getRotations(final BlockPos pos, final EnumFacing facing, final Entity from) {
        return getRotations(pos, facing, from, (IBlockAccess)RotationUtil.mc.world, RotationUtil.mc.world.getBlockState(pos));
    }
    
    public static float[] getRotations(final BlockPos pos, final EnumFacing facing, final Entity from, final IBlockAccess world, final IBlockState state) {
        final AxisAlignedBB bb = state.getBoundingBox(world, pos);
        double x = pos.getX() + (bb.minX + bb.maxX) / 2.0;
        double y = pos.getY() + (bb.minY + bb.maxY) / 2.0;
        double z = pos.getZ() + (bb.minZ + bb.maxZ) / 2.0;
        if (facing != null) {
            x += facing.getDirectionVec().getX() * ((bb.minX + bb.maxX) / 2.0);
            y += facing.getDirectionVec().getY() * ((bb.minY + bb.maxY) / 2.0);
            z += facing.getDirectionVec().getZ() * ((bb.minZ + bb.maxZ) / 2.0);
        }
        return getRotations(x, y, z, from);
    }
    
    public static float[] getRotationsToTopMiddle(final BlockPos pos) {
        return getRotations(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
    }
    
    public static float[] getRotationsMaxYaw(final BlockPos pos, final float max, final float current) {
        return new float[] { updateRotation(current, getRotationsToTopMiddle(pos)[0], max), getRotationsToTopMiddle(pos)[1] };
    }
    
    public static float[] getRotations(final Entity entity, final double height) {
        return getRotations(entity.posX, entity.posY + entity.getEyeHeight() * height, entity.posZ);
    }
    
    public static float[] getRotations(final Entity entity) {
        return getRotations(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
    }
    
    public static float[] getRotationsMaxYaw(final Entity entity, final float max, final float current) {
        return new float[] { MathHelper.wrapDegrees(updateRotation(current, getRotations(entity)[0], max)), getRotations(entity)[1] };
    }
    
    public static float[] getRotations(final Vec3d vec3d) {
        return getRotations(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
    }
    
    public static float[] getRotations(final double x, final double y, final double z) {
        return getRotations(x, y, z, (Entity)getRotationPlayer());
    }
    
    public static float[] getRotations(final double x, final double y, final double z, final Entity f) {
        return getRotations(x, y, z, f.posX, f.posY, f.posZ, f.getEyeHeight());
    }
    
    public static float[] getRotations(final double x, final double y, final double z, final double fromX, final double fromY, final double fromZ, final float fromHeight) {
        final double xDiff = x - fromX;
        final double yDiff = y - (fromY + fromHeight);
        final double zDiff = z - fromZ;
        final double dist = MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff);
        final float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
        final float prevYaw = Managers.ROTATION.getServerYaw();
        float diff = yaw - prevYaw;
        if (diff < -180.0f || diff > 180.0f) {
            final float round = (float)Math.round(Math.abs(diff / 360.0f));
            diff = ((diff < 0.0f) ? (diff + 360.0f * round) : (diff - 360.0f * round));
        }
        return new float[] { prevYaw + diff, pitch };
    }
    
    public static boolean inFov(final Entity entity) {
        return inFov(entity.posX, entity.posY + entity.getEyeHeight() / 2.0f, entity.posZ);
    }
    
    public static boolean inFov(final BlockPos pos) {
        return inFov(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
    }
    
    public static boolean inFov(final double x, final double y, final double z) {
        final Entity renderEntity = RenderUtil.getEntity();
        if (renderEntity == null) {
            return false;
        }
        final Frustum frustum = Interpolation.createFrustum(renderEntity);
        return frustum.isBoundingBoxInFrustum(new AxisAlignedBB(x - 1.0, y - 1.0, x - 1.0, x + 1.0, y + 1.0, z + 1.0));
    }
    
    public static double getAngle(final Entity entity, final double yOffset) {
        final Vec3d vec3d = MathUtil.fromTo(Interpolation.interpolatedEyePos(), entity.posX, entity.posY + yOffset, entity.posZ);
        return MathUtil.angle(vec3d, Interpolation.interpolatedEyeVec());
    }
    
    public static Vec3d getVec3d(final float yaw, final float pitch) {
        final float vx = -MathHelper.sin(MathUtil.rad(yaw)) * MathHelper.cos(MathUtil.rad(pitch));
        final float vz = MathHelper.cos(MathUtil.rad(yaw)) * MathHelper.cos(MathUtil.rad(pitch));
        final float vy = -MathHelper.sin(MathUtil.rad(pitch));
        return new Vec3d((double)vx, (double)vy, (double)vz);
    }
    
    public static boolean isLegit(final Entity entity, final Entity... additional) {
        final RayTraceResult result = RayTracer.rayTraceEntities((World)RotationUtil.mc.world, (Entity)getRotationPlayer(), getRotationPlayer().getDistanceToEntity(entity) + 1.0, Managers.POSITION, Managers.ROTATION, (Predicate<Entity>)(e -> e != null && e.equals((Object)entity)), additional);
        return result != null && result.entityHit != null && (entity.equals((Object)result.entityHit) || (additional != null && additional.length != 0 && Arrays.stream(additional).anyMatch(e -> result.entityHit.equals((Object)e))));
    }
    
    public static boolean isLegit(final BlockPos pos) {
        return isLegit(pos, null);
    }
    
    public static boolean isLegit(final BlockPos pos, final EnumFacing facing) {
        return isLegit(pos, facing, (IBlockAccess)RotationUtil.mc.world);
    }
    
    public static boolean isLegit(final BlockPos pos, final EnumFacing facing, final IBlockAccess world) {
        final RayTraceResult ray = rayTraceTo(pos, world);
        return ray != null && ray.getBlockPos() != null && ray.getBlockPos().equals((Object)pos) && (facing == null || ray.sideHit == facing);
    }
    
    public static RayTraceResult rayTraceTo(final BlockPos pos, final IBlockAccess world) {
        return rayTraceTo(pos, world, (b, p) -> p.equals((Object)pos));
    }
    
    public static RayTraceResult rayTraceTo(final BlockPos pos, final IBlockAccess world, final BiPredicate<Block, BlockPos> check) {
        final Entity from = (Entity)getRotationPlayer();
        final float yaw = Managers.ROTATION.getServerYaw();
        final float pitch = Managers.ROTATION.getServerPitch();
        final Vec3d start = Managers.POSITION.getVec().addVector(0.0, (double)from.getEyeHeight(), 0.0);
        final Vec3d look = getVec3d(yaw, pitch);
        final double d = from.getDistance(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) + 1.0;
        final Vec3d end = start.addVector(look.xCoord * d, look.yCoord * d, look.zCoord * d);
        return RayTracer.trace((World)RotationUtil.mc.world, world, start, end, true, false, true, check);
    }
    
    public static RayTraceResult rayTraceWithYP(final BlockPos pos, final IBlockAccess world, final float yaw, final float pitch, final BiPredicate<Block, BlockPos> check) {
        final Entity from = (Entity)getRotationPlayer();
        final Vec3d start = from.getPositionVector().addVector(0.0, (double)from.getEyeHeight(), 0.0);
        final Vec3d look = getVec3d(yaw, pitch);
        final double d = from.getDistance(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) + 1.0;
        final Vec3d end = start.addVector(look.xCoord * d, look.yCoord * d, look.zCoord * d);
        return RayTracer.trace((World)RotationUtil.mc.world, world, start, end, true, false, true, check);
    }
    
    public static float[] faceSmoothly(final double curYaw, final double curPitch, final double intendedYaw, final double intendedPitch, final double yawSpeed, final double pitchSpeed) {
        final float yaw = updateRotation((float)curYaw, (float)intendedYaw, (float)yawSpeed);
        final float pitch = updateRotation((float)curPitch, (float)intendedPitch, (float)pitchSpeed);
        return new float[] { yaw, pitch };
    }
    
    public static double angle(final float[] rotation1, final float[] rotation2) {
        final Vec3d r1Vec = getVec3d(rotation1[0], rotation1[1]);
        final Vec3d r2Vec = getVec3d(rotation2[0], rotation2[1]);
        return MathUtil.angle(r1Vec, r2Vec);
    }
    
    public static float updateRotation(final float current, final float intended, final float factor) {
        float updated = MathHelper.wrapDegrees(intended - current);
        if (updated > factor) {
            updated = factor;
        }
        if (updated < -factor) {
            updated = -factor;
        }
        return current + updated;
    }
    
    public static int getDirection4D() {
        return MathHelper.floor(RotationUtil.mc.player.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
    }
    
    public static String getDirection4D(final boolean northRed) {
        switch (getDirection4D()) {
            case 0: {
                return "South §7[§f+Z§7]";
            }
            case 1: {
                return "West §7[§f-X§7]";
            }
            case 2: {
                return (northRed ? ChatFormatting.RED : "") + "North " + "§7" + "[" + "§f" + "-Z" + "§7" + "]";
            }
            default: {
                return "East §7[§f+X§7]";
            }
        }
    }
    
    static {
        FREECAM = Caches.getModule(Freecam.class);
    }
}
