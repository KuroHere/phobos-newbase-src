// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.setting.settings;

import me.earth.earthhack.api.setting.*;
import java.awt.*;
import com.google.gson.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.setting.event.*;
import me.earth.earthhack.api.observable.*;

public class ColorSetting extends Setting<Color>
{
    private int red;
    private int green;
    private int blue;
    private int alpha;
    private boolean sync;
    private boolean rainbow;
    private boolean staticRainbow;
    private float rainbowSpeed;
    private float rainbowSaturation;
    private float rainbowBrightness;
    private Color mutableInitial;
    
    public ColorSetting(final String nameIn, final Color initialValue) {
        super(nameIn, initialValue);
        this.rainbowSpeed = 100.0f;
        this.rainbowSaturation = 100.0f;
        this.rainbowBrightness = 100.0f;
        this.mutableInitial = initialValue;
        this.red = initialValue.getRed();
        this.green = initialValue.getGreen();
        this.blue = initialValue.getBlue();
        this.alpha = initialValue.getAlpha();
    }
    
    public void setInitial(final Color color) {
        this.mutableInitial = color;
    }
    
    @Override
    public Color getInitial() {
        return this.mutableInitial;
    }
    
    @Override
    public void reset() {
        this.value = (T)this.mutableInitial;
    }
    
    @Override
    public void setValue(final Color value, final boolean withEvent) {
        if (withEvent) {
            final SettingEvent<Color> event = ((Observable<SettingEvent<Color>>)this).onChange(new SettingEvent<Color>(this, value));
            if (!event.isCancelled()) {
                this.setValueRGBA(event.getValue());
            }
        }
        else {
            this.setValueRGBA(value);
        }
    }
    
    public void setValueAlpha(final Color value) {
        final Color newColor = new Color(value.getRed(), value.getGreen(), value.getBlue(), this.getValue().getAlpha());
        this.setValueRGBA(newColor);
    }
    
