// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autoarmor.util;

import net.minecraft.item.*;

public class DamageStack implements Comparable<DamageStack>
{
    private final ItemStack stack;
    private final float damage;
    private final int slot;
    
    public DamageStack(final ItemStack stack, final float damage, final int slot) {
        this.stack = stack;
        this.damage = damage;
        this.slot = slot;
    }
    
    public int getSlot() {
        return this.slot;
    }
    
    public float getDamage() {
        return this.damage;
    }
    
    public ItemStack getStack() {
        return this.stack;
    }
    
    @Override
    public int compareTo(final DamageStack o) {
        return Float.compare(o.damage, this.damage);
    }
}
