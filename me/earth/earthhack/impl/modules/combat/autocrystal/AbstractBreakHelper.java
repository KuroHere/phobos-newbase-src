// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import java.util.*;
import net.minecraft.entity.*;

public abstract class AbstractBreakHelper<T extends CrystalData> implements IBreakHelper<T>, Globals
{
    protected final AutoCrystal module;
    
    public AbstractBreakHelper(final AutoCrystal module) {
        this.module = module;
    }
    
    protected abstract T newCrystalData(final Entity p0);
    
    protected abstract boolean isValid(final Entity p0, final T p1);
    
    protected abstract boolean calcSelf(final Entity p0, final T p1);
    
    protected abstract void calcCrystal(final BreakData<T> p0, final T p1, final Entity p2, final List<EntityPlayer> p3);
    
    @Override
    public BreakData<T> getData(final Collection<T> dataSet, final List<Entity> entities, final List<EntityPlayer> players, final List<EntityPlayer> friends) {
        final BreakData<T> data = this.newData(dataSet);
        for (final Entity crystal : entities) {
            if (crystal instanceof EntityEnderCrystal) {
                if (EntityUtil.isDead(crystal)) {
                    continue;
                }
                final T crystalData = this.newCrystalData(crystal);
                if (this.calcSelf(crystal, crystalData)) {
                    continue;
                }
                if (!this.isValid(crystal, crystalData)) {
                    continue;
                }
                if (this.module.antiFriendPop.getValue().shouldCalc(AntiFriendPop.Break) && this.checkFriendPop(crystal, friends)) {
                    continue;
                }
                this.calcCrystal(data, crystalData, crystal, players);
            }
        }
        return data;
    }
    
    protected boolean checkFriendPop(final Entity entity, final List<EntityPlayer> friends) {
        for (final EntityPlayer friend : friends) {
            final float fDamage = this.module.damageHelper.getDamage(entity, (EntityLivingBase)friend);
            if (fDamage > EntityUtil.getHealth((EntityLivingBase)friend) - 1.0f) {
                return true;
            }
        }
        return false;
    }
}
