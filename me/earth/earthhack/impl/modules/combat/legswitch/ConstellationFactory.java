//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.legswitch;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import me.earth.earthhack.impl.util.minecraft.blocks.states.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.*;
import java.util.*;

final class ConstellationFactory implements Globals
{
    private ConstellationFactory() {
        throw new AssertionError();
    }
    
    public static LegConstellation create(final LegSwitch module, final List<EntityPlayer> players) {
        return create(module, players, (IBlockAccess)ConstellationFactory.mc.world);
    }
    
    public static LegConstellation create(final LegSwitch module, final List<EntityPlayer> players, final IBlockAccess access) {
        if (module.closest.getValue()) {
            return getConstellation(module, EntityUtil.getClosestEnemy(players), access);
        }
        LegConstellation closest = null;
        double distance = Double.MAX_VALUE;
        LegConstellation closestBad = null;
        double badDistance = Double.MAX_VALUE;
        for (final EntityPlayer player : players) {
            if (!EntityUtil.isValid((Entity)player, 12.0)) {
                continue;
            }
            final double dist = ConstellationFactory.mc.player.getDistanceSqToEntity((Entity)player);
            if (closest != null && distance <= dist) {
                continue;
            }
            final LegConstellation c = getConstellation(module, player, access);
            if (c == null) {
                continue;
            }
            if (c.firstNeedsObby || c.secondNeedsObby) {
                if (dist >= badDistance) {
                    continue;
                }
                badDistance = dist;
                closestBad = c;
            }
            else {
                closest = c;
                distance = dist;
            }
        }
        return (closest == null) ? closestBad : closest;
    }
    
    private static LegConstellation getConstellation(final LegSwitch module, final EntityPlayer player, final IBlockAccess access) {
        if (player == null || ConstellationFactory.mc.player.getDistanceSqToEntity((Entity)player) > MathUtil.square(module.targetRange.getValue())) {
            return null;
        }
        final BlockPos playerPos = PositionUtil.getPosition((Entity)player);
        final IBlockState playerPosState = access.getBlockState(playerPos);
        if (!playerPosState.getMaterial().isReplaceable()) {
            return null;
        }
        LegConstellation result = null;
        LegConstellation closestBad = null;
        double distance = Double.MAX_VALUE;
        double badDistance = Double.MAX_VALUE;
        for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
            final BlockPos offset = playerPos.offset(facing);
            final double dist = BlockUtil.getDistanceSq(offset);
            if (dist < distance) {
                final IBlockState offsetState = access.getBlockState(offset);
                if (offsetState.getMaterial().isReplaceable()) {
                    final LegConstellation constellation = getConstellation(module, offset, facing, player, playerPos, access);
                    if (constellation != null) {
                        constellation.add(playerPos, playerPosState);
                        constellation.add(offset, offsetState);
                        if (constellation.firstNeedsObby || constellation.secondNeedsObby) {
                            if (dist < badDistance) {
                                badDistance = dist;
                                closestBad = constellation;
                            }
                        }
                        else {
                            distance = dist;
                            result = constellation;
                        }
                    }
                }
            }
        }
        return (result == null) ? closestBad : result;
    }
    
    private static LegConstellation getConstellation(final LegSwitch module, final BlockPos pos, final EnumFacing facing, final EntityPlayer target, final BlockPos playerPos, final IBlockAccess access) {
        final EnumFacing[] rotated = MathUtil.getRotated(facing);
        int badStates = 0;
        final Map<BlockPos, IBlockState> states = new HashMap<BlockPos, IBlockState>();
        for (final EnumFacing f : rotated) {
            final BlockPos p = pos.offset(f);
            final IBlockState pState = access.getBlockState(p);
            if (!pState.getMaterial().isReplaceable()) {
                ++badStates;
            }
            states.put(p, pState);
        }
        if (badStates > 1) {
            return null;
        }
        final BlockPos mid = pos.offset(facing);
        if (module.requireMid.getValue() && !access.getBlockState(mid).getMaterial().isReplaceable()) {
            return null;
        }
        final BlockPos first = mid.offset(rotated[0]).down();
        final BlockPos second = mid.offset(rotated[1]).down();
        if (!module.checkPos(first) || !module.checkPos(second)) {
            return null;
        }
        final IBlockState firstState = access.getBlockState(first);
        final IBlockState secondState = access.getBlockState(second);
        final int require = requiresObby(first, firstState, module.newVer.getValue(), module.newVerEntities.getValue());
        if (require == -1) {
            return null;
        }
        final int require2 = requiresObby(second, secondState, module.newVer.getValue(), module.newVerEntities.getValue());
        if (require2 == -1) {
            return null;
        }
        IBlockAccess blockAccess = (IBlockAccess)ConstellationFactory.mc.world;
        if (require == 1 || require2 == 1) {
            final BlockStateHelper helper = new BlockStateHelper();
            helper.addBlockState(first, Blocks.OBSIDIAN.getDefaultState());
            helper.addBlockState(second, Blocks.OBSIDIAN.getDefaultState());
            blockAccess = (IBlockAccess)helper;
        }
        final EntityPlayer player = RotationUtil.getRotationPlayer();
        float self = DamageUtil.calculate(first.getX() + 0.5f, first.getY() + 1, first.getZ() + 0.5f, player.getEntityBoundingBox(), (EntityLivingBase)player, blockAccess, true);
        if (self > module.maxSelfDamage.getValue()) {
            return null;
        }
        self = DamageUtil.calculate(second.getX() + 0.5f, second.getY() + 1, second.getZ() + 0.5f, player.getEntityBoundingBox(), (EntityLivingBase)player, blockAccess, true);
        if (self > module.maxSelfDamage.getValue()) {
            return null;
        }
        if (DamageUtil.calculate(first, (EntityLivingBase)target) > module.minDamage.getValue() || DamageUtil.calculate(second, (EntityLivingBase)target) > module.minDamage.getValue()) {
            return new LegConstellation(target, pos, playerPos, first, second, states, require == 1, require2 == 1);
        }
        return null;
    }
    
    private static int requiresObby(final BlockPos pos, final IBlockState state, final boolean newVer, final boolean newVerEntities) {
        int result = -1;
        if (state.getBlock() != Blocks.OBSIDIAN && state.getBlock() != Blocks.BEDROCK) {
            if (!state.getMaterial().isReplaceable()) {
                return result;
            }
            result = 0;
        }
        final BlockPos up = pos.up();
        final BlockPos upUp = up.up();
        if (ConstellationFactory.mc.world.getBlockState(up).getBlock() != Blocks.AIR || (!newVer && ConstellationFactory.mc.world.getBlockState(upUp).getBlock() != Blocks.AIR) || !BlockUtil.checkEntityList(up, true, null) || (newVerEntities && !BlockUtil.checkEntityList(upUp, true, null))) {
            return -1;
        }
        return ++result;
    }
}
