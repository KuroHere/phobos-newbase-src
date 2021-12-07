// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.helpers;

import me.earth.earthhack.api.setting.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.api.event.bus.api.*;

public class ForceAntiTotemHelper
{
    private final DamageSyncHelper damageSyncHelper;
    private final Setting<Integer> placeConfirm;
    private final Setting<Integer> breakConfirm;
    private BlockPos pos;
    
    public ForceAntiTotemHelper(final EventBus eventBus, final Setting<Boolean> discrete, final Setting<Integer> syncDelay, final Setting<Integer> placeConfirm, final Setting<Integer> breakConfirm, final Setting<Boolean> dangerForce) {
        this.damageSyncHelper = new DamageSyncHelper(eventBus, discrete, syncDelay, dangerForce);
        this.placeConfirm = placeConfirm;
        this.breakConfirm = breakConfirm;
    }
    
    public void setSync(final BlockPos pos, final boolean newVer) {
        this.damageSyncHelper.setSync(pos, Float.MAX_VALUE, newVer);
        this.pos = pos;
    }
    
    public boolean isForcing(final boolean damageSync) {
        final Confirmer c = this.damageSyncHelper.getConfirmer();
        if (c.isValid() && (!c.isPlaceConfirmed(this.placeConfirm.getValue()) || !c.isBreakConfirmed(this.breakConfirm.getValue()))) {
            return c.isValid();
        }
        return this.damageSyncHelper.isSyncing(0.0f, damageSync);
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
}
