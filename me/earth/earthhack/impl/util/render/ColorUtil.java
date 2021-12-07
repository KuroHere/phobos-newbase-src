// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.render;

import java.awt.*;

public class ColorUtil
{
    public static int toARGB(final int r, final int g, final int b) {
        return toARGB(r, g, b, 255);
    }
    
    public static int toARGB(final int r, final int g, final int b, final int a) {
        return (r << 16) + (g << 8) + b + (a << 24);
    }
    
    public static int toARGB(final Color color) {
        return toARGB(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }
    
    public static float[] toArray(final Color color) {
        return new float[] { color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f };
    }
    
    public static float[] toArray(final int color) {
        return new float[] { (color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, (color >> 24 & 0xFF) / 255.0f };
    }
    
    public static int staticRainbow(final float offset, final Color color) {
        final double timer = System.currentTimeMillis() % 1750.0 / 850.0;
        final float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        final float brightness = (float)(hsb[2] * Math.abs((offset + timer) % 1.0 - 0.550000011920929) + 0.44999998807907104);
        return Color.HSBtoRGB(hsb[0], hsb[1], brightness);
    }
    
    public static Color getRainbow(final int speed, final int offset, final float s, final float brightness) {
        float hue = (float)((System.currentTimeMillis() + offset) % speed);
        hue /= speed;
        return Color.getHSBColor(hue, s, brightness);
    }
}
