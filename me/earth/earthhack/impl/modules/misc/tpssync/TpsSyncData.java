// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.tpssync;

import me.earth.earthhack.api.module.data.*;

final class TpsSyncData extends DefaultData<TpsSync>
{
    public TpsSyncData(final TpsSync module) {
        super(module);
        this.register("Attack", "Syncs your attacks with the tps.");
        this.register("Mine", "Syncs mining blocks with the tps.");
    }
    
    @Override
    public int getColor() {
        return -1;
    }
    
    @Override
    public String getDescription() {
        return "Syncs your actions with the servers tps.";
    }
}
