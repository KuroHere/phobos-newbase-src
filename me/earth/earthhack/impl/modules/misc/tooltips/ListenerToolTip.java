// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.tooltips;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;

final class ListenerToolTip extends ModuleListener<ToolTips, ToolTipEvent>
{
    public ListenerToolTip(final ToolTips module) {
        super(module, (Class<? super Object>)ToolTipEvent.class);
    }
    
    public void invoke(final ToolTipEvent event) {
        if (((ToolTips)this.module).shulkers.getValue() && !event.isCancelled() && ((ToolTips)this.module).drawShulkerToolTip(event.getStack(), event.getX(), event.getY(), null)) {
            event.setCancelled(true);
        }
    }
}
