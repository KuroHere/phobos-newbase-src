// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.offhand;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;

final class ListenerGameLoop extends ModuleListener<Offhand, GameLoopEvent>
{
    public ListenerGameLoop(final Offhand module) {
        super(module, (Class<? super Object>)GameLoopEvent.class, Integer.MAX_VALUE);
    }
    
    public void invoke(final GameLoopEvent event) {
        ((Offhand)this.module).doOffhand();
    }
}
