//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.click.component.impl;

import me.earth.earthhack.impl.gui.click.component.*;
import me.earth.earthhack.api.setting.settings.*;
import net.minecraft.client.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.util.math.*;
import java.awt.*;
import me.earth.earthhack.impl.modules.client.clickgui.*;
import me.earth.earthhack.impl.util.render.*;
import me.earth.earthhack.impl.util.math.*;

public class NumberComponent extends Component
{
    private final NumberSetting<Number> numberSetting;
    private boolean sliding;
    
    public NumberComponent(final NumberSetting<Number> numberSetting, final float posX, final float posY, final float offsetX, final float offsetY, final float width, final float height) {
        super(numberSetting.getName(), posX, posY, offsetX, offsetY, width, height);
        this.numberSetting = numberSetting;
    }
    
    @Override
    public void moved(final float posX, final float posY) {
        super.moved(posX, posY);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        final boolean hovered = RenderUtil.mouseWithinBounds(mouseX, mouseY, this.getFinishedX(), this.getFinishedY(), this.getWidth(), this.getHeight());
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.getLabel() + ": " + ChatFormatting.GRAY + this.getNumberSetting().getValue(), this.getFinishedX() + 5.0f, this.getFinishedY() + this.getHeight() / 2.0f - (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT >> 1), -1);
        final float length = (float)MathHelper.floor((this.getNumberSetting().getValue().floatValue() - this.getNumberSetting().getMin().floatValue()) / (this.getNumberSetting().getMax().floatValue() - this.getNumberSetting().getMin().floatValue()) * (this.getWidth() - 10.0f));
        Render2DUtil.drawBorderedRect(this.getFinishedX() + 5.0f, this.getFinishedY() + this.getHeight() - 2.5f, this.getFinishedX() + 5.0f + length, this.getFinishedY() + this.getHeight() - 0.5f, 0.5f, hovered ? Component.getClickGui().get().color.getValue().brighter().getRGB() : Component.getClickGui().get().color.getValue().getRGB(), -16777216);
        if (this.sliding) {
            final double val = (mouseX - (this.getFinishedX() + 5.0f)) * (this.getNumberSetting().getMax().doubleValue() - this.getNumberSetting().getMin().doubleValue()) / (this.getWidth() - 10.0f) + this.getNumberSetting().getMin().doubleValue();
            this.getNumberSetting().setValue(this.getNumberSetting().numberToValue(MathUtil.round(val, 2)));
        }
    }
    
    @Override
    public void keyTyped(final char character, final int keyCode) {
        super.keyTyped(character, keyCode);
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        final boolean hovered = RenderUtil.mouseWithinBounds(mouseX, mouseY, this.getFinishedX(), this.getFinishedY(), this.getWidth(), this.getHeight());
        if (hovered && mouseButton == 0) {
            this.setSliding(true);
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        if (this.isSliding()) {
            this.setSliding(false);
        }
    }
    
    public NumberSetting<Number> getNumberSetting() {
        return this.numberSetting;
    }
    
    public boolean isSliding() {
        return this.sliding;
    }
    
    public void setSliding(final boolean sliding) {
        this.sliding = sliding;
    }
}
