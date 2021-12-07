// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.sounds;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.modules.render.sounds.util.*;
import me.earth.earthhack.impl.managers.*;
import java.awt.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.render.*;
import java.util.*;

final class ListenerRender extends ModuleListener<Sounds, Render3DEvent>
{
    public ListenerRender(final Sounds module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        if (!((Sounds)this.module).render.getValue()) {
            return;
        }
        for (final Map.Entry<SoundPosition, Long> e : ((Sounds)this.module).sounds.entrySet()) {
            final SoundPosition p = e.getKey();
            int color = ((Sounds)this.module).color.getRGB();
            if (p instanceof CustomSound) {
                color = Color.HSBtoRGB(Managers.COLOR.getHueByPosition(p.getY()), 1.0f, 1.0f);
            }
            if (((Sounds)this.module).fade.getValue()) {
                int alpha = color >>> 24;
                final double t = (double)(System.currentTimeMillis() - e.getValue());
                final double factor = 1.0 - t / ((Sounds)this.module).remove.getValue();
                if (factor <= 0.0) {
                    continue;
                }
                alpha = MathUtil.clamp((int)(alpha * factor), 0, 255) << 24;
                color = ((color & 0xFFFFFF) | alpha);
            }
            final double x = p.getX() - Interpolation.getRenderPosX();
            final double y = p.getY() - Interpolation.getRenderPosY();
            final double z = p.getZ() - Interpolation.getRenderPosZ();
            final String c = "§z" + String.format("%08X", color);
            RenderUtil.drawNametag(c + p.getName(), x, y, z, ((Sounds)this.module).scale.getValue(), -1, ((Sounds)this.module).rect.getValue());
        }
    }
}
