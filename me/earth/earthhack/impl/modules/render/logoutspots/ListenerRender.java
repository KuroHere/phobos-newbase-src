// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.logoutspots;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.modules.render.logoutspots.util.*;
import me.earth.earthhack.impl.util.render.*;
import java.awt.*;
import me.earth.earthhack.impl.util.math.*;
import java.util.*;
import net.minecraft.util.math.*;

final class ListenerRender extends ModuleListener<LogoutSpots, Render3DEvent>
{
    public ListenerRender(final LogoutSpots module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        if (((LogoutSpots)this.module).render.getValue()) {
            for (final LogoutSpot spot : ((LogoutSpots)this.module).spots.values()) {
                final AxisAlignedBB bb = Interpolation.interpolateAxis(spot.getBoundingBox());
                RenderUtil.startRender();
                RenderUtil.drawOutline(bb, 1.5f, Color.RED);
                RenderUtil.endRender();
                final String text = "§c" + spot.getName() + " XYZ : " + MathUtil.round(spot.getX(), 1) + ", " + MathUtil.round(spot.getY(), 1) + ", " + MathUtil.round(spot.getZ(), 1) + " (" + MathUtil.round(spot.getDistance(), 1) + ")";
                RenderUtil.drawNametag(text, bb, ((LogoutSpots)this.module).scale.getValue(), -65536);
            }
        }
    }
}
