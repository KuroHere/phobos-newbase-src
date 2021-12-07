// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.nuker;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;

final class ListenerClickBlock extends ModuleListener<Nuker, ClickBlockEvent>
{
    public ListenerClickBlock(final Nuker module) {
        super(module, (Class<? super Object>)ClickBlockEvent.class, 11);
    }
    
    public void invoke(final ClickBlockEvent event) {
        if (((Nuker)this.module).nuke.getValue() && ((Nuker)this.module).timer.passed(((Nuker)this.module).delay.getValue()) && !((Nuker)this.module).breaking) {
            ((Nuker)this.module).currentSelection = ((Nuker)this.module).getSelection(event.getPos());
            ((Nuker)this.module).breaking = true;
            ((Nuker)this.module).breakSelection(((Nuker)this.module).currentSelection, ((Nuker)this.module).autoTool.getValue());
            event.setCancelled(true);
        }
    }
}
