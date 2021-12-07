// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.entitycontrol;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;

final class ListenerHorse extends ModuleListener<EntityControl, HorseEvent>
{
    public ListenerHorse(final EntityControl module) {
        super(module, (Class<? super Object>)HorseEvent.class);
    }
    
    public void invoke(final HorseEvent event) {
        event.setJumpHeight(((EntityControl)this.module).jumpHeight.getValue());
    }
}
