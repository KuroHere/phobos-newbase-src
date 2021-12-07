//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.font;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.client.customfont.*;
import net.minecraft.client.renderer.texture.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import me.earth.earthhack.impl.managers.*;
import java.awt.*;
import java.util.stream.*;
import java.util.*;
import me.earth.earthhack.impl.modules.*;

public class CustomFontRenderer extends CustomFont
{
    private static final String COLOR_CODES = "0123456789abcdefklmnorzy+-";
    private static final Random CHAR_RANDOM;
    private static final List<Character> RANDOM_CHARS;
    private static final SettingCache<Boolean, BooleanSetting, FontMod> SHADOW;
    protected CharData[] boldChars;
    protected CharData[] italicChars;
    protected CharData[] boldItalicChars;
    protected DynamicTexture texBold;
    protected DynamicTexture texItalic;
    protected DynamicTexture texBoth;
    private final int[] colorCode;
    
    public CustomFontRenderer(final Font font, final boolean antiAlias, final boolean fractionalMetrics) {
        super(font, antiAlias, fractionalMetrics);
        this.boldChars = new CharData[256];
        this.italicChars = new CharData[256];
        this.boldItalicChars = new CharData[256];
        this.colorCode = new int[32];
        this.setupMinecraftColorcodes();
        this.setupBoldItalicIDs();
    }
    
    public float drawStringWithShadow(final String text, final double x, final double y, final int color) {
        final float shadowWidth = this.drawString(text, x + 1.0, y + 1.0, color, true);
        return Math.max(shadowWidth, this.drawString(text, x, y, color, false));
    }
    
    public float drawString(final String text, final float x, final float y, final int color) {
        return this.drawString(text, x, y, color, false);
    }
    
    public float drawCenteredString(final String text, final float x, final float y, final int color) {
        return this.drawString(text, x - this.getStringWidth(text) / 2.0f, y, color);
    }
    
    public float drawCenteredStringWithShadow(final String text, final float x, final float y, final int color) {
        this.drawString(text, x - this.getStringWidth(text) / 2.0f + 1.0, y + 1.0, color, true);
        return this.drawString(text, x - this.getStringWidth(text) / 2.0f, y, color);
    }
    
