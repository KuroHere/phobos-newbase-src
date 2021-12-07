// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.chat;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;

final class ListenerLogout extends ModuleListener<Chat, DisconnectEvent>
{
    public ListenerLogout(final Chat module) {
        super(module, (Class<? super Object>)DisconnectEvent.class);
    }
    
    public void invoke(final DisconnectEvent event) {
        ((Chat)this.module).clearNoScroll();
    }
}
