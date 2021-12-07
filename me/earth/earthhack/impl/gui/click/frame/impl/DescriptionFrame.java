//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.click.frame.impl;

import me.earth.earthhack.impl.gui.click.frame.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.clickgui.*;
import net.minecraft.util.*;
import java.awt.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.impl.util.render.*;
import java.util.*;
import me.earth.earthhack.impl.modules.*;

public class DescriptionFrame extends Frame
{
    private static final ModuleCache<ClickGui> CLICK_GUI;
    private static final ResourceLocation LEFT_EAR;
    private static final ResourceLocation RIGH_EAR;
    private String description;
    
    public DescriptionFrame(final float posX, final float posY, final float width, final float height) {
        super("Description", posX, posY, width, height);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.description == null || !DescriptionFrame.CLICK_GUI.get().description.getValue()) {
            return;
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
        final Color clr = DescriptionFrame.CLICK_GUI.get().color.getValue();
        if (DescriptionFrame.CLICK_GUI.get().catEars.getValue()) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(DescriptionFrame.LEFT_EAR);
            GlStateManager.color(clr.getRed() / 255.0f, clr.getGreen() / 255.0f, clr.getBlue() / 255.0f, 1.0f);
            Gui.drawScaledCustomSizeModalRect((int)this.getPosX() - 8, (int)this.getPosY() - 8, 0.0f, 0.0f, 20, 20, 20, 20, 20.0f, 20.0f);
            Minecraft.getMinecraft().getTextureManager().bindTexture(DescriptionFrame.RIGH_EAR);
            GlStateManager.color(clr.getRed() / 255.0f, clr.getGreen() / 255.0f, clr.getBlue() / 255.0f, 1.0f);
            Gui.drawScaledCustomSizeModalRect((int)(this.getPosX() + this.getWidth()) - 12, (int)this.getPosY() - 8, 0.0f, 0.0f, 20, 20, 20, 20, 20.0f, 20.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
        Render2DUtil.drawRect(this.getPosX(), this.getPosY(), this.getPosX() + this.getWidth(), this.getPosY() + this.getHeight(), DescriptionFrame.CLICK_GUI.get().color.getValue().getRGB());
        Render2DUtil.drawBorderedRect(this.getPosX(), this.getPosY(), this.getPosX() + this.getWidth(), this.getPosY() + this.getHeight(), 0.5f, 0, -16777216);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.getLabel(), this.getPosX() + 3.0f, this.getPosY() + this.getHeight() / 2.0f - (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT >> 1), -1);
        float y = this.getPosY() + 2.0f + this.getHeight() / 2.0f + Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
        final List<String> strings = Minecraft.getMinecraft().fontRendererObj.listFormattedStringToWidth(this.getDescription(), (int)this.getWidth() - 1);
        Render2DUtil.drawRect(this.getPosX(), this.getPosY() + this.getHeight(), this.getPosX() + this.getWidth(), this.getPosY() + this.getHeight() + 3.0f + (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 1) * strings.size(), -1845493760);
        for (final String string : strings) {
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(string, this.getPosX() + 3.0f, y, -1);
            y += Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 1;
        }
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    static {
        CLICK_GUI = Caches.getModule(ClickGui.class);
        LEFT_EAR = new ResourceLocation("earthhack:textures/gui/left_ear.png");
        RIGH_EAR = new ResourceLocation("earthhack:textures/gui/right_ear.png");
    }
}
