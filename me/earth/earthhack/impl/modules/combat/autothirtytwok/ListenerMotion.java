// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autothirtytwok;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;

final class ListenerMotion extends ModuleListener<Auto32k, MotionUpdateEvent>
{
    public ListenerMotion(final Auto32k module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        ((Auto32k)this.module).onUpdateWalkingPlayer(event);
    }
}
