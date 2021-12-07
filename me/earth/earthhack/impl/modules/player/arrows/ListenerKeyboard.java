// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.arrows;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.keyboard.*;
import me.earth.earthhack.api.util.bind.*;
import org.lwjgl.input.*;

final class ListenerKeyboard extends ModuleListener<Arrows, KeyboardEvent>
{
    public ListenerKeyboard(final Arrows module) {
        super(module, (Class<? super Object>)KeyboardEvent.class);
    }
    
    public void invoke(final KeyboardEvent event) {
        if (((Arrows)this.module).cycleButton.getValue().getKey() == Keyboard.getEventKey() && event.getEventState()) {
            ((Arrows)this.module).cycle(false, false);
        }
    }
}
