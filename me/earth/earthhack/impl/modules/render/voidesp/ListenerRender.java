//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.voidesp;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;

final class ListenerRender extends ModuleListener<VoidESP, Render3DEvent>
{
    public ListenerRender(final VoidESP module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        if (ListenerRender.mc.player.dimension == DimensionType.THE_END.getId() || ListenerRender.mc.player.posY > ((VoidESP)this.module).maxY.getValue()) {
            return;
        }
        ((VoidESP)this.module).updateHoles();
        for (int holes = ((VoidESP)this.module).holes.getValue(), i = 0; i < holes && i < ((VoidESP)this.module).voidHoles.size(); ++i) {
            ((VoidESP)this.module).renderPos(((VoidESP)this.module).voidHoles.get(i));
        }
    }
}
