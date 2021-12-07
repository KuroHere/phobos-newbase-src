// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autoregear;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.keyboard.*;
import me.earth.earthhack.api.util.bind.*;

final class ListenerKeyPress extends ModuleListener<AutoRegear, KeyboardEvent>
{
    public ListenerKeyPress(final AutoRegear module) {
        super(module, (Class<? super Object>)KeyboardEvent.class);
    }
    
    public void invoke(final KeyboardEvent event) {
        if (event.getKey() == ((AutoRegear)this.module).regear.getValue().getKey()) {
            ((AutoRegear)this.module).shouldRegear = true;
        }
    }
}
