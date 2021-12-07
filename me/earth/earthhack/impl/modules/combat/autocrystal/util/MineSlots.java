// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.util;

public class MineSlots
{
    private final int blockSlot;
    private final int toolSlot;
    private final float damage;
    
    public MineSlots(final int blockSlot, final int toolSlot, final float damage) {
        this.blockSlot = blockSlot;
        this.toolSlot = toolSlot;
        this.damage = damage;
    }
    
    public int getBlockSlot() {
        return this.blockSlot;
    }
    
    public int getToolSlot() {
        return this.toolSlot;
    }
    
    public float getDamage() {
        return this.damage;
    }
}
