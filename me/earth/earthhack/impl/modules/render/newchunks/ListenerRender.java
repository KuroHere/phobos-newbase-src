//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.newchunks;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import org.lwjgl.opengl.*;
import me.earth.earthhack.impl.util.render.*;
import me.earth.earthhack.impl.modules.render.newchunks.util.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.culling.*;
import java.util.*;

final class ListenerRender extends ModuleListener<NewChunks, Render3DEvent>
{
    public ListenerRender(final NewChunks module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        final boolean lightning = GL11.glIsEnabled(2896);
        final boolean blend = GL11.glIsEnabled(3042);
        final boolean texture = GL11.glIsEnabled(3553);
        final boolean depth = GL11.glIsEnabled(2929);
        final boolean lines = GL11.glIsEnabled(2848);
        GL11.glPushMatrix();
        GL11.glDepthMask(false);
        if (lightning) {
            GL11.glDisable(2896);
        }
        GL11.glBlendFunc(770, 771);
        if (!blend) {
            GL11.glEnable(3042);
        }
        GL11.glLineWidth(0.5f);
        if (texture) {
            GL11.glDisable(3553);
        }
        if (depth) {
            GL11.glDisable(2929);
        }
        if (!lines) {
            GL11.glEnable(2848);
        }
        GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        final Frustum frustum = Interpolation.createFrustum(RenderUtil.getEntity());
        for (final ChunkData data : ((NewChunks)this.module).data) {
            final double dX = data.getX() * 16;
            final double dZ = data.getZ() * 16;
            final AxisAlignedBB bb = new AxisAlignedBB(dX, 0.0, dZ, dX + 16.0, 0.0, dZ + 16.0);
            if (frustum.isBoundingBoxInFrustum(bb)) {
                RenderUtil.doPosition(Interpolation.offsetRenderPos(bb));
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (!lines) {
            GL11.glDisable(2848);
        }
        if (depth) {
            GL11.glEnable(2929);
        }
        if (texture) {
            GL11.glEnable(3553);
        }
        if (!blend) {
            GL11.glDisable(3042);
        }
        if (lightning) {
            GL11.glEnable(2896);
        }
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }
}
