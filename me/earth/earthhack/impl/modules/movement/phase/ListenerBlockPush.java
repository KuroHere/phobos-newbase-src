// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.phase;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;

final class ListenerBlockPush extends ModuleListener<Phase, BlockPushEvent>
{
    public ListenerBlockPush(final Phase module) {
        super(module, (Class<? super Object>)BlockPushEvent.class);
    }
    
    public void invoke(final BlockPushEvent event) {
        event.setCancelled(true);
    }
}
