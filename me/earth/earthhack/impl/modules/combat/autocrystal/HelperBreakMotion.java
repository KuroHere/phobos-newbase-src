//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.client.safety.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.*;
import java.util.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.util.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.*;

public class HelperBreakMotion extends AbstractBreakHelper<CrystalDataMotion>
{
    private static final SettingCache<Float, NumberSetting<Float>, Safety> MD;
    
    public HelperBreakMotion(final AutoCrystal module) {
        super(module);
    }
    
    @Override
    public BreakData<CrystalDataMotion> newData(final Collection<CrystalDataMotion> data) {
        return new BreakData<CrystalDataMotion>(data);
    }
    
    @Override
    protected CrystalDataMotion newCrystalData(final Entity crystal) {
        return new CrystalDataMotion(crystal);
    }
    
    @Override
    protected boolean isValid(final Entity crystal, final CrystalDataMotion data) {
        double distance = Managers.POSITION.getDistanceSq(crystal);
        if (distance >= MathUtil.square(this.module.breakRange.getValue()) || (distance >= MathUtil.square(this.module.breakTrace.getValue()) && !Managers.POSITION.canEntityBeSeen(crystal))) {
            data.invalidateTiming(CrystalDataMotion.Timing.PRE);
        }
        final EntityPlayer e = RotationUtil.getRotationPlayer();
        distance = e.getDistanceSqToEntity(crystal);
        if (distance >= MathUtil.square(this.module.breakRange.getValue()) || (distance >= MathUtil.square(this.module.breakTrace.getValue()) && !e.canEntityBeSeen(crystal))) {
            data.invalidateTiming(CrystalDataMotion.Timing.POST);
        }
        return data.getTiming() != CrystalDataMotion.Timing.NONE;
    }
    
    @Override
    protected boolean calcSelf(final Entity crystal, final CrystalDataMotion data) {
        boolean breakCase = true;
        switch (data.getTiming()) {
            case BOTH: {
                breakCase = false;
            }
            case PRE: {
                final float preDamage = this.module.damageHelper.getDamage(crystal);
                data.setSelfDmg(preDamage);
                if (preDamage > EntityUtil.getHealth((EntityLivingBase)HelperBreakMotion.mc.player) - 1.0f && !this.module.suicide.getValue()) {
                    data.invalidateTiming(CrystalDataMotion.Timing.PRE);
                }
                if (breakCase) {
                    break;
                }
            }
            case POST: {
                final float postDamage = this.module.damageHelper.getDamage(crystal, RotationUtil.getRotationPlayer().getEntityBoundingBox());
                data.setPostSelf(postDamage);
                if (postDamage > EntityUtil.getHealth((EntityLivingBase)HelperBreakMotion.mc.player) - 1.0f) {
                    Managers.SAFETY.setSafe(false);
                    if (!this.module.suicide.getValue()) {
                        data.invalidateTiming(CrystalDataMotion.Timing.POST);
                    }
                }
                if (postDamage > HelperBreakMotion.MD.getValue()) {
                    Managers.SAFETY.setSafe(false);
                    break;
                }
                break;
            }
        }
        return data.getTiming() == CrystalDataMotion.Timing.NONE;
    }
    
    @Override
    protected void calcCrystal(final BreakData<CrystalDataMotion> data, final CrystalDataMotion crystalData, final Entity crystal, final List<EntityPlayer> players) {
        boolean highPreSelf = crystalData.getSelfDmg() > this.module.maxSelfBreak.getValue();
        boolean highPostSelf = crystalData.getPostSelf() > this.module.maxSelfBreak.getValue();
        if (!this.module.suicide.getValue() && !this.module.overrideBreak.getValue() && highPreSelf && highPostSelf) {
            crystalData.invalidateTiming(CrystalDataMotion.Timing.PRE);
            crystalData.invalidateTiming(CrystalDataMotion.Timing.POST);
            return;
        }
        float damage = 0.0f;
        boolean killing = false;
        for (final EntityPlayer player : players) {
            if (player.getDistanceSqToEntity(crystal) > 144.0) {
                continue;
            }
            final float playerDamage = this.module.damageHelper.getDamage(crystal, (EntityLivingBase)player);
            if (playerDamage > crystalData.getDamage()) {
                crystalData.setDamage(playerDamage);
            }
            if (playerDamage > EntityUtil.getHealth((EntityLivingBase)player) + 1.0f) {
                highPreSelf = false;
                highPostSelf = false;
                killing = true;
            }
            if (playerDamage <= damage) {
                continue;
            }
            damage = playerDamage;
        }
        if (this.module.antiTotem.getValue() && crystal.getPosition().down().equals((Object)this.module.antiTotemHelper.getTargetPos())) {
            data.setAntiTotem(crystal);
        }
        if (highPreSelf) {
            crystalData.invalidateTiming(CrystalDataMotion.Timing.PRE);
        }
        if (highPostSelf) {
            crystalData.invalidateTiming(CrystalDataMotion.Timing.POST);
        }
        if ((crystalData.getTiming() != CrystalDataMotion.Timing.NONE && (!this.module.efficient.getValue() || damage > crystalData.getSelfDmg())) || killing) {
            data.register(crystalData);
        }
    }
    
    static {
        MD = Caches.getSetting(Safety.class, Setting.class, "MaxDamage", 4.0f);
    }
}
