//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.hud;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.clickgui.*;
import me.earth.earthhack.impl.util.misc.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.api.hud.*;
import java.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import me.earth.earthhack.impl.util.render.*;
import me.earth.earthhack.impl.modules.*;

public class HudPanel extends AbstractGuiElement
{
    private static final ModuleCache<ClickGui> CLICK_GUI;
    private boolean dragging;
    private boolean hovered;
    private boolean stretching;
    private GuiUtil.Edge currentEdge;
    private float draggingX;
    private float draggingY;
    private float stretchingWidth;
    private float stretchingHeight;
    private float stretchingX;
    private float stretchingY;
    private float stretchingX2;
    private float stretchingY2;
    private float scrollOffset;
    private float elementOffset;
    private final Set<HudElementButton> elementButtons;
    
    public HudPanel() {
        super("HudPanel", 200.0f, 200.0f, 100.0f, 300.0f);
        this.elementOffset = 20.0f;
        this.elementButtons = new HashSet<HudElementButton>();
        for (final HudElement element : Managers.ELEMENTS.getRegistered()) {
            this.elementButtons.add(new HudElementButton(element));
        }
    }
    
    public void draw(final int mouseX, final int mouseY, final float partialTicks) {
        this.hovered = GuiUtil.isHovered(this, mouseX, mouseY);
        if (this.dragging) {
            this.setX(mouseX - this.draggingX);
            this.setY(mouseY - this.draggingY);
        }
        if (this.stretching && this.currentEdge != null) {
            switch (this.currentEdge) {
                case BOTTOM: {
                    this.setHeight(this.stretchingHeight + (mouseY - this.stretchingY));
                    break;
                }
                case LEFT: {
                    this.setX(this.stretchingX + (mouseX - this.stretchingX));
                    this.setWidth(this.stretchingX2 - this.getX());
                    break;
                }
                case RIGHT: {
                    this.setWidth(this.stretchingWidth + (mouseX - this.stretchingX));
                    break;
                }
                case BOTTOM_LEFT: {
                    this.setHeight(this.stretchingHeight + (mouseY - this.stretchingY));
                    this.setX(this.stretchingX + (mouseX - this.stretchingX));
                    this.setWidth(this.stretchingX2 - this.getX());
                    break;
                }
                case BOTTOM_RIGHT: {
                    this.setHeight(this.stretchingHeight + (mouseY - this.stretchingY));
                    this.setWidth(this.stretchingWidth + (mouseX - this.stretchingX));
                    break;
                }
            }
        }
        if (this.getX() <= 0.0f) {
            this.setX(0.0f);
        }
        if (this.getWidth() <= 100.0f) {
            this.setWidth(100.0f);
        }
        if (this.getHeight() <= 200.0f) {
            this.setHeight(200.0f);
        }
        if (this.getY() <= 0.0f) {
            this.setY(0.0f);
        }
        GL11.glPushMatrix();
        GL11.glPushAttrib(1048575);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Render2DUtil.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), -1845493760);
        Render2DUtil.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + Managers.TEXT.getStringHeight(1.0f) + 10.0f, HudPanel.CLICK_GUI.get().color.getValue().getRGB());
        Managers.TEXT.drawStringScaled("Hud Elements", this.getX() + this.getWidth() / 2.0f - Managers.TEXT.getStringWidthScaled("Hud Elements", 1.0f) / 2.0f, this.getY() + 5.0f, Color.WHITE.getRGB(), true, 1.0f);
        float yOffset = 0.0f;
        RenderUtil.scissor(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight());
        GL11.glEnable(3089);
        for (final HudElementButton button : this.elementButtons) {
            button.setX(this.getX());
            button.setWidth(this.getWidth());
            button.setY(this.getY() + Managers.TEXT.getStringHeight() + 12.0f + yOffset);
            button.draw(mouseX, mouseY, partialTicks);
            yOffset += button.getHeight() + 1.0f;
        }
        GL11.glDisable(3089);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        this.currentEdge = GuiUtil.getHoveredEdge(this, mouseX, mouseY, 5);
        if (GuiUtil.isHovered(this, mouseX, mouseY)) {
            if (this.currentEdge != null) {
                this.stretching = true;
                this.dragging = false;
                this.stretchingWidth = this.getWidth();
                this.stretchingHeight = this.getHeight();
                this.stretchingX = (float)mouseX;
                this.stretchingY = (float)mouseY;
                this.stretchingX2 = this.getX() + this.getWidth();
                this.stretchingY2 = this.getY() + this.getHeight();
            }
            else if (GuiUtil.isHovered(this.getX(), this.getY(), this.getWidth(), 20.0f, (float)mouseX, (float)mouseY)) {
                this.dragging = true;
                this.stretching = false;
                this.draggingX = mouseX - this.getX();
                this.draggingY = mouseY - this.getY();
            }
        }
        for (final HudElementButton button : this.elementButtons) {
            button.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        this.dragging = false;
        this.stretching = false;
        this.currentEdge = null;
        for (final HudElementButton button : this.elementButtons) {
            button.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }
    
    public void keyPressed(final char eventChar, final int key) {
        for (final HudElementButton button : this.elementButtons) {
            button.keyPressed(eventChar, key);
        }
    }
    
    public void mouseScrolled() {
    }
    
    public Set<HudElementButton> getElementButtons() {
        return this.elementButtons;
    }
    
    static {
        CLICK_GUI = Caches.getModule(ClickGui.class);
    }
}
