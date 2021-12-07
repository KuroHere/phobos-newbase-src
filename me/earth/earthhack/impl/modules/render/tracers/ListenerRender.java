//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.tracers;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.render.esp.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.managers.*;
import org.lwjgl.opengl.*;
import me.earth.earthhack.impl.util.render.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.modules.render.tracers.mode.*;
import java.util.*;
import java.awt.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerRender extends ModuleListener<Tracers, Render3DEvent>
{
    private static final ModuleCache<ESP> ESP;
    
    public ListenerRender(final Tracers module) {
        super(module, (Class<? super Object>)Render3DEvent.class, Integer.MIN_VALUE);
    }
    
    public void invoke(final Render3DEvent event) {
        final Entity renderEntity = (Entity)((ListenerRender.mc.getRenderViewEntity() == null) ? ListenerRender.mc.player : ListenerRender.mc.getRenderViewEntity());
        int i = 0;
        for (final Entity entity : ((Tracers)this.module).sorted) {
            if (i >= ((Tracers)this.module).tracers.getValue()) {
                break;
            }
            if (!((Tracers)this.module).isValid(entity)) {
                continue;
            }
            final Vec3d interpolation = Interpolation.interpolateEntity(entity);
            final double x = interpolation.xCoord;
            final double y = interpolation.yCoord;
            final double z = interpolation.zCoord;
            AxisAlignedBB bb;
            if (((Tracers)this.module).target.getValue() == BodyPart.Head) {
                bb = new AxisAlignedBB(x - 0.25, y + entity.height - 0.45, z - 0.25, x + 0.25, y + entity.height + 0.055, z + 0.25);
            }
            else {
                bb = new AxisAlignedBB(x - 0.4, y, z - 0.4, x + 0.4, y + entity.height + 0.18, z + 0.4);
            }
            RenderUtil.startRender();
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            if (entity instanceof EntityPlayer && Managers.FRIENDS.contains(entity.getName())) {
                GL11.glColor4f(0.33333334f, 0.78431374f, 0.78431374f, 0.55f);
            }
            else {
                final float distance = renderEntity.getDistanceToEntity(entity);
                float red;
                if (distance >= 60.0f) {
                    red = 120.0f;
                }
                else {
                    red = distance + distance;
                }
                final Color color = ColorHelper.toColor(red, 100.0f, 50.0f, 0.55f);
                GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
            }
            final boolean viewBobbing = ListenerRender.mc.gameSettings.viewBobbing;
            ListenerRender.mc.gameSettings.viewBobbing = false;
            ((IEntityRenderer)ListenerRender.mc.entityRenderer).invokeOrientCamera(event.getPartialTicks());
            ListenerRender.mc.gameSettings.viewBobbing = viewBobbing;
            GL11.glLineWidth((float)((Tracers)this.module).lineWidth.getValue());
            final Vec3d rotateYaw = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(renderEntity.rotationPitch)).rotateYaw(-(float)Math.toRadians(renderEntity.rotationYaw));
            GL11.glBegin(1);
            if (((Tracers)this.module).mode.getValue() == TracerMode.Stem && !ListenerRender.ESP.isEnabled()) {
                GL11.glVertex3d(x, y, z);
                GL11.glVertex3d(x, renderEntity.getEyeHeight() + y, z);
            }
            if (((Tracers)this.module).lines.getValue()) {
                GL11.glVertex3d(rotateYaw.xCoord, renderEntity.getEyeHeight() + rotateYaw.yCoord, rotateYaw.zCoord);
                switch (((Tracers)this.module).target.getValue()) {
                    case Head: {
                        GL11.glVertex3d(x, y + entity.height - 0.18, z);
                        break;
                    }
                    case Body: {
                        GL11.glVertex3d(x, y + entity.height / 2.0f, z);
                        break;
                    }
                    case Feet: {
                        GL11.glVertex3d(x, y, z);
                        break;
                    }
                }
            }
            GL11.glEnd();
            GL11.glTranslated(x, y, z);
            GL11.glTranslated(-x, -y, -z);
            switch (((Tracers)this.module).mode.getValue()) {
                case Outline: {
                    RenderUtil.doPosition(bb);
                    break;
                }
                case Fill: {
                    RenderUtil.fillBox(bb);
                    break;
                }
            }
            GlStateManager.popMatrix();
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
            RenderUtil.endRender();
            ++i;
        }
    }
    
    static {
        ESP = Caches.getModule(ESP.class);
    }
}
