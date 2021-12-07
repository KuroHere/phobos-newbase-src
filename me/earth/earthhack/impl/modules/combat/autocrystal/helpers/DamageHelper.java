//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.helpers;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.minecraft.blocks.states.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.world.*;

public class DamageHelper implements Globals
{
    private final Setting<Boolean> terrainCalc;
    private final Setting<Integer> bExtrapolation;
    private final Setting<Integer> pExtrapolation;
    private final Setting<Boolean> selfExtrapolation;
    private final Setting<Boolean> obbyTerrain;
    private final PositionHelper positionHelper;
    
    public DamageHelper(final PositionHelper positionHelper, final Setting<Boolean> terrainCalc, final Setting<Integer> extrapolation, final Setting<Integer> bExtrapolation, final Setting<Boolean> selfExtrapolation, final Setting<Boolean> obbyTerrain) {
        this.positionHelper = positionHelper;
        this.terrainCalc = terrainCalc;
        this.pExtrapolation = extrapolation;
        this.bExtrapolation = bExtrapolation;
        this.selfExtrapolation = selfExtrapolation;
        this.obbyTerrain = obbyTerrain;
    }
    
    public float getDamage(final Entity crystal) {
        return this.getDamage(crystal.posX, crystal.posY, crystal.posZ, Managers.POSITION.getBB(), (EntityLivingBase)DamageHelper.mc.player);
    }
    
    public float getDamage(final Entity crystal, final AxisAlignedBB bb) {
        return DamageUtil.calculate(crystal.posX, crystal.posY, crystal.posZ, bb, (EntityLivingBase)DamageHelper.mc.player);
    }
    
    public float getDamage(final Entity crystal, final EntityLivingBase base) {
        if (this.bExtrapolation.getValue() != 0) {
            return this.getDamage(crystal.posX, crystal.posY, crystal.posZ, this.extrapolateEntity((Entity)base, this.bExtrapolation.getValue()), base);
        }
        return this.getDamage(crystal.posX, crystal.posY, crystal.posZ, base);
    }
    
    public float getDamage(final BlockPos pos) {
        return this.getDamage(pos, (EntityLivingBase)RotationUtil.getRotationPlayer());
    }
    
    public float getDamage(final BlockPos pos, final EntityLivingBase base) {
        if (this.pExtrapolation.getValue() != 0 && (this.selfExtrapolation.getValue() || !base.equals((Object)RotationUtil.getRotationPlayer()))) {
            return this.getDamage(pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f, this.extrapolateEntity((Entity)base, this.pExtrapolation.getValue()), base);
        }
        return this.getDamage(pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f, base);
    }
    
    public float getDamage(final double x, final double y, final double z, final EntityLivingBase base) {
        return this.getDamage(x, y, z, base.getEntityBoundingBox(), base);
    }
    
    public float getDamage(final double x, final double y, final double z, final AxisAlignedBB bb, final EntityLivingBase base) {
        return DamageUtil.calculate(x, y, z, bb, base, this.terrainCalc.getValue());
    }
    
    public float getObbyDamage(final BlockPos pos, final IBlockStateHelper world) {
        AxisAlignedBB bb;
        if (this.selfExtrapolation.getValue()) {
            bb = this.extrapolateEntity((Entity)RotationUtil.getRotationPlayer(), this.pExtrapolation.getValue());
        }
        else {
            bb = RotationUtil.getRotationPlayer().getEntityBoundingBox();
        }
        return this.getObbyDamage(pos, (EntityLivingBase)DamageHelper.mc.player, bb, world);
    }
    
    public float getObbyDamage(final BlockPos pos, final EntityLivingBase base, final IBlockStateHelper world) {
        return this.getObbyDamage(pos, base, this.extrapolateEntity((Entity)base, this.pExtrapolation.getValue()), world);
    }
    
    public float getObbyDamage(final BlockPos pos, final EntityLivingBase base, final AxisAlignedBB bb, final IBlockStateHelper world) {
        return DamageUtil.calculate(pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f, bb, base, (IBlockAccess)world, this.obbyTerrain.getValue());
    }
    
    public AxisAlignedBB extrapolateEntity(final Entity entity, final int ticks) {
        if (ticks == 0) {
            return entity.getEntityBoundingBox();
        }
        final MotionTracker tracker = this.positionHelper.getTrackerFromEntity(entity);
        if (tracker == null) {
            return entity.getEntityBoundingBox();
        }
        final MotionTracker copy = new MotionTracker((World)DamageHelper.mc.world, tracker);
        for (int i = 0; i < ticks; ++i) {
            copy.updateSilent();
        }
        return copy.getEntityBoundingBox();
    }
}
