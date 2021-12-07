// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.snowballer;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;

final class ListenerMotion extends ModuleListener<Snowballer, MotionUpdateEvent>
{
    public ListenerMotion(final Snowballer module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.PRE) {
            ((Snowballer)this.module).runPre(event);
        }
        else if (event.getStage() == Stage.POST) {
            ((Snowballer)this.module).runPost(event);
        }
    }
}
