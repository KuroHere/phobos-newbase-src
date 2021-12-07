// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autoreconnect;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;

final class ListenerWorldClient extends ModuleListener<AutoReconnect, WorldClientEvent.Unload>
{
    public ListenerWorldClient(final AutoReconnect module) {
        super(module, (Class<? super Object>)WorldClientEvent.Unload.class);
    }
    
    public void invoke(final WorldClientEvent.Unload event) {
        ((AutoReconnect)this.module).setServerData();
    }
}
