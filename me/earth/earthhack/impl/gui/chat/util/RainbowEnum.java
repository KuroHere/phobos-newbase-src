// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.util;

import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.commands.hidden.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.math.*;

public enum RainbowEnum implements IColorIncrementor
{
    RainbowSpeed("<0 - 200>", "§f") {
        @Override
        public Runnable getCommand(final ColorSetting s, final boolean i, final Module m) {
            final float speed = (float)IncrementationUtil.crF(s.getRainbowSpeed(), 0.0, 200.0, !i);
            return () -> {
                s.setRainbowSpeed(speed);
                HSettingCommand.update(s, m, null, true);
            };
        }
        
        @Override
        public String getValue(final ColorSetting s) {
            return MathUtil.round(s.getRainbowSpeed(), 2) + "";
        }
    }, 
    RainbowSaturation("<0 - 100>", "§6") {
        @Override
        public Runnable getCommand(final ColorSetting s, final boolean i, final Module m) {
            final float sat = (float)IncrementationUtil.crF(s.getRainbowSaturation(), 0.0, 100.0, !i);
            return () -> {
                s.setRainbowSaturation(sat);
                HSettingCommand.update(s, m, null, true);
            };
        }
        
        @Override
        public String getValue(final ColorSetting s) {
            return MathUtil.round(s.getRainbowSaturation(), 2) + "";
        }
    }, 
    RainbowBrightness("<0 - 100>", "§f") {
        @Override
        public Runnable getCommand(final ColorSetting s, final boolean i, final Module m) {
            final float bright = (float)IncrementationUtil.crF(s.getRainbowBrightness(), 0.0, 100.0, !i);
            return () -> {
                s.setRainbowBrightness(bright);
                HSettingCommand.update(s, m, null, true);
            };
        }
        
        @Override
        public String getValue(final ColorSetting s) {
            return MathUtil.round(s.getRainbowBrightness(), 2) + "";
        }
    };
    
    private final String range;
    private final String color;
    
    private RainbowEnum(final String range, final String color) {
        this.range = range;
        this.color = color;
    }
    
    public abstract String getValue(final ColorSetting p0);
    
    public String getRange() {
        return this.range;
    }
    
    public String getColor() {
        return this.color;
    }
}
