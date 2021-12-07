// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.ducks.entity;

import net.minecraft.util.*;

public interface IEntityOtherPlayerMP
{
    default boolean attackEntitySuper(final DamageSource source, final float amount) {
        return true;
    }
    
    default boolean returnFromSuperAttack(final DamageSource source, final float amount) {
        return this.attackEntitySuper(source, amount);
    }
    
    default boolean shouldAttackSuper() {
        return false;
    }
}
