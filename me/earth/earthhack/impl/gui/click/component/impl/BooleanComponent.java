//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.click.component.impl;

import me.earth.earthhack.impl.gui.click.component.*;
import me.earth.earthhack.api.setting.settings.*;
import net.minecraft.client.*;
import java.awt.*;
import me.earth.earthhack.impl.modules.client.clickgui.*;
import me.earth.earthhack.impl.util.render.*;

public class BooleanComponent extends Component
{
    private final BooleanSetting booleanSetting;
    
    public BooleanComponent(final BooleanSetting booleanSetting, final float posX, final float posY, final float offsetX, final float offsetY, final float width, final float height) {
        super(booleanSetting.getName(), posX, posY, offsetX, offsetY, width, height);
        this.booleanSetting = booleanSetting;
    }
    
    @Override
    public void moved(final float posX, final float posY) {
        super.moved(posX, posY);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        final boolean hovered = RenderUtil.mouseWithinBounds(mouseX, mouseY, this.getFinishedX() + this.getWidth() - 17.0f, this.getFinishedY() + 1.0f, 12.0, this.getHeight() - 2.0f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.getLabel(), this.getFinishedX() + 5.0f, this.getFinishedY() + this.getHeight() / 2.0f - (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT >> 1), ((boolean)this.getBooleanSetting().getValue()) ? -1 : -5592406);
        Render2DUtil.drawBorderedRect(this.getFinishedX() + this.getWidth() - 17.0f, this.getFinishedY() + 1.0f, this.getFinishedX() + this.getWidth() - 5.0f, this.getFinishedY() + this.getHeight() - 1.0f, 0.5f, ((boolean)this.getBooleanSetting().getValue()) ? (hovered ? Component.getClickGui().get().color.getValue().brighter().getRGB() : Component.getClickGui().get().color.getValue().getRGB()) : (hovered ? 1714631475 : 0), -16777216);
        if (this.getBooleanSetting().getValue()) {
            Render2DUtil.drawCheckMark(this.getFinishedX() + this.getWidth() - 11.0f, this.getFinishedY() + 1.0f, 10, -1);
        }
    }
    
    @Override
    public void keyTyped(final char character, final int keyCode) {
        super.keyTyped(character, keyCode);
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        final boolean hovered = RenderUtil.mouseWithinBounds(mouseX, mouseY, this.getFinishedX() + this.getWidth() - 17.0f, this.getFinishedY() + 1.0f, 12.0, this.getHeight() - 2.0f);
        if (hovered && mouseButton == 0) {
            this.getBooleanSetting().setValue(!this.getBooleanSetting().getValue());
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
    }
    
    public BooleanSetting getBooleanSetting() {
        return this.booleanSetting;
    }
}
