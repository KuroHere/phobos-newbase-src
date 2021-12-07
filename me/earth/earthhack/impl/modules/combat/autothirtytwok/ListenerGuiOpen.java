// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autothirtytwok;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;

final class ListenerGuiOpen extends ModuleListener<Auto32k, GuiScreenEvent<?>>
{
    public ListenerGuiOpen(final Auto32k module) {
        super(module, (Class<? super Object>)GuiScreenEvent.class);
    }
    
    public void invoke(final GuiScreenEvent<?> event) {
        ((Auto32k)this.module).onGui(event);
    }
}
