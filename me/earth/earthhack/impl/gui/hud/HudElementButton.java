// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.hud;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.clickgui.*;
import me.earth.earthhack.api.hud.*;
import me.earth.earthhack.impl.managers.*;
import java.awt.*;
import me.earth.earthhack.impl.util.render.*;
import me.earth.earthhack.impl.util.misc.*;
import me.earth.earthhack.impl.modules.*;

public class HudElementButton extends AbstractGuiElement
{
    private static final ModuleCache<ClickGui> CLICK_GUI;
    private final HudElement element;
    
    public HudElementButton(final HudElement element) {
        super(element.getName(), (float)Managers.TEXT.getStringWidth(element.getName()), Managers.TEXT.getStringHeight());
        this.element = element;
    }
    
    public void draw(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.element.isEnabled()) {
            Render2DUtil.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), HudElementButton.CLICK_GUI.get().color.getValue().getRGB());
            Managers.TEXT.drawString(this.element.getName(), this.getX() + this.getWidth() / 2.0f - Managers.TEXT.getStringWidth(this.element.getName()) / 2.0f, this.getY(), Color.WHITE.getRGB());
        }
        else {
            Managers.TEXT.drawString(this.element.getName(), this.getX() + this.getWidth() / 2.0f - Managers.TEXT.getStringWidth(this.element.getName()) / 2.0f, this.getY(), Color.GRAY.getRGB());
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (GuiUtil.isHovered(this, mouseX, mouseY) && mouseButton == 0) {
            this.element.toggle();
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void keyPressed(final char eventChar, final int key) {
    }
    
    public HudElement getElement() {
        return this.element;
    }
    
    @Override
    public float getHeight() {
        return Managers.TEXT.getStringHeight();
    }
    
    static {
        CLICK_GUI = Caches.getModule(ClickGui.class);
    }
}
