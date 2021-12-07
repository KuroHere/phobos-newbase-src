// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.phase;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;

final class ListenerRender extends ModuleListener<Phase, Render3DEvent>
{
    public ListenerRender(final Phase module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        if (((Phase)this.module).esp.getValue() && ((Phase)this.module).pos != null && !((Phase)this.module).clickTimer.passed(750L)) {
            ((Phase)this.module).renderPos(((Phase)this.module).pos);
        }
    }
}
