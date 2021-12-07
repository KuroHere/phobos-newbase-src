// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autotrap;

import me.earth.earthhack.impl.util.helpers.blocks.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.freecam.*;
import me.earth.earthhack.impl.util.helpers.blocks.util.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerAutoTrap extends ObbyListener<AutoTrap>
{
    protected static final ModuleCache<Freecam> FREECAM;
    
    public ListenerAutoTrap(final AutoTrap module) {
        super(module, -999);
    }
    
    @Override
    protected TargetResult getTargets(final TargetResult result) {
        return ((AutoTrap)this.module).getTargets(result);
    }
    
    @Override
    protected boolean updatePlaced() {
        if (ListenerAutoTrap.FREECAM.isEnabled() && !((AutoTrap)this.module).freeCam.getValue()) {
            ((AutoTrap)this.module).disable();
            return true;
        }
        return super.updatePlaced();
    }
    
    static {
        FREECAM = Caches.getModule(Freecam.class);
    }
}
