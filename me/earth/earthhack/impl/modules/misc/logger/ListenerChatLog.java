// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.logger;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;

final class ListenerChatLog extends ModuleListener<Logger, ChatEvent.Log>
{
    public ListenerChatLog(final Logger module) {
        super(module, (Class<? super Object>)ChatEvent.Log.class);
    }
    
    public void invoke(final ChatEvent.Log event) {
        if (((Logger)this.module).cancel) {
            event.setCancelled(true);
        }
    }
}
