//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.search;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import net.minecraft.util.math.*;

final class ListenerUnloadChunk extends ModuleListener<Search, UnloadChunkEvent>
{
    public ListenerUnloadChunk(final Search module) {
        super(module, (Class<? super Object>)UnloadChunkEvent.class);
    }
    
    public void invoke(final UnloadChunkEvent event) {
        if (((Search)this.module).noUnloaded.getValue() && ListenerUnloadChunk.mc.world != null) {
            ((Search)this.module).toRender.keySet().removeIf(p -> !ListenerUnloadChunk.mc.world.isBlockLoaded(p));
        }
    }
}
