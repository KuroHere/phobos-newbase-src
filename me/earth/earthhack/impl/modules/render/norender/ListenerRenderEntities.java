// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.norender;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;

final class ListenerRenderEntities extends ModuleListener<NoRender, RenderEntityEvent.Pre>
{
    public ListenerRenderEntities(final NoRender module) {
        super(module, (Class<? super Object>)RenderEntityEvent.Pre.class);
    }
    
    public void invoke(final RenderEntityEvent.Pre event) {
        if (((NoRender)this.module).entities.getValue()) {
            event.setCancelled(true);
        }
    }
}
