//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.xray;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import net.minecraft.util.*;

final class ListenerBlockLayer extends ModuleListener<XRay, BlockLayerEvent>
{
    public ListenerBlockLayer(final XRay module) {
        super(module, (Class<? super Object>)BlockLayerEvent.class);
    }
    
    public void invoke(final BlockLayerEvent event) {
        if (!((XRay)this.module).isValid(event.getBlock().getLocalizedName())) {
            event.setLayer(BlockRenderLayer.TRANSLUCENT);
        }
    }
}
