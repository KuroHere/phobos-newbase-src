//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.click.component.impl;

import me.earth.earthhack.impl.gui.click.component.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.clickgui.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import java.util.*;
import me.earth.earthhack.impl.util.render.*;
import java.awt.*;
import net.minecraft.client.*;
import me.earth.earthhack.impl.gui.visibility.*;
import me.earth.earthhack.impl.modules.*;

public class ModuleComponent extends Component
{
    private static final SettingCache<Boolean, BooleanSetting, ClickGui> WHITE;
    private final Module module;
    private final ArrayList<Component> components;
    
    public ModuleComponent(final Module module, final float posX, final float posY, final float offsetX, final float offsetY, final float width, final float height) {
        super(module.getName(), posX, posY, offsetX, offsetY, width, height);
        this.components = new ArrayList<Component>();
        this.module = module;
    }
    
    @Override
    public void init() {
        this.getComponents().clear();
        float offY = this.getHeight();
        final ModuleData data = this.getModule().getData();
        if (data != null) {
            this.setDescription(data.getDescription());
        }
        if (!this.getModule().getSettings().isEmpty()) {
            for (final Setting<?> setting : this.getModule().getSettings()) {
                final float before = offY;
                if (setting instanceof BooleanSetting && !setting.getName().equalsIgnoreCase("enabled")) {
                    this.getComponents().add(new BooleanComponent((BooleanSetting)setting, this.getFinishedX(), this.getFinishedY(), 0.0f, offY, this.getWidth(), 14.0f));
                    offY += 14.0f;
                }
                if (setting instanceof BindSetting) {
                    this.getComponents().add(new KeybindComponent((BindSetting)setting, this.getFinishedX(), this.getFinishedY(), 0.0f, offY, this.getWidth(), 14.0f));
                    offY += 14.0f;
                }
                if (setting instanceof NumberSetting) {
                    this.getComponents().add(new NumberComponent((NumberSetting)setting, this.getFinishedX(), this.getFinishedY(), 0.0f, offY, this.getWidth(), 14.0f));
                    offY += 14.0f;
                }
                if (setting instanceof EnumSetting) {
                    this.getComponents().add(new EnumComponent<Object>((EnumSetting)setting, this.getFinishedX(), this.getFinishedY(), 0.0f, offY, this.getWidth(), 14.0f));
                    offY += 14.0f;
                }
                if (setting instanceof ColorSetting) {
                    this.getComponents().add(new ColorComponent((ColorSetting)setting, this.getFinishedX(), this.getFinishedY(), 0.0f, offY, this.getWidth(), 14.0f));
                    offY += 14.0f;
                }
                if (setting instanceof StringSetting) {
                    this.getComponents().add(new StringComponent((StringSetting)setting, this.getFinishedX(), this.getFinishedY(), 0.0f, offY, this.getWidth(), 14.0f));
                    offY += 14.0f;
                }
                if (data != null && before != offY) {
                    String desc = data.settingDescriptions().get(setting);
                    if (desc == null) {
                        desc = "A Setting (" + setting.getInitial().getClass().getSimpleName() + ").";
                    }
                    this.getComponents().get(this.getComponents().size() - 1).setDescription(desc);
                }
            }
        }
        this.getComponents().forEach(Component::init);
    }
    
