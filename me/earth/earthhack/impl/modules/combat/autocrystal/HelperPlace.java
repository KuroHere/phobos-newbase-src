//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.client.safety.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.math.position.*;
import me.earth.earthhack.impl.util.math.geocache.*;
import java.util.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.math.raytrace.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.util.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import me.earth.earthhack.impl.modules.*;

public class HelperPlace implements Globals
{
    private static final SettingCache<Float, NumberSetting<Float>, Safety> MD;
    private final AutoCrystal module;
    
    public HelperPlace(final AutoCrystal module) {
        this.module = module;
    }
    
    public PlaceData getData(final List<EntityPlayer> general, final List<EntityPlayer> players, final List<EntityPlayer> enemies, final List<EntityPlayer> friends, final List<Entity> entities, final float minDamage, final Set<BlockPos> blackList, final double maxY) {
        final PlaceData data = new PlaceData(minDamage);
        final EntityPlayer target = this.module.targetMode.getValue().getTarget(players, enemies, this.module.targetRange.getValue());
        if (target == null && this.module.targetMode.getValue() != Target.Damage) {
            return data;
        }
        data.setTarget(target);
        this.evaluate(data, general, friends, entities, blackList, maxY);
        data.addAllCorrespondingData();
        return data;
    }
    
    private void evaluate(final PlaceData data, final List<EntityPlayer> players, final List<EntityPlayer> friends, final List<Entity> entities, final Set<BlockPos> blackList, final double maxY) {
        final boolean obby = this.module.obsidian.getValue() && this.module.obbyTimer.passed(this.module.obbyDelay.getValue()) && (InventoryUtil.isHolding(Blocks.OBSIDIAN) || (this.module.obbySwitch.getValue() && InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN, new Block[0]) != -1));
        switch (this.module.preCalc.getValue()) {
            case Damage: {
                for (final EntityPlayer player : players) {
                    this.preCalc(data, player, obby, entities, friends, blackList);
                }
            }
            case Target: {
                if (data.getTarget() == null) {
                    if (data.getData().isEmpty()) {
                        break;
                    }
                }
                else {
                    this.preCalc(data, data.getTarget(), obby, entities, friends, blackList);
                }
                for (final PositionData positionData : data.getData()) {
                    if (positionData.getMaxDamage() > data.getMinDamage() && positionData.getMaxDamage() > this.module.preCalcDamage.getValue()) {
                        return;
                    }
                }
                break;
            }
        }
        final BlockPos middle = PositionUtil.getPosition((Entity)RotationUtil.getRotationPlayer());
        for (int maxRadius = Sphere.getRadius(this.module.placeRange.getValue()), i = 1; i < maxRadius; ++i) {
            this.calc(middle.add(Sphere.get(i)), data, players, friends, entities, obby, blackList, maxY);
        }
    }
    
    private void preCalc(final PlaceData data, final EntityPlayer player, final boolean obby, final List<Entity> entities, final List<EntityPlayer> friends, final Set<BlockPos> blackList) {
        final BlockPos pos = PositionUtil.getPosition((Entity)player).down();
        for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
            final PositionData pData = this.selfCalc(data, pos.offset(facing), entities, friends, obby, blackList);
            if (pData != null) {
                this.checkPlayer(data, player, pData);
            }
        }
    }
    
    private PositionData selfCalc(final PlaceData placeData, final BlockPos pos, final List<Entity> entities, final List<EntityPlayer> friends, final boolean obby, final Set<BlockPos> blackList) {
        if (blackList.contains(pos)) {
            return null;
        }
        final PositionData data = PositionData.create(pos, obby, (this.module.rotate.getValue() != ACRotate.None && this.module.rotate.getValue() != ACRotate.Break) ? 0 : this.module.helpingBlocks.getValue(), this.module.newVer.getValue(), this.module.newVerEntities.getValue(), this.module.deathTime.getValue(), entities, this.module.lava.getValue(), this.module.water.getValue(), this.module.ignoreLavaItems.getValue());
        if (data.isBlocked() && !this.module.fallBack.getValue()) {
            return null;
        }
        if (data.isLiquid()) {
            if (!data.isLiquidValid() || (this.module.liquidRayTrace.getValue() && ((this.module.newVer.getValue() && data.getPos().getY() >= RotationUtil.getRotationPlayer().posY + 2.0) || (!this.module.newVer.getValue() && data.getPos().getY() >= RotationUtil.getRotationPlayer().posY + 1.0))) || BlockUtil.getDistanceSq(pos.up()) >= MathUtil.square(this.module.placeRange.getValue()) || BlockUtil.getDistanceSq(pos.up(2)) >= MathUtil.square(this.module.placeRange.getValue())) {
                return null;
            }
            if (data.usesObby()) {
                if (data.isObbyValid()) {
                    placeData.getLiquidObby().put(data.getPos(), data);
                }
                return null;
            }
            placeData.getLiquid().add(data);
            return null;
        }
        else {
            if (data.usesObby()) {
                if (data.isObbyValid()) {
                    placeData.getAllObbyData().put(data.getPos(), data);
                }
                return null;
            }
            if (!data.isValid()) {
                return null;
            }
            return this.validate(data, friends);
        }
    }
    
    public PositionData validate(final PositionData data, final List<EntityPlayer> friends) {
        if (BlockUtil.getDistanceSq(data.getPos()) >= MathUtil.square(this.module.placeTrace.getValue()) && this.noPlaceTrace(data.getPos())) {
            return null;
        }
        final float selfDamage = this.module.damageHelper.getDamage(data.getPos());
        if (selfDamage > EntityUtil.getHealth((EntityLivingBase)HelperPlace.mc.player) - 1.0) {
            if (!data.usesObby() && !data.isLiquid()) {
                Managers.SAFETY.setSafe(false);
            }
            if (!this.module.suicide.getValue()) {
                return null;
            }
        }
        if (selfDamage > HelperPlace.MD.getValue() && !data.usesObby() && !data.isLiquid()) {
            Managers.SAFETY.setSafe(false);
        }
        if (selfDamage > this.module.maxSelfPlace.getValue() && !this.module.override.getValue()) {
            return null;
        }
        if (this.checkFriends(data, friends)) {
            return null;
        }
        data.setSelfDamage(selfDamage);
        return data;
    }
    
    private boolean noPlaceTrace(final BlockPos pos) {
        if (this.module.smartTrace.getValue()) {
            for (final EnumFacing facing : EnumFacing.values()) {
                final Ray ray = RayTraceFactory.rayTrace((Entity)HelperPlace.mc.player, pos, facing, (IBlockAccess)HelperPlace.mc.world, Blocks.OBSIDIAN.getDefaultState(), this.module.traceWidth.getValue());
                if (ray.isLegit()) {
                    return false;
                }
            }
            return true;
        }
        return !RayTraceUtil.raytracePlaceCheck((Entity)HelperPlace.mc.player, pos);
    }
    
    private void calc(final BlockPos pos, final PlaceData data, final List<EntityPlayer> players, final List<EntityPlayer> friends, final List<Entity> entities, final boolean obby, final Set<BlockPos> blackList, final double maxY) {
        if (this.placeCheck(pos, maxY) || (data.getTarget() != null && data.getTarget().getDistanceSq(pos) > MathUtil.square(this.module.range.getValue()))) {
            return;
        }
        final PositionData positionData = this.selfCalc(data, pos, entities, friends, obby, blackList);
        if (positionData == null) {
            return;
        }
        this.calcPositionData(data, positionData, players);
    }
    
    public void calcPositionData(final PlaceData data, final PositionData positionData, final List<EntityPlayer> players) {
        boolean isAntiTotem = false;
        if (data.getTarget() == null) {
            for (final EntityPlayer player : players) {
                isAntiTotem = (this.checkPlayer(data, player, positionData) || isAntiTotem);
            }
        }
        else {
            isAntiTotem = this.checkPlayer(data, data.getTarget(), positionData);
        }
        if (positionData.isForce()) {
            final ForcePosition forcePosition = new ForcePosition(positionData);
            for (final EntityPlayer forced : positionData.getForced()) {
                data.addForceData(forced, forcePosition);
            }
        }
        if (isAntiTotem) {
            data.addAntiTotem(new AntiTotemData(positionData));
        }
        if (positionData.getFacePlacer() != null || positionData.getMaxDamage() > data.getMinDamage()) {
            data.getData().add(positionData);
        }
    }
    
    private boolean placeCheck(final BlockPos pos, final double maxY) {
        return pos.getY() < 0 || pos.getY() - 1 >= maxY || BlockUtil.getDistanceSq(pos) > MathUtil.square(this.module.placeRange.getValue()) || (BlockUtil.getDistanceSq(pos) > MathUtil.square(this.module.pbTrace.getValue()) && !RayTraceUtil.canBeSeen(new Vec3d(pos.getX() + 0.5, pos.getY() + 2.7, pos.getZ() + 0.5), (Entity)HelperPlace.mc.player));
    }
    
    private boolean checkFriends(final PositionData data, final List<EntityPlayer> friends) {
        if (!this.module.antiFriendPop.getValue().shouldCalc(AntiFriendPop.Place)) {
            return false;
        }
        for (final EntityPlayer friend : friends) {
            if (friend != null && !EntityUtil.isDead((Entity)friend) && this.module.damageHelper.getDamage(data.getPos(), (EntityLivingBase)friend) > EntityUtil.getHealth((EntityLivingBase)friend) - 0.5f) {
                return true;
            }
        }
        return false;
    }
    
    private boolean checkPlayer(final PlaceData data, final EntityPlayer player, final PositionData positionData) {
        final BlockPos pos = positionData.getPos();
        if (data.getTarget() == null && player.getDistanceSq(pos) > MathUtil.square(this.module.range.getValue())) {
            return false;
        }
        boolean result = false;
        final float health = EntityUtil.getHealth((EntityLivingBase)player);
        final float damage = this.module.damageHelper.getDamage(pos, (EntityLivingBase)player);
        if (this.module.antiTotem.getValue() && !positionData.usesObby() && !positionData.isLiquid()) {
            if (this.module.antiTotemHelper.isDoublePoppable(player)) {
                if (damage > this.module.popDamage.getValue()) {
                    data.addCorrespondingData(player, positionData);
                }
                else if (damage < health + this.module.maxTotemOffset.getValue() && damage > health + this.module.minTotemOffset.getValue()) {
                    positionData.addAntiTotem(player);
                    result = true;
                }
            }
            else if (this.module.forceAntiTotem.getValue() && Managers.COMBAT.lastPop((Entity)player) > 500L) {
                if (damage > this.module.popDamage.getValue()) {
                    data.confirmHighDamageForce(player);
                }
                if (damage > 0.0f && damage < this.module.totemHealth.getValue() + this.module.maxTotemOffset.getValue()) {
                    data.confirmPossibleAntiTotem(player);
                }
                final float force = health - damage;
                if (force > 0.0f && force < this.module.totemHealth.getValue()) {
                    positionData.addForcePlayer(player);
                    if (force < positionData.getMinDiff()) {
                        positionData.setMinDiff(force);
                    }
                }
            }
        }
        if (damage > this.module.minFaceDmg.getValue() && (health < this.module.facePlace.getValue() || ((IEntityLivingBase)player).getLowestDurability() <= this.module.armorPlace.getValue())) {
            positionData.setFacePlacer(player);
        }
        if (damage > positionData.getMaxDamage()) {
            positionData.setDamage(damage);
            positionData.setTarget(player);
        }
        return result;
    }
    
    static {
        MD = Caches.getSetting(Safety.class, NumberSetting.class, "MaxDamage", 4.0f);
    }
}
