// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.tooltips;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;

final class ListenerLogout extends ModuleListener<ToolTips, DisconnectEvent>
{
    public ListenerLogout(final ToolTips module) {
        super(module, (Class<? super Object>)DisconnectEvent.class);
    }
    
    public void invoke(final DisconnectEvent event) {
        ((ToolTips)this.module).spiedPlayers.clear();
    }
}
