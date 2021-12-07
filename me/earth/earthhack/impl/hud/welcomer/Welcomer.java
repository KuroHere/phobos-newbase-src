//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.hud.welcomer;

import me.earth.earthhack.api.hud.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.client.renderer.*;
import java.awt.*;

public class Welcomer extends HudElement
{
    public static String text;
    
    public Welcomer() {
        super("Welcomer", false, Welcomer.mc.displayWidth / 2.0f - Managers.TEXT.getStringWidth(Welcomer.text) / 2.0f, 2.0f, (float)Managers.TEXT.getStringWidth(Welcomer.text), Managers.TEXT.getStringHeight());
    }
    
    @Override
    public void guiDraw(final int mouseX, final int mouseY, final float partialTicks) {
        GlStateManager.pushMatrix();
        super.guiDraw(mouseX, mouseY, partialTicks);
        Managers.TEXT.drawString(Welcomer.text, this.getX(), this.getY(), Color.WHITE.getRGB(), true);
        GlStateManager.popMatrix();
    }
    
    @Override
    public void guiUpdate(final int mouseX, final int mouseY, final float partialTicks) {
        super.guiUpdate(mouseX, mouseY, partialTicks);
        this.setWidth((float)Managers.TEXT.getStringWidth(Welcomer.text));
        this.setHeight(Managers.TEXT.getStringHeight());
    }
    
    @Override
    public void hudUpdate(final float partialTicks) {
        super.hudUpdate(partialTicks);
        this.setWidth((float)Managers.TEXT.getStringWidth(Welcomer.text));
        this.setHeight(Managers.TEXT.getStringHeight());
    }
    
    @Override
    public void hudDraw(final float partialTicks) {
        Managers.TEXT.drawString(Welcomer.text, this.getX(), this.getY(), Color.WHITE.getRGB(), true);
    }
    
    @Override
    public float getWidth() {
        return (float)Managers.TEXT.getStringWidth(Welcomer.text);
    }
    
    @Override
    public float getHeight() {
        return Managers.TEXT.getStringHeight();
    }
    
    static {
        Welcomer.text = "welcome to phobro hack megyn";
    }
}
