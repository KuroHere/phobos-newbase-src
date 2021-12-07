// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autoarmor;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;

final class ListenerGameLoop extends ModuleListener<AutoArmor, GameLoopEvent>
{
    public ListenerGameLoop(final AutoArmor module) {
        super(module, (Class<? super Object>)GameLoopEvent.class, -5);
    }
    
    public void invoke(final GameLoopEvent event) {
        ((AutoArmor)this.module).runClick();
    }
}
