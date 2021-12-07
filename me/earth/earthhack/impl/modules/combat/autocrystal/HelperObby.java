//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.client.safety.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.util.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.util.math.raytrace.*;
import me.earth.earthhack.impl.util.math.path.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import net.minecraft.entity.*;
import java.util.*;
import me.earth.earthhack.impl.util.minecraft.blocks.states.*;
import me.earth.earthhack.impl.util.math.position.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.modules.*;

public class HelperObby implements Globals
{
    private static final SettingCache<Float, NumberSetting<Float>, Safety> MD;
    private final AutoCrystal module;
    
    public HelperObby(final AutoCrystal module) {
        this.module = module;
    }
    
    public PositionData findBestObbyData(final Map<BlockPos, PositionData> obbyData, final List<EntityPlayer> players, final List<EntityPlayer> friends, final List<Entity> entities, final EntityPlayer target, final boolean newVer) {
        double maxY = 0.0;
        final List<EntityPlayer> filteredPlayers = new LinkedList<EntityPlayer>();
        for (final EntityPlayer player : players) {
            if (player != null && !EntityUtil.isDead((Entity)player) && player.posY <= HelperObby.mc.player.posY + 18.0) {
                if (player.getDistanceSqToEntity((Entity)HelperObby.mc.player) > MathUtil.square(this.module.targetRange.getValue())) {
                    continue;
                }
                filteredPlayers.add(player);
                if (player.posY <= maxY) {
                    continue;
                }
                maxY = player.posY;
            }
        }
        final int fastObby = this.module.fastObby.getValue();
        if (fastObby != 0) {
            Set<BlockPos> positions;
            if (target != null) {
                positions = new HashSet<BlockPos>((int)(4 * fastObby / 0.75) + 1);
                this.addPositions(positions, target, fastObby);
            }
            else {
                positions = new HashSet<BlockPos>((int)(filteredPlayers.size() * 4 * fastObby / 0.75 + 1.0));
                for (final EntityPlayer player2 : filteredPlayers) {
                    this.addPositions(positions, player2, fastObby);
                }
            }
            obbyData.keySet().retainAll(positions);
        }
        int shortest;
        final int maxPath = shortest = this.module.helpingBlocks.getValue();
        float maxDamage = 0.0f;
        float maxSelfDamage = 0.0f;
        PositionData bestData = null;
        for (final PositionData positionData : obbyData.values()) {
            if (positionData.isBlocked()) {
                continue;
            }
            final BlockPos pos = positionData.getPos();
            if (pos.getY() >= maxY) {
                continue;
            }
            float self = Float.MAX_VALUE;
            final boolean preSelf = this.module.obbyPreSelf.getValue();
            final IBlockStateHelper helper = new BlockStateHelper(new HashMap<BlockPos, IBlockState>());
            if (preSelf) {
                helper.addBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
                self = this.module.damageHelper.getObbyDamage(pos, helper);
                if (this.checkSelfDamage(self)) {
                    continue;
                }
                positionData.setSelfDamage(self);
            }
            final BlockPos[] ignore = new BlockPos[newVer ? 1 : 2];
            ignore[0] = pos.up();
            if (!newVer) {
                ignore[1] = pos.up(2);
            }
            if (this.module.interact.getValue()) {
                final RayTraceMode mode = this.module.obbyTrace.getValue();
                for (final EnumFacing facing : EnumFacing.values()) {
                    final BlockPos offset = pos.offset(facing);
                    if (BlockUtil.getDistanceSq(offset) < MathUtil.square(this.module.placeRange.getValue())) {
                        final IBlockState state = HelperObby.mc.world.getBlockState(offset);
                        if (!state.getMaterial().isReplaceable() || state.getMaterial().isLiquid()) {
                            Ray ray = RayTraceFactory.rayTrace(positionData.getFrom(), offset, facing.getOpposite(), (IBlockAccess)HelperObby.mc.world, Blocks.OBSIDIAN.getDefaultState(), (mode == RayTraceMode.Smart) ? -1.0 : 2.0);
                            if (ray.isLegit() || mode != RayTraceMode.Smart) {
                                if (this.module.inside.getValue() && state.getMaterial().isLiquid()) {
                                    ray.getResult().sideHit = ray.getResult().sideHit.getOpposite();
                                    ray = new Ray(ray.getResult(), ray.getRotations(), ray.getPos().offset(ray.getFacing()), ray.getFacing().getOpposite(), ray.getVector());
                                }
                                positionData.setValid(true);
                                positionData.setPath(ray);
                                break;
                            }
                        }
                    }
                }
            }
            if (!positionData.isValid()) {
                PathFinder.findPath(positionData, this.module.placeRange.getValue(), entities, this.module.obbyTrace.getValue(), helper, Blocks.OBSIDIAN.getDefaultState(), PathFinder.CHECK, ignore);
            }
            if (!positionData.isValid() || positionData.getPath() == null) {
                continue;
            }
            if (positionData.getPath().length > maxPath) {
                continue;
            }
            for (final Ray ray2 : positionData.getPath()) {
                helper.addBlockState(ray2.getPos().offset(ray2.getFacing()), Blocks.OBSIDIAN.getDefaultState());
            }
            if (!preSelf) {
                self = this.module.damageHelper.getObbyDamage(pos, helper);
                if (this.checkSelfDamage(self)) {
                    continue;
                }
                positionData.setSelfDamage(self);
            }
            if (this.module.antiFriendPop.getValue().shouldCalc(AntiFriendPop.Place)) {
                boolean poppingFriend = false;
                for (final EntityPlayer friend : friends) {
                    final float damage = this.module.damageHelper.getObbyDamage(pos, (EntityLivingBase)friend, helper);
                    if (damage > EntityUtil.getHealth((EntityLivingBase)friend)) {
                        poppingFriend = true;
                        break;
                    }
                }
                if (poppingFriend) {
                    continue;
                }
            }
            float damage2 = 0.0f;
            if (target != null) {
                positionData.setTarget(target);
                damage2 = this.module.damageHelper.getObbyDamage(pos, (EntityLivingBase)target, helper);
                if (damage2 < this.module.minDamage.getValue()) {
                    continue;
                }
            }
            else {
                for (final EntityPlayer p : filteredPlayers) {
                    final float d = this.module.damageHelper.getObbyDamage(pos, (EntityLivingBase)p, helper);
                    if (d >= this.module.minDamage.getValue()) {
                        if (d < damage2) {
                            continue;
                        }
                        damage2 = d;
                        positionData.setTarget(p);
                    }
                }
            }
            if (damage2 < this.module.minDamage.getValue()) {
                continue;
            }
            positionData.setDamage(damage2);
            final int length = positionData.getPath().length;
            if (bestData == null) {
                bestData = positionData;
                maxDamage = damage2;
                maxSelfDamage = self;
                shortest = length;
            }
            else {
                final boolean betterLen = length - this.module.maxDiff.getValue() < shortest;
                final boolean betterDmg = damage2 + this.module.maxDmgDiff.getValue() > maxDamage && damage2 - this.module.maxDmgDiff.getValue() >= this.module.minDamage.getValue();
                if ((betterLen || damage2 <= maxDamage) && (betterDmg || length >= shortest) && (!betterDmg || length != shortest || self >= maxSelfDamage)) {
                    continue;
                }
                bestData = positionData;
                if (length < shortest) {
                    shortest = length;
                }
                if (damage2 > maxDamage) {
                    maxDamage = damage2;
                }
                if (self >= maxSelfDamage) {
                    continue;
                }
                maxSelfDamage = self;
            }
        }
        return bestData;
    }
    
    private void addPositions(final Set<BlockPos> positions, final EntityPlayer player, final int fastObby) {
        final BlockPos down = PositionUtil.getPosition((Entity)player).down();
        for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
            BlockPos offset = down;
            for (int i = 0; i < fastObby; ++i) {
                offset = offset.offset(facing);
                positions.add(offset);
            }
        }
    }
    
    private boolean checkSelfDamage(final float self) {
        if (self > HelperObby.MD.getValue() && this.module.obbySafety.getValue()) {
            Managers.SAFETY.setSafe(false);
        }
        if (self > EntityUtil.getHealth((EntityLivingBase)HelperObby.mc.player) - 1.0) {
            if (this.module.obbySafety.getValue()) {
                Managers.SAFETY.setSafe(false);
            }
            return true;
        }
        return self > this.module.maxSelfPlace.getValue();
    }
    
    static {
        MD = Caches.getSetting(Safety.class, NumberSetting.class, "MaxDamage", 4.0f);
    }
}
