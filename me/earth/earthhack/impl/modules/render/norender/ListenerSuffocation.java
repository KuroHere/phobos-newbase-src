// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.norender;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;

final class ListenerSuffocation extends ModuleListener<NoRender, SuffocationEvent>
{
    public ListenerSuffocation(final NoRender module) {
        super(module, (Class<? super Object>)SuffocationEvent.class);
    }
    
    public void invoke(final SuffocationEvent event) {
        if (((NoRender)this.module).blocks.getValue()) {
            event.setCancelled(true);
        }
    }
}
