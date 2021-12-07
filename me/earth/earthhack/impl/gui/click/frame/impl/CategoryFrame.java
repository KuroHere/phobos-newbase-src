//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.click.frame.impl;

import me.earth.earthhack.impl.gui.click.frame.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.clickgui.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.api.module.*;
import java.util.function.*;
import java.util.*;
import net.minecraft.client.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.impl.util.render.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import me.earth.earthhack.impl.gui.click.component.*;
import me.earth.earthhack.impl.gui.visibility.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.gui.click.component.impl.*;
import me.earth.earthhack.impl.modules.*;

public class CategoryFrame extends Frame
{
    private final Category moduleCategory;
    private static final ModuleCache<ClickGui> CLICK_GUI;
    private static final ResourceLocation LEFT_EAR;
    private static final ResourceLocation RIGH_EAR;
    
    public CategoryFrame(final Category moduleCategory, final float posX, final float posY, final float width, final float height) {
        super(moduleCategory.name(), posX, posY, width, height);
        this.moduleCategory = moduleCategory;
        this.setExtended(true);
    }
    
    @Override
    public void init() {
        this.getComponents().clear();
        float offsetY = this.getHeight() + 1.0f;
        final List<Module> moduleList = Managers.MODULES.getModulesFromCategory(this.getModuleCategory());
        moduleList.sort(Comparator.comparing((Function<? super Module, ? extends Comparable>)Module::getName));
        for (final Module module : moduleList) {
            this.getComponents().add(new ModuleComponent(module, this.getPosX(), this.getPosY(), 0.0f, offsetY, this.getWidth(), 14.0f));
            offsetY += 14.0f;
        }
        super.init();
    }
    
    @Override
    public void moved(final float posX, final float posY) {
        super.moved(posX, posY);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        final float scrollMaxHeight = (float)new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight();
        final Color clr = CategoryFrame.CLICK_GUI.get().color.getValue();
        if (CategoryFrame.CLICK_GUI.get().catEars.getValue()) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(CategoryFrame.LEFT_EAR);
            GlStateManager.color(clr.getRed() / 255.0f, clr.getGreen() / 255.0f, clr.getBlue() / 255.0f, 1.0f);
            Gui.drawScaledCustomSizeModalRect((int)this.getPosX() - 8, (int)this.getPosY() - 8, 0.0f, 0.0f, 20, 20, 20, 20, 20.0f, 20.0f);
            Minecraft.getMinecraft().getTextureManager().bindTexture(CategoryFrame.RIGH_EAR);
            GlStateManager.color(clr.getRed() / 255.0f, clr.getGreen() / 255.0f, clr.getBlue() / 255.0f, 1.0f);
            Gui.drawScaledCustomSizeModalRect((int)(this.getPosX() + this.getWidth()) - 12, (int)this.getPosY() - 8, 0.0f, 0.0f, 20, 20, 20, 20, 20.0f, 20.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
        Render2DUtil.drawRect(this.getPosX(), this.getPosY(), this.getPosX() + this.getWidth(), this.getPosY() + this.getHeight(), CategoryFrame.CLICK_GUI.get().color.getValue().getRGB());
        Render2DUtil.drawBorderedRect(this.getPosX(), this.getPosY(), this.getPosX() + this.getWidth(), this.getPosY() + this.getHeight(), 0.5f, 0, -16777216);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.getLabel(), this.getPosX() + 3.0f, this.getPosY() + this.getHeight() / 2.0f - (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT >> 1), -1);
        if (this.isExtended()) {
            if (RenderUtil.mouseWithinBounds(mouseX, mouseY, this.getPosX(), this.getPosY() + this.getHeight(), this.getWidth(), Math.min(this.getScrollCurrentHeight(), scrollMaxHeight) + 1.0f) && this.getScrollCurrentHeight() > scrollMaxHeight) {
                final float scrollSpeed = Math.min(this.getScrollCurrentHeight(), scrollMaxHeight) / (Minecraft.getDebugFPS() >> 3);
                final int wheel = Mouse.getDWheel();
                if (wheel < 0) {
                    if (this.getScrollY() - scrollSpeed < -(this.getScrollCurrentHeight() - Math.min(this.getScrollCurrentHeight(), scrollMaxHeight))) {
                        this.setScrollY((int)(-(this.getScrollCurrentHeight() - Math.min(this.getScrollCurrentHeight(), scrollMaxHeight))));
                    }
                    else {
                        this.setScrollY((int)(this.getScrollY() - scrollSpeed));
                    }
                }
                else if (wheel > 0) {
                    this.setScrollY((int)(this.getScrollY() + scrollSpeed));
                }
            }
            if (this.getScrollY() > 0) {
                this.setScrollY(0);
            }
            if (this.getScrollCurrentHeight() > scrollMaxHeight) {
                if (this.getScrollY() - 6 < -(this.getScrollCurrentHeight() - scrollMaxHeight)) {
                    this.setScrollY((int)(-(this.getScrollCurrentHeight() - scrollMaxHeight)));
                }
            }
            else if (this.getScrollY() < 0) {
                this.setScrollY(0);
            }
            Render2DUtil.drawRect(this.getPosX(), this.getPosY() + this.getHeight(), this.getPosX() + this.getWidth(), this.getPosY() + this.getHeight() + 1.0f + this.getCurrentHeight(), -1845493760);
            GL11.glPushMatrix();
            GL11.glEnable(3089);
            RenderUtil.scissor(this.getPosX(), this.getPosY() + this.getHeight() + 1.0f, this.getPosX() + this.getWidth(), this.getPosY() + this.getHeight() + scrollMaxHeight + 1.0f);
            this.getComponents().forEach(component -> component.drawScreen(mouseX, mouseY, partialTicks));
            GL11.glDisable(3089);
            GL11.glPopMatrix();
        }
        this.updatePositions();
    }
    
