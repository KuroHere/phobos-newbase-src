// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.killaura;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;

final class ListenerRiding extends ModuleListener<KillAura, MotionUpdateEvent.Riding>
{
    public ListenerRiding(final KillAura module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.Riding.class);
    }
    
    public void invoke(final MotionUpdateEvent.Riding event) {
        if (event.getStage() == Stage.PRE) {
            ListenerMotion.pre((KillAura)this.module, event, ((KillAura)this.module).ridingTeleports.getValue());
        }
        else {
            ListenerMotion.post((KillAura)this.module);
        }
    }
}
