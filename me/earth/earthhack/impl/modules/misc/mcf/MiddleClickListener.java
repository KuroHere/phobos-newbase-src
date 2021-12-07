// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.mcf;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.keyboard.*;

final class MiddleClickListener extends ModuleListener<MCF, ClickMiddleEvent>
{
    public MiddleClickListener(final MCF module) {
        super(module, (Class<? super Object>)ClickMiddleEvent.class);
    }
    
    public void invoke(final ClickMiddleEvent event) {
        if (event.isModuleCancelled()) {
            return;
        }
        ((MCF)this.module).onMiddleClick();
    }
}
