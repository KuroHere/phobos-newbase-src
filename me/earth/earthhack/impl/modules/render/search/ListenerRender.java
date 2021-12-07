//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.search;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.render.*;
import org.lwjgl.opengl.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.culling.*;
import java.util.*;
import java.awt.*;
import net.minecraft.util.math.*;

final class ListenerRender extends ModuleListener<Search, Render3DEvent>
{
    public ListenerRender(final Search module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        int count = 0;
        ((Search)this.module).found = 0;
        final boolean inRange = ((Search)this.module).countInRange.getValue();
        final boolean colored = ((Search)this.module).coloredTracers.getValue();
        final double distance = MathUtil.square(((Search)this.module).range.getValue());
        final Entity renderEntity = RenderUtil.getEntity();
        final Frustum frustum = Interpolation.createFrustum(renderEntity);
        for (final SearchResult result : ((Search)this.module).toRender.values()) {
            if (!inRange) {
                final Search search = (Search)this.module;
                ++search.found;
            }
            final BlockPos pos = result.getPos();
            if (renderEntity.getDistanceSq(pos) <= distance) {
                if (inRange) {
                    final Search search2 = (Search)this.module;
                    ++search2.found;
                }
                if (++count > ((Search)this.module).maxBlocks.getValue()) {
                    continue;
                }
                final float red = result.getRed();
                final float green = result.getGreen();
                final float blue = result.getBlue();
                final float alpha = result.getAlpha();
                final Color color = result.getColor();
                if (((Search)this.module).lines.getValue() || ((Search)this.module).fill.getValue()) {
                    final AxisAlignedBB bb = result.getBb();
                    if (frustum.isBoundingBoxInFrustum(bb)) {
                        final AxisAlignedBB box = Interpolation.offsetRenderPos(bb);
                        if (((Search)this.module).lines.getValue()) {
                            RenderUtil.startRender();
                            RenderUtil.drawOutline(box, 1.5f, color);
                            RenderUtil.endRender();
                        }
                        if (((Search)this.module).fill.getValue()) {
                            RenderUtil.startRender();
                            RenderUtil.drawBox(box, color);
                            RenderUtil.endRender();
                        }
                    }
                }
                if (!((Search)this.module).tracers.getValue()) {
                    continue;
                }
                final double x = pos.getX() - Interpolation.getRenderPosX();
                final double y = pos.getY() - Interpolation.getRenderPosY();
                final double z = pos.getZ() - Interpolation.getRenderPosZ();
                if (colored) {
                    GL11.glColor4f(red, green, blue, alpha);
                }
                else {
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                }
                RenderUtil.startRender();
                GL11.glLoadIdentity();
                GL11.glLineWidth(1.5f);
                final boolean viewBobbing = ListenerRender.mc.gameSettings.viewBobbing;
                ListenerRender.mc.gameSettings.viewBobbing = false;
                ((IEntityRenderer)ListenerRender.mc.entityRenderer).invokeOrientCamera(event.getPartialTicks());
                ListenerRender.mc.gameSettings.viewBobbing = viewBobbing;
                final Vec3d vec3d = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(renderEntity.rotationPitch)).rotateYaw(-(float)Math.toRadians(renderEntity.rotationYaw));
                GL11.glBegin(1);
                GL11.glVertex3d(vec3d.xCoord, renderEntity.getEyeHeight() + vec3d.yCoord, vec3d.zCoord);
                GL11.glVertex3d(x + 0.5, y + 0.5, z + 0.5);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glEnd();
                RenderUtil.endRender();
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
