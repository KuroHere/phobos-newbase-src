//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.util;

import net.minecraft.entity.*;

public class CrystalData implements Comparable<CrystalData>
{
    private final Entity crystal;
    private float selfDmg;
    private float damage;
    private float[] rotations;
    private double angle;
    
    public CrystalData(final Entity crystal) {
        this.crystal = crystal;
    }
    
    public Entity getCrystal() {
        return this.crystal;
    }
    
    public void setSelfDmg(final float damage) {
        this.selfDmg = damage;
    }
    
    public void setDamage(final float damage) {
        this.damage = damage;
    }
    
    public float getSelfDmg() {
        return this.selfDmg;
    }
    
    public float getDamage() {
        return this.damage;
    }
    
    public float[] getRotations() {
        return this.rotations;
    }
    
    public double getAngle() {
        return this.angle;
    }
    
    public boolean hasCachedRotations() {
        return this.rotations != null;
    }
    
    public void cacheRotations(final float[] rotations, final double angle) {
        this.rotations = rotations;
        this.angle = angle;
    }
    
    @Override
    public int compareTo(final CrystalData o) {
        if (Math.abs(o.damage - this.damage) < 1.0f) {
            return Float.compare(this.selfDmg, o.selfDmg);
        }
        return Float.compare(o.damage, this.damage);
    }
    
    @Override
    public int hashCode() {
        return this.crystal.getEntityId();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof CrystalData && this.hashCode() == o.hashCode();
    }
}