    @Override
    public void fromJson(final JsonElement element) {
        final String parse = element.getAsString();
        if (parse.contains("-")) {
            final String[] values = parse.split("-");
            if (values.length > 6) {
                int color = 0;
                try {
                    color = (int)Long.parseLong(values[0], 16);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                this.setValue(new Color(color, values[0].length() > 6));
                boolean syncBuf = false;
                try {
                    syncBuf = Boolean.parseBoolean(values[1]);
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
                this.setSync(syncBuf);
                boolean rainbowBuf = false;
                try {
                    rainbowBuf = Boolean.parseBoolean(values[2]);
                }
                catch (Exception e3) {
                    e3.printStackTrace();
                }
                this.setRainbow(rainbowBuf);
                boolean rainbowStaticBuf = false;
                try {
                    rainbowStaticBuf = Boolean.parseBoolean(values[3]);
                }
                catch (Exception e4) {
                    e4.printStackTrace();
                }
                this.setStaticRainbow(rainbowStaticBuf);
                float speed = 0.0f;
                try {
                    speed = (float)(int)Float.parseFloat(values[4]);
                }
                catch (Exception e5) {
                    e5.printStackTrace();
                }
                this.setRainbowSpeed(speed);
                float saturation = 0.0f;
                try {
                    saturation = (float)(int)Float.parseFloat(values[5]);
                }
                catch (Exception e6) {
                    e6.printStackTrace();
                }
                this.setRainbowSaturation(saturation);
                float brightness = 0.0f;
                try {
                    brightness = (float)(int)Float.parseFloat(values[6]);
                }
                catch (Exception e7) {
                    e7.printStackTrace();
                }
                this.setRainbowBrightness(brightness);
            }
        }
        else {
            int color2 = 0;
            try {
                color2 = (int)Long.parseLong(parse, 16);
            }
            catch (Exception e8) {
                e8.printStackTrace();
            }
            this.setValue(new Color(color2, parse.length() > 6));
        }
    }
    
    @Override
    public String toJson() {
        return TextUtil.get32BitString(((Color)this.value).getRGB()) + "-" + this.isSync() + "-" + this.isRainbow() + "-" + this.isStaticRainbow() + "-" + this.getRainbowSpeed() + "-" + this.getRainbowSaturation() + "-" + this.getRainbowBrightness();
    }
    
    @Override
    public SettingResult fromString(final String string) {
        if (string.contains("-")) {
            final String[] values = string.split("-");
            if (values.length > 6) {
                int color;
                try {
                    color = (int)Long.parseLong(values[0], 16);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return new SettingResult(false, e.getMessage());
                }
                this.setValue(new Color(color, values[0].length() > 6));
                boolean syncBuf;
                try {
                    syncBuf = Boolean.parseBoolean(values[1]);
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                    return new SettingResult(false, e2.getMessage());
                }
                this.setSync(syncBuf);
                boolean rainbowBuf;
                try {
                    rainbowBuf = Boolean.parseBoolean(values[2]);
                }
                catch (Exception e3) {
                    e3.printStackTrace();
                    return new SettingResult(false, e3.getMessage());
                }
                this.setRainbow(rainbowBuf);
                boolean rainbowStaticBuf;
                try {
                    rainbowStaticBuf = Boolean.parseBoolean(values[3]);
                }
                catch (Exception e4) {
                    e4.printStackTrace();
                    return new SettingResult(false, e4.getMessage());
                }
                this.setStaticRainbow(rainbowStaticBuf);
                float speed;
                try {
                    speed = (float)(int)Float.parseFloat(values[4]);
                }
                catch (Exception e5) {
                    e5.printStackTrace();
                    return new SettingResult(false, e5.getMessage());
                }
                this.setRainbowSpeed(speed);
                float saturation;
                try {
                    saturation = (float)(int)Float.parseFloat(values[5]);
                }
                catch (Exception e6) {
                    e6.printStackTrace();
                    return new SettingResult(false, e6.getMessage());
                }
                this.setRainbowSaturation(saturation);
                float brightness;
                try {
                    brightness = (float)(int)Float.parseFloat(values[6]);
                }
                catch (Exception e7) {
                    e7.printStackTrace();
                    return new SettingResult(false, e7.getMessage());
                }
                this.setRainbowBrightness(brightness);
            }
        }
        else {
            int color2;
            try {
                color2 = (int)Long.parseLong(string, 16);
            }
            catch (Exception e8) {
                e8.printStackTrace();
                return new SettingResult(false, e8.getMessage());
            }
            this.setValue(new Color(color2, string.length() > 6));
        }
        return SettingResult.SUCCESSFUL;
    }
    
    @Override
    public String getInputs(final String string) {
        if (string == null || string.isEmpty()) {
            return "<hex-string>";
        }
        return "";
    }
    
    public int getRed() {
        return this.red;
    }
    
    public float getR() {
        return this.red / 255.0f;
    }
    
    public void setRed(final int red) {
        this.red = red;
        this.setValue(new Color(red, this.blue, this.green, this.alpha));
    }
    
    public int getGreen() {
        return this.green;
    }
    
    public float getG() {
        return this.green / 255.0f;
    }
    
    public void setGreen(final int green) {
        this.green = green;
        this.setValue(new Color(this.red, this.blue, green, this.alpha));
    }
    
    public int getBlue() {
        return this.blue;
    }
    
    public float getB() {
        return this.blue / 255.0f;
    }
    
    public void setBlue(final int blue) {
        this.blue = blue;
        this.setValue(new Color(this.red, blue, this.green, this.alpha));
    }
    
    public int getAlpha() {
        return this.alpha;
    }
    
    public float getA() {
        return this.alpha / 255.0f;
    }
    
    public void setAlpha(final int alpha) {
        this.alpha = alpha;
        this.setValue(new Color(this.red, this.blue, this.green, alpha));
    }
    
    public int getRGB() {
        return ((Color)this.value).getRGB();
    }
    
    public boolean isSync() {
        return this.sync;
    }
    
    public void setSync(final boolean sync) {
        this.sync = sync;
    }
    
    public boolean isRainbow() {
        return this.rainbow;
    }
    
    public void setRainbow(final boolean rainbow) {
        this.rainbow = rainbow;
    }
    
    public float getRainbowSpeed() {
        return this.rainbowSpeed;
    }
    
    public void setRainbowSpeed(final float rainbowSpeed) {
        this.rainbowSpeed = rainbowSpeed;
    }
    
    public float getRainbowSaturation() {
        return this.rainbowSaturation;
    }
    
    public void setRainbowSaturation(final float rainbowSaturation) {
        this.rainbowSaturation = rainbowSaturation;
    }
    
    public float getRainbowBrightness() {
        return this.rainbowBrightness;
    }
    
    public void setRainbowBrightness(final float rainbowBrightness) {
        this.rainbowBrightness = rainbowBrightness;
    }
    
    public boolean isStaticRainbow() {
        return this.staticRainbow;
    }
    
    public void setStaticRainbow(final boolean staticRainbow) {
        this.staticRainbow = staticRainbow;
    }
    
    private void setValueRGBA(final Color value) {
        this.value = (T)value;
        this.red = value.getRed();
        this.blue = value.getBlue();
        this.green = value.getGreen();
        this.alpha = value.getAlpha();
    }
}
