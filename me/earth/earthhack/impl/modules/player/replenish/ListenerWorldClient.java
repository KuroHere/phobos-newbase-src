// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.replenish;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;

final class ListenerWorldClient extends ModuleListener<Replenish, WorldClientEvent>
{
    public ListenerWorldClient(final Replenish module) {
        super(module, (Class<? super Object>)WorldClientEvent.class);
    }
    
    public void invoke(final WorldClientEvent event) {
        ((Replenish)this.module).clear();
    }
}
