// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.util.*;

final class ListenerTick extends ModuleListener<AutoCrystal, TickEvent>
{
    public ListenerTick(final AutoCrystal module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (event.isSafe()) {
            ((AutoCrystal)this.module).checkExecutor();
            ((AutoCrystal)this.module).placed.values().removeIf(stamp -> System.currentTimeMillis() - stamp.getTimeStamp() > ((AutoCrystal)this.module).removeTime.getValue());
            ((AutoCrystal)this.module).crystalRender.tick();
            if (!((AutoCrystal)this.module).idHelper.isUpdated()) {
                ((AutoCrystal)this.module).idHelper.update();
                ((AutoCrystal)this.module).idHelper.setUpdated(true);
            }
            ((AutoCrystal)this.module).weaknessHelper.updateWeakness();
        }
    }
}
