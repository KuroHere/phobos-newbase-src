//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render;

import net.minecraft.client.gui.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.client.hud.*;
import java.util.regex.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.renderer.*;
import me.earth.earthhack.impl.managers.*;
import java.awt.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ FontRenderer.class })
public abstract class MixinFontRenderer
{
    private static final SettingCache<Boolean, BooleanSetting, HUD> SHADOW;
    private static final String COLOR_CODES = "0123456789abcdefklmnorzy+-";
    private static final Pattern CUSTOM_PATTERN;
    @Shadow
    private boolean randomStyle;
    @Shadow
    private boolean boldStyle;
    @Shadow
    private boolean italicStyle;
    @Shadow
    private boolean underlineStyle;
    @Shadow
    private boolean strikethroughStyle;
    @Shadow
    private int textColor;
    @Shadow
    protected float posX;
    @Shadow
    protected float posY;
    @Shadow
    private float alpha;
    private int skip;
    private int currentIndex;
    private boolean currentShadow;
    private String currentText;
    private boolean rainbowPlus;
    private boolean rainbowMinus;
    
    @Shadow
    protected abstract int renderString(final String p0, final float p1, final float p2, final int p3, final boolean p4);
    
    @Redirect(method = { "drawString(Ljava/lang/String;FFIZ)I" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderString(Ljava/lang/String;FFIZ)I"))
    public int renderStringHook(final FontRenderer fontrenderer, final String text, final float x, final float y, final int color, final boolean dropShadow) {
        if (dropShadow && MixinFontRenderer.SHADOW.getValue()) {
            return this.renderString(text, x - 0.4f, y - 0.4f, color, true);
        }
        return this.renderString(text, x, y, color, dropShadow);
    }
    
    @Inject(method = { "renderStringAtPos" }, at = { @At("HEAD") })
    public void resetSkip(final String text, final boolean shadow, final CallbackInfo info) {
        this.skip = 0;
        this.currentIndex = 0;
        this.currentText = text;
        this.currentShadow = shadow;
    }
    
    @Redirect(method = { "renderStringAtPos" }, at = @At(value = "INVOKE", target = "Ljava/lang/String;charAt(I)C", ordinal = 0))
    public char charAtHook(final String text, final int index) {
        this.currentIndex = index;
        return this.getCharAt(text, index);
    }
    
    @Redirect(method = { "renderStringAtPos" }, at = @At(value = "INVOKE", target = "Ljava/lang/String;charAt(I)C", ordinal = 1))
    public char charAtHook1(final String text, final int index) {
        return this.getCharAt(text, index);
    }
    
    @Redirect(method = { "renderStringAtPos" }, at = @At(value = "INVOKE", target = "Ljava/lang/String;length()I", ordinal = 0))
    public int lengthHook(final String string) {
        return string.length() - this.skip;
    }
    
    @Redirect(method = { "renderStringAtPos" }, at = @At(value = "INVOKE", target = "Ljava/lang/String;length()I", ordinal = 1))
    public int lengthHook1(final String string) {
        return string.length() - this.skip;
    }
    
    @Redirect(method = { "renderStringAtPos" }, at = @At(value = "INVOKE", target = "Ljava/lang/String;indexOf(I)I", ordinal = 0))
    public int colorCodeHook(final String colorCode, final int ch) {
        final int result = "0123456789abcdefklmnorzy+-".indexOf(String.valueOf(this.currentText.charAt(this.currentIndex + this.skip + 1)).toLowerCase().charAt(0));
        if (result == 22) {
            this.randomStyle = false;
            this.boldStyle = false;
            this.strikethroughStyle = false;
            this.underlineStyle = false;
            this.italicStyle = false;
            this.rainbowPlus = false;
            this.rainbowMinus = false;
            final char[] h = new char[8];
            try {
                for (int j = 0; j < 8; ++j) {
                    h[j] = this.currentText.charAt(this.currentIndex + this.skip + j + 2);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return result;
            }
            int colorcode = -1;
            try {
                colorcode = (int)Long.parseLong(new String(h), 16);
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
            this.textColor = colorcode;
            GlStateManager.color((colorcode >> 16 & 0xFF) / 255.0f / (this.currentShadow ? 4 : 1), (colorcode >> 8 & 0xFF) / 255.0f / (this.currentShadow ? 4 : 1), (colorcode & 0xFF) / 255.0f / (this.currentShadow ? 4 : 1), (colorcode >> 24 & 0xFF) / 255.0f);
            this.skip += 8;
        }
        else if (result == 23) {
            this.randomStyle = false;
            this.boldStyle = false;
            this.strikethroughStyle = false;
            this.underlineStyle = false;
            this.italicStyle = false;
            this.rainbowPlus = false;
            this.rainbowMinus = false;
            final int rainbow = Color.HSBtoRGB(Managers.COLOR.getHue(), 1.0f, 1.0f);
            GlStateManager.color((rainbow >> 16 & 0xFF) / 255.0f / (this.currentShadow ? 4 : 1), (rainbow >> 8 & 0xFF) / 255.0f / (this.currentShadow ? 4 : 1), (rainbow & 0xFF) / 255.0f / (this.currentShadow ? 4 : 1), (rainbow >> 24 & 0xFF) / 255.0f);
        }
        else if (result == 24) {
            this.randomStyle = false;
            this.boldStyle = false;
            this.strikethroughStyle = false;
            this.underlineStyle = false;
            this.italicStyle = false;
            this.rainbowPlus = true;
            this.rainbowMinus = false;
        }
        else if (result == 25) {
            this.randomStyle = false;
            this.boldStyle = false;
            this.strikethroughStyle = false;
            this.underlineStyle = false;
            this.italicStyle = false;
            this.rainbowPlus = false;
            this.rainbowMinus = true;
        }
        else {
            this.rainbowPlus = false;
            this.rainbowMinus = false;
        }
        return result;
    }
    
    @Inject(method = { "resetStyles" }, at = { @At("HEAD") })
    public void resetStylesHook(final CallbackInfo info) {
        this.rainbowPlus = false;
        this.rainbowMinus = false;
    }
    
    @Inject(method = { "renderStringAtPos" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderChar(CZ)F", shift = At.Shift.BEFORE, ordinal = 0) })
    public void renderCharHook(final String text, final boolean shadow, final CallbackInfo info) {
        if (this.rainbowPlus || this.rainbowMinus) {
            final int rainbow = Color.HSBtoRGB(Managers.COLOR.getHueByPosition(this.rainbowMinus ? ((double)this.posY) : ((double)this.posX)), 1.0f, 1.0f);
            GlStateManager.color((rainbow >> 16 & 0xFF) / 255.0f / (shadow ? 4 : 1), (rainbow >> 8 & 0xFF) / 255.0f / (shadow ? 4 : 1), (rainbow & 0xFF) / 255.0f / (shadow ? 4 : 1), this.alpha);
        }
    }
    
    @ModifyVariable(method = { "getStringWidth" }, at = @At("HEAD"), ordinal = 0)
    private String setText(final String text) {
        return (text == null) ? null : MixinFontRenderer.CUSTOM_PATTERN.matcher(text).replaceAll("§b");
    }
    
    private char getCharAt(final String text, final int index) {
        if (index + this.skip >= text.length()) {
            return text.charAt(text.length() - 1);
        }
        return text.charAt(index + this.skip);
    }
    
    static {
        SHADOW = Caches.getSetting(HUD.class, BooleanSetting.class, "Shadow", false);
        CUSTOM_PATTERN = Pattern.compile("(?i)§Z[0-9A-F]{8}");
    }
}
