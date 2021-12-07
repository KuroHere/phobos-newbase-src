// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.setting.settings;

import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.util.bind.*;
import com.google.gson.*;
import me.earth.earthhack.api.setting.event.*;
import org.lwjgl.input.*;

public class BindSetting extends Setting<Bind>
{
    public BindSetting(final String name) {
        this(name, Bind.none());
    }
    
    public BindSetting(final String name, final Bind initialValue) {
        super(name, initialValue);
    }
    
    @Override
    public void fromJson(final JsonElement element) {
        this.fromString(element.getAsString());
    }
    
    @Override
    public SettingResult fromString(final String string) {
        if ("none".equalsIgnoreCase(string)) {
            this.value = (T)Bind.none();
        }
        else {
            this.setValue(Bind.fromString(string));
        }
        return SettingResult.SUCCESSFUL;
    }
    
    @Override
    public String getInputs(final String string) {
        if (string == null || string.isEmpty()) {
            return "<key>";
        }
        if ("none".startsWith(string.toLowerCase())) {
            return "NONE";
        }
        for (int i = 0; i < 256; ++i) {
            final String keyName = Keyboard.getKeyName(i);
            if (keyName != null && keyName.toLowerCase().startsWith(string.toLowerCase())) {
                return keyName;
            }
        }
        return "";
    }
    
    public void setKey(final int key) {
        this.value = (T)Bind.fromKey(key);
    }
}
