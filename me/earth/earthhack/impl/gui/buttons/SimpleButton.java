//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.buttons;

import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

public class SimpleButton extends GuiButton
{
    protected static final ResourceLocation LOCATION;
    protected final int textureX;
    protected final int textureY;
    protected final int hoveredX;
    protected final int hoveredY;
    
    public SimpleButton(final int buttonID, final int xPos, final int yPos, final int textureX, final int textureY, final int hoveredX, final int hoveredY) {
        super(buttonID, xPos, yPos, 20, 20, "");
        this.textureX = textureX;
        this.textureY = textureY;
        this.hoveredX = hoveredX;
        this.hoveredY = hoveredY;
    }
    
    public void onClick(final GuiScreen parent, final int id) {
    }
    
    public void func_191745_a(final Minecraft mc, final int mouseX, final int mouseY, final float partialTicks) {
        if (this.visible) {
            this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
            mc.getTextureManager().bindTexture(SimpleButton.LOCATION);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, this.hovered ? this.hoveredX : this.textureX, this.hovered ? this.hoveredY : this.textureY, this.width, this.height);
        }
    }
    
    static {
        LOCATION = new ResourceLocation("earthhack:textures/gui/gui_textures.png");
    }
}
