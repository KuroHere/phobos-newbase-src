// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autothirtytwok;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.keyboard.*;

final class ListenerKeyPress extends ModuleListener<Auto32k, KeyboardEvent>
{
    public ListenerKeyPress(final Auto32k module) {
        super(module, (Class<? super Object>)KeyboardEvent.class);
    }
    
    public void invoke(final KeyboardEvent event) {
        ((Auto32k)this.module).onKeyInput(event);
    }
}
