// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.management;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.managers.*;

final class ListenerLogout extends ModuleListener<Management, DisconnectEvent>
{
    public ListenerLogout(final Management module) {
        super(module, (Class<? super Object>)DisconnectEvent.class);
    }
    
    public void invoke(final DisconnectEvent event) {
        if (((Management)this.module).logout.getValue()) {
            Managers.COMBAT.reset();
        }
    }
}
