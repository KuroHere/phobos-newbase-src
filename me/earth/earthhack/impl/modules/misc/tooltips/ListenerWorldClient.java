// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.tooltips;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;

final class ListenerWorldClient extends ModuleListener<ToolTips, WorldClientEvent.Load>
{
    public ListenerWorldClient(final ToolTips module) {
        super(module, (Class<? super Object>)WorldClientEvent.Load.class);
    }
    
    public void invoke(final WorldClientEvent.Load event) {
        ((ToolTips)this.module).spiedPlayers.clear();
    }
}
