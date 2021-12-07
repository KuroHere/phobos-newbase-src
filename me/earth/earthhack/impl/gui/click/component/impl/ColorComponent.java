//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.click.component.impl;

import me.earth.earthhack.impl.gui.click.component.*;
import me.earth.earthhack.api.setting.settings.*;
import net.minecraft.client.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.modules.client.clickgui.*;
import me.earth.earthhack.impl.util.render.*;
import net.minecraft.util.math.*;
import com.mojang.realmsclient.gui.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.api.setting.event.*;
import java.awt.*;
import java.io.*;
import java.awt.datatransfer.*;

public class ColorComponent extends Component
{
    private final ColorSetting colorSetting;
    private boolean colorExtended;
    private boolean colorSelectorDragging;
    private boolean alphaSelectorDragging;
    private boolean hueSelectorDragging;
    private float hue;
    private float saturation;
    private float brightness;
    private float alpha;
    private boolean slidingSpeed;
    private boolean slidingSaturation;
    private boolean slidingBrightness;
    
    public ColorComponent(final ColorSetting colorSetting, final float posX, final float posY, final float offsetX, final float offsetY, final float width, final float height) {
        super(colorSetting.getName(), posX, posY, offsetX, offsetY, width, height);
        this.colorSetting = colorSetting;
        final float[] hsb = Color.RGBtoHSB(this.getColorSetting().getRed(), this.getColorSetting().getGreen(), this.getColorSetting().getBlue(), null);
        this.hue = hsb[0];
        this.saturation = hsb[1];
        this.brightness = hsb[2];
        this.alpha = this.getColorSetting().getAlpha() / 255.0f;
    }
    
