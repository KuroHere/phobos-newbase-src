// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.util;

public class WeaknessSwitch
{
    public static final WeaknessSwitch NONE;
    public static final WeaknessSwitch INVALID;
    private final int slot;
    private final boolean needsSwitch;
    
    public WeaknessSwitch(final int slot, final boolean needsSwitch) {
        this.slot = slot;
        this.needsSwitch = needsSwitch;
    }
    
    public int getSlot() {
        return this.slot;
    }
    
    public boolean needsSwitch() {
        return this.needsSwitch;
    }
    
    static {
        NONE = new WeaknessSwitch(-1, false);
        INVALID = new WeaknessSwitch(-1, true);
    }
}
