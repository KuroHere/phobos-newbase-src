// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autoarmor.util;

import net.minecraft.item.*;

public class LevelStack extends DamageStack
{
    private final int level;
    
    public LevelStack(final ItemStack stack, final float damage, final int slot, final int level) {
        super(stack, damage, slot);
        this.level = level;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public boolean isBetter(final float damage, final float min, final int level, final boolean prio) {
        if (level > this.level) {
            return false;
        }
        if (level < this.level) {
            return true;
        }
        if (prio) {
            return damage <= min || damage >= this.getDamage();
        }
        return damage <= this.getDamage();
    }
}
