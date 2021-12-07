//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.helpers;

import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.managers.*;

public class DamageSyncHelper
{
    private final DiscreteTimer discreteTimer;
    private final StopWatch timer;
    private final Setting<Integer> syncDelay;
    private final Setting<Boolean> discrete;
    private final Setting<Boolean> danger;
    private final Confirmer confirmer;
    private float lastDamage;
    
    public DamageSyncHelper(final EventBus eventBus, final Setting<Boolean> discrete, final Setting<Integer> syncDelay, final Setting<Boolean> danger) {
        this.discreteTimer = new GuardTimer(1000L, 5L);
        this.timer = new StopWatch();
        this.danger = danger;
        this.confirmer = Confirmer.createAndSubscribe(eventBus);
        this.syncDelay = syncDelay;
        this.discrete = discrete;
        this.discreteTimer.reset(syncDelay.getValue());
    }
    
    public void setSync(final BlockPos pos, final float damage, final boolean newVer) {
        final int placeTime = (int)(ServerUtil.getPingNoPingSpoof() / 2.0 + 1.0);
        this.confirmer.setPos(pos.toImmutable(), newVer, placeTime);
        this.lastDamage = damage;
        if (this.discrete.getValue() && this.discreteTimer.passed(this.syncDelay.getValue())) {
            this.discreteTimer.reset(this.syncDelay.getValue());
        }
        else if (!this.discrete.getValue() && this.timer.passed(this.syncDelay.getValue())) {
            this.timer.reset();
        }
    }
    
    public boolean isSyncing(final float damage, final boolean damageSync) {
        return damageSync && (!this.danger.getValue() || Managers.SAFETY.isSafe()) && this.confirmer.isValid() && damage <= this.lastDamage && ((this.discrete.getValue() && !this.discreteTimer.passed(this.syncDelay.getValue())) || (!this.discrete.getValue() && !this.timer.passed(this.syncDelay.getValue())));
    }
    
    public Confirmer getConfirmer() {
        return this.confirmer;
    }
}
