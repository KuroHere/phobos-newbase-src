//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import java.util.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.core.mixins.item.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.raytrace.*;
import net.minecraft.block.*;

public class DamageUtil implements Globals
{
    public static boolean isSharper(final ItemStack stack, final int level) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack) > level;
    }
    
    public static boolean canBreakWeakness(final boolean checkStack) {
        if (!DamageUtil.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
            return true;
        }
        int strengthAmp = 0;
        final PotionEffect effect = DamageUtil.mc.player.getActivePotionEffect(MobEffects.STRENGTH);
        if (effect != null) {
            strengthAmp = effect.getAmplifier();
        }
        return strengthAmp >= 1 || (checkStack && canBreakWeakness(DamageUtil.mc.player.getHeldItemMainhand()));
    }
    
    public static boolean isWeaknessed() {
        try {
            return !canBreakWeakness(true);
        }
        catch (Throwable t) {
            return true;
        }
    }
    
    public static boolean cacheLowestDura(final EntityLivingBase base) {
        final IEntityLivingBase access = (IEntityLivingBase)base;
        final float before = access.getLowestDurability();
        access.setLowestDura(Float.MAX_VALUE);
        try {
            boolean isNaked = true;
            for (final ItemStack stack : base.getArmorInventoryList()) {
                if (!stack.func_190926_b()) {
                    isNaked = false;
                    final float damage = getPercent(stack);
                    if (damage >= access.getLowestDurability()) {
                        continue;
                    }
                    access.setLowestDura(damage);
                }
            }
            return isNaked;
        }
        catch (Throwable t) {
            t.printStackTrace();
            access.setLowestDura(before);
            return false;
        }
    }
    
    public static boolean canBreakWeakness(final ItemStack stack) {
        if (stack.getItem() instanceof ItemSword) {
            return true;
        }
        if (stack.getItem() instanceof ItemTool) {
            final IItemTool tool = (IItemTool)stack.getItem();
            return tool.getAttackDamage() > 4.0f;
        }
        return false;
    }
    
    public static int findAntiWeakness() {
        int slot = -1;
        for (int i = 8; i > -1 && (!canBreakWeakness(DamageUtil.mc.player.inventory.getStackInSlot(i)) || DamageUtil.mc.player.inventory.currentItem != (slot = i)); --i) {}
        return slot;
    }
    
    public static int getDamage(final ItemStack stack) {
        return stack.getMaxDamage() - stack.getItemDamage();
    }
    
    public static float getPercent(final ItemStack stack) {
        return getDamage(stack) / (float)stack.getMaxDamage() * 100.0f;
    }
    
    public static float calculate(final Entity crystal) {
        return calculate(crystal.posX, crystal.posY, crystal.posZ, (EntityLivingBase)RotationUtil.getRotationPlayer());
    }
    
    public static float calculate(final BlockPos pos) {
        return calculate(pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f, (EntityLivingBase)RotationUtil.getRotationPlayer());
    }
    
    public static float calculate(final BlockPos p, final EntityLivingBase base) {
        return calculate(p.getX() + 0.5f, p.getY() + 1, p.getZ() + 0.5f, base);
    }
    
    public static float calculate(final BlockPos p, final EntityLivingBase base, final IBlockAccess world) {
        return calculate(p.getX() + 0.5f, p.getY() + 1, p.getZ() + 0.5f, base.getEntityBoundingBox(), base, world, false);
    }
    
    public static float calculate(final Entity crystal, final EntityLivingBase base) {
        return calculate(crystal.posX, crystal.posY, crystal.posZ, base);
    }
    
    public static float calculate(final double x, final double y, final double z, final EntityLivingBase base) {
        return calculate(x, y, z, base.getEntityBoundingBox(), base);
    }
    
    public static float calculate(final double x, final double y, final double z, final AxisAlignedBB bb, final EntityLivingBase base) {
        return calculate(x, y, z, bb, base, false);
    }
    
    public static float calculate(final double x, final double y, final double z, final AxisAlignedBB bb, final EntityLivingBase base, final boolean terrainCalc) {
        return calculate(x, y, z, bb, base, (IBlockAccess)DamageUtil.mc.world, terrainCalc);
    }
    
    public static float calculate(final double x, final double y, final double z, final AxisAlignedBB bb, final EntityLivingBase base, final IBlockAccess world, final boolean terrainCalc) {
        return calculate(x, y, z, bb, base, world, terrainCalc, false);
    }
    
    public static float calculate(final double x, final double y, final double z, final AxisAlignedBB bb, final EntityLivingBase base, final IBlockAccess world, final boolean terrainCalc, final boolean anvils) {
        return calculate(x, y, z, bb, base, world, terrainCalc, anvils, 6.0f);
    }
    
    public static float calculate(final double x, final double y, final double z, final AxisAlignedBB bb, final EntityLivingBase base, final IBlockAccess world, final boolean terrainCalc, final boolean anvils, final float power) {
        double distance = base.getDistance(x, y, z) / 12.0;
        if (distance > 1.0) {
            return 0.0f;
        }
        final double density = getBlockDensity(new Vec3d(x, y, z), bb, world, true, true, terrainCalc, anvils);
        final double densityDistance;
        distance = (densityDistance = (1.0 - distance) * density);
        float damage = getDifficultyMultiplier((float)((densityDistance * densityDistance + distance) / 2.0 * 7.0 * 12.0 + 1.0));
        final DamageSource damageSource = DamageSource.causeExplosionDamage(new Explosion((World)DamageUtil.mc.world, (Entity)DamageUtil.mc.player, x, y, z, power, false, true));
        final ICachedDamage cache = (ICachedDamage)base;
        final int armorValue = cache.getArmorValue();
        final float toughness = cache.getArmorToughness();
        damage = CombatRules.getDamageAfterAbsorb(damage, (float)armorValue, toughness);
        final PotionEffect resistance = base.getActivePotionEffect(MobEffects.RESISTANCE);
        if (resistance != null) {
            damage = damage * (25 - (resistance.getAmplifier() + 1) * 5) / 25.0f;
        }
        if (damage <= 0.0f) {
            return 0.0f;
        }
        final int modifierDamage = cache.getExplosionModifier(damageSource);
        if (modifierDamage > 0) {
            damage = CombatRules.getDamageAfterMagicAbsorb(damage, (float)modifierDamage);
        }
        return Math.max(damage, 0.0f);
    }
    
    public static float getDifficultyMultiplier(final float distance) {
        switch (DamageUtil.mc.world.getDifficulty()) {
            case PEACEFUL: {
                return 0.0f;
            }
            case EASY: {
                return Math.min(distance / 2.0f + 1.0f, distance);
            }
            case HARD: {
                return distance * 3.0f / 2.0f;
            }
            default: {
                return distance;
            }
        }
    }
    
    public static float getBlockDensity(final Vec3d vec, final AxisAlignedBB bb, final IBlockAccess world, final boolean ignoreWebs, final boolean ignoreBeds, final boolean terrainCalc, final boolean anvils) {
        final double x = 1.0 / ((bb.maxX - bb.minX) * 2.0 + 1.0);
        final double y = 1.0 / ((bb.maxY - bb.minY) * 2.0 + 1.0);
        final double z = 1.0 / ((bb.maxZ - bb.minZ) * 2.0 + 1.0);
        final double xFloor = (1.0 - Math.floor(1.0 / x) * x) / 2.0;
        final double zFloor = (1.0 - Math.floor(1.0 / z) * z) / 2.0;
        if (x >= 0.0 && y >= 0.0 && z >= 0.0) {
            int air = 0;
            int traced = 0;
            for (float a = 0.0f; a <= 1.0f; a += (float)x) {
                for (float b = 0.0f; b <= 1.0f; b += (float)y) {
                    for (float c = 0.0f; c <= 1.0f; c += (float)z) {
                        final double xOff = bb.minX + (bb.maxX - bb.minX) * a;
                        final double yOff = bb.minY + (bb.maxY - bb.minY) * b;
                        final double zOff = bb.minZ + (bb.maxZ - bb.minZ) * c;
                        final RayTraceResult result = rayTraceBlocks(new Vec3d(xOff + xFloor, yOff, zOff + zFloor), vec, world, false, false, false, ignoreWebs, ignoreBeds, terrainCalc, anvils);
                        if (result == null) {
                            ++air;
                        }
                        ++traced;
                    }
                }
            }
            return air / (float)traced;
        }
        return 0.0f;
    }
    
    public static RayTraceResult rayTraceBlocks(final Vec3d start, final Vec3d end, final IBlockAccess world, final boolean stopOnLiquid, final boolean ignoreNoBox, final boolean lastUncollidableBlock, final boolean ignoreWebs, final boolean ignoreBeds, final boolean terrainCalc, final boolean anvils) {
        return RayTracer.trace((World)DamageUtil.mc.world, world, start, end, stopOnLiquid, ignoreNoBox, lastUncollidableBlock, (b, p) -> ((!terrainCalc || b.getExplosionResistance((Entity)DamageUtil.mc.player) >= 100.0f || p.distanceSq(end.xCoord, end.yCoord, end.zCoord) > 36.0) && (!ignoreBeds || !(b instanceof BlockBed)) && (!ignoreWebs || !(b instanceof BlockWeb))) || (anvils && b instanceof BlockAnvil));
    }
}
