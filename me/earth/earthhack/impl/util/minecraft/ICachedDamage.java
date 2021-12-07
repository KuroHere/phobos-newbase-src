// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft;

import me.earth.earthhack.api.setting.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.api.setting.settings.*;

public interface ICachedDamage
{
    public static final Setting<Boolean> SHOULD_CACHE = new BooleanSetting("CacheAttributes", true);
    
    int getArmorValue();
    
    float getArmorToughness();
    
    int getExplosionModifier(final DamageSource p0);
    
    default boolean shouldCache() {
        return ICachedDamage.SHOULD_CACHE.getValue() && this instanceof EntityPlayer;
    }
}
