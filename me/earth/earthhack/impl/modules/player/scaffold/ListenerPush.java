// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.scaffold;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;

final class ListenerPush extends ModuleListener<Scaffold, BlockPushEvent>
{
    public ListenerPush(final Scaffold module) {
        super(module, (Class<? super Object>)BlockPushEvent.class);
    }
    
    public void invoke(final BlockPushEvent event) {
        if (((Scaffold)this.module).tower.getValue()) {
            event.setCancelled(true);
        }
    }
}
