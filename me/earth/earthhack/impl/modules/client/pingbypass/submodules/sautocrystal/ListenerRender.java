// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass.submodules.sautocrystal;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.pingbypass.*;
import java.awt.*;
import me.earth.earthhack.impl.util.render.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerRender extends ModuleListener<ServerAutoCrystal, Render3DEvent>
{
    private static final ModuleCache<PingBypass> PINGBYPASS;
    
    public ListenerRender(final ServerAutoCrystal module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        final BlockPos pos;
        if ((pos = ((ServerAutoCrystal)this.module).renderPos) != null && ListenerRender.PINGBYPASS.isEnabled()) {
            RenderUtil.renderBox(Interpolation.interpolatePos(pos, 1.0f), new Color(255, 255, 255, 120), Color.WHITE, 1.5f);
        }
    }
    
    static {
        PINGBYPASS = Caches.getModule(PingBypass.class);
    }
}
