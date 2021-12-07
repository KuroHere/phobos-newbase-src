// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.step;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;

final class ListenerBreak extends ModuleListener<Step, BlockDestroyEvent>
{
    public ListenerBreak(final Step module) {
        super(module, (Class<? super Object>)BlockDestroyEvent.class);
    }
    
    public void invoke(final BlockDestroyEvent event) {
        ((Step)this.module).onBreak();
    }
}
