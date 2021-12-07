//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.lagometer;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import java.awt.*;
import me.earth.earthhack.impl.util.render.*;

final class ListenerRender extends ModuleListener<LagOMeter, Render3DEvent>
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
    
    public ListenerRender(final LagOMeter module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        if (!((LagOMeter)this.module).esp.getValue()) {
            return;
        }
        double factor = 1.0;
        if (((LagOMeter)this.module).teleported.get()) {
            final double time = ((LagOMeter)this.module).time.getValue();
            final long t = System.currentTimeMillis() - Managers.NCP.getTimeStamp();
            if (t > time) {
                return;
            }
            factor = MathUtil.clamp(1.0 - t / time, 0.0, 1.0);
        }
        GlStateManager.pushMatrix();
        final double x = ((LagOMeter)this.module).x - ListenerRender.mc.getRenderManager().viewerPosX;
        final double y = ((LagOMeter)this.module).y - ListenerRender.mc.getRenderManager().viewerPosY;
        final double z = ((LagOMeter)this.module).z - ListenerRender.mc.getRenderManager().viewerPosZ;
        final float yaw = ((LagOMeter)this.module).yaw;
        final float pitch = ((LagOMeter)this.module).pitch;
        final AxisAlignedBB head = new AxisAlignedBB(x - 0.2, y + 1.5, z - 0.25, x + 0.2, y + 1.95, z + 0.25);
        final AxisAlignedBB chest = new AxisAlignedBB(x - 0.18, y + 0.8, z - 0.275, x + 0.18, y + 1.5, z + 0.275);
        final AxisAlignedBB arm1 = new AxisAlignedBB(x - 0.1, y + 0.75, z + 0.275, x + 0.1, y + 1.5, z + 0.5);
        final AxisAlignedBB arm2 = new AxisAlignedBB(x - 0.1, y + 0.75, z - 0.275, x + 0.1, y + 1.5, z - 0.5);
        final AxisAlignedBB leg1 = new AxisAlignedBB(x - 0.15, y + 0.0, z + 0.0, x + 0.15, y + 0.8, z + 0.25);
        final AxisAlignedBB leg2 = new AxisAlignedBB(x - 0.15, y + 0.0, z + 0.0, x + 0.15, y + 0.8, z - 0.25);
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(180.0f + -(yaw + 90.0f), 0.0f, 1.0f, 0.0f);
        GlStateManager.translate(-x, -y, -z);
        final Color box = this.getColor(((LagOMeter)this.module).color.getValue(), factor);
        final Color out = this.getColor(((LagOMeter)this.module).outline.getValue(), factor);
        this.renderAxis(chest, box, out);
        this.renderAxis(arm1, box, out);
        this.renderAxis(arm2, box, out);
        this.renderAxis(leg1, box, out);
        this.renderAxis(leg2, box, out);
        GlStateManager.translate(x, y + 1.5, z);
        GlStateManager.rotate(pitch, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(-x, -y - 1.5, -z);
        this.renderAxis(head, box, out);
        GlStateManager.translate(x, y + 1.5, z);
        GlStateManager.rotate(-pitch, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(-x, -y - 1.5, -z);
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(180.0f + (yaw + 90.0f), 0.0f, 1.0f, 0.0f);
        GlStateManager.translate(-x, -y, -z);
        if (((LagOMeter)this.module).nametag.getValue()) {
            final int color = ((LagOMeter)this.module).textColor.getRGB();
            final double scale = ((LagOMeter)this.module).scale.getValue();
            RenderUtil.drawNametag("Lag", x, y + 0.7, z, scale, color, false);
        }
        GlStateManager.popMatrix();
    }
    
    private void renderAxis(final AxisAlignedBB bb, final Color color, final Color outline) {
        RenderUtil.renderBox(bb, color, outline, ((LagOMeter)this.module).lineWidth.getValue());
    }
    
    private Color getColor(final Color c, final double factor) {
        if (factor == 1.0) {
            return c;
        }
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), (int)MathUtil.clamp(c.getAlpha() * factor, 0.0, 255.0));
    }
}
