//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.render;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.customfont.*;
import me.earth.earthhack.impl.gui.font.*;
import me.earth.earthhack.impl.modules.*;
import java.awt.*;
import net.minecraft.client.renderer.*;

public class TextRenderer implements Globals
{
    private final ModuleCache<FontMod> fontMod;
    private CustomFontRenderer renderer;
    
    public TextRenderer() {
        this.fontMod = Caches.getModule(FontMod.class);
        this.renderer = new CustomFontRenderer(new Font("Arial", 0, 17), true, true);
    }
    
    public float drawStringWithShadow(final String text, final float x, final float y, final int color) {
        if (this.fontMod.isEnabled()) {
            return this.renderer.drawStringWithShadow(text, x, y, color);
        }
        return (float)TextRenderer.mc.fontRendererObj.drawString(text, x, y, color, true);
    }
    
    public float drawString(final String text, final float x, final float y, final int color) {
        if (this.fontMod.isEnabled()) {
            return this.renderer.drawString(text, x, y, color);
        }
        return (float)TextRenderer.mc.fontRendererObj.drawString(text, x, y, color, false);
    }
    
    public float drawString(final String text, final float x, final float y, final int color, final boolean dropShadow) {
        if (!this.fontMod.isEnabled()) {
            return (float)TextRenderer.mc.fontRendererObj.drawString(text, x, y, color, dropShadow);
        }
        if (dropShadow) {
            return this.renderer.drawStringWithShadow(text, x, y, color);
        }
        return this.renderer.drawString(text, x, y, color);
    }
    
    public void drawStringScaled(final String text, final float x, final float y, final int color, final boolean dropShadow, final float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, scale);
        this.drawString(text, x / scale, y / scale, color, dropShadow);
        GlStateManager.scale(1.0f / scale, 1.0f / scale, 1.0f / scale);
        GlStateManager.popMatrix();
    }
    
    public int getStringWidth(final String text) {
        if (this.fontMod.isEnabled()) {
            return this.renderer.getStringWidth(text);
        }
        return TextRenderer.mc.fontRendererObj.getStringWidth(text);
    }
    
    public float getStringWidthScaled(final String text, final float scale) {
        if (this.fontMod.isEnabled()) {
            return this.renderer.getStringWidth(text) * scale;
        }
        return TextRenderer.mc.fontRendererObj.getStringWidth(text) * scale;
    }
    
    public float getStringHeight() {
        if (this.fontMod.isEnabled()) {
            return (float)this.renderer.getHeight();
        }
        return (float)TextRenderer.mc.fontRendererObj.FONT_HEIGHT;
    }
    
    public float getStringHeight(final float scale) {
        if (this.fontMod.isEnabled()) {
            return this.renderer.getHeight() * scale;
        }
        return TextRenderer.mc.fontRendererObj.FONT_HEIGHT * scale;
    }
    
    public void setFontRenderer(final Font font, final boolean antiAlias, final boolean metrics) {
        this.renderer = new CustomFontRenderer(font, antiAlias, metrics);
    }
}
