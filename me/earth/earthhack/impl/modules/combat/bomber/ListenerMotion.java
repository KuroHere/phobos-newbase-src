// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.bomber;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;

final class ListenerMotion extends ModuleListener<CrystalBomber, MotionUpdateEvent>
{
    public ListenerMotion(final CrystalBomber module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        ((CrystalBomber)this.module).doCrystalBomber(event);
    }
}