    @Override
    public void moved(final float posX, final float posY) {
        super.moved(posX, posY);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.getLabel(), this.getFinishedX() + 5.0f, this.getFinishedY() + 7.0f - (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT >> 1), -1);
        Render2DUtil.drawBorderedRect(this.getFinishedX() + this.getWidth() - 20.0f, this.getFinishedY() + 4.0f, this.getFinishedX() + this.getWidth() - 5.0f, this.getFinishedY() + 11.0f, 0.5f, this.getColorSetting().getRGB(), -16777216);
        this.setHeight(this.isColorExtended() ? ((float)(((this.getColorSetting() == Managers.COLOR.getColorSetting()) ? 134 : 154) + (this.getColorSetting().isRainbow() ? 56 : 0))) : 14.0f);
        if (this.isColorExtended()) {
            final float expandedX = this.getFinishedX() + 1.0f;
            final float expandedY = this.getFinishedY() + 14.0f;
            final float colorPickerLeft = expandedX + 6.0f;
            final float colorPickerTop = expandedY + 1.0f;
            final float colorPickerRight = colorPickerLeft + (this.getWidth() - 20.0f);
            final float colorPickerBottom = colorPickerTop + (this.getHeight() - (((this.getColorSetting() == Managers.COLOR.getColorSetting()) ? 52 : 68) + (this.getColorSetting().isRainbow() ? 56 : 0)));
            final int selectorWhiteOverlayColor = new Color(255, 255, 255, 180).getRGB();
            final int colorMouseX = (int)MathUtil.clamp((float)mouseX, colorPickerLeft, colorPickerRight);
            final int colorMouseY = (int)MathUtil.clamp((float)mouseY, colorPickerTop, colorPickerBottom);
            Render2DUtil.drawRect(colorPickerLeft - 0.5f, colorPickerTop - 0.5f, colorPickerRight + 0.5f, colorPickerBottom + 0.5f, -16777216);
            this.drawColorPickerRect(colorPickerLeft, colorPickerTop, colorPickerRight, colorPickerBottom);
            float colorSelectorX = this.saturation * (colorPickerRight - colorPickerLeft);
            float colorSelectorY = (1.0f - this.brightness) * (colorPickerBottom - colorPickerTop);
            if (this.colorSelectorDragging) {
                final float wWidth = colorPickerRight - colorPickerLeft;
                final float xDif = colorMouseX - colorPickerLeft;
                this.saturation = xDif / wWidth;
                colorSelectorX = xDif;
                final float hHeight = colorPickerBottom - colorPickerTop;
                final float yDif = colorMouseY - colorPickerTop;
                this.brightness = 1.0f - yDif / hHeight;
                colorSelectorY = yDif;
                this.updateColor(Color.HSBtoRGB(this.hue, this.saturation, this.brightness));
            }
            final float csLeft = colorPickerLeft + colorSelectorX - 0.5f;
            final float csTop = colorPickerTop + colorSelectorY - 0.5f;
            final float csRight = colorPickerLeft + colorSelectorX + 0.5f;
            final float csBottom = colorPickerTop + colorSelectorY + 0.5f;
            Render2DUtil.drawRect(csLeft - 1.0f, csTop - 1.0f, csLeft, csBottom + 1.0f, -16777216);
            Render2DUtil.drawRect(csRight, csTop - 1.0f, csRight + 1.0f, csBottom + 1.0f, -16777216);
            Render2DUtil.drawRect(csLeft, csTop - 1.0f, csRight, csTop, -16777216);
            Render2DUtil.drawRect(csLeft, csBottom, csRight, csBottom + 1.0f, -16777216);
            Render2DUtil.drawRect(csLeft, csTop, csRight, csBottom, selectorWhiteOverlayColor);
            final float hueSliderLeft = colorPickerRight + 2.0f;
            final float hueSliderRight = hueSliderLeft + 4.0f;
            final int hueMouseY = (int)MathUtil.clamp((float)mouseY, colorPickerTop, colorPickerBottom);
            final float hueSliderYDif = colorPickerBottom - colorPickerTop;
            float hueSelectorY = (1.0f - this.hue) * hueSliderYDif;
            if (this.hueSelectorDragging) {
                final float yDif2 = hueMouseY - colorPickerTop;
                this.hue = 1.0f - yDif2 / hueSliderYDif;
                hueSelectorY = yDif2;
                this.updateColor(Color.HSBtoRGB(this.hue, this.saturation, this.brightness));
            }
            Render2DUtil.drawRect(hueSliderLeft - 0.5f, colorPickerTop - 0.5f, hueSliderRight + 0.5f, colorPickerBottom + 0.5f, -16777216);
            final float inc = 0.2f;
            final float times = 5.0f;
            final float sHeight = colorPickerBottom - colorPickerTop;
            final float size = sHeight / 5.0f;
            float sY = colorPickerTop;
            for (int i = 0; i < 5.0f; ++i) {
                final boolean last = i == 4.0f;
                Render2DUtil.drawGradientRect(hueSliderLeft, sY, hueSliderRight, sY + size, false, Color.HSBtoRGB(1.0f - 0.2f * i, 1.0f, 1.0f), Color.HSBtoRGB(1.0f - 0.2f * (i + 1), 1.0f, 1.0f));
                if (!last) {
                    sY += size;
                }
            }
            final float hsTop = colorPickerTop + hueSelectorY - 0.5f;
            final float hsBottom = colorPickerTop + hueSelectorY + 0.5f;
            Render2DUtil.drawRect(hueSliderLeft - 1.0f, hsTop - 1.0f, hueSliderLeft, hsBottom + 1.0f, -16777216);
            Render2DUtil.drawRect(hueSliderRight, hsTop - 1.0f, hueSliderRight + 1.0f, hsBottom + 1.0f, -16777216);
            Render2DUtil.drawRect(hueSliderLeft, hsTop - 1.0f, hueSliderRight, hsTop, -16777216);
            Render2DUtil.drawRect(hueSliderLeft, hsBottom, hueSliderRight, hsBottom + 1.0f, -16777216);
            Render2DUtil.drawRect(hueSliderLeft, hsTop, hueSliderRight, hsBottom, selectorWhiteOverlayColor);
            final float alphaSliderTop = colorPickerBottom + 2.0f;
            final float alphaSliderBottom = alphaSliderTop + 4.0f;
            final int color = Color.HSBtoRGB(this.hue, this.saturation, this.brightness);
            final int r = color >> 16 & 0xFF;
            final int g = color >> 8 & 0xFF;
            final int b = color & 0xFF;
            final float hsHeight = colorPickerRight - colorPickerLeft;
            float alphaSelectorX = this.alpha * hsHeight;
            if (this.alphaSelectorDragging) {
                final float xDif2 = colorMouseX - colorPickerLeft;
                this.alpha = xDif2 / hsHeight;
                alphaSelectorX = xDif2;
                this.updateColor(new Color(r, g, b, (int)(this.alpha * 255.0f)).getRGB());
            }
            Render2DUtil.drawRect(colorPickerLeft - 0.5f, alphaSliderTop - 0.5f, colorPickerRight + 0.5f, alphaSliderBottom + 0.5f, -16777216);
            Render2DUtil.drawCheckeredBackground(colorPickerLeft, alphaSliderTop, colorPickerRight, alphaSliderBottom);
            Render2DUtil.drawGradientRect(colorPickerLeft, alphaSliderTop, colorPickerRight, alphaSliderBottom, true, new Color(r, g, b, 0).getRGB(), new Color(r, g, b, 255).getRGB());
            final float asLeft = colorPickerLeft + alphaSelectorX - 0.5f;
            final float asRight = colorPickerLeft + alphaSelectorX + 0.5f;
            Render2DUtil.drawRect(asLeft - 1.0f, alphaSliderTop, asRight + 1.0f, alphaSliderBottom, -16777216);
            Render2DUtil.drawRect(asLeft, alphaSliderTop, asRight, alphaSliderBottom, selectorWhiteOverlayColor);
            Render2DUtil.drawGradientRect(colorPickerLeft, alphaSliderBottom + 2.0f, colorPickerLeft + (this.getWidth() - 16.0f) / 2.0f, alphaSliderBottom + 14.0f, false, Component.getClickGui().get().color.getValue().getRGB(), Component.getClickGui().get().color.getValue().darker().darker().getRGB());
            Render2DUtil.drawBorderedRect(colorPickerLeft, alphaSliderBottom + 2.0f, colorPickerLeft + (this.getWidth() - 16.0f) / 2.0f, alphaSliderBottom + 14.0f, 0.5f, 0, -16777216);
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Copy", colorPickerLeft + (this.getWidth() - 16.0f) / 2.0f / 2.0f - (Minecraft.getMinecraft().fontRendererObj.getStringWidth("Copy") >> 1), alphaSliderBottom + 8.0f - (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT >> 1), -1);
            Render2DUtil.drawGradientRect(hueSliderRight - (this.getWidth() - 16.0f) / 2.0f, alphaSliderBottom + 2.0f, hueSliderRight, alphaSliderBottom + 14.0f, false, Component.getClickGui().get().color.getValue().getRGB(), Component.getClickGui().get().color.getValue().darker().darker().getRGB());
            Render2DUtil.drawBorderedRect(hueSliderRight - (this.getWidth() - 16.0f) / 2.0f, alphaSliderBottom + 2.0f, hueSliderRight, alphaSliderBottom + 14.0f, 0.5f, 0, -16777216);
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Paste", hueSliderRight - (this.getWidth() - 16.0f) / 4.0f - (Minecraft.getMinecraft().fontRendererObj.getStringWidth("Paste") >> 1), alphaSliderBottom + 8.0f - (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT >> 1), -1);
            if (this.getColorSetting() != Managers.COLOR.getColorSetting()) {
                final boolean hoveredSync = RenderUtil.mouseWithinBounds(mouseX, mouseY, hueSliderRight - 12.0f, alphaSliderBottom + 16.0f, 12.0, 12.0);
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Sync", colorPickerLeft, alphaSliderBottom + 17.0f, this.getColorSetting().isSync() ? -1 : -5592406);
                Render2DUtil.drawBorderedRect(hueSliderRight - 12.0f, alphaSliderBottom + 16.0f, hueSliderRight, alphaSliderBottom + 28.0f, 0.5f, this.getColorSetting().isSync() ? (hoveredSync ? Component.getClickGui().get().color.getValue().brighter().getRGB() : Component.getClickGui().get().color.getValue().getRGB()) : (hoveredSync ? 1714631475 : 0), -16777216);
                if (this.getColorSetting().isSync()) {
                    Render2DUtil.drawCheckMark(hueSliderRight - 6.0f, alphaSliderBottom + 16.0f, 10, -1);
                }
            }
            final boolean hoveredRainbow = RenderUtil.mouseWithinBounds(mouseX, mouseY, hueSliderRight - 12.0f, alphaSliderBottom + ((this.getColorSetting() == Managers.COLOR.getColorSetting()) ? 16 : 30), 12.0, 12.0);
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Rainbow", colorPickerLeft, alphaSliderBottom + ((this.getColorSetting() == Managers.COLOR.getColorSetting()) ? 17 : 31), this.getColorSetting().isRainbow() ? -1 : -5592406);
            Render2DUtil.drawBorderedRect(hueSliderRight - 12.0f, alphaSliderBottom + ((this.getColorSetting() == Managers.COLOR.getColorSetting()) ? 16 : 30), hueSliderRight, alphaSliderBottom + ((this.getColorSetting() == Managers.COLOR.getColorSetting()) ? 28 : 42), 0.5f, this.getColorSetting().isRainbow() ? (hoveredRainbow ? Component.getClickGui().get().color.getValue().brighter().getRGB() : Component.getClickGui().get().color.getValue().getRGB()) : (hoveredRainbow ? 1714631475 : 0), -16777216);
            if (this.getColorSetting().isRainbow()) {
                Render2DUtil.drawCheckMark(hueSliderRight - 6.0f, alphaSliderBottom + ((this.getColorSetting() == Managers.COLOR.getColorSetting()) ? 16 : 30), 10, -1);
                final float smallWidth = hueSliderRight - colorPickerLeft;
                final float lengthSpeed = (float)MathHelper.floor(this.getColorSetting().getRainbowSpeed() / 200.0f * smallWidth);
                final float lengthSaturation = (float)MathHelper.floor(this.getColorSetting().getRainbowSaturation() / 100.0f * smallWidth);
                final float lengthBrightness = (float)MathHelper.floor(this.getColorSetting().getRainbowBrightness() / 100.0f * smallWidth);
                final float offset = alphaSliderBottom + ((this.getColorSetting() == Managers.COLOR.getColorSetting()) ? 17 : 31);
                final boolean hoveredStatic = RenderUtil.mouseWithinBounds(mouseX, mouseY, hueSliderRight - 12.0f, alphaSliderBottom + ((this.getColorSetting() == Managers.COLOR.getColorSetting()) ? 16 : 30) + 14.0f, 12.0, 12.0);
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Static", colorPickerLeft, alphaSliderBottom + ((this.getColorSetting() == Managers.COLOR.getColorSetting()) ? 17 : 31) + 14.0f, this.getColorSetting().isStaticRainbow() ? -1 : -5592406);
                Render2DUtil.drawBorderedRect(hueSliderRight - 12.0f, alphaSliderBottom + ((this.getColorSetting() == Managers.COLOR.getColorSetting()) ? 16 : 30) + 14.0f, hueSliderRight, alphaSliderBottom + ((this.getColorSetting() == Managers.COLOR.getColorSetting()) ? 28 : 42) + 14.0f, 0.5f, this.getColorSetting().isStaticRainbow() ? (hoveredStatic ? Component.getClickGui().get().color.getValue().brighter().getRGB() : Component.getClickGui().get().color.getValue().getRGB()) : (hoveredStatic ? 1714631475 : 0), -16777216);
                if (this.getColorSetting().isStaticRainbow()) {
                    Render2DUtil.drawCheckMark(hueSliderRight - 6.0f, alphaSliderBottom + ((this.getColorSetting() == Managers.COLOR.getColorSetting()) ? 16 : 30) + 14.0f, 10, -1);
                }
                final boolean hoveredSpeed = RenderUtil.mouseWithinBounds(mouseX, mouseY, colorPickerLeft, offset + 28.0f, smallWidth, 12.0);
                final boolean hoveredSaturation = RenderUtil.mouseWithinBounds(mouseX, mouseY, colorPickerLeft, offset + 42.0f, smallWidth, 12.0);
                final boolean hoveredBrightness = RenderUtil.mouseWithinBounds(mouseX, mouseY, colorPickerLeft, offset + 56.0f, smallWidth, 12.0);
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Speed: " + ChatFormatting.GRAY + this.getColorSetting().getRainbowSpeed(), colorPickerLeft, offset + 28.0f, -1);
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Saturation: " + ChatFormatting.GRAY + this.getColorSetting().getRainbowSaturation(), colorPickerLeft, offset + 42.0f, -1);
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Brightness: " + ChatFormatting.GRAY + this.getColorSetting().getRainbowBrightness(), colorPickerLeft, offset + 56.0f, -1);
                Render2DUtil.drawBorderedRect(colorPickerLeft, offset + 36.5f, colorPickerLeft + lengthSpeed, offset + 38.5f, 0.5f, hoveredSpeed ? Component.getClickGui().get().color.getValue().brighter().getRGB() : Component.getClickGui().get().color.getValue().getRGB(), -16777216);
                if (this.slidingSpeed) {
                    final float speedValue = (mouseX - colorPickerLeft) * 200.0f / smallWidth;
                    this.getColorSetting().setRainbowSpeed(MathUtil.round(speedValue, 2, 0.0f, 200.0f));
                }
                Render2DUtil.drawBorderedRect(colorPickerLeft, offset + 50.5f, colorPickerLeft + lengthSaturation, offset + 52.5f, 0.5f, hoveredSaturation ? Component.getClickGui().get().color.getValue().brighter().getRGB() : Component.getClickGui().get().color.getValue().getRGB(), -16777216);
                if (this.slidingSaturation) {
                    final float saturationValue = (mouseX - colorPickerLeft) * 100.0f / smallWidth;
                    this.getColorSetting().setRainbowSaturation(MathUtil.round(saturationValue, 2, 0.0f, 100.0f));
                }
                Render2DUtil.drawBorderedRect(colorPickerLeft, offset + 64.5f, colorPickerLeft + lengthBrightness, offset + 66.5f, 0.5f, hoveredBrightness ? Component.getClickGui().get().color.getValue().brighter().getRGB() : Component.getClickGui().get().color.getValue().getRGB(), -16777216);
                if (this.slidingBrightness) {
                    final float brightnessValue = (mouseX - colorPickerLeft) * 100.0f / smallWidth;
                    this.getColorSetting().setRainbowBrightness(MathUtil.round(brightnessValue, 2, 0.0f, 100.0f));
                }
            }
        }
        if (this.getColorSetting().isSync() || this.getColorSetting().isRainbow()) {
            final float[] hsb = Color.RGBtoHSB(this.getColorSetting().getRed(), this.getColorSetting().getGreen(), this.getColorSetting().getBlue(), null);
            if (this.hue != hsb[0] || this.saturation != hsb[1] || this.brightness != hsb[2] || this.alpha != this.getColorSetting().getAlpha() / 255.0f) {
                this.hue = hsb[0];
                this.saturation = hsb[1];
                this.brightness = hsb[2];
                this.alpha = this.getColorSetting().getAlpha() / 255.0f;
            }
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            final boolean hovered = RenderUtil.mouseWithinBounds(mouseX, mouseY, this.getFinishedX() + this.getWidth() - 20.0f, this.getFinishedY() + 4.0f, 15.0, 7.0);
            if (this.isColorExtended()) {
                final float expandedX = this.getFinishedX() + 1.0f;
                final float expandedY = this.getFinishedY() + 14.0f;
                final float colorPickerLeft = expandedX + 6.0f;
                final float colorPickerTop = expandedY + 1.0f;
                final float colorPickerRight = colorPickerLeft + (this.getWidth() - 20.0f);
                final float colorPickerBottom = colorPickerTop + (this.getHeight() - (((this.getColorSetting() == Managers.COLOR.getColorSetting()) ? 52 : 68) + (this.getColorSetting().isRainbow() ? 56 : 0)));
                final float alphaSliderTop = colorPickerBottom + 2.0f;
                final float alphaSliderBottom = alphaSliderTop + 4.0f;
                final float hueSliderLeft = colorPickerRight + 2.0f;
                final float hueSliderRight = hueSliderLeft + 4.0f;
                final boolean hoveredCopy = RenderUtil.mouseWithinBounds(mouseX, mouseY, colorPickerLeft, alphaSliderBottom + 2.0f, (this.getWidth() - 16.0f) / 2.0f, 12.0);
                final boolean hoveredPaste = RenderUtil.mouseWithinBounds(mouseX, mouseY, hueSliderRight - (this.getWidth() - 16.0f) / 2.0f, alphaSliderBottom + 2.0f, (this.getWidth() - 16.0f) / 2.0f, 12.0);
                final boolean hoveredSync = RenderUtil.mouseWithinBounds(mouseX, mouseY, hueSliderRight - 12.0f, alphaSliderBottom + 16.0f, 12.0, 12.0);
                final boolean hoveredRainbow = RenderUtil.mouseWithinBounds(mouseX, mouseY, hueSliderRight - 12.0f, alphaSliderBottom + ((this.getColorSetting() == Managers.COLOR.getColorSetting()) ? 16 : 30), 12.0, 12.0);
                final float smallWidth = hueSliderRight - colorPickerLeft;
                final float offset = alphaSliderBottom + ((this.getColorSetting() == Managers.COLOR.getColorSetting()) ? 17 : 31);
                final boolean hoveredStatic = RenderUtil.mouseWithinBounds(mouseX, mouseY, hueSliderRight - 12.0f, alphaSliderBottom + ((this.getColorSetting() == Managers.COLOR.getColorSetting()) ? 16 : 30) + 14.0f, 12.0, 12.0);
                final boolean hoveredSpeed = RenderUtil.mouseWithinBounds(mouseX, mouseY, colorPickerLeft, offset + 28.0f, smallWidth, 12.0);
                final boolean hoveredSaturation = RenderUtil.mouseWithinBounds(mouseX, mouseY, colorPickerLeft, offset + 42.0f, smallWidth, 12.0);
                final boolean hoveredBrightness = RenderUtil.mouseWithinBounds(mouseX, mouseY, colorPickerLeft, offset + 56.0f, smallWidth, 12.0);
                if (hoveredRainbow) {
                    this.getColorSetting().setRainbow(!this.getColorSetting().isRainbow());
                }
                if (this.getColorSetting() != Managers.COLOR.getColorSetting() && hoveredSync) {
                    this.getColorSetting().setSync(!this.getColorSetting().isSync());
                }
                if (hoveredCopy) {
                    final StringSelection selection = new StringSelection(TextUtil.get32BitString(this.getColorSetting().getRGB()));
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
                    ChatUtil.sendMessage("§aColor Copied: " + TextUtil.get32BitString(this.getColorSetting().getRGB()) + "!");
                }
                if (hoveredPaste && this.getClipBoard() != null) {
                    if (this.getColorSetting().fromString(this.getClipBoard()) == SettingResult.SUCCESSFUL) {
                        final float[] hsb = Color.RGBtoHSB(this.getColorSetting().getRed(), this.getColorSetting().getGreen(), this.getColorSetting().getBlue(), null);
                        this.hue = hsb[0];
                        this.saturation = hsb[1];
                        this.brightness = hsb[2];
                        this.alpha = this.getColorSetting().getAlpha() / 255.0f;
                        ChatUtil.sendMessage("§aColor Pasted: " + this.getClipBoard() + "!");
                    }
                    else {
                        ChatUtil.sendMessage("§cInvalid Color!");
                    }
                }
                if (!this.getColorSetting().isSync() && (!this.getColorSetting().isRainbow() || this.getColorSetting().isStaticRainbow()) && !hoveredRainbow && !hoveredSync) {
                    if (RenderUtil.mouseWithinBounds(mouseX, mouseY, colorPickerLeft, colorPickerTop - (((this.getColorSetting() == Managers.COLOR.getColorSetting()) ? 16 : 32) + (this.getColorSetting().isRainbow() ? 56 : 0)), this.getWidth() - 20.0f, this.getHeight() - 36.0f)) {
                        this.colorSelectorDragging = true;
                    }
                    if (RenderUtil.mouseWithinBounds(mouseX, mouseY, hueSliderLeft, colorPickerTop - (((this.getColorSetting() == Managers.COLOR.getColorSetting()) ? 16 : 32) + (this.getColorSetting().isRainbow() ? 56 : 0)), 4.0, this.getHeight() - 36.0f)) {
                        this.hueSelectorDragging = true;
                    }
                }
                if (!hoveredRainbow && !hoveredSync && RenderUtil.mouseWithinBounds(mouseX, mouseY, colorPickerLeft, alphaSliderTop, this.getWidth() - 20.0f, 4.0)) {
                    this.alphaSelectorDragging = true;
                }
                if (this.getColorSetting().isRainbow()) {
                    if (hoveredStatic) {
                        this.getColorSetting().setStaticRainbow(!this.getColorSetting().isStaticRainbow());
                    }
                    if (hoveredSpeed) {
                        this.setSlidingSpeed(true);
                    }
                    if (hoveredSaturation) {
                        this.setSlidingSaturation(true);
                    }
                    if (hoveredBrightness) {
                        this.setSlidingBrightness(true);
                    }
                }
            }
            if (hovered) {
                this.setColorExtended(!this.isColorExtended());
            }
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            if (this.colorSelectorDragging) {
                this.colorSelectorDragging = false;
            }
            if (this.alphaSelectorDragging) {
                this.alphaSelectorDragging = false;
            }
            if (this.hueSelectorDragging) {
                this.hueSelectorDragging = false;
            }
            if (this.slidingSpeed) {
                this.slidingSpeed = false;
            }
            if (this.slidingSaturation) {
                this.slidingSaturation = false;
            }
            if (this.slidingBrightness) {
                this.slidingBrightness = false;
            }
        }
    }
    
    private String getClipBoard() {
        try {
            return (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        }
        catch (HeadlessException | IOException | UnsupportedFlavorException ex) {
            final Exception ex2;
            final Exception e = ex2;
            e.printStackTrace();
            return null;
        }
    }
    
    private void updateColor(final int hex) {
        this.getColorSetting().setValue(new Color(hex >> 16 & 0xFF, hex >> 8 & 0xFF, hex & 0xFF, (int)(this.alpha * 255.0f)));
    }
    
    private void drawColorPickerRect(final float left, final float top, final float right, final float bottom) {
        final int hueBasedColor = Color.HSBtoRGB(this.hue, 1.0f, 1.0f);
        Render2DUtil.drawGradientRect(left, top, right, bottom, true, -1, hueBasedColor);
        Render2DUtil.drawGradientRect(left, top, right, bottom, false, 0, -16777216);
    }
    
    public ColorSetting getColorSetting() {
        return this.colorSetting;
    }
    
    public void setHue(final float hue) {
        this.hue = hue;
    }
    
    public void setSaturation(final float saturation) {
        this.saturation = saturation;
    }
    
    public void setBrightness(final float brightness) {
        this.brightness = brightness;
    }
    
    public float getAlpha() {
        return this.alpha;
    }
    
    public void setAlpha(final float alpha) {
        this.alpha = alpha;
    }
    
    public boolean isColorExtended() {
        return this.colorExtended;
    }
    
    public void setColorExtended(final boolean colorExtended) {
        this.colorExtended = colorExtended;
    }
    
    public void setSlidingSpeed(final boolean slidingSpeed) {
        this.slidingSpeed = slidingSpeed;
    }
    
    public void setSlidingSaturation(final boolean slidingSaturation) {
        this.slidingSaturation = slidingSaturation;
    }
    
    public void setSlidingBrightness(final boolean slidingBrightness) {
        this.slidingBrightness = slidingBrightness;
    }
}
