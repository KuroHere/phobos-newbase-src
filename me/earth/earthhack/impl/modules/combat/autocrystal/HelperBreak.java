//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.client.safety.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.util.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.*;

public class HelperBreak extends AbstractBreakHelper<CrystalData>
{
    private static final SettingCache<Float, NumberSetting<Float>, Safety> MD;
    
    public HelperBreak(final AutoCrystal module) {
        super(module);
    }
    
    @Override
    public BreakData<CrystalData> newData(final Collection<CrystalData> data) {
        return new BreakData<CrystalData>(data);
    }
    
    @Override
    protected CrystalData newCrystalData(final Entity crystal) {
        return new CrystalData(crystal);
    }
    
    @Override
    protected boolean isValid(final Entity crystal, final CrystalData data) {
        final double distance = Managers.POSITION.getDistanceSq(crystal);
        return (distance <= MathUtil.square(this.module.breakTrace.getValue()) || Managers.POSITION.canEntityBeSeen(crystal)) && distance <= MathUtil.square(this.module.breakRange.getValue());
    }
    
    @Override
    protected boolean calcSelf(final Entity crystal, final CrystalData data) {
        final float selfDamage = this.module.damageHelper.getDamage(crystal);
        data.setSelfDmg(selfDamage);
        if (selfDamage > EntityUtil.getHealth((EntityLivingBase)HelperBreak.mc.player) - 1.0f) {
            Managers.SAFETY.setSafe(false);
            if (!this.module.suicide.getValue()) {
                return true;
            }
        }
        if (selfDamage > HelperBreak.MD.getValue()) {
            Managers.SAFETY.setSafe(false);
        }
        return false;
    }
    
    @Override
    protected void calcCrystal(final BreakData<CrystalData> data, final CrystalData crystalData, final Entity crystal, final List<EntityPlayer> players) {
        boolean highSelf = crystalData.getSelfDmg() > this.module.maxSelfBreak.getValue();
        if (!this.module.suicide.getValue() && !this.module.overrideBreak.getValue() && highSelf) {
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
                killing = true;
                highSelf = false;
            }
            if (playerDamage <= damage) {
                continue;
            }
            damage = playerDamage;
        }
        if (this.module.antiTotem.getValue() && crystal.getPosition().down().equals((Object)this.module.antiTotemHelper.getTargetPos())) {
            data.setAntiTotem(crystal);
        }
        if (!highSelf && (!this.module.efficient.getValue() || damage > crystalData.getSelfDmg() || killing)) {
            data.register(crystalData);
        }
    }
    
    static {
        MD = Caches.getSetting(Safety.class, Setting.class, "MaxDamage", 4.0f);
    }
}
