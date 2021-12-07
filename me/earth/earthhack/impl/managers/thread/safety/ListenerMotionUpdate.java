// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.thread.safety;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;

final class ListenerMotionUpdate extends ModuleListener<SafetyManager, MotionUpdateEvent>
{
    public ListenerMotionUpdate(final SafetyManager module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.POST && ((SafetyManager)this.module).post.getValue()) {
            ((SafetyManager)this.module).runThread();
        }
    }
}
