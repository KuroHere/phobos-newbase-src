// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.autotool;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;

final class ListenerWorldClient extends ModuleListener<AutoTool, WorldClientEvent>
{
    public ListenerWorldClient(final AutoTool module) {
        super(module, (Class<? super Object>)WorldClientEvent.class);
    }
    
    public void invoke(final WorldClientEvent event) {
        ((AutoTool)this.module).reset();
    }
}
