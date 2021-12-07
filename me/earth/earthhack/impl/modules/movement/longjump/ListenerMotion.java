// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.longjump;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.util.minecraft.*;

final class ListenerMotion extends ModuleListener<LongJump, MotionUpdateEvent>
{
    public ListenerMotion(final LongJump module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.PRE) {
            ((LongJump)this.module).distance = MovementUtil.getDistance2D();
        }
    }
}
