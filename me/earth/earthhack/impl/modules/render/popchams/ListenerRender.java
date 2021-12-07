//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.popchams;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import java.awt.*;
import java.util.*;
import me.earth.earthhack.impl.util.render.*;

final class ListenerRender extends ModuleListener<PopChams, Render3DEvent>
{
    private static final double HEAD_X = -0.2;
    private static final double HEAD_Y = 1.5;
    private static final double HEAD_Z = -0.25;
    private static final double HEAD_X1 = 0.2;
    private static final double HEAD_Y1 = 1.95;
    private static final double HEAD_Z1 = 0.25;
    private static final double CHEST_X = -0.18;
    private static final double CHEST_Y = 0.8;
    private static final double CHEST_Z = -0.275;
    private static final double CHEST_X1 = 0.18;
    private static final double CHEST_Y1 = 1.5;
    private static final double CHEST_Z1 = 0.275;
    private static final double ARM1_X = -0.1;
    private static final double ARM1_Y = 0.75;
    private static final double ARM1_Z = 0.275;
    private static final double ARM1_X1 = 0.1;
    private static final double ARM1_Y1 = 1.5;
    private static final double ARM1_Z1 = 0.5;
    private static final double ARM2_X = -0.1;
    private static final double ARM2_Y = 0.75;
    private static final double ARM2_Z = -0.275;
    private static final double ARM2_X1 = 0.1;
    private static final double ARM2_Y1 = 1.5;
    private static final double ARM2_Z1 = -0.5;
    private static final double LEG1_X = -0.15;
    private static final double LEG1_Y = 0.0;
    private static final double LEG1_Z = 0.0;
    private static final double LEG1_X1 = 0.15;
    private static final double LEG1_Y1 = 0.8;
    private static final double LEG1_Z1 = 0.25;
    private static final double LEG2_X = -0.15;
    private static final double LEG2_Y = 0.0;
    private static final double LEG2_Z = 0.0;
    private static final double LEG2_X1 = 0.15;
    private static final double LEG2_Y1 = 0.8;
    private static final double LEG2_Z1 = -0.25;
    
    public ListenerRender(final PopChams module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        for (final Map.Entry<String, PopChams.PopData> set : ((PopChams)this.module).getPopDataHashMap().entrySet()) {
            final PopChams.PopData data = set.getValue();
            final double x = data.getX() - ListenerRender.mc.getRenderManager().viewerPosX;
            final double y = data.getY() - ListenerRender.mc.getRenderManager().viewerPosY;
            final double z = data.getZ() - ListenerRender.mc.getRenderManager().viewerPosZ;
            final float yaw = data.getYaw();
            final float pitch = data.getPitch();
            final AxisAlignedBB head = new AxisAlignedBB(x - 0.2, y + 1.5, z - 0.25, x + 0.2, y + 1.95, z + 0.25);
            final AxisAlignedBB chest = new AxisAlignedBB(x - 0.18, y + 0.8, z - 0.275, x + 0.18, y + 1.5, z + 0.275);
            final AxisAlignedBB arm1 = new AxisAlignedBB(x - 0.1, y + 0.75, z + 0.275, x + 0.1, y + 1.5, z + 0.5);
            final AxisAlignedBB arm2 = new AxisAlignedBB(x - 0.1, y + 0.75, z - 0.275, x + 0.1, y + 1.5, z - 0.5);
            final AxisAlignedBB leg1 = new AxisAlignedBB(x - 0.15, y + 0.0, z + 0.0, x + 0.15, y + 0.8, z + 0.25);
            final AxisAlignedBB leg2 = new AxisAlignedBB(x - 0.15, y + 0.0, z + 0.0, x + 0.15, y + 0.8, z - 0.25);
            GlStateManager.pushMatrix();
            GlStateManager.translate(x, y, z);
            GlStateManager.rotate(180.0f + -(yaw + 90.0f), 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(-x, -y, -z);
            final Color boxColor = ((PopChams)this.module).getColor(data.getPlayer());
            final Color outlineColor = ((PopChams)this.module).getOutlineColor(data.getPlayer());
            final float maxBoxAlpha = (float)boxColor.getAlpha();
            final float maxOutlineAlpha = (float)outlineColor.getAlpha();
            final float alphaBoxAmount = maxBoxAlpha / ((PopChams)this.module).fadeTime.getValue();
            final float alphaOutlineAmount = maxOutlineAlpha / ((PopChams)this.module).fadeTime.getValue();
            final int fadeBoxAlpha = MathHelper.clamp((int)(alphaBoxAmount * (data.getTime() + ((PopChams)this.module).fadeTime.getValue() - System.currentTimeMillis())), 0, (int)maxBoxAlpha);
            final int fadeOutlineAlpha = MathHelper.clamp((int)(alphaOutlineAmount * (data.getTime() + ((PopChams)this.module).fadeTime.getValue() - System.currentTimeMillis())), 0, (int)maxOutlineAlpha);
            final Color box = new Color(boxColor.getRed(), boxColor.getGreen(), boxColor.getBlue(), fadeBoxAlpha);
            final Color out = new Color(outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue(), fadeOutlineAlpha);
            this.renderAxis(chest, box, out);
            this.renderAxis(arm1, box, out);
            this.renderAxis(arm2, box, out);
            this.renderAxis(leg1, box, out);
            this.renderAxis(leg2, box, out);
            GlStateManager.translate(x, y + 1.5, z);
            GlStateManager.rotate(pitch, 0.0f, 0.0f, 1.0f);
            GlStateManager.translate(-x, -y - 1.5, -z);
            this.renderAxis(head, box, out);
            GlStateManager.popMatrix();
        }
        ((PopChams)this.module).getPopDataHashMap().entrySet().removeIf(e -> e.getValue().getTime() + ((PopChams)this.module).fadeTime.getValue() < System.currentTimeMillis());
    }
    
    private void renderAxis(final AxisAlignedBB bb, final Color color, final Color outline) {
        RenderUtil.renderBox(bb, color, outline, ((PopChams)this.module).lineWidth.getValue());
    }
}
