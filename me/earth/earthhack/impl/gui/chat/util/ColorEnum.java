// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.util;

import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.*;
import java.awt.*;
import me.earth.earthhack.impl.commands.hidden.*;
import me.earth.earthhack.api.setting.*;

public enum ColorEnum implements IColorIncrementor
{
    Red("§c") {
        @Override
        public Runnable getCommand(final ColorSetting s, final boolean i, final Module m) {
            int red = s.getRed();
            if ((red == 0 && !i) || (red == 255 && i)) {
                return () -> {};
            }
            red = (int)IncrementationUtil.crL(red, 0L, 255L, !i);
            final Color c = new Color(red, s.getGreen(), s.getBlue(), s.getAlpha());
            return () -> {
                s.setValue(c);
                HSettingCommand.update(s, m, null, true);
            };
        }
        
        @Override
        public int getValue(final ColorSetting s) {
            return s.getRed();
        }
    }, 
    Green("§a") {
        @Override
        public Runnable getCommand(final ColorSetting s, final boolean i, final Module m) {
            int green = s.getGreen();
            if ((green == 0 && !i) || (green == 255 && i)) {
                return () -> {};
            }
            green = (int)IncrementationUtil.crL(green, 0L, 255L, !i);
            final Color c = new Color(s.getRed(), green, s.getBlue(), s.getAlpha());
            return () -> {
                s.setValue(c);
                HSettingCommand.update(s, m, null, true);
            };
        }
        
        @Override
        public int getValue(final ColorSetting s) {
            return s.getGreen();
        }
    }, 
    Blue("§9") {
        @Override
        public Runnable getCommand(final ColorSetting s, final boolean i, final Module m) {
            int blue = s.getBlue();
            if ((blue == 0 && !i) || (blue == 255 && i)) {
                return () -> {};
            }
            blue = (int)IncrementationUtil.crL(blue, 0L, 255L, !i);
            final Color c = new Color(s.getRed(), s.getGreen(), blue, s.getAlpha());
            return () -> {
                s.setValue(c);
                HSettingCommand.update(s, m, null, true);
            };
        }
        
        @Override
        public int getValue(final ColorSetting s) {
            return s.getBlue();
        }
    }, 
    Alpha("§f") {
        @Override
        public Runnable getCommand(final ColorSetting s, final boolean i, final Module m) {
            int alpha = s.getAlpha();
            if ((alpha == 0 && !i) || (alpha == 255 && i)) {
                return () -> {};
            }
            alpha = (int)IncrementationUtil.crL(alpha, 0L, 255L, !i);
            final Color c = new Color(s.getRed(), s.getGreen(), s.getBlue(), alpha);
            return () -> {
                s.setValue(c);
                HSettingCommand.update(s, m, null, true);
            };
        }
        
        @Override
        public int getValue(final ColorSetting s) {
            return s.getAlpha();
        }
    };
    
    private final String textColor;
    
    private ColorEnum(final String textColor) {
        this.textColor = textColor;
    }
    
    public abstract int getValue(final ColorSetting p0);
    
    public String getTextColor() {
        return this.textColor;
    }
}
