// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.safewalk;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;

final class ListenerMove extends ModuleListener<SafeWalk, MoveEvent>
{
    public ListenerMove(final SafeWalk module) {
        super(module, (Class<? super Object>)MoveEvent.class);
    }
    
    public void invoke(final MoveEvent event) {
        event.setSneaking(true);
    }
}
