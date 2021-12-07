// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.noglitchblocks;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.event.events.*;

final class ListenerBlockDestroy extends ModuleListener<NoGlitchBlocks, BlockDestroyEvent>
{
    public ListenerBlockDestroy(final NoGlitchBlocks module) {
        super(module, (Class<? super Object>)BlockDestroyEvent.class, 1000);
    }
    
    public void invoke(final BlockDestroyEvent event) {
        if (((NoGlitchBlocks)this.module).crack.getValue() && event.getStage() == Stage.PRE) {
            event.setCancelled(true);
        }
    }
}
