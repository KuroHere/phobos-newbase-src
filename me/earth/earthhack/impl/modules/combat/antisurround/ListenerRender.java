// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.antisurround;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.util.render.*;
import net.minecraft.util.math.*;

final class ListenerRender extends ModuleListener<AntiSurround, Render3DEvent>
{
    public ListenerRender(final AntiSurround module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        if (((AntiSurround)this.module).active.get() && ((AntiSurround)this.module).drawEsp.getValue()) {
            final BlockPos pos = ((AntiSurround)this.module).pos;
            if (pos != null) {
                ((AntiSurround)this.module).esp.render(Interpolation.interpolatePos(pos, 1.0f));
            }
        }
    }
}
