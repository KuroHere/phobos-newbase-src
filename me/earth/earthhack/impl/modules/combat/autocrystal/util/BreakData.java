// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.util;

import java.util.*;
import net.minecraft.entity.*;

public class BreakData<T extends CrystalData>
{
    private final Collection<T> data;
    private float fallBackDmg;
    private Entity antiTotem;
    private Entity fallBack;
    
    public BreakData(final Collection<T> data) {
        this.fallBackDmg = Float.MAX_VALUE;
        this.data = data;
    }
    
    public void register(final T dataIn) {
        if (dataIn.getSelfDmg() < this.fallBackDmg) {
            this.fallBack = dataIn.getCrystal();
            this.fallBackDmg = dataIn.getSelfDmg();
        }
        this.data.add(dataIn);
    }
    
    public float getFallBackDmg() {
        return this.fallBackDmg;
    }
    
    public Entity getAntiTotem() {
        return this.antiTotem;
    }
    
    public void setAntiTotem(final Entity antiTotem) {
        this.antiTotem = antiTotem;
    }
    
    public Entity getFallBack() {
        return this.fallBack;
    }
    
    public Collection<T> getData() {
        return this.data;
    }
}
