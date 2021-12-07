//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import net.minecraft.util.*;
import java.awt.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.render.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.impl.managers.render.*;

final class ListenerRender extends ModuleListener<AutoCrystal, Render3DEvent>
{
    private final Map<BlockPos, Long> fadeList;
    private static final ResourceLocation CRYSTAL_LOCATION;
    
    public ListenerRender(final AutoCrystal module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
        this.fadeList = new HashMap<BlockPos, Long>();
    }
    
    public void invoke(final Render3DEvent event) {
        final RenderDamagePos mode = ((AutoCrystal)this.module).renderDamage.getValue();
        if (((AutoCrystal)this.module).render.getValue() && ((AutoCrystal)this.module).box.getValue() && ((AutoCrystal)this.module).fade.getValue() && !((AutoCrystal)this.module).isPingBypass()) {
            for (final Map.Entry<BlockPos, Long> set : this.fadeList.entrySet()) {
                if (((AutoCrystal)this.module).getRenderPos() == set.getKey()) {
                    continue;
                }
                final Color boxColor = ((AutoCrystal)this.module).boxColor.getValue();
                final Color outlineColor = ((AutoCrystal)this.module).outLine.getValue();
                final float maxBoxAlpha = (float)boxColor.getAlpha();
                final float maxOutlineAlpha = (float)outlineColor.getAlpha();
                final float alphaBoxAmount = maxBoxAlpha / ((AutoCrystal)this.module).fadeTime.getValue();
                final float alphaOutlineAmount = maxOutlineAlpha / ((AutoCrystal)this.module).fadeTime.getValue();
                final int fadeBoxAlpha = MathHelper.clamp((int)(alphaBoxAmount * (set.getValue() + ((AutoCrystal)this.module).fadeTime.getValue() - System.currentTimeMillis())), 0, (int)maxBoxAlpha);
                final int fadeOutlineAlpha = MathHelper.clamp((int)(alphaOutlineAmount * (set.getValue() + ((AutoCrystal)this.module).fadeTime.getValue() - System.currentTimeMillis())), 0, (int)maxOutlineAlpha);
                if (!((AutoCrystal)this.module).box.getValue()) {
                    continue;
                }
                RenderUtil.renderBox(Interpolation.interpolatePos(set.getKey(), 1.0f), new Color(boxColor.getRed(), boxColor.getGreen(), boxColor.getBlue(), fadeBoxAlpha), new Color(outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue(), fadeOutlineAlpha), 1.5f);
            }
        }
        final BlockPos pos;
        if (((AutoCrystal)this.module).render.getValue() && !((AutoCrystal)this.module).isPingBypass() && (pos = ((AutoCrystal)this.module).getRenderPos()) != null) {
            if (!((AutoCrystal)this.module).fade.getValue() && ((AutoCrystal)this.module).box.getValue()) {
                RenderUtil.renderBox(Interpolation.interpolatePos(pos, 1.0f), ((AutoCrystal)this.module).boxColor.getValue(), ((AutoCrystal)this.module).outLine.getValue(), 1.5f);
            }
            if (mode != RenderDamagePos.None) {
                this.renderDamage(pos);
            }
            if (((AutoCrystal)this.module).fade.getValue()) {
                this.fadeList.put(pos, System.currentTimeMillis());
            }
        }
        this.fadeList.entrySet().removeIf(e -> e.getValue() + ((AutoCrystal)this.module).fadeTime.getValue() < System.currentTimeMillis());
    }
    
    private void renderDamage(final BlockPos pos) {
        final String text = ((AutoCrystal)this.module).damage;
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        final double x = pos.getX() + 0.5;
        final double y = pos.getY() + ((((AutoCrystal)this.module).renderDamage.getValue() == RenderDamagePos.OnTop) ? 1.35 : 0.5);
        final double z = pos.getZ() + 0.5;
        final float scale = 0.016666668f * ((((AutoCrystal)this.module).renderMode.getValue() == RenderDamage.Indicator) ? 0.95f : 1.3f);
        GlStateManager.translate(x - Interpolation.getRenderPosX(), y - Interpolation.getRenderPosY(), z - Interpolation.getRenderPosZ());
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-ListenerRender.mc.player.rotationYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(ListenerRender.mc.player.rotationPitch, (ListenerRender.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
        final int distance = (int)ListenerRender.mc.player.getDistance(x, y, z);
        float scaleD = distance / 2.0f / 3.0f;
        if (scaleD < 1.0f) {
            scaleD = 1.0f;
        }
        GlStateManager.scale(scaleD, scaleD, scaleD);
        final TextRenderer m = Managers.TEXT;
        GlStateManager.translate(-(m.getStringWidth(text) / 2.0), 0.0, 0.0);
        if (((AutoCrystal)this.module).renderMode.getValue() == RenderDamage.Indicator) {
            final Color clr = ((AutoCrystal)this.module).indicatorColor.getValue();
            Render2DUtil.drawUnfilledCircle(m.getStringWidth(text) / 2.0f, 0.0f, 22.0f, new Color(5, 5, 5, clr.getAlpha()).getRGB(), 5.0f);
            Render2DUtil.drawCircle(m.getStringWidth(text) / 2.0f, 0.0f, 22.0f, clr.getRGB());
            m.drawString(text, 0.0f, 6.0f, new Color(255, 255, 255).getRGB());
            Minecraft.getMinecraft().getTextureManager().bindTexture(ListenerRender.CRYSTAL_LOCATION);
            Gui.drawScaledCustomSizeModalRect((int)(m.getStringWidth(text) / 2.0f) - 10, -17, 0.0f, 0.0f, 12, 12, 22, 22, 12.0f, 12.0f);
        }
        else {
            m.drawStringWithShadow(text, 0.0f, 0.0f, new Color(255, 255, 255).getRGB());
        }
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    static {
        CRYSTAL_LOCATION = new ResourceLocation("earthhack:textures/client/crystal.png");
    }
}
