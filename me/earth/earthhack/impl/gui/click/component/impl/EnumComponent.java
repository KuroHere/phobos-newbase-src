//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.click.component.impl;

import me.earth.earthhack.impl.gui.click.component.*;
import me.earth.earthhack.api.setting.settings.*;
import net.minecraft.client.*;
import com.mojang.realmsclient.gui.*;
import me.earth.earthhack.impl.util.render.*;
import me.earth.earthhack.api.util.*;

public class EnumComponent<E extends Enum<E>> extends Component
{
    private final EnumSetting<E> enumSetting;
    
    public EnumComponent(final EnumSetting<E> enumSetting, final float posX, final float posY, final float offsetX, final float offsetY, final float width, final float height) {
        super(enumSetting.getName(), posX, posY, offsetX, offsetY, width, height);
        this.enumSetting = enumSetting;
    }
    
    @Override
    public void moved(final float posX, final float posY) {
        super.moved(posX, posY);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.getLabel() + ": " + ChatFormatting.GRAY + this.getEnumSetting().getValue().name(), this.getFinishedX() + 5.0f, this.getFinishedY() + this.getHeight() / 2.0f - (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT >> 1), -1);
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        final boolean hovered = RenderUtil.mouseWithinBounds(mouseX, mouseY, this.getFinishedX() + 5.0f, this.getFinishedY() + 1.0f, this.getWidth() - 10.0f, this.getHeight() - 2.0f);
        if (hovered) {
            if (mouseButton == 0) {
                this.getEnumSetting().setValue((E)EnumHelper.next(this.getEnumSetting().getValue()));
            }
            else if (mouseButton == 1) {
                this.getEnumSetting().setValue((E)EnumHelper.previous(this.getEnumSetting().getValue()));
            }
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
    }
    
    public EnumSetting<E> getEnumSetting() {
        return this.enumSetting;
    }
}
