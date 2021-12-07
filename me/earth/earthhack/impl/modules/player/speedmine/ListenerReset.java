// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.speedmine;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.player.speedmine.mode.*;

final class ListenerReset extends ModuleListener<Speedmine, ResetBlockEvent>
{
    public ListenerReset(final Speedmine module) {
        super(module, (Class<? super Object>)ResetBlockEvent.class);
    }
    
    public void invoke(final ResetBlockEvent event) {
        if (((Speedmine)this.module).noReset.getValue() || ((Speedmine)this.module).mode.getValue() == MineMode.Reset) {
            event.setCancelled(true);
        }
    }
}
