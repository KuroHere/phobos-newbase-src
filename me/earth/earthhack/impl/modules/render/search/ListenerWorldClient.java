// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.search;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;

final class ListenerWorldClient extends ModuleListener<Search, WorldClientEvent.Load>
{
    public ListenerWorldClient(final Search module) {
        super(module, (Class<? super Object>)WorldClientEvent.Load.class);
    }
    
    public void invoke(final WorldClientEvent.Load event) {
        ((Search)this.module).toRender.clear();
    }
}