    public float drawString(final String text, double x, double y, int color, final boolean shadow) {
        --x;
        if (text == null) {
            return 0.0f;
        }
        if (shadow) {
            if (CustomFontRenderer.SHADOW.getValue()) {
                x -= 0.4;
                y -= 0.4;
            }
            color = ((color & 0xFCFCFC) >> 2 | (color & 0xFF000000));
        }
        CharData[] currentData = this.charData;
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        boolean random = false;
        boolean bold = false;
        boolean italic = false;
        boolean strike = false;
        boolean underline = false;
        boolean rainbowP = false;
        boolean rainbowM = false;
        x *= 2.0;
        y = (y - 3.0) * 2.0;
        GL11.glPushMatrix();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, alpha);
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(this.tex.getGlTextureId());
        GL11.glBindTexture(3553, this.tex.getGlTextureId());
        for (int i = 0; i < text.length(); ++i) {
            char character = text.charAt(i);
            if (character == '§' && i + 1 < text.length()) {
                int colorIndex = "0123456789abcdefklmnorzy+-".indexOf(text.charAt(i + 1));
                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                    random = false;
                    underline = false;
                    strike = false;
                    rainbowP = false;
                    rainbowM = false;
                    GlStateManager.bindTexture(this.tex.getGlTextureId());
                    currentData = this.charData;
                    if (colorIndex < 0) {
                        colorIndex = 15;
                    }
                    if (shadow) {
                        colorIndex += 16;
                    }
                    final int colorcode = this.colorCode[colorIndex];
                    GlStateManager.color((colorcode >> 16 & 0xFF) / 255.0f, (colorcode >> 8 & 0xFF) / 255.0f, (colorcode & 0xFF) / 255.0f, alpha);
                }
                else if (colorIndex == 16) {
                    random = true;
                }
                else if (colorIndex == 17) {
                    bold = true;
                    if (italic) {
                        GlStateManager.bindTexture(this.texBoth.getGlTextureId());
                        currentData = this.boldItalicChars;
                    }
                    else {
                        GlStateManager.bindTexture(this.texBold.getGlTextureId());
                        currentData = this.boldChars;
                    }
                }
                else if (colorIndex == 18) {
                    strike = true;
                }
                else if (colorIndex == 19) {
                    underline = true;
                }
                else if (colorIndex == 20) {
                    italic = true;
                    if (bold) {
                        GlStateManager.bindTexture(this.texBoth.getGlTextureId());
                        currentData = this.boldItalicChars;
                    }
                    else {
                        GlStateManager.bindTexture(this.texItalic.getGlTextureId());
                        currentData = this.italicChars;
                    }
                }
                else if (colorIndex == 21) {
                    bold = false;
                    italic = false;
                    random = false;
                    underline = false;
                    strike = false;
                    rainbowP = false;
                    rainbowM = false;
                    GlStateManager.color((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, alpha);
                    GlStateManager.bindTexture(this.tex.getGlTextureId());
                    currentData = this.charData;
                }
                else if (colorIndex == 22) {
                    bold = false;
                    italic = false;
                    random = false;
                    underline = false;
                    strike = false;
                    rainbowP = false;
                    rainbowM = false;
                    GlStateManager.bindTexture(this.tex.getGlTextureId());
                    currentData = this.charData;
                    final char[] h = new char[8];
                    if (i + 9 < text.length()) {
                        for (int j = 0; j < 8; ++j) {
                            h[j] = text.charAt(i + j + 2);
                        }
                        int colorcode2;
                        try {
                            colorcode2 = (int)Long.parseLong(new String(h), 16);
                        }
                        catch (Exception e) {
                            continue;
                        }
                        GlStateManager.color((colorcode2 >> 16 & 0xFF) / 255.0f / (shadow ? 4 : 1), (colorcode2 >> 8 & 0xFF) / 255.0f / (shadow ? 4 : 1), (colorcode2 & 0xFF) / 255.0f / (shadow ? 4 : 1), (colorcode2 >> 24 & 0xFF) / 255.0f);
                        i += 9;
                        continue;
                    }
                    ++i;
                    continue;
                }
                else if (colorIndex == 23) {
                    bold = false;
                    italic = false;
                    random = false;
                    underline = false;
                    strike = false;
                    rainbowP = false;
                    rainbowM = false;
                    GlStateManager.bindTexture(this.tex.getGlTextureId());
                    currentData = this.charData;
                    final int rainbow = Color.HSBtoRGB(Managers.COLOR.getHue(), 1.0f, 1.0f);
                    GlStateManager.color((rainbow >> 16 & 0xFF) / 255.0f / (shadow ? 4 : 1), (rainbow >> 8 & 0xFF) / 255.0f / (shadow ? 4 : 1), (rainbow & 0xFF) / 255.0f / (shadow ? 4 : 1), alpha);
                }
                else if (colorIndex == 24) {
                    bold = false;
                    italic = false;
                    random = false;
                    underline = false;
                    strike = false;
                    rainbowP = true;
                    rainbowM = false;
                    GlStateManager.bindTexture(this.tex.getGlTextureId());
                    currentData = this.charData;
                }
                else {
                    bold = false;
                    italic = false;
                    random = false;
                    underline = false;
                    strike = false;
                    rainbowP = false;
                    rainbowM = true;
                    GlStateManager.bindTexture(this.tex.getGlTextureId());
                    currentData = this.charData;
                }
                ++i;
            }
            else if (character < currentData.length) {
                if (random) {
                    final int w = currentData[character].width;
                    final CharData[] finalCurrentData = currentData;
                    final List<Character> randoms = CustomFontRenderer.RANDOM_CHARS.stream().filter(c -> {
                        if (c < finalCurrentData.length) {
                            return finalCurrentData[(char)c].width == w;
                        }
                        else {
                            return false;
                        }
                    }).collect((Collector<? super Object, ?, List<Character>>)Collectors.toList());
                    if (randoms.size() != 0) {
                        character = randoms.get(CustomFontRenderer.CHAR_RANDOM.nextInt(randoms.size()));
                    }
                }
                if (rainbowP || rainbowM) {
                    final int rainbow2 = Color.HSBtoRGB(Managers.COLOR.getHueByPosition(rainbowM ? y : x), 1.0f, 1.0f);
                    GlStateManager.color((rainbow2 >> 16 & 0xFF) / 255.0f / (shadow ? 4 : 1), (rainbow2 >> 8 & 0xFF) / 255.0f / (shadow ? 4 : 1), (rainbow2 & 0xFF) / 255.0f / (shadow ? 4 : 1), alpha);
                }
                GL11.glBegin(4);
                this.drawChar(currentData, character, (float)x, (float)y);
                GL11.glEnd();
                if (strike) {
                    this.drawLine(x, y + currentData[character].height / 2.0, x + currentData[character].width - 8.0, y + currentData[character].height / 2.0);
                }
                if (underline) {
                    this.drawLine(x, y + currentData[character].height - 2.0, x + currentData[character].width - 8.0, y + currentData[character].height - 2.0);
                }
                x += currentData[character].width - 8 + this.charOffset;
            }
        }
        GL11.glHint(3155, 4352);
        GL11.glPopMatrix();
        return (float)(x / 2.0);
    }
    
    @Override
    public int getStringWidth(final String text) {
        if (text == null) {
            return 0;
        }
        CharData[] currentData = this.charData;
        int width = 0;
        boolean bold = false;
        boolean italic = false;
        for (int i = 0; i < text.length(); ++i) {
            final char character = text.charAt(i);
            if (character == '§' && i + 1 < text.length()) {
                final int colorIndex = "0123456789abcdefklmnorzy+-".indexOf(text.charAt(i + 1));
                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                }
                else if (colorIndex == 17) {
                    bold = true;
                    if (italic) {
                        currentData = this.boldItalicChars;
                    }
                    else {
                        currentData = this.boldChars;
                    }
                }
                else if (colorIndex == 20) {
                    italic = true;
                    if (bold) {
                        currentData = this.boldItalicChars;
                    }
                    else {
                        currentData = this.italicChars;
                    }
                }
                else if (colorIndex == 21) {
                    bold = false;
                    italic = false;
                    currentData = this.charData;
                }
                else {
                    if (colorIndex == 22) {
                        bold = false;
                        italic = false;
                        currentData = this.charData;
                        i += 9;
                        continue;
                    }
                    if (colorIndex == 23) {
                        bold = false;
                        italic = false;
                    }
                    else if (colorIndex == 24) {
                        bold = false;
                        italic = false;
                    }
                    else {
                        bold = false;
                        italic = false;
                    }
                }
                ++i;
            }
            else if (character < currentData.length) {
                width += currentData[character].width - 8 + this.charOffset;
            }
        }
        return width / 2;
    }
    
    @Override
    public void setFont(final Font font) {
        super.setFont(font);
        this.setupBoldItalicIDs();
    }
    
    @Override
    public void setAntiAlias(final boolean antiAlias) {
        super.setAntiAlias(antiAlias);
        this.setupBoldItalicIDs();
    }
    
    @Override
    public void setFractionalMetrics(final boolean fractionalMetrics) {
        super.setFractionalMetrics(fractionalMetrics);
        this.setupBoldItalicIDs();
    }
    
    private void setupBoldItalicIDs() {
        this.texBold = this.setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
        this.texItalic = this.setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
        this.texBoth = this.setupTexture(this.font.deriveFont(3), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
    }
    
    private void drawLine(final double x, final double y, final double x1, final double y1) {
        GL11.glDisable(3553);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glEnable(3553);
    }
    
    public List<String> wrapWords(final String text, final double width) {
        final List<String> result = new ArrayList<String>();
        if (this.getStringWidth(text) > width) {
            final String[] words = text.split(" ");
            StringBuilder current = new StringBuilder();
            char lastColorCode = '\uffff';
            for (final String word : words) {
                final char[] array = word.toCharArray();
                for (int i = 0; i < array.length; ++i) {
                    final char c = array[i];
                    if (c == '§' && i + 1 < array.length) {
                        lastColorCode = array[i + 1];
                    }
                }
                if (this.getStringWidth((Object)current + word + " ") < width) {
                    current.append(word).append(" ");
                }
                else {
                    result.add(current.toString());
                    current = new StringBuilder("§").append(lastColorCode).append(word).append(" ");
                }
            }
            if (current.length() > 0) {
                if (this.getStringWidth(current.toString()) < width) {
                    result.add("§" + lastColorCode + (Object)current + " ");
                }
                else {
                    result.addAll(this.formatString(current.toString(), width));
                }
            }
        }
        else {
            result.add(text);
        }
        return result;
    }
    
    public List<String> formatString(final String string, final double width) {
        final List<String> result = new ArrayList<String>();
        StringBuilder current = new StringBuilder();
        char lastColorCode = '\uffff';
        final char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            final char c = chars[i];
            if (c == '§' && i < chars.length - 1) {
                lastColorCode = chars[i + 1];
            }
            if (this.getStringWidth(current.toString() + c) < width) {
                current.append(c);
            }
            else {
                result.add(current.toString());
                current = new StringBuilder("§").append(lastColorCode).append(c);
            }
        }
        if (current.length() > 0) {
            result.add(current.toString());
        }
        return result;
    }
    
    private void setupMinecraftColorcodes() {
        for (int i = 0; i < 32; ++i) {
            final int o = (i >> 3 & 0x1) * 85;
            int r = (i >> 2 & 0x1) * 170 + o;
            int g = (i >> 1 & 0x1) * 170 + o;
            int b = (i & 0x1) * 170 + o;
            if (i == 6) {
                r += 85;
            }
            if (i >= 16) {
                r /= 4;
                g /= 4;
                b /= 4;
            }
            this.colorCode[i] = ((r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF));
        }
    }
    
    static {
        CHAR_RANDOM = new Random();
        RANDOM_CHARS = new ArrayList<Character>((Collection<? extends Character>)"\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8£\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1ªº¿®¬½¼¡«»\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261±\u2265\u2264\u2320\u2321\u00f7\u2248°\u2219·\u221a\u207f²\u25a0\u0000".chars().mapToObj(c -> Character.valueOf((char)c)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        SHADOW = Caches.getSetting(FontMod.class, BooleanSetting.class, "Shadow", false);
    }
}
