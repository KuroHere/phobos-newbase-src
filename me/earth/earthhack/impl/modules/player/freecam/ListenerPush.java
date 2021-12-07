// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.freecam;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;

final class ListenerPush extends ModuleListener<Freecam, BlockPushEvent>
{
    public ListenerPush(final Freecam module) {
        super(module, (Class<? super Object>)BlockPushEvent.class);
    }
    
    public void invoke(final BlockPushEvent event) {
        event.setCancelled(true);
    }
}
