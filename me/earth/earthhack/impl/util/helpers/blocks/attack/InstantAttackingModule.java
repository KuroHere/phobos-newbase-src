// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.blocks.attack;

import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.math.*;

public interface InstantAttackingModule extends AttackingModule
{
    default void postAttack(final EntityEnderCrystal entity) {
    }
    
    boolean shouldAttack(final EntityEnderCrystal p0);
    
    Passable getTimer();
    
    int getBreakDelay();
    
    int getCooldown();
}
