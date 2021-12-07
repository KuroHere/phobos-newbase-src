//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.render;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.core.ducks.render.*;
import net.minecraft.client.shader.*;
import java.awt.*;
import net.minecraft.client.renderer.vertex.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.math.*;

public class Render2DUtil implements Globals
{
    protected static ShaderGroup blurShader;
    protected static float zLevel;
    protected static Framebuffer buffer;
    protected static int lastScale;
    protected static int lastScaleWidth;
    protected static int lastScaleHeight;
    protected static final ResourceLocation shader;
    protected static final StopWatch timer;
    
    public static void initFboAndShader() {
        try {
            (Render2DUtil.blurShader = new ShaderGroup(Render2DUtil.mc.getTextureManager(), Render2DUtil.mc.getResourceManager(), Render2DUtil.mc.getFramebuffer(), Render2DUtil.shader)).createBindFramebuffers(Render2DUtil.mc.displayWidth, Render2DUtil.mc.displayHeight);
            Render2DUtil.buffer = ((IShaderGroup)Render2DUtil.blurShader).getListFramebuffers().get(0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void setShaderConfigs(final float intensity, final float blurWidth, final float blurHeight) {
        ((IShaderGroup)Render2DUtil.blurShader).getListShaders().get(0).getShaderManager().getShaderUniform("Radius").set(intensity);
        ((IShaderGroup)Render2DUtil.blurShader).getListShaders().get(1).getShaderManager().getShaderUniform("Radius").set(intensity);
        ((IShaderGroup)Render2DUtil.blurShader).getListShaders().get(0).getShaderManager().getShaderUniform("BlurDir").set(blurWidth, blurHeight);
        ((IShaderGroup)Render2DUtil.blurShader).getListShaders().get(1).getShaderManager().getShaderUniform("BlurDir").set(blurHeight, blurWidth);
    }
    
    public static void drawBlurryRect(final float x, final float y, final float x1, final float y1, final int intensity, final int size) {
        drawRect((float)(int)x, (float)(int)y, (float)(int)x1, (float)(int)y1, new Color(50, 50, 50, 50).getRGB());
        blurArea((int)x, (int)y, (int)x1 - (int)x, (int)y1 - (int)y, (float)intensity, (float)size, (float)size);
    }
    
    public static void drawRect(final float startX, final float startY, final float endX, final float endY, final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)startX, (double)endY, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)endX, (double)endY, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)endX, (double)startY, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)startX, (double)startY, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawUnfilledCircle(final float x, final float y, final float radius, final int color, final float width) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glLineWidth(width);
        GL11.glBegin(2);
        for (int i = 0; i <= 360; ++i) {
            GL11.glVertex2d(x + Math.sin(i * 3.141526 / 180.0) * radius, y + Math.cos(i * 3.141526 / 180.0) * radius);
        }
        GL11.glEnd();
        GlStateManager.resetColor();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawCircle(final float x, final float y, final float radius, final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(9);
        for (int i = 0; i <= 360; ++i) {
            GL11.glVertex2d(x + Math.sin(i * 3.141526 / 180.0) * radius, y + Math.cos(i * 3.141526 / 180.0) * radius);
        }
        GL11.glEnd();
        GlStateManager.resetColor();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawBorderedRect(final float x, final float y, final float x2, final float y2, final float lineSize, final int color, final int borderColor) {
        drawRect(x, y, x2, y2, color);
        drawRect(x, y, x + lineSize, y2, borderColor);
        drawRect(x2 - lineSize, y, x2, y2, borderColor);
        drawRect(x, y2 - lineSize, x2, y2, borderColor);
        drawRect(x, y, x2, y + lineSize, borderColor);
    }
    
    public static void drawTexturedRect(final int x, final int y, final int textureX, final int textureY, final int width, final int height, final int zLevel) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder BufferBuilder = tessellator.getBuffer();
        BufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        BufferBuilder.pos((double)x, (double)(y + height), (double)zLevel).tex((double)(textureX * 0.00390625f), (double)((textureY + height) * 0.00390625f)).endVertex();
        BufferBuilder.pos((double)(x + width), (double)(y + height), (double)zLevel).tex((double)((textureX + width) * 0.00390625f), (double)((textureY + height) * 0.00390625f)).endVertex();
        BufferBuilder.pos((double)(x + width), (double)y, (double)zLevel).tex((double)((textureX + width) * 0.00390625f), (double)(textureY * 0.00390625f)).endVertex();
        BufferBuilder.pos((double)x, (double)y, (double)zLevel).tex((double)(textureX * 0.00390625f), (double)(textureY * 0.00390625f)).endVertex();
        tessellator.draw();
    }
    
