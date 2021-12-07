// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass.submodules.sautototem;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;

final class ListenerTick extends ModuleListener<ServerAutoTotem, TickEvent>
{
    public ListenerTick(final ServerAutoTotem module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        ((ServerAutoTotem)this.module).onTick();
    }
}
