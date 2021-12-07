// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.setting.settings;

import me.earth.earthhack.api.setting.*;
import com.google.gson.*;
import me.earth.earthhack.api.setting.event.*;

public class StringSetting extends Setting<String>
{
    public StringSetting(final String nameIn, final String initialValue) {
        super(nameIn, initialValue);
    }
    
    @Override
    public void fromJson(final JsonElement element) {
        this.setValue(element.getAsString());
    }
    
    @Override
    public SettingResult fromString(final String string) {
        this.setValue(string);
        return SettingResult.SUCCESSFUL;
    }
    
    @Override
    public String getInputs(final String string) {
        if (string == null || string.isEmpty()) {
            return "<name>";
        }
        return "";
    }
}
