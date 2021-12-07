// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.autosprint;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.movement.autosprint.mode.*;

final class ListenerTick extends ModuleListener<AutoSprint, TickEvent>
{
    public ListenerTick(final AutoSprint module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if ((AutoSprint.canSprint() && ((AutoSprint)this.module).mode.getValue() == SprintMode.Legit) || (AutoSprint.canSprintBetter() && ((AutoSprint)this.module).mode.getValue() == SprintMode.Rage)) {
            ((AutoSprint)this.module).mode.getValue().sprint();
        }
    }
}
