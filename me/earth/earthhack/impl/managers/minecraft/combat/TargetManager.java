// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft.combat;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.*;
import me.earth.earthhack.impl.modules.combat.killaura.*;
import me.earth.earthhack.impl.modules.combat.autotrap.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.modules.*;

public class TargetManager
{
    private static final ModuleCache<AutoCrystal> AUTO_CRYSTAL;
    private static final ModuleCache<KillAura> KILL_AURA;
    private static final ModuleCache<AutoTrap> AUTO_TRAP;
    
    public Entity getKillAura() {
        return TargetManager.KILL_AURA.returnIfPresent(KillAura::getTarget, null);
    }
    
    public EntityPlayer getAutoTrap() {
        return TargetManager.AUTO_TRAP.returnIfPresent(AutoTrap::getTarget, null);
    }
    
    public EntityPlayer getAutoCrystal() {
        return TargetManager.AUTO_CRYSTAL.returnIfPresent(AutoCrystal::getTarget, null);
    }
    
    public Entity getCrystal() {
        return TargetManager.AUTO_CRYSTAL.returnIfPresent(AutoCrystal::getCrystal, null);
    }
    
    static {
        AUTO_CRYSTAL = Caches.getModule(AutoCrystal.class);
        KILL_AURA = Caches.getModule(KillAura.class);
        AUTO_TRAP = Caches.getModule(AutoTrap.class);
    }
}
