// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.holeesp;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;

final class ListenerRender extends ModuleListener<HoleESP, Render3DEvent>
{
    public ListenerRender(final HoleESP module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        ((HoleESP)this.module).onRender3D();
    }
}
