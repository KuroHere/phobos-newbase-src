// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.announcer;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;

final class ListenerWorldClient extends ModuleListener<Announcer, WorldClientEvent>
{
    public ListenerWorldClient(final Announcer module) {
        super(module, (Class<? super Object>)WorldClientEvent.class);
    }
    
    public void invoke(final WorldClientEvent event) {
        ((Announcer)this.module).reset();
    }
}
