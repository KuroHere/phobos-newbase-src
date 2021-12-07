//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.trails;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.modules.render.breadcrumbs.util.*;
import org.lwjgl.opengl.*;
import me.earth.earthhack.impl.util.animation.*;
import net.minecraft.util.math.*;
import java.util.function.*;
import java.util.*;
import me.earth.earthhack.impl.util.render.*;

final class ListenerRender extends ModuleListener<Trails, Render3DEvent>
{
    public ListenerRender(final Trails module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        for (final Map.Entry<Integer, List<Trace>> entry : ((Trails)this.module).traceLists.entrySet()) {
            RenderUtil.startRender();
            GL11.glLineWidth((float)((Trails)this.module).width.getValue());
            final TimeAnimation animation = ((Trails)this.module).ids.get(entry.getKey());
            animation.add(event.getPartialTicks());
            GL11.glColor4f(((Trails)this.module).color.getR(), ((Trails)this.module).color.getG(), ((Trails)this.module).color.getB(), MathHelper.clamp((float)(((Trails)this.module).color.getA() - animation.getCurrent() / 255.0), 0.0f, 255.0f));
            final Trace trace;
            entry.getValue().forEach(trace -> {
                GL11.glBegin(3);
                trace.getTrace().forEach(this::renderVec);
                GL11.glEnd();
                return;
            });
            GL11.glColor4f(((Trails)this.module).color.getR(), ((Trails)this.module).color.getG(), ((Trails)this.module).color.getB(), MathHelper.clamp((float)(((Trails)this.module).color.getA() - animation.getCurrent() / 255.0), 0.0f, 255.0f));
            GL11.glBegin(3);
            trace = ((Trails)this.module).traces.get(entry.getKey());
            if (trace != null) {
                trace.getTrace().forEach(this::renderVec);
            }
            GL11.glEnd();
            RenderUtil.endRender();
        }
    }
    
    private void renderVec(final Trace.TracePos tracePos) {
        final double x = tracePos.getPos().xCoord - Interpolation.getRenderPosX();
        final double y = tracePos.getPos().yCoord - Interpolation.getRenderPosY();
        final double z = tracePos.getPos().zCoord - Interpolation.getRenderPosZ();
        GL11.glVertex3d(x, y, z);
    }
}
