// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.sounds;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import java.util.*;

final class ListenerTick extends ModuleListener<Sounds, TickEvent>
{
    public ListenerTick(final Sounds module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        ((Sounds)this.module).sounds.entrySet().removeIf(e -> System.currentTimeMillis() - e.getValue() > ((Sounds)this.module).remove.getValue());
    }
}
