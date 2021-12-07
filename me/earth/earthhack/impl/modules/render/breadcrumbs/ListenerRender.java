//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.breadcrumbs;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import org.lwjgl.opengl.*;
import java.util.function.*;
import me.earth.earthhack.impl.modules.render.breadcrumbs.util.*;
import me.earth.earthhack.impl.util.render.*;
import net.minecraft.util.math.*;

final class ListenerRender extends ModuleListener<BreadCrumbs, Render3DEvent>
{
    public ListenerRender(final BreadCrumbs module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        if (((BreadCrumbs)this.module).render.getValue() && ((BreadCrumbs)this.module).trace != null) {
            RenderUtil.startRender();
            GL11.glLineWidth((float)((BreadCrumbs)this.module).width.getValue());
            ((BreadCrumbs)this.module).positions.forEach(trace -> {
                GL11.glBegin(3);
                trace.getTrace().forEach(this::renderVec);
                GL11.glEnd();
                if (((BreadCrumbs)this.module).fade.getValue()) {
                    trace.getTrace().removeIf(Trace.TracePos::shouldRemoveTrace);
                }
                return;
            });
            GL11.glBegin(3);
            ((BreadCrumbs)this.module).trace.getTrace().forEach(this::renderVec);
            if (((BreadCrumbs)this.module).fade.getValue()) {
                ((BreadCrumbs)this.module).trace.getTrace().removeIf(Trace.TracePos::shouldRemoveTrace);
            }
            GL11.glEnd();
            RenderUtil.endRender();
        }
    }
    
    private void renderVec(final Trace.TracePos tracePos) {
        final double x = tracePos.getPos().xCoord - Interpolation.getRenderPosX();
        final double y = tracePos.getPos().yCoord - Interpolation.getRenderPosY();
        final double z = tracePos.getPos().zCoord - Interpolation.getRenderPosZ();
        final float percentage = ((BreadCrumbs)this.module).fade.getValue() ? (MathHelper.clamp(((BreadCrumbs)this.module).color.getA() / ((BreadCrumbs)this.module).fadeDelay.getValue() * (tracePos.getTime() - System.currentTimeMillis()), 0.0f, ((BreadCrumbs)this.module).color.getB()) / ((BreadCrumbs)this.module).color.getB()) : ((BreadCrumbs)this.module).color.getA();
        GL11.glColor4f(((BreadCrumbs)this.module).color.getR(), ((BreadCrumbs)this.module).color.getG(), ((BreadCrumbs)this.module).color.getB(), percentage);
        GL11.glVertex3d(x, y, z);
    }
}
