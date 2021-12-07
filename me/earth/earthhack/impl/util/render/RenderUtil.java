//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.render;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.client.gui.*;
import java.nio.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import java.awt.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.*;
import org.lwjgl.*;

public class RenderUtil implements Globals
{
    private static ScaledResolution res;
    private static final GlShader IMAGE_SHADER;
    private static final FloatBuffer screenCoords;
    private static final IntBuffer viewport;
    private static final FloatBuffer modelView;
    private static final FloatBuffer projection;
    
    public static void updateMatrices() {
        GL11.glGetFloat(2982, RenderUtil.modelView);
        GL11.glGetFloat(2983, RenderUtil.projection);
        GL11.glGetInteger(2978, RenderUtil.viewport);
        final ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        GLUProjection.getInstance().updateMatrices(RenderUtil.viewport, RenderUtil.modelView, RenderUtil.projection, res.getScaledWidth() / (float)Minecraft.getMinecraft().displayWidth, res.getScaledHeight() / (float)Minecraft.getMinecraft().displayHeight);
    }
    
    public static Entity getEntity() {
        return (Entity)((RenderUtil.mc.getRenderViewEntity() == null) ? RenderUtil.mc.player : RenderUtil.mc.getRenderViewEntity());
    }
    
    public static void renderBox(final BlockPos pos, final Color color, final float height) {
        GL11.glPushMatrix();
        GL11.glPushAttrib(1048575);
        final AxisAlignedBB bb = Interpolation.interpolatePos(pos, height);
        startRender();
        drawOutline(bb, 1.5f, color);
        endRender();
        final Color boxColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 76);
        startRender();
        drawBox(bb, boxColor);
        endRender();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }
    
    public static void renderBox(final BlockPos pos, final Color color, final float height, final int boxAlpha) {
        GL11.glPushMatrix();
        GL11.glPushAttrib(1048575);
        final AxisAlignedBB bb = Interpolation.interpolatePos(pos, height);
        startRender();
        drawOutline(bb, 1.5f, color);
        endRender();
        final Color boxColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), boxAlpha);
        startRender();
        drawBox(bb, boxColor);
        endRender();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }
    
    public static void renderBox(final AxisAlignedBB bb, final Color color, final Color outLineColor, final float lineWidth) {
        GL11.glPushMatrix();
        GL11.glPushAttrib(1048575);
        startRender();
        drawOutline(bb, lineWidth, outLineColor);
        endRender();
        startRender();
        drawBox(bb, color);
        endRender();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }
    
    public static void drawBox(final AxisAlignedBB bb) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        fillBox(bb);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawBox(final AxisAlignedBB bb, final Color color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        color(color);
        fillBox(bb);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawOutline(final AxisAlignedBB bb, final float lineWidth) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(lineWidth);
        fillOutline(bb);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawOutline(final AxisAlignedBB bb, final float lineWidth, final Color color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(lineWidth);
        color(color);
        fillOutline(bb);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void fillOutline(final AxisAlignedBB bb) {
        if (bb != null) {
            GL11.glBegin(1);
            GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
            GL11.glEnd();
        }
    }
    
    public static void fillBox(final AxisAlignedBB boundingBox) {
        if (boundingBox != null) {
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glEnd();
        }
    }
    
    public static void prepare(final float x, final float y, final float x1, final float y1, final float lineWidth, final int color, final int color1, final int color2) {
        startRender();
        prepare(x, y, x1, y1, color2, color1);
        color(color);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        endRender();
    }
    
    public static void prepare(final float x, final float y, final float x1, final float y1, final float lineWidth, final int color, final int color1) {
        startRender();
        prepare(x, y, x1, y1, color);
        color(color1);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        endRender();
    }
    
    public static void prepare(final float x, final float y, final float x1, final float y1, final int color, final int color1) {
        startRender();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        color(color);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        color(color1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        endRender();
    }
    
    public static void prepare(final float x, final float y, final float x1, final float y1, final int color) {
        startRender();
        color(color);
        scissor(x, y, x1, y1);
        endRender();
    }
    
    public static void scissor(final float x, final float y, final float x1, final float y1) {
        RenderUtil.res = new ScaledResolution(RenderUtil.mc);
        final int scale = RenderUtil.res.getScaleFactor();
        GL11.glScissor((int)(x * scale), (int)((RenderUtil.res.getScaledHeight() - y1) * scale), (int)((x1 - x) * scale), (int)((y1 - y) * scale));
    }
    
    public static void color(final Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    public static void color(final int color) {
        final float[] color4f = ColorUtil.toArray(color);
        GL11.glColor4f(color4f[0], color4f[1], color4f[2], color4f[3]);
    }
    
    public static void color(final float r, final float g, final float b, final float a) {
        GL11.glColor4f(r, g, b, a);
    }
    
    public static void startRender() {
        GL11.glPushAttrib(1048575);
        GL11.glPushMatrix();
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4353);
        GL11.glDisable(2896);
    }
    
    public static void endRender() {
        GL11.glEnable(2896);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }
    
    public static void doPosition(final AxisAlignedBB bb) {
        GL11.glBegin(3);
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
        GL11.glEnd();
    }
    
    public static boolean mouseWithinBounds(final int mouseX, final int mouseY, final double x, final double y, final double width, final double height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
    
    public static void drawNametag(final String text, final AxisAlignedBB interpolated, final double scale, final int color) {
        drawNametag(text, interpolated, scale, color, true);
    }
    
    public static void drawNametag(final String text, final AxisAlignedBB interpolated, final double scale, final int color, final boolean rectangle) {
        final double x = (interpolated.minX + interpolated.maxX) / 2.0;
        final double y = (interpolated.minY + interpolated.maxY) / 2.0;
        final double z = (interpolated.minZ + interpolated.maxZ) / 2.0;
        drawNametag(text, x, y, z, scale, color, rectangle);
    }
    
    public static void drawNametag(final String text, final double x, final double y, final double z, final double scale, final int color) {
        drawNametag(text, x, y, z, scale, color, true);
    }
    
    public static void drawNametag(final String text, final double x, final double y, final double z, final double scale, final int color, final boolean rectangle) {
        final double dist = getEntity().getDistance(x + RenderUtil.mc.getRenderManager().viewerPosX, y + RenderUtil.mc.getRenderManager().viewerPosY, z + RenderUtil.mc.getRenderManager().viewerPosZ);
        final int textWidth = Managers.TEXT.getStringWidth(text) / 2;
        double scaling = 0.0018 + scale * dist;
        if (dist <= 8.0) {
            scaling = 0.0245;
        }
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate(x, y + 0.4000000059604645, z);
        GlStateManager.rotate(-RenderUtil.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        final float xRot = (RenderUtil.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f;
        GlStateManager.rotate(RenderUtil.mc.getRenderManager().playerViewX, xRot, 0.0f, 0.0f);
        GlStateManager.scale(-scaling, -scaling, scaling);
        GlStateManager.disableDepth();
        if (rectangle) {
            GlStateManager.enableBlend();
            prepare((float)(-textWidth - 1), -Managers.TEXT.getStringHeight(), (float)(textWidth + 2), 1.0f, 1.8f, 1426064384, 855638016);
            GlStateManager.disableBlend();
        }
        GlStateManager.enableBlend();
        Managers.TEXT.drawStringWithShadow(text, (float)(-textWidth), (float)(-(RenderUtil.mc.fontRendererObj.FONT_HEIGHT - 1)), color);
        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();
    }
    
    public static void drawImageBlock(final BlockPos pos) {
        assert RenderUtil.IMAGE_SHADER != null;
        GL11.glPushAttrib(1048575);
        GL11.glPushMatrix();
        final AxisAlignedBB bb = Interpolation.interpolatePos(pos, 1.0f);
        RenderUtil.IMAGE_SHADER.bind();
        RenderUtil.mc.getTextureManager().bindTexture(new ResourceLocation("earthhack:textures/client/galaxy.jpg"));
        RenderUtil.IMAGE_SHADER.set("overlaySampler", 0);
        RenderUtil.IMAGE_SHADER.set("dimensions", new Vec2f((float)RenderUtil.mc.displayWidth, (float)RenderUtil.mc.displayHeight));
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        fillBox(bb);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        RenderUtil.IMAGE_SHADER.unbind();
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }
    
    public static Vec2d to2D(final double x, final double y, final double z) {
        final GLUProjection.Projection projection = GLUProjection.getInstance().project(x, y, z, GLUProjection.ClampMode.ORTHOGONAL, true);
        return new Vec2d(projection.getX(), projection.getY());
    }
    
    static {
        IMAGE_SHADER = GlShader.createShader("image");
        screenCoords = BufferUtils.createFloatBuffer(3);
        viewport = BufferUtils.createIntBuffer(16);
        modelView = BufferUtils.createFloatBuffer(16);
        projection = BufferUtils.createFloatBuffer(16);
        RenderUtil.res = new ScaledResolution(RenderUtil.mc);
    }
}
