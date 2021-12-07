// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;

final class ListenerRenderEntities extends ModuleListener<AutoCrystal, PostRenderEntitiesEvent>
{
    public ListenerRenderEntities(final AutoCrystal module) {
        super(module, (Class<? super Object>)PostRenderEntitiesEvent.class);
    }
    
    public void invoke(final PostRenderEntitiesEvent event) {
        if (event.getPass() == 0) {
            ((AutoCrystal)this.module).crystalRender.render(event.getPartialTicks());
        }
    }
}
