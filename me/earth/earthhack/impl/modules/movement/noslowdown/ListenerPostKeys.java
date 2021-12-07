// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.noslowdown;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.keyboard.*;

final class ListenerPostKeys extends ModuleListener<NoSlowDown, KeyboardEvent.Post>
{
    public ListenerPostKeys(final NoSlowDown module) {
        super(module, (Class<? super Object>)KeyboardEvent.Post.class);
    }
    
    public void invoke(final KeyboardEvent.Post event) {
        ((NoSlowDown)this.module).updateKeyBinds();
    }
}