    @Override
    public void moved(final float posX, final float posY) {
        super.moved(posX, posY);
        this.getComponents().forEach(component -> component.moved(this.getFinishedX(), this.getFinishedY()));
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        final boolean hovered = RenderUtil.mouseWithinBounds(mouseX, mouseY, this.getFinishedX(), this.getFinishedY(), this.getWidth(), this.getHeight());
        if (hovered) {
            Render2DUtil.drawRect(this.getFinishedX() + 1.0f, this.getFinishedY() + 0.5f, this.getFinishedX() + this.getWidth() - 1.0f, this.getFinishedY() + this.getHeight() - 0.5f, 1714631475);
        }
        if (this.getModule().isEnabled()) {
            Render2DUtil.drawRect(this.getFinishedX() + 1.0f, this.getFinishedY() + 0.5f, this.getFinishedX() + this.getWidth() - 1.0f, this.getFinishedY() + this.getHeight() - 0.5f, hovered ? Component.getClickGui().get().color.getValue().brighter().getRGB() : Component.getClickGui().get().color.getValue().getRGB());
        }
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.getLabel(), this.getFinishedX() + 4.0f, this.getFinishedY() + this.getHeight() / 2.0f - (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT >> 1), this.getModule().isEnabled() ? -1 : -5592406);
        if (!this.getComponents().isEmpty()) {
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.isExtended() ? Component.getClickGui().get().close.getValue() : Component.getClickGui().get().open.getValue(), this.getFinishedX() + this.getWidth() - 4.0f - Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.isExtended() ? Component.getClickGui().get().close.getValue() : Component.getClickGui().get().open.getValue()), this.getFinishedY() + this.getHeight() / 2.0f - (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT >> 1), this.getModule().isEnabled() ? -1 : -5592406);
        }
        if (this.isExtended()) {
            for (final Component component : this.getComponents()) {
                if (component instanceof BooleanComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((BooleanComponent)component).getBooleanSetting())) {
                    component.drawScreen(mouseX, mouseY, partialTicks);
                }
                if (component instanceof KeybindComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((KeybindComponent)component).getBindSetting())) {
                    component.drawScreen(mouseX, mouseY, partialTicks);
                }
                if (component instanceof NumberComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((NumberComponent)component).getNumberSetting())) {
                    component.drawScreen(mouseX, mouseY, partialTicks);
                }
                if (component instanceof EnumComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((EnumComponent)component).getEnumSetting())) {
                    component.drawScreen(mouseX, mouseY, partialTicks);
                }
                if (component instanceof ColorComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((ColorComponent)component).getColorSetting())) {
                    component.drawScreen(mouseX, mouseY, partialTicks);
                }
                if (component instanceof StringComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((StringComponent)component).getStringSetting())) {
                    component.drawScreen(mouseX, mouseY, partialTicks);
                }
            }
            if (this.getModule().isEnabled()) {
                Render2DUtil.drawRect(this.getFinishedX() + 1.0f, this.getFinishedY() + this.getHeight() - 0.5f, this.getFinishedX() + 3.0f, this.getFinishedY() + this.getHeight() + this.getComponentsSize(), hovered ? Component.getClickGui().get().color.getValue().brighter().getRGB() : Component.getClickGui().get().color.getValue().getRGB());
                Render2DUtil.drawRect(this.getFinishedX() + 1.0f, this.getFinishedY() + this.getHeight() + this.getComponentsSize(), this.getFinishedX() + this.getWidth() - 1.0f, this.getFinishedY() + this.getHeight() + this.getComponentsSize() + 2.0f, hovered ? Component.getClickGui().get().color.getValue().brighter().getRGB() : Component.getClickGui().get().color.getValue().getRGB());
                Render2DUtil.drawRect(this.getFinishedX() + this.getWidth() - 3.0f, this.getFinishedY() + this.getHeight() - 0.5f, this.getFinishedX() + this.getWidth() - 1.0f, this.getFinishedY() + this.getHeight() + this.getComponentsSize(), hovered ? Component.getClickGui().get().color.getValue().brighter().getRGB() : Component.getClickGui().get().color.getValue().getRGB());
            }
            Render2DUtil.drawBorderedRect(this.getFinishedX() + 3.0f, this.getFinishedY() + this.getHeight() - 0.5f, this.getFinishedX() + this.getWidth() - 3.0f, this.getFinishedY() + this.getHeight() + this.getComponentsSize() + 0.5f, 0.5f, 0, ((boolean)ModuleComponent.WHITE.getValue()) ? -1 : -16777216);
        }
        Render2DUtil.drawBorderedRect(this.getFinishedX() + 1.0f, this.getFinishedY() + 0.5f, this.getFinishedX() + 1.0f + this.getWidth() - 2.0f, this.getFinishedY() - 0.5f + this.getHeight() + (this.isExtended() ? (this.getComponentsSize() + 3.0f) : 0.0f), 0.5f, 0, -16777216);
        this.updatePositions();
    }
    
    @Override
    public void keyTyped(final char character, final int keyCode) {
        super.keyTyped(character, keyCode);
        if (this.isExtended()) {
            for (final Component component : this.getComponents()) {
                if (component instanceof BooleanComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((BooleanComponent)component).getBooleanSetting())) {
                    component.keyTyped(character, keyCode);
                }
                if (component instanceof KeybindComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((KeybindComponent)component).getBindSetting())) {
                    component.keyTyped(character, keyCode);
                }
                if (component instanceof NumberComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((NumberComponent)component).getNumberSetting())) {
                    component.keyTyped(character, keyCode);
                }
                if (component instanceof EnumComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((EnumComponent)component).getEnumSetting())) {
                    component.keyTyped(character, keyCode);
                }
                if (component instanceof ColorComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((ColorComponent)component).getColorSetting())) {
                    component.keyTyped(character, keyCode);
                }
                if (component instanceof StringComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((StringComponent)component).getStringSetting())) {
                    component.keyTyped(character, keyCode);
                }
            }
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        final boolean hovered = RenderUtil.mouseWithinBounds(mouseX, mouseY, this.getFinishedX(), this.getFinishedY(), this.getWidth(), this.getHeight());
        if (hovered) {
            switch (mouseButton) {
                case 0: {
                    this.getModule().toggle();
                    break;
                }
                case 1: {
                    if (!this.getComponents().isEmpty()) {
                        this.setExtended(!this.isExtended());
                        break;
                    }
                    break;
                }
            }
        }
        if (this.isExtended()) {
            for (final Component component : this.getComponents()) {
                if (component instanceof BooleanComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((BooleanComponent)component).getBooleanSetting())) {
                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
                if (component instanceof KeybindComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((KeybindComponent)component).getBindSetting())) {
                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
                if (component instanceof NumberComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((NumberComponent)component).getNumberSetting())) {
                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
                if (component instanceof EnumComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((EnumComponent)component).getEnumSetting())) {
                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
                if (component instanceof ColorComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((ColorComponent)component).getColorSetting())) {
                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
                if (component instanceof StringComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((StringComponent)component).getStringSetting())) {
                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        if (this.isExtended()) {
            for (final Component component : this.getComponents()) {
                if (component instanceof BooleanComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((BooleanComponent)component).getBooleanSetting())) {
                    component.mouseReleased(mouseX, mouseY, mouseButton);
                }
                if (component instanceof KeybindComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((KeybindComponent)component).getBindSetting())) {
                    component.mouseReleased(mouseX, mouseY, mouseButton);
                }
                if (component instanceof NumberComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((NumberComponent)component).getNumberSetting())) {
                    component.mouseReleased(mouseX, mouseY, mouseButton);
                }
                if (component instanceof EnumComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((EnumComponent)component).getEnumSetting())) {
                    component.mouseReleased(mouseX, mouseY, mouseButton);
                }
                if (component instanceof ColorComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((ColorComponent)component).getColorSetting())) {
                    component.mouseReleased(mouseX, mouseY, mouseButton);
                }
                if (component instanceof StringComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((StringComponent)component).getStringSetting())) {
                    component.mouseReleased(mouseX, mouseY, mouseButton);
                }
            }
        }
    }
    
    private float getComponentsSize() {
        float size = 0.0f;
        for (final Component component : this.getComponents()) {
            if (component instanceof BooleanComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((BooleanComponent)component).getBooleanSetting())) {
                size += component.getHeight();
            }
            if (component instanceof KeybindComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((KeybindComponent)component).getBindSetting())) {
                size += component.getHeight();
            }
            if (component instanceof NumberComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((NumberComponent)component).getNumberSetting())) {
                size += component.getHeight();
            }
            if (component instanceof EnumComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((EnumComponent)component).getEnumSetting())) {
                size += component.getHeight();
            }
            if (component instanceof ColorComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((ColorComponent)component).getColorSetting())) {
                size += component.getHeight();
            }
            if (component instanceof StringComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((StringComponent)component).getStringSetting())) {
                size += component.getHeight();
            }
        }
        return size;
    }
    
    private void updatePositions() {
        float offsetY = this.getHeight();
        for (final Component component : this.getComponents()) {
            if (component instanceof BooleanComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((BooleanComponent)component).getBooleanSetting())) {
                component.setOffsetY(offsetY);
                component.moved(this.getPosX(), this.getPosY());
                offsetY += component.getHeight();
            }
            if (component instanceof KeybindComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((KeybindComponent)component).getBindSetting())) {
                component.setOffsetY(offsetY);
                component.moved(this.getPosX(), this.getPosY());
                offsetY += component.getHeight();
            }
            if (component instanceof NumberComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((NumberComponent)component).getNumberSetting())) {
                component.setOffsetY(offsetY);
                component.moved(this.getPosX(), this.getPosY());
                offsetY += component.getHeight();
            }
            if (component instanceof EnumComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((EnumComponent)component).getEnumSetting())) {
                component.setOffsetY(offsetY);
                component.moved(this.getPosX(), this.getPosY());
                offsetY += component.getHeight();
            }
            if (component instanceof ColorComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((ColorComponent)component).getColorSetting())) {
                component.setOffsetY(offsetY);
                component.moved(this.getPosX(), this.getPosY());
                offsetY += component.getHeight();
            }
            if (component instanceof StringComponent && Visibilities.VISIBILITY_MANAGER.isVisible(((StringComponent)component).getStringSetting())) {
                component.setOffsetY(offsetY);
                component.moved(this.getPosX(), this.getPosY());
                offsetY += component.getHeight();
            }
        }
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public ArrayList<Component> getComponents() {
        return this.components;
    }
    
    static {
        WHITE = Caches.getSetting(ClickGui.class, BooleanSetting.class, "White-Settings", true);
    }
}