    public static void drawCompleteImage(final float posX, final float posY, final float width, final float height) {
        GL11.glPushMatrix();
        GL11.glTranslatef(posX, posY, 0.0f);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(0.0f, height, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f(width, height, 0.0f);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f(width, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glPopMatrix();
    }
    
    public static void drawCheckMark(final float x, final float y, final int width, final int color) {
        final float f = (color >> 24 & 0xFF) / 255.0f;
        final float f2 = (color >> 16 & 0xFF) / 255.0f;
        final float f3 = (color >> 8 & 0xFF) / 255.0f;
        final float f4 = (color & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(3.0f);
        GL11.glBegin(3);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glVertex2d(x + width - 6.25, (double)(y + 2.75f));
        GL11.glVertex2d(x + width - 11.5, (double)(y + 10.25f));
        GL11.glVertex2d((double)(x + width - 13.75f), (double)(y + 7.75f));
        GL11.glEnd();
        GL11.glLineWidth(1.5f);
        GL11.glBegin(3);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glVertex2d(x + width - 6.5, (double)(y + 3.0f));
        GL11.glVertex2d(x + width - 11.5, (double)(y + 10.0f));
        GL11.glVertex2d(x + width - 13.5, (double)(y + 8.0f));
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void drawLine(final float x, final float y, final float x1, final float y1, final float lineWidth, final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(lineWidth);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(1);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x1, y1);
        GL11.glEnd();
        GlStateManager.resetColor();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawCheckeredBackground(final float x, float y, final float x2, final float y2) {
        drawRect(x, y, x2, y2, -1);
        boolean offset = false;
        while (y < y2) {
            for (float x3 = x + ((offset = !offset) ? 1 : 0); x3 < x2; x3 += 2.0f) {
                if (x3 <= x2 - 1.0f) {
                    drawRect(x3, y, x3 + 1.0f, y + 1.0f, -8355712);
                }
            }
            ++y;
        }
    }
    
    public static void drawGradientRect(final float left, final float top, final float right, final float bottom, final boolean sideways, final int startColor, final int endColor) {
        final float f = (startColor >> 24 & 0xFF) / 255.0f;
        final float f2 = (startColor >> 16 & 0xFF) / 255.0f;
        final float f3 = (startColor >> 8 & 0xFF) / 255.0f;
        final float f4 = (startColor & 0xFF) / 255.0f;
        final float f5 = (endColor >> 24 & 0xFF) / 255.0f;
        final float f6 = (endColor >> 16 & 0xFF) / 255.0f;
        final float f7 = (endColor >> 8 & 0xFF) / 255.0f;
        final float f8 = (endColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        if (sideways) {
            bufferbuilder.pos((double)left, (double)top, (double)Render2DUtil.zLevel).color(f2, f3, f4, f).endVertex();
            bufferbuilder.pos((double)left, (double)bottom, (double)Render2DUtil.zLevel).color(f2, f3, f4, f).endVertex();
            bufferbuilder.pos((double)right, (double)bottom, (double)Render2DUtil.zLevel).color(f6, f7, f8, f5).endVertex();
            bufferbuilder.pos((double)right, (double)top, (double)Render2DUtil.zLevel).color(f6, f7, f8, f5).endVertex();
        }
        else {
            bufferbuilder.pos((double)right, (double)top, (double)Render2DUtil.zLevel).color(f2, f3, f4, f).endVertex();
            bufferbuilder.pos((double)left, (double)top, (double)Render2DUtil.zLevel).color(f2, f3, f4, f).endVertex();
            bufferbuilder.pos((double)left, (double)bottom, (double)Render2DUtil.zLevel).color(f6, f7, f8, f5).endVertex();
            bufferbuilder.pos((double)right, (double)bottom, (double)Render2DUtil.zLevel).color(f6, f7, f8, f5).endVertex();
        }
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    public static void blurArea(final int x, final int y, final int width, final int height, final float intensity, final float blurWidth, final float blurHeight) {
        final ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
        final int factor = scale.getScaleFactor();
        final int factor2 = scale.getScaledWidth();
        final int factor3 = scale.getScaledHeight();
        if (Render2DUtil.lastScale != factor || Render2DUtil.lastScaleWidth != factor2 || Render2DUtil.lastScaleHeight != factor3 || Render2DUtil.buffer == null || Render2DUtil.blurShader == null) {
            initFboAndShader();
        }
        Render2DUtil.lastScale = factor;
        Render2DUtil.lastScaleWidth = factor2;
        Render2DUtil.lastScaleHeight = factor3;
        if (OpenGlHelper.isFramebufferEnabled()) {
            if (Render2DUtil.timer.passed(1000L)) {
                Render2DUtil.buffer.framebufferClear();
                Render2DUtil.timer.reset();
            }
            GL11.glScissor(x * factor, Render2DUtil.mc.displayHeight - y * factor - height * factor, width * factor, height * factor);
            GL11.glEnable(3089);
            setShaderConfigs(intensity, blurWidth, blurHeight);
            Render2DUtil.buffer.bindFramebuffer(true);
            Render2DUtil.blurShader.loadShaderGroup(Render2DUtil.mc.getRenderPartialTicks());
            Render2DUtil.mc.getFramebuffer().bindFramebuffer(true);
            GL11.glDisable(3089);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            Render2DUtil.buffer.framebufferRenderExt(Render2DUtil.mc.displayWidth, Render2DUtil.mc.displayHeight, false);
            GlStateManager.disableBlend();
            GL11.glScalef((float)factor, (float)factor, 0.0f);
        }
    }
    
    public static float[] getOnScreen2DHitBox(final Entity e, final int width, final int height) {
        float x = -1.0f;
        float y = -1.0f;
        float w = (float)(width + 1);
        float h = (float)(height + 1);
        final Vec3d pos = Interpolation.interpolateEntity(e);
        AxisAlignedBB bb = e.getEntityBoundingBox();
        if (e instanceof EntityEnderCrystal) {
            bb = new AxisAlignedBB(bb.minX + 0.30000001192092896, bb.minY + 0.20000000298023224, bb.minZ + 0.30000001192092896, bb.maxX - 0.30000001192092896, bb.maxY, bb.maxZ - 0.30000001192092896);
        }
        if (e instanceof EntityItem) {
            bb = new AxisAlignedBB(bb.minX, bb.minY + 0.699999988079071, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
        }
        bb = bb.addCoord(0.15000000596046448, 0.10000000149011612, 0.15000000596046448);
        final Vec3d[] array;
        final Vec3d[] corners = array = new Vec3d[] { new Vec3d(bb.minX - bb.maxX + e.width / 2.0f, 0.0, bb.minZ - bb.maxZ + e.width / 2.0f), new Vec3d(bb.maxX - bb.minX - e.width / 2.0f, 0.0, bb.minZ - bb.maxZ + e.width / 2.0f), new Vec3d(bb.minX - bb.maxX + e.width / 2.0f, 0.0, bb.maxZ - bb.minZ - e.width / 2.0f), new Vec3d(bb.maxX - bb.minX - e.width / 2.0f, 0.0, bb.maxZ - bb.minZ - e.width / 2.0f), new Vec3d(bb.minX - bb.maxX + e.width / 2.0f, bb.maxY - bb.minY, bb.minZ - bb.maxZ + e.width / 2.0f), new Vec3d(bb.maxX - bb.minX - e.width / 2.0f, bb.maxY - bb.minY, bb.minZ - bb.maxZ + e.width / 2.0f), new Vec3d(bb.minX - bb.maxX + e.width / 2.0f, bb.maxY - bb.minY, bb.maxZ - bb.minZ - e.width / 2.0f), new Vec3d(bb.maxX - bb.minX - e.width / 2.0f, bb.maxY - bb.minY, bb.maxZ - bb.minZ - e.width / 2.0f) };
        for (final Vec3d vec : array) {
            final GLUProjection.Projection projection = GLUProjection.getInstance().project(pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord, GLUProjection.ClampMode.NONE, false);
            x = Math.max(x, (float)projection.getX());
            y = Math.max(y, (float)projection.getY());
            w = Math.min(w, (float)projection.getX());
            h = Math.min(h, (float)projection.getY());
        }
        if (x != -1.0f && y != -1.0f && w != width + 1 && h != height + 1) {
            return new float[] { x, y, w, h };
        }
        return null;
    }
    
    public static void drawOutlineRect(final float x, final float y, final float w, final float h, final float lineWidth, final int c) {
        drawRect(x, y, x - lineWidth, h, c);
        drawRect(w + lineWidth, y, w, h, c);
        drawRect(x, y, w, y - lineWidth, c);
        drawRect(x, h + lineWidth, w, h, c);
    }
    
    static {
        shader = new ResourceLocation("earthhack:shaders/blur.json");
        timer = new StopWatch();
    }
}
