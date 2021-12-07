// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.management;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;

final class ListenerAspectRatio extends ModuleListener<Management, AspectRatioEvent>
{
    public ListenerAspectRatio(final Management module) {
        super(module, (Class<? super Object>)AspectRatioEvent.class);
    }
    
    public void invoke(final AspectRatioEvent event) {
        if (((Management)this.module).aspectRatio.getValue()) {
            event.setAspectRatio(((Management)this.module).aspectRatioWidth.getValue() / (float)((Management)this.module).aspectRatioHeight.getValue());
        }
    }
}
