//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.math.path;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.util.math.geocache.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import me.earth.earthhack.impl.util.minecraft.blocks.states.*;
import net.minecraft.block.state.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.math.raytrace.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.entity.item.*;

public class PathFinder implements Globals
{
    public static final double MAX_RANGE = 7.0;
    public static final GeoCache CACHE;
    public static final TriPredicate<BlockPos, Pathable, Entity> CHECK;
    private static final EnumFacing[] SN;
    private static final EnumFacing[] EW;
    private static final EnumFacing[] UD;
    private static final boolean[][] CHECKS;
    
    private PathFinder() {
        throw new AssertionError();
    }
    
    public static void efficient(final Pathable p, final double pr, final List<Entity> es, final RayTraceMode mode, final IBlockStateHelper world, final IBlockState setState, final TriPredicate<BlockPos, Pathable, Entity> check, final Iterable<BlockPos> limited, final BlockPos... ignore) {
        fastPath(p, pr, es, mode, world, setState, check, limited, ignore);
        if (!p.isValid()) {
            findPath(p, pr, es, mode, world, setState, check, ignore);
        }
    }
    
    public static void fastPath(final Pathable p, final double pr, final List<Entity> es, final RayTraceMode m, final IBlockStateHelper world, final IBlockState s, final TriPredicate<BlockPos, Pathable, Entity> c, final Iterable<BlockPos> tc, final BlockPos... ignore) {
        if (pr > 7.0) {
            throw new IllegalArgumentException("Range " + pr + " was bigger than MAX_RANGE: " + 7.0);
        }
        final Set<BlockPos> ignored = Sets.newHashSet((Object[])ignore);
        for (final BlockPos pos : tc) {
            if (checkPos(pos, p, ignored, pr, es, m, world, s, c)) {
                break;
            }
        }
    }
    
    public static void findPath(final Pathable pathable, final double pr, final List<Entity> entities, final RayTraceMode mode, final IBlockStateHelper world, final IBlockState setState, final TriPredicate<BlockPos, Pathable, Entity> check, final BlockPos... ignore) {
        if (pr > 7.0) {
            throw new IllegalArgumentException("Range " + pr + " was bigger than MAX_RANGE: " + 7.0);
        }
        final Set<BlockPos> ignored = Sets.newHashSet((Object[])ignore);
        final int maxRadius = PathFinder.CACHE.getRadius(pr);
        final Vec3i[] offsets = PathFinder.CACHE.array();
        for (int i = 1; i < maxRadius && !checkPos(pathable.getPos().add(offsets[i]), pathable, ignored, pr, entities, mode, world, setState, check); ++i) {}
    }
    
    private static boolean checkPos(final BlockPos pos, final Pathable pathable, final Set<BlockPos> ignored, final double pr, final List<Entity> entities, final RayTraceMode mode, final IBlockStateHelper world, final IBlockState setState, final TriPredicate<BlockPos, Pathable, Entity> c) {
        final IBlockState state = world.getBlockState(pos);
        if (state.getMaterial().isReplaceable()) {
            return false;
        }
        if (pathable.getFrom().getDistanceSq(pos) > MathUtil.square(pr) || ignored.contains(pos)) {
            return false;
        }
        final int xDiff = pathable.getPos().getX() - pos.getX();
        final int yDiff = pathable.getPos().getY() - pos.getY();
        final int zDiff = pathable.getPos().getZ() - pos.getZ();
        for (int i = 0; i < PathFinder.CHECKS.length; ++i) {
            final boolean[] check = PathFinder.CHECKS[i];
            final EnumFacing[] facings = produceOffsets(check[0], check[1], xDiff, yDiff, zDiff);
            if (facings.length <= pathable.getMaxLength()) {
                int index = 0;
                boolean valid = true;
                BlockPos current = pos;
                final Ray[] path = new Ray[facings.length];
                for (final EnumFacing facing : facings) {
                    final BlockPos offset = current.offset(facing);
                    if (check(offset, pr, ignored, pathable, entities, c)) {
                        valid = false;
                        break;
                    }
                    final Ray ray = RayTraceFactory.rayTrace(pathable.getFrom(), current, facing, (IBlockAccess)world, setState, (mode == RayTraceMode.Smart) ? -1.0 : 2.0);
                    if (!ray.isLegit() && mode == RayTraceMode.Smart) {
                        valid = false;
                        break;
                    }
                    path[index++] = ray;
                    current = offset;
                }
                if (valid) {
                    pathable.setPath(path);
                    pathable.setValid(true);
                    return true;
                }
                if (facings.length == 1) {
                    break;
                }
                if (facings.length < 4 && i > 0) {
                    break;
                }
                pathable.getBlockingEntities().clear();
            }
        }
        return false;
    }
    
    private static boolean check(final BlockPos pos, final double pr, final Set<BlockPos> ignored, final Pathable pathable, final List<Entity> entities, final TriPredicate<BlockPos, Pathable, Entity> c) {
        return pathable.getFrom().getDistanceSq(pos) > MathUtil.square(pr) || ignored.contains(pos) || checkEntities(pathable, pos, entities, c);
    }
    
    private static boolean checkEntities(final Pathable data, final BlockPos pos, final List<Entity> entities, final TriPredicate<BlockPos, Pathable, Entity> check) {
        for (final Entity entity : entities) {
            if (check.test(pos, data, entity)) {
                continue;
            }
            return true;
        }
        return false;
    }
    
    public static EnumFacing[] produceOffsets(final boolean yFirst, final boolean xFirst, final int xDiff, final int yDiff, final int zDiff) {
        final EnumFacing[] result = new EnumFacing[Math.abs(xDiff) + Math.abs(yDiff) + Math.abs(zDiff)];
        int index = 0;
        if (yFirst) {
            index = apply(result, yDiff, index, PathFinder.UD);
            if (xFirst) {
                index = apply(result, xDiff, index, PathFinder.EW);
                apply(result, zDiff, index, PathFinder.SN);
            }
            else {
                index = apply(result, zDiff, index, PathFinder.SN);
                apply(result, xDiff, index, PathFinder.EW);
            }
        }
        else {
            if (xFirst) {
                index = apply(result, xDiff, index, PathFinder.EW);
                index = apply(result, zDiff, index, PathFinder.SN);
            }
            else {
                index = apply(result, zDiff, index, PathFinder.SN);
                index = apply(result, xDiff, index, PathFinder.EW);
            }
            apply(result, yDiff, index, PathFinder.UD);
        }
        return result;
    }
    
    private static int apply(final EnumFacing[] array, final int diff, final int start, final EnumFacing[] facings) {
        int i;
        for (i = 0; i < Math.abs(diff); ++i) {
            array[i + start] = ((diff > 0) ? facings[0] : facings[1]);
        }
        return start + i;
    }
    
    static {
        CHECK = ((b, d, e) -> {
            if (e == null || !e.preventEntitySpawning || EntityUtil.isDead(e) || !PositionUtil.intersects(e.getEntityBoundingBox(), b)) {
                return true;
            }
            else if (d != null && e instanceof EntityEnderCrystal) {
                d.getBlockingEntities().add(new BlockingEntity(e, b));
                return false;
            }
            else {
                return false;
            }
        });
        SN = new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.NORTH };
        EW = new EnumFacing[] { EnumFacing.EAST, EnumFacing.WEST };
        UD = new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN };
        CHECKS = new boolean[][] { { true, false }, { false, true }, { true, true }, { false, false } };
        (CACHE = new PathCache(1365, 8, 7.0)).cache();
    }
}
