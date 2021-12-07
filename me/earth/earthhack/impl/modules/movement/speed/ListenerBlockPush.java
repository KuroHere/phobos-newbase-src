// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.speed;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;

final class ListenerBlockPush extends ModuleListener<Speed, BlockPushEvent>
{
    public ListenerBlockPush(final Speed module) {
        super(module, (Class<? super Object>)BlockPushEvent.class, 999);
    }
    
    public void invoke(final BlockPushEvent event) {
        if (((Speed)this.module).lagOut.getValue()) {
            event.setCancelled(false);
        }
    }
}
