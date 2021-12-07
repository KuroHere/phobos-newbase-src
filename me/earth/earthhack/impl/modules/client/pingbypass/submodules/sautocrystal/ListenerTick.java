// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass.submodules.sautocrystal;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.keyboard.*;

final class ListenerTick extends ModuleListener<ServerAutoCrystal, KeyboardEvent.Post>
{
    public ListenerTick(final ServerAutoCrystal module) {
        super(module, (Class<? super Object>)KeyboardEvent.Post.class);
    }
    
    public void invoke(final KeyboardEvent.Post event) {
        ((ServerAutoCrystal)this.module).onTick();
    }
}
