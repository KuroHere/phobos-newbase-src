// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.helpers;

import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import me.earth.earthhack.impl.util.minecraft.*;

public class WeaknessHelper
{
    private final Setting<AntiWeakness> antiWeakness;
    private final Setting<Integer> cooldown;
    private boolean weaknessed;
    
    public WeaknessHelper(final Setting<AntiWeakness> antiWeakness, final Setting<Integer> cooldown) {
        this.antiWeakness = antiWeakness;
        this.cooldown = cooldown;
    }
    
    public void updateWeakness() {
        this.weaknessed = !DamageUtil.canBreakWeakness(true);
    }
    
    public boolean isWeaknessed() {
        return this.weaknessed;
    }
    
    public boolean canSwitch() {
        return this.antiWeakness.getValue() == AntiWeakness.Switch && this.cooldown.getValue() == 0 && this.weaknessed;
    }
}
