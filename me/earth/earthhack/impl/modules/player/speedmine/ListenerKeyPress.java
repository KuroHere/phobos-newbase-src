// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.speedmine;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.keyboard.*;
import me.earth.earthhack.api.util.bind.*;

final class ListenerKeyPress extends ModuleListener<Speedmine, KeyboardEvent>
{
    public ListenerKeyPress(final Speedmine module) {
        super(module, (Class<? super Object>)KeyboardEvent.class);
    }
    
    public void invoke(final KeyboardEvent event) {
        if (event.getEventState() && event.getKey() == ((Speedmine)this.module).breakBind.getValue().getKey()) {
            ((Speedmine)this.module).tryBreak();
        }
    }
}
