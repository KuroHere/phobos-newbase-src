// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.setting.settings;

import me.earth.earthhack.api.setting.*;
import com.google.gson.*;
import me.earth.earthhack.api.setting.event.*;

public class BooleanSetting extends Setting<Boolean>
{
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    
    public BooleanSetting(final String nameIn, final Boolean initialValue) {
        super(nameIn, initialValue);
    }
    
    @Override
    public void fromJson(final JsonElement element) {
        this.setValue(element.getAsBoolean());
    }
    
    @Override
    public SettingResult fromString(final String string) {
        if ("true".equalsIgnoreCase(string)) {
            this.setValue(true);
            return SettingResult.SUCCESSFUL;
        }
        if ("false".equalsIgnoreCase(string)) {
            this.setValue(false);
            return SettingResult.SUCCESSFUL;
        }
        return new SettingResult(false, string + " is a bad input. Value should be \"true\" or \"false\".");
    }
    
    @Override
    public String getInputs(final String string) {
        if (string == null || string.isEmpty()) {
            return "<true/false>";
        }
        if ("true".startsWith(string.toLowerCase())) {
            return "true";
        }
        if ("false".startsWith(string.toLowerCase())) {
            return "false";
        }
        return "";
    }
}
