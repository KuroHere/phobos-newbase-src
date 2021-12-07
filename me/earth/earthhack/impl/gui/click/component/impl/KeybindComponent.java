//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.click.component.impl;

import me.earth.earthhack.impl.gui.click.component.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.render.*;
import net.minecraft.client.*;
import com.mojang.realmsclient.gui.*;
import me.earth.earthhack.api.util.bind.*;
import me.earth.earthhack.api.setting.*;

public class KeybindComponent extends Component
{
    private final BindSetting bindSetting;
    private boolean binding;
    
    public KeybindComponent(final BindSetting bindSetting, final float posX, final float posY, final float offsetX, final float offsetY, final float width, final float height) {
        super(bindSetting.getName(), posX, posY, offsetX, offsetY, width, height);
        this.bindSetting = bindSetting;
    }
    
    @Override
    public void moved(final float posX, final float posY) {
        super.moved(posX, posY);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        final boolean hovered = RenderUtil.mouseWithinBounds(mouseX, mouseY, this.getFinishedX() + 5.0f, this.getFinishedY() + 1.0f, this.getWidth() - 10.0f, this.getHeight() - 2.0f);
        Render2DUtil.drawBorderedRect(this.getFinishedX() + 4.5f, this.getFinishedY() + 1.0f, this.getFinishedX() + this.getWidth() - 4.5f, this.getFinishedY() + this.getHeight() - 0.5f, 0.5f, hovered ? 1714631475 : 0, -16777216);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.isBinding() ? "Press a key..." : (this.getBindSetting().getName() + ": " + ChatFormatting.GRAY + ((Setting<Object>)this.getBindSetting()).getValue()), this.getFinishedX() + 6.5f, this.getFinishedY() + this.getHeight() - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT - 1.0f, -1);
    }
    
    @Override
    public void keyTyped(final char character, final int keyCode) {
        super.keyTyped(character, keyCode);
        if (this.isBinding()) {
            final Bind bind = Bind.fromKey((keyCode == 1 || keyCode == 57 || keyCode == 211) ? 0 : keyCode);
            this.getBindSetting().setValue(bind);
            this.setBinding(false);
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        final boolean hovered = RenderUtil.mouseWithinBounds(mouseX, mouseY, this.getFinishedX() + 5.0f, this.getFinishedY() + 1.0f, this.getWidth() - 10.0f, this.getHeight() - 2.0f);
        if (hovered && mouseButton == 0) {
            this.setBinding(!this.isBinding());
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
    }
    
    public BindSetting getBindSetting() {
        return this.bindSetting;
    }
    
    public boolean isBinding() {
        return this.binding;
    }
    
    public void setBinding(final boolean binding) {
        this.binding = binding;
    }
}
