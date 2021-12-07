// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.util;

public class CrystalTimeStamp extends TimeStamp
{
    private final float damage;
    
    public CrystalTimeStamp(final float damage) {
        this.damage = damage;
    }
    
    public float getDamage() {
        return this.damage;
    }
}