    @Override
    public void keyTyped(final char character, final int keyCode) {
        super.keyTyped(character, keyCode);
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        final float scrollMaxHeight = new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() - this.getHeight();
        if (this.isExtended() && RenderUtil.mouseWithinBounds(mouseX, mouseY, this.getPosX(), this.getPosY() + this.getHeight(), this.getWidth(), Math.min(this.getScrollCurrentHeight(), scrollMaxHeight) + 1.0f)) {
            this.getComponents().forEach(component -> component.mouseClicked(mouseX, mouseY, mouseButton));
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
    }
    
    private void updatePositions() {
        float offsetY = this.getHeight() + 1.0f;
        for (final Component component : this.getComponents()) {
            component.setOffsetY(offsetY);
            component.moved(this.getPosX(), this.getPosY() + this.getScrollY());
            if (component instanceof ModuleComponent && component.isExtended()) {
                for (final Component component2 : ((ModuleComponent)component).getComponents()) {
                    if (component2 instanceof BooleanComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((BooleanComponent)component2).getBooleanSetting())) {
                        offsetY += component2.getHeight();
                    }
                    if (component2 instanceof KeybindComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((KeybindComponent)component2).getBindSetting())) {
                        offsetY += component2.getHeight();
                    }
                    if (component2 instanceof NumberComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((NumberComponent)component2).getNumberSetting())) {
                        offsetY += component2.getHeight();
                    }
                    if (component2 instanceof EnumComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((EnumComponent)component2).getEnumSetting())) {
                        offsetY += component2.getHeight();
                    }
                    if (component2 instanceof ColorComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((ColorComponent)component2).getColorSetting())) {
                        offsetY += component2.getHeight();
                    }
                    if (component2 instanceof StringComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((StringComponent)component2).getStringSetting())) {
                        offsetY += component2.getHeight();
                    }
                }
                offsetY += 3.0f;
            }
            offsetY += component.getHeight();
        }
    }
    
    private float getScrollCurrentHeight() {
        return this.getCurrentHeight() + this.getHeight() + 3.0f;
    }
    
    private float getCurrentHeight() {
        float cHeight = 1.0f;
        for (final Component component : this.getComponents()) {
            if (component instanceof ModuleComponent && component.isExtended()) {
                for (final Component component2 : ((ModuleComponent)component).getComponents()) {
                    if (component2 instanceof BooleanComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((BooleanComponent)component2).getBooleanSetting())) {
                        cHeight += component2.getHeight();
                    }
                    if (component2 instanceof KeybindComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((KeybindComponent)component2).getBindSetting())) {
                        cHeight += component2.getHeight();
                    }
                    if (component2 instanceof NumberComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((NumberComponent)component2).getNumberSetting())) {
                        cHeight += component2.getHeight();
                    }
                    if (component2 instanceof EnumComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((EnumComponent)component2).getEnumSetting())) {
                        cHeight += component2.getHeight();
                    }
                    if (component2 instanceof ColorComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((ColorComponent)component2).getColorSetting())) {
                        cHeight += component2.getHeight();
                    }
                    if (component2 instanceof StringComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((StringComponent)component2).getStringSetting())) {
                        cHeight += component2.getHeight();
                    }
                }
                cHeight += 3.0f;
            }
            cHeight += component.getHeight();
        }
        return cHeight;
    }
    
    public Category getModuleCategory() {
        return this.moduleCategory;
    }
    
    static {
        CLICK_GUI = Caches.getModule(ClickGui.class);
        LEFT_EAR = new ResourceLocation("earthhack:textures/gui/left_ear.png");
        RIGH_EAR = new ResourceLocation("earthhack:textures/gui/right_ear.png");
    }
}
