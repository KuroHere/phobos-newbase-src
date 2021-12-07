//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.hud.watermark;

import me.earth.earthhack.api.hud.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.client.renderer.*;
import java.awt.*;

public class Watermark extends HudElement
{
    public static String text;
    
    public Watermark() {
        super("Watermark", false, 2.0f, 2.0f, (float)Managers.TEXT.getStringWidth(Watermark.text), Managers.TEXT.getStringHeight());
    }
    
    @Override
    public void guiDraw(final int mouseX, final int mouseY, final float partialTicks) {
        GlStateManager.pushMatrix();
        super.guiDraw(mouseX, mouseY, partialTicks);
        Managers.TEXT.drawString(Watermark.text, this.getX(), this.getY(), Color.WHITE.getRGB(), true);
        GlStateManager.popMatrix();
    }
    
    @Override
    public void guiUpdate(final int mouseX, final int mouseY, final float partialTicks) {
        super.guiUpdate(mouseX, mouseY, partialTicks);
        this.setWidth((float)Managers.TEXT.getStringWidth(Watermark.text));
        this.setHeight(Managers.TEXT.getStringHeight());
    }
    
    @Override
    public void hudUpdate(final float partialTicks) {
        super.hudUpdate(partialTicks);
        this.setWidth((float)Managers.TEXT.getStringWidth(Watermark.text));
        this.setHeight(Managers.TEXT.getStringHeight());
    }
    
    @Override
    public void hudDraw(final float partialTicks) {
        Managers.TEXT.drawString(Watermark.text, this.getX(), this.getY(), Color.WHITE.getRGB(), true);
    }
    
    @Override
    public float getWidth() {
        return (float)Managers.TEXT.getStringWidth(Watermark.text);
    }
    
    @Override
    public float getHeight() {
        return Managers.TEXT.getStringHeight();
    }
    
    static {
        Watermark.text = "3arthh4ck 1.3.1";
    }
}
