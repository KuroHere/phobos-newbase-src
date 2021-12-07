//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.math.raytrace;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.managers.minecraft.movement.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import com.google.common.base.*;
import me.earth.earthhack.impl.managers.*;
import java.util.stream.*;
import java.util.*;
import net.minecraft.world.*;
import java.util.function.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.util.math.path.*;
import net.minecraft.util.math.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class RayTracer implements Globals
{
    private static final Predicate<Entity> PREDICATE;
    
    public static RayTraceResult rayTraceEntities(final World world, final Entity from, final double range, final PositionManager position, final RotationManager rotation, final Predicate<Entity> entityCheck, final Entity... additional) {
        return rayTraceEntities(world, from, range, position.getX(), position.getY(), position.getZ(), rotation.getServerYaw(), rotation.getServerPitch(), position.getBB(), entityCheck, additional);
    }
    
    public static RayTraceResult rayTraceEntities(final World world, final Entity from, final double range, final double posX, final double posY, final double posZ, final float yaw, final float pitch, final AxisAlignedBB fromBB, final Predicate<Entity> entityCheck, final Entity... additional) {
        final Vec3d eyePos = new Vec3d(posX, posY + from.getEyeHeight(), posZ);
        final Vec3d rot = RotationUtil.getVec3d(yaw, pitch);
        final Vec3d intercept = eyePos.addVector(rot.xCoord * range, rot.yCoord * range, rot.zCoord * range);
        Entity pointedEntity = null;
        Vec3d hitVec = null;
        double distance = range;
        final AxisAlignedBB within = fromBB.addCoord(rot.xCoord * range, rot.yCoord * range, rot.zCoord * range).expand(1.0, 1.0, 1.0);
        final Predicate<Entity> predicate = (Predicate<Entity>)((entityCheck == null) ? RayTracer.PREDICATE : Predicates.and((Predicate)RayTracer.PREDICATE, (Predicate)entityCheck));
        List<Entity> entities;
        if (RayTracer.mc.isCallingFromMinecraftThread()) {
            entities = world.getEntitiesInAABBexcluding(from, within, (Predicate)predicate);
        }
        else {
            entities = Managers.ENTITIES.getEntities().stream().filter(e -> e != null && e.getEntityBoundingBox().intersectsWith(within) && predicate.test((Object)e)).collect((Collector<? super Object, ?, List<Entity>>)Collectors.toList());
        }
        for (final Entity entity : additional) {
            if (entity != null && entity.getEntityBoundingBox().intersectsWith(within)) {
                entities.add(entity);
            }
        }
        for (final Entity entity2 : entities) {
            final AxisAlignedBB bb = entity2.getEntityBoundingBox().expandXyz((double)entity2.getCollisionBorderSize());
            final RayTraceResult result = bb.calculateIntercept(eyePos, intercept);
            if (bb.isVecInside(eyePos)) {
                if (distance < 0.0) {
                    continue;
                }
                pointedEntity = entity2;
                hitVec = ((result == null) ? eyePos : result.hitVec);
                distance = 0.0;
            }
            else {
                if (result == null) {
                    continue;
                }
                final double hitDistance = eyePos.distanceTo(result.hitVec);
                if (hitDistance >= distance && distance != 0.0) {
                    continue;
                }
                if (entity2.getLowestRidingEntity() == from.getLowestRidingEntity() && !entity2.canRiderInteract()) {
                    if (distance != 0.0) {
                        continue;
                    }
                    pointedEntity = entity2;
                    hitVec = result.hitVec;
                }
                else {
                    pointedEntity = entity2;
                    hitVec = result.hitVec;
                    distance = hitDistance;
                }
            }
        }
        if (pointedEntity != null && hitVec != null) {
            return new RayTraceResult(pointedEntity, hitVec);
        }
        return null;
    }
    
    public static RayTraceResult trace(final World world, final IBlockAccess access, final Vec3d start, final Vec3d end, final boolean stopOnLiquid, final boolean ignoreBlockWithoutBoundingBox, final boolean returnLastUncollidableBlock) {
        return trace(world, access, start, end, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock, null);
    }
    
    public static RayTraceResult trace(final World world, final Vec3d start, final Vec3d end, final boolean stopOnLiquid, final boolean ignoreBlockWithoutBoundingBox, final boolean returnLastUncollidableBlock, final BiPredicate<Block, BlockPos> blockChecker) {
        return trace(world, (IBlockAccess)world, start, end, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock, blockChecker);
    }
    
    public static RayTraceResult trace(final World world, final IBlockAccess access, final Vec3d start, final Vec3d end, final boolean stopOnLiquid, final boolean ignoreBlockWithoutBoundingBox, final boolean returnLastUncollidableBlock, final BiPredicate<Block, BlockPos> blockChecker) {
        return traceTri(world, access, start, end, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock, (blockChecker == null) ? null : ((b, p, ef) -> blockChecker.test(b, p)));
    }
    
    public static RayTraceResult traceTri(final World world, final IBlockAccess access, final Vec3d start, final Vec3d end, final boolean stopOnLiquid, final boolean ignoreBlockWithoutBoundingBox, final boolean returnLastUncollidableBlock, final TriPredicate<Block, BlockPos, EnumFacing> blockChecker) {
        return traceTri(world, access, start, end, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock, blockChecker, null);
    }
    
    public static RayTraceResult traceTri(final World world, final IBlockAccess access, final Vec3d start, final Vec3d end, final boolean stopOnLiquid, final boolean ignoreBlockWithoutBoundingBox, final boolean returnLastUncollidableBlock, final TriPredicate<Block, BlockPos, EnumFacing> blockChecker, final TriPredicate<Block, BlockPos, EnumFacing> collideCheck) {
        return traceTri(world, access, start, end, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock, blockChecker, collideCheck, CollisionFunction.DEFAULT);
    }
    
    public static RayTraceResult traceTri(final World world, final IBlockAccess access, Vec3d start, final Vec3d end, final boolean stopOnLiquid, final boolean ignoreBlockWithoutBoundingBox, final boolean returnLastUncollidableBlock, final TriPredicate<Block, BlockPos, EnumFacing> blockChecker, final TriPredicate<Block, BlockPos, EnumFacing> collideCheck, final CollisionFunction crt) {
        if (Double.isNaN(start.xCoord) || Double.isNaN(start.yCoord) || Double.isNaN(start.zCoord)) {
            return null;
        }
        if (!Double.isNaN(end.xCoord) && !Double.isNaN(end.yCoord) && !Double.isNaN(end.zCoord)) {
            final int feX = MathHelper.floor(end.xCoord);
            final int feY = MathHelper.floor(end.yCoord);
            final int feZ = MathHelper.floor(end.zCoord);
            int fsX = MathHelper.floor(start.xCoord);
            int fsY = MathHelper.floor(start.yCoord);
            int fsZ = MathHelper.floor(start.zCoord);
            BlockPos pos = new BlockPos(fsX, fsY, fsZ);
            final IBlockState state = access.getBlockState(pos);
            final Block block = state.getBlock();
            if ((!ignoreBlockWithoutBoundingBox || state.getCollisionBoundingBox(access, pos) != Block.NULL_AABB) && (block.canCollideCheck(state, stopOnLiquid) || (collideCheck != null && collideCheck.test(block, pos, null))) && (blockChecker == null || blockChecker.test(block, pos, null))) {
                final RayTraceResult raytraceresult = crt.collisionRayTrace(state, world, pos, start, end);
                if (raytraceresult != null) {
                    return raytraceresult;
                }
            }
            RayTraceResult result = null;
            int steps = 200;
            while (steps-- >= 0) {
                if (Double.isNaN(start.xCoord) || Double.isNaN(start.yCoord) || Double.isNaN(start.zCoord)) {
                    return null;
                }
                if (fsX == feX && fsY == feY && fsZ == feZ) {
                    return returnLastUncollidableBlock ? result : null;
                }
                boolean xEq = true;
                boolean yEq = true;
                boolean zEq = true;
                double x = 999.0;
                double y = 999.0;
                double z = 999.0;
                if (feX > fsX) {
                    x = fsX + 1.0;
                }
                else if (feX < fsX) {
                    x = fsX + 0.0;
                }
                else {
                    xEq = false;
                }
                if (feY > fsY) {
                    y = fsY + 1.0;
                }
                else if (feY < fsY) {
                    y = fsY + 0.0;
                }
                else {
                    yEq = false;
                }
                if (feZ > fsZ) {
                    z = fsZ + 1.0;
                }
                else if (feZ < fsZ) {
                    z = fsZ + 0.0;
                }
                else {
                    zEq = false;
                }
                double xOff = 999.0;
                double yOff = 999.0;
                double zOff = 999.0;
                final double diffX = end.xCoord - start.xCoord;
                final double diffY = end.yCoord - start.yCoord;
                final double diffZ = end.zCoord - start.zCoord;
                if (xEq) {
                    xOff = (x - start.xCoord) / diffX;
                }
                if (yEq) {
                    yOff = (y - start.yCoord) / diffY;
                }
                if (zEq) {
                    zOff = (z - start.zCoord) / diffZ;
                }
                if (xOff == -0.0) {
                    xOff = -1.0E-4;
                }
                if (yOff == -0.0) {
                    yOff = -1.0E-4;
                }
                if (zOff == -0.0) {
                    zOff = -1.0E-4;
                }
                EnumFacing enumfacing;
                if (xOff < yOff && xOff < zOff) {
                    enumfacing = ((feX > fsX) ? EnumFacing.WEST : EnumFacing.EAST);
                    start = new Vec3d(x, start.yCoord + diffY * xOff, start.zCoord + diffZ * xOff);
                }
                else if (yOff < zOff) {
                    enumfacing = ((feY > fsY) ? EnumFacing.DOWN : EnumFacing.UP);
                    start = new Vec3d(start.xCoord + diffX * yOff, y, start.zCoord + diffZ * yOff);
                }
                else {
                    enumfacing = ((feZ > fsZ) ? EnumFacing.NORTH : EnumFacing.SOUTH);
                    start = new Vec3d(start.xCoord + diffX * zOff, start.yCoord + diffY * zOff, z);
                }
                fsX = MathHelper.floor(start.xCoord) - ((enumfacing == EnumFacing.EAST) ? 1 : 0);
                fsY = MathHelper.floor(start.yCoord) - ((enumfacing == EnumFacing.UP) ? 1 : 0);
                fsZ = MathHelper.floor(start.zCoord) - ((enumfacing == EnumFacing.SOUTH) ? 1 : 0);
                pos = new BlockPos(fsX, fsY, fsZ);
                final IBlockState state2 = access.getBlockState(pos);
                final Block block2 = state2.getBlock();
                if (ignoreBlockWithoutBoundingBox && state2.getMaterial() != Material.PORTAL && state2.getCollisionBoundingBox(access, pos) == Block.NULL_AABB) {
                    continue;
                }
                if ((block2.canCollideCheck(state2, stopOnLiquid) || (collideCheck != null && collideCheck.test(block2, pos, enumfacing))) && (blockChecker == null || blockChecker.test(block2, pos, enumfacing))) {
                    final RayTraceResult raytraceresult2 = crt.collisionRayTrace(state2, world, pos, start, end);
                    if (raytraceresult2 != null) {
                        return raytraceresult2;
                    }
                    continue;
                }
                else {
                    result = new RayTraceResult(RayTraceResult.Type.MISS, start, enumfacing, pos);
                }
            }
            return returnLastUncollidableBlock ? result : null;
        }
        return null;
    }
    
    static {
        PREDICATE = Predicates.and(EntitySelectors.NOT_SPECTATING, e -> e != null && e.canBeCollidedWith());
    }
}
