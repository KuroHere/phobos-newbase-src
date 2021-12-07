// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.automine;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;

final class ListenerWorldClient extends ModuleListener<AutoMine, WorldClientEvent.Load>
{
    public ListenerWorldClient(final AutoMine module) {
        super(module, (Class<? super Object>)WorldClientEvent.Load.class);
    }
    
    public void invoke(final WorldClientEvent.Load event) {
        ((AutoMine)this.module).reset(true);
        ((AutoMine)this.module).blackList.clear();
    }
}
