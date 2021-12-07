// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.thread.safety;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.modules.client.safety.util.*;

final class ListenerGameLoop extends ModuleListener<SafetyManager, GameLoopEvent>
{
    private final StopWatch timer;
    
    public ListenerGameLoop(final SafetyManager manager) {
        super(manager, (Class<? super Object>)GameLoopEvent.class);
        this.timer = new StopWatch();
    }
    
    public void invoke(final GameLoopEvent event) {
        if (((SafetyManager)this.module).mode.getValue() == Update.Fast && this.timer.passed(((SafetyManager)this.module).d.getValue())) {
            ((SafetyManager)this.module).runThread();
            this.timer.reset();
        }
    }
}
