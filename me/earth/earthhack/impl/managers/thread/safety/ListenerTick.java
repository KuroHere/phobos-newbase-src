// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.thread.safety;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.client.safety.util.*;

final class ListenerTick extends ModuleListener<SafetyManager, TickEvent>
{
    public ListenerTick(final SafetyManager manager) {
        super(manager, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (((SafetyManager)this.module).mode.getValue() == Update.Tick) {
            ((SafetyManager)this.module).runThread();
        }
    }
}
