// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.util;

import java.awt.*;

public class ColorFunctions
{
    public static Color applyFunctions(final Color color, final ColorFunction... functions) {
        final float[] hsl = HSLColor.fromRGB(color);
        final float alpha = color.getAlpha() / 255.0f;
        final float[] hsla = { hsl[0], hsl[1], hsl[2], alpha * 100.0f };
        for (final ColorFunction function : functions) {
            function.apply(hsla);
        }
        return HSLColor.toRGB(hsla[0], hsla[1], hsla[2], hsla[3] / 100.0f);
    }
    
    public static float clamp(final float value) {
        return (value < 0.0f) ? 0.0f : ((value > 100.0f) ? 100.0f : value);
    }
    
    public static Color mix(final Color color1, final Color color2, final float weight) {
        if (weight >= 1.0f) {
            return color1;
        }
        if (weight <= 0.0f) {
            return color2;
        }
        final int r1 = color1.getRed();
        final int g1 = color1.getGreen();
        final int b1 = color1.getBlue();
        final int a1 = color1.getAlpha();
        final int r2 = color2.getRed();
        final int g2 = color2.getGreen();
        final int b2 = color2.getBlue();
        final int a2 = color2.getAlpha();
        return new Color(Math.round(r2 + (r1 - r2) * weight), Math.round(g2 + (g1 - g2) * weight), Math.round(b2 + (b1 - b2) * weight), Math.round(a2 + (a1 - a2) * weight));
    }
    
    public static class HSLIncreaseDecrease implements ColorFunction
    {
        public final int hslIndex;
        public final boolean increase;
        public final float amount;
        public final boolean relative;
        public final boolean autoInverse;
        
        public HSLIncreaseDecrease(final int hslIndex, final boolean increase, final float amount, final boolean relative, final boolean autoInverse) {
            this.hslIndex = hslIndex;
            this.increase = increase;
            this.amount = amount;
            this.relative = relative;
            this.autoInverse = autoInverse;
        }
        
        @Override
        public void apply(final float[] hsla) {
            float amount2 = this.increase ? this.amount : (-this.amount);
            if (this.hslIndex == 0) {
                hsla[0] = (hsla[0] + amount2) % 360.0f;
                return;
            }
            amount2 = ((this.autoInverse && this.shouldInverse(hsla)) ? (-amount2) : amount2);
            hsla[this.hslIndex] = ColorFunctions.clamp(this.relative ? (hsla[this.hslIndex] * ((100.0f + amount2) / 100.0f)) : (hsla[this.hslIndex] + amount2));
        }
        
        protected boolean shouldInverse(final float[] hsla) {
            return this.increase ? (hsla[this.hslIndex] > 65.0f) : (hsla[this.hslIndex] < 35.0f);
        }
        
        @Override
        public String toString() {
            String name = null;
            switch (this.hslIndex) {
                case 0: {
                    name = "spin";
                    break;
                }
                case 1: {
                    name = (this.increase ? "saturate" : "desaturate");
                    break;
                }
                case 2: {
                    name = (this.increase ? "lighten" : "darken");
                    break;
                }
                case 3: {
                    name = (this.increase ? "fadein" : "fadeout");
                    break;
                }
                default: {
                    throw new IllegalArgumentException();
                }
            }
            return String.format("%s(%.0f%%%s%s)", name, this.amount, this.relative ? " relative" : "", this.autoInverse ? " autoInverse" : "");
        }
    }
    
    public static class Fade implements ColorFunction
    {
        public final float amount;
        
        public Fade(final float amount) {
            this.amount = amount;
        }
        
        @Override
        public void apply(final float[] hsla) {
            hsla[3] = ColorFunctions.clamp(this.amount);
        }
        
        @Override
        public String toString() {
            return String.format("fade(%.0f%%)", this.amount);
        }
    }
    
    public interface ColorFunction
    {
        void apply(final float[] p0);
    }
}
